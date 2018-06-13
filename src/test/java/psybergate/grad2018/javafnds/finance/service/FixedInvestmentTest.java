package psybergate.grad2018.javafnds.finance.service;

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
		fixedInvestment.setInitailAmount(1000.00);
		fixedInvestment.setMonths(60);
		fixedInvestment.setRate(12.5);
		
		return fixedInvestment;
	}

	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentEntityNullInSaveForecast() {
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentNameNullInSaveForecast() {
		populateFixedInvestment().setName(null);
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentNameEmptyInSaveForecast() {
		populateFixedInvestment().setName(" ");
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentInitialAmountNullInSaveForecast() {
		populateFixedInvestment().setInitailAmount(null);
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentInitialAmountZeroSaveForecast() {
		populateFixedInvestment().setInitailAmount(0D);
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentInitialAmountLessThanZeroSaveForecast() {
		populateFixedInvestment().setInitailAmount(-11D);
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentMonthsNullInSaveForecast() {
		populateFixedInvestment().setInitailAmount(null);
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentMonthsZeroSaveForecast() {
		populateFixedInvestment().setMonths(0);
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentMonthsLessThanZeroSaveForecast() {
		populateFixedInvestment().setMonths(-11);
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentRateNullInSaveForecast() {
		populateFixedInvestment().setRate(null);
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentRateLessThanZeroInSaveForecast() {
		populateFixedInvestment().setRate(-12D);
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentRateZeroInSaveForecast() {
		populateFixedInvestment().setRate(0D);
		fis.saveForecast(fixedInvestment);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFixedInvestmentRateGreaterThan100InSaveForecast() {
		populateFixedInvestment().setRate(101D);
		fis.saveForecast(fixedInvestment);
	}
	
	
}
