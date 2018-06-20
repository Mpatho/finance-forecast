package psybergate.grad2018.javafnds.finance.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Bond;
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
			List<ForecastItem> forecastItems = bondForecastService.getForecastItems(bond);
			setBond(response, bond);
			response.put("forecastItems", forecastItems);
		}
		else if (request.get("name") != null) {
			Bond bond = bondForecastService.getBondByName(request.get("name")[0]);
			List<ForecastItem> forecastItems = bondForecastService.getForecastItems(bond);
			setBond(response, bond);
			response.put("forecastItems", forecastItems);
		}
		return "/WEB-INF/views/bond/forecast.jsp";
	}

	private void setBond(Map<String, Object> response, Bond bond) {
		response.put("id", bond.getId());
		response.put("price", bond.getPrice().doubleValue());
		response.put("deposit", bond.getDeposit().doubleValue());
		response.put("rate", bond.getRate().doubleValue());
		response.put("months", bond.getMonths());
	}

	private boolean validInput(Map<String, String[]> request) {
		return request.get("price") != null && request.get("rate") != null && request.get("deposit") != null && request.get(
				"months") != null;
	}
}