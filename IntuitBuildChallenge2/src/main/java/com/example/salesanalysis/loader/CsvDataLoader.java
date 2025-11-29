package com.example.salesanalysis.loader;

import com.example.salesanalysis.model.SalesRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvDataLoader {

  public List<SalesRecord> loadRecords(String csvFilePath) throws IOException {
    List<SalesRecord> loadedRecords = new ArrayList<>();
    try (
      Reader reader = new FileReader(csvFilePath);
      CSVParser csvParser = new CSVParser(
        reader,
        CSVFormat.DEFAULT.builder()
          .setHeader()
          .setSkipHeaderRecord(true)
          .setIgnoreHeaderCase(true)
          .setTrim(true)
          .build()
      )
    ) {
      for (CSVRecord csvRecord : csvParser) {
        SalesRecord record = new SalesRecord(
          csvRecord.get("ORDERNUMBER"),
          parseInt(csvRecord.get("QUANTITYORDERED")),
          parseDouble(csvRecord.get("PRICEEACH")),
          parseInt(csvRecord.get("ORDERLINENUMBER")),
          parseDouble(csvRecord.get("SALES")),
          csvRecord.get("ORDERDATE"),
          csvRecord.get("STATUS"),
          parseInt(csvRecord.get("QTR_ID")),
          parseInt(csvRecord.get("MONTH_ID")),
          parseInt(csvRecord.get("YEAR_ID")),
          csvRecord.get("PRODUCTLINE"),
          parseInt(csvRecord.get("MSRP")),
          csvRecord.get("PRODUCTCODE"),
          csvRecord.get("CUSTOMERNAME"),
          csvRecord.get("PHONE"),
          csvRecord.get("ADDRESSLINE1"),
          csvRecord.get("ADDRESSLINE2"),
          csvRecord.get("CITY"),
          csvRecord.get("STATE"),
          csvRecord.get("POSTALCODE"),
          csvRecord.get("COUNTRY"),
          csvRecord.get("TERRITORY"),
          csvRecord.get("CONTACTLASTNAME"),
          csvRecord.get("CONTACTFIRSTNAME"),
          csvRecord.get("DEALSIZE")
        );
        loadedRecords.add(record);
      }
    }
    return loadedRecords;
  }

  private int parseInt(String value) {
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  private double parseDouble(String value) {
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }
}
