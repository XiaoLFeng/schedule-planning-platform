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
create table xf_role
(
    role_uuid    varchar(36)             not null
        constraint xf_role_pk
            primary key,
    name         varchar(36)             not null,
    display_name varchar(36)             not null,
    role_desc    varchar(256)            not null,
    created_at   timestamp default now() not null
);

comment on table xf_role is '用户角色表';
comment on column xf_role.role_uuid is '角色主键';
comment on column xf_role.name is '角色名字';
comment on column xf_role.display_name is '角色名字展示';
comment on column xf_role.role_desc is '角色信息描述';
comment on column xf_role.created_at is '创建时间';

create unique index xf_role_name_uindex
    on xf_role (name);

