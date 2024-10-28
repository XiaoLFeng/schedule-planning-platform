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
create table xf_group
(
    group_uuid    varchar(32)             not null
        constraint xf_group_pk
            primary key,
    name          varchar(30)             not null,
    master        varchar(36)             not null
        constraint xf_group_xf_user_uuid_fk
            references xf_user
            on update cascade on delete cascade,
    tags          varchar   default '[]'  not null,
    user_able_add boolean   default true  not null,
    created_at    timestamp default now() not null,
    updated_at    timestamp,
    deleted_at    timestamp
);

comment on table xf_group is '分组';
comment on column xf_group.group_uuid is '小组主键';
comment on column xf_group.name is '小组名字';
comment on column xf_group.master is '小组队长';
comment on column xf_group.tags is '类型（自定义输入，便于区分）';
comment on column xf_group.user_able_add is '其余用户允许添加日程';
comment on column xf_group.created_at is '创建时间';
comment on column xf_group.updated_at is '更新时间';
comment on column xf_group.deleted_at is '删除时间';
