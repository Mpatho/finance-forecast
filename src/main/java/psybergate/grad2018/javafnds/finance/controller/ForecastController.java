package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Event;
import psybergate.grad2018.javafnds.finance.entity.ForecastEntity;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.service.BondForecastService;
import psybergate.grad2018.javafnds.finance.service.InvestmentForecastService;

@ManagedBean("Forecast")
public class ForecastController {

	@EJB
	private InvestmentForecastService investmentForecastService;

	@EJB
	private BondForecastService bondForecastService;

	public String viewForecasts(Map<String, String[]> request, Map<String, Object> response) {
		Collection<Investment> investments = investmentForecastService.getInvestments();
		Collection<Bond> bonds = bondForecastService.getBonds();
		response.put("investments", investments);
		response.put("bonds", bonds);
		return "/WEB-INF/views/forecast/forecasts.jsp";
	}

	protected void addEvent(Map<String, String[]> request, ForecastEntity entity, int i) {
		if (validateEvent(entity.getMonths(), request.get("eventType")[i], request.get("eventValue")[i], request.get(
				"eventMonth")[i])) {
			String eventType = request.get("eventType")[i];
			BigDecimal eventValue = new BigDecimal(request.get("eventValue")[i]);
			int month = Integer.parseInt(request.get("eventMonth")[i]);
			Event event = new Event(eventType, month, eventValue);
			entity.addEvent(event);
		}
	}

	protected void getEvents(Map<String, String[]> request, ForecastEntity entity) {
		if (request.get("eventType") != null) {
			for (int i = 0; i < request.get("eventType").length; i++) {
				addEvent(request, entity, i);
			}
		}
	}

	protected boolean validateEvent(Integer investmentMonths, String eventType, String eventValue, String eventMonth) {
		if (eventMonth == null || eventType == null || eventValue == null) return false;
		if (eventMonth.equals("") || eventType.equals("") || eventValue.equals("")) return false;
		Double doubleEventValue = Double.valueOf(eventValue);
		Integer integerEventMonth = Integer.valueOf(eventMonth);
		if (integerEventMonth > investmentMonths || integerEventMonth < 2 || doubleEventValue <= 0) return false;
		if (eventType.equals(Event.RATE_CHANGE) && doubleEventValue > 100) return false;
		return true;
	}

}
