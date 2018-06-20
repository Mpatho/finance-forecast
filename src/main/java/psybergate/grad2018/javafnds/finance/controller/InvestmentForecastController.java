package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.Collection;
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
public class InvestmentForecastController {

	@EJB
	private InvestmentForecastService investmentForecastService;

	public String save(Map<String, String[]> request, Map<String, Object> response) {
		Investment investment = null;
		if (validInput(request)) {
			String name = request.get("name")[0];
			Money initialAmount = new Money(Double.valueOf(request.get("initialAmount")[0]));
			BigDecimal rate = new BigDecimal(request.get("rate")[0]);
			Integer months = new Integer(request.get("months")[0]);

			String type = request.get("type")[0];

			if (type.equals("fixed")) {
				investment = new Investment(name, type, initialAmount, months, rate);
				for (int i = 0; i < request.get("eventType").length; i++) {

					if (validateEvent(investment.getMonths(), request.get("eventType")[i], request.get("eventValue")[i],
							request.get("eventMonth")[i])) {
						String eventType = request.get("eventType")[i];
						System.out.println("Mahlori");
						BigDecimal eventValue = new BigDecimal(request.get("eventValue")[i]);
						int month = Integer.parseInt(request.get("eventMonth")[i]);

						Event event = new Event(eventType, month, eventValue);
						event.setInvestment(investment);
						investment.addEvent(event);
					}

				}
			}
			else if (type.equals("monthly")) {
				investment = new Investment(name, type, initialAmount, months, rate);
				for (int i = 0; i < request.get("eventType").length; i++) {
					if (validateEvent(investment.getMonths(), request.get("eventType")[i], request.get("eventValue")[i],
							request.get("eventMonth")[i])) {
						String eventType = request.get("eventType")[i];

						BigDecimal eventValue = new BigDecimal(request.get("eventValue")[i]);
						Integer month = Integer.parseInt(request.get("eventMonth")[i]);
						Event event = new Event(eventType, month, eventValue);
						event.setInvestment(investment);
						investment.addEvent(event);
					}
				}
			}
		}
		else {
			// investment = investmentForecastService.getInvestmentByName(name);
		}
		for (Event event : investment.getEvents()) {

			System.out.println("month: " + event.getMonth() + " type: " + event.getType() + " value: " + event.getValue());
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
		if (request.isEmpty())
			return "/WEB-INF/views/investment/fixed.jsp";
		List<ForecastItem> forecastItems = null;
		Investment investment = getFixedInvestment(request);
		forecastItems = investmentForecastService.getForecastItemsByInv(investment);
		loadInvestmentResponce(response, investment, forecastItems);
		return "/WEB-INF/views/investment/fixed.jsp";
	}

	public String forecastMonthlyInvestment(Map<String, String[]> request, Map<String, Object> response) {
		if (request.isEmpty())
			return "/WEB-INF/views/investment/monthly.jsp";
		List<ForecastItem> forecastItems = null;
		Investment investment = getMonthlyInvestment(request);
		forecastItems = investmentForecastService.getForecastItemsByInv(investment);
		loadInvestmentResponce(response, investment, forecastItems);
		return "/WEB-INF/views/investment/monthly.jsp";
	}

	public String forecastInvestment(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		Investment investment = investmentForecastService.getInvestmentByName(name);
		List<ForecastItem> forecastItems = investmentForecastService.getForecastItemsByInv(investment);
		loadInvestmentResponce(response, investment, forecastItems);
		return "/WEB-INF/views/investment/" + investment.getType() + ".jsp";
	}

	private void loadInvestmentResponce(Map<String, Object> response, Investment investment,
			List<ForecastItem> forecastItems) {
		response.put("rate", investment.getRate().doubleValue());
		response.put("months", investment.getMonths());
		response.put("initialAmount", investment.getInitialAmount().doubleValue());
		response.put("forecastItems", forecastItems);
	}

	private boolean validInput(Map<String, String[]> request) {
		return request.get("initialAmount") != null && request.get("rate") != null && request.get("months") != null
				&& request.get("type") != null;
	}

	private Investment getMonthlyInvestment(Map<String, String[]> request) {
		Investment investment = null;
		if (validInput(request)) {
			BigDecimal rate = new BigDecimal(request.get("rate")[0]);
			Double doubleInitialAmount = Double.valueOf(request.get("initialAmount")[0]);
			Money initialAmount = new Money(doubleInitialAmount);
			Integer months = Integer.valueOf(request.get("months")[0]);
			investment = new Investment("temp", "monthly", initialAmount, months, rate);
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
			BigDecimal rate = new BigDecimal(request.get("rate")[0]);
			Double doubleInitialAmount = Double.valueOf(request.get("initialAmount")[0]);
			Money initialAmount = new Money(doubleInitialAmount);
			Integer months = Integer.valueOf(request.get("months")[0]);
			investment = new Investment("temp", "fixed", initialAmount, months, rate);
			for (int i = 0; i < request.get("eventType").length; i++) {
				if (validateEvent(investment.getMonths(), request.get("eventType")[i], request.get("eventValue")[i],
						request.get("eventMonth")[i])) {
					String eventType = request.get("eventType")[i];
					System.out.println("Event: " + i);
					BigDecimal eventValue = new BigDecimal(request.get("eventValue")[i]);
					int month = Integer.parseInt(request.get("eventMonth")[i]);
					Event event = new Event(eventType, month, eventValue);
					investment.addEvent(event);
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
		if (eventMonth == null || eventType == null || eventValue == null) {
			return false;
		}
		else if(eventMonth.trim().equals("") || eventType.trim().equals("") || eventValue.trim().equals("")) {
			return false;
		}
		Double doubleEventValue = Double.valueOf(eventValue);
		Integer integerEventMonth = Integer.valueOf(eventMonth);
		if (integerEventMonth > investmentMonths || integerEventMonth < 2 || doubleEventValue <= 0) {
			return false;
		}
		else if (eventType.equals(Event.RATE_CHANGE) && doubleEventValue > 100) {
			return false;
		}
		return true;
	}

}
