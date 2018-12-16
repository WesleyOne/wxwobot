package io.wxwobot.admin.web.utils;

import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.dialect.Dialect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 主要解决sqlserver部分关键字问题导致的使用Model数据库操作异常
 * 通过对表名和条件字段名加上[]处理
 * 以下情况仍需手写SQL处理:
 * 1.分页sql
 * 2.SELECT order ... 请手写SQL改为 SELECT [order] ...
 *
 * @author WesleyOne
 * @see com.jfinal.plugin.activerecord.dialect.SqlServerDialect 为OSC 网友战五渣贡献代码：http://www.oschina.net/question/2333909_234198
 * @create 2018/8/4
 */

public class NewSqlServerDialect extends Dialect {

    @Override
    public String forTableBuilderDoBuild(String tableName) {
        return "select * from [" + tableName + "] with(nolock) where 1 = 2";
    }

    @Override
    public void forModelSave(Table table, Map<String, Object> attrs, StringBuilder sql, List<Object> paras) {
        sql.append("insert into ").append('[').append(table.getName()).append(']').append("(");
        StringBuilder temp = new StringBuilder(") values(");
        for (Map.Entry<String, Object> e: attrs.entrySet()) {
            String colName = e.getKey();
            if (table.hasColumnLabel(colName)) {
                if (paras.size() > 0) {
                    sql.append(", ");
                    temp.append(", ");
                }
                sql.append('[').append(colName).append(']');
                temp.append('?');
                paras.add(e.getValue());
            }
        }
        sql.append(temp.toString()).append(')');
    }

    @Override
    public String forModelDeleteById(Table table) {
        String[] pKeys = table.getPrimaryKey();
        StringBuilder sql = new StringBuilder(45);
        sql.append("delete from ");
        sql.append('[').append(table.getName()).append(']');
        sql.append(" where ");
        for (int i=0; i<pKeys.length; i++) {
            if (i > 0) {
                sql.append(" and ");
            }
            sql.append('[').append(pKeys[i]).append(']').append(" = ?");
        }
        return sql.toString();
    }

    @Override
    public void forModelUpdate(Table table, Map<String, Object> attrs, Set<String> modifyFlag, StringBuilder sql, List<Object> paras) {
        sql.append("update ").append('[').append(table.getName()).append(']').append(" set ");
        String[] pKeys = table.getPrimaryKey();
        for (Map.Entry<String, Object> e : attrs.entrySet()) {
            String colName = e.getKey();
            if (modifyFlag.contains(colName) && !isPrimaryKey(colName, pKeys) && table.hasColumnLabel(colName)) {
                if (paras.size() > 0) {
                    sql.append(", ");
                }
                sql.append('[').append(colName).append(']').append(" = ? ");
                paras.add(e.getValue());
            }
        }
        sql.append(" where ");
        for (int i=0; i<pKeys.length; i++) {
            if (i > 0) {
                sql.append(" and ");
            }
            sql.append('[').append(pKeys[i]).append(']').append(" = ?");
            paras.add(attrs.get(pKeys[i]));
        }
    }

    @Override
    public String forModelFindById(Table table, String columns) {
        StringBuilder sql = new StringBuilder("select ").append(columns).append(" from ");
        sql.append('[').append(table.getName()).append(']').append(" with(nolock) ");
        sql.append(" where ");
        String[] pKeys = table.getPrimaryKey();
        for (int i=0; i<pKeys.length; i++) {
            if (i > 0) {
                sql.append(" and ");
            }
            sql.append('[').append(pKeys[i]).append(']').append(" = ?");
        }
        return sql.toString();
    }

    @Override
    public String forDbFindById(String tableName, String[] pKeys) {
        tableName = tableName.trim();
        trimPrimaryKeys(pKeys);

        StringBuilder sql = new StringBuilder("select * from ").append('[').append(tableName).append(']').append(" with(nolock) where ");
        for (int i=0; i<pKeys.length; i++) {
            if (i > 0) {
                sql.append(" and ");
            }
            sql.append('[').append(pKeys[i]).append(']').append(" = ?");
        }
        return sql.toString();
    }

    @Override
    public String forDbDeleteById(String tableName, String[] pKeys) {
        tableName = tableName.trim();
        trimPrimaryKeys(pKeys);

        StringBuilder sql = new StringBuilder("delete from ").append('[').append(tableName).append(']').append(" where ");
        for (int i=0; i<pKeys.length; i++) {
            if (i > 0) {
                sql.append(" and ");
            }
            sql.append('[').append(pKeys[i]).append(']').append(" = ?");
        }
        return sql.toString();
    }

    @Override
    public void forDbSave(String tableName, String[] pKeys, Record record, StringBuilder sql, List<Object> paras) {
        tableName = tableName.trim();
        trimPrimaryKeys(pKeys);

        sql.append("insert into ");
        sql.append('[').append(tableName).append(']').append('(');
        StringBuilder temp = new StringBuilder();
        temp.append(") values(");

        for (Map.Entry<String, Object> e: record.getColumns().entrySet()) {
            if (paras.size() > 0) {
                sql.append(", ");
                temp.append(", ");
            }
            sql.append('[').append(e.getKey()).append(']');
            temp.append('?');
            paras.add(e.getValue());
        }
        sql.append(temp.toString()).append(')');
    }

    @Override
    public void forDbUpdate(String tableName, String[] pKeys, Object[] ids, Record record, StringBuilder sql, List<Object> paras) {
        tableName = tableName.trim();
        trimPrimaryKeys(pKeys);

        sql.append("update ").append('[').append(tableName).append(']').append(" set ");
        for (Map.Entry<String, Object> e: record.getColumns().entrySet()) {
            String colName = e.getKey();
            if (!isPrimaryKey(colName, pKeys)) {
                if (paras.size() > 0) {
                    sql.append(", ");
                }
                sql.append('[').append(colName).append(']').append(" = ? ");
                paras.add(e.getValue());
            }
        }
        sql.append(" where ");
        for (int i=0; i<pKeys.length; i++) {
            if (i > 0) {
                sql.append(" and ");
            }
            sql.append('[').append(pKeys[i]).append(']').append(" = ?");
            paras.add(ids[i]);
        }
    }

    /**
     * sql.replaceFirst("(?i)select", "") 正则中带有 "(?i)" 前缀，指定在匹配时不区分大小写
     */
    @Override
    public String forPaginate(int pageNumber, int pageSize, StringBuilder findSql) {
        int end = pageNumber * pageSize;
        if (end <= 0) {
            end = pageSize;
        }
        int begin = (pageNumber - 1) * pageSize;
        if (begin < 0) {
            begin = 0;
        }
        StringBuilder ret = new StringBuilder();
        ret.append("SELECT * FROM ( SELECT row_number() over (order by tempcolumn) temprownumber, * FROM ");
        ret.append(" ( SELECT TOP ").append(end).append(" tempcolumn=0,");
        ret.append(findSql.toString().replaceFirst("(?i)select", ""));
        ret.append(")vip)mvp where temprownumber>").append(begin);
        return ret.toString();
    }

    @Override
    public void fillStatement(PreparedStatement pst, List<Object> paras) throws SQLException {
        fillStatementHandleDateType(pst, paras);
    }

    @Override
    public void fillStatement(PreparedStatement pst, Object... paras) throws SQLException {
        fillStatementHandleDateType(pst, paras);
    }
}
