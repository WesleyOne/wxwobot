
-- ----------------------------
-- Table structure for wx_rob_relation
-- ----------------------------
DROP TABLE [dbo].[wx_rob_relation]
GO
CREATE TABLE [dbo].[wx_rob_relation] (
[id] int NOT NULL IDENTITY(1,1) ,
[out_key] nvarchar(64) NOT NULL ,
[unique_key] nvarchar(64) NOT NULL ,
[nick_name] nvarchar(255) NOT NULL ,
[create_time] datetime NOT NULL ,
[enable] bit NOT NULL DEFAULT ((1)) ,
[to_group] bit NOT NULL DEFAULT ((1)) ,
[white_list] nvarchar(255) NOT NULL DEFAULT '' 
)


GO
DBCC CHECKIDENT(N'[dbo].[wx_rob_relation]', RESEED, 1)
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_relation', 
'COLUMN', N'out_key')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'外部id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'out_key'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'外部id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'out_key'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_relation', 
'COLUMN', N'unique_key')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'关联识别码'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'unique_key'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'关联识别码'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'unique_key'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_relation', 
'COLUMN', N'nick_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'对象昵称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'nick_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'对象昵称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'nick_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_relation', 
'COLUMN', N'enable')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'启用1,停用0'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'enable'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'启用1,停用0'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'enable'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_relation', 
'COLUMN', N'to_group')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'1群聊，0好友'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'to_group'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'1群聊，0好友'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'to_group'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_rob_relation', 
'COLUMN', N'white_list')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'对外接口白名单'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'white_list'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'对外接口白名单'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'white_list'
GO

-- ----------------------------
-- Indexes structure for table wx_rob_relation
-- ----------------------------
CREATE INDEX [idx_wx_rob_relation_okey] ON [dbo].[wx_rob_relation]
([out_key] ASC) 
GO

-- ----------------------------
-- Primary Key structure for table wx_rob_relation
-- ----------------------------
ALTER TABLE [dbo].[wx_rob_relation] ADD PRIMARY KEY ([id])
GO
