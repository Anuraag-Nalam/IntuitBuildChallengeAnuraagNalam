package com.example.salesanalysis.service;

import com.example.salesanalysis.model.SalesRecord;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SalesAnalysisService {

  private final List<SalesRecord> records;

  public SalesAnalysisService(List<SalesRecord> records) {
    this.records = records;
  }

  public List<SalesRecord> getRecords() {
    return records;
  }

  // 1. Sales by Product Line (Summing revenue per category)
  public Map<String, Double> getTotalSalesByProductLine() {
    return records
      .stream()
      .collect(
        Collectors.groupingBy(
          SalesRecord::getProductLine,
          Collectors.summingDouble(SalesRecord::getSales)
        )
      );
  }

  // 2. Revenue by Country (Summing revenue per geographic region)
  public Map<String, Double> getTotalSalesByCountry() {
    return records
      .stream()
      .collect(
        Collectors.groupingBy(
          SalesRecord::getCountry,
          Collectors.summingDouble(SalesRecord::getSales)
        )
      );
  }

  // 3. Order Status Counts (Counting how many orders are Shipped, Cancelled, etc.)
  public Map<String, Long> getOrderStatusCounts() {
    return records
      .stream()
      .collect(
        Collectors.groupingBy(SalesRecord::getStatus, Collectors.counting())
      );
  }

  // 4. Best Sales Year (Finding the year with the highest revenue)
  public Optional<Map.Entry<Integer, Double>> getBestSalesYear() {
    Map<Integer, Double> salesByYear = records
      .stream()
      .collect(
        Collectors.groupingBy(
          SalesRecord::getYearId,
          Collectors.summingDouble(SalesRecord::getSales)
        )
      );

    return salesByYear.entrySet().stream().max(Map.Entry.comparingByValue());
  }

  // 5. Monthly Sales Trends (Grouping sales by month to see seasonality)
  public Map<Integer, Double> getMonthlySalesTrends() {
    return records
      .stream()
      .collect(
        Collectors.groupingBy(
          SalesRecord::getMonthId,
          Collectors.summingDouble(SalesRecord::getSales)
        )
      );
  }

  // 6. Top N Customers.
  public List<Map.Entry<String, Double>> getTopNCustomersBySales(int n) {
    Map<String, Double> salesByCustomer = records
      .stream()
      .collect(
        Collectors.groupingBy(
          SalesRecord::getCustomerName,
          Collectors.summingDouble(SalesRecord::getSales)
        )
      );

    return salesByCustomer
      .entrySet()
      .stream()
      .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
      .limit(n)
      .collect(Collectors.toList());
  }

  // 7. Most Expensive Items
  public List<SalesRecord> getMostExpensiveItems(int n) {
    return records
      .stream()
      .sorted(Comparator.comparingDouble(SalesRecord::getMsrp).reversed())
      .limit(n)
      .collect(Collectors.toList());
  }

  // 8. Average Order Value
  public double getAverageOrderValue() {
    return records
      .stream()
      .mapToDouble(SalesRecord::getSales)
      .average()
      .orElse(0.0);
  }

  // 9. Deal Size Analysis (Comparing average sales for Small vs. Medium vs. Large deals)
  public Map<String, Double> getAverageSalesByDealSize() {
    return records
      .stream()
      .collect(
        Collectors.groupingBy(
          SalesRecord::getDealSize,
          Collectors.averagingDouble(SalesRecord::getSales)
        )
      );
  }
}
