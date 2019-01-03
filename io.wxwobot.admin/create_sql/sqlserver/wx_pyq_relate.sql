
-- ----------------------------
-- Table structure for wx_pyq_relate
-- ----------------------------
DROP TABLE [dbo].[wx_pyq_relate]
GO
CREATE TABLE [dbo].[wx_pyq_relate] (
[id] int NOT NULL IDENTITY(1,1) ,
[app_id] int NOT NULL ,
[out_key] nvarchar(50) NOT NULL ,
[img] nvarchar(255) NOT NULL ,
[enable] bit NOT NULL DEFAULT ((0)) ,
[pyq_id] int NOT NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_pyq_relate', 
'COLUMN', N'app_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'游戏id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_pyq_relate'
, @level2type = 'COLUMN', @level2name = N'app_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'游戏id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_pyq_relate'
, @level2type = 'COLUMN', @level2name = N'app_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_pyq_relate', 
'COLUMN', N'out_key')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'外接Key'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_pyq_relate'
, @level2type = 'COLUMN', @level2name = N'out_key'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'外接Key'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_pyq_relate'
, @level2type = 'COLUMN', @level2name = N'out_key'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_pyq_relate', 
'COLUMN', N'img')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'图片ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_pyq_relate'
, @level2type = 'COLUMN', @level2name = N'img'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'图片ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_pyq_relate'
, @level2type = 'COLUMN', @level2name = N'img'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_pyq_relate', 
'COLUMN', N'enable')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'启停'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_pyq_relate'
, @level2type = 'COLUMN', @level2name = N'enable'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'启停'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_pyq_relate'
, @level2type = 'COLUMN', @level2name = N'enable'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'wx_pyq_relate', 
'COLUMN', N'pyq_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'亲友圈ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_pyq_relate'
, @level2type = 'COLUMN', @level2name = N'pyq_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'亲友圈ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_pyq_relate'
, @level2type = 'COLUMN', @level2name = N'pyq_id'
GO

-- ----------------------------
-- Indexes structure for table wx_pyq_relate
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table wx_pyq_relate
-- ----------------------------
ALTER TABLE [dbo].[wx_pyq_relate] ADD PRIMARY KEY ([id])
GO
