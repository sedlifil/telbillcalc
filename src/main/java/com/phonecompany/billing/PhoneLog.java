package com.phonecompany.billing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PhoneLog {
    private final long phone;
    private final String startCallTimestamp;
    private final String endCallTimestamp;
    private final LocalDateTime startCallDateTime;
    private final LocalDateTime endCallDateTime;
    private final long callDurationInSeconds;
    private final long callDurationInMinutes;
    private final BigDecimal calculatedCallPrince;

    private final static int HOUR_TO_MINUTES = 60;
    private final static int EXPENSIVE_TARIFF_STARTING_POINT_IN_MIN = 8 * 60;
    private final static int EXPENSIVE_TARIFF_ENDING_POINT_IN_MIN = 16 * 60;
    private final static int HIGHER_TARIFF_DURATION_MINUTES = 5;

    private final static double HIGHER_TARIFF_PRICE = 1;
    private final static double NORMAL_TARIFF_PRICE = 0.5;
    private final static double CHEAPER_TARIFF_PRICE = 0.2;


    public PhoneLog(long phone, String startCallTimestamp, String endCallTimestamp) {
        this.phone = phone;
        this.startCallTimestamp = startCallTimestamp;
        this.endCallTimestamp = endCallTimestamp;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        startCallDateTime = LocalDateTime.parse(startCallTimestamp, formatter);
        endCallDateTime = LocalDateTime.parse(endCallTimestamp, formatter);
        callDurationInSeconds = callDurationInSeconds();
        callDurationInMinutes = callDurationInMinutes();
        calculatedCallPrince = calculateCallPrice();
    }

    private long callDurationInSeconds(){
        return ChronoUnit.SECONDS.between(startCallDateTime, endCallDateTime);
    }

    private long callDurationInMinutes(){
        if(callDurationInSeconds % HOUR_TO_MINUTES > 0)
            return callDurationInSeconds / HOUR_TO_MINUTES + 1;
        return callDurationInSeconds / HOUR_TO_MINUTES;
    }

    public BigDecimal calculateCallPrice(){
        BigDecimal priceSum = new BigDecimal("0");
        int startingCallInMinutes = startCallDateTime.getHour() * HOUR_TO_MINUTES + startCallDateTime.getMinute();
        // number of minutes in higher tariff with max of HIGHER_TARIFF_DURATION_MINUTES
        long higherTariffCallDurationMinutes = Math.min(callDurationInMinutes, HIGHER_TARIFF_DURATION_MINUTES);
        long restOfCallDurationMinutes = callDurationInMinutes - HIGHER_TARIFF_DURATION_MINUTES > 0 ? callDurationInMinutes - HIGHER_TARIFF_DURATION_MINUTES : 0;

        for(int i = startingCallInMinutes; i < startingCallInMinutes + higherTariffCallDurationMinutes; i++){
            double tarifPriceForMinute = NORMAL_TARIFF_PRICE;
            if (i >= EXPENSIVE_TARIFF_STARTING_POINT_IN_MIN && i < EXPENSIVE_TARIFF_ENDING_POINT_IN_MIN){
                tarifPriceForMinute = HIGHER_TARIFF_PRICE;
            }
            priceSum = priceSum.add(BigDecimal.valueOf(tarifPriceForMinute));
        }
        priceSum = priceSum.add(BigDecimal.valueOf(restOfCallDurationMinutes * CHEAPER_TARIFF_PRICE));


        return priceSum;
    }

    public long getPhone() {
        return phone;
    }

    public String getStartCallTimestamp() {
        return startCallTimestamp;
    }

    public String getEndCallTimestamp() {
        return endCallTimestamp;
    }

    @Override
    public String toString() {
        return "PhoneLog{" +
                "phone=" + phone +
                ", startCallDateTime=" + startCallDateTime +
                ", endCallDateTime=" + endCallDateTime +
                ", callDurationInSeconds=" + callDurationInSeconds +
                ", callDurationInMinutes=" + callDurationInMinutes +
                ", calculatedCallPrince=" + calculatedCallPrince +
                '}';
    }
}
