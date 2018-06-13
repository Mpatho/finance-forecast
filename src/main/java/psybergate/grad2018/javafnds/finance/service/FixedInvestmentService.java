package psybergate.grad2018.javafnds.finance.service;

import java.util.List;

import javax.ejb.Local;

import psybergate.grad2018.javafnds.finance.entity.Investment;

@Local
public interface FixedInvestmentService {

	List<ForecastItem> calculateForecast(Investment fixedInvestment);

}
