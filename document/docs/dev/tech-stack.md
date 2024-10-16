# 技术栈

## 前端技术栈

- 核心框架
- React 18：用于构建用户界面，支持组件化开发。
- TypeScript：为JavaScript提供静态类型支持，提升代码的可维护性和开发体验。
- Vite：快速开发和构建工具，优化资源加载和开发速度。
- 路由与状态管理
- React Router（可选）：用于前端路由管理，实现页面跳转。
- HTTP请求与API管理
- Axios：用于发起HTTP请求，与后端服务交互。
- CSS与样式处理
- Tailwind CSS：实用性优先的CSS框架，用于快速构建响应式页面。
- PostCSS：支持CSS的预处理和自动添加浏览器前缀。
- 开发和代码质量管理
- ESLint + eslint-plugin-react-hooks：用于代码格式检查，确保React组件的最佳实践。
- TypeScript ESLint：用于TS项目的代码检查和规范管理。
- 构建与开发工具
- Vite：支持开发服务器和生产构建。
- vite.config.ts：配置文件用于自定义Vite的行为。

## 后端技术栈

- 核心框架与语言
- Java 17：用于开发后端逻辑。
- Spring Boot 3：构建后端RESTful API服务，简化配置和开发。
- 数据库与持久化
- PostgreSQL：关系型数据库，用于存储用户、日程和课程数据。
- Spring Data JPA：简化数据库操作，与PostgreSQL集成。
- Spring Boot Starter JDBC：用于执行原生SQL查询。
- 缓存
- Redis：用于存储会话数据和缓存，提升系统性能。
- 邮件服务
- Spring Boot Starter Mail：实现邮件发送功能（如用户通知、日程提醒）。
- 认证与安全
- Spring Security + JWT：实现身份认证和权限管理。
- Thymeleaf + Spring Security Extras：在视图中集成权限控制。
- 业务支持与工具
- Lombok：减少Java代码中的样板代码（如getter/setter）。
- AOP（Aspect-Oriented Programming）：用于日志和权限控制。
- com.x-lf.utility.general-utils：自定义工具库，用于辅助后端开发。
- 测试框架
- JUnit 5 + Spring Security Test：用于单元测试和集成测试。

## 数据库与缓存

- PostgreSQL：关系型数据库，用于存储用户、日程、课程表等数据。

## 开发和部署工具

- 版本控制
- Git + GitHub：用于版本控制和代码托管。
- 构建工具与依赖管理
- Maven：管理后端依赖，并执行构建任务。
- 容器化与部署
- Docker：用于容器化部署，保证环境一致性。
- Nginx：作为反向代理服务器，支持静态资源托管和API请求转发。
- 持续集成与部署（CI/CD）
- GitHub Actions / Jenkins：实现自动化构建、测试和部署。

## 脚本与配置管理

- 前端脚本

```json
"scripts": {
  "dev": "vite",
  "build": "tsc -b && vite build",
  "lint": "eslint .",
  "preview": "vite preview"
}
```

- dev：启动开发服务器。
- build：构建生产环境资源。
- lint：运行ESLint检查代码格式。
- preview：预览生产构建的页面。

- 后端依赖管理（Maven示例）

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

## 总结

|   层级   | 技术栈                            |
| :------: | --------------------------------- |
|   前端   | React, Vite, Tailwind CSS, Axios  |
|   后端   | Spring Boot, Redis, JPA, Lombok   |
|  数据库  | PostgreSQL, Redis                 |
|   部署   | Docker, Nginx, GitHub Actions     |
| 开发工具 | Git, Maven, IntelliJ IDEA, Apifox |

这套技术栈确保系统在开发、测试、部署各个阶段的高效运行，并为前后端的协同开发和集成提供了良好的支持。