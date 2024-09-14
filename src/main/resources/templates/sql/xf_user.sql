/*
 * ***************************************************************************************
 * author: XiaoLFeng(https://www.x-lf.com)
 * about:
 *   The project contains the source code of com.xlf.schedule.
 *   All source code for this project is licensed under the MIT open source license.
 * licenseStatement:
 *   Copyright (c) 2016-2024 XiaoLFeng. All rights reserved.
 *   For more information about the MIT license, please view the LICENSE file
 *     in the project root directory or visit:
 *   https://opensource.org/license/MIT
 * disclaimer:
 *   Since this project is in the model design stage, we are not responsible for any losses
 *     caused by using this project for commercial purposes.
 *   If you modify the code and redistribute it, you need to clearly indicate what changes
 *     you made in the corresponding file.
 *   If you want to modify it for commercial use, please contact me.
 * ***************************************************************************************
 */

-- auto-generated definition
create table xf_user
(
    uuid         varchar(36)             not null
        constraint xf_user_pk
            primary key,
    username     varchar(36),
    phone        varchar(11)             not null,
    email        varchar(254),
    password     char(60)                not null,
    old_password char(60),
    role         varchar(36)             not null
        constraint xf_user_role_fk
            references xf_role
            on update restrict on delete restrict,
    created_at   timestamp default now() not null,
    updated_at   timestamp default now() not null,
    enable       boolean   default true  not null,
    banned_at    timestamp
);

comment on table xf_user is '用户表';
comment on column xf_user.uuid is ' 角色主键';
comment on column xf_user.username is '用户名';
comment on column xf_user.phone is '手机号';
comment on column xf_user.email is '用户邮箱';
comment on column xf_user.password is '用户当前密码';
comment on column xf_user.old_password is '用户旧密码';
comment on column xf_user.created_at is '创建时间';
comment on column xf_user.updated_at is '修改时间';
comment on column xf_user.enable is '用户是否开启';
comment on column xf_user.banned_at is '封禁到';

create index xf_user_email_index
    on xf_user (email);

create unique index xf_user_phone_uindex
    on xf_user (phone);

create unique index xf_user_username_uindex
    on xf_user (username);

