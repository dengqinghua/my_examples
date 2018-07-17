package com.dengqinghua.calcite;

import org.jooq.Record;
import org.jooq.Result;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SqlRunnerTest {
    static Database database;
    static SqlRunner sqlRunner;
    static String sql;

    public static class Database {
        public Seller[] sellers;
        public Product[] products;
    }

    public static class Seller {
        public String name;
        public int id;

        public Seller(String name, int id) {
            this.name = name;
            this.id   = id;
        }
    }

    public static class Product {
        public int id, sellerId;

        public Product(int id, int sellerId) {
            this.id        = id;
            this.sellerId = sellerId;
        }
    }

    @BeforeClass public static void setUp() {
        Seller[] sellers = {
                new Seller("dengqinghua", 1),
                new Seller("kimiGao", 2),
                new Seller("DS", 3),
        };

        Product[] products = {
                new Product(1024, 1),
                new Product(1025, 2),
                new Product(1026, 3),
        };

        database = new Database();
        database.products = products;
        database.sellers  = sellers;
    }

    @Test public void run() throws Exception {
        sqlRunner = new SqlRunner("merchant_system", database);
        sql = "SELECT \n"
                + "\"products\".\"id\", \"sellers\".\"name\" \n"
                + "FROM \"merchant_system\".\"products\" \n"
                + "INNER JOIN \"merchant_system\".\"sellers\" \n"
                + "ON \"merchant_system\".\"sellers\".\"id\" = \"merchant_system\".\"products\".\"sellerId\" \n"
                + "WHERE \"sellers\".\"name\" = 'dengqinghua'";

        Result<Record> result = sqlRunner.run(sql);

        assertThat(result.format(), is(
                "+----+-----------+" + "\n" +
                "|  id|name       |" + "\n" +
                "+----+-----------+" + "\n" +
                "|1024|dengqinghua|" + "\n" +
                "+----+-----------+"
                ));
    }
}