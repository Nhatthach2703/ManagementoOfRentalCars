package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Validation {

    public static List<String> customerIDs = new ArrayList<>();
    public static List<String> carIDs = new ArrayList<>();
    public static List<String> carLicensePlates = new ArrayList<>();
    public static List<String> customerCitizenIdentificationNumber = new ArrayList<>();
    
    public Validation() {
    }

    //check carID
    public String validCarID(String id) {
        if (id.matches("^CAR\\d{3}$")) {
            return id;
        }
        return null;
    }

    public String validCarIDNonLoop(String id) {
        if (id.matches("^CAR\\d{3}$")) {
            if (checkCarLoopID(id)) {
                return id;
            } else {
                return null;
            }
        }
        return null;
    }

    private boolean checkCarLoopID(String carID) {
        if (carIDs.contains(carID)) {
            System.out.println("Duplicate ID: " + carID);
            return false;
        } else {
            carIDs.add(carID);
            return true;
        }
    }

    public String validCarBrand(String carBrand) {
        if (carBrand.matches("^[a-zA-Z\\s]+$")) {
            return carBrand;
        }
        return null;
    }

    public String validCarModel(String carModel) {
        if (carModel.matches("^[a-zA-Z\\s]+$")) {
            return carModel;
        }
        return null;
    }

    public String validNumberOfSeats(String numberOfSeats) {
        if (numberOfSeats.matches("^(4|7|16)$")) {
            return numberOfSeats;
        }
        return "0";
    }

    public String validRentalPrice(String rentalPrice) {
        if (rentalPrice.matches("\\d+")) {
            return rentalPrice;
        }
        return "0";
    }

    public String validNumberOfRentalDays(String numberOfRentalDays) {
        if (numberOfRentalDays.matches("\\d+")) {
            return numberOfRentalDays;
        }
        return "0";
    }

    //check ko lap id
    public String validCustomerID(String id) {
        if (id.matches("CUS\\d{3}$")) {
            return id;
        }
        return null;
    }

    public String validCustomerIDNonLoop(String id) {
        if (id.matches("^CUS\\d{3}$")) {
            if (checkCusLoopID(id)) {
                return id;
            } else {
                return null;
            }
        }
        return null;
    }

    private boolean checkCusLoopID(String customerID) {
        if (customerIDs.contains(customerID)) {
            System.out.println("Duplicate ID: " + customerID);
            return false;
        } else {
            customerIDs.add(customerID);
            return true;
        }
    }

    public String validName(String name) {
        if (name.matches("^[a-zA-Z\\s]+$")) {
            return name;
        }
        return null;
    }
    
    public String validPhone(String phone) {
        if (phone.matches("^09\\d{8}$")) {
            return phone;
        }
        return null;
    }

    public String validAge(String age) {
        if (age.matches("\\d{2}")) {
            int ageInput = Integer.parseInt(age);
            if (ageInput >= 18 && ageInput < 90) {
                return age;
            }
        }
        return "0";
    }
    public String validPayment(String payment) {
        if (payment.matches("-?\\d+(\\.\\d+)?")) {
            return payment;
        }
        return "0";
    }
    
    public String validCitizenIdentificationNumber(String citizenIdentificationNumber) {
        if (citizenIdentificationNumber.matches("^0\\d{11}$")) {
            return citizenIdentificationNumber;
        }
        return null;
    }
    
    public String validCitizenIdentificationNumberNonLoop(String citizenIdentificationNumber) {
        if (citizenIdentificationNumber.matches("^0\\d{11}$")) {
            if (checkCCCDLoop(citizenIdentificationNumber)) {
                return citizenIdentificationNumber;
            } else {
                return null;
            }
        }
        return null;
    }

    private boolean checkCCCDLoop(String citizenIdentificationNumber) {
        if (customerCitizenIdentificationNumber.contains(citizenIdentificationNumber)) {
            System.out.println("Duplicate citizen identification number: " + citizenIdentificationNumber);
            return false;
        } else {
            customerCitizenIdentificationNumber.add(citizenIdentificationNumber);
            return true;
        }
    }

    public String validLicensePlates(String licensePlates) {
        if (licensePlates.matches("^(?!0[1-9]|10|13|42|44|45|46|80|87|91|96)\\d{2}[A-Z] \\d{5}$")) {
            return licensePlates;
        }
        return null;
    }
    
    public String validLicensePlatesNonLoop(String licensePlates) {    //check bien so ko lap lai
        if (licensePlates.matches("^(?!0[1-9]|10|13|42|44|45|46|80|87|91|96)\\d{2}[A-Z] \\d{5}$")) {
            if (checkLicensePlatesLoop(licensePlates)) {
                carLicensePlates.add(licensePlates);
                return licensePlates;
            } else {
                System.out.println("Duplicate license plates: " + licensePlates);
                return null;
            }
        }
        return null;
    }

    private boolean checkLicensePlatesLoop(String licensePlates) {
        if (carLicensePlates.contains(licensePlates)) {
            return false;
        } else {
            return true;
        }
    }
    
    public String validLicensePlatesNonLoop2(String licensePlates) {    //check bien so ko lap lai cho doc file
        if (licensePlates.matches("^(?!0[1-9]|10|13|42|44|45|46|80|87|91|96)\\d{2}[A-Z] \\d{5}$")) {
            if (checkLicensePlatesLoop(licensePlates)) {
                return licensePlates;
            } else {
                return null;
            }
        }
        return null;
    }
    
    public Date validDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            Date rentDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2022); // Chỉ cho phép năm 2023 và sau đó
            if (rentDate.after(calendar.getTime())) {
                return rentDate;
            } else {
                return null;
            }
        } catch (ParseException e) {
            return null;
        }
    }
}