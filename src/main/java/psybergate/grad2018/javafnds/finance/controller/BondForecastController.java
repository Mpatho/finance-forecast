
package psybergate.grad2018.javafnds.finance.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Inject;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.service.BondForecastService;

@ManagedBean("Bond")
public class BondForecastController extends BaseForecastController {

	public static final String WEB_INF_VIEWS_BOND_FORECAST_JSP = "/WEB-INF/views/bond/forecast.jsp";
	@EJB
	private BondForecastService bondForecastService;

	@Inject
	private ForecastsController forecastsController;

	@Override
	public String forecast(Map<String, String[]> request, Map<String, Object> response) {
		if (request.isEmpty()) {
			response.put("summary", bondForecastService.getSummary(null, false));
			return WEB_INF_VIEWS_BOND_FORECAST_JSP;
		}
		Bond bond = getBond(request);
		updateBond(bond, request);
		boolean includeCashRequired = request.get("include_cash_required") != null;
		List<ForecastItem> forecastItems = bondForecastService.getForecastItems(bond, includeCashRequired);
		loadBondResponce(response, bond, forecastItems, includeCashRequired);
		return WEB_INF_VIEWS_BOND_FORECAST_JSP;
	}

	@Override
	public String save(Map<String, String[]> request, Map<String, Object> response) {
		Bond bond = getBond(request);
		bond.setName(request.get("name")[0]);
		updateBond(bond, request);
		bondForecastService.save(bond);
		return forecastsController.viewForecasts(request, response);
	}

	@Override
	public String delete(Map<String, String[]> request, Map<String, Object> response) {
		Bond bond = getBond(request);
		bondForecastService.delete(bond);
		return forecastsController.viewForecasts(request, response);
	}

	private void updateBond(Bond bond, Map<String, String[]> request) {
		if (validInput(request)) {
			Money price = new Money(Double.valueOf(request.get("price")[0]));
			Money deposit = new Money(request.get("deposit")[0]);
			Double rate = Double.valueOf(request.get("rate")[0]);
			Integer months = Integer.valueOf(request.get("months")[0]);
			bond.setPrice(price);
			bond.setDeposit(deposit);
			bond.setRate(rate);
			bond.setMonths(months);
		}
		getEvents(request, bond);
	}

	private Bond getBond(Map<String, String[]> request) {
		if (request.get("id") != null && request.get("id")[0].trim().length() != 0) {
			Long id = Long.valueOf(request.get("id")[0]);
			return bondForecastService.getById(id);
		}
		return new Bond();
	}

	private void loadBondResponce(Map<String, Object> response, Bond bond, List<ForecastItem> forecastItems,
			boolean includeCashRequired) {
		response.put("checked", includeCashRequired ? "checked" : "");
		response.put("summary", bondForecastService.getSummary(bond, includeCashRequired));
		response.put("id", bond.getId());
		response.put("price", bond.getPrice().doubleValue());
		response.put("deposit", bond.getDeposit().doubleValue());
		response.put("rate", bond.getRate().doubleValue());
		response.put("months", bond.getMonths());
		response.put("bondCost", bondForecastService.getBondCost(bond).stringValue());
		response.put("transferCost", bondForecastService.getTransferCost(bond).stringValue());
		response.put("legalCost", bondForecastService.getLegalCost(bond).stringValue());
		response.put("cashRequired", bondForecastService.getCashRequired(bond).stringValue());
		response.put("forecastItems", forecastItems);
	}

	private boolean validInput(Map<String, String[]> request) {
		return request.get("price") != null && request.get("rate") != null && request.get("deposit") != null && request.get(
				"months") != null;
	}

}
