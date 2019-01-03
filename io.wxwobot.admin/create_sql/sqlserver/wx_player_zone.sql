
-- ----------------------------
-- Table structure for wx_player_zone
-- ----------------------------
DROP TABLE [dbo].[wx_player_zone]
GO
CREATE TABLE [dbo].[wx_player_zone] (
[id] int NOT NULL IDENTITY(1,1) ,
[app_id] int NOT NULL ,
[user_id] int NOT NULL ,
[img_url] nvarchar(64) NULL ,
[content] nvarchar(255) NULL ,
[create_time] datetime NULL DEFAULT (getdate()) ,
[is_delete] bit NOT NULL DEFAULT ((0)) ,
[uuid] nvarchar(64) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[wx_player_zone]', RESEED, 1)
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_player_zone', 
'COLUMN', N'app_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'游戏ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'app_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'游戏ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'app_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_player_zone', 
'COLUMN', N'user_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'玩家ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'user_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'玩家ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'user_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_player_zone', 
'COLUMN', N'img_url')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'图片地址'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'img_url'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'图片地址'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'img_url'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_player_zone', 
'COLUMN', N'content')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'content'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'content'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_player_zone', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建日期'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建日期'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_player_zone', 
'COLUMN', N'is_delete')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'是否删除'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'is_delete'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'是否删除'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_player_zone'
, @level2type = 'COLUMN', @level2name = N'is_delete'
GO

-- ----------------------------
-- Indexes structure for table wx_player_zone
-- ----------------------------
CREATE INDEX [idx_wx_player_zone_app_user] ON [dbo].[wx_player_zone]
([app_id] ASC, [user_id] ASC) 
GO

-- ----------------------------
-- Primary Key structure for table wx_player_zone
-- ----------------------------
ALTER TABLE [dbo].[wx_player_zone] ADD PRIMARY KEY ([id])
GO
