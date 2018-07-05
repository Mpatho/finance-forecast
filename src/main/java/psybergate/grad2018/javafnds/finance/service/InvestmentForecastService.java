
package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.interceptor.Interceptors;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.interceptor.LoggerInterceptor;

@Local
public interface InvestmentForecastService extends ForecastService<Investment> {

	Collection<Investment> getInvestments();

	List<ForecastItem> getForecastItems(Investment entity);

	Map<String, Money> getSummary(Investment entity);

}
