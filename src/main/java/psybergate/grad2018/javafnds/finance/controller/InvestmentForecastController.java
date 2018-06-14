package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Inject;

import psybergate.grad2018.javafnds.finance.entity.FixedInvestment;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.entity.MonthlyInvestment;
import psybergate.grad2018.javafnds.finance.service.ForecastItem;
import psybergate.grad2018.javafnds.finance.service.ForecastService;
import psybergate.grad2018.javafnds.finance.service.InvestmentForecastService;

@ManagedBean("Investment")
public class InvestmentForecastController {

	@EJB
	private InvestmentForecastService investmentForecastService;
	
	@Inject
	private ForecastService fixedInvestmentForecastService;

	public InvestmentForecastController() {
		System.out.println("daljda");
	}

	public String save(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		Investment investment = investmentForecastService.getInvestmentByName(name);
		Money initialAmount = null;
		BigDecimal rate = null;
		Integer months = null;
		String type;
		if (investment == null) {
			initialAmount = new Money(Double.valueOf(request.get("initialAmount")[0]));
			rate = new BigDecimal(request.get("rate")[0]);
			months = new Integer(request.get("months")[0]);
			type = request.get("type")[0];
			if (type.equals("fixed")) {
				investment = new FixedInvestment(name, initialAmount, months, rate);
			}
			else if (type.equals("monthly")) {
				investment = new MonthlyInvestment(name, initialAmount, months, rate);
			}
		}
		System.out.println("investment: " + investment);
		boolean saved = investmentForecastService.save(investment);
		response.put("saved", saved);
		response.put("forecast", saved);

		List<ForecastItem> forecastItems = fixedInvestmentForecastService.getForecastItems(name);
		
		response.put("forecastItems", forecastItems);
		response.put("rate", rate.doubleValue());
		response.put("months", months);
		response.put("initialAmount", initialAmount);
		return "/WEB-INF/views/investment/forecast.jsp";
	}

	public String delete(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		boolean deleted = investmentForecastService.deleteInvestmentByName(name);
		response.put("deleted", deleted);
		response.put("forecast", deleted);
		return "/WEB-INF/views/investment/forecast.jsp";
	}

	public String view(Map<String, String[]> request, Map<String, Object> response) {
		System.out.println("in view");
		Collection<Investment> investments = investmentForecastService.getInvestments();
		response.put("investments", investments);
		return "/WEB-INF/views/investment/forecasts.jsp";
	}

}
