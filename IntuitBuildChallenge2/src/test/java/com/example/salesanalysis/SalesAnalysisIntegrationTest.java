package com.example.salesanalysis;

import static org.junit.jupiter.api.Assertions.*;

import com.example.salesanalysis.loader.CsvDataLoader;
import com.example.salesanalysis.model.SalesRecord;
import com.example.salesanalysis.service.SalesAnalysisService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SalesAnalysisIntegrationTest {

  @TempDir
  Path tempDir;

  @Test
  void testEndToEndAnalysis() throws IOException {
    // 1. Create a sample CSV file with known data
    Path csvPath = tempDir.resolve("test_data.csv");
    String csvContent =
      "ORDERNUMBER,QUANTITYORDERED,PRICEEACH,ORDERLINENUMBER,SALES,ORDERDATE,STATUS,QTR_ID,MONTH_ID,YEAR_ID,PRODUCTLINE,MSRP,PRODUCTCODE,CUSTOMERNAME,PHONE,ADDRESSLINE1,ADDRESSLINE2,CITY,STATE,POSTALCODE,COUNTRY,TERRITORY,CONTACTLASTNAME,CONTACTFIRSTNAME,DEALSIZE\n" +
      "10107,30,95.7,2,2871.0,2/24/2003 0:00,Shipped,1,2,2003,Motorcycles,95,S10_1678,Land of Toys Inc.,2125557818,897 Long Airport Avenue,,NYC,NY,10022,USA,NA,Yu,Kwai,Small\n" +
      "10121,34,81.35,5,2765.9,05-07-2003 00:00,Shipped,2,5,2003,Motorcycles,95,S10_1678,Reims Collectables,26.47.1555,59 rue de l'Abbaye,,Reims,,51100,France,EMEA,Henriot,Paul,Small";
    Files.writeString(csvPath, csvContent);

    // 2. Load data using CsvDataLoader (Testing the IO layer)
    CsvDataLoader loader = new CsvDataLoader();
    List<SalesRecord> records = loader.loadRecords(csvPath.toString());

    // Verify loading correctness
    assertEquals(2, records.size(), "Should have loaded 2 records");
    assertEquals("Land of Toys Inc.", records.get(0).getCustomerName());
    assertEquals(2871.0, records.get(0).getSales());

    // 3. Analyze data using SalesAnalysisService (Testing the Service layer integration)
    SalesAnalysisService service = new SalesAnalysisService(records);

    // Verify analysis results
    Map<String, Double> salesByCountry = service.getTotalSalesByCountry();
    assertEquals(2871.0, salesByCountry.get("USA"), 0.01);
    assertEquals(2765.9, salesByCountry.get("France"), 0.01);

    Map<String, Double> salesByProductLine =
      service.getTotalSalesByProductLine();
    assertEquals(5636.9, salesByProductLine.get("Motorcycles"), 0.01); // 2871.0 + 2765.9
  }
}
