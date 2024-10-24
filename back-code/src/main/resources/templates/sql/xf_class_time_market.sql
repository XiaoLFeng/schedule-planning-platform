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
create table xf_class_time_market
(
    class_time_market_uuid varchar(32)                   not null
        constraint xf_class_time_market_pk
            primary key,
    name                   varchar(64)                   not null,
    timetable              jsonb     default '[]'::jsonb not null,
    public                 boolean   default true        not null,
    official               boolean   default false       not null,
    created_at             timestamp default now()       not null,
    updated_at             timestamp
);

comment on table xf_class_time_market is '课表时间市场';
comment on column xf_class_time_market.class_time_market_uuid is '课表时间市场主键';
comment on column xf_class_time_market.name is '显示名字';
comment on column xf_class_time_market.timetable is '时间表';
comment on column xf_class_time_market.public is '是否公开';
comment on column xf_class_time_market.official is '该课表时间是否是官方可认证课表';
comment on column xf_class_time_market.created_at is '创建时间';
comment on column xf_class_time_market.updated_at is '修改时间';
