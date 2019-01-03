
-- ----------------------------
-- Table structure for wx_rob_keyword
-- ----------------------------
DROP TABLE [dbo].[wx_rob_keyword]
GO
CREATE TABLE [dbo].[wx_rob_keyword] (
[id] int NOT NULL IDENTITY(1,1) ,
[unique_key] nvarchar(64) NOT NULL ,
[key_data] nvarchar(64) NOT NULL ,
[value_data] nvarchar(1024) NOT NULL ,
[type_data] nvarchar(64) NOT NULL ,
[create_time] datetime NOT NULL DEFAULT (getdate()) ,
[enable] bit NOT NULL DEFAULT ((1)) ,
[to_group] bit NOT NULL DEFAULT ((1)) ,
[nick_name] nvarchar(255) NOT NULL DEFAULT '' 
)


GO
DBCC CHECKIDENT(N'[dbo].[wx_rob_keyword]', RESEED, 1)
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_keyword', 
'COLUMN', N'unique_key')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'识别码'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'unique_key'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'识别码'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'unique_key'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_keyword', 
'COLUMN', N'key_data')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'关键字'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'key_data'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'关键字'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'key_data'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_keyword', 
'COLUMN', N'value_data')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'值'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'value_data'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'值'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'value_data'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_keyword', 
'COLUMN', N'type_data')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'消息类型'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'type_data'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'消息类型'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'type_data'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_keyword', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_keyword', 
'COLUMN', N'enable')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'启用1，禁用0'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'enable'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'启用1，禁用0'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'enable'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_keyword', 
'COLUMN', N'to_group')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'群聊1，好友0'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'to_group'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'群聊1，好友0'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'to_group'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_keyword', 
'COLUMN', N'nick_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'昵称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'nick_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'昵称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_keyword'
, @level2type = 'COLUMN', @level2name = N'nick_name'
GO

-- ----------------------------
-- Indexes structure for table wx_rob_keyword
-- ----------------------------
CREATE INDEX [idx_wx_rob_keyword_unikey] ON [dbo].[wx_rob_keyword]
([unique_key] ASC) 
GO

-- ----------------------------
-- Primary Key structure for table wx_rob_keyword
-- ----------------------------
ALTER TABLE [dbo].[wx_rob_keyword] ADD PRIMARY KEY ([id])
GO
