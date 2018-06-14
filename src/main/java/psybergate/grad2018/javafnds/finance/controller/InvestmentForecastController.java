package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import psybergate.grad2018.javafnds.finance.entity.FixedInvestment;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.entity.MonthlyInvestment;
import psybergate.grad2018.javafnds.finance.service.InvestmentForecastService;

@ManagedBean("Investment")
public class InvestmentForecastController {

	@EJB
	private InvestmentForecastService investmentForecastService;

	public String save(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		Investment investment = investmentForecastService.getInvestmentByName(name);
		if (investment == null) {
			Money initialAmount = new Money(Double.valueOf(request.get("initialAmount")[0]));
			BigDecimal rate = new BigDecimal(request.get("rate")[0]);
			Integer months = new Integer(request.get("months")[0]);
			String type = request.get("type")[0];
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
		return "/WEB-INF/views/investment/forecast.jsp";
	}

	public String delete(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		Investment investment = investmentForecastService.getInvestmentByName(name);
		if (investment != null && investmentForecastService.deleteInvestmentByName(name)) {
			response.put("message", "deleted successfully");
			response.put("investment", investment);
		}
		return "/WEB-INF/views/investment/forecast.jsp";
	}

	public String view(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		Investment investment = investmentForecastService.getInvestmentByName(name);
		response.put("investment", investment);
		return "/WEB-INF/views/investment/forecast.jsp";
	}

	public String viewAll(Map<String, String[]> request, Map<String, Object> response) {
		Collection<Investment> investments = investmentForecastService.getInvestments();
		response.put("investments", investments);
		return "/WEB-INF/views/investment/forecasts.jsp";
	}

}
