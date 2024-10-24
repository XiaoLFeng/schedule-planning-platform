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
create table xf_schedule
(
    schedule_uuid varchar(32)                   not null
        constraint xf_schedule_pk
            primary key,
    user_uuid     varchar(36)
        constraint xf_schedule_xf_user_uuid_fk
            references xf_user
            on update cascade on delete cascade,
    group_uuid    varchar(32)
        constraint xf_schedule_xf_group_group_uuid_fk
            references xf_group
            on update cascade on delete cascade,
    name          varchar(64)                   not null,
    description   varchar,
    start_time    timestamp                     not null,
    end_time      timestamp                     not null,
    列_name_2     integer,
    type          smallint  default 0           not null,
    loop_type     smallint,
    custom_loop   integer,
    tags          varchar default '[]' not null,
    priority      smallint  default 1           not null,
    resources     varchar,
    created_at    timestamp default now()       not null,
    updated_at    timestamp
);

comment on table xf_schedule is '日程表';
comment on column xf_schedule.schedule_uuid is '日程主键';
comment on column xf_schedule.user_uuid is '用户主键';
comment on column xf_schedule.group_uuid is '小组主键';
comment on column xf_schedule.name is '日程名字';
comment on column xf_schedule.description is '描述';
comment on column xf_schedule.start_time is '开始时间';
comment on column xf_schedule.end_time is '结束时间';
comment on column xf_schedule.type is '类型（0: 单次任务，1: 循环任务，2: 一日任务）';
comment on column xf_schedule.loop_type is '循环类型（1: 每天登录，2: 每周, 3: 每个工作日, 4: 每个月 1 号，5: 每个月 14号，0: 自定义）';
comment on column xf_schedule.custom_loop is '自定义天数（多少天循环一次）';
comment on column xf_schedule.tags is '标签';
comment on column xf_schedule.priority is '优先级(1,2,3,4: 较低，低，中，高）';
comment on column xf_schedule.resources is '导入资源';
comment on column xf_schedule.created_at is '创建时间';
comment on column xf_schedule.updated_at is '修改时间';
