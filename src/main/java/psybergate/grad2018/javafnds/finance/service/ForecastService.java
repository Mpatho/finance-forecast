package psybergate.grad2018.javafnds.finance.service;

import java.util.List;
import java.util.Map;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;

public interface ForecastService<T> {

	List<ForecastItem> getForecastItems(T entity);

	List<ForecastItem> getForecastItemsByName(String name);

	boolean save(T entity);

	boolean delete(T entity);

	Map<String, String> getSummary(T entity);

}