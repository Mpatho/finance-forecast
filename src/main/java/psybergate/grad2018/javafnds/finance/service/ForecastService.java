package psybergate.grad2018.javafnds.finance.service;

import java.util.List;

import javax.ejb.Local;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Investment;

@Local
public interface ForecastService {

	List<ForecastItem> getForecastItems(Investment investment);

	List<ForecastItem> getForecastItems(String name);

}