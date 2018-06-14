package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;

@Stateful
public class FixedInvestmentForecastService extends AbstractForecastService implements ForecastService {

	@EJB
	private InvestmentForecastService inv;

	@Override
	public List<ForecastItem> getForecastItems() {

		List<ForecastItem> forecastItems = new ArrayList<>();
		Money currentAmount = getInitialAmount();
		BigDecimal rate = getRate();

		return createForecastItemsList(forecastItems, rate, currentAmount);

	}

	@Override
	public List<ForecastItem> getForecastItems(String name) {
		Investment investment = inv.getInvestmentByName(name);
		List<ForecastItem> forecastItems = new ArrayList<>();
		BigDecimal rate = investment.getRate();
		Money currentAmount = investment.getInitialAmount();

		return createForecastItemsList(forecastItems, rate, currentAmount);

	}

	private boolean isValidateFixedInvestment() {
		Money initialAmount = getInitialAmount();
		Integer months = getMonths();
		BigDecimal rate = getRate();
		if (initialAmount == null || initialAmount.compareTo(new Money(0)) <= 0)
			return false;
		else if (months == null || months <= 0)
			return false;
		else if (rate == null || rate.doubleValue() <= 0 || rate.doubleValue() > 100)
			return false;

		return true;
	}

	private List<ForecastItem> createForecastItemsList(List<ForecastItem> forecastItems, BigDecimal rate,
			Money currentAmount) {
		if (isValidateFixedInvestment()) {
			for (int i = 0; i < getMonths(); i++) {
				ForecastItem item = new FixedForecastItem(currentAmount, rate);
				forecastItems.add(item);
				currentAmount = item.getEndAmount();
			}
		}
		return forecastItems;
	}

}
