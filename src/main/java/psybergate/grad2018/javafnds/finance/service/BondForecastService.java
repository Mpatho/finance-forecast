package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;
import java.util.List;

import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Money;

public interface BondForecastService extends ForecastService<Bond> {

	Collection<Bond> getBonds();

	Money getBondCost(Bond bond);

	Money getTransferCost(Bond bond);

	Money getLegalCost(Bond bond);

	Money getCashRequired(Bond bond);

	List<ForecastItem> getForecastItems(Bond bond, boolean includeCashRequired);
}
