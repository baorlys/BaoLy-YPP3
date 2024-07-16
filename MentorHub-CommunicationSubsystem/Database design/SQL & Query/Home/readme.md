# Home

![img](../../Entity/Home%20-%20Entity.png)
![img](../../Detail/Home.png)
![img](../../../UX-UI%20design/Version3/Workspace/Workspace%20-%20Thread.png)

## Query

```sql
-- 1/ Get channels in workspace
SELECT 
 *
FROM channel
WHERE workspace_id = 1
 
-- 2/ Get member in private channel
SELECT
 *
FROM channel_private_member
WHERE channel_id = 1

-- 3/ Get message in channel
SELECT 
 *
FROM message
WHERE channel_id = 1

-- Get thread
SELECT
 *
FROM message
WHERE parent_id = 1

-- Get poll
SELECT
 *
FROM poll_question
WHERE channel_id = 1

SELECT 
 *
FROM poll_answer
WHERE question_id = 1

SELECT
 *
FROM poll_voting_history
WHERE question_id = 1


--  Get attachment
SELECT
 *
FROM message_attachment
WHERE message_id IN (SELECT message_id FROM message WHERE channel_id = 1)

-- Get reaction of message
SELECT
 *
FROM message_reaction mr
JOIN emoji e ON mr.emoji_id = e.id
WHERE message_id = 1 

SELECT
 COUNT(*)
FROM message_reaction mr
JOIN emoji e ON mr.emoji_id = e.id
WHERE message_id = 1 
GROUP BY message_id

-- Get log activity
SELECT
 *
FROM log l
JOIN event_type et ON l.event_type_id = et.id 
JOIN event_parameter ep ON et.id = ep.event_type_id
JOIN log_detail ld ON l.id = ld.log_id AND ld.event_parameter_id = ep.id
WHERE user_id = 1

-- Count replies
SELECT 
    COUNT(*) as total_replies
FROM [Message]
WHERE parent_id = 1

-- Get all
SELECT
    *
FROM [Message] m
    LEFT JOIN PollQuestion pq ON pq.channel_id = m.channel_id
    LEFT JOIN PollAnswer pa ON pa.question_id = pq.id AND pa.created_by = m.sender_id
    LEFT JOIN MessageReaction mr ON mr.message_id = m.id
    LEFT JOIN MessageAttachment ma ON ma.message_id = m.id
WHERE m.channel_id = 1

-- Get dms
DROP PROCEDURE IF EXISTS get_dms_by_user_id ;
GO
CREATE PROCEDURE get_dms_by_user_id
    @user_id INT
AS
BEGIN
    SELECT
        DISTINCT dm.id AS dms_id,
        user1,
        user2
    FROM [Message] m
        JOIN DirectMessage dm ON m.dms_id = dm.id AND dm.user1 = @user_id OR dm.user2 = @user_id
    WHERE sender_id != @user_id;
END

GO
EXEC get_dms_by_user_id @user_id = 2;

```
