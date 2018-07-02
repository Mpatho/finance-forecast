package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import psybergate.grad2018.javafnds.finance.bean.BondForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Event;
import psybergate.grad2018.javafnds.finance.entity.Money;

public class BondForecastServiceTest {

	private static BondForecastServiceImpl fs = new BondForecastServiceImpl();

	@Test
	public void testGetForecastItems() {
		Bond bond = new Bond(new Money(750000.00), new Money(100000.00), 12.00, 240);
		List<ForecastItem> forecastItems = fs.getForecastItems(bond, false);
		assertListEquals(loadList(240), forecastItems);
	}

//@Test
//public void testGetRepayment() {
//	Bond bond = new Bond(new Money(750000.00), new Money(100000.00), 12.00, 240, null);
//	assertEquals(new Money(7157.06), fs.getRepayment(bond));
//}

	private List<ForecastItem> loadList(int months) {
		List<ForecastItem> forecastItems = new LinkedList<>();
		forecastItems.add(new BondForecastItem(new Money(650_000.00), 12.00, months));
		forecastItems.add(new BondForecastItem(new Money(649_342.94), 12.00, months));
		forecastItems.add(new BondForecastItem(new Money(648_679.31), 12.00, months));
		forecastItems.add(new BondForecastItem(new Money(648_009.04), 12.00, months));
		forecastItems.add(new BondForecastItem(new Money(647_332.07), 12.00, months));
		return forecastItems;
	}

	private void assertListEquals(List<ForecastItem> expected, List<ForecastItem> actual) {
		for (int index = 0; index < expected.size(); index++) {
			if (!expected.get(index).equals(actual.get(index))) fail("expected : " + expected.get(index) + ", actaul : "
					+ actual.get(index));
		}
	}

	@Test
	public void testGetBondCost() {
		Bond bond = new Bond(new Money(700_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(7_000.0), fs.getBondCost(bond));
		bond = new Bond(new Money(1_000_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(10_000.0), fs.getBondCost(bond));
		bond = new Bond(new Money(5_600_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(56_000.0), fs.getBondCost(bond));
	}

	@Test
	public void testGetLegalCost() {
		Bond bond = new Bond(new Money(700_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(8_400.0), fs.getLegalCost(bond));
		bond = new Bond(new Money(1_000_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(12_000.0), fs.getLegalCost(bond));
		bond = new Bond(new Money(5_600_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(67_200.0), fs.getLegalCost(bond));
	}

	@Test
	public void testGetCashRequired() {
		Bond bond = new Bond(new Money(700_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(15_400.0), fs.getCashRequired(bond));
		bond = new Bond(new Money(1_000_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(25_000.0), fs.getCashRequired(bond));
		bond = new Bond(new Money(5_600_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(572_200.0), fs.getCashRequired(bond));
	}

	@Test
	public void testGetTransferCostIfPriceLessThan900K() {
		Bond bond = new Bond(new Money(700_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(0.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceEqua900K() {
		Bond bond = new Bond(new Money(900_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(0.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceGreaterThan900KAndLessThan1250K() {
		Bond bond = new Bond(new Money(1_200_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(9000.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceEqual1250K() {
		Bond bond = new Bond(new Money(1_250_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(10_500.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceGreaterThan1250KAndLessThan1750K() {
		Bond bond = new Bond(new Money(1_700_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(37_500.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceEqual1750K() {
		// given
		Money price = new Money(1_750_000.00);
		Money deposit = new Money(100000.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		// then
		assertEquals(new Money(40_500.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceGreaterThan1750KAndLessThan2250K() {
		// given
		Money price = new Money(2_200_000.00);
		Money deposit = new Money(100000.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		// then
		assertEquals(new Money(76_500.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceEqual2250K() {
		// given
		Money price = new Money(2_250_000.00);
		Money deposit = new Money(100000.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		// then
		assertEquals(new Money(80_500.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceGreaterThan2250KAndLessThan10M() {
		// given
		Money price = new Money(5_000_000.00);
		Money deposit = new Money(100000.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		// then
		assertEquals(new Money(383_000.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceEqual10M() {
		// given
		Money price = new Money(10_000_000.00);
		Money deposit = new Money(100000.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		// then
		assertEquals(new Money(933_000.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceGreaterThan10M() {
		// given
		Money price = new Money(11_000_000.00);
		Money deposit = new Money(100000.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		// then
		assertEquals(new Money(1_063_000.00), fs.getTransferCost(bond));
	}

	@Test
	public void testGetSummaryWith10K() {
		// given
		Money price = new Money(10_000.00);
		Money deposit = new Money(0.00);
		double rate = 12.00;
		int months = 12;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		Map<String, Money> summary = fs.getSummary(bond, false);
		// then
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalInterest = new Money(661.86);
		Money totalContributions = new Money(10_661.86);
		Money endBalance = new Money(0.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryWith1M() {
		// given
		Money price = new Money(1_000_000.00);
		Money deposit = new Money(0.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months);
		Map<String, Money> summary = fs.getSummary(bond, false);
		// then
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalInterest = new Money(1_642_607.06);
		Money totalContributions = new Money(2_642_607.06);
		Money endBalance = new Money(0.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryWithDeposit() {
		// given
		Money price = new Money(1_000_000.00);
		Money deposit = new Money(0.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		bond.addEvent(new Event(Event.DEPOSIT, 10, new BigDecimal(100_000)));
		Map<String, Money> summary = fs.getSummary(bond, false);
		// then
		Money totalDeposits = new Money(100_000.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalInterest = new Money(1_485_823.73);
		Money totalContributions = new Money(2_385_823.73);
		Money endBalance = new Money(0.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryWithWithdrawal() {
		// given
		Money price = new Money(1_000_000.00);
		Money deposit = new Money(0.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		bond.addEvent(new Event(Event.WITHDRAW, 10, new BigDecimal(100_000)));
		Map<String, Money> summary = fs.getSummary(bond, false);
		// then
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(100_000.0);
		Money totalInterest = new Money(1_799_389.46);
		Money totalContributions = new Money(2_899_389.46);
		Money endBalance = new Money(0.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryWithMultipleEvents() {
		// given
		Money price = new Money(1_000_000.00);
		Money deposit = new Money(0.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		bond.addEvent(new Event(Event.WITHDRAW, 10, new BigDecimal(100_000)));
		bond.addEvent(new Event(Event.DEPOSIT, 15, new BigDecimal(100_000)));
		bond.addEvent(new Event(Event.DEPOSIT, 23, new BigDecimal(5_000)));
		bond.addEvent(new Event(Event.RATE_CHANGE, 40, new BigDecimal(14)));
		bond.addEvent(new Event(Event.WITHDRAW, 43, new BigDecimal(50_000)));
		bond.addEvent(new Event(Event.RATE_CHANGE, 54, new BigDecimal(9)));

		Map<String, Money> summary = fs.getSummary(bond, false);
		// then
		Money totalDeposits = new Money(105_000.0);
		Money totalWithdrawals = new Money(150_000.0);
		Money totalInterest = new Money(1_389_334.61);
		Money totalContributions = new Money(2_434_334.61);
		Money endBalance = new Money(0.0);
		assertSummary(summary, totalDeposits, totalWithdrawals, totalInterest, totalContributions, endBalance);
	}

	@Test
	public void testGetSummaryWithRateChange() {
		// given
		Money price = new Money(1_000_000.00);
		Money deposit = new Money(0.00);
		double rate = 12.00;
		int months = 240;
		// when
		Bond bond = new Bond(price, deposit, rate, months, null);
		bond.addEvent(new Event(Event.RATE_CHANGE, 15, new BigDecimal(9)));
		Map<String, Money> summary = fs.getSummary(bond, false);
		// then
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalInterest = new Money(1_201_889.41);
		Money totalContributions = new Money(2_201_889.41);
		Money endBalance = new Money(0.0);
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
}
