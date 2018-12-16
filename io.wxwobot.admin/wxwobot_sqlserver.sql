# sqlserver

# wx_rob_config

CREATE TABLE [dbo].[wx_rob_config] (
[id] int NOT NULL IDENTITY(1,1) ,
[unique_key] nvarchar(255) COLLATE Chinese_PRC_CI_AS NOT NULL ,
[send_friend] tinyint NOT NULL DEFAULT ((0)) ,
[send_group] tinyint NOT NULL DEFAULT ((0)) ,
[remark] nvarchar(255) COLLATE Chinese_PRC_CI_AS NOT NULL ,
[white_list] nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL ,
[create_time] datetime NULL DEFAULT (getdate()) ,
[update_time] datetime NULL DEFAULT (getdate()) ,
CONSTRAINT [PK__wx_rob_c__3213E83F5EBF139D] PRIMARY KEY ([id]),
CONSTRAINT [uid_wx_rob_config_unikey] UNIQUE ([unique_key] ASC)
)
ON [PRIMARY]
GO

DBCC CHECKIDENT(N'[dbo].[wx_rob_config]', RESEED, 5)
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
'COLUMN', N'send_friend')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'开启回复好友消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'send_friend'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'开启回复好友消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'send_friend'
GO

IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'wx_rob_config',
'COLUMN', N'send_group')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'开启回复群消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'send_group'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'开启回复群消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'send_group'
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
'COLUMN', N'white_list')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'对外接口IP白名单'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'white_list'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'对外接口IP白名单'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_config'
, @level2type = 'COLUMN', @level2name = N'white_list'
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

# wx_rob_keyword

CREATE TABLE [dbo].[wx_rob_keyword] (
[id] int NOT NULL IDENTITY(1,1) ,
[unique_key] nvarchar(64) COLLATE Chinese_PRC_CI_AS NOT NULL ,
[key_data] nvarchar(64) COLLATE Chinese_PRC_CI_AS NOT NULL ,
[value_data] nvarchar(1024) COLLATE Chinese_PRC_CI_AS NOT NULL ,
[type_data] varchar(64) COLLATE Chinese_PRC_CI_AS NOT NULL ,
[create_time] datetime NOT NULL DEFAULT (getdate()) ,
CONSTRAINT [PK__wx_rob_k__3213E83F6C190EBB] PRIMARY KEY ([id])
)
ON [PRIMARY]
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

CREATE INDEX [idx_wx_rob_keyword_unikey] ON [dbo].[wx_rob_keyword]
([unique_key] ASC)
ON [PRIMARY]
GO

# wx_rob_relation
CREATE TABLE [dbo].[wx_rob_relation] (
[id] int NOT NULL IDENTITY(1,1) ,
[out_key] nvarchar(64) COLLATE Chinese_PRC_CI_AS NOT NULL ,
[unique_key] nvarchar(64) COLLATE Chinese_PRC_CI_AS NOT NULL ,
[nick_name] nvarchar(255) COLLATE Chinese_PRC_CI_AS NOT NULL ,
[type_data] tinyint NOT NULL DEFAULT ((0)) ,
[enable] tinyint NOT NULL DEFAULT ((0)) ,
[create_time] datetime NOT NULL ,
CONSTRAINT [PK__wx_rob_r__3213E83F70DDC3D8] PRIMARY KEY ([id])
)
ON [PRIMARY]
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
'COLUMN', N'type_data')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'对象类型:0群聊,1好友'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'type_data'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'对象类型:0群聊,1好友'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'type_data'
GO

IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'wx_rob_relation',
'COLUMN', N'enable')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'启停:0停用，1启用'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'enable'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'启停:0停用，1启用'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'wx_rob_relation'
, @level2type = 'COLUMN', @level2name = N'enable'
GO

CREATE INDEX [idx_wx_rob_keyword_okey] ON [dbo].[wx_rob_relation]
([out_key] ASC)
ON [PRIMARY]
GO





