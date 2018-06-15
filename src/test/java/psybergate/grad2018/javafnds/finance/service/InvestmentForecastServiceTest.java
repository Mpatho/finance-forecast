package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations.Mock;
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
	public void testSaveIfNameExist() {
		Whitebox.setInternalState(ifs, "investmentResource", investmentResource);
		Whitebox.setInternalState(ifs, "moneyResource", moneyResource);
		Investment investment1 = new FixedInvestment("Sizwe", new Money(10000), 20, new BigDecimal(8));
		Investment investment2 = new FixedInvestment("Sizwe", new Money(10000), 20, new BigDecimal(8));
		ifs.save(investment1);
		ifs.save(investment2);
	}

}
