# Dashboard

![img](../../Entity/Dashboard%20-%20Entity.png)
![img](../../Detail/Dashboard.png)
![img](../../../UX-UI%20design/Version3/Dashboard/Dashboard.png)

## Query

```sql
-- Query
-- 1. New member in workspace
DROP PROCEDURE IF EXISTS proc_new_member_in_workspace;
GO
CREATE PROCEDURE proc_new_member_in_workspace
    @workspace_id INT
AS
BEGIN
    SELECT
        w.name,
        COUNT(user_id) as new_member
    FROM WorkspaceMember as wm
        JOIN Workspace AS w on w.id = wm.workspace_id
    WHERE CAST(wm.join_at as DATE) = CAST(GETDATE() AS DATE) -- current
        AND w.id = @workspace_id
    GROUP BY w.name
END

GO
EXEC proc_new_member_in_workspace @workspace_id = 1;


-- New message
DROP PROCEDURE IF EXISTS proc_new_message ;
GO
CREATE PROCEDURE proc_new_message
    @user_id INT
AS
BEGIN
    WITH
        id_dms
        AS
        (
            SELECT
                id
            FROM DirectMessage
            WHERE user1 = @user_id OR user2 = @user_id
        )
    SELECT
        COUNT(*) AS new_message
    FROM
        message
    WHERE 
  dms_id IN (SELECT id
        FROM id_dms) AND
        sender_id != @user_id AND
        status_id = 2
END

GO
EXEC proc_new_message @user_id = 2;
  
-- 3. Up coming event
DROP PROCEDURE IF EXISTS proc_upcoming_event ;
GO
CREATE PROCEDURE proc_upcoming_event
    @user_id INT
AS
BEGIN
    WITH
        get_meeting_id
        AS
        (
            SELECT *
            FROM MeetingParticipant
            WHERE user_id = @user_id and status_id = 1
        )
    SELECT
        COUNT(id) AS upcoming_event
    FROM Meeting
    WHERE 
        id IN (SELECT meeting_id
        FROM get_meeting_id) AND
        CAST(start_at AS DATE) = CAST(GETDATE() AS DATE)
END

GO
EXEC proc_upcoming_event @user_id = 1;
    
-- 4. Member left
DROP PROCEDURE IF EXISTS proc_member_left_count ;
GO
CREATE PROCEDURE proc_member_left_count
    @workspace_id INT
AS
BEGIN
    SELECT
        COUNT(*) AS member_left_count
    FROM
        WorkspaceMember
    WHERE
        CAST(left_at AS DATE) = CAST(GETDATE() AS DATE)
        AND workspace_id = @workspace_id
END

GO
EXEC proc_member_left_count @workspace_id = 1;


-- 5. Member active
DROP PROCEDURE IF EXISTS proc_member_active_count;
GO
CREATE PROCEDURE proc_member_active_count
    @workspace_id INT,
    @is_previous TINYINT
AS
BEGIN
    DECLARE @day INT;

    IF @is_previous = 0
        SET @day = -6;
    ELSE
        SET @day = -12;

    WITH days_7 AS (
        SELECT
            CASE
                WHEN @is_previous = 1 THEN DATEADD(DAY, -6, GETDATE())
                ELSE GETDATE()
            END AS day
        UNION ALL
        SELECT DATEADD(DAY, -1, day)
        FROM days_7
        WHERE day > DATEADD(DAY, @day, GETDATE())
    )
    SELECT
        day,
        COUNT(l.id) AS active_count
    FROM
        days_7
        LEFT JOIN [Log] l ON CAST(l.log_time AS DATE) = CAST(days_7.day AS DATE)
        LEFT JOIN [EventType] et ON et.id = l.event_type_id AND et.name = 'User Online'
        LEFT JOIN [EventParameter] ep ON ep.event_type_id = et.id AND ep.name = 'workspace_id'
        LEFT JOIN LogDetail ld ON ld.log_id = l.id AND ld.event_parameter_id = ep.id AND ld.value = @workspace_id
    GROUP BY
        days_7.day
    ORDER BY
        days_7.day DESC;
END
GO

-- Execute the procedure to test
EXEC proc_member_active_count @workspace_id = 1, @is_previous = 0;


-- 6. Total member
DROP PROCEDURE IF EXISTS proc_member_in_workspace;
GO
CREATE PROCEDURE proc_member_in_workspace
    @workspace_id INT
AS
BEGIN
    SELECT
        COUNT(user_id) as new_member
    FROM WorkspaceMember as wm
    WHERE wm.workspace_id = @workspace_id
    GROUP BY wm.workspace_id
END

GO
EXEC proc_member_in_workspace @workspace_id = 1;

DROP TABLE IF EXISTS TotalMember;
CREATE TABLE TotalMember
(
    total_member INT
);
INSERT INTO TotalMember
    (total_member)
EXEC proc_member_in_workspace @workspace_id = 1;

SELECT *
FROM TotalMember


DROP TABLE IF EXISTS MemberActiveCount;
CREATE TABLE MemberActiveCount
(
    day DATE,
    active_count INT
);
INSERT INTO MemberActiveCount
    (day, active_count)
EXEC proc_member_active_count @workspace_id = 1, @is_previous = 0;

SELECT *
FROM MemberActiveCount
WHERE CAST(day AS DATE) = CAST(GETDATE() AS DATE)


SELECT
    *
FROM TotalMember
    JOIN MemberActiveCount ON 1 = 1
WHERE CAST(day AS DATE) = CAST(GETDATE() AS DATE)

DROP TABLE TotalMember;
DROP TABLE MemberActiveCount;
  

-- 6. Notification
DROP PROCEDURE IF EXISTS proc_get_notification;
GO
CREATE PROCEDURE proc_get_notification
    @notification_type_id INT,
    @user_id INT
AS
BEGIN
    SELECT *
    FROM NotificationQueue
    WHERE 
        user_id = @user_id
        AND is_read = 0
        AND notification_type_id = @notification_type_id
END

GO
EXEC proc_get_notification @notification_type_id = 4, @user_id = 1

-- 7. Avg time online
DROP PROCEDURE IF EXISTS proc_member_active_time;
GO
CREATE PROCEDURE proc_member_active_time
    @workspace_id INT,
    @is_previous TINYINT
AS
BEGIN
    DECLARE @day INT;

    IF @is_previous = 0
        SET @day = -6;
    ELSE
        SET @day = -12;

    WITH
        days_7
        AS
        (
                            SELECT
                    CASE
                WHEN @is_previous = 1 THEN DATEADD(DAY, -6, GETDATE())
                ELSE GETDATE()
            END AS day
            UNION ALL
                SELECT DATEADD(DAY, -1, day)
                FROM days_7
                WHERE day > DATEADD(DAY, @day, GETDATE())
        ),
        get_workspace
        AS
        (
            SELECT
                ld.event_parameter_id,
                ld.value,
                l.log_time
            FROM
                [Log] l
                LEFT JOIN [EventType] et ON et.id = l.event_type_id AND et.name = 'User Online'
                LEFT JOIN [EventParameter] ep ON ep.event_type_id = et.id
                LEFT JOIN LogDetail ld ON ld.log_id = l.id AND ld.event_parameter_id = ep.id
        ),
        get_time
        AS
        (
            SELECT

                [value] as time_online,
                log_time
            FROM
                get_workspace
            WHERE event_parameter_id = 2
            GROUP BY 
            event_parameter_id, value, log_time
        )
    SELECT
        day,
        SUM(CONVERT(FLOAT,time_online))/COUNT(*) AS avg_time_online
    FROM
        days_7
        LEFT JOIN get_time ON CAST(log_time AS DATE) = CAST(day AS DATE)
    GROUP BY 
            day
    ORDER BY 
            day DESC

END
GO

-- Execute the procedure to test
EXEC proc_member_active_time @workspace_id = 1, @is_previous = 0;

```
