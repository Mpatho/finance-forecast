
package psybergate.grad2018.javafnds.finance.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Event;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.service.BondForecastService;

@ManagedBean("Bond")
public class BondForecastController extends ForecastController {

	@EJB
	private BondForecastService bondForecastService;

	public String save(Map<String, String[]> request, Map<String, Object> response) {
		Bond bond;
		if (validInput(request)) {
			bond = getBond(request);
			bondForecastService.save(bond);
		}
		return viewForecasts(request, response);
	}

	private Bond getBond(Map<String, String[]> request) {
		Bond bond;
		Money price = new Money(Double.valueOf(request.get("price")[0]));
		Money deposit = new Money(request.get("deposit")[0]);
		Double rate = Double.valueOf(request.get("rate")[0]);
		Integer months = Integer.valueOf(request.get("months")[0]);
		String name = request.get("name")[0];
		if (request.get("id")[0].length() != 0) {
			Long id = Long.valueOf(request.get("id")[0]);
			bond = new Bond(id, price, deposit, rate, months, name);
		}
		else {
			bond = new Bond(price, deposit, rate, months, name);
		}
		if (request.get("eventType") != null) {
			for (int i = 0; i < request.get("eventType").length; i++) {
				if (validateEvent(bond.getMonths(), request.get("eventType")[i], request.get("eventValue")[i],
						request.get("eventMonth")[i])) {
					String eventType = request.get("eventType")[i];
					BigDecimal eventValue = new BigDecimal(request.get("eventValue")[i]);
					int month = Integer.parseInt(request.get("eventMonth")[i]);
					Event event = new Event(eventType, month, eventValue);
					System.out.println(
							"Event: Type" + event.getType() + " Value: " + event.getValue() + " Month: " + event.getMonth());
					bond.addEvent(event);
				}
			}
		}
		return bond;
	}

	public String delete(Map<String, String[]> request, Map<String, Object> response) {
		String name = request.get("name")[0];
		bondForecastService.deleteBondByName(name);
		return viewForecasts(request, response);
	}

	public String forecastBond(Map<String, String[]> request, Map<String, Object> response) {
		if (validInput(request)) {
			Bond bond = getBond(request);
			forecastBond(request, response, bond);
		}
		else if (request.get("name") != null) {
			Bond bond = bondForecastService.getBondByName(request.get("name")[0]);
			forecastBond(request, response, bond);
		}
		return "/WEB-INF/views/bond/forecast.jsp";
	}

	private void forecastBond(Map<String, String[]> request, Map<String, Object> response, Bond bond) {
		boolean required = request.get("include_cash_required") != null ? true : false;
		List<ForecastItem> forecastItems = bondForecastService.getForecastItems(bond, required);
		setBond(response, bond);
		response.put("checked", required ? "checked" : "");
		response.put("forecastItems", forecastItems);
	}

	private void setBond(Map<String, Object> response, Bond bond) {
		response.put("id", bond.getId());
		response.put("price", bond.getPrice().doubleValue());
		response.put("deposit", bond.getDeposit().doubleValue());
		response.put("rate", bond.getRate().doubleValue());
		response.put("months", bond.getMonths());
		response.put("bondCost", bondForecastService.getBondCost(bond).stringValue());
		response.put("transferCost", bondForecastService.getTransferCost(bond).stringValue());
		response.put("legalCost", bondForecastService.getLegalCost(bond).stringValue());
		response.put("cashRequired", bondForecastService.getCashRequired(bond).stringValue());
	}

	private boolean validInput(Map<String, String[]> request) {
		return request.get("price") != null && request.get("rate") != null && request.get("deposit") != null && request.get(
				"months") != null;
	}
	
	private boolean validateEvent(Integer investmentMonths, String eventType, String eventValue, String eventMonth) {
		if (eventMonth == null || eventType == null || eventValue == null) {
			return false;
		}
		else if (eventMonth.trim().equals("") || eventType.trim().equals("") || eventValue.trim().equals("")) {
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