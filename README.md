# 学生选课管理系统
数据库课程设计项目，Java控制台 + MySQL + JDBC

## 功能
1. 查询全部学生信息
2. 新增学生
3. 修改学生姓名
4. 删除学生记录
5. 多表联查：根据学号查询学生所选课程和成绩

## 技术栈
- Java
- MySQL数据库
- JDBC（mysql驱动jar包，实现Java和数据库交互）

## 数据库设计
一共3张表：
student（学生表）、course（课程表）、sc选课中间表
学生和课程是**多对多关系**，使用中间表sc存储学号、课程号、成绩，联合主键：s_id + c_id，添加外键保证数据参照完整性。

## 运行步骤
1. 在MySQL执行 student-db.sql，一键建库、建表、导入测试数据
2. Eclipse导入项目，添加mysql驱动jar包
3. 修改代码中MySQL账号密码
4. 运行StudentManager.java，控制台菜单选择功能操作

## 项目踩坑记录
1. 一开始Eclipse导入jar包报错，因为项目自带module-info模块化文件，删除后驱动正常识别
2. 代码import导入位置写错，报语法错误，调整到文件顶部解决
