-- DROP FOREIGN KEYS
declare @sql nvarchar(max) = (
    select
    'alter table ' + quotename(schema_name(schema_id)) + '.' +
        quotename(object_name(parent_object_id)) +
        ' drop constraint '+quotename(name) + ';'
from sys.foreign_keys
for xml path('')
);
exec sp_executesql @sql;

DROP TABLE IF EXISTS Subsystem
CREATE TABLE Subsystem
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255),
    description varchar(500),
    created_at datetime
);
INSERT INTO Subsystem
    (name, description, created_at)
VALUES
    ('Communication', 'Communication Subsystem', GETDATE()),
    ('Course Management', 'Course Management Subsystem', GETDATE()),
    ('Mentoring Hub', 'Mentoring Hub', GETDATE());

DROP TABLE IF EXISTS [User];
CREATE TABLE [User]
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    email varchar(255),
    [name] varchar(255),
    dob date DEFAULT null,
    created_at datetime
);

INSERT INTO [User]
    (email, [name], dob, created_at)
VALUES
    ('zleffler@example.com', 'Jaquelin Heathcote', GETDATE(), GETDATE()),
    ('brian59@example.com', 'Mr. Abdiel Zemlak II', GETDATE(), GETDATE()),
    ('jaquan94@example.com', 'Dr. Clementine Rice', GETDATE(), GETDATE()),
    ('brook64@example.net', 'Whitney Reilly', GETDATE(), GETDATE()),
    ('lynch.daisy@example.com', 'Merl Renner', GETDATE(), GETDATE()),
    ('jerome.mccullough@example.com', 'Annabell Welch', GETDATE(), GETDATE()),
    ('eusebio.cartwright@example.org', 'Miss Nadia Wilkinson MD', GETDATE(), GETDATE()),
    ('megane42@example.net', 'Tessie Beahan', GETDATE(), GETDATE()),
    ('tfahey@example.org', 'Maximillian Lynch', GETDATE(), GETDATE()),
    ('christiansen.nico@example.org', 'Jayson Walker', GETDATE(), GETDATE()),
    ('ehegmann@example.com', 'Misty Carter V', GETDATE(), GETDATE()),
    ('harber.kiera@example.com', 'Arturo Johns IV', GETDATE(), GETDATE()),
    ('zaria19@example.org', 'Prof. Torey Hammes', GETDATE(), GETDATE()),
    ('dion.muller@example.org', 'Izaiah Murazik IV', GETDATE(), GETDATE()),
    ('blair54@example.com', 'Dr. Erwin Weber', GETDATE(), GETDATE()),
    ('wanda.herman@example.net', 'Miss Alexandrea Howe', GETDATE(), GETDATE()),
    ('pmuller@example.com', 'Lowell Denesik', GETDATE(), GETDATE()),
    ('aubree.emard@example.org', 'Prof. Ryley Mante V', GETDATE(), GETDATE()),
    ('sallie.crist@example.com', 'Dr. Donnie Erdman MD', GETDATE(), GETDATE());


DROP TABLE IF EXISTS NotificationType;
CREATE TABLE NotificationType
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255)
);

INSERT INTO NotificationType
    (name)
VALUES
    ( 'Email'),
    ( 'SMS'),
    ( 'Push Notification'),
    ( 'In-App Message');

DROP TABLE IF EXISTS StatusMessage;
CREATE TABLE StatusMessage
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255)
);

INSERT INTO StatusMessage
    (name)
VALUES
    ( 'Sent'),
    ( 'Delivered'),
    ( 'Read');

DROP TABLE IF EXISTS Workspace;
CREATE TABLE Workspace
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255),
    owner_id int,
    source_id int DEFAULT null,
    source_type_id int
);

INSERT INTO Workspace
    (name, owner_id)
VALUES
    ('Workspace 1', 1),
    ('Workspace 2', 2);

INSERT INTO Workspace
    (name, owner_id, source_id, source_type_id)
VALUES
    ('Workspace 3', 3, 1, 1);



DROP TABLE IF EXISTS EventType;
CREATE TABLE EventType
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255)
);

INSERT INTO EventType
    (name)
VALUES
    ( 'User Login'),
    ( 'User Online'),
    ( 'User Logout'),
    ( 'User Register'),
    ( 'User Update'),
    ( 'User Delete'),
    ( 'User Block'),
    ( 'User Unblock'),
    ( 'User Change Password'),
    ( 'User Forgot Password'),
    ( 'User Reset Password'),
    ( 'User Change Email'),
    ( 'User Change Role'),
    ( 'User Change Workspace'),
    ( 'User Change Language'),
    ( 'User Change Preference'),
    ( 'User Change Notification Setting'),
    ( 'User Change Navigation Bar'),
    ( 'User Change Dark Mode'),
    ( 'User Change Full Name'),
    ( 'User Change DOB'),
    ( 'User Change Email'),
    ( 'User Change Avatar'),
    ( 'User Change Status'),
    ( 'User Change Phone Number'),
    ( 'User Change Address');


DROP TABLE IF EXISTS MeetingParticipantStatus;
CREATE TABLE MeetingParticipantStatus
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255)
);

INSERT INTO MeetingParticipantStatus
    (name)
VALUES
    ( 'Invited'),
    ( 'Accepted'),
    ( 'Joined'),
    ( 'Left'),
    ( 'Declined');

DROP TABLE IF EXISTS Channel;
CREATE TABLE Channel
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    workspace_id int,
    name varchar(255),
    description varchar(500),
    is_private tinyInt DEFAULT 1,
    created_at datetime
);

INSERT INTO Channel
    (workspace_id, name, description, is_private, created_at)
VALUES
    (1, 'Channel 1', 'Description 1', 0, GETDATE()),
    (1, 'Channel 2', 'Description 2', 1, GETDATE()),
    (2, 'Channel 3', 'Description 3', 0, GETDATE()),
    (2, 'Channel 4', 'Description 4', 1, GETDATE());

DROP TABLE IF EXISTS [Role];
CREATE TABLE Role
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255)
);


DROP TABLE IF EXISTS [Language];
CREATE TABLE Language
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255)
);

DROP TABLE IF EXISTS MainFeature;
CREATE TABLE MainFeature
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255)
);

DROP TABLE IF EXISTS Emoji;
CREATE TABLE Emoji
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    unicode varchar(255),
    name varchar(255)
);
INSERT INTO Emoji
    (unicode, name)
VALUES
    ('U+1F600', 'Grinning Face'),
    ('U+1F603', 'Grinning Face with Big Eyes'),
    ('U+1F604', 'Grinning Face with Smiling Eyes'),
    ('U+1F601', 'Beaming Face with Smiling Eyes'),
    ('U+1F606', 'Grinning Squinting Face'),
    ('U+1F605', 'Grinning Face with Sweat'),
    ('U+1F923', 'Rolling on the Floor Laughing'),
    ('U+1F602', 'Face with Tears of Joy'),
    ('U+1F642', 'Slightly Smiling Face'),
    ('U+1F643', 'Upside-Down Face'),
    ('U+1F609', 'Winking Face'),
    ('U+1F60A', 'Smiling Face with Smiling Eyes'),
    ('U+1F607', 'Smiling Face with Halo'),
    ('U+1F970', 'Smiling Face with Hearts'),
    ('U+1F60D', 'Smiling Face with Heart-Eyes'),
    ('U+1F929', 'Star-Struck'),
    ('U+1F618', 'Face Blowing a Kiss'),
    ('U+1F617', 'Kissing Face'),
    ('U+263A', 'Smiling Face'),
    ('U+1F61A', 'Kissing Face with Closed Eyes'),
    ('U+1F619', 'Kissing Face with Smiling Eyes'),
    ('U+1F60B', 'Face Savoring Food'),
    ('U+1F61B', 'Face with Tongue'),
    ('U+1F61C', 'Winking Face with Tongue'),
    ('U+1F92A', 'Zany Face'),
    ('U+1F61D', 'Squinting Face with Tongue'),
    ('U+1F911', 'Money-Mouth Face'),
    ('U+1F917', 'Hugging Face'),
    ('U+1F92D', 'Face with Hand Over Mouth'),
    ('U+1F92B', 'Shushing Face'),
    ('U+1F914', 'Thinking Face'),
    ('U+1F910', 'Zipper-Mouth Face'),
    ('U+1F928', 'Face with Raised Eyebrow'),
    ('U+1F610', 'Neutral Face'),
    ('U+1F611', 'Expressionless Face'),
    ('U+1F636', 'Face Without Mouth'),
    ('U+1F60F', 'Smirking Face'),
    ('U+1F612', 'Unamused Face'),
    ('U+1F644', 'Face with Rolling Eyes'),
    ('U+1F62C', 'Grimacing Face'),
    ('U+1F925', 'Lying Face'),
    ('U+1F60C', 'Relieved Face'),
    ('U+1F614', 'Pensive Face'),
    ('U+1F62A', 'Sleepy Face'),
    ('U+1F924', 'Drooling Face'),
    ('U+1F634', 'Sleeping Face'),
    ('U+1F637', 'Face with Medical Mask'),
    ('U+1F912', 'Face with Thermometer'),
    ('U+1F915', 'Face with Head-Bandage'),
    ('U+1F922', 'Nauseated Face'),
    ('U+1F92E', 'Face Vomiting'),
    ('U+1F927', 'Sneezing Face'),
    ('U+1F975', 'Hot Face'),
    ('U+1F976', 'Cold Face'),
    ('U+1F974', 'Woozy Face'),
    ('U+1F635', 'Dizzy Face'),
    ('U+1F92F', 'Exploding Head'),
    ('U+9757', 'Cowboy Hat Face'),
    ('U+1F920', 'Partying Face'),
    ('U+1F973', 'Disguised Face'),
    ('U+1F60E', 'Smiling Face with Sunglasses'),
    ('U+1F913', 'Nerd Face'),
    ('U+1F9D0', 'Face with Monocle'),
    ('U+1F615', 'Confused Face'),
    ('U+1F61F', 'Worried Face'),
    ('U+1F641', 'Slightly Frowning Face'),
    ('U+2639', 'Frowning Face'),
    ('U+1F62E', 'Face with Open Mouth'),
    ('U+1F62F', 'Hushed Face'),
    ('U+1F632', 'Astonished Face'),
    ('U+1F633', 'Flushed Face'),
    ('U+1F97A', 'Pleading Face'),
    ('U+1F626', 'Frowning Face with Open Mouth');


DROP TABLE IF EXISTS FeedbackGroup;
CREATE TABLE FeedbackGroup
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255)
);
INSERT INTO FeedbackGroup
    (name)
VALUES
    ( 'Bug'),
    ( 'Feature Request'),
    ( 'Improvement'),
    ( 'Others');

DROP TABLE IF EXISTS FeedbackStatus;
CREATE TABLE FeedbackStatus
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255)
);
INSERT INTO FeedbackStatus
    (name)
VALUES
    ( 'Open'),
    ( 'In Progress'),
    ( 'Closed');

DROP TABLE IF EXISTS Meeting;
CREATE TABLE Meeting
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    owner_id int,
    name varchar(255),
    description varchar(500),
    start_at datetime,
    end_at datetime,
    created_at datetime
);
INSERT INTO Meeting
    (owner_id, name, description, start_at, end_at, created_at)
VALUES
    (1, 'Meeting 1', 'Description 1', GETDATE(), GETDATE(), GETDATE()),
    (1, 'Meeting 2', 'Description 2', GETDATE(), GETDATE(), GETDATE()),
    (2, 'Meeting 3', 'Description 3', GETDATE(), GETDATE(), GETDATE()),
    (2, 'Meeting 4', 'Description 4', GETDATE(), GETDATE(), GETDATE());

DROP TABLE IF EXISTS DirectMessage;
CREATE TABLE DirectMessage
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    user1 int,
    user2 int,
    created_at datetime
);
INSERT INTO DirectMessage
    (user1, user2, created_at)
VALUES
    (1, 2, GETDATE()),
    (1, 3, GETDATE()),
    (2, 3, GETDATE()),
    (2, 4, GETDATE());

DROP TABLE IF EXISTS MeetingParticipant;
CREATE TABLE MeetingParticipant
(
    meeting_id int,
    user_id int,
    status_id int,
    joined_at datetime DEFAULT null,
    left_at datetime DEFAULT null
);
INSERT INTO MeetingParticipant
    (meeting_id, user_id, status_id)
VALUES
    (1, 1, 1),
    (1, 2, 1),
    (1, 3, 1),
    (2, 1, 1),
    (2, 2, 1),
    (2, 3, 1),
    (3, 1, 1),
    (3, 2, 1),
    (3, 3, 1),
    (4, 1, 1),
    (4, 2, 1),
    (4, 3, 1);

DROP TABLE IF EXISTS NotificationQueue;
CREATE TABLE NotificationQueue
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    user_id int,
    notification_type_id int,
    content text,
    is_read tinyInt DEFAULT 0
);
INSERT INTO NotificationQueue
    (user_id, notification_type_id, content)
VALUES
    (1, 4, 'Content 1'),
    (1, 4, 'Content 2'),
    (1, 4, 'Content 3'),
    (1, 4, 'Content 4'),
    (1, 4, 'Content 5'),
    (1, 4, 'Content 6');

DROP TABLE IF EXISTS Permission;
CREATE TABLE Permission
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,

    role_id int,
    name varchar(255),
    value int
);

DROP TABLE IF EXISTS WorkspaceMember;
CREATE TABLE WorkspaceMember
(
    workspace_id int,
    user_id int,
    join_at datetime,
    updated_at datetime,
    left_at datetime DEFAULT null,
    role_id int DEFAULT null
);
INSERT INTO WorkspaceMember
    (workspace_id, user_id, join_at, updated_at)
VALUES
    (1, 1, GETDATE(), GETDATE()),
    (1, 2, GETDATE(), GETDATE()),
    (1, 3, GETDATE(), GETDATE()),
    (1, 2, GETDATE(), GETDATE()),
    (1, 3, GETDATE(), GETDATE()),
    (1, 4, GETDATE(), GETDATE()),
    (1, 5, GETDATE(), GETDATE()),
    (1, 6, GETDATE(), GETDATE()),
    (1, 7, GETDATE(), GETDATE()),
    (2, 1, GETDATE(), GETDATE()),
    (2, 2, GETDATE(), GETDATE()),
    (2, 3, GETDATE(), GETDATE()),
    (3, 1, GETDATE(), GETDATE()),
    (3, 2, GETDATE(), GETDATE()),
    (3, 3, GETDATE(), GETDATE());


DROP TABLE IF EXISTS ChannelShared;
CREATE TABLE ChannelShared
(
    channel_id int,
    original_workspace_id int,
    target_workspace_id int
);
INSERT INTO ChannelShared
    (channel_id, original_workspace_id, target_workspace_id)
VALUES
    (1, 1, 2),
    (2, 1, 2),
    (3, 2, 1),
    (4, 2, 1);

DROP TABLE IF EXISTS PollQuestion;
CREATE TABLE PollQuestion
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    question varchar(255),
    channel_id int,
    created_by int,
    created_at datetime,
    expires_at datetime
);
INSERT INTO PollQuestion
    (question, channel_id, created_by, created_at, expires_at)
VALUES
    ('Question 1', 1, 1, GETDATE(), GETDATE()),
    ('Question 2', 1, 2, GETDATE(), GETDATE()),
    ('Question 3', 2, 1, GETDATE(), GETDATE()),
    ('Question 4', 2, 2, GETDATE(), GETDATE());

DROP TABLE IF EXISTS PollAnswer;
CREATE TABLE PollAnswer
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    question_id int,
    answer varchar(255),
    created_by int,
    created_at datetime
);
INSERT INTO PollAnswer
    (question_id, answer, created_by, created_at)
VALUES
    (1, 'Answer 1', 1, GETDATE()),
    (1, 'Answer 2', 1, GETDATE()),
    (1, 'Answer 3', 1, GETDATE()),
    (2, 'Answer 1', 2, GETDATE()),
    (2, 'Answer 2', 2, GETDATE()),
    (2, 'Answer 3', 2, GETDATE()),
    (3, 'Answer 1', 1, GETDATE()),
    (3, 'Answer 2', 1, GETDATE()),
    (3, 'Answer 3', 1, GETDATE()),
    (4, 'Answer 1', 2, GETDATE()),
    (4, 'Answer 2', 2, GETDATE()),
    (4, 'Answer 3', 2, GETDATE());

DROP TABLE IF EXISTS PollVotingHistory;
CREATE TABLE PollVotingHistory
(
    question_id int,
    answer_id int,
    voted_by int,
    voted_at datetime
);
INSERT INTO PollVotingHistory
    (question_id, answer_id, voted_by, voted_at)
VALUES
    (1, 1, 1, GETDATE()),
    (1, 2, 1, GETDATE()),
    (1, 3, 1, GETDATE()),
    (2, 1, 2, GETDATE()),
    (2, 2, 2, GETDATE()),
    (2, 3, 2, GETDATE()),
    (3, 1, 1, GETDATE()),
    (3, 2, 1, GETDATE()),
    (3, 3, 1, GETDATE()),
    (4, 1, 2, GETDATE()),
    (4, 2, 2, GETDATE()),
    (4, 3, 2, GETDATE());

DROP TABLE IF EXISTS [Log];
CREATE TABLE Log
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    user_id int,
    event_type_id int,
    log_time datetime
);
INSERT INTO [Log]
    (user_id, event_type_id, log_time)
VALUES
    (1, 2, GETDATE()),
    (2, 2, GETDATE()),
    (3, 2, GETDATE()),
    (4, 2, GETDATE()),
    (5, 2, GETDATE());



DROP TABLE IF EXISTS EventParameter;
CREATE TABLE EventParameter
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name varchar(255),
    data_type varchar(255),
    event_type_id int
);
INSERT INTO EventParameter
    (name, data_type, event_type_id)
VALUES
    ('workspace_id', 'int', 2),
    ('duration', 'int', 2);

DROP TABLE IF EXISTS LogDetail;
CREATE TABLE LogDetail
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    log_id int,
    event_parameter_id int,
    value varchar(255)
);
INSERT INTO LogDetail
    (log_id, event_parameter_id, [value])
VALUES
    (1, 1, '1'),
    (2, 1, '1'),
    (3, 1, '1'),
    (4, 1, '1'),
    (5, 1, '1'),
    (1, 2, '20'),
    (2, 2, '30'),
    (3, 2, '40'),
    (4, 2, '50'),
    (5, 2, '60');


DROP TABLE IF EXISTS ChannelPrivateMember;
CREATE TABLE ChannelPrivateMember
(
    channel_id int,
    user_id int,
    role_id int DEFAULT null
);

DROP TABLE IF EXISTS [Message];
CREATE TABLE [Message]
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,

    sender_id int,
    content text,
    channel_id int DEFAULT null,
    dms_id int DEFAULT null,
    parent_id int DEFAULT null,
    meeting_id int DEFAULT null,
    status_id int DEFAULT null,
    send_at datetime,
    edited_at datetime DEFAULT null,
    is_deleted tinyInt DEFAULT 0,
    deleted_at datetime DEFAULT null
);
INSERT INTO [Message]
    (sender_id, content, channel_id, send_at)
VALUES
    (1, 'Message 1', 1, GETDATE()),
    (1, 'Message 2', 1, GETDATE()),
    (1, 'Message 3', 2, GETDATE()),
    (1, 'Message 4', 2, GETDATE());
INSERT INTO [Message]
    (sender_id, content, dms_id, send_at)
VALUES
    (1, 'Message 1', 1, GETDATE()),
    (1, 'Message 2', 1, GETDATE()),
    (1, 'Message 3', 2, GETDATE()),
    (1, 'Message 4', 2, GETDATE());

DROP TABLE IF EXISTS MessageMention;
CREATE TABLE MessageMention
(
    message_id int,
    user_id int
);
INSERT INTO MessageMention
    (message_id, user_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 2);

DROP TABLE IF EXISTS MessageAttachment;
CREATE TABLE MessageAttachment
(
    message_id int,
    asset_id int
);
INSERT INTO MessageAttachment
    (message_id, asset_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 2);

DROP TABLE IF EXISTS MessageReaction;
CREATE TABLE MessageReaction
(
    message_id int,
    emoji_id int,
    user_id int
);
INSERT INTO MessageReaction
    (message_id, emoji_id, user_id)
VALUES
    (1, 1, 1),
    (1, 2, 1),
    (2, 1, 1),
    (2, 2, 1);


DROP TABLE IF EXISTS BlockList;
CREATE TABLE BlockList
(
    user_id int,
    user_id_is_blocked int,
    dms_id int
);
INSERT INTO BlockList
    (user_id, user_id_is_blocked, dms_id)
VALUES
    (1, 2, 1),
    (1, 3, 1),
    (2, 3, 1),
    (2, 4, 1);

DROP TABLE IF EXISTS Feedback;
CREATE TABLE Feedback
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    user_id int,
    subsystem_id int,
    sender_email varchar(100),
    group_id int DEFAULT null,
    status_id int,
    content text,
    send_at datetime,
    updated_at datetime DEFAULT null
);
INSERT INTO Feedback
    (user_id, subsystem_id, sender_email, group_id, status_id, content, send_at)
VALUES
    (1, 1, 'test@gmail.com', 1, 2, 'Content 1', GETDATE()),
    (1, 2, 'test@gmail.com', 2, 1, 'Content 2', GETDATE()),
    (1, 1, 'test@gmail.com', 3, 1, 'Content 3', GETDATE()),
    (1, 1, 'test@gmail.com', 1, 3, 'Content 4', GETDATE());


DROP TABLE IF EXISTS FeedbackAttachment
CREATE TABLE FeedbackAttachment
(
    feedback_id int,
    asset_id int
);
INSERT INTO FeedbackAttachment
    (feedback_id, asset_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 2);



DROP TABLE IF EXISTS FeedbackAssignee;
CREATE TABLE FeedbackAssignee
(
    feedback_id int,
    assignee_id int,
    assign_at datetime,
    content text
);
INSERT INTO FeedbackAssignee
    (feedback_id, assignee_id, assign_at, content)
VALUES
    (1, 4, GETDATE(), 'Content 1');


DROP TABLE IF EXISTS FeedbackResult;
CREATE TABLE FeedbackResult
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    feedback_id int,
    content text,
    send_at datetime
);
INSERT INTO FeedbackResult
    (feedback_id, content, send_at)
VALUES
    (4, 'Content 1', GETDATE());



DROP TABLE IF EXISTS FeedbackResultReply;
CREATE TABLE FeedbackResultReply
(
    feedback_result_id int,
    replied_by int,
    replied_at datetime,
    content text
);


DROP TABLE IF EXISTS NavigationBar;
CREATE TABLE NavigationBar
(
    user_id int,
    feature_nav_bar int
);

DROP TABLE IF EXISTS NotificationSetting;
CREATE TABLE NotificationSetting
(
    preference_id int,
    notification_type_id int,
    enable tinyInt DEFAULT 1
);

DROP TABLE IF EXISTS Preference;
CREATE TABLE Preference
(
    id int PRIMARY KEY IDENTITY(1,1) NOT NULL,
    user_id int,
    dark_mode tinyInt DEFAULT 0,
    language_id int
);


ALTER TABLE Workspace ADD FOREIGN KEY (owner_id) REFERENCES [User] (id);

ALTER TABLE Channel ADD FOREIGN KEY (workspace_id) REFERENCES Workspace (id);

ALTER TABLE Meeting ADD FOREIGN KEY (owner_id) REFERENCES [User] (id);

ALTER TABLE DirectMessage ADD FOREIGN KEY (user1) REFERENCES [User] (id);

ALTER TABLE DirectMessage ADD FOREIGN KEY (user2) REFERENCES [User] (id);

ALTER TABLE MeetingParticipant ADD FOREIGN KEY (meeting_id) REFERENCES Meeting (id);

ALTER TABLE MeetingParticipant ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE MeetingParticipant ADD FOREIGN KEY (status_id) REFERENCES MeetingParticipantStatus (id);

ALTER TABLE NotificationQueue ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE NotificationQueue ADD FOREIGN KEY (notification_type_id) REFERENCES NotificationType (id);

ALTER TABLE Permission ADD FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE WorkspaceMember ADD FOREIGN KEY (workspace_id) REFERENCES Workspace (id);

ALTER TABLE WorkspaceMember ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE WorkspaceMember ADD FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE ChannelShared ADD FOREIGN KEY (channel_id) REFERENCES Channel (id);

ALTER TABLE ChannelShared ADD FOREIGN KEY (original_workspace_id) REFERENCES Workspace (id);

ALTER TABLE ChannelShared ADD FOREIGN KEY (target_workspace_id) REFERENCES Workspace (id);

ALTER TABLE PollQuestion ADD FOREIGN KEY (channel_id) REFERENCES Channel (id);

ALTER TABLE PollQuestion ADD FOREIGN KEY (created_by) REFERENCES [User] (id);

ALTER TABLE PollAnswer ADD FOREIGN KEY (question_id) REFERENCES PollQuestion (id);

ALTER TABLE PollAnswer ADD FOREIGN KEY (created_by) REFERENCES [User] (id);

ALTER TABLE PollVotingHistory ADD FOREIGN KEY (question_id) REFERENCES PollQuestion (id);

ALTER TABLE PollVotingHistory ADD FOREIGN KEY (answer_id) REFERENCES PollAnswer (id);

ALTER TABLE PollVotingHistory ADD FOREIGN KEY (voted_by) REFERENCES [User] (id);

ALTER TABLE log ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE log ADD FOREIGN KEY (event_type_id) REFERENCES EventType (id);

ALTER TABLE EventParameter ADD FOREIGN KEY (event_type_id) REFERENCES EventType (id);

ALTER TABLE LogDetail ADD FOREIGN KEY (log_id) REFERENCES log (id);

ALTER TABLE LogDetail ADD FOREIGN KEY (event_parameter_id) REFERENCES EventParameter (id);

ALTER TABLE ChannelPrivateMember ADD FOREIGN KEY (channel_id) REFERENCES Channel (id);

ALTER TABLE ChannelPrivateMember ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE ChannelPrivateMember ADD FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE [Message] ADD FOREIGN KEY (sender_id) REFERENCES [User] (id);

ALTER TABLE [Message] ADD FOREIGN KEY (channel_id) REFERENCES Channel (id);

ALTER TABLE [Message] ADD FOREIGN KEY (dms_id) REFERENCES DirectMessage (id);

ALTER TABLE [Message] ADD FOREIGN KEY (parent_id) REFERENCES [Message] (id);

ALTER TABLE [Message] ADD FOREIGN KEY (meeting_id) REFERENCES Meeting (id);

ALTER TABLE [Message] ADD FOREIGN KEY (status_id) REFERENCES StatusMessage(id);

ALTER TABLE MessageMention ADD FOREIGN KEY (message_id) REFERENCES [Message] (id);

ALTER TABLE MessageMention ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE MessageAttachment ADD FOREIGN KEY (message_id) REFERENCES [Message] (id);

ALTER TABLE MessageReaction ADD FOREIGN KEY (message_id) REFERENCES [Message] (id);

ALTER TABLE MessageReaction ADD FOREIGN KEY (emoji_id) REFERENCES Emoji (id);

ALTER TABLE MessageReaction ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE BlockList ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE BlockList ADD FOREIGN KEY (user_id_is_blocked) REFERENCES [User] (id);

ALTER TABLE BlockList ADD FOREIGN KEY (dms_id) REFERENCES DirectMessage (id);

ALTER TABLE Feedback ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE Feedback ADD FOREIGN KEY (subsystem_id) REFERENCES Subsystem (id);


ALTER TABLE Feedback ADD FOREIGN KEY (group_id) REFERENCES FeedbackGroup (id);

ALTER TABLE Feedback ADD FOREIGN KEY (status_id) REFERENCES FeedbackStatus (id);

ALTER TABLE FeedbackAssignee ADD FOREIGN KEY (feedback_id) REFERENCES Feedback (id);

ALTER TABLE FeedbackAssignee ADD FOREIGN KEY (assignee_id) REFERENCES [User] (id);

ALTER TABLE FeedbackResult ADD FOREIGN KEY (feedback_id) REFERENCES Feedback (id);

ALTER TABLE FeedbackResultReply ADD FOREIGN KEY (feedback_result_id) REFERENCES FeedbackResult (id);

ALTER TABLE FeedbackResultReply ADD FOREIGN KEY (replied_by) REFERENCES [User] (id);

ALTER TABLE NavigationBar ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE NavigationBar ADD FOREIGN KEY (feature_nav_bar) REFERENCES MainFeature (id);

ALTER TABLE NotificationSetting ADD FOREIGN KEY (preference_id) REFERENCES Preference (id);

ALTER TABLE NotificationSetting ADD FOREIGN KEY (notification_type_id) REFERENCES NotificationType (id);

ALTER TABLE Preference ADD FOREIGN KEY (user_id) REFERENCES [User] (id);

ALTER TABLE Preference ADD FOREIGN KEY (language_id) REFERENCES language (id);
