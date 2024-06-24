# DBML

```sql
Table block_list {
  user_id int [default: null, ref: > user_info.id]
  user_id_is_blocked int [ref: > user_info.id]
  dms_id int [ref: > direct_message.id]
}


Table direct_message {
  id int [pk, not null, increment]
  user1 int [default: null, ref: > user_info.id]
  user2 int [default: null, ref: > user_info.id]
}

Table message_attachment {
  message_id int [default: null, ref: > message.id]
  access_id int 
}


Table message_reaction {
  message_id int [default: null, ref: > message.id]
  emoji_id int [default: null, ref: > emoji.id]
  user_id int [default: null, ref: > user_info.id]
}



Table user_info {
  id int [pk, not null, increment]
}

Table message {
  id int [pk, not null, increment]
    sender_id int [default: null, ref: > user_info.id]
  status_id int [default: null, ref: > status_message.id]
}

Table status_message {
  id int [pk, not null, increment]
}


Table emoji {
  id int [pk, not null, increment]
}

```