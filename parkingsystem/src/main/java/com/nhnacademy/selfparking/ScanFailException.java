package com.nhnacademy.selfparking;

public class ScanFailException extends RuntimeException{

    public ScanFailException(String plateNumber){
        super("Vehicle license plate scan failed."+plateNumber);
    }
}
