package com.ilumin.Config;

import com.ilumin.Order.Orders;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

/**
 * This configuration reads all rows found from the students table into `memory`.
 * We use this configuration because the students table has only three rows.
 *
 * If you are dealing with large dataset, you probably don’t want to read the entire
 * `ResultSet` into memory. If this is the case, you must limit the number of rows
 * that are fetched from the database when more rows are needed. You can do this by
 * invoking the `setFetchSize()` method of the `AbstractCursorItemReader` class.
 */
@Configuration
public class BatchForSmallDataSetConfig {

    private static final String QUERY_FIND_ORDER = "SELECT * FROM orders ORDER BY id DESC";

    ItemReader<Orders> dbItemReader(DataSource dataSource) {
        JdbcCursorItemReader<Orders> dataReader = new JdbcCursorItemReader<>();

        dataReader.setDataSource(dataSource);
        dataReader.setSql(QUERY_FIND_ORDER);
        dataReader.setRowMapper(new BeanPropertyRowMapper<>(Orders.class));

        return dataReader;
    }

}
