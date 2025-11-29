package com.example.salesanalysis.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SalesRecord {

  private String orderNumber;
  private int quantityOrdered;
  private double priceEach;
  private int orderLineNumber;
  private double sales;
  private String orderDate; // Keeping as String for simplicity, or could parse
  private String status;
  private int qtrId;
  private int monthId;
  private int yearId;
  private String productLine;
  private int msrp;
  private String productCode;
  private String customerName;
  private String phone;
  private String addressLine1;
  private String addressLine2;
  private String city;
  private String state;
  private String postalCode;
  private String country;
  private String territory;
  private String contactLastName;
  private String contactFirstName;
  private String dealSize;

  public SalesRecord(
    String orderNumber,
    int quantityOrdered,
    double priceEach,
    int orderLineNumber,
    double sales,
    String orderDate,
    String status,
    int qtrId,
    int monthId,
    int yearId,
    String productLine,
    int msrp,
    String productCode,
    String customerName,
    String phone,
    String addressLine1,
    String addressLine2,
    String city,
    String state,
    String postalCode,
    String country,
    String territory,
    String contactLastName,
    String contactFirstName,
    String dealSize
  ) {
    this.orderNumber = orderNumber;
    this.quantityOrdered = quantityOrdered;
    this.priceEach = priceEach;
    this.orderLineNumber = orderLineNumber;
    this.sales = sales;
    this.orderDate = orderDate;
    this.status = status;
    this.qtrId = qtrId;
    this.monthId = monthId;
    this.yearId = yearId;
    this.productLine = productLine;
    this.msrp = msrp;
    this.productCode = productCode;
    this.customerName = customerName;
    this.phone = phone;
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
    this.country = country;
    this.territory = territory;
    this.contactLastName = contactLastName;
    this.contactFirstName = contactFirstName;
    this.dealSize = dealSize;
  }

  // Getters
  public String getOrderNumber() {
    return orderNumber;
  }

  public int getQuantityOrdered() {
    return quantityOrdered;
  }

  public double getPriceEach() {
    return priceEach;
  }

  public int getOrderLineNumber() {
    return orderLineNumber;
  }

  public double getSales() {
    return sales;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public String getStatus() {
    return status;
  }

  public int getQtrId() {
    return qtrId;
  }

  public int getMonthId() {
    return monthId;
  }

  public int getYearId() {
    return yearId;
  }

  public String getProductLine() {
    return productLine;
  }

  public int getMsrp() {
    return msrp;
  }

  public String getProductCode() {
    return productCode;
  }

  public String getCustomerName() {
    return customerName;
  }

  public String getPhone() {
    return phone;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCountry() {
    return country;
  }

  public String getTerritory() {
    return territory;
  }

  public String getContactLastName() {
    return contactLastName;
  }

  public String getContactFirstName() {
    return contactFirstName;
  }

  public String getDealSize() {
    return dealSize;
  }

  @Override
  public String toString() {
    return (
      "SalesRecord{" +
      "orderNumber='" +
      orderNumber +
      '\'' +
      ", sales=" +
      sales +
      ", productLine='" +
      productLine +
      '\'' +
      ", country='" +
      country +
      '\'' +
      '}'
    );
  }
}
