package view;

import controller.CarRental;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import model.Car;
import model.Customer;

public class CarRentalManager extends Menu<String> {

    private CarRental carRental = new CarRental();
    Scanner scanner = new Scanner(System.in);
    Validation val = new Validation();
    
    static String[] menu = {"Display all cars", "Display rented cars", "Display available cars", "Add/Remove customer rent car",
        "Display all customer", "Car Statistics", "Statistics Revenue by Date", "Search car", "Update Customer Information", "Search customer", "Update Car Information", "Write data to file"};

    public CarRentalManager() {
        super("Company Management System", menu, "Exit");
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1:
                carRental.displayAllCars();
                break;
            case 2:
                carRental.displayRentedCars();
                break;
            case 3:
                carRental.displayAvailableCars();
                break;
            case 4:
                addRemoveCustomerMenu();
                break;
            case 5:
                carRental.displayAllCustomer();
                break;
            case 6:
                carStatisticsMenu();
                break;
            case 7:
                showRevenueByDateMenu();
                break;
            case 8:
                searchCar();
                break;
            case 9:
                updateCustomerInformationMenu();
                break;
            case 10:
                searchCustomer();
                break;
            case 11:
                updateCarInformationMenu();
                break;
            case 12:
                writeDataToFile();
                break;
//            case 0:
//                System.out.println("Exit!");
//                break;
        }
        System.out.println();
    }

    private void addRemoveCustomerMenu() {
        String[] mAdd = {"Add customer rent car", "Remove customer rent car"};
        Menu m = new Menu("\nAdd/remove customer rent car", mAdd, "Return to main menu") {
            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        addCustomer();
                        break;
                    case 2:
                        removeCustomer();
                        break;
                }
            }
        };
        m.run();
    }

    private void carStatisticsMenu() {
        String[] mStatistics = {"Show number of 4-seater cars", "Show number of 7-seater cars", "Show number of 16-seater cars"};
        Menu m = new Menu("\nCAR Statistics", mStatistics, "Return to main menu") {
            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        carRental.showCarStatisticsBySeats(4);
                        break;
                    case 2:
                        carRental.showCarStatisticsBySeats(7);
                        break;
                    case 3:
                        carRental.showCarStatisticsBySeats(16);
                        break;
                }
            }
        };
        m.run();
    }

    private void addCustomer() {
        String customerID = null, name = null, phone = null, citizenIdentificationNumber = null, licensePlates = null, rentDayStr = null;
        int age = 0, numberOfRentalDays = 0;

        while (customerID == null) {
            System.out.print("Enter customer ID (CUSXXX): ");
            customerID = val.validCustomerIDNonLoop(scanner.nextLine().trim());
        }
        while (name == null) {
            System.out.print("Enter name: ");
            name = val.validName(scanner.nextLine().trim());
        }
        String validatedAge = "0";
        while (validatedAge == "0") {
            System.out.print("Enter customer age: ");
            validatedAge = val.validAge(scanner.nextLine().trim());
        }
        age = Integer.parseInt(validatedAge);
        while (phone == null) {
            System.out.print("Enter phone number(09xxxxxxxx): ");
            phone = val.validPhone(scanner.nextLine().trim());
        }
        while (citizenIdentificationNumber == null) {
            System.out.print("Enter customer citizen identification number: ");
            citizenIdentificationNumber = val.validCitizenIdentificationNumberNonLoop(scanner.nextLine().trim());
        }
        while (licensePlates == null) {
            System.out.print("Enter license plates of the car to rent: ");
            licensePlates = val.validLicensePlates(scanner.nextLine().trim());
        }
        Date rentDay = null;
        boolean isRentDayValid = false;

        while (!isRentDayValid) {
            System.out.print("Enter rent day (dd/MM/yyyy): ");
            rentDayStr = scanner.nextLine().trim();
            rentDay = val.validDate(rentDayStr);

            if (rentDay == null) {
                System.out.println("Invalid date format! Please try again.");
            } else {
                isRentDayValid = true;
            }
        }
        String validatedNumberOfRentalDays = "0";
        while (validatedNumberOfRentalDays == "0") {
            System.out.print("Enter number of rental days: ");
            validatedNumberOfRentalDays = val.validNumberOfRentalDays(scanner.nextLine().trim());
        }
        numberOfRentalDays = Integer.parseInt(validatedNumberOfRentalDays);
        carRental.addCustomerRentCar(customerID, name, age, phone, citizenIdentificationNumber, licensePlates, rentDay, numberOfRentalDays);
    }

    private void removeCustomer() {
        String licensePlates = null;
        while (licensePlates == null) {
            System.out.print("Enter license plates of the car to return: ");
            licensePlates = val.validLicensePlates(scanner.nextLine().trim());
        }
        if (licensePlates != null) {
            carRental.removeCustomerRentCar(licensePlates);
        }
    }
        

    private void showRevenueByDateMenu() {
        boolean validDateEntered = false;
        Date date = null;

        while (!validDateEntered) {
            System.out.print("Enter the date to show revenue (dd/MM/yyyy): ");
            String dateStr = scanner.nextLine().trim();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);

            try {
                date = dateFormat.parse(dateStr);
                validDateEntered = true;
            } catch (ParseException e) {
                System.out.println("Invalid date format! Please enter the date again.");
            }
        }

        carRental.showRevenueByDate(date);
    }

    private void searchCar() {
        String[] mSearch = {"By car brand", "By car model", "By license plates", "By car ID"};
        Menu m = new Menu("\nSearch car", mSearch, "Return to main menu") {
            ArrayList<Car> result = null;

            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        while (true) {
                            System.out.print("Enter car brand: ");
                            String brand = scanner.nextLine().trim();
                            String validatedBrand = val.validCarBrand(brand);
                            if (validatedBrand != null) {
                                result = carRental.searchCar(c -> c.getCarBrand().equalsIgnoreCase(validatedBrand));
                                break;
                            }
                        }
                        break;
                    case 2:
                        while (true) {
                            System.out.print("Enter car model: ");
                            String model = scanner.nextLine().trim();
                            String validatedModel = val.validCarModel(model);
                            if (validatedModel != null) {
                                result = carRental.searchCar(c -> c.getCarModel().equalsIgnoreCase(validatedModel));
                                break;
                            }
                        }
                        break;
                    case 3:
                        while (true) {
                            System.out.print("Enter car license plates: ");
                            String licensePlates = scanner.nextLine().trim();
                            String validatedLicensePlates = val.validLicensePlates(licensePlates);
                            if (validatedLicensePlates != null) {
                                result = carRental.searchCar(c -> c.getLicensePlates().equalsIgnoreCase(licensePlates));
                                break;
                            }
                        }
                        break;
                    case 4:
                        while (true) {
                            System.out.print("Enter car ID: ");
                            String id = scanner.nextLine().trim();
                            String validatedID = val.validCarID(id);
                            if (validatedID != null) {
                                result = carRental.searchCar(c -> c.getCarID().equalsIgnoreCase(id));
                                break;
                            }
                        }
                        break;
                    default:
                        return;
                }
                if (!result.isEmpty()) {
                    carRental.displayCar(result);
                } else {
                    System.out.println("Can't found any car!");
                }
            }
        };
        m.run();
    }

    private void searchCustomer() {
        String[] mSearch = {"By customer ID", "By customer name", "By customer citizen identification number", "By rental car license plate", "By customer rent day"};
        Menu m = new Menu("\nSearch customer", mSearch, "Return to main menu") {
            ArrayList<Customer> result = null;

            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        while (true) {
                            System.out.print("Enter customer ID: ");
                            String id = scanner.nextLine().trim();
                            String validatedID = val.validCustomerID(id);
                            if (validatedID != null) {
                                result = carRental.searchCustomer(customer -> customer.getCustomerID().equalsIgnoreCase(id));
                                break;
                            }
                        }
                        break;
                    case 2:
                        while (true) {
                            System.out.print("Enter customer name: ");
                            String name = scanner.nextLine().trim();
                            String validatedName = val.validName(name);
                            if (validatedName != null) {
                                result = carRental.searchCustomer(c -> c.getName().equalsIgnoreCase(name.trim()));
                                break;
                            }
                        }
                        break;
                    case 3:
                        while (true) {
                            System.out.print("Enter customer citizen identification number: ");
                            String nameCitizenIdentificationNumber = scanner.nextLine().trim();
                            String validatedNameCitizenIdentificationNumber = val.validCitizenIdentificationNumber(nameCitizenIdentificationNumber.trim());
                            if (validatedNameCitizenIdentificationNumber != null) {
                                result = carRental.searchCustomer(c -> c.getCitizenIdentificationNumber().equalsIgnoreCase(nameCitizenIdentificationNumber.trim()));
                                break;
                            }
                        }
                        break;
                    case 4:
                        while (true) {
                            System.out.print("Enter rental car license plates: ");
                            String licensePlates = scanner.nextLine().trim();
                            String validatedLicensePlates = val.validLicensePlates(licensePlates.trim());
                            if (validatedLicensePlates != null) {
                                result = carRental.searchCustomer(c -> c.getLicensePlates().equalsIgnoreCase(licensePlates));
                                break;
                            }
                        }
                        break;
                    case 5:
                        while (true) {
                            System.out.print("Enter rent day: ");
                            Date rentDay = val.validDate(scanner.nextLine().trim());
                            if (rentDay != null) {
                                result = carRental.searchCustomer(c -> c.getRentDay().equals(rentDay));
                                break;
                            }
                        }
                        break;
                    default:
                        return;
                }
                if (!result.isEmpty()) {
                    carRental.displayCustomer(result);
                } else {
                    System.out.println("Can't found any customer!");
                }
            }
        };
        m.run();
    }

    private void updateCustomerInformationMenu() {
        String[] mUpdate = {"Update phone", "Update citizen identification number"};
        Menu m = new Menu("\nUpdate customer information", mUpdate, "Return to main menu") {
            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        updatePhone();
                        break;
                    case 2:
                        updateCitizenIdentificationNumber();
                        break;
                }
            }
        };
        m.run();
    }

    private void updatePhone() {
        String id = null;
        while (id == null) {
            System.out.print("Enter customer ID: ");
            id = val.validCustomerID(scanner.nextLine().trim());
        }
        String idSearch = id;
        ArrayList<Customer> list = CarRental.searchCustomer(c -> c.getCustomerID().equalsIgnoreCase(idSearch));
        
        if (!list.isEmpty()) {
            String newPhone = null;
            while (newPhone == null) {
                System.out.print("Enter new phone (09xxxxxxxx): ");
                newPhone = val.validPhone(scanner.nextLine().trim());
            }
            if (newPhone != null) {
                CarRental.updatePhone(list, newPhone);
                System.out.println("Update phone successful!");
            } else {
                System.out.println("Update phone fail!");
            }
        } else {
            System.out.println("Can't find any customers with the ID!");
        }
    }

    private void updateCitizenIdentificationNumber() {
        String id = null;
        while (id == null) {
            System.out.print("Enter customer ID: ");
            id = val.validCustomerID(scanner.nextLine().trim()); 
        }
        String idSearch = id;
        ArrayList<Customer> list = CarRental.searchCustomer(c -> c.getCustomerID().equalsIgnoreCase(idSearch));
        if (!list.isEmpty()) {
            String newCitizenIdentificationNumber = null;
            while (newCitizenIdentificationNumber == null) {
                System.out.print("Enter new citizen identification number: ");
                newCitizenIdentificationNumber = val.validCitizenIdentificationNumber(scanner.nextLine().trim());

            }
            if (newCitizenIdentificationNumber != null) {
                CarRental.updateCitizenIdentificationNumber(list, newCitizenIdentificationNumber);
                System.out.println("Update citizen identification number successful!");
            } else {
                System.out.println("Update citizen identification number fail!");
            }
        } else {
            System.out.println("Can't found any customers with the ID!");
        }
    }

    private void updateCarInformationMenu() {
        String[] mUpdate = {"Update rent price", "Add car ", "remove car"};
        Menu m = new Menu("\nUpdate car information", mUpdate, "Return to main menu") {
            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        updateRentalPrice();
                        break;
                    case 2:
                        addCar();
                        break;
                    case 3:
                        removeCar();
                        break;
                }
            }
        };
        m.run();
    }

    private void updateRentalPrice() {
        String id = null;
        while (id == null) {
            System.out.print("Enter car ID: ");
            id = val.validCarID(scanner.nextLine().trim()); 
        }
        String idSearch = id;
        ArrayList<Car> list = carRental.searchCar(c -> c.getCarID().equalsIgnoreCase(idSearch));
        if (!list.isEmpty()) {
            String newRentalPriceStr = "0";
            while (newRentalPriceStr == "0") {
                System.out.print("Enter new rental price: ");
                newRentalPriceStr = val.validRentalPrice(scanner.nextLine().trim());
            }
            if (newRentalPriceStr != "0") {
                long newRentalPrice = Long.parseLong(newRentalPriceStr);
                CarRental.updateRentPrice(list, newRentalPrice);
                System.out.println("Update new rental successful!");
            } else {
                System.out.println("Update new rental fail!");
            }
        } else {
            System.out.println("Can't found any cars with the ID!");
        }
    }

    private void addCar() {
        String carID = null, carBrand = null, carModel = null, licensePlates = null;
        int seats = 0;
        String validatedSeats = "0";
        while (validatedSeats == "0") {
            System.out.print("Enter the number of seats: ");
            if (scanner.hasNextInt()) {
                seats = scanner.nextInt();
                validatedSeats = val.validNumberOfSeats(Integer.toString(seats));
                scanner.nextLine();
            } else {
                scanner.nextLine();
            }
        }
        while (carID == null) {
            System.out.print("Enter the car ID: ");
            carID = val.validCarIDNonLoop(scanner.nextLine().trim());
        }
        while (carBrand == null) {
            System.out.print("Enter the car brand: ");
            carBrand = val.validCarBrand(scanner.nextLine().trim());
        }
        while (carModel == null) {
            System.out.print("Enter the car model: ");
            carModel = val.validCarModel(scanner.nextLine().trim());
        }
        while (licensePlates == null) {
            System.out.print("Enter the license plates: ");
            licensePlates = val.validLicensePlatesNonLoop(scanner.nextLine().trim());
        }

        Long rentalPrice = null;
        boolean isValidRentalPrice = false;

        while (!isValidRentalPrice) {
            System.out.print("Enter the rental price: ");
            String rentalPriceStr = scanner.nextLine().trim();

            try {
                String validatedRentalPrice = val.validRentalPrice(rentalPriceStr.trim());
                if (validatedRentalPrice != "0") {
                    rentalPrice = Long.parseLong(validatedRentalPrice);
                    isValidRentalPrice = true; // Đánh dấu giá trị hợp lệ, thoát khỏi vòng lặp
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid rental price format! Please try again.");
            }
        }

        boolean beingRented = false;
        carRental.addCar(seats, carID, carBrand, carModel, licensePlates, rentalPrice, beingRented);
    }

    private void removeCar() {
        String licensePlates = null;
        while (licensePlates == null) {
            System.out.print("Enter the license plates: ");
            licensePlates = val.validLicensePlates(scanner.nextLine().trim());
        }
        if (licensePlates != null) {
            carRental.removeCar(licensePlates);
        }
    }

    private void writeDataToFile() {
        if (CarRental.writeCustomersToFile("src\\Customer1.txt") && CarRental.writeCarsToFile("src\\Car1.txt")) {
            System.out.println("Write file successful!");
        } else {
            System.out.println("Fail to write file!");
        }
    }
}