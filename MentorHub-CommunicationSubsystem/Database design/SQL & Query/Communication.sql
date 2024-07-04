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
  `owner_id` int
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
