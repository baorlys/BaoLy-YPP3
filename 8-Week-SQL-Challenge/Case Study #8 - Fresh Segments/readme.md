# Case Study #8 - Fresh Segments

![img](https://8weeksqlchallenge.com/images/case-study-designs/8.png)

**4 SECTIONS**

[A. Data Exploration and Cleansing](#a-data-exploration-and-cleansing)

[B. Interest Analysis](#b-interest-analysis)

[C. Segment Analysis](#c-segment-analysis)

[D. Index Analysis](#d-index-analysis)

---


## **A. Data Exploration and Cleansing**

**1 - Update the `fresh_segments.interest_metrics` table by modifying the `month_year` column to be a `date` data type with the start of the month**

```sql
ALTER TABLE interest_metrics
MODIFY month_year VARCHAR(10);

UPDATE interest_metrics
SET month_year =  CONCAT('01-',month_year) ;

UPDATE interest_metrics
SET month_year =  STR_TO_DATE(month_year,'%d-%m-%Y');

ALTER TABLE interest_metrics
MODIFY month_year DATE
```

**2 - What is count of records in the `fresh_segments.interest_metrics` for each `month_year` value sorted in chronological order (earliest to latest) with the `null` values appearing first?**

```sql
SELECT
 month_year,
 COUNT(*)
FROM interest_metrics
GROUP BY month_year
ORDER BY month_year IS NOT NULL, month_year ASC
```

**3 - What do you think we should do with these `null` values in the `fresh_segments.interest_metrics`?**

```sql
DELETE FROM interest_metrics
WHERE interest_id IS NULL
```

**4 - How many `interest_id` values exist in the `fresh_segments.interest_metrics` table but not in the `fresh_segments.interest_map` table? What about the other way around?**

```sql
SELECT
 COUNT(DISTINCT interest_id) AS metric_count,
 COUNT(DISTINCT id) AS map_count,
    COUNT(DISTINCT id) - COUNT(DISTINCT interest_id) AS not_in_metric
FROM interest_metrics AS metric
RIGHT JOIN interest_map AS map ON metric.interest_id = map.id
UNION
SELECT
 COUNT(DISTINCT interest_id) AS metric_count,
 COUNT(DISTINCT id) AS map_count,
    COUNT(DISTINCT interest_id) - COUNT(DISTINCT id) AS not_in_metric
FROM interest_metrics AS metric
LEFT JOIN interest_map AS map ON metric.interest_id = map.id
```

**5 - Summarise the id values in the `fresh_segments.interest_map` by its total record count in this table.**

```sql
SELECT
 COUNT(DISTINCT id) AS map_count
FROM interest_map
```

**6 - What sort of table join should we perform for our analysis and why? Check your logic by checking the rows where 'interest_id = 21246' in your joined output and include all columns from `fresh_segments.interest_metrics` and all columns from `fresh_segments.interest_map` except from the id column.**

```sql
SELECT
 _month,
    _year,
    month_year,
    interest_id,
    composition,
    index_value,
    ranking,
    percentile_ranking,
    interest_name,
    interest_summary,
    created_at,
    last_modified
FROM interest_metrics AS metric
JOIN interest_map AS map ON metric.interest_id = map.id
WHERE interest_id = 21246 AND month_year IS NOT NULL
```

**7 - Are there any records in your joined table where the `month_year` value is before the `created_at` value from the `fresh_segments.interest_map` table? Do you think these values are valid and why?**

```sql
SELECT
 COUNT(*)
FROM interest_metrics AS metric
JOIN interest_map AS map ON metric.interest_id = map.id
WHERE  month_year < created_at
-- 188 records

```

## **B. Interest Analysis**

**1 - Which interests have been present in all `month_year` dates in our dataset?**

```sql
SELECT
 COUNT(DISTINCT month_year) AS unique_month_count
FROM interest_metrics;
-- 14 unique months
SELECT
 interest_id,
    COUNT(*) AS frequency
FROM interest_metrics
GROUP BY interest_id
HAVING frequency = 14
```

**2 - Using this same total_months measure - calculate the cumulative percentage of all records starting at 14 months - which total_months value passes the 90% cumulative percentage value?**

```sql
WITH count_frequency AS
(SELECT
 interest_id,
    COUNT(month_year) AS month_frequency
FROM interest_metrics
GROUP BY interest_id),
count_interest AS
(SELECT
 month_frequency,
    COUNT(*) AS interest_count
FROM count_frequency
GROUP BY month_frequency
ORDER BY month_frequency),
cal_cumulative AS
(SELECT
 *,
    ROUND(SUM(interest_count) OVER(ORDER BY month_frequency DESC) / SUM(interest_count) OVER() * 100 , 2) AS cumulative
FROM count_interest
GROUP BY month_frequency,interest_count)
SELECT
 *
FROM cal_cumulative
WHERE cumulative > 90

```

**3 - If we were to remove all `interest_id` values which are lower than the `total_months` value we found in the previous question - how many total data points would we be removing?**

```sql
WITH count_frequency AS
(SELECT
 interest_id,
    COUNT(month_year) AS month_frequency
FROM interest_metrics
GROUP BY interest_id
HAVING month_frequency <= 6 )
SELECT
 COUNT(*) AS record_count
FROM interest_metrics
WHERE interest_id IN (SELECT interest_id FROM count_frequency);
```

**4 - Does this decision make sense to remove these data points from a business perspective? Use an example where there are all 14 months present to a removed interest example for your arguments - think about what it means to have less months present from a segment perspective.**

```sql
WITH count_frequency AS
(SELECT
 interest_id,
    COUNT(month_year) AS month_frequency
FROM interest_metrics
GROUP BY interest_id
HAVING month_frequency <= 6),
not_interests AS (
    SELECT 
        COUNT(*) AS remove_interest, 
        month_year 
    FROM 
        interest_metrics 
    WHERE 
        interest_id IN (SELECT interest_id FROM count_frequency) 
    GROUP BY 
        month_year
),
current_interests AS (
    SELECT 
        COUNT(*) AS current_interest, 
        month_year 
    FROM 
        interest_metrics 
    WHERE 
        interest_id NOT IN (SELECT interest_id FROM count_frequency) 
    GROUP BY 
        month_year
)
SELECT 
    remove_interest, 
    current_interest, 
    not_interests.month_year, 
    100 * remove_interest / current_interest AS remove_pct 
FROM 
    not_interests 
JOIN 
    current_interests 
ON 
    not_interests.month_year = current_interests.month_year;

```

**5 - If we include all of our interests regardless of their counts - how many unique interests are there for each month?**

```sql
WITH count_frequency AS
(SELECT
 interest_id,
    COUNT(month_year) AS month_frequency
FROM interest_metrics
GROUP BY interest_id
HAVING month_frequency <= 6)
SELECT
 COUNT(DISTINCT interest_id) AS current_interest, 
    month_year 
FROM interest_metrics 
WHERE month_year IS NOT NULL AND interest_id NOT IN (SELECT interest_id FROM count_frequency) 
GROUP BY month_year

```

## **C. Segment Analysis**

```sql
CREATE VIEW filtered_data AS
WITH count_frequency AS
(SELECT
 interest_id,
    COUNT(month_year) AS month_frequency
FROM interest_metrics
GROUP BY interest_id
HAVING month_frequency >= 6 )
SELECT
 *
FROM interest_metrics
WHERE interest_id IN (SELECT interest_id FROM count_frequency)
```

**1 - Using the complete dataset - which are the top 10 and bottom 10 interests which have the largest composition values in any month_year? Only use the maximum composition value for each interest but you must keep the corresponding month_year**

```sql
-- Bottom 10
SELECT
    month_year, interest_id, MAX(composition) AS max_composition
FROM
    filtered_data
GROUP BY interest_id , month_year
ORDER BY max_composition ASC,month_year
LIMIT 10;

-- Top 10
SELECT
    month_year, interest_id, MAX(composition) AS max_composition
FROM
    filtered_data
GROUP BY interest_id , month_year
ORDER BY max_composition DESC,month_year
LIMIT 10;

```

**2 - Which 5 interests had the lowest average ranking value?**

```sql
SELECT
    DISTINCT interest_id,
    AVG(ranking) OVER(PARTITION BY interest_id) AS avg_ranking
FROM
    filtered_data
ORDER BY avg_ranking
LIMIT 5

```

**3 - Which 5 interests had the largest standard deviation in their percentile_ranking value?**

```sql
SELECT
    interest_id,
 STDDEV(percentile_ranking) AS standard_deviation
FROM
    filtered_data
GROUP BY interest_id
ORDER BY standard_deviation DESC
LIMIT 5
```

**4 - For the 5 interests found in the previous question - what was minimum and maximum percentile_ranking values for each interest and its corresponding year_month value? Can you describe what is happening for these 5 interests?**

```sql
WITH top_5_stddev AS
(SELECT
    interest_id,
 STDDEV(percentile_ranking) AS standard_deviation
FROM
    filtered_data
GROUP BY interest_id
ORDER BY standard_deviation DESC
LIMIT 5)
SELECT
 interest_id,
    MIN(percentile_ranking) AS minimum_pr,
    MAX(percentile_ranking) AS maximum_pr
FROM filtered_data
WHERE interest_id IN (SELECT interest_id FROM top_5_stddev)
GROUP BY interest_id

```

**5 - How would you describe our customers in this segment based off their composition and ranking values? What sort of products or services should we show to these customers and what should we avoid?**

```sql
SELECT
 interest_id,
    interest_name,
    interest_summary,
    AVG(composition) AS avg_composition,
    AVG(ranking) AS avg_ranking
FROM
 filtered_data AS filtered_metric
JOIN interest_map AS map ON filtered_metric.interest_id = map.id
GROUP BY 
    interest_id,
 interest_name,
 interest_summary
ORDER BY 
    avg_composition DESC, 
    avg_ranking ASC
LIMIT 10
```

## **D. Index Analysis**

```sql
CREATE VIEW metric_with_avg_composition AS
SELECT
 *,
    ROUND(composition/index_value,2) AS avg_composition
FROM interest_metrics
```

The `index_value` is a measure which can be used to reverse calculate the average composition for Fresh Segments’ clients. Average composition can be calculated by dividing the composition column by the index_value column rounded to 2 decimal places.

**1 - What is the top 10 interests by the average composition for each month?**

```sql
SELECT
 month_year,
    interest_id,
    avg_composition,
    RANK() OVER(PARTITION BY month_year ORDER BY avg_composition DESC) AS ranking
FROM metric_with_avg_composition
WHERE ranking <= 10
ORDER BY month_year
```

**2 - For all of these top 10 interests - which interest appears the most often?**

```sql
WITH cte AS
(SELECT
 month_year,
    interest_id,
    RANK() OVER(PARTITION BY month_year ORDER BY avg_composition DESC) AS ranking
FROM metric_with_avg_composition
WHERE ranking <= 10
ORDER BY month_year)
SELECT
 interest_id,
    COUNT(*) AS frequency
FROM cte
GROUP BY interest_id
ORDER BY frequency DESC
LIMIT 10
```

**3 - What is the average of the average composition for the top 10 interests for each month?**

```sql
WITH cte AS
(SELECT
 month_year,
    interest_id,
    avg_composition,
    RANK() OVER(PARTITION BY month_year ORDER BY avg_composition DESC) AS ranking
FROM metric_with_avg_composition
WHERE ranking <= 10
ORDER BY month_year)
SELECT
 month_year,
    ROUND(AVG(avg_composition),2) AS avg_composition_each_month
FROM cte
GROUP BY month_year;

```

**4 - What is the 3 month rolling average of the max average composition value from September 2018 to August 2019 and include the previous top ranking interests in the same output shown below.**

```sql
WITH get_max AS
(SELECT 
 month_year,
 MAX(avg_composition) AS max_avg_composition
FROM metric_with_avg_composition
WHERE month_year IS NOT NULL
GROUP BY month_year),
get_3_month_rolling_avg AS
(SELECT 
 mwac.month_year,
 interest_id,
    avg_composition,
    ROUND(AVG(avg_composition) OVER(ORDER BY month_year 
         ROWS BETWEEN 2 PRECEDING AND CURRENT ROW),2) AS 3_month_rolling_avg
FROM metric_with_avg_composition mwac
JOIN get_max gm ON mwac.month_year = gm.month_year
WHERE avg_composition = max_avg_composition)
SELECT
 *
FROM get_3_month_rolling_avg
WHERE month_year BETWEEN '2018-09-01' AND '2019-08-01'

```

---

[**Case Study #1 - Danny's Diner**](../Case%20Study%20%231%20-%20Danny's%20Diner)
