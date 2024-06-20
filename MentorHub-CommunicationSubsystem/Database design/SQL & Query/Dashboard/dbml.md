# DBML

``` sql
Table channel {
  id int [pk, not null, increment]
  workspace_id int [default: null, ref: > workspace.id]
  name varchar(255) [default: null]
  isPrivate tinyint(1) [default: null]

  indexes {
    workspace_id
  }
}

Table direct_message {
  id int [pk, not null, increment]
  user1 int [default: null, ref: > user_info.id]
  user2 int [default: null, ref: > user_info.id]

  indexes {
    user1
    user2
  }
}

Table meeting_participant {
  meeting_id int [default: null, ref: > meeting.id]
  user_id int [default: null, ref: > user_info.id]
  status_id int [default: null, ref: > meeting_status.id]

  indexes {
    meeting_id
    user_id
    status_id
  }
}

Table meeting {
  id int [pk, not null, increment]
  owner_id int [default: null, ref: > user_info.id]
  name varchar(255) [default: null]
  start_at timestamp [default: null]
  end_at timestamp [default: null]

  indexes {
    owner_id
  }
}

Table meeting_status {
  id int [pk, not null, increment]
  name varchar(255) [default: null]
}

Table mention_list {
  message_id int [default: null, ref: > message.id]
  user_id int [default: null, ref: > user_info.id]

  indexes {
    message_id
    user_id
  }
}

Table message {
  id int [pk, not null, increment]
  sender_id int [default: null, ref: > user_info.id]
  content text
  channel_id int [default: null, ref: > channel.id]
  dms_id int [default: null, ref: > direct_message.id]
  status_id int [default: null, ref: > status_message.id]
  send_at timestamp [default: null]

  indexes {
    sender_id
    channel_id
    dms_id
    status_id
  }
}

Table notification_queue {
  id int [pk, not null, increment]
  user_id int [default: null, ref: > user_info.id]
  notification_type_id int [default: null, ref: > notification_type.id]
  content text
  isRead tinyint(1) [default: null]

  indexes {
    user_id
    notification_type_id
  }
}

Table notification_type {
  id int [pk, not null, increment]
  name varchar(255) [default: null]
}

Table status_message {
  id int [pk, not null, increment]
  name varchar(255) [default: null]
}

Table user_info {
  id int [pk, not null, increment]
  email varchar(255) [default: null]
  full_name varchar(255) [default: null]
  birthday date [default: null]
  last_active_at timestamp [default: null]
}

Table workspace {
  id int [pk, not null, increment]
  name varchar(255) [default: null]
  owner_id int [default: null, ref: > user_info.id]

  indexes {
    owner_id
  }
}

Table workspace_member {
  workspace_id int [default: null, ref: > workspace.id]
  user_id int [default: null, ref: > user_info.id]
  join_at timestamp [default: null]
  left_at timestamp [default: null]

  indexes {
    workspace_id
    user_id
  }
}

```

