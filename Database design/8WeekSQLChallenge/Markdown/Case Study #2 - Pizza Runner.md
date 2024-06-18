# Case Study #2 - Pizza Runner

# **A. Pizza Metrics**

**1. How many pizzas were ordered?**

```sql
SELECT count(order_id) AS pizza_order_count
FROM customer_orders
```

**2. How many unique customer orders were made?**

```sql
SELECT count(DISTINCT order_id) AS unique_customer_count
FROM customer_orders
```

**3. How many successful orders were delivered by each runner?**

```sql
SELECT runner_id,
		count(pickup_time) AS count
FROM runner_orders
WHERE pickup_time != 'null'
GROUP BY runner_id

```

**4. How many of each type of pizza was delivered?**

```sql
WITH cte_order_id_success_delivered as (
SELECT order_id
FROM runner_orders
WHERE pickup_time != 'null'
)

SELECT
	pizza_id,
    COUNT(order_id)
FROM customer_orders
WHERE order_id IN ( SELECT order_id FROM cte_order_id_success_delivered)
GROUP BY pizza_id

```

**5. How many Vegetarian and Meatlovers were ordered by each customer?**

```sql
SELECT
	customer_id,
  pizza_name,
  COUNT(pn.pizza_id) AS pizza_count
FROM customer_orders as co
JOIN pizza_names AS pn ON pn.pizza_id = co.pizza_id
GROUP BY customer_id, pizza_name
ORDER BY customer_id
```

**6. What was the maximum number of pizzas delivered in a single order?**

```sql
SELECT
		order_id,
		COUNT(pizza_id) AS pizza_count
FROM customer_orders
GROUP BY order_id
```

**7. For each customer, how many delivered pizzas had at least 1 change and how many had no changes?**

```sql
WITH cte_order_id_success_delivered as (
SELECT order_id
FROM runner_orders
WHERE pickup_time != 'null'
)

SELECT 
	customer_id,
    count(CASE
			WHEN (exclusions not in ('null','') and exclusions is not NULL)  or ( extras not in ('null','') and extras is not NULL) THEN 1
		END) as at_least_1_change,
	count(CASE
			WHEN (exclusions in ('null','') or exclusions is NULL)  and ( extras in ('null','')  or extras is NULL) THEN 1
		END) as no_change
FROM customer_orders
WHERE order_id in (SELECT order_id FROM cte_order_id_success_delivered)
GROUP BY customer_id
```

**8. How many pizzas were delivered that had both exclusions and extras?**

```sql
SELECT 
    count(CASE
			WHEN (exclusions not in ('null','') and exclusions is not NULL)  and ( extras not in ('null','') and extras is not NULL) THEN 1
		END) as pizza_count_w_exclusions_extras
FROM customer_orders
WHERE order_id in (SELECT order_id FROM cte_order_id_success_delivered)
```

**9. What was the total volume of pizzas ordered for each hour of the day?**

```sql
SELECT 
    HOUR(order_time) AS hour_of_day,
    COUNT(*) AS order_count
FROM 
    customer_orders
GROUP BY 
    hour_of_day
ORDER BY 
    hour_of_day;
```

**10. What was the volume of orders for each day of the week?**

```sql
-- ADD 1 to adjust 1st day of the week as monday
SELECT 
    ELT(DAYOFWEEK(order_time + INTERVAL 1 day),'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday','Sunday') AS date_of_week, 
    COUNT(*) AS order_count
FROM 
    customer_orders
GROUP BY 
    date_of_week
ORDER BY order_count desc 
```

# **B. Runner and Customer Experience**

1. How many runners signed up for each 1 week period? (i.e. week starts `2021-01-01`)

```sql
SELECT 
	WEEKOFYEAR(registration_date + INTERVAL 1 week) AS registraion_week,
    COUNT(*) AS runner_signup
FROM runners
GROUP BY registraion_week
```

1. What was the average time in minutes it took for each runner to arrive at the Pizza Runner HQ to pickup the order?

```sql
SELECT AVG(avg_each_order) AS avg_pickup_minutes
FROM (
	SELECT 
		DISTINCT ro.order_id,
		TIMESTAMPDIFF(MINUTE,order_time,pickup_time) AS avg_each_order
	FROM runner_orders AS ro
	JOIN customer_orders AS co ON ro.order_id = co.order_id
) AS subquery
```

1. Is there any relationship between the number of pizzas and how long the order takes to prepare?

```sql
SELECT pizza_count,
		AVG(avg_pickup_time) AS avg_pickup_time
FROM
	(SELECT 
			ro.order_id,
			COUNT(pizza_id) AS pizza_count,
			AVG(TIMESTAMPDIFF(MINUTE,order_time,pickup_time)) AS avg_pickup_time
	FROM runner_orders AS ro
	JOIN customer_orders AS co ON ro.order_id = co.order_id
	GROUP BY ro.order_id) AS subquery
GROUP BY pizza_count
```

1. What was the average distance travelled for each customer?

```sql

SELECT 
	customer_id,
    AVG(distance) AS avg_distance
FROM runner_orders AS ro
JOIN customer_orders AS co ON ro.order_id = co.order_id
WHERE pickup_time != 'null'
GROUP BY customer_id
```

1. What was the difference between the longest and shortest delivery times for all orders?

```sql

SELECT
	MAX(CAST(duration AS FLOAT)) - MIN(CAST(duration AS FLOAT)) as delivery_time_difference
FROM runner_orders
WHERE pickup_time != 'null'
```

1. What was the average speed for each runner for each delivery and do you notice any trend for these values?

```sql
SELECT 
	runner_id,
    ROUND(CAST(distance AS FLOAT)/CAST(duration AS FLOAT )*60,2) as avg_speed
FROM runner_orders
WHERE pickup_time != 'null'
```

1. What is the successful delivery percentage for each runner?

```sql

SELECT 
	runner_id,
    ROUND(SUM(CASE
			WHEN pickup_time != 'null' THEN 1
			ELSE 0 END
        )/COUNT(*)*100,0) as success_rate
FROM runner_orders
GROUP BY runner_id
```

# **C. Ingredient Optimization**

1. What are the standard ingredients for each pizza?

```sql
SELECT 
	pizza_id,
    topping_name
FROM pizza_recipes
JOIN pizza_toppings
WHERE FIND_IN_SET(topping_id,REPLACE(toppings,' ','')) != 0
ORDER BY pizza_id
```

1. What was the most commonly added extra?

```sql

SELECT 
	topping_id,
    topping_name,
    SUM(CASE 
		WHEN FIND_IN_SET(topping_id,REPLACE(extras,' ','')) != 0 THEN 1
        ELSE 0 END) as topping_count
FROM pizza_toppings
JOIN customer_orders
WHERE (extras not in ('null','') and extras is not NULL)
GROUP BY topping_id,topping_name
HAVING topping_count > 0
```

1. What was the most common exclusion?

```sql
SELECT 
	topping_id,
    topping_name,
    SUM(CASE 
		WHEN FIND_IN_SET(topping_id,REPLACE(exclusions,' ','')) != 0 THEN 1
        ELSE 0 END) as topping_count
FROM pizza_toppings
JOIN customer_orders
WHERE (exclusions not in ('null','') and exclusions is not NULL)
GROUP BY topping_id,topping_name
HAVING topping_count > 0

```

1. Generate an order item for each record in the `customers_orders` table in the format of one of the following:
    - `Meat Lovers`
    - `Meat Lovers - Exclude Beef`
    - `Meat Lovers - Extra Bacon`
    - `Meat Lovers - Exclude Cheese, Bacon - Extra Mushroom, Peppers`
    
    ```sql
    SELECT 
    	CONCAT(pizza_names.pizza_name, 
    		IF(exclusions NOT IN ('null','') AND exclusions IS NOT NULL, 
    			CONCAT(' - Exclude ', 
    				(SELECT GROUP_CONCAT(topping_name separator  ', ')
    				FROM pizza_toppings
    				WHERE FIND_IN_SET(topping_id,REPLACE(exclusions,' ','')) != 0
    				GROUP BY pizza_names.pizza_name
    				)
    			)
            , ''), 	
            IF(extras NOT IN ('null','') AND extras IS NOT NULL, 
    			CONCAT(' - Extra ', 
    				(SELECT GROUP_CONCAT(topping_name separator  ', ')
    				FROM pizza_toppings
    				WHERE FIND_IN_SET(topping_id,REPLACE(extras,' ','')) != 0
    				GROUP BY pizza_names.pizza_name
    				)
                )
            , '')) AS order_item
    FROM customer_orders
    JOIN pizza_names ON customer_orders.pizza_id = pizza_names.pizza_id
    
    ```
    
2. Generate an alphabetically ordered comma separated ingredient list for each pizza order from the `customer_orders` table and add a `2x` in front of any relevant ingredients
    - For example: `"Meat Lovers: 2xBacon, Beef, ... , Salami"`
    
    ```sql
    
    ```
    
3. What is the total quantity of each ingredient used in all delivered pizzas sorted by most frequent first?

```sql

```

# **D. Pricing and Ratings**

1. If a Meat Lovers pizza costs $12 and Vegetarian costs $10 and there were no charges for changes - how much money has Pizza Runner made so far if there are no delivery fees?

```sql
SELECT
	SUM(CASE
		WHEN pizza_name = 'Meatlovers' THEN 12
        ELSE 10 END) as total
FROM customer_orders AS co
JOIN pizza_names AS pn ON co.pizza_id = pn.pizza_id
JOIN runner_orders AS ro ON co.order_id = ro.order_id
WHERE pickup_time != 'null'
```

1. What if there was an additional $1 charge for any pizza extras?
- Add cheese is $1 extra

```sql
SELECT
	SUM(CASE
		WHEN pizza_name = 'Meatlovers' THEN IF(extras NOT IN ('null','') and extras IS NOT NULL ,LENGTH(extras) - LENGTH(REPLACE(extras, ',', '')) + 1 + 12,12)
        ELSE IF(extras NOT IN ('null','') and extras IS NOT NULL ,LENGTH(extras) - LENGTH(REPLACE(extras, ',', '')) + 1 + 10,10) END) as total
FROM customer_orders AS co
JOIN pizza_names AS pn ON co.pizza_id = pn.pizza_id
JOIN runner_orders AS ro ON co.order_id = ro.order_id
WHERE pickup_time != 'null'
```

1. The Pizza Runner team now wants to add an additional ratings system that allows customers to rate their runner, how would you design an additional table for this new dataset generate a schema for this new table and insert your own data for ratings for each successful customer order between 1 to 5.

```sql
CREATE TABLE ratings (
	order_id int,
    rating int,
    UNIQUE(order_id),
    CHECK(rating BETWEEN 1 AND 5)
);

INSERT INTO ratings VALUES 
		(1,1),
    (2,3),
    (3,5),
    (4,4),
    (5,3),
    (7,2),
    (8,5),
    (10,3)
```

1. Using your newly generated table - can you join all of the information together to form a table which has the following information for successful deliveries?
- customer_id
- order_id
- runner_id
- rating
- order_time
- pickup_time
- Time between order and pickup
- Delivery duration
- Average speed
- Total number of pizzas

```sql
SELECT 
	customer_id,
    co.order_id,
    runner_id,
    rating,
    order_time,
    pickup_time,
    TIMESTAMPDIFF(MINUTE, order_time, pickup_time) AS time_between_order_pickup,
    CAST(duration AS FLOAT) as duration_min,
	ROUND(CAST(distance AS FLOAT)/CAST(duration AS FLOAT )*60,2) AS avg_speed,
    COUNT(pizza_id) AS pizza_count
FROM customer_orders AS co
JOIN runner_orders AS ro ON co.order_id = ro.order_id
JOIN ratings AS r ON r.order_id = co.order_id
GROUP BY customer_id,
    co.order_id,
    runner_id,
    rating,
    order_time,
    pickup_time,
    time_between_order_pickup,
    duration,
    avg_speed

```

1. If a Meat Lovers pizza was $12 and Vegetarian $10 fixed prices with no cost for extras and each runner is paid $0.30 per kilometre traveled - how much money does Pizza Runner have left over after these deliveries?

```sql
WITH total_without_delivery AS
(SELECT
	co.order_id,
	SUM(CASE
		WHEN pizza_name = 'Meatlovers' THEN 12
        ELSE 10 END)  as total
FROM customer_orders AS co
JOIN pizza_names AS pn ON co.pizza_id = pn.pizza_id
JOIN runner_orders AS ro ON co.order_id = ro.order_id
WHERE pickup_time != 'null'
GROUP BY co.order_id)
SELECT 
	ROUND(SUM(total - 0.3 * CAST(distance AS FLOAT)) OVER(),2) AS total_with_delivery
FROM runner_orders AS ro
JOIN total_without_delivery AS twd ON ro.order_id = twd.order_id
LIMIT 1

```