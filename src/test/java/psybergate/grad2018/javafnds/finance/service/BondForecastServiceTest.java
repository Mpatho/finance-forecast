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
			if (!expected.get(index).equals(actual.get(index))) fail("expected : " + expected.get(index) + ", actaul : " + actual.get(index));
		}
	}
}
