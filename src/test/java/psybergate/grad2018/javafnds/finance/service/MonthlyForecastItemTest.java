package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.bean.MonthlyForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Money;

@RunWith(MockitoJUnitRunner.class)
public class MonthlyForecastItemTest {

	private ForecastItem ffi;

	private static Money zero = new Money(0.0);

	@Test
	public void testGetInterestCalculationFirstMonth() {
		ffi = new MonthlyForecastItem(new Money(0.00), 12.00, new Money(1000.00), zero, zero);

		assertEquals(new Money(10.00), ffi.getInterest());
	}

	@Test
	public void testGetEndAmountCalculationFirstMonth() {
		ffi = new MonthlyForecastItem(new Money(0.00), 12.00, new Money(1000.00), zero, zero);

		assertEquals(new Money(1010.00), ffi.getEndAmount());
	}

	@Test
	public void testGetInterestCalculation() {
		ffi = new MonthlyForecastItem(new Money(1018.67), 22.4, new Money(1000.00), zero, zero);
		assertEquals(new Money(37.68).stringValue(), ffi.getInterest().stringValue());
	}

	@Test
	public void testGetEndAmountCalculation() {
		ffi = new MonthlyForecastItem(new Money(1018.67), 22.4, new Money(1000.00), zero, zero);

		assertEquals(new Money(2056.35).stringValue(), ffi.getEndAmount().stringValue());
	}

}
