package psybergate.grad2018.javafnds.finance.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import psybergate.grad2018.javafnds.finance.entity.FixedInvestment;
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
		Investment investment = new FixedInvestment(null, new Money(10000), 20, new BigDecimal(8));
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfNameIsEmpty() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new FixedInvestment("", new Money(10000), 20, new BigDecimal(8));
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfInvestmentIsFine() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new FixedInvestment("Sizwe", new Money(10000), 20, new BigDecimal(8));
		assertTrue(ifs.save(investment));
	}

	@Test
	public void testSaveIfInitialAmountIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new FixedInvestment("Sizwe", null, 20, new BigDecimal(8));
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfRateIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new FixedInvestment("Sizwe", new Money(10000), 20, null);
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfRateIsNegative() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new FixedInvestment("Sizwe", new Money(10000), 20, new BigDecimal(-8));
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfMonthsIsNegative() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new FixedInvestment("Sizwe", new Money(10000), -20, new BigDecimal(-8));
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfMonthsEqualsZero() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new FixedInvestment("Sizwe", new Money(10000), 0, new BigDecimal(-8));
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testSaveIfMonthsIsNull() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment = new FixedInvestment("Sizwe", new Money(10000), null, new BigDecimal(-8));
		assertFalse(ifs.save(investment));
	}

	@Test
	public void testDeleteIfInvestmentExist() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new FixedInvestment("Sizwe", new Money(10000), 20, new BigDecimal(8));
		when(investmentResource.contains(investment)).thenReturn(true);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		assertTrue(ifs.delete(investment));
	}

	@Test
	public void testDeleteIfInvestmentNotExist() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Investment investment = new FixedInvestment("Sizwe", new Money(10000), 20, new BigDecimal(8));
		when(investmentResource.contains(investment)).thenReturn(false);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		assertFalse(ifs.delete(investment));
	}
}
