package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Event;
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
			investment = investmentForecastService.getInvestmentById(id);
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

	private void addEvent(Map<String, String[]> request, Investment investment, int i) {
		if (validateEvent(investment.getMonths(), request.get("eventType")[i], request.get("eventValue")[i], request.get(
				"eventMonth")[i])) {
			String eventType = request.get("eventType")[i];
			BigDecimal eventValue = new BigDecimal(request.get("eventValue")[i]);
			int month = Integer.parseInt(request.get("eventMonth")[i]);
			Event event = new Event(eventType, month, eventValue);
			investment.addEvent(event);
		}
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

	private void getEvents(Map<String, String[]> request, Investment investment) {
		if (request.get("eventType") != null) {
			for (int i = 0; i < request.get("eventType").length; i++) {
				addEvent(request, investment, i);
			}
		}
	}

	private boolean validateEvent(Integer investmentMonths, String eventType, String eventValue, String eventMonth) {
		if (eventMonth == null || eventType == null || eventValue == null) return false;
		if (eventMonth.equals("") || eventType.equals("") || eventValue.equals("")) return false;
		Double doubleEventValue = Double.valueOf(eventValue);
		Integer integerEventMonth = Integer.valueOf(eventMonth);
		if (integerEventMonth > investmentMonths || integerEventMonth < 2 || doubleEventValue <= 0) return false;
		if (eventType.equals(Event.RATE_CHANGE) && doubleEventValue > 100) return false;
		return true;
	}

}
