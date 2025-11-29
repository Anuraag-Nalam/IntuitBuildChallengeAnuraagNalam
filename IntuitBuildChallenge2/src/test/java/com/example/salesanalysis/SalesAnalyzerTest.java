package com.example.salesanalysis;

import static org.junit.jupiter.api.Assertions.*;

import com.example.salesanalysis.model.SalesRecord;
import com.example.salesanalysis.service.SalesAnalysisService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SalesAnalyzerTest {

  private SalesAnalysisService analyzer;

  @BeforeEach
  void setUp() {
    // Create dummy data for testing
    List<SalesRecord> records = Arrays.asList(
      createRecord("Classic Cars", "Small", "USA", 1000.0, "Customer A", 2003),
      createRecord("Classic Cars", "Medium", "USA", 2000.0, "Customer B", 2003),
      createRecord("Motorcycles", "Small", "France", 500.0, "Customer A", 2004),
      createRecord(
        "Motorcycles",
        "Large",
        "France",
        1500.0,
        "Customer C",
        2004
      ),
      createRecord("Planes", "Medium", "USA", 3000.0, "Customer B", 2005)
    );
    analyzer = new SalesAnalysisService(records);
  }

  private SalesRecord createRecord(
    String productLine,
    String dealSize,
    String country,
    double sales,
    String customerName,
    int year
  ) {
    return new SalesRecord(
      "1",
      1,
      10.0,
      1,
      sales,
      "1/1/2003",
      "Shipped",
      1,
      1,
      year,
      productLine,
      100,
      "S10_1",
      customerName,
      "555-1234",
      "Address",
      "",
      "City",
      "State",
      "12345",
      country,
      "NA",
      "Doe",
      "John",
      dealSize
    );
  }

  @Test
  void testGetTotalSalesByProductLine() {
    Map<String, Double> result = analyzer.getTotalSalesByProductLine();
    assertEquals(3000.0, result.get("Classic Cars"));
    assertEquals(2000.0, result.get("Motorcycles"));
    assertEquals(3000.0, result.get("Planes"));
  }

  @Test
  void testGetTotalSalesByCountry() {
    Map<String, Double> result = analyzer.getTotalSalesByCountry();
    assertEquals(6000.0, result.get("USA")); // 1000 + 2000 + 3000
    assertEquals(2000.0, result.get("France")); // 500 + 1500
  }

  @Test
  void testGetOrderStatusCounts() {
    Map<String, Long> result = analyzer.getOrderStatusCounts();
    assertEquals(5, result.get("Shipped"));
  }

  @Test
  void testGetBestSalesYear() {
    var result = analyzer.getBestSalesYear();
    assertTrue(result.isPresent());
    assertEquals(2003, result.get().getKey());
    assertEquals(3000.0, result.get().getValue());
  }

  @Test
  void testGetMonthlySalesTrends() {
    Map<Integer, Double> result = analyzer.getMonthlySalesTrends();
    assertEquals(8000.0, result.get(1)); // All dummy records have month 1
  }

  @Test
  void testGetTopNCustomersBySales() {
    List<Map.Entry<String, Double>> result = analyzer.getTopNCustomersBySales(
      2
    );
    assertEquals(2, result.size());
    assertEquals("Customer B", result.get(0).getKey());
    assertEquals(5000.0, result.get(0).getValue()); // 2000 + 3000

    // Customer A: 1000 + 500 = 1500
    // Customer C: 1500
    // Since values are equal, the order is not guaranteed without secondary sort.
    // We can check that the second one is either A or C with 1500.
    String secondCustomer = result.get(1).getKey();
    assertTrue(
      secondCustomer.equals("Customer A") || secondCustomer.equals("Customer C")
    );
    assertEquals(1500.0, result.get(1).getValue());
  }

  @Test
  void testGetMostExpensiveItems() {
    List<SalesRecord> result = analyzer.getMostExpensiveItems(1);
    assertEquals(1, result.size());
    assertEquals(100, result.get(0).getMsrp()); // All dummy records have MSRP 100
  }

  @Test
  void testGetAverageOrderValue() {
    double result = analyzer.getAverageOrderValue();
    assertEquals(1600.0, result); // (1000+2000+500+1500+3000) / 5 = 8000 / 5 = 1600
  }

  @Test
  void testGetAverageSalesByDealSize() {
    Map<String, Double> result = analyzer.getAverageSalesByDealSize();
    assertEquals(750.0, result.get("Small")); // (1000 + 500) / 2
    assertEquals(2500.0, result.get("Medium")); // (2000 + 3000) / 2
    assertEquals(1500.0, result.get("Large"));
  }
}
