package com.phonecompany.billing;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator{

    @Override
    public BigDecimal calculate(String phoneLog) {
        List<String> phoneStringLogs = new ArrayList<>(Arrays.asList(phoneLog.split("\n")));
        List<PhoneLog> phoneLogs = phoneStringLogs.stream()
                .map(log -> {
                    String[] items = log.split(",");
                    return new PhoneLog(Long.parseLong(items[0]), items[1], items[2]);
                }).toList();
        Map<Long, List<PhoneLog>> phoneLogsMap = phoneLogs.stream()
                        .collect(Collectors.groupingBy(PhoneLog::getPhone));
        long mostCalledPhone = getMostCalledPhone(phoneLogsMap);

        BigDecimal sumOfCalls = new BigDecimal(0);
        for (Map.Entry<Long, List<PhoneLog>> entry: phoneLogsMap.entrySet()){
            if(entry.getKey() == mostCalledPhone){
                continue;
            }
            BigDecimal sum = new BigDecimal(0);
            for(PhoneLog phoneLog1: entry.getValue()){
                sum = sum.add(phoneLog1.calculateCallPrice());
            }
             sumOfCalls = sumOfCalls.add(sum);
        }

    return sumOfCalls;
    }

    /**
     * Method to retrieve phone number with most calls
     * if there are more phone numbers with same count of call,
     *  it will pick the one with higher arithmetic higher value
     * @param phoneLogsMap map with key of phone number and list of PhoneLogs
     * @return phone number with most calls
     */
    private long getMostCalledPhone(Map<Long, List<PhoneLog>> phoneLogsMap){
        long mostCalledPhone = 0;
        int calledCount = 0;

        for (Map.Entry<Long, List<PhoneLog>> entry: phoneLogsMap.entrySet()){
            int entryCalledCount =  entry.getValue().size();
            long entryPhone = entry.getKey();
            if ( entryCalledCount > calledCount ||
                    (entryCalledCount == calledCount && entryPhone > mostCalledPhone)){
                mostCalledPhone = entryPhone;
                calledCount = entryCalledCount;
            }
        }
        return mostCalledPhone;
    }
}
