# 数据库文档

## 数据库关系

![image](../assets/database-image.png)



## 数据库表信息

### xf_info

| Column Name | Data Type    | Primary Key | Foreign Key | Extra                   |
| ----------- | ------------ | ----------- | ----------- | ----------------------- |
| info_uuid   | varchar(36)  | Yes         | No          | not null                |
| key         | varchar(128) | No          | No          | not null                |
| value       | varchar      | No          | No          |                         |
| updated_at  | timestamp    | No          | No          | default now(), not null |

#### Indexes

| Index Name         | Column | Type   |
| ------------------ | ------ | ------ |
| xf_info_key_uindex | key    | unique |

#### Comments

- Table: 信息表
- Column `info_uuid`: 信息表主键
- Column `key`: 键值对-键
- Column `value`: 键值对-值
- Column `updated_at`: 修改时间

### xf_logs

| Column Name | Data Type   | Primary Key | Foreign Key | Extra                   |
| ----------- | ----------- | ----------- | ----------- | ----------------------- |
| log_uuid    | varchar(32) | Yes         | No          | not null                |
| type        | smallint    | No          | No          | default 0, not null     |
| business    | varchar(16) | No          | No          | not null                |
| user        | varchar(36) | No          | No          |                         |
| value       | varchar     | No          | No          | not null                |
| created_at  | timestamp   | No          | No          | default now(), not null |

#### Comments

- Table: 日志数据表
- Column `log_uuid`: 日志表主键
- Column `type`: 日志类型
- Column `business`: 业务类型
- Column `user`: 执行用户（可为空）
- Column `value`: 日志内容
- Column `created_at`: 日志创建时间

### xf_mail_code

| Column Name | Data Type    | Primary Key | Foreign Key | Extra                   |
| ----------- | ------------ | ----------- | ----------- | ----------------------- |
| code_uuid   | varchar(32)  | Yes         | No          | not null                |
| mail        | varchar(254) | No          | No          | not null                |
| code        | varchar(6)   | No          | No          | not null                |
| created_at  | timestamp    | No          | No          | default now(), not null |
| expired_at  | timestamp    | No          | No          | not null                |

#### Indexes

| Index Name               | Column | Type   |
| ------------------------ | ------ | ------ |
| xf_mail_code_code_uindex | code   | unique |
| xf_mail_code_mail_uindex | mail   | unique |

#### Comments

- Table: 邮箱验证码
- Column `code_uuid`: 邮箱验证码主键
- Column `mail`: 邮箱
- Column `code`: 邮箱验证码
- Column `created_at`: 创建时间
- Column `expired_at`: 过期时间

### xf_role

| Column Name  | Data Type    | Primary Key | Foreign Key | Extra                   |
| ------------ | ------------ | ----------- | ----------- | ----------------------- |
| role_uuid    | varchar(36)  | Yes         | No          | not null                |
| name         | varchar(36)  | No          | No          | not null                |
| display_name | varchar(36)  | No          | No          | not null                |
| role_desc    | varchar(256) | No          | No          | not null                |
| created_at   | timestamp    | No          | No          | default now(), not null |

#### Indexes

| Index Name          | Column | Type   |
| ------------------- | ------ | ------ |
| xf_role_name_uindex | name   | unique |

#### Comments

- Table: 用户角色表
- Column `role_uuid`: 角色主键
- Column `name`: 角色名字
- Column `display_name`: 角色名字展示
- Column `role_desc`: 角色信息描述
- Column `created_at`: 创建时间

### xf_token

| Column Name       | Data Type     | Primary Key | Foreign Key | Extra                   |
| ----------------- | ------------- | ----------- | ----------- | ----------------------- |
| token_uuid        | varchar(36)   | Yes         | No          | not null                |
| user_uuid         | varchar(36)   | No          | Yes         | not null                |
| created_at        | timestamp     | No          | No          | default now(), not null |
| expired_at        | timestamp     | No          | No          | not null                |
| client_ip         | varchar(39)   | No          | No          | not null                |
| client_referer    | varchar(1024) | No          | No          |                         |
| client_user_agent | varchar       | No          | No          | not null                |

#### Foreign Keys

| Column    | References Table | References Column |
| --------- | ---------------- | ----------------- |
| user_uuid | xf_user          | uuid              |

#### Comments

- Table: 令牌
- Column `token_uuid`: 令牌
- Column `user_uuid`: 用户主键
- Column `created_at`: 创建时间
- Column `expired_at`: 过期时间
- Column `client_ip`: 客户端登录时 IP
- Column `client_referer`: 客户端 Referer
- Column `client_user_agent`: 客户端用户信息

### xf_user

| Column Name  | Data Type    | Primary Key | Foreign Key | Extra                   |
| ------------ | ------------ | ----------- | ----------- | ----------------------- |
| uuid         | varchar(36)  | Yes         | No          | not null                |
| username     | varchar(36)  | No          | No          |                         |
| phone        | varchar(11)  | No          | No          | not null                |
| email        | varchar(254) | No          | No          |                         |
| password     | char(60)     | No          | No          | not null                |
| old_password | char(60)     | No          | No          |                         |
| role         | varchar(36)  | No          | Yes         | not null                |
| created_at   | timestamp    | No          | No          | default now(), not null |
| updated_at   | timestamp    | No          | No          | default now(), not null |
| enable       | boolean      | No          | No          | default true, not null  |
| banned_at    | timestamp    | No          | No          |                         |

#### Foreign Keys

| Column | References Table | References Column |
| ------ | ---------------- | ----------------- |
| role   | xf_role          | uuid              |

#### Indexes

| Index Name              | Column   | Type   |
| ----------------------- | -------- | ------ |
| xf_user_email_index     | email    | index  |
| xf_user_phone_uindex    | phone    | unique |
| xf_user_username_uindex | username | unique |

#### Comments

- Table: 用户表
- Column `uuid`: 角色主键
- Column `username`: 用户名
- Column `phone`: 手机号
- Column `email`: 用户邮箱
- Column `password`: 用户当前密码
- Column `old_password`: 用户旧密码
- Column `created_at`: 创建时间
- Column `updated_at`: 修改时间
- Column `enable`: 用户是否开启
- Column `banned_at`: 封禁到

## xf_group

| Column Name | Data Type   | Primary Key | Foreign Key | Extra                         |
| ----------- | ----------- | ----------- | ----------- | ----------------------------- |
| group_uuid  | varchar(32) | Yes         | No          | not null                      |
| name        | varchar(30) | No          | No          | not null                      |
| master      | varchar(36) | No          | Yes         | not null                      |
| tags        | jsonb       | No          | No          | default '[]'::jsonb, not null |
| created_at  | timestamp   | No          | No          | default now(), not null       |
| updated_at  | timestamp   | No          | No          |                               |
| deleted_at  | timestamp   | No          | No          |                               |

### Foreign Keys

| Column | References Table | References Column |
| ------ | ---------------- | ----------------- |
| master | xf_user          | uuid              |

### Comments

- Table: 分组
- Column `group_uuid`: 小组主键
- Column `name`: 小组名字
- Column `master`: 小组队长
- Column `tags`: 类型（自定义输入，便于区分）
- Column `created_at`: 创建时间
- Column `updated_at`: 更新时间
- Column `deleted_at`: 删除时间

## xf_group_member

| Column Name       | Data Type   | Primary Key | Foreign Key | Extra                   |
| ----------------- | ----------- | ----------- | ----------- | ----------------------- |
| group_member_uuid | varchar(32) | Yes         | No          | not null                |
| group_uuid        | varchar(32) | No          | Yes         | not null                |
| user_uuid         | varchar(36) | No          | Yes         | not null                |
| status            | smallint    | No          | No          | default 0, not null     |
| created_at        | timestamp   | No          | No          | default now(), not null |
| updated_at        | timestamp   | No          | No          |                         |

### Foreign Keys

| Column     | References Table | References Column |
| ---------- | ---------------- | ----------------- |
| group_uuid | xf_group         | group_uuid        |
| user_uuid  | xf_user          | uuid              |

### Comments

- Table: 小组成员
- Column `group_member_uuid`: 小组成员主键
- Column `group_uuid`: 小组主键
- Column `user_uuid`: 用户主键
- Column `status`: 状态（0: 等待同意，1: 用户同意，2: 用户拒绝）
- Column `created_at`: 创建时间
- Column `updated_at`: 更新时间

## 建表文件

[Github 建表文件](https://github.com/XiaoLFeng/schedule-planning-platform/tree/master/src/main/resources/templates/sql)