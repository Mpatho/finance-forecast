package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import psybergate.grad2018.javafnds.finance.entity.Money;

@Stateful
public class FixedInvestmentForecastService extends AbstractForecastService implements ForecastService {

	@EJB
	private InvestmentForecastService inv;
	
	@Override
	public List<ForecastItem> getForecastItems() {

		List<ForecastItem> forecastItems = new ArrayList<>();
		if (isValidateFixedInvestment()) {
			Money currentAmount = getInitialAmount();
			for (int i = 0; i < getMonths(); i++) {
				ForecastItem item = new FixedForecastItem(currentAmount, getRate());
				forecastItems.add(item);
				currentAmount = item.getEndAmount();
			}
		}
		return forecastItems;
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

	@Override
	public List<ForecastItem> getForecastItems(String name) {
	inv.getInvestmentByName(name);
		return null;
	}

}
