package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Inject;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.FixedInvestment;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.service.ForecastService;

@ManagedBean("FixedInvestment")
public class FixedInvestmentForecastController {

	@EJB
	private ForecastService<Investment, ForecastItem> fixedInvestmentForecastService;

	private InvestmentForecastController investmentForecastController;

	public String forecast(Map<String, String[]> request, Map<String, Object> response) {
		if (request.isEmpty()) { return "/WEB-INF/views/fixed_investment/forecast.jsp"; }
		BigDecimal rate = new BigDecimal(request.get("rate")[0]);

		Double doubleInitialAmount = Double.valueOf(request.get("initialAmount")[0]);
		Money initialAmount = new Money(doubleInitialAmount);
		Integer months = Integer.valueOf(request.get("months")[0]);

		Investment investment = new FixedInvestment(null, initialAmount, months, rate);
		List<ForecastItem> forecastItems = fixedInvestmentForecastService.getForecastItems(investment);

		response.put("forecastItems", forecastItems);

		return "/WEB-INF/views/fixed_investment/forecast.jsp";

	}

}
