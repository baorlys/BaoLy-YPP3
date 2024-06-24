# DBML

```sql
Table meeting_participant {
  meeting_id int [default: null, ref: > meeting.id]
  user_id int [default: null, ref: > user_info.id]
  status_id int [default: null, ref: > meeting_status.id]
}

Table meeting {
  id int [pk, not null, increment]
  owner_id int [default: null, ref: > user_info.id]
  name varchar(255) [default: null]
  start_at timestamp [default: null]
  end_at timestamp [default: null]
}

Table meeting_status {
  id int [pk, not null, increment]
  name varchar(255) [default: null]
}





Table user_info {
  id int [pk, not null, increment]
}

Table message {
  id int [pk, not null, increment]
  sender_id int [default: null, ref: > user_info.id]
  status_id int [default: null, ref: > status_message.id]
  meeting_id int [default: null, ref: > meeting.id]
}

Table status_message {
  id int [pk, not null, increment]
}



```
