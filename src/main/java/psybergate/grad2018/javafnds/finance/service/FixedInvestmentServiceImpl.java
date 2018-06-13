package psybergate.grad2018.javafnds.finance.service;

import javax.ejb.Stateless;

import psybergate.grad2018.javafnds.finance.entity.FixedInvestment;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.resource.Resource;

@Stateless
public class FixedInvestmentServiceImpl implements FixedInvestmentService {

	Resource<FixedInvestment> fxdInvRes;

	@Override
	public void saveForecast(Investment fixedInvestment) {
		if(isValidateFixedInvestment(fixedInvestment)) {
			System.out.println("Yay");
		}
		else {
			throw new RuntimeException("Something went wrong");
		}
	}

	@Override
	public void updateForecast(Investment fixedInvestment) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteForecast(Investment fixedInvestment) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewAllForecast() {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewForecast(Investment fixedInvestment) {
		// TODO Auto-generated method stub

	}

	private boolean isValidateFixedInvestment(Investment investment) {
		if(investment == null) {
			return false;
		} 
		else if(investment != null) {
			String name = investment.getName();
			Double initailAmount = investment.getInitailAmount();
			Integer months = investment.getMonths();
			Double rate = investment.getRate();
			if(name == null || name.trim().length() == 0) {
				return false;
			}
			else if(initailAmount==null || initailAmount <= 0)
				return false;
			else if(months==null || months <= 0)
				return false;
			else if(rate==null || rate <= 0 || rate > 100)
				return false;
		}
		return true;
	}

}
