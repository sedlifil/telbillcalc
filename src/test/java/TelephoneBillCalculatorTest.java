import com.phonecompany.billing.TelephoneBillCalculatorImpl;
import junit.framework.*;

import java.math.BigDecimal;


public class TelephoneBillCalculatorTest extends TestCase{

    public void testCalc(){
        String input = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57";
        TelephoneBillCalculatorImpl tbc = new TelephoneBillCalculatorImpl();
        BigDecimal result = tbc.calculate(input);
        BigDecimal expected = new BigDecimal("0");
        assertEquals(expected, result);
    }
}
