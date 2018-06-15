package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import psybergate.grad2018.javafnds.finance.bean.FixedForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.FixedInvestment;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.resource.InvestmentResource;

@RunWith(MockitoJUnitRunner.class)
public class FixedInvestmentForecastServiceTest {

	private static FixedInvestmentForecastService fifs = new FixedInvestmentForecastService();

	@Mock
	private InvestmentResource invRes;

	Investment investment = null;

	@Test
	public void testIfReturnsCorrectAmountsinGetForecastItems() {
		Investment investment1 = new FixedInvestment("Sizwe", new Money(1_000_000), 3, new BigDecimal(8));

		List<ForecastItem> serviceForecastItems = fifs.getForecastItems(investment1);
		List<ForecastItem> testForecastItems = getForecastItems();
		for (int i = 0; i < getForecastItems().size(); i++) {
			assertEquals(testForecastItems.get(i).getInitialAmount().stringValue(),
					serviceForecastItems.get(i).getInitialAmount().stringValue());
		}
	}

	public void testFixedItemsSizeEqualnumberOfMonthsinGetForecastItems() {
		Investment investment1 = new FixedInvestment("Sizwe", new Money(1_000_000), 50, new BigDecimal(8));
		List<ForecastItem> forecastItems = fifs.getForecastItems(investment1);
		assertEquals(50, forecastItems.size());
	}
	
	private List<ForecastItem> getForecastItems() {

		List<ForecastItem> list = new ArrayList<>();
		ForecastItem fi1 = new FixedForecastItem(new Money(1_000_000), new BigDecimal(8));
		ForecastItem fi2 = new FixedForecastItem(new Money(1_006_666.67), new BigDecimal(8));
		ForecastItem fi3 = new FixedForecastItem(new Money(1_013_377.78), new BigDecimal(8));

		list.add(fi1);
		list.add(fi2);
		list.add(fi3);

		return list;
	}

	@Test
	public void testNullInvestmentinGetForecastItems() {
		assertNull(fifs.getForecastItems(investment));
	}

	@Test(expected = RuntimeException.class)
	public void testNullInitialAmountinGetForecastItems() {
		investment = new FixedInvestment("Mahlori", null, 20, new BigDecimal(10));
		fifs.getForecastItems(investment);

	}

	@Test(expected = RuntimeException.class)
	public void testNegativeInitialAmountinGetForecastItems() {
		investment = new FixedInvestment("Mahlori", new Money(-11), 20, new BigDecimal(10));
		fifs.getForecastItems(investment);

	}

	@Test(expected = RuntimeException.class)
	public void testZeroInitialAmountinGetForecastItems() {
		investment = new FixedInvestment("Mahlori", null, 20, new BigDecimal(10));
		investment.setInitialAmount(new Money(0));
		fifs.getForecastItems(investment);
	}

	@Test(expected = RuntimeException.class)
	public void testNullMonthsinGetForecastItems() {
		investment = new FixedInvestment("Mahlori", new Money(1000), null, new BigDecimal(10));
		fifs.getForecastItems(investment);

	}

	@Test(expected = RuntimeException.class)
	public void testNegativeMonthsinGetForecastItems() {
		investment = new FixedInvestment("Mahlori", new Money(1000), -11, new BigDecimal(10));
		fifs.getForecastItems(investment);

	}

	@Test(expected = RuntimeException.class)
	public void testZeroMonthsinGetForecastItems() {
		investment = new FixedInvestment("Mahlori", new Money(1000), 0, new BigDecimal(10));
		fifs.getForecastItems(investment);

	}
	
	@Test(expected = RuntimeException.class)
	public void testNullRateinGetForecastItems() {
		investment = new FixedInvestment("Mahlori", new Money(1000), 20, null);
		fifs.getForecastItems(investment);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testNegativeRateinGetForecastItems() {
		investment = new FixedInvestment("Mahlori", new Money(1000), 11, new BigDecimal(-10));
		fifs.getForecastItems(investment);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testZeroRateinGetForecastItems() {
		investment = new FixedInvestment("Mahlori", new Money(1000), 10, new BigDecimal(0));
		fifs.getForecastItems(investment);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testGreaterThan100RateinGetForecastItems() {
		investment = new FixedInvestment("Mahlori", new Money(1000), 10, new BigDecimal(101));
		fifs.getForecastItems(investment);
		
	}

	
	
}
