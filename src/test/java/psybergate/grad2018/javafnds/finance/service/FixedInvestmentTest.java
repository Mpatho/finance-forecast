package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;

import org.junit.Test;

import psybergate.grad2018.javafnds.finance.entity.FixedInvestment;
import psybergate.grad2018.javafnds.finance.entity.Investment;

public class FixedInvestmentTest {

	private FixedInvestmentService fis = new FixedInvestmentServiceImpl();
	
	private Investment fixedInvestment = null;
	
	private void createFixedInvestmentInstance() {
		fixedInvestment = new FixedInvestment();
	}
	
	private Investment populateFixedInvestment() {
		createFixedInvestmentInstance();
		fixedInvestment.setName("PsyName 1");
		fixedInvestment.setInitailAmount(new BigDecimal(1000));
		fixedInvestment.setMonths(60);
		fixedInvestment.setRate(12.5);
		
		return fixedInvestment;
	}

	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentEntityNullInSaveForecast() {
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentNameNullInSaveForecast() {
		populateFixedInvestment().setName(null);
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentNameEmptyInSaveForecast() {
		populateFixedInvestment().setName(" ");
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentInitialAmountNullInSaveForecast() {
		populateFixedInvestment().setInitailAmount(null);
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentInitialAmountZeroSaveForecast() {
		populateFixedInvestment().setInitailAmount(new BigDecimal(0));
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentInitialAmountLessThanZeroSaveForecast() {
		populateFixedInvestment().setInitailAmount(new BigDecimal(-11));
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentMonthsNullInSaveForecast() {
		populateFixedInvestment().setInitailAmount(null);
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentMonthsZeroSaveForecast() {
		populateFixedInvestment().setMonths(0);
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentMonthsLessThanZeroSaveForecast() {
		populateFixedInvestment().setMonths(-11);
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentRateNullInSaveForecast() {
		populateFixedInvestment().setRate(null);
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentRateLessThanZeroInSaveForecast() {
		populateFixedInvestment().setRate(-12D);
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentRateZeroInSaveForecast() {
		populateFixedInvestment().setRate(0D);
		fis.calculateForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentRateGreaterThan100InSaveForecast() {
		populateFixedInvestment().setRate(101D);
		fis.calculateForecast(fixedInvestment);
	}
	
	
}
