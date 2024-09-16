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
create table xf_mail_code
(
    code_uuid  varchar(32)             not null
        constraint xf_mail_code_pk
            primary key,
    mail       varchar(254)            not null,
    code       varchar(6)              not null,
    created_at timestamp default now() not null,
    expired_at timestamp               not null
);

comment on table xf_mail_code is '邮箱验证码';
comment on column xf_mail_code.code_uuid is '邮箱验证码主键';
comment on column xf_mail_code.mail is '邮箱';
comment on column xf_mail_code.code is '邮箱验证码';
comment on column xf_mail_code.created_at is '创建时间';
comment on column xf_mail_code.expired_at is '过期时间';

create unique index xf_mail_code_code_uindex
    on xf_mail_code (code);

create unique index xf_mail_code_mail_uindex
    on xf_mail_code (mail);

