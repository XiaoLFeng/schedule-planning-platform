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
create table xf_logs
(
    log_uuid   varchar(32)             not null
        constraint xf_logs_pk
            primary key,
    type       smallint  default 0     not null,
    business   varchar(16)             not null,
    "user"     varchar(36),
    value      varchar                 not null,
    created_at timestamp default now() not null
);

comment on table xf_logs is '日志数据表';
comment on column xf_logs.log_uuid is '日志表主键';
comment on column xf_logs.type is '日志类型';
comment on column xf_logs.business is '业务类型';
comment on column xf_logs."user" is '执行用户（可为空）';
comment on column xf_logs.value is '日志内容';
comment on column xf_logs.created_at is '日志创建时间';
