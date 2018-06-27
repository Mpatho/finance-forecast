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
		Investment investment = null;
		if (validInput(request)) {
			String name = request.get("name")[0];
			Money amount = new Money(Double.valueOf(request.get("amount")[0]));
			Double rate = Double.valueOf(request.get("rate")[0]);
			Integer months = new Integer(request.get("months")[0]);
			String type = request.get("type")[0];

			if (request.get("id")[0].length() != 0) {
				Long id = Long.valueOf(request.get("id")[0]);
				investment = new Investment(id, name, type, amount, months, rate);
			}
			else {
				investment = new Investment(name, type, amount, months, rate);
			}
			for (int i = 0; i < request.get("eventType").length; i++) {

				if (validateEvent(investment.getMonths(), request.get("eventType")[i], request.get("eventValue")[i], request
						.get("eventMonth")[i])) {
					String eventType = request.get("eventType")[i];
					BigDecimal eventValue = new BigDecimal(request.get("eventValue")[i]);
					int month = Integer.parseInt(request.get("eventMonth")[i]);

					Event event = new Event(eventType, month, eventValue);
					investment.addEvent(event);
				}

			}
			investmentForecastService.save(investment);
		}
		return viewForecasts(request, response);
	}

	public String delete(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		investmentForecastService.deleteInvestmentByName(name);
		return viewForecasts(request, response);
	}

	public String forecastFixedInvestment(Map<String, String[]> request, Map<String, Object> response) {
		List<ForecastItem> forecastItems = null;
		Investment investment = getFixedInvestment(request);
		forecastItems = investmentForecastService.getForecastItems(investment);
		loadInvestmentResponce(response, investment, forecastItems);
		return "/WEB-INF/views/investment/forecast.jsp";
	}

	public String forecastMonthlyInvestment(Map<String, String[]> request, Map<String, Object> response) {
		List<ForecastItem> forecastItems = null;
		Investment investment = getMonthlyInvestment(request);
		forecastItems = investmentForecastService.getForecastItems(investment);
		loadInvestmentResponce(response, investment, forecastItems);
		return "/WEB-INF/views/investment/forecast.jsp";
	}

	public String forecastInvestment(Map<String, String[]> request, Map<String, Object> response) {
		if (request.get("type") != null) {
			if (request.get("type")[0].equals("fixed")) return forecastFixedInvestment(request, response);
			if (request.get("type")[0].equals("monthly")) return forecastMonthlyInvestment(request, response);
		}
		Investment investment = null;
		if (request.get("name") != null && !request.get("name")[0].trim().equals("")) {
			String name = request.get("name")[0];
			investment = investmentForecastService.getInvestmentByName(name);
			List<ForecastItem> forecastItems = investmentForecastService.getForecastItems(investment);
			loadInvestmentResponce(response, investment, forecastItems);
			return "/WEB-INF/views/investment/forecast.jsp";
		}
		response.put("summary", investmentForecastService.getSummary(investment));
		return "/WEB-INF/views/investment/forecast.jsp";
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

	private Investment getMonthlyInvestment(Map<String, String[]> request) {
		Investment investment = null;
		if (validInput(request)) {
			Long id = request.get("id")[0].length() == 0 ? null : Long.valueOf(request.get("id")[0]);
			Double rate = Double.valueOf(request.get("rate")[0]);
			Double doubleInitialAmount = Double.valueOf(request.get("amount")[0]);
			Money amount = new Money(doubleInitialAmount);
			Integer months = Integer.valueOf(request.get("months")[0]);
			investment = new Investment(id, "temp", "monthly", amount, months, rate);
			if (request.get("eventType") != null) {
				for (int i = 0; i < request.get("eventType").length; i++) {
					if (validateEvent(investment.getMonths(), request.get("eventType")[i], request.get("eventValue")[i], request
							.get("eventMonth")[i])) {
						String eventType = request.get("eventType")[i];
						BigDecimal eventValue = new BigDecimal(request.get("eventValue")[i]);
						int month = Integer.parseInt(request.get("eventMonth")[i]);
						Event event = new Event(eventType, month, eventValue);
						investment.addEvent(event);
					}
				}
			}
		}
		else {
			String name = request.get("name")[0];
			investment = investmentForecastService.getInvestmentByName(name);
		}
		return investment;
	}

	private Investment getFixedInvestment(Map<String, String[]> request) {
		Investment investment = null;
		if (validInput(request)) {
			Long id = request.get("id")[0].length() == 0 ? null : Long.valueOf(request.get("id")[0]);
			Double rate = Double.valueOf(request.get("rate")[0]);
			Double doubleInitialAmount = Double.valueOf(request.get("amount")[0]);
			Money amount = new Money(doubleInitialAmount);
			Integer months = Integer.valueOf(request.get("months")[0]);
			investment = new Investment(id, "temp", "fixed", amount, months, rate);
			if (request.get("eventType") != null) {
				for (int i = 0; i < request.get("eventType").length; i++) {
					if (validateEvent(investment.getMonths(), request.get("eventType")[i], request.get("eventValue")[i], request
							.get("eventMonth")[i])) {
						String eventType = request.get("eventType")[i];
						BigDecimal eventValue = new BigDecimal(request.get("eventValue")[i]);
						int month = Integer.parseInt(request.get("eventMonth")[i]);
						Event event = new Event(eventType, month, eventValue);
						investment.addEvent(event);
					}
				}
			}
		}
		else {
			String name = request.get("name")[0];
			investment = investmentForecastService.getInvestmentByName(name);
		}
		return investment;
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
