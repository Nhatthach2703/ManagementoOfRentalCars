package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;
import model.Car;
import model.Customer;
import model.Four_seater;
import model.Seven_seater;
import model.Sixteen_seater;
import view.Validation;

public class CarRental {

    private static ArrayList<Car> cars;
    private static ArrayList<Customer> customers;
    Validation val = new Validation();
    
    public CarRental() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        readCarsFromFile("src\\car.txt");
        readCustomersFromFile("src\\customer.txt");
    }

    public void readCarsFromFile(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] carData = line.split(", ");
                String seatSts = val.validNumberOfSeats(carData[0].trim());
                int seats = Integer.parseInt(seatSts);
                String carID = val.validCarID(carData[1].trim());
                String carBrand = val.validCarBrand(carData[2].trim());
                String carModel = val.validCarModel(carData[3].trim());
                String licensePlates = val.validLicensePlates(carData[4].trim());
                String rentalPriceStr = val.validRentalPrice(carData[5].trim());
                long rentalPrice = Long.parseLong(rentalPriceStr);
                boolean beingRented = carData[6].equals("yes");

                if (seats != 0 && carID != null && carBrand != null && carModel != null && licensePlates != null && rentalPrice != 0) {
                    Car car = null;
                    if (seats == 4) {
                        car = new Four_seater(carID, carBrand, carModel, licensePlates, rentalPrice, beingRented);
                        val.carIDs.add(carID);
                        val.carLicensePlates.add(licensePlates);
                    } else if (seats == 7) {
                        car = new Seven_seater(carID, carBrand, carModel, licensePlates, rentalPrice, beingRented);
                        val.carIDs.add(carID);
                        val.carLicensePlates.add(licensePlates);
                    } else if (seats == 16) {
                        car = new Sixteen_seater(carID, carBrand, carModel, licensePlates, rentalPrice, beingRented);
                        val.carIDs.add(carID);
                        val.carLicensePlates.add(licensePlates);
                    } 
                    cars.add(car);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCustomersFromFile(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerData = line.split(", ");
                if (customerData.length == 10) {
                    String customerID = val.validCustomerID(customerData[0].trim());
                    String name = val.validName(customerData[1].trim());
                    String ageStr = val.validAge(customerData[2].trim());
                    int age = Integer.parseInt(ageStr);
                    String phone = val.validPhone(customerData[3].trim());
                    String citizenIdentificationNumber = val.validCitizenIdentificationNumber(customerData[4].trim());
                    
                    //Kiem tra xem thu bien so xe có trong đội xe hay ko
                    String checkLicensePlates = val.validLicensePlates(customerData[5].trim());
                    String licensePlates = null;
                    if (val.validLicensePlatesNonLoop2(checkLicensePlates) == null) {
                        licensePlates = checkLicensePlates;
                    } else {
                        licensePlates = null;
                    }
                    
                    Date rentDay = val.validDate(customerData[6]);
                    Date returnDay = val.validDate(customerData[7]);
                    String nodStr = val.validNumberOfRentalDays(customerData[8].trim());
                    int numberOfRentalDays = Integer.parseInt(nodStr);
                    String paymentStr = val.validPayment(customerData[9].trim());
                    double payment = Double.parseDouble(paymentStr);
                    if (customerID != null && age != 0 && name != null && phone != null && citizenIdentificationNumber != null && licensePlates != null && rentDay != null && returnDay != null && numberOfRentalDays != 0 && payment != 0) {
                        customers.add(new Customer(customerID, name, age, phone, citizenIdentificationNumber, licensePlates, rentDay, returnDay, numberOfRentalDays, payment));
                        val.customerIDs.add(customerID);
                        val.customerCitizenIdentificationNumber.add(citizenIdentificationNumber);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayRentedCars() {
        ArrayList<Car> availableCars = searchCar(car -> car.isBeingRented());
        displayCar(availableCars);
    }

    public void displayAvailableCars() {
        ArrayList<Car> availableCars = searchCar(car -> !car.isBeingRented());
        displayCar(availableCars);
    }

    public void displayAllCars() {
        displayCar(cars);
    }

    public static void displayCar(ArrayList<Car> cars) {
        System.out.println("List of Car");
        System.out.println("-----------------------------------------------------");
        System.out.println("|   ID   |    Brand     |    Model     | License plates | Rental price | Being rented |");
        if (!cars.isEmpty()) {
            cars.forEach(c -> System.out.println(c));
            System.out.println("-----------------------------------------------------");
            System.out.println("Total: " + cars.size() + " cars.");
        } else {
            System.out.println("List of car is empty!");
        }
    }

    public static ArrayList<Customer> searchCustomer(Predicate<Customer> condition) {
        ArrayList<Customer> customerSearching = new ArrayList<>();
        for (Customer customer : customers) {
            if (condition.test(customer)) {
                customerSearching.add(customer);
            }
        }
        return customerSearching;
    }

    public void displayAllCustomer() {
        displayCustomer(customers);
    }

    public static void displayCustomer(ArrayList<Customer> customers) {
        System.out.println("List of Customer");
        System.out.println("-----------------------------------------------------");
        System.out.println("|   ID   |         Name        | Age |    Phone   | Citizen identification number | License plates |  Rent day  |  Return day  | Number of rental days |     Payment     |");
        if (!customers.isEmpty()) {
            customers.forEach(c -> System.out.println(c));
            System.out.println("-----------------------------------------------------");
            System.out.println("Total: " + customers.size() + " customers.");
        } else {
            System.out.println("List of customer is empty!");
        }
    }

    public void addCustomerRentCar(String customerID, String name, int age, String phone, String citizenIdentificationNumber, String licensePlates, Date rentDay, int numberOfRentalDays) {
        Car car = findCarByLicensePlates(licensePlates);
        if (car != null && !car.isBeingRented()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            double rentalPrice = car.getRentalPrice();
            Date calculatedReturnDay = calculateReturnDay(rentDay, numberOfRentalDays);
            double calculatedPayment = rentalPrice * numberOfRentalDays;

            Customer customer = new Customer(customerID, name, age, phone, citizenIdentificationNumber, licensePlates, rentDay, calculatedReturnDay, numberOfRentalDays, calculatedPayment);
            customers.add(customer);

            car.setBeingRented(true);
            System.out.println("Customer added successfully.");
        } else {
            val.customerIDs.remove(customerID); // xóa ID khách hàng ra khỏi List
            val.customerCitizenIdentificationNumber.remove(citizenIdentificationNumber); // xóa cccd khách hàng ra khỏi List
            System.out.println("Invalid license plates or the car is already rented!");
        }
    }

    private Date calculateReturnDay(Date rentDay, int numberOfRentalDays) {
        long rentTime = rentDay.getTime();
        long returnTime = rentTime + (numberOfRentalDays * 24 * 60 * 60 * 1000);
        return new Date(returnTime);
    }

    public void removeCustomerRentCar(String licensePlates) {
        Car car = findCarByLicensePlates(licensePlates);
        if (car != null && car.isBeingRented()) {
            Customer customer = findCustomerByLicensePlates(licensePlates);
            if (customer != null) {
                customers.remove(customer);
                car.setBeingRented(false);
                val.customerIDs.remove(customer.getCustomerID()); // xóa ID khách hàng ra khỏi List
                val.customerCitizenIdentificationNumber.remove(customer.getCitizenIdentificationNumber()); // xóa cccd khách hàng ra khỏi List
                System.out.println("Customer removed successfully!");
            } else {
                System.out.println("No customer found with the given license plates!");
            }
        } else {
            System.out.println("Invalid license plates or the car is not rented!");
        }
    }

    private Car findCarByLicensePlates(String licensePlates) {
        ArrayList<Car> carSearching = searchCar(car -> car.getLicensePlates().equalsIgnoreCase(licensePlates));
        if (!carSearching.isEmpty()) {
            return carSearching.get(0);
        }
        return null;
    }

    private Customer findCustomerByLicensePlates(String licensePlates) {
        ArrayList<Customer> customersSearching = searchCustomer(customer -> customer.getLicensePlates().equalsIgnoreCase(licensePlates));
        if (!customersSearching.isEmpty()) {
            return customersSearching.get(0);
        }
        return null;
    }

    public void showCarStatisticsBySeats(int seats) {
        ArrayList<Car> carStatistics = new ArrayList<>();
        for (Car car : cars) {
            if (car instanceof Four_seater && seats == 4) {
                carStatistics.add(car);
            } else if (car instanceof Seven_seater && seats == 7) {
                carStatistics.add(car);
            } else if (car instanceof Sixteen_seater && seats == 16) {
                carStatistics.add(car);
            }
        }
        if (!carStatistics.isEmpty()) {
            displayCar(carStatistics);
        } else {
            System.out.println("There is no car with that number of seats!");
        }
    }

    public void showRevenueByDate(Date date) {
        ArrayList<Customer> customersOnDate = searchCustomer(customer -> customer.getRentDay().equals(date));
        double revenue = 0;
        for (Customer customer : customersOnDate) {
            revenue += customer.getPayment();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Revenue on " + sdf.format(date) + ": " + String.format("%,.2f", revenue));
    }

    public static ArrayList<Car> searchCar(Predicate<Car> condition) {
        ArrayList<Car> carSearching = new ArrayList<>();
        for (Car car : cars) {
            if (condition.test(car)) {
                carSearching.add(car);
            }
        }
        return carSearching;
    }

    public static void updatePhone(ArrayList<Customer> list, String newPhone) {
        list.forEach(c -> c.setPhone(newPhone));
        displayCustomer(list);
    }

    public static void updateCitizenIdentificationNumber(ArrayList<Customer> list, String newCitizenIdentificationNumber) {
        list.forEach(c -> c.setCitizenIdentificationNumber(newCitizenIdentificationNumber));
        displayCustomer(list);
    }

    public static void updateRentPrice(ArrayList<Car> list, long newRentalPrice) {
        list.forEach(c -> c.setRentalPrice(newRentalPrice));
        displayCar(list);
    }

    public void addCar(int seats, String carID, String carBrand, String carModel, String licensePlates, long rentalPrice, boolean beingRented) {
        Car newCar;
        if (seats == 4) {
            newCar = new Four_seater(carID, carBrand, carModel, licensePlates, rentalPrice, beingRented);
        } else if (seats == 7) {
            newCar = new Seven_seater(carID, carBrand, carModel, licensePlates, rentalPrice, beingRented);
        } else if (seats == 16) {
            newCar = new Sixteen_seater(carID, carBrand, carModel, licensePlates, rentalPrice, beingRented);
        } else {
            val.carLicensePlates.remove(licensePlates);//xóa biển số ra khỏi List
            val.carIDs.remove(carID); // xóa ID của xe ra khỏi List
            System.out.println("Invalid number of seats. Car not added!");
            return;
        }
        cars.add(newCar);
        System.out.println("Car added successfully!");
    }

    public void removeCar(String licensePlates) {
        ArrayList<Car> carSearching = searchCar(car -> car.getLicensePlates().equalsIgnoreCase(licensePlates));
        if (!carSearching.isEmpty()) {
            Car carToRemove = carSearching.get(0);
            if (!carToRemove.isBeingRented()) {
                cars.remove(carToRemove);
                val.carLicensePlates.remove(licensePlates);//xóa biển số ra khỏi List
                val.carIDs.remove(carToRemove.getCarID()); // xóa ID của xe ra khỏi List
                System.out.println("Car with license plates " + licensePlates + " has been removed successfully!");
            } else {
                System.out.println("Cannot remove the car. It is currently being rented!");
            }
        } else {
            System.out.println("Car with license plates " + licensePlates + " not found!");
        }
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public static boolean writeCarsToFile(String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (Car car : cars) {
                String line = "";
                if (car instanceof Four_seater) {
                    line = "4, ";
                } else if (car instanceof Seven_seater) {
                    line = "7, ";
                } else if (car instanceof Sixteen_seater) {
                    line = "16, ";
                }
                line += car.getCarID() + ", " + car.getCarBrand() + ", " + car.getCarModel() + ", " + car.getLicensePlates() + ", " + car.getRentalPrice() + ", " + (car.isBeingRented() ? "yes" : "no");//car.isBeingRented() sẽ in thàn true/false
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean writeCustomersToFile(String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (Customer customer : customers) {
                String line = customer.getCustomerID() + ", " + customer.getName() + ", " + customer.getAge() + ", " + customer.getPhone() + ", " + customer.getCitizenIdentificationNumber() + ", " + customer.getLicensePlates() + ", " + formatDate(customer.getRentDay()) + ", " + formatDate(customer.getReturnDay()) + ", " + customer.getNumberOfRentalDays() + ", " + customer.getPayment();
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
