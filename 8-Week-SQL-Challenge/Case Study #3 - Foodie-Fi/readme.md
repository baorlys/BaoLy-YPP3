# Case Study #3 - Foodie-Fi
![img](https://8weeksqlchallenge.com/images/case-study-designs/3.png)

**4 SECTIONS**

[A. Customer Journey](#a-customer-journey)

[B. Data Analysis Questions](#b-data-analysis-questions)

[C. Challenge Payment Question](#c-challenge-payment-question)

[D. Outside The Box Questions](#d-outside-the-box-questions)

---

## **A. Customer Journey**

Based off the 8 sample customers provided in the sample from the subscriptions table, write a brief description about each customer’s onboarding journey.

Try to keep it as short as possible - you may also want to run some sort of join to make your explanations a bit easier!

```sql
SELECT 
 customer_id,
    plan_name,
    start_date
FROM subscriptions s
JOIN plans p ON p.plan_id = s.plan_id
```

## **B. Data Analysis Questions**

**1 - How many customers has Foodie-Fi ever had?**

```sql
SELECT
 COUNT(DISTINCT customer_id) AS customer_count
FROM subscriptions

```

**2 - What is the monthly distribution of `trial` plan `start_date` values for our dataset - use the start of the month as the group by value**

```sql
SELECT
 MONTH(start_date) AS month_of_year,
 COUNT(*) AS trial_plan_count
FROM subscriptions AS s
JOIN plans AS p ON s.plan_id = p.plan_id
WHERE plan_name = 'trial'
GROUP BY month_of_year
ORDER BY month_of_year
```

**3 - What plan `start_date` values occur after the year 2020 for our dataset? Show the breakdown by count of events for each `plan_name`**

```sql
SELECT
 s.plan_id,
 plan_name,
 COUNT(*) AS plan_count
FROM subscriptions AS s
JOIN plans AS p ON s.plan_id = p.plan_id
WHERE YEAR(start_date) > 2020
GROUP BY s.plan_id, plan_name
ORDER BY s.plan_id

```

**4 - What is the customer count and percentage of customers who have churned rounded to 1 decimal place?**

```sql
SELECT
 SUM(IF(plan_name = 'churn',1,0)) AS churn_count,
 ROUND(SUM(IF(plan_name = 'churn',1,0)) / 
 COUNT(DISTINCT customer_id) *100,1)  AS churn_count
FROM subscriptions AS s
JOIN plans AS p ON s.plan_id = p.plan_id

```

**5 - How many customers have churned straight after their initial free trial - what percentage is this rounded to the nearest whole number?**

```sql
WITH cte_rank AS (
 SELECT
  *,
  ROW_NUMBER() OVER (PARTITION BY customer_id) AS ranking
 FROM subscriptions
)

SELECT
 COUNT(*) AS customer_count,
    ROUND(COUNT(*)/(SELECT COUNT(DISTINCT customer_id) FROM subscriptions)*100) AS percentage
FROM cte_rank
WHERE customer_id IN
  (SELECT
   customer_id
  FROM cte_rank
  WHERE (ranking = 1 AND plan_id = 0))
  AND ranking = 2 AND plan_id = 4
```

**6 - What is the number and percentage of customer plans after their initial free trial?**

```sql
SELECT
 plan_id,
    COUNT(customer_id) AS customer_count,
 ROUND(COUNT(customer_id)/(SELECT COUNT(DISTINCT customer_id) FROM subscriptions)*100,1) AS percentage
FROM
 ( SELECT
   *,
   LAG(plan_id) OVER (PARTITION BY customer_id) AS previous_plan
  FROM subscriptions) AS subquery
WHERE previous_plan = 0
GROUP BY plan_id
ORDER BY plan_id
```

**7 - What is the customer count and percentage breakdown of all 5 `plan_name` values at `2020-12-31`?**

```sql
SELECT
 plan_id,
    COUNT(customer_id) AS customer_count,
    ROUND(COUNT(customer_id)/(SELECT COUNT(DISTINCT customer_id) FROM subscriptions)*100,1) AS percentage
FROM (
 SELECT
  *,
  LAST_VALUE(plan_id) OVER(PARTITION BY customer_id) AS last_plan_id
 FROM subscriptions
    WHERE start_date <= '2020-12-31') AS subquery
WHERE plan_id = last_plan_id
GROUP BY plan_id
ORDER BY plan_id

```

**8 - How many customers have upgraded to an annual plan in 2020?**

```sql
SELECT
 COUNT(DISTINCT customer_id) AS customer_count
FROM subscriptions
WHERE YEAR(start_date) = 2020 AND plan_id = 3
```

**9 - How many days on average does it take for a customer to an annual plan from the day they join Foodie-Fi?**

```sql
SELECT
 ROUND(AVG(TIMESTAMPDIFF(DAY, start_date, annual_date)),0) AS avg_date_join
FROM subscriptions AS s1
JOIN (
 SELECT
  customer_id,
  start_date AS annual_date
 FROM subscriptions
    WHERE plan_id = 3) AS s2 ON s1.customer_id = s2.customer_id
WHERE plan_id = 0
```

**10 - Can you further breakdown this average value into 30 day periods (i.e. 0-30 days, 31-60 days etc)**

```sql
SELECT
 CONCAT(TIMESTAMPDIFF(MONTH, start_date, annual_date) *30,' - ',(TIMESTAMPDIFF(MONTH, start_date, annual_date) + 1)*30,' days') AS period,
    TIMESTAMPDIFF(MONTH, start_date, annual_date) AS period_month,
    COUNT(*) AS customer_count
FROM subscriptions AS s1
JOIN (
 SELECT
  customer_id,
  start_date AS annual_date
 FROM subscriptions
    WHERE plan_id = 3) AS s2 ON s1.customer_id = s2.customer_id
WHERE plan_id = 0
GROUP BY period,period_month
ORDER BY period_month

```

**11 - How many customers downgraded from a pro monthly to a basic monthly plan in 2020?**

```sql
SELECT
    COUNT(DISTINCT customer_id) AS customer_count
FROM
 ( SELECT
   *,
   LAG(plan_id) OVER (PARTITION BY customer_id) AS previous_plan
  FROM subscriptions) AS sub
WHERE plan_id = 2 AND previous_plan = 3

```

## **C. Challenge Payment Question**

The Foodie-Fi team wants you to create a new payments table for the year 2020 that includes amounts paid by each customer in the subscriptions table with the following requirements:

- monthly payments always occur on the same day of month as the original start_date of any monthly paid plan
- upgrades from basic to monthly or pro plans are reduced by the current paid amount in that month and start immediately
- upgrades from pro monthly to pro annual are paid at the end of the current billing period and also starts at the end of the month period
- once a customer churns they will no longer make payments


```sql
CREATE VIEW subscriptions_new AS (
 SELECT
  customer_id,
        plan_id,
        start_date,
        LEAD(start_date) OVER(PARTITION BY customer_id) AS next_plan
 FROM subscriptions
    WHERE plan_id != 0 AND YEAR(start_date) = 2020
);

WITH RECURSIVE generate_month AS (
 SELECT 
  customer_id,
        plan_id,
  start_date,
        next_plan,
        start_date AS payment_date
 FROM subscriptions_new s
 UNION
 SELECT 
  customer_id,
        plan_id,
  start_date,
        next_plan,
        payment_date + INTERVAL 1 MONTH
 FROM generate_month g
 WHERE 
  plan_id < 3  AND
  CASE WHEN next_plan IS NOT NULL THEN payment_date < next_plan 
        ELSE YEAR(payment_date + INTERVAL 1 MONTH) <= 2020  END
 UNION
 SELECT 
  customer_id,
        plan_id,
  start_date,
        next_plan,
        payment_date + INTERVAL 1 YEAR
 FROM generate_month g
 WHERE 
  plan_id = 3 AND 
  CASE WHEN next_plan IS NOT NULL THEN payment_date < next_plan 
        ELSE YEAR(payment_date + INTERVAL 1 YEAR) <= 2020  END
)
SELECT 
 customer_id,
    gm.plan_id,
    plan_name,
    payment_date,
    price AS amount,
    ROW_NUMBER() OVER(PARTITION BY customer_id) as payment_order
FROM generate_month gm
JOIN plans p ON gm.plan_id = p.plan_id
WHERE p.plan_id != 4
ORDER BY customer_id,gm.plan_id;
```

## **D. Outside The Box Questions**

**1 - How would you calculate the rate of growth for Foodie-Fi?**

```sql
-- growth rate customer each month in 2020
WITH count_customer_each_month AS (
 SELECT
  MONTH(start_date) AS month_calendar,
  COUNT(DISTINCT customer_id) AS customer_count
 FROM subscriptions
 WHERE YEAR(start_date) = 2020
 GROUP BY month_calendar )
SELECT 
 *,
    CASE 
  WHEN LAG(customer_count) OVER() IS NOT NULL THEN ROUND((customer_count-LAG(customer_count) OVER())/LAG(customer_count) OVER() * 100,2) 
  ELSE 0 END AS growth_rate
FROM count_customer_each_month

-- grow rate revenue each month in 2020
WITH revenue_each_month AS (
 SELECT
  MONTH(payment_date) AS month_calendar,
  SUM(amount) AS amount
 FROM payments
 GROUP BY month_calendar
 ORDER BY month_calendar)
SELECT 
 *,
    CASE 
  WHEN LAG(amount) OVER() IS NOT NULL THEN ROUND((amount-LAG(amount) OVER())/LAG(amount) OVER() * 100,2) 
  ELSE 0 END AS growth_rate
FROM revenue_each_month
```

---

[**Case Study #4 - Data Bank**](../Case%20Study%20%234%20-%20Data%20Bank)
