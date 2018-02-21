package com.packt.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JacksonCsvMapper {
    public static void main(String[] args) throws IOException {
        File file = new ClassPathResource("rename.csv").getFile();

        System.out.println("read csv");

        // load file from resource

        // configure the schema we want to read
        CsvSchema schema = CsvSchema.builder()
                .addColumn("gupGipNummer")
                .addColumn("eigenNummer")
                .addColumn("investeringsType")
                .addColumn("prioriteit", CsvSchema.ColumnType.NUMBER)
                .build()
                .withSkipFirstDataRow(true)
                .withColumnSeparator(';');

        CsvMapper mapper = new CsvMapper();

        // configure the reader on what bean to read and how we want to write
        // that bean
        ObjectReader oReader = mapper.readerFor(WUPRecord.class).with(schema);

        // read from file
        try (Reader reader = new FileReader(file)) {
            MappingIterator<WUPRecord> mi = oReader.readValues(reader);
            while (mi.hasNext()) {
                System.out.println(mi.next());
            }
        }
    }
}
