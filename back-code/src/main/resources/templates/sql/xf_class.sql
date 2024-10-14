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
create table xf_class
(
    class_uuid varchar(32)                   not null
        constraint xf_class_pk
            primary key,
    user_uuid  varchar(36)                   not null
        constraint xf_class_xf_user_uuid_fk
            references xf_user
            on update cascade on delete cascade,
    semester   varchar(48)                   not null,
    start_time date      default now()       not null,
    end_time   date                          not null,
    curriculum jsonb     default '[]'::jsonb not null,
    created_at timestamp default now()       not null,
    updated_at timestamp,
    deleted_at timestamp
);

comment on table xf_class is '课程表';
comment on column xf_class.class_uuid is '课程表主键';
comment on column xf_class.user_uuid is '用户主键';
comment on column xf_class.semester is '课程学期（名字）';
comment on column xf_class.start_time is '开始时间';
comment on column xf_class.end_time is '结束时间';
comment on column xf_class.curriculum is '课程信息';
comment on column xf_class.created_at is '创建时间';
comment on column xf_class.updated_at is '更新时间';
comment on column xf_class.deleted_at is '删除时间';
