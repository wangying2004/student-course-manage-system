-- 创建数据库
CREATE DATABASE IF NOT EXISTS student_db DEFAULT CHARACTER SET utf8mb4;
USE student_db;

-- 学生表
CREATE TABLE student(
    s_id VARCHAR(20) PRIMARY KEY,
    s_name VARCHAR(20) NOT NULL,
    s_gender VARCHAR(4),
    s_birth DATE,
    class_id INT
);

-- 课程表
CREATE TABLE course(
    c_id VARCHAR(20) PRIMARY KEY,
    c_name VARCHAR(50) NOT NULL
);

-- 选课成绩表
CREATE TABLE sc(
    s_id VARCHAR(20),
    c_id VARCHAR(20),
    score DOUBLE,
    PRIMARY KEY(s_id,c_id)
);