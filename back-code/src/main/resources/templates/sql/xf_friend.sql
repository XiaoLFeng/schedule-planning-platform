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
create table xf_friend
(
    friend_uuid       varchar(32)             not null
        constraint xf_friend_pk
            primary key,
    sender_user_uuid  varchar(36)             not null
        constraint xf_friend_xf_user_uuid_fk_2
            references xf_user
            on update cascade on delete cascade,
    allower_user_uuid varchar(36)             not null
        constraint xf_friend_xf_user_uuid_fk
            references xf_user
            on update cascade on delete cascade,
    sender_remarks    varchar(128),
    allower_remarks   varchar(128),
    is_friend         smallint  default 0     not null,
    sent_at         timestamp default now() not null,
    created_at        timestamp default now() not null,
    updated_at        timestamp
);


comment on table xf_friend is '好友信息';
comment on column xf_friend.friend_uuid is '好友主键';
comment on column xf_friend.sender_user_uuid is '发送好友请求用户主键';
comment on column xf_friend.allower_user_uuid is '接受好友主键';
comment on column xf_friend.sender_remarks is '发送请求用户对接受用户的好友名字备注';
comment on column xf_friend.allower_remarks is '接受方对请求方的好友备注';
comment on column xf_friend.is_friend is '成为了好友(0: 等待审核, 1: 成为了好友, 2: 好友申请被拒绝)';
comment on column xf_friend.sent_at is '发送时间';
comment on column xf_friend.created_at is '创建时间';
comment on column xf_friend.updated_at is '更新时间';

create unique index xf_friend_sender_user_uuid_allower_user_uuid_uindex
    on xf_friend (sender_user_uuid, allower_user_uuid);

