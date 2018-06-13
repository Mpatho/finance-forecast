package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import psybergate.grad2018.javafnds.finance.entity.Investment;

public class Forecast {
	
	private List<ForecastItem> forecastItems = new ArrayList<>();
	
	public List<ForecastItem> getForecastItems(Investment fixedInvestment) {
		BigDecimal currentAmount = fixedInvestment.getInitailAmount();
		for (int i = 1; i < fixedInvestment.getMonths(); i++) {
			ForecastItem fi = new FixedForecastItem(currentAmount, fixedInvestment.getRate());
			forecastItems.add(fi);
			currentAmount = fi.getEndMonthBalance();
		}
		
		return forecastItems;
	}
}
