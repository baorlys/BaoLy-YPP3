# DBML

```sql
Table role {
  id int [pk, not null, increment]
  name varchar(255) [default: null]
}


Table permission {
  id int [pk, not null, increment]
  role_id int [default: null, ref: > role.id]
  name varchar(255) [default: null]
  value int [default: null]
}




Table message_attachment {
  message_id int [default: null, ref: > message.id]
  access_id int 
}

Table emoji {
  id int [pk, not null, increment]
  unicode varchar(255) [default: null]
  name varchar(255) [default: null]
}

Table message_reaction {
  message_id int [default: null, ref: > message.id]
  emoji_id int [default: null, ref: > emoji.id]
  user_id int [default: null, ref: > user_info.id]
}

Table channel_shared {
  channel_id int [default: null, ref: > channel.id]
  original_workspace_id int [default: null, ref: > workspace.id]
  target_workspace_id int [default: null, ref: > workspace.id]
}


Table poll_question {
  id int [pk, not null, increment]
  question varchar(255) [default: null]
  channel_id int [default: null, ref: > channel.id]
  created_by int [default: null, ref: > user_info.id]
  created_at timestamp
}

Table poll_answer {
  id int [pk, not null, increment]
  question_id int [default: null, ref: > poll_question.id]
  answer varchar(255) [default: null]
  created_by int [default: null, ref: > user_info.id]
  created_at timestamp
}

Table poll_voting_history {
  question_id int [default: null, ref: > poll_question.id]
  answer_id int [default: null, ref: > poll_answer.id]
  voted_by int [default: null, ref: > user_info.id]
  voted_at timestamp
}

Table event_type {
  id int [pk, not null, increment]
  name varchar(255) [default: null]
}

Table log {
  id int [pk, not null, increment]
  user_id int [default: null, ref: > user_info.id]
  event_type_id int [default: null, ref: > event_type.id]
  logtime timestamp
}

Table event_parameter {
  id int [pk, not null, increment]
  name varchar(255)
  data_type varchar(255)
  event_type_id int [default: null, ref: > event_type.id]
}

Table log_detail {
  id int [pk, not null, increment]
  log_id int [default: null, ref: > log.id]
  event_parameter_id int [default: null, ref: > event_parameter.id]
  value varchar(255)
}




Table user_info {
  id int [pk, not null, increment]
}

Table message {
  id int [pk, not null, increment]
}

Table workspace {
  id int [pk, not null, increment]
}

Table channel {
  id int [pk, not null, increment]
}

```
