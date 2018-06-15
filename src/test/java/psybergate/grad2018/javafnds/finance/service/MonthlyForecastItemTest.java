package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.bean.MonthlyForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Money;

@RunWith(MockitoJUnitRunner.class)
public class MonthlyForecastItemTest {

	private ForecastItem ffi;


	@Test
	public void testGetInterestCalculationFirstMonth() {
		 ffi= new MonthlyForecastItem(new Money(0),new BigDecimal(12),new Money(1000));
		
		assertEquals(new Money(10), ffi.getInterest());
	}
	
	@Test
	public void testGetEndAmountCalculationFirstMonth() {
		ffi= new MonthlyForecastItem(new Money(0),new BigDecimal(12),new Money(1000));
		
		assertEquals(new Money(1010), ffi.getEndAmount());
	}
	@Test
	public void testGetInterestCalculation() {
		ffi= new MonthlyForecastItem(new Money(1018.67),new BigDecimal(22.4),new Money(1000));
		
		assertEquals(new Money(37.68).stringValue(), ffi.getInterest().stringValue());
	}
	
	@Test
	public void testGetEndAmountCalculation() {
		ffi= new MonthlyForecastItem(new Money(1018.67),new BigDecimal(22.4),new Money(1000));
		
		assertEquals(new Money(2056.35).stringValue(), ffi.getEndAmount().stringValue());
	}
	
}
