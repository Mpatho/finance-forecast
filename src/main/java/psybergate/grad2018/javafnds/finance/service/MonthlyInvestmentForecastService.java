package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.bean.MonthlyForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.resource.InvestmentResource;

@Stateless
@Named("monthly")
public class MonthlyInvestmentForecastService implements ForecastService {

	@EJB
	private InvestmentResource invRes;
	
	@Override
	public List<ForecastItem> getForecastItems(Investment investment) {
		if (investment == null) return null;
		
		List<ForecastItem> forecastItems = new ArrayList<>();
		if (isValidateFixedInvestment(investment)) {
			
			Money currentAmount = investment.getInitialAmount();
			Money monthlyAmount = currentAmount;
			currentAmount = new Money(0);
			for (int i = 0; i < investment.getMonths(); i++) {
				ForecastItem item = new MonthlyForecastItem(currentAmount, investment.getRate(), monthlyAmount);
				forecastItems.add(item);
				currentAmount = item.getEndAmount();
			}
		} 
		else {
			throw new RuntimeException("Invalid investment");
		}
		return forecastItems;
	}

	@Override
	public List<ForecastItem> getForecastItems(String name) {
		Investment investment = invRes.getByName(name);
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

}
