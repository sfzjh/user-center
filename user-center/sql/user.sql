create table user
(
    id          bigint primary key,                not null comment 'id',
    nickname    varchar(256)                       null comment '用户昵称',
    username    varchar(256)                       null comment '账号',
    avatar_url  varchar(1024)                      null comment '用户头像',
    gender      tinyint                            null comment '性别',
    password    varchar(512)                       null comment '密码',
    phone       varchar(128)                       null comment '电话',
    email       varchar(512)                       null comment '邮箱',
    user_status int      default 0                 not null comment '状态 0 - 正常',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    is_delete   tinyint  default 0                 not null comment '是否删除',
    user_role   int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    planet_code varchar(512)                       null comment '星球编号'
)
    comment '用户表';


INSERT INTO yupi.user (id, nickname, username, avatar_url, gender, password, phone, email, user_status, create_time, update_time, is_delete, user_role, planet_code) VALUES (3688177892430184448, '鱼皮', 'yupi', 'https://himg.bdimg.com/sys/portraitn/item/public.1.e137c1ac.yS1WqOXfSWEasOYJ2-0pvQ', null, 'b0dd3697a192885d7c055db46155b26a', null, null, 0, '2023-08-06 14:14:22', '2023-08-06 14:39:37', 0, 1, '1');

