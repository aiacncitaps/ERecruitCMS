/*
Navicat SQL Server Data Transfer

Source Server         : mssql
Source Server Version : 90000
Source Host           : 192.168.64.129:1433
Source Database       : aiawebsite
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 90000
File Encoding         : 65001

Date: 2015-12-24 19:19:21
*/


-- ----------------------------
-- Table structure for [dbo].[attract_agent]
-- ----------------------------
DROP TABLE [dbo].[attract_agent]
GO
CREATE TABLE [dbo].[attract_agent] (
[id] varchar(50) NOT NULL ,
[name] varchar(50) NULL ,
[sex] varchar(50) NULL ,
[sub_company_id] varchar(50) NULL ,
[region] varchar(50) NULL ,
[agent_code] varchar(50) NULL ,
[password] varchar(50) NULL ,
[agent_long_code] varchar(50) NULL ,
[source] varchar(50) NULL ,
[device_num] varchar(50) NULL ,
[last_login_time] datetime NULL ,
[login_num] varchar(50) NULL ,
[is_install] bit NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_agent
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_children]
-- ----------------------------
DROP TABLE [dbo].[attract_children]
GO
CREATE TABLE [dbo].[attract_children] (
[id] varchar(50) NOT NULL ,
[name] varchar(50) NULL ,
[fileCounts] numeric(16) NULL ,
[fileTypes] varchar(50) NULL ,
[filePaths] varchar(50) NULL ,
[fileNames] varchar(50) NULL ,
[contentTypes] varchar(50) NULL ,
[nextFlag] bit NULL ,
[fileIds] varchar(50) NULL ,
[resource_id] varchar(50) NULL ,
[squence] numeric(8) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_children
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_children_next]
-- ----------------------------
DROP TABLE [dbo].[attract_children_next]
GO
CREATE TABLE [dbo].[attract_children_next] (
[id] varchar(50) NOT NULL ,
[name] varchar(50) NULL ,
[fileCounts] numeric(16) NULL ,
[fileTypes] varchar(50) NULL ,
[filePaths] varchar(50) NULL ,
[fileNames] varchar(50) NULL ,
[contentTypes] varchar(50) NULL ,
[fileIds] varchar(50) NULL ,
[children_id] varchar(50) NULL ,
[squence] numeric(8) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_children_next
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_config]
-- ----------------------------
DROP TABLE [dbo].[attract_config]
GO
CREATE TABLE [dbo].[attract_config] (
[id] varchar(50) NOT NULL ,
[keyA] varchar(50) NULL ,
[value] varchar(50) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_config
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_files]
-- ----------------------------
DROP TABLE [dbo].[attract_files]
GO
CREATE TABLE [dbo].[attract_files] (
[id] varchar(50) NOT NULL ,
[name] varchar(50) NULL ,
[path] varchar(50) NULL ,
[contentType] varchar(50) NULL ,
[data] binary(1) NULL ,
[resourceId] varchar(50) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_files
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_login_records]
-- ----------------------------
DROP TABLE [dbo].[attract_login_records]
GO
CREATE TABLE [dbo].[attract_login_records] (
[id] varchar(50) NOT NULL ,
[agent_code] varchar(50) NULL ,
[sub_company_id] varchar(50) NULL ,
[region] varchar(50) NULL ,
[system_name] varchar(50) NULL ,
[host_ip] varchar(50) NULL ,
[host_name] varchar(50) NULL ,
[remote_user] varchar(50) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_login_records
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_peoples]
-- ----------------------------
DROP TABLE [dbo].[attract_peoples]
GO
CREATE TABLE [dbo].[attract_peoples] (
[id] varchar(50) NOT NULL ,
[name] varchar(50) NULL ,
[join_date] varchar(50) NULL ,
[old_job] varchar(50) NULL ,
[share_word] varchar(50) NULL ,
[fileId] varchar(50) NULL ,
[filePath] varchar(50) NULL ,
[fileName] varchar(50) NULL ,
[fileType] varchar(50) NULL ,
[contentType] varchar(50) NULL ,
[old_mark] varchar(50) NULL ,
[new_mark] varchar(50) NULL ,
[sub_excel_id] varchar(50) NULL ,
[deleteFlag] bit NULL ,
[sub_company] varchar(50) NULL ,
[marks] varchar(50) NULL ,
[squence] numeric(8) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_peoples
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_report]
-- ----------------------------
DROP TABLE [dbo].[attract_report]
GO
CREATE TABLE [dbo].[attract_report] (
[id] varchar(50) NOT NULL ,
[talent_id] varchar(50) NULL ,
[data] binary(1) NULL ,
[file_name] varchar(50) NULL ,
[image_url] varchar(50) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_report
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_resource]
-- ----------------------------
DROP TABLE [dbo].[attract_resource]
GO
CREATE TABLE [dbo].[attract_resource] (
[id] varchar(50) NOT NULL ,
[title] varchar(50) NULL ,
[content] varchar(50) NULL ,
[type] numeric(16) NULL ,
[fileCounts] numeric(16) NULL ,
[fileTypes] varchar(50) NULL ,
[filePaths] varchar(50) NULL ,
[fileNames] varchar(50) NULL ,
[contentTypes] varchar(50) NULL ,
[deleteFlag] bit NULL ,
[fileIds] varchar(50) NULL ,
[squence] numeric(8) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_resource
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_setting]
-- ----------------------------
DROP TABLE [dbo].[attract_setting]
GO
CREATE TABLE [dbo].[attract_setting] (
[agentId] varchar(50) NOT NULL ,
[sqe_num] numeric(16) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_setting
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_special_agent]
-- ----------------------------
DROP TABLE [dbo].[attract_special_agent]
GO
CREATE TABLE [dbo].[attract_special_agent] (
[id] varchar(50) NOT NULL ,
[sub_company] varchar(50) NULL ,
[agent_code] varchar(50) NULL ,
[name] varchar(50) NULL ,
[title] varchar(50) NULL ,
[last_login_time] datetime NULL ,
[login_num] varchar(50) NULL ,
[is_install] bit NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_special_agent
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_sub_excellence]
-- ----------------------------
DROP TABLE [dbo].[attract_sub_excellence]
GO
CREATE TABLE [dbo].[attract_sub_excellence] (
[id] varchar(50) NOT NULL ,
[name] varchar(50) NULL ,
[fileCounts] numeric(16) NULL ,
[fileTypes] varchar(50) NULL ,
[filePaths] varchar(50) NULL ,
[fileNames] varchar(50) NULL ,
[contentTypes] varchar(50) NULL ,
[fileIds] varchar(50) NULL ,
[top_excel_id] varchar(50) NULL ,
[deleteFlag] bit NULL ,
[squence] numeric(8) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_sub_excellence
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_talent]
-- ----------------------------
DROP TABLE [dbo].[attract_talent]
GO
CREATE TABLE [dbo].[attract_talent] (
[id] varchar(50) NOT NULL ,
[name] varchar(50) NULL ,
[sex] varchar(50) NULL ,
[birthday] varchar(50) NULL ,
[agent_code] varchar(50) NULL ,
[agent_id] varchar(50) NULL ,
[chat_time] varchar(50) NULL ,
[sub_company_id] varchar(50) NULL ,
[region] varchar(50) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_talent
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_test_agent]
-- ----------------------------
DROP TABLE [dbo].[attract_test_agent]
GO
CREATE TABLE [dbo].[attract_test_agent] (
[id] varchar(50) NOT NULL ,
[sub_company] varchar(50) NULL ,
[agent_code] varchar(50) NULL ,
[password] varchar(50) NULL ,
[name] varchar(50) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_test_agent
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_top_excellence]
-- ----------------------------
DROP TABLE [dbo].[attract_top_excellence]
GO
CREATE TABLE [dbo].[attract_top_excellence] (
[id] varchar(50) NOT NULL ,
[name] varchar(50) NULL ,
[fileCounts] numeric(16) NULL ,
[fileTypes] varchar(50) NULL ,
[filePaths] varchar(50) NULL ,
[fileNames] varchar(50) NULL ,
[contentTypes] varchar(50) NULL ,
[fileIds] varchar(50) NULL ,
[deleteFlag] bit NULL ,
[squence] numeric(8) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_top_excellence
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[attract_user]
-- ----------------------------
DROP TABLE [dbo].[attract_user]
GO
CREATE TABLE [dbo].[attract_user] (
[id] varchar(50) NOT NULL ,
[name] varchar(50) NULL ,
[passwd] varchar(50) NULL ,
[salt] varchar(50) NULL ,
[ct] datetime NULL ,
[ut] datetime NULL 
)


GO

-- ----------------------------
-- Records of attract_user
-- ----------------------------

-- ----------------------------
-- Indexes structure for table attract_agent
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_agent]
-- ----------------------------
ALTER TABLE [dbo].[attract_agent] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_children
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_children]
-- ----------------------------
ALTER TABLE [dbo].[attract_children] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_children_next
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_children_next]
-- ----------------------------
ALTER TABLE [dbo].[attract_children_next] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_config
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_config]
-- ----------------------------
ALTER TABLE [dbo].[attract_config] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_files
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_files]
-- ----------------------------
ALTER TABLE [dbo].[attract_files] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_login_records
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_login_records]
-- ----------------------------
ALTER TABLE [dbo].[attract_login_records] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_peoples
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_peoples]
-- ----------------------------
ALTER TABLE [dbo].[attract_peoples] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_report
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_report]
-- ----------------------------
ALTER TABLE [dbo].[attract_report] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_resource
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_resource]
-- ----------------------------
ALTER TABLE [dbo].[attract_resource] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_setting
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_setting]
-- ----------------------------
ALTER TABLE [dbo].[attract_setting] ADD PRIMARY KEY ([agentId])
GO

-- ----------------------------
-- Indexes structure for table attract_special_agent
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_special_agent]
-- ----------------------------
ALTER TABLE [dbo].[attract_special_agent] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_sub_excellence
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_sub_excellence]
-- ----------------------------
ALTER TABLE [dbo].[attract_sub_excellence] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_talent
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_talent]
-- ----------------------------
ALTER TABLE [dbo].[attract_talent] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_test_agent
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_test_agent]
-- ----------------------------
ALTER TABLE [dbo].[attract_test_agent] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_top_excellence
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_top_excellence]
-- ----------------------------
ALTER TABLE [dbo].[attract_top_excellence] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table attract_user
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[attract_user]
-- ----------------------------
ALTER TABLE [dbo].[attract_user] ADD PRIMARY KEY ([id])
GO
