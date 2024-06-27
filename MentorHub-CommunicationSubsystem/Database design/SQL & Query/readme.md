# SQL & Query

- Should review from [Dashboard](Dashboard) -> [Home](Home) -> [Direct Message](DirectMessage) -> [Meeting](Meeting) -> [Feedback](Feedback)

- View [Sample Data](https://docs.google.com/spreadsheets/d/1iZMjM3puy4DVT-Nmn2sfRYUixvbTIX2Fhw903t7ScH4/edit?gid=898446453#gid=898446453) 

- MySQL: [Create Schema and insert data](#create-schema-and-insert-data)

- DBML: [Table design](#dbml)

## Create Schema and Insert Data

```sql
CREATE SCHEMA mentorhub_communication;
USE mentorhub_communication;

CREATE TABLE `channel` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `workspace_id` int,
  `name` varchar(255),
  `description` varchar(500),
  `is_private` tinyint(1),
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

CREATE TABLE `meeting_participant_status` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `message_mention` (
  `message_id` int,
  `user_id` int
);

CREATE TABLE `message` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `sender_id` int,
  `content` text DEFAULT null,
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

CREATE TABLE `notification_queue` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `user_id` int DEFAULT null,
  `notification_type_id` int DEFAULT null,
  `content` text,
  `is_read` tinyint(1)
);

CREATE TABLE `notification_type` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255) DEFAULT null
);

CREATE TABLE `status_message` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255) DEFAULT null
);

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

CREATE TABLE `workspace` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255),
  `owner_id` int
);

CREATE TABLE `workspace_member` (
  `workspace_id` int,
  `user_id` int,
  `join_at` timestamp,
  `updated_at` timestamp,
  `left_at` timestamp DEFAULT null,
  `role_id` int DEFAULT null
);

CREATE TABLE `role` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
);

CREATE TABLE `permission` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `role_id` int,
  `name` varchar(255),
  `value` int
);

CREATE TABLE `message_attachment` (
  `message_id` int,
  `access_id` int
);

CREATE TABLE `emoji` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `unicode` varchar(255),
  `name` varchar(255)
);

CREATE TABLE `message_reaction` (
  `message_id` int,
  `emoji_id` int,
  `user_id` int
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

CREATE TABLE `event_type` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `name` varchar(255)
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

CREATE TABLE `block_list` (
  `user_id` int,
  `user_id_is_blocked` int,
  `dms_id` int
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

CREATE TABLE `navigation_bar` (
  `user_id` int,
  `feature_nav_bar` int
);

CREATE TABLE `notification_setting` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `notification_type_id` int,
  `meta_data` text
);

CREATE TABLE `preference` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uuid` char(36) UNIQUE NOT NULL,
  `user_id` int,
  `dark_mode` tinyint(1) DEFAULT 0,
  `language_id` int,
  `enable` tinyint(1)
);

ALTER TABLE `channel` ADD FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`);

ALTER TABLE `direct_message` ADD FOREIGN KEY (`user1`) REFERENCES `user` (`id`);

ALTER TABLE `direct_message` ADD FOREIGN KEY (`user2`) REFERENCES `user` (`id`);

ALTER TABLE `meeting_participant` ADD FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`);

ALTER TABLE `meeting_participant` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `meeting_participant` ADD FOREIGN KEY (`status_id`) REFERENCES `meeting_participant_status` (`id`);

ALTER TABLE `meeting` ADD FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);

ALTER TABLE `message_mention` ADD FOREIGN KEY (`message_id`) REFERENCES `message` (`id`);

ALTER TABLE `message_mention` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`dms_id`) REFERENCES `direct_message` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`parent_id`) REFERENCES `message` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`);

ALTER TABLE `message` ADD FOREIGN KEY (`status_id`) REFERENCES `status_message` (`id`);

ALTER TABLE `notification_queue` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `notification_queue` ADD FOREIGN KEY (`notification_type_id`) REFERENCES `notification_type` (`id`);

ALTER TABLE `workspace` ADD FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);

ALTER TABLE `workspace_member` ADD FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`);

ALTER TABLE `workspace_member` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `workspace_member` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `permission` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `message_attachment` ADD FOREIGN KEY (`message_id`) REFERENCES `message` (`id`);

ALTER TABLE `message_reaction` ADD FOREIGN KEY (`message_id`) REFERENCES `message` (`id`);

ALTER TABLE `message_reaction` ADD FOREIGN KEY (`emoji_id`) REFERENCES `emoji` (`id`);

ALTER TABLE `message_reaction` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

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

ALTER TABLE `notification_setting` ADD FOREIGN KEY (`notification_type_id`) REFERENCES `notification_type` (`id`);

ALTER TABLE `preference` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `preference` ADD FOREIGN KEY (`language_id`) REFERENCES `language` (`id`);


-- Insert into user table
INSERT INTO user (uuid, email, full_name, dob, last_login_at, is_active, created_at)
VALUES
  ('uuid1', 'user1@example.com', 'User One', '1990-01-01', '2023-06-01 12:00:00', TRUE, '2023-01-01 12:00:00'),
  ('uuid2', 'user2@example.com', 'User Two', '1985-02-02', '2023-06-02 12:00:00', TRUE, '2023-01-02 12:00:00'),
  ('uuid3', 'user3@example.com', 'User Three', '1995-03-03', '2023-06-03 12:00:00', FALSE, '2023-01-03 12:00:00'),
  ('uuid4', 'user4@example.com', 'User Four', '2000-04-04', '2023-06-04 12:00:00', TRUE, '2023-01-04 12:00:00'),
  ('uuid5', 'user5@example.com', 'User Five', '1992-05-05', '2023-06-05 12:00:00', TRUE, '2023-01-05 12:00:00'),
  ('uuid6', 'user6@example.com', 'User Six', '1988-06-06', '2023-06-06 12:00:00', TRUE, '2023-01-06 12:00:00'),
  ('uuid7', 'user7@example.com', 'User Seven', '1975-07-07', '2023-06-07 12:00:00', TRUE, '2023-01-07 12:00:00'),
  ('uuid8', 'user8@example.com', 'User Eight', '1980-08-08', '2023-06-08 12:00:00', TRUE, '2023-01-08 12:00:00'),
  ('uuid9', 'user9@example.com', 'User Nine', '1983-09-09', '2023-06-09 12:00:00', TRUE, '2023-01-09 12:00:00'),
  ('uuid10', 'user10@example.com', 'User Ten', '1998-10-10', '2023-06-10 12:00:00', TRUE, '2023-01-10 12:00:00');

-- Insert into workspace table
INSERT INTO workspace (uuid, name, owner_id)
VALUES
  ('workspace-uuid1', 'Workspace One', 1),
  ('workspace-uuid2', 'Workspace Two', 2),
  ('workspace-uuid3', 'Workspace Three', 3),
  ('workspace-uuid4', 'Workspace Four', 4),
  ('workspace-uuid5', 'Workspace Five', 5);

-- Insert into channel table
INSERT INTO channel (uuid, workspace_id, name, description, is_private, created_at)
VALUES
  ('channel-uuid1', 1, 'Channel One', 'Description One', 0, '2023-01-01 12:00:00'),
  ('channel-uuid2', 2, 'Channel Two', 'Description Two', 1, '2023-01-02 12:00:00'),
  ('channel-uuid3', 3, 'Channel Three', 'Description Three', 0, '2023-01-03 12:00:00'),
  ('channel-uuid4', 4, 'Channel Four', 'Description Four', 1, '2023-01-04 12:00:00'),
  ('channel-uuid5', 5, 'Channel Five', 'Description Five', 0, '2023-01-05 12:00:00');

-- Insert into direct_message table
INSERT INTO direct_message (uuid, user1, user2, created_at)
VALUES
  ('dms-uuid1', 1, 2, '2023-01-01 12:00:00'),
  ('dms-uuid2', 3, 4, '2023-01-02 12:00:00'),
  ('dms-uuid3', 5, 6, '2023-01-03 12:00:00'),
  ('dms-uuid4', 7, 8, '2023-01-04 12:00:00'),
  ('dms-uuid5', 9, 10, '2023-01-05 12:00:00');

-- Insert into meeting table
INSERT INTO meeting (uuid, owner_id, name, description, start_at, end_at, created_at)
VALUES
  ('meeting-uuid1', 1, 'Meeting One', 'Description One', '2023-01-01 14:00:00', '2023-01-01 15:00:00', '2023-01-01 12:00:00'),
  ('meeting-uuid2', 2, 'Meeting Two', 'Description Two', '2023-01-02 14:00:00', '2023-01-02 15:00:00', '2023-01-02 12:00:00'),
  ('meeting-uuid3', 3, 'Meeting Three', 'Description Three', '2023-01-03 14:00:00', '2023-01-03 15:00:00', '2023-01-03 12:00:00'),
  ('meeting-uuid4', 4, 'Meeting Four', 'Description Four', '2023-01-04 14:00:00', '2023-01-04 15:00:00', '2023-01-04 12:00:00'),
  ('meeting-uuid5', 5, 'Meeting Five', 'Description Five', '2023-01-05 14:00:00', '2023-01-05 15:00:00', '2023-01-05 12:00:00');

-- Insert into meeting_participant table
INSERT INTO meeting_participant (meeting_id, user_id, status_id, joined_at, left_at)
VALUES
  (1, 1, 1, '2023-01-01 14:00:00', '2023-01-01 15:00:00'),
  (2, 2, 2, '2023-01-02 14:00:00', '2023-01-02 15:00:00'),
  (3, 3, 3, '2023-01-03 14:00:00', '2023-01-03 15:00:00'),
  (4, 4, 1, '2023-01-04 14:00:00', '2023-01-04 15:00:00'),
  (5, 5, 2, '2023-01-05 14:00:00', '2023-01-05 15:00:00');

-- Insert into message table
INSERT INTO message (uuid, sender_id, content, channel_id, dms_id, parent_id, meeting_id, status_id, send_at, edited_at, is_deleted, deleted_at)
VALUES
  ('message-uuid1', 1, 'Message Content One', 1, NULL, NULL, NULL, 1, '2023-01-01 12:00:00', NULL, 0, NULL),
  ('message-uuid2', 2, 'Message Content Two', NULL, 1, NULL, NULL, 2, '2023-01-02 12:00:00', NULL, 0, NULL),
  ('message-uuid3', 3, 'Message Content Three', NULL, NULL, NULL, 1, 3, '2023-01-03 12:00:00', NULL, 0, NULL),
  ('message-uuid4', 4, 'Message Content Four', 2, NULL, NULL, NULL, 1, '2023-01-04 12:00:00', NULL, 0, NULL),
  ('message-uuid5', 5, 'Message Content Five', NULL, 2, NULL, NULL, 2, '2023-01-05 12:00:00', NULL, 0, NULL);

-- Insert into message_mention table
INSERT INTO message_mention (message_id, user_id)
VALUES
  (1, 2),
  (2, 3),
  (3, 4),
  (4, 5),
  (5, 1);

-- Insert into notification_queue table
INSERT INTO notification_queue (uuid, user_id, notification_type_id, content, is_read)
VALUES
  ('notification-uuid1', 1, 1, 'Notification Content One', 0),
  ('notification-uuid2', 2, 2, 'Notification Content Two', 1),
  ('notification-uuid3', 3, 3, 'Notification Content Three', 0),
  ('notification-uuid4', 4, 4, 'Notification Content Four', 1),
  ('notification-uuid5', 5, 5, 'Notification Content Five', 0);

-- Insert into notification_type table
INSERT INTO notification_type (uuid, name)
VALUES
  ('notification-type-uuid1', 'Notification Type One'),
  ('notification-type-uuid2', 'Notification Type Two'),
  ('notification-type-uuid3', 'Notification Type Three'),
  ('notification-type-uuid4', 'Notification Type Four'),
  ('notification-type-uuid5', 'Notification Type Five');

-- Insert into status_message table
INSERT INTO status_message (uuid, name)
VALUES
  ('status-message-uuid1', 'Status One'),
  ('status-message-uuid2', 'Status Two'),
  ('status-message-uuid3', 'Status Three'),
  ('status-message-uuid4', 'Status Four'),
  ('status-message-uuid5', 'Status Five');

-- Insert into workspace_member table
INSERT INTO workspace_member (workspace_id, user_id, join_at, updated_at, left_at, role_id)
VALUES
  (1, 1, '2023-01-01 12:00:00', '2023-01-01 12:00:00', NULL, 1),
  (2, 2, '2023-01-02 12:00:00', '2023-01-02 12:00:00', NULL, 2),
  (3, 3, '2023-01-03 12:00:00', '2023-01-03 12:00:00', NULL, 3),
  (4, 4, '2023-01-04 12:00:00', '2023-01-04 12:00:00', NULL, 4),
  (5, 5, '2023-01-05 12:00:00', '2023-01-05 12:00:00', NULL, 5);

-- Insert into role table
INSERT INTO role (uuid, name)
VALUES
  ('role-uuid1', 'Role One'),
  ('role-uuid2', 'Role Two'),
  ('role-uuid3', 'Role Three'),
  ('role-uuid4', 'Role Four'),
  ('role-uuid5', 'Role Five');

-- Insert into permission table
INSERT INTO permission (uuid, role_id, name, value)
VALUES
  ('permission-uuid1', 1, 'Permission One', 1),
  ('permission-uuid2', 2, 'Permission Two', 2),
  ('permission-uuid3', 3, 'Permission Three', 3),
  ('permission-uuid4', 4, 'Permission Four', 4),
  ('permission-uuid5', 5, 'Permission Five', 5);

-- Insert into message_attachment table
INSERT INTO message_attachment (message_id, access_id)
VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5);

-- Insert into emoji table
INSERT INTO emoji (uuid, unicode, name)
VALUES
  ('emoji-uuid1', 'U+1F600', 'Grinning Face'),
  ('emoji-uuid2', 'U+1F601', 'Beaming Face with Smiling Eyes'),
  ('emoji-uuid3', 'U+1F602', 'Face with Tears of Joy'),
  ('emoji-uuid4', 'U+1F603', 'Grinning Face with Big Eyes'),
  ('emoji-uuid5', 'U+1F604', 'Grinning Face with Smiling Eyes');

-- Insert into message_reaction table
INSERT INTO message_reaction (message_id, emoji_id, user_id)
VALUES
  (1, 1, 1),
  (2, 2, 2),
  (3, 3, 3),
  (4, 4, 4),
  (5, 5, 5);

-- Insert into channel_shared table
INSERT INTO channel_shared (channel_id, original_workspace_id, target_workspace_id)
VALUES
  (1, 1, 2),
  (2, 2, 3),
  (3, 3, 4),
  (4, 4, 5),
  (5, 5, 1);

-- Insert into poll_question table
INSERT INTO poll_question (uuid, question, channel_id, created_by, created_at, expires_at)
VALUES
  ('poll-question-uuid1', 'Question One', 1, 1, '2023-01-01 12:00:00', '2023-01-02 12:00:00'),
  ('poll-question-uuid2', 'Question Two', 2, 2, '2023-01-02 12:00:00', '2023-01-03 12:00:00'),
  ('poll-question-uuid3', 'Question Three', 3, 3, '2023-01-03 12:00:00', '2023-01-04 12:00:00'),
  ('poll-question-uuid4', 'Question Four', 4, 4, '2023-01-04 12:00:00', '2023-01-05 12:00:00'),
  ('poll-question-uuid5', 'Question Five', 5, 5, '2023-01-05 12:00:00', '2023-01-06 12:00:00');

-- Insert into poll_answer table
INSERT INTO poll_answer (uuid, question_id, answer, created_by, created_at)
VALUES
  ('poll-answer-uuid1', 1, 'Answer One', 1, '2023-01-01 12:00:00'),
  ('poll-answer-uuid2', 2, 'Answer Two', 2, '2023-01-02 12:00:00'),
  ('poll-answer-uuid3', 3, 'Answer Three', 3, '2023-01-03 12:00:00'),
  ('poll-answer-uuid4', 4, 'Answer Four', 4, '2023-01-04 12:00:00'),
  ('poll-answer-uuid5', 5, 'Answer Five', 5, '2023-01-05 12:00:00');

-- Insert into poll_voting_history table
INSERT INTO poll_voting_history (question_id, answer_id, voted_by, voted_at)
VALUES
  (1, 1, 1, '2023-01-01 12:00:00'),
  (2, 2, 2, '2023-01-02 12:00:00'),
  (3, 3, 3, '2023-01-03 12:00:00'),
  (4, 4, 4, '2023-01-04 12:00:00'),
  (5, 5, 5, '2023-01-05 12:00:00');

-- Insert into event_type table
INSERT INTO event_type (uuid, name)
VALUES
  ('event-type-uuid1', 'Event Type One'),
  ('event-type-uuid2', 'Event Type Two'),
  ('event-type-uuid3', 'Event Type Three'),
  ('event-type-uuid4', 'Event Type Four'),
  ('event-type-uuid5', 'Event Type Five');

-- Insert into log table
INSERT INTO log (uuid, user_id, event_type_id, logtime)
VALUES
  ('log-uuid1', 1, 1, '2023-01-01 12:00:00'),
  ('log-uuid2', 2, 2, '2023-01-02 12:00:00'),
  ('log-uuid3', 3, 3, '2023-01-03 12:00:00'),
  ('log-uuid4', 4, 4, '2023-01-04 12:00:00'),
  ('log-uuid5', 5, 5, '2023-01-05 12:00:00');

-- Insert into event_parameter table
INSERT INTO event_parameter (uuid, name, data_type, event_type_id)
VALUES
  ('event-parameter-uuid1', 'Parameter One', 'String', 1),
  ('event-parameter-uuid2', 'Parameter Two', 'Integer', 2),
  ('event-parameter-uuid3', 'Parameter Three', 'Boolean', 3),
  ('event-parameter-uuid4', 'Parameter Four', 'Float', 4),
  ('event-parameter-uuid5', 'Parameter Five', 'Date', 5);

-- Insert into log_detail table
INSERT INTO log_detail (uuid, log_id, event_parameter_id, value)
VALUES
  ('log-detail-uuid1', 1, 1, 'Value One'),
  ('log-detail-uuid2', 2, 2, 'Value Two'),
  ('log-detail-uuid3', 3, 3, 'Value Three'),
  ('log-detail-uuid4', 4, 4, 'Value Four'),
  ('log-detail-uuid5', 5, 5, 'Value Five');

-- Insert into channel_private_member table
INSERT INTO channel_private_member (channel_id, user_id, role_id)
VALUES
  (1, 1, 1),
  (2, 2, 2),
  (3, 3, 3),
  (4, 4, 4),
  (5, 5, 5);

-- Insert into block_list table
INSERT INTO block_list (user_id, user_id_is_blocked, dms_id)
VALUES
  (1, 2, 1),
  (3, 4, 2),
  (5, 6, 3),
  (7, 8, 4),
  (9, 10, 5);

-- Insert into feedback_group table
INSERT INTO feedback_group (uuid, name)
VALUES
  ('feedback-group-uuid1', 'Group One'),
  ('feedback-group-uuid2', 'Group Two'),
  ('feedback-group-uuid3', 'Group Three'),
  ('feedback-group-uuid4', 'Group Four'),
  ('feedback-group-uuid5', 'Group Five');

-- Insert into feedback_status table
INSERT INTO feedback_status (uuid, name)
VALUES
  ('feedback-status-uuid1', 'Status One'),
  ('feedback-status-uuid2', 'Status Two'),
  ('feedback-status-uuid3', 'Status Three'),
  ('feedback-status-uuid4', 'Status Four'),
  ('feedback-status-uuid5', 'Status Five');

-- Insert into feedback table
INSERT INTO feedback (uuid, user_id, sender_email, group_id, status_id, content, send_at, updated_at)
VALUES
  ('feedback-uuid1', 1, 'sender1@example.com', 1, 1, 'Feedback Content One', '2023-01-01 12:00:00', '2023-01-01 12:00:00'),
  ('feedback-uuid2', 2, 'sender2@example.com', 2, 2, 'Feedback Content Two', '2023-01-02 12:00:00', '2023-01-02 12:00:00'),
  ('feedback-uuid3', 3, 'sender3@example.com', 3, 3, 'Feedback Content Three', '2023-01-03 12:00:00', '2023-01-03 12:00:00'),
  ('feedback-uuid4', 4, 'sender4@example.com', 4, 4, 'Feedback Content Four', '2023-01-04 12:00:00', '2023-01-04 12:00:00'),
  ('feedback-uuid5', 5, 'sender5@example.com', 5, 5, 'Feedback Content Five', '2023-01-05 12:00:00', '2023-01-05 12:00:00');

-- Insert into feedback_assignee table
INSERT INTO feedback_assignee (feedback_id, assignee_id, assign_at, message)
VALUES
  (1, 1, '2023-01-01 12:00:00', 'Assignee Message One'),
  (2, 2, '2023-01-02 12:00:00', 'Assignee Message Two'),
  (3, 3, '2023-01-03 12:00:00', 'Assignee Message Three'),
  (4, 4, '2023-01-04 12:00:00', 'Assignee Message Four'),
  (5, 5, '2023-01-05 12:00:00', 'Assignee Message Five');

-- Insert into feedback_result table
INSERT INTO feedback_result (uuid, feedback_id, content, send_at)
VALUES
  ('feedback-result-uuid1', 1, 'Feedback Result Content One', '2023-01-01 12:00:00'),
  ('feedback-result-uuid2', 2, 'Feedback Result Content Two', '2023-01-02 12:00:00'),
  ('feedback-result-uuid3', 3, 'Feedback Result Content Three', '2023-01-03 12:00:00'),
  ('feedback-result-uuid4', 4, 'Feedback Result Content Four', '2023-01-04 12:00:00'),
  ('feedback-result-uuid5', 5, 'Feedback Result Content Five', '2023-01-05 12:00:00');

-- Insert into feedback_result_reply table
INSERT INTO feedback_result_reply (feedback_result_id, replied_by, replied_at, content)
VALUES
  (1, 1, '2023-01-01 12:00:00', 'Reply Content One'),
  (2, 2, '2023-01-02 12:00:00', 'Reply Content Two'),
  (3, 3, '2023-01-03 12:00:00', 'Reply Content Three'),
  (4, 4, '2023-01-04 12:00:00', 'Reply Content Four'),
  (5, 5, '2023-01-05 12:00:00', 'Reply Content Five');

-- Insert into language table
INSERT INTO language (uuid, name)
VALUES
  ('language-uuid1', 'Language One'),
  ('language-uuid2', 'Language Two'),
  ('language-uuid3', 'Language Three'),
  ('language-uuid4', 'Language Four'),
  ('language-uuid5', 'Language Five');

-- Insert into main_feature table
INSERT INTO main_feature (uuid, name)
VALUES
  ('main-feature-uuid1', 'Feature One'),
  ('main-feature-uuid2', 'Feature Two'),
  ('main-feature-uuid3', 'Feature Three'),
  ('main-feature-uuid4', 'Feature Four'),
  ('main-feature-uuid5', 'Feature Five');

-- Insert into navigation_bar table
INSERT INTO navigation_bar (user_id, feature_nav_bar)
VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5);

-- Insert into notification_setting table
INSERT INTO notification_setting (uuid, notification_type_id, meta_data)
VALUES
  ('notification-setting-uuid1', 1, 'Meta Data One'),
  ('notification-setting-uuid2', 2, 'Meta Data Two'),
  ('notification-setting-uuid3', 3, 'Meta Data Three'),
  ('notification-setting-uuid4', 4, 'Meta Data Four'),
  ('notification-setting-uuid5', 5, 'Meta Data Five');

-- Insert into preference table
INSERT INTO preference (uuid, user_id, dark_mode, language_id, enable)
VALUES
  ('preference-uuid1', 1, 0, 1, 1),
  ('preference-uuid2', 2, 1, 2, 1),
  ('preference-uuid3', 3, 0, 3, 1),
  ('preference-uuid4', 4, 1, 4, 1),
  ('preference-uuid5', 5, 0, 5, 1);
```

## DBML

```sql
Table channel {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  workspace_id int [ref: > workspace.id]
  name varchar(255) 
  description varchar(500)
  is_private tinyint(1)
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

Table meeting_participant_status {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255) 
}

Table message_mention {
  message_id int [ref: > message.id]
  user_id int [ref: > user.id]
}

Table message {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  sender_id int [ref: > user.id]
  content text [default: null]
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

Table notification_queue {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  user_id int [default: null, ref: > user.id]
  notification_type_id int [default: null, ref: > notification_type.id]
  content text
  is_read tinyint(1)
}

Table notification_type {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255) [default: null]
}

Table status_message {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255) [default: null]
}

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

Table workspace {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255) 
  owner_id int [ref: > user.id]
}

Table workspace_member {
  workspace_id int [ref: > workspace.id]
  user_id int [ref: > user.id]
  join_at timestamp
  updated_at timestamp
  left_at timestamp [default: null]
  role_id int [default: null, ref: > role.id]
  
}

Table role {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255)
}


Table permission {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  role_id int [ref: > role.id]
  name varchar(255)
  value int
}

Table message_attachment {
  message_id int [ref: > message.id]
  access_id int 
}

Table emoji {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  unicode varchar(255) 
  name varchar(255)
}

Table message_reaction {
  message_id int [ref: > message.id]
  emoji_id int [ref: > emoji.id]
  user_id int [ref: > user.id]
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

Table event_type {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  name varchar(255) 
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

Table block_list {
  user_id int [ref: > user.id]
  user_id_is_blocked int [ref: > user.id]
  dms_id int [ref: > direct_message.id]
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

Table navigation_bar {
  user_id int [ref: > user.id]
  feature_nav_bar int [ref: > main_feature.id]
}

Table notification_setting {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  notification_type_id int [ref: > notification_type.id]
  meta_data text
}

Table preference {
  id int [pk, not null, increment]
  uuid char(36) [not null, unique]
  user_id int [ref: > user.id]
  dark_mode tinyint(1) [default: 0]
  language_id int [ref: > language.id]
  enable tinyint(1)
}


```
