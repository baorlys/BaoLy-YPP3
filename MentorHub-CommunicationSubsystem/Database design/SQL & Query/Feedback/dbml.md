# DBML

```sql
Table feedback_group {
  id int [pk, not null, increment]
  name varchar(255)
}

Table feedback_status {
  id int [pk, not null, increment]
  name varchar(255)
}

Table feedback {
  id int [pk, not null, increment]
  user_id int [ref: > user_info.id]
  group_id int [default: null, ref: > feedback_group.id]
  status_id int [ref: > feedback_status.id]
  content text
  send_at timestamp
  sender_email varchar(100)
}


Table feedback_assignee {
  feedback_id int [ref: > feedback.id]
  assignee_id int [ref: > user_info.id]
  assign_at timestamp
  message text
}


Table feedback_result {
  id int [pk, not null, increment]
  feedback_id int [ref: > feedback.id]
  content text
  send_at timestamp
}

Table feedback_result_reply {
  feedback_result_id int [ref: > feedback_result.id]
  replied_by int [ref: > user_info.id]
  replied_at timestamp
  content text
}






Table user_info {
  id int [pk, not null, increment]
  role_id int [default: null, ref: > role.id]
}

Table role {
  id int [pk, not null, increment]
}


Table permission {
  id int [pk, not null, increment]
  role_id int [default: null, ref: > role.id]
  name varchar(255) [default: null]
  value int [default: null]
}




```