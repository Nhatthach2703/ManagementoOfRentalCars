
package model;

public class Car {
private String carID;
private String carBrand;
private String carModel;
private String licensePlates;
private long rentalPrice;
private boolean beingRented;

    public Car(String carID, String carBrand, String carModel, String licensePlates, long rentalPrice, boolean beingRented) {
        this.carID = carID;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.licensePlates = licensePlates;
        this.rentalPrice = rentalPrice;
        this.beingRented = beingRented;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getLicensePlates() {
        return licensePlates;
    }

    public void setLicensePlates(String licensePlates) {
        this.licensePlates = licensePlates;
    }

    public long getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(long rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public boolean isBeingRented() {
        return beingRented;
    }

    public void setBeingRented(boolean beingRented) {
        this.beingRented = beingRented;
    }
    
    @Override
    public String toString() {
//        return "CarID: " + carID +
//                ", Brand: " + carBrand +
//                ", Model: " + carModel +
//                ", License Plates: " + licensePlates +
//                ", Rental Price: " + rentalPrice +
//                ", Being Rented: " + (beingRented ? "Yes" : "No");
        return String.format("| %-6s | %-12s | %-12s |  %-13s | %-12s | %-12s |", carID, carBrand, carModel, licensePlates, String.format("%,d", rentalPrice), (beingRented ? "Yes" : "No"));
    }
}
