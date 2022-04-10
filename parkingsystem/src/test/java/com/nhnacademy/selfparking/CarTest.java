package com.nhnacademy.selfparking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarTest {
    private Car car;
    private CarParkingService carParkingService;
    private CarRepository repository;

    @BeforeEach
    void setUp() {
        car = Car.plateNumber("485나8282", null, 5000, "midSize");
        repository = mock(CarRepository.class);
        carParkingService = new CarParkingService(repository);

    }

    @DisplayName("차량번호 스캔")
    @Test
    void scanCarPlateNumber(){
        Car scanPlateNumber = Car.plateNumber("485나8282", car.parkingSpace, car.money, car.size);

        assertThatThrownBy(() -> car.scan(scanPlateNumber))
            .isInstanceOf(ScanFailException.class)
            .hasMessageContainingAll("failed");
    }

    @DisplayName("A-1에 주차한다.")
    @Test
    void parking() {
        String parkingSpace = "A-1";
        String carPlateNumber = "485나8282";
        int money = 5000;
        String size = "compact";


        Car carr = new Car(carPlateNumber,parkingSpace, money, size);
        when(repository.findByPlateNumber(carPlateNumber)).thenReturn(carr);

        Car parking = carParkingService.parking(carPlateNumber, parkingSpace, size);

        assertThat(parking.getCarPlateNumber()).isEqualTo(carPlateNumber);
        assertThat(parking.getParkingSpace()).isEqualTo(parkingSpace);

        verify(repository).findByPlateNumber(carPlateNumber);
    }

    @DisplayName("주차장에서 나간다. 50분 주차 금액인 2000원 정상결제")
    @Test
    void exitAfterPayment(){
        //가진돈 - 나가기 필요한 금액 먼저 계산
        // 타임 패키지 사용 요금 부과 후 계산
        Car fee = Car.plateNumber("485나8282", null, 2000, "midSize");
        Car result = car.payment(fee);

        assertEquals(3000, result.getMoney());

    }

    @DisplayName("돈이 없으면 주차장에서 나갈 수 없다.")
    @Test
    void exitAfterPayment_throwIllegalArgumentException() {
        Car highFee = Car.plateNumber("485나8282", null, 10000, "midSize");

        assertThatThrownBy(() -> car.payment(highFee))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("lack of money");
    }

    @DisplayName("대형차는 주차 할 수 없다.")
    @Test
    void fullSize_parkingTest() {
        String parkingSpace = "A-1";
        String carPlateNumber = "485나8282";
        int money = 100000;
        String size = "fullSize";

        Car fullSizeCar = new Car(carPlateNumber,parkingSpace, money, size);
        when(repository.findByPlateNumber(carPlateNumber)).thenReturn(fullSizeCar);
        when(repository.findBySize(size)).thenReturn(fullSizeCar);

        Car fullSize_parkingTest = carParkingService.parking(carPlateNumber, parkingSpace, size);
        assertEquals("fullSize", fullSize_parkingTest.getSize());

        verify(repository).findBySize(size);
    }

    @DisplayName("경차면 50% 할인")
    @Test
    void exitAfterPaymentIfCompect(){
        Car fee = Car.plateNumber("485나8282", null, 8000, "compact");
        Car result = car.payment(fee);

        assertEquals(1000, result.getMoney());

    }



}