# 🚀 Quick Start Guide

## 快速启动图书馆管理系统

---

## ✅ 前置条件检查

在开始之前，确保你已经：

- [x] 安装了 XAMPP（包含 MySQL 和 phpMyAdmin）
- [x] 导入了数据库（`database/library_management.sql`）
- [x] 修改了 `persistence.xml` 中的数据库密码
- [x] 项目已经打包成功（`mvn clean install`）

---

## 📦 现在需要做的：部署应用

### 选项 A：在 IDEA 中配置 Tomcat（推荐）

#### 1. 下载 Apache Tomcat

1. 访问：https://tomcat.apache.org/download-10.cgi
2. 下载 **"64-bit Windows zip"**
3. 解压到 `C:\tomcat` 或任意位置

#### 2. 在 IDEA 中添加 Tomcat

1. 点击 IDEA 右上角 **"Add Configuration..."**
2. 点击左上角 **"+"** → 选择 **"Tomcat Server"** → **"Local"**
3. 配置：
   ```
   Name: Tomcat 10
   Application server: 点击 "Configure..." 
   → 选择 Tomcat 解压目录 (C:\tomcat)
   → 点击 OK
   ```

#### 3. 配置 Deployment

1. 在同一窗口，点击 **"Deployment"** 标签
2. 点击 **"+"** → 选择 **"Artifact..."**
3. 选择 **"library-management-system:war exploded"**
4. Application context 设置为：`/library-management-system`
5. 点击 **"Apply"** 和 **"OK"**

#### 4. 启动应用

1. 点击右上角的 **绿色播放按钮 ▶️**
2. 等待 Tomcat 启动（底部控制台会显示日志）
3. 浏览器会自动打开：`http://localhost:8080/library-management-system/`

#### 5. 如果遇到端口占用

如果 8080 端口被占用：
1. 在 Run Configuration 中
2. 切换到 **"Server"** 标签
3. 修改 **HTTP port** 为 `8090` 或其他端口
4. 重新启动

---

### 选项 B：手动部署到 Tomcat

#### 1. 启动 XAMPP MySQL

1. 打开 XAMPP Control Panel
2. 确保 **MySQL** 是绿色（正在运行）

#### 2. 部署 WAR 文件

1. 找到打包好的文件：
   ```
   D:\Project\LibararyManageSystem\target\library-management-system.war
   ```

2. 复制到 Tomcat webapps 目录：
   ```
   C:\tomcat\webapps\library-management-system.war
   ```

#### 3. 启动 Tomcat

打开命令提示符（CMD）：
```bash
cd C:\tomcat\bin
startup.bat
```

等待 Tomcat 自动解压 WAR 文件。

#### 4. 访问应用

打开浏览器，访问：
```
http://localhost:8080/library-management-system/
```

---

## 🧪 测试系统功能

### 1. 测试登录

使用默认账号登录：

#### 👤 普通用户
```
Username: user1
Password: admin123
```
**可以做：**
- 浏览图书
- 借阅图书
- 查看我的借阅
- 归还图书

#### 👨‍🏫 讲师账号
```
Username: instructor1
Password: admin123
```
**可以做：**
- 所有用户功能
- 为他人借书
- 处理还书
- 查看所有借阅记录
- 管理逾期图书

#### 👨‍💼 管理员账号
```
Username: admin
Password: admin123
```
**可以做：**
- 所有讲师功能
- 添加/编辑/删除图书
- 管理用户
- 查看统计数据
- 完全控制系统

---

### 2. 功能测试流程

#### 测试 A：普通用户借书

1. 用 `user1` 登录
2. 点击导航栏的 **"Books"**
3. 搜索 "Harry Potter"
4. 点击书籍卡片查看详情
5. 点击 **"Borrow This Book"**
6. 看到成功提示
7. 点击导航栏的 **"My Borrowings"**
8. 应该看到刚借的书

#### 测试 B：归还图书

1. 在 **"My Borrowings"** 页面
2. 点击借阅记录的 **"Return Book"** 按钮
3. 确认归还
4. 看到成功提示（如果逾期会显示罚金）

#### 测试 C：管理员添加图书

1. 用 `admin` 登录
2. 点击导航栏的 **"Admin"**
3. 点击 **"Books Management"** 标签
4. 点击 **"Add Book"** 按钮
5. 填写图书信息：
   ```
   ISBN: 978-1234567890
   Title: Test Book
   Total Copies: 5
   ```
6. 点击 **"Save Book"**
7. 应该在列表中看到新书

#### 测试 D：讲师为他人借书

1. 用 `instructor1` 登录
2. 进入 **"Admin"** → **"Books Management"**
3. 找到一本可借的书
4. 点击 **"Issue"** 按钮
5. 输入用户信息
6. 设置借阅天数（如 14 天）
7. 点击 **"Issue Book"**

#### 测试 E：查看逾期图书

1. 用管理员或讲师账号登录
2. 进入 **"Admin"** → **"Overdue Books"** 标签
3. 查看所有逾期的借阅记录
4. 点击 **"Process Return"** 处理归还
5. 系统会自动计算罚金（$1/天）

---

## 🎯 各角色功能导航

### 👤 USER (普通用户)

**可访问页面：**
- ✅ Home - 首页
- ✅ Books - 浏览图书
- ✅ My Borrowings - 我的借阅

**主要功能：**
1. 浏览所有图书
2. 搜索图书
3. 查看图书详情
4. 借阅可用图书
5. 查看借阅记录
6. 归还图书
7. 修改个人资料

**测试路径：**
```
首页 → Books → 搜索图书 → 点击查看详情 → 借书 → My Borrowings → 查看 → 归还
```

---

### 👨‍🏫 INSTRUCTOR (讲师)

**可访问页面：**
- ✅ Home
- ✅ Books
- ✅ My Borrowings
- ✅ Admin (部分功能)

**额外功能：**
1. 所有用户功能
2. 为其他用户借书
3. 处理还书
4. 查看所有借阅记录
5. 管理逾期图书

**测试路径：**
```
Admin → Books Management → 点击 Issue → 输入用户 → 借书
Admin → Overdue Books → 查看逾期 → 处理归还
```

---

### 👨‍💼 ADMIN (管理员)

**可访问页面：**
- ✅ Home
- ✅ Books
- ✅ My Borrowings
- ✅ Admin (完全访问)

**完全控制：**
1. 所有讲师功能
2. 添加/编辑/删除图书
3. 管理用户账号
4. 查看系统统计
5. 完全控制所有数据

**测试路径：**
```
Admin → Users Management → Add User → 创建用户
Admin → Books Management → Add Book → 添加图书
Admin → Books Management → Edit/Delete → 编辑/删除图书
Admin → 查看统计数据
```

---

## 🔧 常见问题解决

### 问题 1：页面显示空白

**检查：**
1. Tomcat 是否正常启动
2. 浏览器控制台是否有错误
3. 访问 URL 是否正确

**解决：**
```bash
# 检查 Tomcat 日志
查看: C:\tomcat\logs\catalogs.out
或: IDEA 底部 Run 窗口的日志
```

### 问题 2：无法登录

**检查：**
1. MySQL 是否运行（XAMPP 中 MySQL 应该是绿色）
2. 数据库是否导入成功
3. `persistence.xml` 密码是否正确

**解决：**
```bash
# 验证数据库
1. 打开 phpMyAdmin (http://localhost/phpmyadmin)
2. 检查是否有 library_management 数据库
3. 检查 users 表是否有 3 个默认用户
```

### 问题 3：API 请求失败

**检查：**
1. 浏览器控制台 (F12) 的 Network 标签
2. 查看 API 请求的状态码和响应

**常见错误：**
- 404: URL 路径错误
- 500: 服务器错误（查看 Tomcat 日志）
- 401: 未授权（token 过期，重新登录）

### 问题 4：Tomcat 端口 8080 被占用

**解决：**
```bash
# 方法 1: 查找并关闭占用进程
netstat -ano | findstr :8080
taskkill /PID [进程ID] /F

# 方法 2: 修改 Tomcat 端口
编辑: C:\tomcat\conf\server.xml
找到: <Connector port="8080"
改为: <Connector port="8090"
访问: http://localhost:8090/library-management-system/
```

---

## 📱 功能演示顺序

### 完整演示流程（5-10分钟）

#### 1. 首页展示（1分钟）
- 打开首页
- 展示功能特性
- 显示统计数据

#### 2. 用户功能演示（2分钟）
- 注册新用户
- 登录系统
- 浏览和搜索图书
- 借阅一本书
- 查看我的借阅

#### 3. 讲师功能演示（2分钟）
- 用讲师账号登录
- 进入管理面板
- 为用户借书
- 查看所有借阅
- 处理逾期还书

#### 4. 管理员功能演示（3分钟）
- 用管理员登录
- 查看统计面板
- 添加新图书
- 编辑图书信息
- 管理用户（演示）
- 查看逾期图书列表

#### 5. 数据库展示（2分钟）
- 打开 phpMyAdmin
- 展示数据库表结构
- 显示借阅记录数据
- 展示触发器和存储过程

---

## 🎬 演示建议

### 准备工作
1. ✅ 提前启动 MySQL 和 Tomcat
2. ✅ 清除浏览器缓存
3. ✅ 准备好测试数据
4. ✅ 打开多个浏览器窗口（不同角色）
5. ✅ 准备 phpMyAdmin 窗口展示数据库

### 演示亮点
1. **响应式设计** - 缩放浏览器窗口展示
2. **实时搜索** - 快速搜索图书
3. **角色权限** - 切换不同账号展示不同功能
4. **自动计算** - 逾期罚金自动计算
5. **现代界面** - 美观的 UI 设计

### 故事线
```
场景：一个学生想借书
1. 学生浏览图书 (USER)
2. 找到想要的书并借阅
3. 图书馆员帮其他学生借书 (INSTRUCTOR)
4. 管理员添加新到的图书 (ADMIN)
5. 处理逾期还书并计算罚金
6. 展示数据库中的变化
```

---

## ✨ 关键功能展示点

### 必须展示的功能
1. ✅ **用户注册和登录** - 安全认证
2. ✅ **图书搜索** - 实时搜索
3. ✅ **借阅流程** - 完整的借还书
4. ✅ **角色权限** - 三种角色不同权限
5. ✅ **管理面板** - 完整的后台管理
6. ✅ **数据库设计** - 展示 DDL 脚本和表结构
7. ✅ **响应式设计** - 手机/平板/电脑适配
8. ✅ **逾期管理** - 自动计算和提醒

---

## 📞 获取帮助

如果遇到问题：

1. **查看日志**
   - Tomcat: `logs/catalina.out`
   - IDEA: Run 窗口底部

2. **检查文档**
   - README.md - 项目概述
   - DEPLOYMENT.md - 部署详细步骤
   - FEATURES.md - 功能说明

3. **验证配置**
   - persistence.xml - 数据库连接
   - web.xml - Web 应用配置

---

## 🎉 准备就绪！

现在你可以：

1. ✅ 启动应用
2. ✅ 测试所有功能
3. ✅ 准备演示
4. ✅ 展示给老师/同学

**祝你演示成功！** 🚀

---

**下一步：** 
- 重新打包项目 (`mvn clean install`)
- 部署到 Tomcat
- 开始测试！

