# Feedback

![img](../../Entity/Feedback%20-%20Entity.png)
![img](../../Detail/Feedback.png)
![img](../../../UX-UI%20design/Version3/Ticket/Ticket%20List.png)

## Query

```sql
-- Get feedback is unsolved
SELECT *
FROM feedback f
JOIN feedback_assignee fa ON f.id = fa.feedback_id
JOIN user_info ui ON ui.id = fa.assignee_id 
JOIN feedback_group fg ON fg.id = f.group_id
WHERE status_id != 3


-- Get result feedback
SELECT *
FROM feedback_result fr
JOIN feedback f ON f.id = fr.feedback_id
JOIN feedback_assignee fa ON f.id = fa.feedback_id
WHERE feedback_id = 1

-- Get result feedback replies
SELECT *
FROM feedback_result_reply
WHERE feedback_result_id = 1
```
