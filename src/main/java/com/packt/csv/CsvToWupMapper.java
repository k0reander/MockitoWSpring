package com.packt.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class CsvToWupMapper {

    CSVFormat csvFormat;

    public CsvToWupMapper(CSVFormat csvFormat) {
        this.csvFormat = csvFormat;
    }

    public static void main(String[] args) throws IOException {
        File file = new ClassPathResource("rename.csv").getFile();
        CSVFormat csvFormat = CSVFormat.INFORMIX_UNLOAD_CSV.withHeader("GUP/GIP", "Eigen Nummer", "Investeringstype", "Prioriteit");

        CsvToWupMapper mapper = new CsvToWupMapper(csvFormat);
        List<WUPRecord> wupRecords = mapper.map(file);

        wupRecords.stream().forEach(System.out::println);

    }

    public List<WUPRecord> map(File file) throws IOException {
        FileReader in = new FileReader(file);
        CSVParser csvParser = csvFormat.parse(in);

        Map<String, Integer> headerMap = csvParser.getHeaderMap();
        WUPRecordBuilder builder = new WUPRecordBuilder(headerMap);

        return csvParser.getRecords()
                .stream()
                .skip(1)
                .map(builder::buildFromCSVRecord)
                .collect(toList());
    }


    class WUPRecordBuilder {
        Map<String, Integer> headerMap;
        public WUPRecordBuilder(Map<String, Integer> headerMap) {
            this.headerMap = headerMap;
        }

        public WUPRecord buildFromCSVRecord(CSVRecord csvRecord){
            WUPRecord wupRecord = new WUPRecord();
            wupRecord.gupGipNummer = csvRecord.get(headerMap.get("GUP/GIP"));
            wupRecord.eigenNummer = csvRecord.get(headerMap.get("Eigen Nummer"));
            wupRecord.investeringsType = csvRecord.get(headerMap.get("Investeringstype"));;
            wupRecord.prioriteit = Integer.valueOf(csvRecord.get(headerMap.get("Prioriteit")));
            return wupRecord;
        }
    }
}
