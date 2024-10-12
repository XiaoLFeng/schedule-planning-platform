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
create table xf_token
(
    token_uuid        varchar(36)             not null
        constraint xf_token_pk
            primary key,
    user_uuid         varchar(36)             not null
        constraint xf_token_xf_user_uuid_fk
            references xf_user
            on update cascade on delete cascade,
    created_at        timestamp default now() not null,
    expired_at        timestamp               not null,
    client_ip         varchar(39)             not null,
    client_referer    varchar(1024),
    client_user_agent varchar                 not null
);

comment on table xf_token is '令牌';
comment on column xf_token.token_uuid is '令牌';
comment on column xf_token.user_uuid is '用户主键';
comment on column xf_token.created_at is '创建时间';
comment on column xf_token.expired_at is '过期时间';
comment on column xf_token.client_ip is '客户端登录时 IP';
comment on column xf_token.client_referer is '客户端 Referer';
comment on column xf_token.client_user_agent is '客户端用户信息';
