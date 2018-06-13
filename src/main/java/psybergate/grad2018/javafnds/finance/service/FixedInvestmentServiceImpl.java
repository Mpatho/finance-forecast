package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.resource.InvestmentResource;
import psybergate.grad2018.javafnds.finance.resource.Resource;

@Stateless
public class FixedInvestmentServiceImpl implements FixedInvestmentService {

	Resource<Investment> fxdInvRes = new InvestmentResource();

	@Override
	public List<ForecastItem> calculateForecast(Investment fixedInvestment) {
		Forecast forecast = new Forecast();
		if(isValidateFixedInvestment(fixedInvestment)) {
			return forecast.getForecastItems(fixedInvestment);
		}
		else {
			throw new RuntimeException("Something went wrong");
		}
		
	}


	private boolean isValidateFixedInvestment(Investment investment) {
		if(investment == null) {
			return false;
		} 
		else if(investment != null) {
			String name = investment.getName();
			BigDecimal initailAmount = investment.getInitailAmount();
			Integer months = investment.getMonths();
			Double rate = investment.getRate();
			if(name == null || name.trim().length() == 0) {
				return false;
			}
			else if(initailAmount==null || initailAmount.compareTo(new BigDecimal(0))<=0)
				return false;
			else if(months==null || months <= 0)
				return false;
			else if(rate==null || rate <= 0 || rate > 100)
				return false;
		}
		return true;
	}

}
