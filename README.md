# wxwobot

#### 项目介绍
- 项目简介：某信WEB端的自助工具
- 基于<a href="https://github.com/yaphone/itchat4j">itchat4j开源项目</a>开发

#### 项目特点
- 支持多账号
- 重启项目热登录
- 消息使用队列间隔时间发送，减少被封几率
- 其他业务访问项目的对外接口，可实现消息转发到某信(需布置到公网环境)
- 自动回复（默认效果是全匹配关键字回复和进群欢迎语）
- jfinal-undertow开发部署的种种优势
- 有开发能力可自行实现消息的处理

#### 软件组成
- JDK1.8 + JFinal3.5 + undertow + mysql 
- Bootstrap3+及相关插件
- ......

#### 计划任务
- [ ] 【长期任务】线程管理优化 ( **多多指点** )
- [ ] 【长期任务】代码优化

#### 安装教程

**准备**
- 使用 create_sql/mysql/wxwobot.sql 中的 sql 语句创建数据库与数据库表
- 修改 pom.xml 文件，填入正确的数据库连接用户名、密码
- 请确保安装了 JavaSE 1.8 或更高版本

**本地开发** 
1. 将项目导入开发工具，推荐使用IDEA
2. 打开 io.wxwobot.admin.web.common.MyConfig 文件，运行
3. 打开浏览器输入  localhost:8180 即可查看运行效果,默认账号密码均为 wxwobot，开发模式无需登录
注意： 

**生产部署**
1. 修改pom.xml中uat配置，填入生产的数据库连接用户名、密码
2. 命令行进入项目根目录，然后运行 mvn install -P uat 即可打包
3. 打包完后，将 io.wxwobot.admin/target下的wxwobot-release.zip或wxwobot-release.tar.gz发送到云服务器上
4. 登入云服务器找到压缩包并解压
5. linux 下运行 start.sh 脚本启动项目,stop.sh 关闭项目;windows 下双击 start.bat 启动项目;
6. 访问云服务器的公网IP(或域名):8180，查看效果

3：start.sh 脚本中提供了详细的说明，根据说明可选择不同的运行模式

**常见问题**
https://www.jfinal.com/doc/1-5

#### 鸣谢
<ul>
    <li><a href="https://www.jfinal.com/">JFinal极速开发社区</a></li>
    <li><a href="https://github.com/yaphone/itchat4j">itchat4j开源项目</a></li>
    <li><a href="http://www.bootcss.com">Bootstrap中文网</a>,<a href="https://www.bootcdn.cn/">BootCDN</a></li>
    <li><a href="https://www.glyphicons.com/">Glyphicons</a>,<a href="https://bootstrap-table.wenzhixin.net.cn/" target="_blank">BootstrapTable</a></li>
    <li><a href="https://github.com/Bttstrp/bootstrap-switch">Bootstrap Switch</a>,<a href="http://bootboxjs.com/" target="_blank">Bootbox.js</a></li>
    <li><a href="https://github.com/nghuuphuoc/bootstrapvalidator">bootstrapvalidator</a></li>
    <li><a href="https://github.com/kartik-v/bootstrap-fileinput">bootstrap-fileinput</a> </li>
    <li><a href="https://promotion.aliyun.com/ntms/yunparter/invite.html?userCode=dnuqwh0e" target="_blank">阿里云推广计划</a></li>
</ul>

#### 众筹植发(滑稽)
