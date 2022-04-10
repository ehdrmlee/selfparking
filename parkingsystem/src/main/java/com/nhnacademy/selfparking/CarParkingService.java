package com.nhnacademy.selfparking;

public class CarParkingService {
    private final CarRepository repository;

    public CarParkingService(CarRepository repository) {
        this.repository = repository;
    }


    public Car parking(String carPlateNumber, String parkingSpace, String carSize) {

        Car number =repository.findByPlateNumber(carPlateNumber);
        Car space =repository.findByParkingSpace(parkingSpace);
        Car size = repository.findBySize(carSize);

        if(number == null){
            return null;
        }

        if(carSize.equals("fullSize")){
            return size;
        }

        return number;

    }
}
