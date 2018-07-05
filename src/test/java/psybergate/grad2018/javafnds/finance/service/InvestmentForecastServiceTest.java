package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import psybergate.grad2018.javafnds.finance.bean.FixedInvestmentForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.bean.MonthlyInvestmentForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Event;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.resource.InvestmentResource;
import psybergate.grad2018.javafnds.finance.service.internal.InvestmentForecastServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class InvestmentForecastServiceTest {

	private static InvestmentForecastService ifs = new InvestmentForecastServiceImpl();

	@Mock
	private InvestmentResource investmentResource;

	@Test
	public void testSaveIfNameIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment(null, Investment.FIXED, new Money(10000.00), 20, 8.0);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfNameIsEmpty() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("", Investment.FIXED, new Money(10000.00), 20, 8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfInvestmentIsFine() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 20, 8.0);
		assertTrue(ifs.save(investment));
	}

	@Test
	public void testSaveIfInitialAmountIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, null, 20, 8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfRateIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 20, null);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfRateIsNegative() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 20, -8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfMonthsIsNegative() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), -20, -8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfMonthsEqualsZero() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 0, -8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfMonthsIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), null, -8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testDeleteIfInvestmentExist() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 20, 8.00);
		when(investmentResource.contains(investment)).thenReturn(true);
		assertTrue(ifs.delete(investment));
	}

	@Test
	public void testDeleteIfInvestmentNotExist() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 20, 8.00);
		when(investmentResource.contains(investment)).thenReturn(false);
		assertFalse(ifs.delete(investment));
	}

	@Test
	public void testIfReturnsCorrectAmountsinGetForecastItems() {
		Investment investment1 = new Investment("Sizwe", Investment.FIXED, new Money(1_000_000.00), 3, 8.00);

		List<ForecastItem> serviceForecastItems = ifs.getForecastItems(investment1);
		List<ForecastItem> testForecastItems = getForecastItemsFixed();
		for (int i = 0; i < getForecastItemsFixed().size(); i++) {
			assertEquals(testForecastItems.get(i).getInitialAmount().stringValue(), serviceForecastItems.get(i)
					.getInitialAmount().stringValue());
		}
	}

	public void testFixedItemsSizeEqualnumberOfMonthsinGetForecastItems() {

		Investment investment1 = new Investment("Sizwe", Investment.FIXED, new Money(1_000_000.00), 50, 8.00);
		List<ForecastItem> forecastItems = ifs.getForecastItems(investment1);
		assertEquals(50, forecastItems.size());
	}

	private List<ForecastItem> getForecastItemsFixed() {

		List<ForecastItem> list = new ArrayList<>();
		ForecastItem fi1 = new FixedInvestmentForecastItem(new Money(1_000_000.00), 8.00, 3);
		ForecastItem fi2 = new FixedInvestmentForecastItem(new Money(1_006_666.67), 8.00, 2);
		ForecastItem fi3 = new FixedInvestmentForecastItem(new Money(1_013_377.78), 8.00, 1);

		list.add(fi1);
		list.add(fi2);
		list.add(fi3);

		return list;
	}

	@Test
	public void testIfReturnsCorrectAmountsinGetForecastItemsMonthly() {
		Investment investment1 = new Investment("Sizwe", Investment.MONTHLY, new Money(1_000.00), 3, 22.4);
		List<ForecastItem> serviceForecastItems = ifs.getForecastItems(investment1);
		assertListEquals(getForecastItemsMonthly(3), serviceForecastItems);
	}

	@Test
	public void testFixedItemsSizeEqualnumberOfMonthsInGetForecastItemsMonthly() {
		Investment investment1 = new Investment("Sizwe", Investment.MONTHLY, new Money(1_000_000.00), 50, 8.0);
		List<ForecastItem> forecastItems = ifs.getForecastItems(investment1);
		assertEquals(50, forecastItems.size());
	}

	@Test
	public void testGetSummaryForFixedInvestment() {
		// given
		Money initialAmount = new Money(1_000_000.00);
		double rate = 8.00;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.FIXED, initialAmount, months, rate);
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalInterest = new Money(489_845.69);
		Money endBalance = new Money(1_489_845.69);
		Money totalContributions = new Money(1_000_000.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryForFixedInvestmentWithDeposit() {
		// given
		Money initialAmount = new Money(1_000_000.00);
		double rate = 8.00;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.FIXED, initialAmount, months, rate);
		investment.addEvent(new Event(Event.DEPOSIT, 15, new BigDecimal(100_000)));
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalDeposits = new Money(100_000.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalInterest = new Money(525_596.28);
		Money endBalance = new Money(1_625_596.28);
		Money totalContributions = new Money(1_000_000.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryForFixedInvestmentWithWithdrawal() {
		// given
		Money initialAmount = new Money(1_000_000.00);
		double rate = 8.00;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.FIXED, initialAmount, months, rate);
		investment.addEvent(new Event(Event.WITHDRAW, 15, new BigDecimal(100_000)));
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalWithdrawals = new Money(100_000.0);
		Money totalDeposits = new Money(0.0);
		Money totalInterest = new Money(454_095.14);
		Money endBalance = new Money(1_354_095.14);
		Money totalContributions = new Money(1_000_000.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryForFixedInvestmentWithRateChange() {
		// given
		Money initialAmount = new Money(1_000_000.00);
		double rate = 8.00;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.FIXED, initialAmount, months, rate);
		investment.addEvent(new Event(Event.RATE_CHANGE, 10, new BigDecimal(6)));
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalWithdrawals = new Money(0.0);
		Money totalDeposits = new Money(0.0);
		Money totalInterest = new Money(369_116.32);
		Money endBalance = new Money(1_369_116.32);
		Money totalContributions = new Money(1_000_000.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryForFixedInvestmentWithMultipleEvents() {
		// given
		Money initialAmount = new Money(1_000_000.00);
		double rate = 8.00;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.FIXED, initialAmount, months, rate);
		investment.addEvent(new Event(Event.RATE_CHANGE, 10, new BigDecimal(6)));
		investment.addEvent(new Event(Event.WITHDRAW, 13, new BigDecimal(50_000)));
		investment.addEvent(new Event(Event.DEPOSIT, 15, new BigDecimal(100_000)));
		investment.addEvent(new Event(Event.DEPOSIT, 23, new BigDecimal(5_000)));
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalWithdrawals = new Money(50_000.0);
		Money totalDeposits = new Money(105_000.0);
		Money totalInterest = new Money(382_423.19);
		Money endBalance = new Money(1_437_423.19);
		Money totalContributions = new Money(1_000_000.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryForMonthlyInvestment() {
		// given
		Money initialAmount = new Money(1_000.00);
		double rate = 22.40;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.MONTHLY, initialAmount, months, rate);
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalInterest = new Money(50_964.04);
		Money endBalance = new Money(110_964.04);
		Money totalContributions = new Money(60_000.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryForMonthlyInvestmentWithDeposit() {
		// given
		Money initialAmount = new Money(1_000.00);
		double rate = 22.40;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.MONTHLY, initialAmount, months, rate);
		investment.addEvent(new Event(Event.DEPOSIT, 12, new BigDecimal(10_000)));
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalDeposits = new Money(10_000.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalInterest = new Money(65_713.93);
		Money endBalance = new Money(135_713.93);
		Money totalContributions = new Money(60_000.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryForMonthlyInvestmentWithWithdrawal() {
		// given
		Money initialAmount = new Money(1_000.00);
		double rate = 22.40;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.MONTHLY, initialAmount, months, rate);
		investment.addEvent(new Event(Event.WITHDRAW, 25, new BigDecimal(20_000)));
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(20_000.0);
		Money totalInterest = new Money(32_042.88);
		Money endBalance = new Money(72_042.88);
		Money totalContributions = new Money(60_000.0);

		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryForMonthlyInvestmentWithRateChange() {
		// given
		Money initialAmount = new Money(1_000.00);
		double rate = 22.40;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.MONTHLY, initialAmount, months, rate);
		investment.addEvent(new Event(Event.RATE_CHANGE, 40, new BigDecimal(18)));
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalInterest = new Money(43_698.37);
		Money endBalance = new Money(103_698.37);
		Money totalContributions = new Money(60_000.0);

		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	// @Test
	public void testGetSummaryForMonthlyInvestmentWithChangeInMonthlyContribution() {
		// given
		Money initialAmount = new Money(1_000.00);
		double rate = 22.40;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.MONTHLY, initialAmount, months, rate);
		investment.addEvent(new Event(Event.AMOUNT_CHANGE, 40, new BigDecimal(5000)));
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalInterest = new Money(70_562.28);
		Money endBalance = new Money(214_562.28);
		Money totalContributions = new Money(144_000.0);

		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryForMonthlyInvestmentWithMultipleEvents() {
		// given
		Money initialAmount = new Money(1_000.00);
		double rate = 22.40;
		int months = 60;
		// when
		Investment investment = new Investment("temp", Investment.MONTHLY, initialAmount, months, rate);
		investment.addEvent(new Event(Event.RATE_CHANGE, 40, new BigDecimal(18)));
		investment.addEvent(new Event(Event.RATE_CHANGE, 54, new BigDecimal(22.4)));
		investment.addEvent(new Event(Event.WITHDRAW, 43, new BigDecimal(50_000)));
		investment.addEvent(new Event(Event.DEPOSIT, 15, new BigDecimal(100_000)));
		investment.addEvent(new Event(Event.DEPOSIT, 23, new BigDecimal(5_000)));
		Map<String, Money> summary = ifs.getSummary(investment);
		// then
		Money totalWithdrawals = new Money(50_000.0);
		Money totalDeposits = new Money(105_000.0);
		Money totalInterest = new Money(156_444.43);
		Money endBalance = new Money(271_444.43);
		Money totalContributions = new Money(60_000.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	private void assertSummary(Map<String, Money> summary, Money totalDeposits, Money totalWithdrawals,
			Money totalInterest, Money totalContributions, Money endBalance) {
		assertEquals(totalDeposits, summary.get(ForecastService.TOTAL_DEPOSITS));
		assertEquals(totalWithdrawals, summary.get(ForecastService.TOTAL_WITHDRAWALS));
		assertEquals(totalInterest, summary.get(ForecastService.TOTAL_INTEREST));
		assertEquals(totalContributions, summary.get(ForecastService.TOTAL_CONTRIBUTION));
		assertEquals(endBalance, summary.get(ForecastService.END_BALANCE));
	}

	private List<ForecastItem> getForecastItemsMonthly(int months) {

		List<ForecastItem> list = new ArrayList<>();
		MonthlyInvestmentForecastItem fi1 = new MonthlyInvestmentForecastItem(new Money(0.00), 22.4, months, 1);
		fi1.setFixedRepayment(new Money(1_000.00));
		ForecastItem fi2 = new MonthlyInvestmentForecastItem(new Money(1_018.67), 22.4, months, 2);
		fi2.setFixedRepayment(new Money(1_000.00));
		ForecastItem fi3 = new MonthlyInvestmentForecastItem(new Money(2_056.35), 22.4, months, 3);

		list.add(fi1);
		list.add(fi2);
		list.add(fi3);

		return list;
	}

	private void assertListEquals(List<ForecastItem> expected, List<ForecastItem> actaul) {

		for (int index = 0; index < expected.size(); index++) {
			if (!expected.get(index).equals(actaul.get(index))) fail("expected " + expected.get(index) + "actaul :" + actaul
					.get(index));
		}
	}

}
