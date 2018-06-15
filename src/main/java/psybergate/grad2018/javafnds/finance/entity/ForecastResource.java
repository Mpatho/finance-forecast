package psybergate.grad2018.javafnds.finance.entity;

import psybergate.grad2018.javafnds.finance.resource.Resource;

public interface ForecastResource<T> extends Resource<T> {

	T getByName(String name);

}
