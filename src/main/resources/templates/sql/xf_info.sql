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
create table xf_info
(
    info_uuid  varchar(36)             not null
        constraint xf_info_pk
            primary key,
    key        varchar(128)            not null,
    value      varchar,
    updated_at timestamp default now() not null
);

comment on table xf_info is '信息表';
comment on column xf_info.info_uuid is '信息表主键';
comment on column xf_info.key is '键值对-键';
comment on column xf_info.value is '键值对-值';
comment on column xf_info.updated_at is '修改时间';

create unique index xf_info_key_uindex
    on xf_info (key);

