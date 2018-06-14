package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.service.ForecastItem;
import psybergate.grad2018.javafnds.finance.service.ForecastService;

@ManagedBean("FixedInvestment")
public class FixedInvestmentForecastController {

	@EJB
	private ForecastService fixedInvestmentForecastService;
	
	public String getForecast(Map<String, String[]> request, Map<String, Object> response) {
		BigDecimal rate =  new BigDecimal(request.get("rate")[0]);
		
		Double doubleInitialAmount = Double.valueOf(request.get("initialAmout")[0]);
		Money initialAmount =  new Money(doubleInitialAmount);
		Integer numOfMonths = Integer.valueOf(request.get("months")[0]);
		
		fixedInvestmentForecastService.setInitialAmount(initialAmount);
		fixedInvestmentForecastService.setMonths(numOfMonths);
		fixedInvestmentForecastService.setRate(rate);
		List<ForecastItem> forecastItems = fixedInvestmentForecastService.getForecastItems();
		
		response.put("forecastItems", forecastItems);
		
		
		return "/WEB-INF/views/fixed_investment/forecast.jsp";
		
	}
	
}
