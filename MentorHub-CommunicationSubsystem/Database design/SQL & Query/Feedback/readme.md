# Feedback

![img](../../Entity/Feedback%20-%20Entity.png)
![img](../../Detail/Feedback.png)
![img](../../../UX-UI%20design/Version3/Ticket/Ticket%20List.png)

**[DBML FILE](dbml.md)**

## Create table and insert data

```sql
CREATE TABLE `feedback_group` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(255)
);

CREATE TABLE `feedback_status` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(255)
);

CREATE TABLE `feedback` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `group_id` int DEFAULT null,
  `status_id` int,
  `content` text,
  `send_at` timestamp,
  `sender_email` varchar(100)
);

CREATE TABLE `feedback_assignee` (
  `feedback_id` int,
  `assignee_id` int,
  `assign_at` timestamp,
  `message` text
);

CREATE TABLE `feedback_result` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `feedback_id` int,
  `content` text,
  `send_at` timestamp
);

CREATE TABLE `feedback_result_reply` (
  `feedback_result_id` int,
  `replied_by` int,
  `replied_at` timestamp,
  `content` text
);



ALTER TABLE `feedback` ADD FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`group_id`) REFERENCES `feedback_group` (`id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`status_id`) REFERENCES `feedback_status` (`id`);

ALTER TABLE `feedback_assignee` ADD FOREIGN KEY (`feedback_id`) REFERENCES `feedback` (`id`);

ALTER TABLE `feedback_assignee` ADD FOREIGN KEY (`assignee_id`) REFERENCES `user_info` (`id`);

ALTER TABLE `feedback_result` ADD FOREIGN KEY (`feedback_id`) REFERENCES `feedback` (`id`);

ALTER TABLE `feedback_result_reply` ADD FOREIGN KEY (`feedback_result_id`) REFERENCES `feedback_result` (`id`);

ALTER TABLE `feedback_result_reply` ADD FOREIGN KEY (`replied_by`) REFERENCES `user_info` (`id`);


INSERT INTO feedback_group(id,name) VALUES
  (1,'Support'),
  (2,'Report Bug')

INSERT INTO feedback_status(id, name) VALUES
  (1,'Open'),
  (1,'New'),
  (1,'Solved')

INSERT INTO feedback(id,user_id,group_id,status_id,content,send_at,sender_email) VALUES 
  (1,2,1,1,'Can cai thien UI', '12/06/2024 4:29:23','anna@gmail.com'),
  (2,3,1,2,'UI qua xau', '12/06/2024 4:29:23','qtsc@gmail.com')

INSERT INTO feedback_attachment(feedback_id,access_id) VALUES
  (1,1),
  (1,2)

INSERT INTO feedback_assignee(feedback_id,assignee_id,assign_at,message) VALUES
  (1,5,'12/06/2024 4:29:23','Co len nhe'),
  (2,6,'12/06/2024 4:29:23','Can sua gap'),


```

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
