
package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;

import javax.ejb.Local;

import psybergate.grad2018.javafnds.finance.entity.Investment;

@Local
public interface InvestmentForecastService extends ForecastService<Investment> {

	Collection<Investment> getInvestments();

	Investment getByName(String name);

	Investment getById(Long id);

	boolean deleteInvestmentByName(String name);
	
}
