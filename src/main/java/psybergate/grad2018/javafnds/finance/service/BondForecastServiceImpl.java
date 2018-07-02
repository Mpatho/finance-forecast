package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import psybergate.grad2018.javafnds.finance.bean.BondForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Event;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.resource.Resource;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BondForecastServiceImpl extends AbstractForecastService<Bond> implements BondForecastService {

	private static final double BOND_COST_PERCENT = 1.0;

	private static final double LEGAL_COST_PERCENT = 1.2;

	private static final Money[] TRANSFER_DUTY_PRICES = {
			new Money(900_000.00),
			new Money(1_250_000.00),
			new Money(1_750_000.00),
			new Money(2_250_000.00),
			new Money(10_000_000.00),
	};

	private static final Double[] TRANSFER_DUTY_RATES = {
			3.0,
			6.0,
			8.0,
			11.0,
			13.0,
	};

	@Inject
	private Resource<Bond> bondResource;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ForecastItem> getForecastItems(Bond bond, boolean includeCashRequired) {
		if (bond == null) return new LinkedList<>();
		Money balance = bond.getPrice().subtract(bond.getDeposit());
		if (includeCashRequired) {
			balance = balance.add(getCashRequired(bond));
		}
		return getBondForecastItems(bond, balance);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean save(Bond bond) {
		if (validate(bond)) {
			bondResource.save(bond);
			return true;
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean delete(Bond bond) {
		if (bondResource.contains(bond)) {
			bondResource.remove(bond);
			return true;
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Collection<Bond> getBonds() {
		return bondResource.getAll();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Money getBondCost(Bond bond) {
		return bond.getPrice().percentOf(BOND_COST_PERCENT);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Money getTransferCost(Bond bond) {
		Money price = bond.getPrice();
		Money transerCost = new Money(0.0);
		if (price.compareTo(TRANSFER_DUTY_PRICES[0]) <= 0) return transerCost;
		for (int i = 1; i < TRANSFER_DUTY_PRICES.length; i++) {
			if (price.compareTo(TRANSFER_DUTY_PRICES[i]) <= 0) {
				Money cost = price.subtract(TRANSFER_DUTY_PRICES[i - 1]).percentOf(TRANSFER_DUTY_RATES[i - 1]);
				return transerCost.add(cost);
			}
			else {
				Money cost = TRANSFER_DUTY_PRICES[i].subtract(TRANSFER_DUTY_PRICES[i - 1]).percentOf(TRANSFER_DUTY_RATES[i
						- 1]);
				transerCost = transerCost.add(cost);
			}
		}
		int last = TRANSFER_DUTY_PRICES.length - 1;
		Money cost = price.subtract(TRANSFER_DUTY_PRICES[last]).percentOf(TRANSFER_DUTY_RATES[last]);
		return transerCost.add(cost);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Money getLegalCost(Bond bond) {
		return bond.getPrice().percentOf(LEGAL_COST_PERCENT);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Money getCashRequired(Bond bond) {
		return getBondCost(bond).add(getLegalCost(bond)).add(getTransferCost(bond));
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<String, Money> getSummary(Bond bond, boolean includeCashRequired) {
		List<ForecastItem> forecastItems = getForecastItems(bond, includeCashRequired);
		Money totalInterest = new Money(0.0);
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalContribution = new Money(0.0);
		Money endBalance = new Money(0.0);
		for (ForecastItem forecastItem : forecastItems) {
			totalInterest = totalInterest.add(forecastItem.getInterest());
			totalDeposits = totalDeposits.add(forecastItem.getDeposit());
			totalWithdrawals = totalWithdrawals.add(forecastItem.getWithdrawal());
			totalContribution = totalContribution.add(((BondForecastItem) forecastItem).getRepayment());
		}
		if (!forecastItems.isEmpty()) {
			endBalance = forecastItems.get(forecastItems.size() - 1).getEndAmount();
		}
		return getSummary(totalInterest, totalDeposits, totalWithdrawals, totalContribution, endBalance);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Bond getById(Long id) {
		return bondResource.getById(id);
	}

	private boolean validate(Bond bond) {
		if (bond.getMonths() == null || bond.getPrice() == null || bond.getRate() == null) return false;
		if (bond.getMonths() > 0 && bond.getPrice().compareTo(new Money(0.0)) != 0 && bond.getRate() > 0) return true;
		return false;
	}

	private List<ForecastItem> getBondForecastItems(Bond bond, Money currentBalance) {
		List<ForecastItem> forecastItems = new LinkedList<>();
		Money fixedRepayment = null;
		Double currentRate = bond.getRate();
		int months = bond.getMonths();
		int month = 1;
		while (months > 0) {
			BondForecastItem item = new BondForecastItem(currentBalance, currentRate, months);
			item.setFixedRepayment(fixedRepayment);
			forecastItems.add(item);
			for (Event event : bond.getEvents(month)) {
				item.addEvent(event);
			}
			currentBalance = item.getEndAmount();
			currentRate = item.getRate();
			fixedRepayment = item.getFixedRepayment();
			months = item.getRemainingMonths() - 1;
			month++;
		}
		return forecastItems;
	}

}
