
package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;

import javax.ejb.Local;

import psybergate.grad2018.javafnds.finance.entity.Investment;

@Local
public interface InvestmentForecastService extends ForecastService<Investment> {

	Collection<Investment> getInvestments();

	Investment getInvestmentByName(String name);

	Investment getInvestmentById(Long id);

	boolean deleteInvestmentByName(String name);
	
}
