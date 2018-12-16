package io.wxwobot.admin.web.common;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import io.wxwobot.admin.web.interceptor.ExceptionInterceptor;
import io.wxwobot.admin.web.utils.NewSqlServerDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author admin
 */
public class MyConfig extends JFinalConfig {

	public final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/**
	 * 配置常量
	 */
	@Override
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("appConfig.properties");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		//上传的文件的最大50M
		me.setMaxPostSize(50 * 1024 * 1024);
		me.setEncoding("UTF-8");
		me.setJsonFactory(new FastJsonFactory());
		me.setBaseUploadPath(PropKit.get("cachUploadPath"));
	}
	
	/**
	 * 配置路由
	 */
	@Override
	public void configRoute(Routes me) {
		me.add(new MyRoute());
		me.add(new OutRoute());
	}

	@Override
	public void configEngine(Engine me) {

	}
	
	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = createAgentDruidPlugin();
		//防止sql 注入
		druidPlugin.addFilter(new StatFilter());//sql 监控
		WallFilter wall = new WallFilter();
		wall.setDbType("sqlserver");
		druidPlugin.addFilter(wall);
		druidPlugin.setInitialSize(1);
		me.add(druidPlugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		arp.setDialect(new NewSqlServerDialect());
		arp.setShowSql(PropKit.getBoolean("devMode", false));
		arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
		me.add(arp);
	}

	public static DruidPlugin createAgentDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim(),PropKit.get("jdbcDriverSqlServe"));
	}
	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new ExceptionInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	@Override
	public void configHandler(Handlers me) {
	}

	@Override
	public void afterJFinalStart() {

	}

}
