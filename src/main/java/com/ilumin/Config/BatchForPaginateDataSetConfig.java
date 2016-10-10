package com.ilumin.Config;

import com.ilumin.Order.Orders;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.H2PagingQueryProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * The performance of this solution depends from the size of your dataset and the used database. Some implementations
 * of the `PagingQueryProvider` interface paginate the query results by using the `LIMIT` and `OFFSET` keywords,
 * and this is a lot slower than using the so called seek method.
 * (http://use-the-index-luke.com/sql/partial-results/fetch-next-page)
 */
@Configuration
public class BatchForPaginateDataSetConfig {

    ItemReader<Orders> dbItemReader(DataSource dataSource) {
        JdbcPagingItemReader<Orders> dataReader = new JdbcPagingItemReader<>();

        dataReader.setDataSource(dataSource);
        dataReader.setPageSize(1);

        // from setSql
        // change to QueryProvider
        PagingQueryProvider queryProvider = createQueryProvider();
        dataReader.setQueryProvider(queryProvider);

        dataReader.setRowMapper(new BeanPropertyRowMapper<>(Orders.class));

        return dataReader;
    }

    private PagingQueryProvider createQueryProvider() {
        H2PagingQueryProvider queryProvider = new H2PagingQueryProvider();
        queryProvider.setSelectClause("SELECT OrderID, CustomerID, OrderDate");
        queryProvider.setFromClause("FROM orders");
        queryProvider.setSortKeys(sortByEmailAddressAsc());
        return queryProvider;
    }

    private Map<String, Order> sortByEmailAddressAsc() {
        Map<String, Order> sortConfiguration = new HashMap<>();
        sortConfiguration.put("OrderID", Order.ASCENDING);
        return sortConfiguration;
    }

}
