# 项目启动

##  前端项目启动（Vite + React + TypeScript）

### 安装依赖

在frontend/目录下打开终端，运行以下命令安装所有前端依赖：

```shell
yarn
```

### 配置环境变量

在前端项目根目录创建 `.env` 文件，用于配置后端API地址：

```plaintext
APP_API_URL=http://localhost:8080/api
```

### 启动开发服务器

运行以下命令启动Vite开发服务器：

```shell
yarn dev
```

- 默认访问地址：http://localhost:5173

### 生产构建
如果要生成生产环境静态资源文件，运行：

```shell
yarn build
```

- 构建后的文件将输出到dist/目录中，可用于部署到Nginx或其他静态服务器。

### 预览生产环境

在本地预览生产环境的页面：

```shell
yarn preview
```

## 后端项目启动（Spring Boot + PostgreSQL）

### 配置数据库

确保PostgreSQL服务已启动，并创建数据库：

```sql
CREATE DATABASE schedule_planner;
```

### 配置数据库连接
在resources/application-dev.yaml中配置数据库的连接信息：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/schedule_planner
    username: postgres
    password: your_password
```

### 安装Maven依赖

在后端项目根目录下运行以下命令，下载所有依赖：

```shell
mvn clean install
```

### 启动后端服务

运行以下命令启动Spring Boot后端服务：

```shell
mvn spring-boot:run
```

- 默认访问地址：http://localhost:8080

## 前后端联调

- 确保后端服务已启动，并在http://localhost:8080上正常运行。
- 确认前端.env文件中的API地址配置正确：

```plaintext
APP_API_URL=http://localhost:8080/api
```


- 打开浏览器访问：http://localhost:5173，测试前后端是否正常联通。

## 常见问题及解决方案

### 端口冲突

- 前端端口冲突：如果5173端口被占用，可以使用其他端口：

```shell
PORT=3001 yarn dev
```

- 后端端口冲突：如8080端口被占用，在`application.yaml`中修改端口：

```yaml
server:
  port: 8081
```


### 数据库连接失败

- 检查PostgreSQL服务是否已启动，并确保数据库schedule_planner已创建。
- 确认application-dev.yaml中的数据库用户名和密码正确无误。

### 前端依赖安装失败

- 尝试删除`node_modules`和`package-lock.json`后重新安装：

```shell
rm -rf node_modules package-lock.json
yarn
```

### 后端依赖安装失败

- 如果Maven依赖下载失败，尝试以下命令强制更新依赖：

```shell
mvn clean install -U
```

## 启动流程总结

- 启动PostgreSQL数据库，并创建数据库。
- 启动后端服务（Spring Boot），并确保运行在http://localhost:8080。
- 启动前端开发服务器（Vite），访问http://localhost:5173。
- 确保前后端API联通，完成功能测试。