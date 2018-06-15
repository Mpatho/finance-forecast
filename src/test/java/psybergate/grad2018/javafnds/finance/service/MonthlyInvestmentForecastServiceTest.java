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
import psybergate.grad2018.javafnds.finance.bean.MonthlyForecastItem;
import psybergate.grad2018.javafnds.finance.entity.FixedInvestment;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.entity.MonthlyInvestment;
import psybergate.grad2018.javafnds.finance.resource.InvestmentResource;

@RunWith(MockitoJUnitRunner.class)
public class MonthlyInvestmentForecastServiceTest {

	private static MonthlyInvestmentForecastService mifs = new MonthlyInvestmentForecastService();

	@Mock
	private InvestmentResource invRes;

	Investment investment = null;

	@Test
	public void testIfReturnsCorrectAmountsinGetForecastItems() {
		Investment investment1 = new FixedInvestment("Sizwe", new Money(1_000), 3, new BigDecimal(22.4));

		List<ForecastItem> serviceForecastItems = mifs.getForecastItems(investment1);
		List<ForecastItem> testForecastItems = getForecastItems();
		for (int i = 0; i < getForecastItems().size(); i++) {
			assertEquals(testForecastItems.get(i).getInitialAmount().stringValue(),
					serviceForecastItems.get(i).getInitialAmount().stringValue());
		}
	}

	public void testFixedItemsSizeEqualnumberOfMonthsinGetForecastItems() {
		Investment investment1 = new FixedInvestment("Sizwe", new Money(1_000_000), 50, new BigDecimal(8));
		List<ForecastItem> forecastItems = mifs.getForecastItems(investment1);
		assertEquals(50, forecastItems.size());
	}
	
	private List<ForecastItem> getForecastItems() {

		List<ForecastItem> list = new ArrayList<>();
		ForecastItem fi1 = new MonthlyForecastItem(new Money(0), new BigDecimal(22.4), new Money(1_000));
		ForecastItem fi2 = new MonthlyForecastItem(new Money(1_018.67), new BigDecimal(22.4),new Money(1_000));
		ForecastItem fi3 = new MonthlyForecastItem(new Money(2_056.35), new BigDecimal(22.4), new Money(1_000));

		list.add(fi1);
		list.add(fi2);
		list.add(fi3);

		return list;
	}

	@Test
	public void testNullInvestmentinGetForecastItems() {
		assertNull(mifs.getForecastItems(investment));
	}

	@Test(expected = RuntimeException.class)
	public void testNullInitialAmountinGetForecastItems() {
		investment = new MonthlyInvestment("Mahlori", null, 20, new BigDecimal(10));
		mifs.getForecastItems(investment);

	}

	@Test(expected = RuntimeException.class)
	public void testNegativeInitialAmountinGetForecastItems() {
		investment = new MonthlyInvestment("Mahlori", new Money(-11), 20, new BigDecimal(10));
		mifs.getForecastItems(investment);

	}

	@Test(expected = RuntimeException.class)
	public void testZeroInitialAmountinGetForecastItems() {
		investment = new MonthlyInvestment("Mahlori", null, 20, new BigDecimal(10));
		investment.setInitialAmount(new Money(0));
		mifs.getForecastItems(investment);
	}

	@Test(expected = RuntimeException.class)
	public void testNullMonthsinGetForecastItems() {
		investment = new MonthlyInvestment("Mahlori", new Money(1000), null, new BigDecimal(10));
		mifs.getForecastItems(investment);

	}

	@Test(expected = RuntimeException.class)
	public void testNegativeMonthsinGetForecastItems() {
		investment = new MonthlyInvestment("Mahlori", new Money(1000), -11, new BigDecimal(10));
		mifs.getForecastItems(investment);

	}

	@Test(expected = RuntimeException.class)
	public void testZeroMonthsinGetForecastItems() {
		investment = new MonthlyInvestment("Mahlori", new Money(1000), 0, new BigDecimal(10));
		mifs.getForecastItems(investment);

	}
	
	@Test(expected = RuntimeException.class)
	public void testNullRateinGetForecastItems() {
		investment = new MonthlyInvestment("Mahlori", new Money(1000), 20, null);
		mifs.getForecastItems(investment);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testNegativeRateinGetForecastItems() {
		investment = new MonthlyInvestment("Mahlori", new Money(1000), 11, new BigDecimal(-10));
		mifs.getForecastItems(investment);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testZeroRateinGetForecastItems() {
		investment = new MonthlyInvestment("Mahlori", new Money(1000), 10, new BigDecimal(0));
		mifs.getForecastItems(investment);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testGreaterThan100RateinGetForecastItems() {
		investment = new MonthlyInvestment("Mahlori", new Money(1000), 10, new BigDecimal(101));
		mifs.getForecastItems(investment);
		
	}

	
	
}
