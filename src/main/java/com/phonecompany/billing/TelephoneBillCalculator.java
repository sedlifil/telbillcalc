package com.phonecompany.billing;

import java.math.BigDecimal;

public interface TelephoneBillCalculator {
    /**
     *
     * @param phoneLog input of logs in format "phone_number,start_of_call,end_of_call"
     * @return price for calls from phone logs
     */
    BigDecimal calculate (String phoneLog);
}
