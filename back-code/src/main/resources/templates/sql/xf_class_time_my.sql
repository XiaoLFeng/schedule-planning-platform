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
create table xf_class_time_my
(
    class_time_my_uuid varchar(32)             not null
        constraint xf_class_time_my_pk
            primary key,
    user_uuid          varchar(36)             not null
        constraint xf_class_time_my_xf_user_uuid_fk
            references xf_user
            on update cascade on delete cascade,
    time_market_uuid   varchar(32)             not null
        constraint xf_class_time_my_xf_class_time_market_class_time_market_uuid_fk
            references xf_class_time_market
            on update cascade on delete cascade,
    created_at         timestamp default now() not null
);

comment on table xf_class_time_my is '我的课表时间';
comment on column xf_class_time_my.class_time_my_uuid is '我的课表主键';
comment on column xf_class_time_my.user_uuid is '用户主键';
comment on column xf_class_time_my.time_market_uuid is '课表市场主键';
comment on column xf_class_time_my.created_at is '添加时间';
