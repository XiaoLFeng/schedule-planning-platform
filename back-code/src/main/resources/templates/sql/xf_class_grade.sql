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
create table xf_class_grade
(
    class_grade_uuid varchar(32)             not null
        constraint xf_class_grade_pk
            primary key,
    user_uuid        varchar(36)             not null
        constraint xf_class_grade_xf_user_uuid_fk
            references xf_user
            on update cascade on delete cascade,
    semester_begin   date                    not null,
    semester_end     date,
    nickname         varchar(64)             not null,
    created_at       timestamp default now() not null,
    updated_at       timestamp
);

comment on table xf_class_grade is '课程表学年';
comment on column xf_class_grade.class_grade_uuid is '课程表学年主键';
comment on column xf_class_grade.user_uuid is '用户主键';
comment on column xf_class_grade.semester_begin is '学期开始（需要存储时间为第一周的周一）';
comment on column xf_class_grade.semester_end is '学期结束';
comment on column xf_class_grade.nickname is '别名';
comment on column xf_class_grade.created_at is '创建时间';
comment on column xf_class_grade.updated_at is '更新时间';
