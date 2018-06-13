package psybergate.grad2018.javafnds.finance.service;

import javax.ejb.Local;

import psybergate.grad2018.javafnds.finance.entity.Investment;

@Local
public interface FixedInvestmentService {

	void saveForecast(Investment fixedInvestment);

	void updateForecast(Investment fixedInvestment);

	void deleteForecast(Investment fixedInvestment);

	void viewAllForecast();

	void viewForecast(Investment fixedInvestment);

}
