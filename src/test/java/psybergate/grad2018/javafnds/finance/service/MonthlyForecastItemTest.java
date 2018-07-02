package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.bean.MonthlyInvestmentForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Money;

@RunWith(MockitoJUnitRunner.class)
public class MonthlyForecastItemTest {

	private ForecastItem ffi;

	@Test
	public void testGetInterestCalculationFirstMonth() {
		ffi = new MonthlyInvestmentForecastItem(new Money(0.00), 12.00, 1 ,1);
		ffi.setFixedRepayment(new Money(1000.00));
		assertEquals(new Money(10.00), ffi.getInterest());
	}

	@Test
	public void testGetEndAmountCalculationFirstMonth() {
		ffi = new MonthlyInvestmentForecastItem(new Money(0.00), 12.00, 1 ,1);
		ffi.setFixedRepayment(new Money(1000.00));
		assertEquals(new Money(1010.00), ffi.getEndAmount());
}

	@Test
	public void testGetInterestCalculation() {
		ffi = new MonthlyInvestmentForecastItem(new Money(1018.67), 22.4, 1 ,1);
		ffi.setFixedRepayment(new Money(1000.00));
		assertEquals(new Money(37.68).stringValue(), ffi.getInterest().stringValue());
	}

	@Test
	public void testGetEndAmountCalculation() {
		ffi = new MonthlyInvestmentForecastItem(new Money(1018.67), 22.4, 1 ,1);
		ffi.setFixedRepayment(new Money(1000.00));
		assertEquals(new Money(2056.35).stringValue(), ffi.getEndAmount().stringValue());
	}

}
