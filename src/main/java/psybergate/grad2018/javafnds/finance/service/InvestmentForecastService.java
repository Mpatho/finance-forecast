package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;

import javax.ejb.Local;

import psybergate.grad2018.javafnds.finance.entity.Investment;

@Local
public interface InvestmentForecastService {

	Collection<Investment> getInvestments();

	boolean save(Investment investment);

	boolean delete(Investment investment);
	
	Investment getInvestmentByName(String name);

	boolean deleteInvestmentByName(String name);
}
