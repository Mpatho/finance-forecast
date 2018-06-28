package psybergate.grad2018.javafnds.finance.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.service.InvestmentForecastService;

@ManagedBean("Investment")
public class InvestmentForecastController extends ForecastController {

	@EJB
	private InvestmentForecastService investmentForecastService;

	public String save(Map<String, String[]> request, Map<String, Object> response) {
		Investment investment = getInvestmentById(request);
		updateInvestment(investment, request);
		investment.setName(request.get("name")[0]);
		investmentForecastService.save(investment);
		return viewForecasts(request, response);
	}

	public String delete(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		investmentForecastService.deleteInvestmentByName(name);
		return viewForecasts(request, response);
	}

	public String forecastInvestment(Map<String, String[]> request, Map<String, Object> response) {
		if (request.isEmpty()) {
			response.put("summary", investmentForecastService.getSummary(null));
			return "/WEB-INF/views/investment/forecast.jsp";
		}
		Investment investment = getInvestmentById(request);
		updateInvestment(investment, request);
		List<ForecastItem> forecastItems = investmentForecastService.getForecastItems(investment);
		loadInvestmentResponce(response, investment, forecastItems);
		return "/WEB-INF/views/investment/forecast.jsp";
	}

	private Investment getInvestmentById(Map<String, String[]> request) {
		Investment investment = null;
		if (request.get("id") != null && !request.get("id")[0].trim().equals("")) {
			Long id = Long.valueOf(request.get("id")[0]);
			investment = investmentForecastService.getById(id);
		}
		if (investment == null) {
			investment = new Investment();
		}
		return investment;
	}

	private void updateInvestment(Investment investment, Map<String, String[]> request) {
		if (validInput(request)) {
			Double rate = Double.valueOf(request.get("rate")[0]);
			Money amount = new Money(Double.valueOf(request.get("amount")[0]));
			Integer months = Integer.valueOf(request.get("months")[0]);
			String type = request.get("type")[0];
			investment.setRate(rate);
			investment.setAmount(amount);
			investment.setMonths(months);
			investment.setType(type);
		}
		getEvents(request, investment);
	}

	private void loadInvestmentResponce(Map<String, Object> response, Investment investment,
			List<ForecastItem> forecastItems) {
		response.put("summary", investmentForecastService.getSummary(investment));
		response.put("id", investment.getId());
		response.put("type", investment.getType());
		response.put("rate", investment.getRate().doubleValue());
		response.put("months", investment.getMonths());
		response.put("amount", investment.getAmount().doubleValue());
		response.put("forecastItems", forecastItems);
	}

	private boolean validInput(Map<String, String[]> request) {
		return request.get("amount") != null && request.get("rate") != null && request.get("months") != null && request.get(
				"type") != null;
	}
}
