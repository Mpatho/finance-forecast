package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Named;

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
	@Named("fixed")
	private ForecastService fixedInvestmentForecastService;

	@EJB
	@Named("monthly")
	private ForecastService monthlyInvestmentForecastService;

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
		investmentForecastService.save(investment);
		return viewForecasts(request, response);
	}

	public String delete(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		Investment investment = investmentForecastService.getInvestmentByName(name);
		if (investment != null && investmentForecastService.deleteInvestmentByName(name)) {
			response.put("message", "deleted successfully");
			response.put("investment", investment);
		}
		return viewForecasts(request, response);
	}

	public String viewForecasts(Map<String, String[]> request, Map<String, Object> response) {
		Collection<Investment> investments = investmentForecastService.getInvestments();
		response.put("investments", investments);
		return "/WEB-INF/views/investment/forecasts.jsp";
	}

	public String forecastFixedInvestment(Map<String, String[]> request, Map<String, Object> response) {
		if (request.isEmpty()) return "/WEB-INF/views/investment/fixed.jsp";
		List<ForecastItem> forecastItems = null;
		Investment investment = getFixedInvestment(request);
		forecastItems = fixedInvestmentForecastService.getForecastItems(investment);
		response.put("rate", investment.getRate().doubleValue());
		response.put("months", investment.getMonths());
		response.put("initialAmount", investment.getInitialAmount());
		response.put("forecastItems", forecastItems);
		return "/WEB-INF/views/investment/fixed.jsp";
	}

	public String forecastMonthlyInvestment(Map<String, String[]> request, Map<String, Object> response) {
		if (request.isEmpty()) return "/WEB-INF/views/investment/monthly.jsp";
		List<ForecastItem> forecastItems = null;
		Investment investment = getMonthlyInvestment(request);
		response.put("rate", investment.getRate().doubleValue());
		response.put("months", investment.getMonths());
		response.put("initialAmount", investment.getInitialAmount());
		response.put("forecastItems", forecastItems);
		return "/WEB-INF/views/investment/monthly.jsp";
	}

	public String forecastInvestment(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		Investment investment = investmentForecastService.getInvestmentByName(name);
		if (investment.getClass().equals(FixedInvestment.class)) {
			List<ForecastItem> forecastItems = fixedInvestmentForecastService.getForecastItems(investment);
			response.put("rate", investment.getRate().doubleValue());
			response.put("months", investment.getMonths());
			response.put("initialAmount", investment.getInitialAmount());
			response.put("forecastItems", forecastItems);
			return "/WEB-INF/views/investment/fixed.jsp";
		}
		if (investment.getClass().equals(MonthlyInvestment.class)) {
			List<ForecastItem> forecastItems = monthlyInvestmentForecastService.getForecastItems(investment);
			response.put("rate", investment.getRate().doubleValue());
			response.put("months", investment.getMonths());
			response.put("initialAmount", investment.getInitialAmount());
			response.put("forecastItems", forecastItems);
			return "/WEB-INF/views/investment/monthly.jsp";
		}
		return "/WEB-INF/views/investment/forecasts.jsp";

	}

	private Investment getMonthlyInvestment(Map<String, String[]> request) {
		Investment investment = null;
		if (request.get("name") == null) {
			BigDecimal rate = new BigDecimal(request.get("rate")[0]);
			Double doubleInitialAmount = Double.valueOf(request.get("initialAmount")[0]);
			Money initialAmount = new Money(doubleInitialAmount);
			Integer months = Integer.valueOf(request.get("months")[0]);
			investment = new MonthlyInvestment(null, initialAmount, months, rate);
		}
		else {
			String name = request.get("name")[0];
			investment = investmentForecastService.getInvestmentByName(name);
		}
		return investment;
	}

	private Investment getFixedInvestment(Map<String, String[]> request) {
		Investment investment = null;
		if (request.get("name") == null) {
			BigDecimal rate = new BigDecimal(request.get("rate")[0]);
			Double doubleInitialAmount = Double.valueOf(request.get("initialAmount")[0]);
			Money initialAmount = new Money(doubleInitialAmount);
			Integer months = Integer.valueOf(request.get("months")[0]);
			investment = new FixedInvestment(null, initialAmount, months, rate);
		}
		else {
			String name = request.get("name")[0];
			investment = investmentForecastService.getInvestmentByName(name);
		}
		return investment;
	}
}
