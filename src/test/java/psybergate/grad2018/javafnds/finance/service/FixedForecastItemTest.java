package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import psybergate.grad2018.javafnds.finance.bean.FixedForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Money;

@RunWith(MockitoJUnitRunner.class)
public class FixedForecastItemTest {

	private ForecastItem ffi = new FixedForecastItem();


	@Test
	public void testGetInterestCalculation() {
		ffi.setInitialAmount(new Money(1000));
		ffi.setRate(new BigDecimal(12));
		
		assertEquals(new Money(10), ffi.getInterest());
	}
	
	@Test
	public void testGetEndAmountCalculation() {
		ffi.setInitialAmount(new Money(1000));
		ffi.setRate(new BigDecimal(12));
		
		assertEquals(new Money(1010), ffi.getEndAmount());
	}
	
}
