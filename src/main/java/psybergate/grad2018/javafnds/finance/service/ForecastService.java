package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.List;

public interface ForecastService {

	List<ForecastItem> getForecastItems();

	BigDecimal getInitialAmount();

	void setInitialAmount(BigDecimal initialAmount);

	BigDecimal getRate();

	void setRate(BigDecimal rate);

	Integer getMonths();

	void setMonths(Integer months);

}