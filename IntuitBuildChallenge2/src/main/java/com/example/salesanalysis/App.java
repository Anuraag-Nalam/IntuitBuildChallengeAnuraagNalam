package com.example.salesanalysis;

import com.example.salesanalysis.loader.CsvDataLoader;
import com.example.salesanalysis.model.SalesRecord;
import com.example.salesanalysis.service.SalesAnalysisService;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class App {

  public static void main(String[] args) {
    String csvFile = "src/main/resources/sales_data_sample.csv";

    try {
      System.out.println("Loading data from: " + csvFile);
      CsvDataLoader dataLoader = new CsvDataLoader();
      List<SalesRecord> records = dataLoader.loadRecords(csvFile);

      SalesAnalysisService analyzer = new SalesAnalysisService(records);
      System.out.println(
        "Data loaded successfully. Total records: " +
        analyzer.getRecords().size()
      );
      System.out.println("--------------------------------------------------");

      // 1. Sales by Product Line
      System.out.println("1. Sales by Product Line:");
      Map<String, Double> salesByProductLine =
        analyzer.getTotalSalesByProductLine();
      salesByProductLine.forEach((k, v) ->
        System.out.printf("   %s: $%.2f%n", k, v)
      );
      System.out.println("--------------------------------------------------");

      // 2. Revenue by Country
      System.out.println("2. Revenue by Country:");
      Map<String, Double> salesByCountry = analyzer.getTotalSalesByCountry();
      salesByCountry.forEach((k, v) -> System.out.printf("   %s: $%.2f%n", k, v)
      );
      System.out.println("--------------------------------------------------");

      // 3. Order Status Counts
      System.out.println("3. Order Status Counts:");
      Map<String, Long> statusCounts = analyzer.getOrderStatusCounts();
      statusCounts.forEach((k, v) -> System.out.printf("   %s: %d%n", k, v));
      System.out.println("--------------------------------------------------");

      // 4. Best Sales Year
      System.out.println("4. Best Sales Year:");
      analyzer
        .getBestSalesYear()
        .ifPresent(entry ->
          System.out.printf(
            "   Year: %d, Total Sales: $%.2f%n",
            entry.getKey(),
            entry.getValue()
          )
        );
      System.out.println("--------------------------------------------------");

      // 5. Monthly Sales Trends
      System.out.println("5. Monthly Sales Trends:");
      Map<Integer, Double> monthlyTrends = analyzer.getMonthlySalesTrends();
      monthlyTrends
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(entry ->
          System.out.printf(
            "   Month %d: $%.2f%n",
            entry.getKey(),
            entry.getValue()
          )
        );
      System.out.println("--------------------------------------------------");

      // 6. Top 5 Customers
      System.out.println("6. Top 5 Customers:");
      List<Map.Entry<String, Double>> topCustomers =
        analyzer.getTopNCustomersBySales(5);
      topCustomers.forEach(entry ->
        System.out.printf("   %s: $%.2f%n", entry.getKey(), entry.getValue())
      );
      System.out.println("--------------------------------------------------");

      // 7. Most Expensive Items (Top 5 by MSRP)
      System.out.println("7. Most Expensive Items (Top 5 by MSRP):");
      analyzer
        .getMostExpensiveItems(5)
        .forEach(record ->
          System.out.printf(
            "   %s (%s): MSRP $%d%n",
            record.getProductCode(),
            record.getProductLine(),
            record.getMsrp()
          )
        );
      System.out.println("--------------------------------------------------");

      // 8. Average Order Value
      System.out.println("8. Average Order Value:");
      System.out.printf("   $%.2f%n", analyzer.getAverageOrderValue());
      System.out.println("--------------------------------------------------");

      // 9. Deal Size Analysis
      System.out.println("9. Deal Size Analysis:");
      Map<String, Double> avgSalesByDealSize =
        analyzer.getAverageSalesByDealSize();
      avgSalesByDealSize.forEach((k, v) ->
        System.out.printf("   %s: $%.2f%n", k, v)
      );
      System.out.println("--------------------------------------------------");
    } catch (IOException e) {
      System.err.println("Error reading CSV file: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
