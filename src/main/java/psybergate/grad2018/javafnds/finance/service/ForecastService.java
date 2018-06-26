package psybergate.grad2018.javafnds.finance.service;

import java.util.List;
import java.util.Map;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Money;

public interface ForecastService<T> {

	String END_BALANCE = "endBalance";

	String TOTAL_CONTRIBUTION = "totalContribution";

	String TOTAL_WITHDRAWALS = "totalWithdrawals";

	String TOTAL_DEPOSITS = "totalDeposits";

	String TOTAL_INTEREST = "totalInterest";

	List<ForecastItem> getForecastItems(T entity);

	List<ForecastItem> getForecastItemsByName(String name);

	boolean save(T entity);

	boolean delete(T entity);

	Map<String, Money> getSummary(T entity);

}