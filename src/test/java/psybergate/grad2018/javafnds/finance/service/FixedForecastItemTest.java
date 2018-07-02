package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import psybergate.grad2018.javafnds.finance.bean.FixedInvestmentForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Money;

@RunWith(MockitoJUnitRunner.class)
public class FixedForecastItemTest {

	private ForecastItem ffi = new FixedInvestmentForecastItem(new Money(0.0), 0.0, 1);

	@Test
	public void testGetInterestCalculation() {
		ffi.setInitialAmount(new Money(1000.00));
		ffi.setRate(12.00);

		assertEquals(new Money(10.00), ffi.getInterest());
	}

	@Test
	public void testGetEndAmountCalculation() {
		ffi.setInitialAmount(new Money(1000.00));
		ffi.setRate(12.00);

		assertEquals(new Money(1010.00), ffi.getEndAmount());
	}

}
