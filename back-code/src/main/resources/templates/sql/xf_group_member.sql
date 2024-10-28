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
create table xf_group_member
(
    group_member_uuid varchar(32)             not null
        constraint xf_group_member_pk
            primary key,
    group_uuid        varchar(32)             not null
        constraint xf_group_member_xf_group_group_uuid_fk
            references xf_group
            on update cascade on delete cascade,
    user_uuid         varchar(36)             not null
        constraint xf_group_member_xf_user_uuid_fk
            references xf_user
            on update cascade on delete cascade,
    status            smallint  default 0     not null,
    created_at        timestamp default now() not null,
    updated_at        timestamp
);

comment on table xf_group_member is '小组成员';
comment on column xf_group_member.group_member_uuid is '小组成员主键';
comment on column xf_group_member.group_uuid is '小组主键';
comment on column xf_group_member.user_uuid is '用户主键';
comment on column xf_group_member.status is '状态（0: 等待同意，1: 用户同意，2: 用户拒绝）';
comment on column xf_group_member.created_at is '创建时间';
comment on column xf_group_member.updated_at is '更新时间';

create unique index xf_group_member_group_uuid_user_uuid_uindex
    on xf_group_member (group_uuid, user_uuid);
