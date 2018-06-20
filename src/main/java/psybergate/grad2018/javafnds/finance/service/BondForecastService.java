package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;

import psybergate.grad2018.javafnds.finance.entity.Bond;

public interface BondForecastService extends ForecastService<Bond> {

	Collection<Bond> getBonds();

	Bond getBondByName(String name);

	boolean deleteBondByName(String name);

}
