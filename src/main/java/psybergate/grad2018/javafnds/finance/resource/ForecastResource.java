package psybergate.grad2018.javafnds.finance.resource;

public interface ForecastResource<T> extends Resource<T> {

	T getByName(String name);

}
