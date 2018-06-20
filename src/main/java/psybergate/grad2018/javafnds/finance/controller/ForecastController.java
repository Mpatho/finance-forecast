package psybergate.grad2018.javafnds.finance.controller;

import java.util.Collection;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import psybergate.grad2018.javafnds.finance.entity.Bond;
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

}
