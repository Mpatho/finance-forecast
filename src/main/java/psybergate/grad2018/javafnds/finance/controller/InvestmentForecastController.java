package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.FixedInvestment;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.entity.MonthlyInvestment;
import psybergate.grad2018.javafnds.finance.service.ForecastService;
import psybergate.grad2018.javafnds.finance.service.InvestmentForecastService;

@ManagedBean("Investment")
public class InvestmentForecastController {

	@EJB
	private InvestmentForecastService investmentForecastService;

	@EJB
	private ForecastService fixedInvestmentForecastService;

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
			response.put("type", type);
			investmentForecastService.save(investment);
		}
		return viewAll(request, response);
	}

	public String delete(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		Investment investment = investmentForecastService.getInvestmentByName(name);
		if (investment != null && investmentForecastService.deleteInvestmentByName(name)) {
			response.put("message", "deleted successfully");
			response.put("investment", investment);
		}
		return viewAll(request, response);
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

	public String forecast(Map<String, String[]> request, Map<String, Object> response) {
		if (request.isEmpty()) return "/WEB-INF/views/investment/forecast.jsp";
		String name = request.get("name") == null ? null : request.get("name")[0];
		List<ForecastItem> forecastItems = fixedInvestmentForecastService.getForecastItems(name);
		if (forecastItems == null) {
			BigDecimal rate = new BigDecimal(request.get("rate")[0]);
			Double doubleInitialAmount = Double.valueOf(request.get("initialAmount")[0]);
			Money initialAmount = new Money(doubleInitialAmount);
			Integer months = Integer.valueOf(request.get("months")[0]);
			String type = request.get("type")[0];
			if (type.equals("fixed")) {
				Investment investment = new FixedInvestment(name, initialAmount, months, rate);
				forecastItems = fixedInvestmentForecastService.getForecastItems(investment);
			}
			else if (type.equals("monthly")) {
				Investment investment = new MonthlyInvestment(name, initialAmount, months, rate);
				// this is incorrect logic 
				forecastItems = fixedInvestmentForecastService.getForecastItems(investment);
			}
			response.put("type", type);
			response.put("rate", rate.doubleValue());
			response.put("months", months);
			response.put("initialAmount", initialAmount);
		}
		response.put("forecastItems", forecastItems);
		return "/WEB-INF/views/investment/forecast.jsp";
	}
}
