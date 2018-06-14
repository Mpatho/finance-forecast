package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.List;

import psybergate.grad2018.javafnds.finance.entity.Money;

public interface ForecastService {

	List<ForecastItem> getForecastItems();

	Money getInitialAmount();

	void setInitialAmount(Money initialAmount);

	BigDecimal getRate();

	void setRate(BigDecimal rate);

	Integer getMonths();

	void setMonths(Integer months);

}