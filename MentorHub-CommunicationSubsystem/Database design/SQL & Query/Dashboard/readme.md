# Dashboard

![img](../../Entity/Dashboard%20-%20Entity.png)
![img](../../Database%20design/Dashboard.png)
![img](../../../UX-UI%20design/Version3/Dashboard/Dashboard.png)

**[DBML FILE](dbml.md)**

## Create table and insert data

```sql
INSERT INTO workspace_member (workspace_id, user_id, join_at) VALUES (1, 1, '1999-04-01 11:21:06');
INSERT INTO workspace_member (workspace_id, user_id, join_at) VALUES (1, 2, '2024-01-27 20:09:00');
INSERT INTO workspace_member (workspace_id, user_id, join_at) VALUES (1, 3, '2011-03-02 13:28:10');
INSERT INTO workspace_member (workspace_id, user_id, join_at) VALUES (1, 4, '2024-06-11 21:12:40');
INSERT INTO workspace_member (workspace_id, user_id, join_at) VALUES (1, 5, '2024-03-05 12:32:41');
INSERT INTO workspace_member (workspace_id, user_id, join_at) VALUES (1, 6, '2024-06-24 13:43:28');
INSERT INTO workspace_member (workspace_id, user_id, join_at) VALUES (1, 7, '2024-08-22 20:41:53');
INSERT INTO workspace_member (workspace_id, user_id, join_at) VALUES (1, 8, '2024-04-08 15:13:11');
INSERT INTO workspace_member (workspace_id, user_id, join_at) VALUES (1, 9, '2024-09-13 06:37:44');
INSERT INTO workspace_member (workspace_id, user_id, join_at, left_at) VALUES (1, 10, '1992-01-04 04:20:21', '2024-01-04 04:20:21');

CREATE TABLE channel (
  id INT AUTO_INCREMENT PRIMARY KEY,
  workspace_id INT,
  name VARCHAR(255),
  isPrivate BOOLEAN,
  FOREIGN KEY (workspace_id) REFERENCES workspace(id)
);

INSERT INTO channel (workspace_id, name) 
    VALUES 
        (1,'team-chat'),
        (1,'project-gizmo'),
        (1,'genz-team');
        
CREATE TABLE status_message (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);

INSERT INTO status_message (name)
    VALUES
        ('pending'),
        ('sent'),
        ('read'),
        ('error');
        
CREATE TABLE direct_message (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user1 INT,
  user2 INT,
  
  FOREIGN KEY (user1) REFERENCES user_info(id),
  FOREIGN KEY (user2) REFERENCES user_info(id),
  
);

INSERT INTO direct_message (user1, user2, status_id)
    VALUES
        (1,2,2),
        (1,3,2),
        (2,3,3);

CREATE TABLE message (
  id INT AUTO_INCREMENT PRIMARY KEY,
  sender_id INT,
  content TEXT,
  channel_id INT,
  dms_id INT,
  status_id INT,
  send_at TIMESTAMP,
  FOREIGN KEY (sender_id) REFERENCES user_info(id),
  FOREIGN KEY (channel_id) REFERENCES channel(id),
  FOREIGN KEY (dms_id) REFERENCES direct_message(id),
  FOREIGN KEY (status_id) REFERENCES status_message(id)
);

INSERT INTO message (sender_id, content, channel_id, dms_id) VALUES (1, 'laboriosam asperiores dignissimos', NULL ,1);
INSERT INTO message (sender_id, content, channel_id, dms_id) VALUES (1, 'ex nobis id', NULL, 1);
INSERT INTO message (sender_id, content, channel_id, dms_id) VALUES (2, 'hic facilis magnam', NULL, 1);
INSERT INTO message (sender_id, content, channel_id, dms_id) VALUES (2, 'illo rem accusamus', NULL, 1);
INSERT INTO message (sender_id, content, channel_id, dms_id) VALUES (1, 'aliquam repudiandae quo', NULL, 1);
INSERT INTO message (sender_id, content, channel_id, dms_id) VALUES (2, 'nihil exercitationem ex', NULL, 1);
INSERT INTO message (sender_id, content, channel_id, dms_id) VALUES (3, 'eum dicta iste', NULL, 1);
INSERT INTO message (sender_id, content, channel_id, dms_id) VALUES (1, 'voluptates consequatur assumenda', NULL, 1);
INSERT INTO message (sender_id, content, channel_id, dms_id) VALUES (2, 'delectus iure asperiores', 1, NULL);
INSERT INTO message (sender_id, content, channel_id, dms_id) VALUES (3, 'corrupti eveniet quia', 1, NULL);

CREATE TABLE mention_list (
  message_id INT,
  user_id INT,
  FOREIGN KEY (message_id) REFERENCES message(id),
  FOREIGN KEY (user_id) REFERENCES user_info(id)
);

INSERT INTO mention_list (message_id, user_id)
    VALUES 
        (9,4),
        (9,5);
        
CREATE TABLE notification_type (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);

INSERT INTO notification_type (name)
    VALUES 
        ('Welcome'),
        ('Meeting'),
        ('Mention');
        
CREATE TABLE notification_queue (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  notification_type_id INT,
  content TEXT,
  isRead BOOLEAN,
  FOREIGN KEY (user_id) REFERENCES user_info(id),
  FOREIGN KEY (notification_type_id) REFERENCES notification_type(id)
);

INSERT INTO notification_queue (user_id, notification_type_id, content)
    VALUES 
        (1,1,'Welcome to MentorHub'),
        (1,2,'Peter invite you join meeting'),
        (1,3,'Anna mention you in #team-chat');
        
CREATE TABLE meeting (
  id INT AUTO_INCREMENT PRIMARY KEY,
  owner_id INT,
  name VARCHAR(255),
  start_at TIMESTAMP,
  end_at TIMESTAMP,
  FOREIGN KEY (owner_id) REFERENCES user_info(id)
);

INSERT INTO meeting (owner_id, name, start_at, end_at)
    VALUES
        (2,'Welcome to my program','2024-06-13 09:09:00','2024-06-13 09:10:00'),
        (3,'Training','2024-06-13 09:11:00','2024-06-13 09:12:00');

CREATE TABLE meeting_status (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);

INSERT INTO meeting_status (name)
    VALUES
        ('accepted'),
        ('declined');

CREATE TABLE meeting_participant (
  meeting_id INT,
  user_id INT,
  status_id INT,
  FOREIGN KEY (meeting_id) REFERENCES meeting(id),
  FOREIGN KEY (user_id) REFERENCES user_info(id),
  FOREIGN KEY (status_id) REFERENCES meeting_status(id)
);

INSERT INTO meeting_participant (meeting_id, user_id, status_id)
    VALUES
        (1,1,1),
        (1,2,1),
        (1,3,1),                
        (2,1,2),
        (2,2,1),
        (2,3,1);

```


## Query

```sql
-- Query
-- New member
SELECT
 w.name,
 COUNT(user_id) as new_member
FROM workspace_member as wm
JOIN workspace AS w on w.id = wm.workspace_id
WHERE DATE(join_at) = CURRENT_DATE -- current
GROUP BY w.name;

SELECT
 w.name,
 COUNT(user_id) as new_member
FROM workspace_member as wm
JOIN workspace AS w on w.id = wm.workspace_id
WHERE DATE(join_at) = (CURRENT_DATE - INTERVAL 1 day) -- yesterday
GROUP BY w.name


-- New message
-- Curent
WITH id_dms AS
(SELECT 
 id
FROM direct_message
WHERE user1 = 1 OR user2 = 1)
SELECT
 COUNT(*) AS new_message
FROM 
 message
WHERE 
 dms_id IN (SELECT id FROM id_dms) AND
    sender_id != 1 AND
    status_id = 2
-- Yesterday
WITH id_dms AS
(SELECT 
 id
FROM direct_message
WHERE user1 = 1 OR user2 = 1)
SELECT
 COUNT(*) AS new_message
FROM 
 message
WHERE 
 dms_id IN (SELECT id FROM id_dms) AND
    sender_id != 1 AND
    status_id = 2 AND
    DATE(send_at) = CURRENT_DATE() - INTERVAL 1 DAY
   
-- Up comming event
WITH get_meeting_id AS
(SELECT *
FROM meeting_participant
WHERE user_id =1 and status_id = 1)
SELECT *
FROM meeting
WHERE 
 id IN (SELECT meeting_id FROM get_meeting_id) AND
    start_at > CURRENT_TIMESTAMP() AND DATE(start_at) = CURRENT_DATE();
    
-- Member left
SELECT 
 COUNT(*) AS member_left_count
FROM
 workspace_member
WHERE DATE(left_at) = CURRENT_DATE()



-- Notification
SELECT 
 *
FROM
 notification_queue
WHERE 
 user_id = 1 AND
    isRead = 0
```