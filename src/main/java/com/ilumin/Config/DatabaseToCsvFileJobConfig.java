package com.ilumin.Config;

import com.ilumin.Order.Orders;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class DatabaseToCsvFileJobConfig {

    ItemWriter<Orders> databaseCsvItemWriter() {
        FlatFileItemWriter<Orders> csvFileWriter = new FlatFileItemWriter<>();

        String exportFileHeader = "ORDER_ID;CUSTOMER_ID;ORDER_DATE";
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        csvFileWriter.setHeaderCallback(headerWriter);

        String exportFilePath = "/tmp/orders.csv";
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));

        LineAggregator<Orders> lineAggregator = createOrderLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private LineAggregator<Orders> createOrderLineAggregator() {
        DelimitedLineAggregator<Orders> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<Orders> fieldExtractor = createOrderFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<Orders> createOrderFieldExtractor() {
        BeanWrapperFieldExtractor<Orders> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] { "OrderID", "CustomerID", "OrderDate" });
        return extractor;
    }

}
