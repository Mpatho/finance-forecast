package psybergate.grad2018.javafnds.finance.service;

import java.util.List;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;

public interface ForecastService<T> {

	List<ForecastItem> getForecastItemsByInv(T investment);

	List<ForecastItem> getForecastItems(String name);
	
	boolean save(T investment);

	boolean delete(T investment);

}