# SQL & Query

- Should review from [Dashboard](Dashboard) -> [Home](Home) -> [Direct Message](DirectMessage) -> [Meeting](Meeting) -> [Feedback](Feedback)

- View [Sample Data](https://docs.google.com/spreadsheets/d/1iZMjM3puy4DVT-Nmn2sfRYUixvbTIX2Fhw903t7ScH4/edit?gid=898446453#gid=898446453) 

- MySQL: [Create Schema](#create-schema) with [References](#add-references) and [Insert Data](#insert-data)

- DBML: [Table design](#dbml)

## Create Schema

```sql
CREATE SCHEMA mentorhub_communication;
USE mentorhub_communication;
CREATE TABLE `user` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `email` varchar(255),
  `full_name` varchar(255),
  `dob` date DEFAULT null,
  `last_login_at` timestamp,
  `is_active` boolean,
  `created_at` timestamp
);

CREATE TABLE `notification_type` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `status_message` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `workspace` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255),
  `owner_id` int,
  `source_id` int,
  `source_type_id` int
);

CREATE TABLE `event_type` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `meeting_participant_status` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `channel` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `workspace_id` int,
  `name` varchar(255),
  `description` varchar(500),
  `is_private` tinyint(1),
  `created_at` timestamp
);

CREATE TABLE `role` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `language` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `main_feature` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `emoji` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `unicode` varchar(255),
  `name` varchar(255)
);

CREATE TABLE `feedback_group` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `feedback_status` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `meeting` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `owner_id` int,
  `name` varchar(255),
  `description` varchar(500),
  `start_at` timestamp,
  `end_at` timestamp,
  `created_at` timestamp
);

CREATE TABLE `direct_message` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `user1` int,
  `user2` int,
  `created_at` timestamp
);

CREATE TABLE `meeting_participant` (
  `meeting_id` int,
  `user_id` int,
  `status_id` int,
  `joined_at` timestamp DEFAULT null,
  `left_at` timestamp DEFAULT null
);

CREATE TABLE `notification_queue` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `user_id` int,
  `notification_type_id` int,
  `content` text,
  `is_read` tinyint(1)
);

CREATE TABLE `permission` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `role_id` int,
  `name` varchar(255),
  `value` int
);

CREATE TABLE `workspace_member` (
  `workspace_id` int,
  `user_id` int,
  `join_at` timestamp,
  `updated_at` timestamp,
  `left_at` timestamp DEFAULT null,
  `role_id` int DEFAULT null
);

CREATE TABLE `channel_shared` (
  `channel_id` int,
  `original_workspace_id` int,
  `target_workspace_id` int
);

CREATE TABLE `poll_question` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `question` varchar(255),
  `channel_id` int,
  `created_by` int,
  `created_at` timestamp,
  `expires_at` timestamp
);

CREATE TABLE `poll_answer` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `question_id` int,
  `answer` varchar(255),
  `created_by` int,
  `created_at` timestamp
);

CREATE TABLE `poll_voting_history` (
  `question_id` int,
  `answer_id` int,
  `voted_by` int,
  `voted_at` timestamp
);

CREATE TABLE `log` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `user_id` int,
  `event_type_id` int,
  `logtime` timestamp
);

CREATE TABLE `event_parameter` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255),
  `data_type` varchar(255),
  `event_type_id` int
);

CREATE TABLE `log_detail` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `log_id` int,
  `event_parameter_id` int,
  `value` varchar(255)
);

CREATE TABLE `channel_private_member` (
  `channel_id` int,
  `user_id` int,
  `role_id` int DEFAULT null
);

CREATE TABLE `message` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `sender_id` int,
  `content` text,
  `channel_id` int DEFAULT null,
  `dms_id` int DEFAULT null,
  `parent_id` int DEFAULT null,
  `meeting_id` int DEFAULT null,
  `status_id` int DEFAULT null,
  `send_at` timestamp,
  `edited_at` timestamp DEFAULT null,
  `is_deleted` tinyint(1) DEFAULT 0,
  `deleted_at` timestamp DEFAULT null
);

CREATE TABLE `message_mention` (
  `message_id` int,
  `user_id` int
);

CREATE TABLE `message_attachment` (
  `message_id` int,
  `access_id` int
);

CREATE TABLE `message_reaction` (
  `message_id` int,
  `emoji_id` int,
  `user_id` int
);

CREATE TABLE `block_list` (
  `user_id` int,
  `user_id_is_blocked` int,
  `dms_id` int
);

CREATE TABLE `feedback` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `user_id` int,
  `sender_email` varchar(100),
  `group_id` int DEFAULT null,
  `status_id` int,
  `content` text,
  `send_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `feedback_assignee` (
  `feedback_id` int,
  `assignee_id` int,
  `assign_at` timestamp,
  `message` text
);

CREATE TABLE `feedback_result` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
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

CREATE TABLE `navigation_bar` (
  `user_id` int,
  `feature_nav_bar` int
);

CREATE TABLE `notification_setting` (
  `preference_id` int,
  `notification_type_id` int,
  `enable` tinyint(1)
);

CREATE TABLE `preference` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `user_id` int,
  `dark_mode` tinyint(1) DEFAULT 0,
  `language_id` int
);

```

## Add References

```sql
ALTER TABLE `workspace` ADD FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);

ALTER TABLE `channel` ADD FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`);

ALTER TABLE `meeting` ADD FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);

ALTER TABLE `direct_message` ADD FOREIGN KEY (`user1`) REFERENCES `user` (`id`);

ALTER TABLE `direct_message` ADD FOREIGN KEY (`user2`) REFERENCES `user` (`id`);

ALTER TABLE `meeting_participant` ADD FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`);

ALTER TABLE `meeting_participant` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `meeting_participant` ADD FOREIGN KEY (`status_id`) REFERENCES `meeting_participant_status` (`id`);

ALTER TABLE `notification_queue` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `notification_queue` ADD FOREIGN KEY (`notification_type_id`) REFERENCES `notification_type` (`id`);

ALTER TABLE `permission` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `workspace_member` ADD FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`);

ALTER TABLE `workspace_member` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `workspace_member` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `channel_shared` ADD FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`);

ALTER TABLE `channel_shared` ADD FOREIGN KEY (`original_workspace_id`) REFERENCES `workspace` (`id`);

ALTER TABLE `channel_shared` ADD FOREIGN KEY (`target_workspace_id`) REFERENCES `workspace` (`id`);

ALTER TABLE `poll_question` ADD FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`);

ALTER TABLE `poll_question` ADD FOREIGN KEY (`created_by`) REFERENCES `user` (`id`);

ALTER TABLE `poll_answer` ADD FOREIGN KEY (`question_id`) REFERENCES `poll_question` (`id`);

ALTER TABLE `poll_answer` ADD FOREIGN KEY (`created_by`) REFERENCES `user` (`id`);

ALTER TABLE `poll_voting_history` ADD FOREIGN KEY (`question_id`) REFERENCES `poll_question` (`id`);

ALTER TABLE `poll_voting_history` ADD FOREIGN KEY (`answer_id`) REFERENCES `poll_answer` (`id`);

ALTER TABLE `poll_voting_history` ADD FOREIGN KEY (`voted_by`) REFERENCES `user` (`id`);

ALTER TABLE `log` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `log` ADD FOREIGN KEY (`event_type_id`) REFERENCES `event_type` (`id`);

ALTER TABLE `event_parameter` ADD FOREIGN KEY (`event_type_id`) REFERENCES `event_type` (`id`);

ALTER TABLE `log_detail` ADD FOREIGN KEY (`log_id`) REFERENCES `log` (`id`);

ALTER TABLE `log_detail` ADD FOREIGN KEY (`event_parameter_id`) REFERENCES `event_parameter` (`id`);

ALTER TABLE `channel_private_member` ADD FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`);

ALTER TABLE `channel_private_member` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `channel_private_member` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`dms_id`) REFERENCES `direct_message` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`parent_id`) REFERENCES `message` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`status_id`) REFERENCES `status_message` (`id`);

ALTER TABLE `message_mention` ADD FOREIGN KEY (`message_id`) REFERENCES `message` (`id`);

ALTER TABLE `message_mention` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `message_attachment` ADD FOREIGN KEY (`message_id`) REFERENCES `message` (`id`);

ALTER TABLE `message_reaction` ADD FOREIGN KEY (`message_id`) REFERENCES `message` (`id`);

ALTER TABLE `message_reaction` ADD FOREIGN KEY (`emoji_id`) REFERENCES `emoji` (`id`);

ALTER TABLE `message_reaction` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `block_list` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `block_list` ADD FOREIGN KEY (`user_id_is_blocked`) REFERENCES `user` (`id`);

ALTER TABLE `block_list` ADD FOREIGN KEY (`dms_id`) REFERENCES `direct_message` (`id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`group_id`) REFERENCES `feedback_group` (`id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`status_id`) REFERENCES `feedback_status` (`id`);

ALTER TABLE `feedback_assignee` ADD FOREIGN KEY (`feedback_id`) REFERENCES `feedback` (`id`);

ALTER TABLE `feedback_assignee` ADD FOREIGN KEY (`assignee_id`) REFERENCES `user` (`id`);

ALTER TABLE `feedback_result` ADD FOREIGN KEY (`feedback_id`) REFERENCES `feedback` (`id`);

ALTER TABLE `feedback_result_reply` ADD FOREIGN KEY (`feedback_result_id`) REFERENCES `feedback_result` (`id`);

ALTER TABLE `feedback_result_reply` ADD FOREIGN KEY (`replied_by`) REFERENCES `user` (`id`);

ALTER TABLE `navigation_bar` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `navigation_bar` ADD FOREIGN KEY (`feature_nav_bar`) REFERENCES `main_feature` (`id`);

ALTER TABLE `notification_setting` ADD FOREIGN KEY (`preference_id`) REFERENCES `preference` (`id`);

ALTER TABLE `notification_setting` ADD FOREIGN KEY (`notification_type_id`) REFERENCES `notification_type` (`id`);

ALTER TABLE `preference` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `preference` ADD FOREIGN KEY (`language_id`) REFERENCES `language` (`id`);

```

## Insert Data

```sql
-- User
INSERT INTO `user` (uuid, email, full_name, dob, last_login_at, is_active, created_at) VALUES
(UUID(),'zleffler@example.com','Jaquelin Heathcote','2015-07-27',NOW(),0,NOW()),
(UUID(),'brian59@example.com','Mr. Abdiel Zemlak II','2020-11-09',NOW(),1,NOW()),
(UUID(),'jaquan94@example.com','Dr. Clementine Rice','2003-11-08',NOW(),0,NOW()),
(UUID(),'brook64@example.net','Whitney Reilly','1992-04-02',NOW(),1,NOW()),
(UUID(),'lynch.daisy@example.com','Merl Renner','1974-06-03',NOW(),0,NOW()),
(UUID(),'jerome.mccullough@example.com','Annabell Welch','2022-05-20',NOW(),0,NOW()),
(UUID(),'eusebio.cartwright@example.org','Miss Nadia Wilkinson MD','1987-09-05',NOW(),0,NOW()),
(UUID(),'megane42@example.net','Tessie Beahan','2020-08-07',NOW(),0,NOW()),
(UUID(),'tfahey@example.org','Maximillian Lynch','1993-10-15',NOW(),0,NOW()),
(UUID(),'christiansen.nico@example.org','Jayson Walker','1995-12-24',NOW(),1,NOW()),
(UUID(),'ehegmann@example.com','Misty Carter V','1977-05-04',NOW(),1,NOW()),
(UUID(),'harber.kiera@example.com','Arturo Johns IV','1995-11-15',NOW(),0,NOW()),
(UUID(),'zaria19@example.org','Prof. Torey Hammes','1991-07-17',NOW(),1,NOW()),
(UUID(),'dion.muller@example.org','Izaiah Murazik IV','2013-04-25',NOW(),0,NOW()),
(UUID(),'blair54@example.com','Dr. Erwin Weber','2021-02-28',NOW(),1,NOW()),
(UUID(),'wanda.herman@example.net','Miss Alexandrea Howe','1986-11-02',NOW(),0,NOW()),
(UUID(),'pmuller@example.com','Lowell Denesik','2002-07-03',NOW(),0,NOW()),
(UUID(),'aubree.emard@example.org','Prof. Ryley Mante V','1977-08-18',NOW(),1,NOW()),
(UUID(),'sallie.crist@example.com','Dr. Donnie Erdman MD','1975-04-02',NOW(),0,NOW())

-- Notification Type
INSERT INTO notification_type (uuid, name) VALUES
(UUID(), 'Email'),
(UUID(), 'SMS'),
(UUID(), 'Push Notification'),
(UUID(), 'In-App Message');

-- Status Message
INSERT INTO status_message (uuid, name) VALUES
(UUID(), 'Sent'),
(UUID(), 'Delivered'),
(UUID(), 'Read'),
(UUID(), 'Failed');

-- Workspace
INSERT INTO workspace (uuid, name, owner_id) VALUES
(UUID(), 'Development Team', 1),
(UUID(), 'Marketing Team', 2),
(UUID(), 'Sales Team', 3),
(UUID(), 'HR Team', 4),
(UUID(), 'Support Team', 5);


-- Role
INSERT INTO role (uuid, name, value) VALUES
(UUID(), 'Admin',8),
(UUID(), 'Mentor',4),
(UUID(), 'Channel Management',2),
(UUID(), 'Channel Management',1);

-- Status Meeting Participant
INSERT INTO meeting_participant_status (uuid, name) VALUES
(UUID(), 'Pending'),
(UUID(), 'Accepted'),
(UUID(), 'Declined');

-- Event Type
INSERT INTO event_type (uuid, name) VALUES
(UUID(), 'Message Sent'),
(UUID(), 'Message Received'),
(UUID(), 'File Uploaded'),
(UUID(), 'File Downloaded'),
(UUID(), 'User Online');

-- Status Message
INSERT INTO status_message (uuid, name) VALUES
(UUID(),'Sent'),
(UUID(),'Delivered'),
(UUID(),'Read');


-- Emoji
INSERT INTO emoji (uuid, unicode, name) VALUES
(UUID(), 'U+1F600', 'Grinning Face'),
(UUID(), 'U+1F601', 'Grinning Face with Smiling Eyes'),
(UUID(), 'U+1F602', 'Face with Tears of Joy'),
(UUID(), 'U+1F603', 'Smiling Face with Open Mouth'),
(UUID(), 'U+1F604', 'Smiling Face with Open Mouth and Smiling Eyes'),
(UUID(), 'U+1F605', 'Smiling Face with Open Mouth and Cold Sweat'),
(UUID(), 'U+1F606', 'Smiling Face with Open Mouth and Tightly-Closed Eyes'),
(UUID(), 'U+1F607', 'Smiling Face with Halo'),
(UUID(), 'U+1F608', 'Smiling Face with Horns'),
(UUID(), 'U+1F609', 'Winking Face');

-- Channel
INSERT INTO channel (uuid, workspace_id, name, description, is_private, created_at) VALUES
(UUID(), 1, 'Channel One', 'Description for Channel One', 0, NOW()),
(UUID(), 1, 'Channel Two', 'Description for Channel Two', 1, NOW()),
(UUID(), 1, 'Channel Three', 'Description for Channel Three', 0, NOW()),
(UUID(), 1, 'Channel Four', 'Description for Channel Four', 1, NOW()),
(UUID(), 2, 'Channel Five', 'Description for Channel Five', 0, NOW()),
(UUID(), 2, 'Channel Six', 'Description for Channel Six', 1, NOW()),
(UUID(), 2, 'Channel Seven', 'Description for Channel Seven', 0, NOW()),
(UUID(), 2, 'Channel Eight', 'Description for Channel Eight', 1, NOW()),
(UUID(), 2, 'Channel Nine', 'Description for Channel Nine', 0, NOW()),
(UUID(), 3, 'Channel Ten', 'Description for Channel Ten', 1, NOW());

-- Direct Message
INSERT INTO direct_message (uuid, user1, user2, created_at) VALUES
(UUID(), 1, 2, NOW()),
(UUID(), 3, 4, NOW()),
(UUID(), 5, 6, NOW()),
(UUID(), 7, 8, NOW()),
(UUID(), 9, 10, NOW()),
(UUID(), 1, 3, NOW()),
(UUID(), 2, 4, NOW()),
(UUID(), 5, 7, NOW()),
(UUID(), 6, 8, NOW()),
(UUID(), 9, 1, NOW());

-- Meeting
INSERT INTO meeting (uuid, owner_id, name, description, start_at, end_at, created_at) VALUES
(UUID(), 1, 'Meeting One', 'Description for Meeting One', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW()),
(UUID(), 1, 'Meeting Two', 'Description for Meeting Two', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW()),
(UUID(), 1, 'Meeting Three', 'Description for Meeting Three', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW()),
(UUID(), 2, 'Meeting Four', 'Description for Meeting Four', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW()),
(UUID(), 3, 'Meeting Five', 'Description for Meeting Five', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW()),
(UUID(), 4, 'Meeting Six', 'Description for Meeting Six', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW()),
(UUID(), 3, 'Meeting Seven', 'Description for Meeting Seven', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW()),
(UUID(), 5, 'Meeting Eight', 'Description for Meeting Eight', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW()),
(UUID(), 2, 'Meeting Nine', 'Description for Meeting Nine', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW()),
(UUID(), 1, 'Meeting Ten', 'Description for Meeting Ten', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW());




-- Meeting Participant
INSERT INTO meeting_participant (meeting_id, user_id, status_id, joined_at) VALUES
(1, 1, 2, NOW()),
(1, 2, 2, NOW()),
(1, 3, 2, NOW()),
(1, 4, 3, NOW()),
(2, 5, 1, NOW()),
(2, 6, 2, NOW()),
(3, 7, 2, NOW()),
(3, 8, 2, NOW()),
(4, 9, 3, NOW()),
(4, 10, 2, NOW());


-- Event Parameters
INSERT INTO event_parameter (uuid,name, data_type, event_type_id) VALUES 
(UUID(),'Duration', 'INT', 5),
(UUID(),'Unit Duration', 'VARCHAR', 5);


-- Log
INSERT INTO log (uuid,user_id, event_type_id, logtime) VALUES
(UUID(),1, 5, NOW()),
(UUID(),2, 5, NOW()),
(UUID(),3, 5, NOW()),
(UUID(),4, 5, NOW()),
(UUID(),5, 5, NOW()),
(UUID(),6, 5, NOW()),
(UUID(),7, 5, NOW()),
(UUID(),8, 5, NOW()),
(UUID(),9, 5, NOW()),
(UUID(),10, 5, NOW());

-- Log Details
INSERT INTO log_detail (uuid,log_id, event_parameter_id, value) VALUES 
(UUID(),1, 1, 30),
(UUID(),1, 2, 'minutes'),
(UUID(),2, 1, 30),
(UUID(),2, 2, 'hours'),
(UUID(),3, 1, 30),
(UUID(),3, 2, 'minutes'),
(UUID(),4, 1, 30),
(UUID(),4, 2, 'minutes'),
(UUID(),5, 1, 30),
(UUID(),5, 2, 'minutes'),
(UUID(),6, 1, 30),
(UUID(),6, 2, 'minutes'),
(UUID(),7, 1, 30),
(UUID(),7, 2, 'minutes'),
(UUID(),8, 1, 30),
(UUID(),8, 2, 'minutes'),
(UUID(),9, 1, 30),
(UUID(),9, 2, 'minutes'),
(UUID(),10, 1, 30),
(UUID(),10, 2, 'minutes');

-- Block List
INSERT INTO block_list (user_id, user_id_is_blocked) VALUES
(1, 2),
(2, 3);

-- Feedback Group
INSERT INTO `feedback_group` (`uuid`, `name`)
VALUES
(UUID(), 'Bug Reports'),
(UUID(), 'Feature Requests');

-- Feedback Status
INSERT INTO `feedback_status` (`uuid`, `name`)
VALUES
(UUID(), 'Open'),
(UUID(), 'In progress')
(UUID(), 'Closed');


-- Feedback
INSERT INTO `feedback` (`uuid`, `user_id`, `sender_email`, `group_id`, `status_id`, `content`, `send_at`, `updated_at`)
VALUES
(UUID(), 1, 'user1@example.com', 1, 1, 'There is a bug in the system', NOW(), NOW());

-- Feedback Assignee
INSERT INTO `feedback_assignee` (`feedback_id`, `assignee_id`, `assign_at`, `message`)
VALUES
(1, 2, NOW(), 'Please look into this issue');

-- Feedback Result
INSERT INTO `feedback_result` (`uuid`, `feedback_id`, `content`, `send_at`)
VALUES
(UUID(), 1, 'The issue has been resolved', NOW());

-- Feedback Result reply
INSERT INTO `feedback_result_reply` (`feedback_result_id`, `replied_by`, `replied_at`, `content`)
VALUES
(1, 1, NOW(), 'Thank you for fixing it');


```

## DBML

```sql
Table user {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  email varchar(255)
  full_name varchar(255)
  dob date [default: null]
  last_login_at timestamp
  is_active boolean
  created_at timestamp
}

Table notification_type {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255) 
}

Table status_message {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255) 
}

Table workspace {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255) 
  owner_id int [ref: > user.id]
  source_id int [default: null]
  source_type_id int [default: null]
}


Table event_type {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255) 
}

Table meeting_participant_status {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255) 
}

Table channel {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  workspace_id int [ref: > workspace.id]
  name varchar(255) 
  description varchar(500)
  is_private tinyint(1)
  created_at timestamp
}

Table role {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255)
}

Table language {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255)
}

Table main_feature {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255)
}

Table emoji {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  unicode varchar(255) 
  name varchar(255)
}

Table feedback_group {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255)
}

Table feedback_status {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255)
}

Table meeting {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  owner_id int [ref: > user.id]
  name varchar(255) 
  description varchar(500)
  start_at timestamp
  end_at timestamp 
  created_at timestamp
}

Table direct_message {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  user1 int [ref: > user.id]
  user2 int [ref: > user.id]
  created_at timestamp
}

Table meeting_participant {
  meeting_id int [ref: > meeting.id]
  user_id int [ref: > user.id]
  status_id int [ref: > meeting_participant_status.id]
  joined_at timestamp [default: null]
  left_at timestamp [default: null]
}

Table notification_queue {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  user_id int [ref: > user.id]
  notification_type_id int [ref: > notification_type.id]
  content text
  is_read tinyint(1)
}


Table permission {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  role_id int [ref: > role.id]
  name varchar(255)
  value int
}

Table workspace_member {
  workspace_id int [ref: > workspace.id]
  user_id int [ref: > user.id]
  join_at timestamp
  updated_at timestamp
  left_at timestamp [default: null]
  role_id int [default: null, ref: > role.id]
}

Table channel_shared {
  channel_id int [ref: > channel.id]
  original_workspace_id int [ref: > workspace.id]
  target_workspace_id int [ref: > workspace.id]
}

Table poll_question {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  question varchar(255)
  channel_id int [ref: > channel.id]
  created_by int [ref: > user.id]
  created_at timestamp
  expires_at timestamp
}

Table poll_answer {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  question_id int [ref: > poll_question.id]
  answer varchar(255) 
  created_by int [ref: > user.id]
  created_at timestamp
}

Table poll_voting_history {
  question_id int [ref: > poll_question.id]
  answer_id int [ref: > poll_answer.id]
  voted_by int [ref: > user.id]
  voted_at timestamp
}

Table log {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  user_id int [ref: > user.id]
  event_type_id int [ref: > event_type.id]
  logtime timestamp
}

Table event_parameter {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255)
  data_type varchar(255)
  event_type_id int [ref: > event_type.id]
}

Table log_detail {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  log_id int [ref: > log.id]
  event_parameter_id int [ref: > event_parameter.id]
  value varchar(255)
}

Table channel_private_member {
  channel_id int [ref: > channel.id]
  user_id int [ref: > user.id]
  role_id int [default: null, ref: > role.id]
}



Table message {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  sender_id int [ref: > user.id]
  content text 
  channel_id int [default: null, ref: > channel.id]
  dms_id int [default: null, ref: > direct_message.id]
  parent_id int [default: null, ref: > message.id]
  meeting_id int [default: null, ref: > meeting.id]
  status_id int [default: null, ref: > status_message.id]
  send_at timestamp 
  edited_at timestamp [default: null]
  is_deleted tinyint(1) [default: 0]
  deleted_at timestamp [default: null]
}


Table message_mention {
  message_id int [ref: > message.id]
  user_id int [ref: > user.id]
}

Table message_attachment {
  message_id int [ref: > message.id]
  access_id int 
}



Table message_reaction {
  message_id int [ref: > message.id]
  emoji_id int [ref: > emoji.id]
  user_id int [ref: > user.id]
}

Table block_list {
  user_id int [ref: > user.id]
  user_id_is_blocked int [ref: > user.id]
  dms_id int [ref: > direct_message.id]
}

Table feedback {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  user_id int [ref: > user.id]
  sender_email varchar(100)
  group_id int [default: null, ref: > feedback_group.id]
  status_id int [ref: > feedback_status.id]
  content text 
  send_at timestamp
  updated_at timestamp
}


Table feedback_assignee {
  feedback_id int [ref: > feedback.id]
  assignee_id int [ref: > user.id]
  assign_at timestamp
  message text
}


Table feedback_result {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  feedback_id int [ref: > feedback.id]
  content text
  send_at timestamp
}

Table feedback_result_reply {
  feedback_result_id int [ref: > feedback_result.id]
  replied_by int [ref: > user.id]
  replied_at timestamp
  content text
}


Table navigation_bar {
  user_id int [ref: > user.id]
  feature_nav_bar int [ref: > main_feature.id]
}

Table notification_setting {
  preference_id int [ref: > preference.id] 
  notification_type_id int [ref: > notification_type.id]
  enable tinyint(1)
}

Table preference {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  user_id int [ref: > user.id]
  dark_mode tinyint(1) [default: 0]
  language_id int [ref: > language.id]
}
```
