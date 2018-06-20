package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import psybergate.grad2018.javafnds.finance.bean.FixedForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.bean.MonthlyForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.resource.InvestmentResource;
import psybergate.grad2018.javafnds.finance.resource.MoneyResource;

@RunWith(MockitoJUnitRunner.class)
public class InvestmentForecastServiceTest {

	private static InvestmentForecastService ifs = new InvestmentForecastServiceImpl();

	@Mock
	private InvestmentResource investmentResource;

	@Mock
	private MoneyResource moneyResource;

	@Test
	public void testSaveIfNameIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new Investment(null, Investment.FIXED, new Money(10000.00), 20, 8.0);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfNameIsEmpty() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new Investment("", Investment.FIXED, new Money(10000.00), 20, 8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfInvestmentIsFine() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 20, 8.0);
		assertTrue(ifs.save(investment));
	}

	@Test
	public void testSaveIfInitialAmountIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, null, 20, 8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfRateIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 20, null);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfRateIsNegative() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 20, -8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfMonthsIsNegative() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), -20, -8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfMonthsEqualsZero() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 0, -8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfMonthsIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), null, -8.00);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testDeleteIfInvestmentExist() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 20, 8.00);
		when(investmentResource.contains(investment)).thenReturn(true);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		assertTrue(ifs.delete(investment));
	}

	@Test
	public void testDeleteIfInvestmentNotExist() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new Investment("Sizwe", Investment.FIXED, new Money(10000.00), 20, 8.00);
		when(investmentResource.contains(investment)).thenReturn(false);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
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
		ForecastItem fi1 = new FixedForecastItem(new Money(1_000_000.00), 8.00);
		ForecastItem fi2 = new FixedForecastItem(new Money(1_006_666.67), 8.00);
		ForecastItem fi3 = new FixedForecastItem(new Money(1_013_377.78), 8.00);

		list.add(fi1);
		list.add(fi2);
		list.add(fi3);

		return list;
	}

	@Test
	public void testIfReturnsCorrectAmountsinGetForecastItemsMonthly() {
		Investment investment1 = new Investment("Sizwe", Investment.MONTHLY, new Money(1_000.00), 3, 22.4);
		List<ForecastItem> serviceForecastItems = ifs.getForecastItems(investment1);

		assertListEquals(getForecastItemsMonthly(), serviceForecastItems);
	}

	public void testFixedItemsSizeEqualnumberOfMonthsInGetForecastItemsMonthly() {
		Investment investment1 = new Investment("Sizwe", Investment.MONTHLY, new Money(1_000_000.00), 50, 8.0);
		List<ForecastItem> forecastItems = ifs.getForecastItems(investment1);
		assertEquals(50, forecastItems.size());
	}

	private List<ForecastItem> getForecastItemsMonthly() {

		List<ForecastItem> list = new ArrayList<>();
		ForecastItem fi1 = new MonthlyForecastItem(new Money(0.00), 22.4, new Money(1_000.00));
		ForecastItem fi2 = new MonthlyForecastItem(new Money(1_018.67), 22.4, new Money(1_000.00));
		ForecastItem fi3 = new MonthlyForecastItem(new Money(2_056.35), 22.4, new Money(1_000.00));

		list.add(fi1);
		list.add(fi2);
		list.add(fi3);

		return list;
	}

	private void assertListEquals(List<ForecastItem> expected, List<ForecastItem> actaul) {

		for (int index = 0; index < expected.size(); index++) {
			if (!expected.get(index).equals(actaul.get(index))) fail("");
		}
	}
}
