package com.dengqinghua.calcite;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import java.sql.*;
import java.util.Properties;

/**
 * 使用 calcite 将数组变成一个数据库, 并执行SQL. 请查看测试用例
 */
public class SqlRunner {
    private CalciteConnection connection;

    public SqlRunner(String databaseName, Object sourceData) {
        this.connection = initConnection();
        SchemaPlus databaseInstance = fetchDatabaseInstance();
        databaseInstance.add(databaseName, new ReflectiveSchema(sourceData));
    }

    public Result<Record> run(String sql) {
        try {
            Statement statement = connection.createStatement();
            return DSL.using(new DefaultConfiguration()).fetch(statement.executeQuery(sql));
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    private CalciteConnection initConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:calcite:", new Properties());
            return connection.unwrap(CalciteConnection.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    private SchemaPlus fetchDatabaseInstance() {
        return connection.getRootSchema();
    }
}
