package com.nhnacademy.selfparking;

public interface CarRepository {

    Car findByPlateNumber(String plateNumber);

    Car findByParkingSpace(String parkingSpace);

    Car findBySize(String size);


}
