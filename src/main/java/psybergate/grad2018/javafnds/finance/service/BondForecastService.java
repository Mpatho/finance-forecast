package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;

import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Investment;

public interface BondForecastService extends ForecastService<Bond> {

	Collection<Bond> getBonds();

	Investment getBondByName(String name);

	boolean deleteBondByName(String name);
	

}
