package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations.Mock;
import org.mockito.internal.util.reflection.Whitebox;
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
	public void test() {
		Investment investment1 = new FixedInvestment("Sizwe", new Money(1_000_000), 3, new BigDecimal(8));
		fifs.getForecastItems(investment1);
		assertEquals(getForecastItems().size(), fifs.getForecastItems(investment1).size());
		
		List<ForecastItem> serviceForecastItems = fifs.getForecastItems(investment1);
		List<ForecastItem> testForecastItems = getForecastItems();
		for(int i = 0; i < getForecastItems().size(); i++) {
			assertEquals(testForecastItems.get(i).getInitialAmount().stringValue(), serviceForecastItems.get(i).getInitialAmount().stringValue());
		}
	}
	
	private List<ForecastItem> getForecastItems(){
		
		List<ForecastItem> list = new ArrayList<>();
		ForecastItem fi1 = new FixedForecastItem(new Money(1_000_000),new BigDecimal(8));
		ForecastItem fi2 = new FixedForecastItem(new Money(1_006_666.67),new BigDecimal(8));
		ForecastItem fi3 = new FixedForecastItem(new Money(1_013_377.78),new BigDecimal(8));

		list.add(fi1);
		list.add(fi2);
		list.add(fi3);

		return list;
	}
	@Test
	public void testNullInvestment() {
		assertNull(fifs.getForecastItems(investment));
	}
	
	@Test(expected=RuntimeException.class)
	public void testInvalidInitialAmount() {
		investment = new FixedInvestment("Mahlori", null, 20, new BigDecimal(10));
		List<ForecastItem> forecastItems = fifs.getForecastItems(investment);
		investment.setInitialAmount(new Money(-1));
		fifs.getForecastItems(investment);
		investment.setInitialAmount(new Money(0));
		fifs.getForecastItems(investment);
	}
	@Test(expected=RuntimeException.class)
	public void testInvalidI() {
		investment = new FixedInvestment("Mahlori", null, 20, new BigDecimal(10));
		List<ForecastItem> forecastItems = fifs.getForecastItems(investment);
	}

}
