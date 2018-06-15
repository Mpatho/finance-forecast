package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import psybergate.grad2018.javafnds.finance.bean.FixedForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.FixedInvestment;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;

@Stateful
public class FixedInvestmentForecastService implements ForecastService {

	@EJB
	private InvestmentForecastService inv;

	@Override
	public List<ForecastItem> getForecastItems(Investment investment) {
		if (investment == null) return null;
		List<ForecastItem> forecastItems = new ArrayList<>();
		if (isValidateFixedInvestment(investment)) {
			Money currentAmount = investment.getInitialAmount();
			for (int i = 0; i < investment.getMonths(); i++) {
				ForecastItem item = new FixedForecastItem(currentAmount, investment.getRate());
				forecastItems.add(item);
				currentAmount = item.getEndAmount();
			}
		}
		return forecastItems;
	}

	@Override
	public List<ForecastItem> getForecastItems(String name) {
		Investment investment = inv.getInvestmentByName(name);
		return getForecastItems(investment);
	}

	private boolean isValidateFixedInvestment(Investment investment) {
		Money initialAmount = investment.getInitialAmount();
		Integer months = investment.getMonths();
		BigDecimal rate = investment.getRate();
		if (initialAmount == null || initialAmount.compareTo(new Money(0)) <= 0) return false;
		else if (months == null || months <= 0) return false;
		else if (rate == null || rate.doubleValue() <= 0 || rate.doubleValue() > 100) return false;
		return true;
	}

	private List<ForecastItem> createForecastItemsList(List<ForecastItem> forecastItems, BigDecimal rate,
			Money currentAmount) {
		Investment investment = new FixedInvestment(null, currentAmount, null, rate);
		if (isValidateFixedInvestment(investment)) {
			for (int i = 0; i < investment.getMonths(); i++) {
				ForecastItem item = new FixedForecastItem(currentAmount, rate);
				forecastItems.add(item);
				currentAmount = item.getEndAmount();
			}
		}
		return forecastItems;
	}

}
