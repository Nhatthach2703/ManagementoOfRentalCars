
package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer {
   private String customerID;
   private String name;
   private int age;
   private String phone;
   private String citizenIdentificationNumber;
   private String licensePlates;
   private Date rentDay;
   private Date  returnDay;
   private int numberOfRentalDays;
   private double payment;

    public Customer(String customerID, String name, int age, String phone, String citizenIdentificationNumber, String licensePlates, Date rentDay, Date returnDay, int numberOfRentalDays, double payment) {
        this.customerID = customerID;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.citizenIdentificationNumber = citizenIdentificationNumber;
        this.licensePlates = licensePlates;
        this.rentDay = rentDay;
        this.returnDay = returnDay;
        this.numberOfRentalDays = numberOfRentalDays;
        this.payment = payment;
    }

   

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getCitizenIdentificationNumber() {
        return citizenIdentificationNumber;
    }

    public void setCitizenIdentificationNumber(String citizenIdentificationNumber) {
        this.citizenIdentificationNumber = citizenIdentificationNumber;
    }

    public String getLicensePlates() {
        return licensePlates;
    }

    public void setLicensePlates(String licensePlates) {
        this.licensePlates = licensePlates;
    }

    public Date getRentDay() {
        return rentDay;
    }

    public void setRentDay(Date rentDay) {
        this.rentDay = rentDay;
    }

    public Date getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(Date returnDay) {
        this.returnDay = returnDay;
    }

    public int getNumberOfRentalDays() {
        return numberOfRentalDays;
    }

    public void setNumberOfRentalDays(int numberOfRentalDays) {
        this.numberOfRentalDays = numberOfRentalDays;
    }
    
    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
//        return "CustomerID: " + customerID +
//                ", Name: " + name +
//                ", Age: " + age +
//                ", Phone: " + phone +
//                ", Citizen Identification Number: " + citizenIdentificationNumber +
//                ", License Plates: " + licensePlates +
//                ", Rent Day: " + new SimpleDateFormat("dd/MM/yyyy").format(rentDay) +
//                ", Return Day: " + new SimpleDateFormat("dd/MM/yyyy").format(returnDay) +
//                ", Rental Days: " + numberOfRentalDays +
//                ", Payment: " +String.format("%,.2f", payment);
        return String.format("| %-6s | %-20s| %-3s | %-10s | %-29s | %-14s | %-10s |  %-11s | %-21s | %-15s |", customerID, name, age, phone, citizenIdentificationNumber, licensePlates, new SimpleDateFormat("dd/MM/yyyy").format(rentDay), new SimpleDateFormat("dd/MM/yyyy").format(returnDay), numberOfRentalDays, String.format("%,.2f", payment));
    }
}
