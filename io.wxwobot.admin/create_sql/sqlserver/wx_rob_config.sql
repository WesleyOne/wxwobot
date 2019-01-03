
-- ----------------------------
-- Table structure for wx_rob_config
-- ----------------------------
-- DROP TABLE [dbo].[wx_rob_config]
-- GO
CREATE TABLE [dbo].[wx_rob_config] (
[id] int NOT NULL IDENTITY(1,1) ,
[unique_key] nvarchar(255) NOT NULL ,
[remark] nvarchar(255) NOT NULL ,
[create_time] datetime NULL DEFAULT (getdate()) ,
[update_time] datetime NULL DEFAULT (getdate()) ,
[to_friend] bit NOT NULL DEFAULT ((0)) ,
[to_group] bit NOT NULL DEFAULT ((0)) ,
[enable] bit NOT NULL DEFAULT ((1)) ,
[from_out] bit NOT NULL DEFAULT ((0)) ,
[default_friend] bit NOT NULL DEFAULT ((0)) ,
[default_group] bit NOT NULL DEFAULT ((0)) 
)


GO
DBCC CHECKIDENT(N'[dbo].[wx_rob_config]', RESEED, 1)
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_config', 
'COLUMN', N'unique_key')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'唯一识别码'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'unique_key'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'唯一识别码'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'unique_key'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_config', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'remark'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_config', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_config', 
'COLUMN', N'to_friend')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'开启回复好友消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'to_friend'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'开启回复好友消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'to_friend'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_config', 
'COLUMN', N'to_group')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'开启回复群消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'to_group'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'开启回复群消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'to_group'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_config', 
'COLUMN', N'enable')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'启用1,禁用0'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'enable'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'启用1,禁用0'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'enable'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_config', 
'COLUMN', N'from_out')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'允许外部消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'from_out'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'允许外部消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'from_out'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_config', 
'COLUMN', N'default_friend')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'默认全局好友回复'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'default_friend'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'默认全局好友回复'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'default_friend'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_config', 
'COLUMN', N'default_group')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'默认全局群聊回复'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'default_group'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'默认全局群聊回复'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'default_group'
GO

-- ----------------------------
-- Indexes structure for table wx_rob_config
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table wx_rob_config
-- ----------------------------
ALTER TABLE [dbo].[wx_rob_config] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Uniques structure for table wx_rob_config
-- ----------------------------
ALTER TABLE [dbo].[wx_rob_config] ADD UNIQUE ([unique_key] ASC)
GO
