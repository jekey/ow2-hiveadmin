create database if not exists hiveadmin;

create table if not exists hiveadmin.user
	(username varchar(30) primary key,
	userpass varchar(30));

create table if not exists  hiveadmin.op_record
	(op_record_id int auto_increment primary key,
	op_user_name varchar(30) not null,
	op_desc text not null,
	op_sql text not null,
	op_res boolean,
	op_ts timestamp);

create table if not exists hiveadmin.template
	(temp_name varchar(60) primary key,
	owner_name varchar(30),
	temp_description text,
	temp_content text not null,
	temp_ts timestamp default current_timestamp);
