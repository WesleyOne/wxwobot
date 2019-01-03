# wxwobot

#### 项目介绍
项目简介：V信自动收发机器

#### 项目特点
- 简陋的管理后台
- 支持多账号登入
- 支持短时间内热登入(譬如重启项目时)
- WEB部署可以对外提供消息转发接口

#### 软件架构
- JDK1.8 + JFinal3.4 + mysql 
- itchat4j
- Bootstrap3+及相关插件
- ......

#### 计划任务
- [ ] 【长期任务】线程管理优化 ( **多多指点** )
- [ ] 【长期任务】代码优化
- [ ] 实现多MsgHandler

#### 安装教程

推荐方法:
1.clone项目到自己的空间
2.IDEA -> File->New->Project from Version Control -> git ->填写自己项目的地址
3.add maven project ->选中项目pom.xml文件 -> 自动构建项目结构
4.默认window环境,提供mac环境选择,修改pom.xml的配置
5.mvn install -> jetty:run 

#### 使用说明
项目启动后,访问localhost,默认账号admin密码123456
1. 警钟长鸣-都是社会主义接班人，用途不要太社会
2. 警钟长鸣-不要频繁操作VX官方接口，如发送消息等
3. 警钟长鸣-建议使用小号

#### 参与贡献
我也不懂下面的操作,逃
1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

