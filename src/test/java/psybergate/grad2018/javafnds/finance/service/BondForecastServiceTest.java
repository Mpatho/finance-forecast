package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import psybergate.grad2018.javafnds.finance.bean.BondForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Money;

public class BondForecastServiceTest {

	private static BondForecastServiceImpl fs = new BondForecastServiceImpl();

	@Test
	public void testGetForecastItems() {

		Bond bond = new Bond(new Money(750000.00), new Money(100000.00), 12.00, 240, null);
		List<ForecastItem> forecastItems = fs.getForecastItems(bond);
		assertListEquals(loadList(), forecastItems);
	}

	@Test
	public void testGetRepayment() {
		Bond bond = new Bond(new Money(750000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(7157.06), fs.getRepayment(bond));
	}

	private List<ForecastItem> loadList() {
		List<ForecastItem> forecastItems = new LinkedList<>();
		forecastItems.add(new BondForecastItem(new Money(650_000.00), 12.00, new Money(7157.06)));
		forecastItems.add(new BondForecastItem(new Money(649_342.94), 12.00, new Money(7157.06)));
		forecastItems.add(new BondForecastItem(new Money(648_679.31), 12.00, new Money(7157.06)));
		forecastItems.add(new BondForecastItem(new Money(648_009.04), 12.00, new Money(7157.06)));
		forecastItems.add(new BondForecastItem(new Money(647_332.07), 12.00, new Money(7157.06)));
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
		Bond bond = new Bond(new Money(1_750_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(40_500.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceGreaterThan1750KAndLessThan2250K() {
		Bond bond = new Bond(new Money(2_200_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(76_500.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceEqual2250K() {
		Bond bond = new Bond(new Money(2_250_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(80_500.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceGreaterThan2250KAndLessThan10M() {
		Bond bond = new Bond(new Money(5_000_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(383_000.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceEqual10M() {
		Bond bond = new Bond(new Money(10_000_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(933_000.0), fs.getTransferCost(bond));
	}

	@Test
	public void testGetTransferCostIfPriceGreaterThan10M() {
		Bond bond = new Bond(new Money(11_000_000.00), new Money(100000.00), 12.00, 240, null);
		assertEquals(new Money(1_063_000.00), fs.getTransferCost(bond));
	}

}
