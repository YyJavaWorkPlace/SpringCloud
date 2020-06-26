drop table if exists `chapter`;
create table `chapter`(
    `id` char(8) not null comment 'ID',
    `course_id` char(8) comment '课程ID',
    `name` varchar(50) comment '名称',
    primary key (`id`)
)engine = innodb default charset = utf8mb4 comment ='课程大章';


insert into `chapter` (id,course_id,name) values ('00000003','00000003','测试章节03');
insert into `chapter` (id,course_id,name) values ('00000004','00000004','测试章节04');
insert into `chapter` (id,course_id,name) values ('00000005','00000005','测试章节05');
insert into `chapter` (id,course_id,name) values ('00000006','00000006','测试章节06');
insert into `chapter` (id,course_id,name) values ('00000007','00000007','测试章节07');
insert into `chapter` (id,course_id,name) values ('00000008','00000008','测试章节08');
insert into `chapter` (id,course_id,name) values ('00000009','00000009','测试章节09');
insert into `chapter` (id,course_id,name) values ('00000010','00000010','测试章节10');
insert into `chapter` (id,course_id,name) values ('00000011','00000011','测试章节11');




# 测试
drop table if exists `test`;
create table `test`(
    `id` char(8) not null default '' comment 'id',
    `name` varchar(50) comment '名称',
    primary key (`id`)
)engine=innodb default  charset=utf8mb4 comment ='测试';
insert  into `test` (id,name) value (1,'测试');
insert  into `test` (id,name) value (2,'测试');