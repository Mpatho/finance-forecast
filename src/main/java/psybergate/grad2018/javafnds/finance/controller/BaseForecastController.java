package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;

import psybergate.grad2018.javafnds.finance.entity.Event;
import psybergate.grad2018.javafnds.finance.entity.ForecastEntity;

public abstract class BaseForecastController implements ForecastController {

    private static final String EVENT_TYPE = "eventType";
	private static final String EVENT_VALUE = "eventValue";
	private static final String EVENT_MONTH = "eventMonth";

    protected void addEvent(Map<String, String[]> request, ForecastEntity entity, int i) {
		if (validateEvent(entity.getMonths(), request.get(EVENT_TYPE)[i], request.get(EVENT_VALUE)[i], request.get(
                EVENT_MONTH)[i])) {
			String eventType = request.get(EVENT_TYPE)[i];
			BigDecimal eventValue = new BigDecimal(request.get(EVENT_VALUE)[i]);
			int month = Integer.parseInt(request.get(EVENT_MONTH)[i]);
			Event event = new Event(eventType, month, eventValue);
			entity.addEvent(event);
		}
	}

	protected void getEvents(Map<String, String[]> request, ForecastEntity entity) {
		if (request.get(EVENT_TYPE) != null) {
			entity.setEvents(new HashSet<>());
			for (int i = 0; i < request.get(EVENT_TYPE).length; i++) {
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
