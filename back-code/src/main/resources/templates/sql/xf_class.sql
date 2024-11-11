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
    class_uuid       varchar(32)             not null
        constraint xf_class_pk
            primary key,
    class_grade_uuid varchar(32)             not null
        constraint xf_class_xf_class_grade_uuid_fk
            references xf_class_grade
            on update cascade on delete cascade,
    name             varchar(256)            not null,
    day_tick         smallint  default 1     not null,
    start_tick       smallint  default 0     not null,
    end_tick         smallint  default 1     not null,
    week            smallint  default 1     not null,
    teacher          varchar(128),
    location         varchar(512),
    created_at       timestamp default now() not null,
    updated_at       timestamp
);

comment on table xf_class is '课程表';
comment on column xf_class.class_uuid is '课程表主键';
comment on column xf_class.class_grade_uuid is '用户主键';
COMMENT ON COLUMN xf_class.name IS '课程名称';
COMMENT ON COLUMN xf_class.day_tick IS '课程上课的星期几';
COMMENT ON COLUMN xf_class.start_tick IS '课程开始的节次编号';
COMMENT ON COLUMN xf_class.end_tick IS '课程结束的节次编号';
COMMENT ON COLUMN xf_class.week IS '课程周次信息';
COMMENT ON COLUMN xf_class.teacher IS '授课老师的名字';
COMMENT ON COLUMN xf_class.location IS '上课地点';
COMMENT ON COLUMN xf_class.created_at IS '记录创建时间';
COMMENT ON COLUMN xf_class.updated_at IS '记录更新时间';
