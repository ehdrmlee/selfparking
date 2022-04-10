package com.nhnacademy.selfparking;

import java.util.Objects;

public class Car {
    String carPlateNumber;
    String parkingSpace;
    int money;
    String size;

    public Car(String carPlateNumber, String parkingSpace, int money, String size) {
        this.carPlateNumber = carPlateNumber;
        this.parkingSpace = parkingSpace;
        this.money = money;
        this.size = size;
    }

    public static Car plateNumber(String carPlateNumber, String parkingSpace, int money, String size) {
        return new Car(carPlateNumber, parkingSpace, money, size);
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }
    public String getParkingSpace() {
        return parkingSpace;
    }

    public int getMoney() {
        return money;
    }

    public String getSize() {
        return size;
    }

    public Car scan(Car scanPlateNumber) {
        if(!this.carPlateNumber.equals(scanPlateNumber.getCarPlateNumber())) {
            return new Car(this.getCarPlateNumber(), parkingSpace, money, size);
        }
        throw new ScanFailException("Vehicle license plate scan failed");
    }

    public Car payment(Car car) {
        if (car.getSize().equals("compact")){
            return new Car(this.carPlateNumber,this.parkingSpace,this.money - (car.getMoney()/2), this.size);
        }

        if (this.money < car.getMoney()) {
            throw new IllegalArgumentException("lack of money");
        }
        return new Car(this.carPlateNumber,this.parkingSpace,this.money - car.getMoney(), this.size);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(carPlateNumber, car.carPlateNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carPlateNumber);
    }


}
