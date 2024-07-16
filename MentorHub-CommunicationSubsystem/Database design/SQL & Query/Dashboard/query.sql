SELECT
 w.name,
 COUNT(user_id) as new_member
FROM workspace_member as wm
JOIN workspace AS w on w.id = wm.workspace_id
WHERE DATE(join_at) = (CURRENT_DATE - INTERVAL 1 day) -- yesterday
GROUP BY w.name