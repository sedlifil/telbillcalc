import com.phonecompany.billing.TelephoneBillCalculatorImpl;
import junit.framework.*;

import java.math.BigDecimal;


public class TelephoneBillCalculatorTest extends TestCase{

    /**
     * test for only one phone number, should return 0
     */
    public void testOneNumber(){
        String input = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57";
        TelephoneBillCalculatorImpl tbc = new TelephoneBillCalculatorImpl();
        BigDecimal result = tbc.calculate(input);
        BigDecimal expected = new BigDecimal("0");
        assertEquals(expected, result);
    }

    /**
     * test for two phone numbers
     * outside EXPENSIVE tariff
     */
    public void testTwoNumbers(){
        String input = """
                420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57
                420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00
                """;
        TelephoneBillCalculatorImpl tbc = new TelephoneBillCalculatorImpl();
        BigDecimal result = tbc.calculate(input);
        BigDecimal expected = new BigDecimal("1.5");
        assertEquals(expected, result);
    }

    /**
     * test that call is in expensive tariff
     */
    public void testExpensiveTariffInside(){
        String input = """
                420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57
                420774577452,18-01-2020 08:59:20,18-01-2020 09:10:00
                """;
        TelephoneBillCalculatorImpl tbc = new TelephoneBillCalculatorImpl();
        BigDecimal result = tbc.calculate(input);
        BigDecimal expected = new BigDecimal("6.2");
        assertEquals(expected, result);
    }

    /**
     * test that call is starting outside expensive tariff but some minutes
     * are inside expensive tariff
     */
    public void testExpensiveTariffStartOutsideRestInsideExpensiveTariff(){
        String input = """
                420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57
                420774577452,18-01-2020 07:59:20,18-01-2020 08:10:00
                """;
        TelephoneBillCalculatorImpl tbc = new TelephoneBillCalculatorImpl();
        BigDecimal result = tbc.calculate(input);
        BigDecimal expected = new BigDecimal("5.7");
        assertEquals(expected, result);
    }

    /**
     * test call during midnight
     *
     */
    public void testMidnightCall(){
        String input = """
                420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57
                420774577452,18-01-2020 23:58:20,19-01-2020 00:10:00
                """;
        TelephoneBillCalculatorImpl tbc = new TelephoneBillCalculatorImpl();
        BigDecimal result = tbc.calculate(input);
        BigDecimal expected = new BigDecimal("3.9");
        assertEquals(expected, result);
    }


}
