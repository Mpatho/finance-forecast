package psybergate.grad2018.javafnds.finance.controller;

import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Inject;

import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.service.InvestmentForecastService;

@ManagedBean("Investment")
public class InvestmentForecastController extends AbstractForecastController {

	@EJB
	private InvestmentForecastService investmentForecastService;
	
	@Inject
	private ForecastsController forecasts;

	@Override
	public String save(Map<String, String[]> request, Map<String, Object> response) {
		Investment investment = getInvestment(request);
		updateInvestment(investment, request);
		investment.setName(request.get("name")[0]);
		investmentForecastService.save(investment);
		return forecasts.viewForecasts(request, response);
	}

	@Override
	public String delete(Map<String, String[]> request, Map<String, Object> response) {
		Investment investment = getInvestment(request);
		investmentForecastService.delete(investment);
		return forecasts.viewForecasts(request, response);
	}

	@Override
	public String forecast(Map<String, String[]> request, Map<String, Object> response) {
		if (request.isEmpty()) {
			response.put("summary", investmentForecastService.getSummary(null));
			return "/WEB-INF/views/investment/forecast.jsp";
		}
		Investment investment = getInvestment(request);
		updateInvestment(investment, request);
		loadInvestmentResponce(response, investment);
		return "/WEB-INF/views/investment/forecast.jsp";
	}

	private Investment getInvestment(Map<String, String[]> request) {
		if (request.get("id") != null && request.get("id")[0].trim().length() != 0) {
			Long id = Long.valueOf(request.get("id")[0]);
			return investmentForecastService.getById(id);
		}
		return new Investment();
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

	private void loadInvestmentResponce(Map<String, Object> response, Investment investment) {
		response.put("id", investment.getId());
		response.put("type", investment.getType());
		response.put("rate", investment.getRate().doubleValue());
		response.put("months", investment.getMonths());
		response.put("amount", investment.getAmount().doubleValue());
		response.put("summary", investmentForecastService.getSummary(investment));
		response.put("forecastItems", investmentForecastService.getForecastItems(investment));
	}

	private boolean validInput(Map<String, String[]> request) {
		return request.get("amount") != null && request.get("rate") != null && request.get("months") != null && request.get(
				"type") != null;
	}
}
