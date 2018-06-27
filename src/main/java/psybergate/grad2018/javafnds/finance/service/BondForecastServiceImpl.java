package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import psybergate.grad2018.javafnds.finance.bean.BondForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Event;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.resource.ForecastResource;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BondForecastServiceImpl extends AbstractForecastService<Bond> implements BondForecastService {

	private static final double BOND_COST_PERCENT = 1.0;

	private static final double LEGAL_COST_PERCENT = 1.2;

	private static final Money[] TRANSFER_DUTY_PRICES = { new Money(900_000.00), new Money(1_250_000.00), new Money(
			1_750_000.00), new Money(2_250_000.00), new Money(10_000_000.00), };

	private static final Double[] TRANSFER_DUTY_RATES = { 3.0, 6.0, 8.0, 11.0, 13.0, };

	@Inject
	private ForecastResource<Bond> bondResource;

	protected Money getRepayment(Bond bond) {
		Double one = 1.00;
		Double rate = bond.getRate() / 1200;
		Double fraction = Math.pow((one / (one + rate)), bond.getMonths());
		Double factor = rate / (one - (fraction));
		Money subtract = bond.getPrice().subtract(bond.getDeposit());
		return subtract.multiply(factor);
	}

	@Override
	public List<ForecastItem> getForecastItems(Bond bond, boolean includeCashRequired) {
		List<ForecastItem> forecastItems = new LinkedList<>();
		boolean updateRepayment = true;
		Money currentRepayment = getRepayment(bond);
		Money currentMoney = bond.getPrice().subtract(bond.getDeposit());
		Double currentRate = bond.getRate();
		if (includeCashRequired) {
			currentMoney = currentMoney.add(getCashRequired(bond));
		}
		for (int month = 1; month <= bond.getMonths(); month++) {
			BondForecastItem item = new BondForecastItem(currentMoney, currentRate);
			item.setRepayment(currentRepayment);
			forecastItems.add(item);
			for (Event event : bond.getEvents(month)) {
				updateRepayment = processEvent(bond, bond.getMonths() - month + 1, item, event, updateRepayment);
			}
			currentRate = item.getRate();
			currentMoney = item.getEndAmount();
			System.out.println(" Month: " + month + "current rate: " + currentRate + "current amount: " + currentMoney);
			if (updateRepayment) {
				currentRepayment = updateRepayment(currentMoney, currentRate, bond.getMonths() - month);
			}
		}
		return forecastItems;

	}

	private boolean processEvent(Bond bond, int month, BondForecastItem item, Event event, boolean updateRepayment) {
		switch (event.getType()) {
			case Event.DEPOSIT:
				Money deposit = new Money(event.getValue().doubleValue());
				item.setDeposit(deposit);
				System.out.println("deposit" + deposit.stringValue());
				break;
			case Event.WITHDRAW:
				Money withdrawal = new Money(event.getValue().doubleValue());
				item.setWithdrawal(withdrawal);
				System.out.println("withdrawal" + withdrawal.stringValue());
				break;
			case Event.RATE_CHANGE:
				Double rate = event.getValue().doubleValue();
				item.setRate(rate);
				item.setRepayment(updateRepayment(item.getInitialAmount(), item.getRate(), month));
				break;
			case Event.AMOUNT_CHANGE:
				Money repayment = new Money(event.getValue().doubleValue());
				item.setRepayment(repayment);
		}
		return updateRepayment;
	}

	private Money updateRepayment(Money currentMoney, Double rate, int month) {
		Money repayment = getRepayment(new Bond(currentMoney, new Money(0.0), rate, month));
		return repayment;
	}

	@Override
	public List<ForecastItem> getForecastItems(Bond bond) {
		return getForecastItems(bond, false);
	}

	@Override
	public List<ForecastItem> getForecastItemsByName(String name) {
		Bond bond = bondResource.getByName(name);
		return getForecastItems(bond);
	}

	@Override
	public boolean save(Bond bond) {
		if (validate(bond)) {
			bondResource.save(bond);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Bond bond) {
		bondResource.remove(bond);
		return true;
	}

	@Override
	public Collection<Bond> getBonds() {
		return bondResource.getAll();
	}

	@Override
	public Bond getBondByName(String name) {
		return bondResource.getByName(name);
	}

	@Override
	public boolean deleteBondByName(String name) {
		Bond bond = getBondByName(name);
		if (bond != null) {
			bondResource.remove(bond);
			return true;
		}
		return false;
	}

	private boolean validate(Bond bond) {
		if (bond.getMonths() == null || bond.getPrice() == null || bond.getRate() == null) return false;
		if (bond.getMonths() > 0 && bond.getPrice().compareTo(new Money(0.0)) != 0 && bond.getRate() > 0) return true;
		return false;
	}

	@Override
	public Money getBondCost(Bond bond) {
		return bond.getPrice().percentOf(BOND_COST_PERCENT);
	}

	@Override
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
	public Money getLegalCost(Bond bond) {
		return bond.getPrice().percentOf(LEGAL_COST_PERCENT);
	}

	@Override
	public Money getCashRequired(Bond bond) {
		return getBondCost(bond).add(getLegalCost(bond)).add(getTransferCost(bond));
	}

	@Override
	public Map<String, Money> getSummary(Bond bond) {
		List<ForecastItem> forecastItems = getForecastItems(bond);
		Money totalInterest = new Money(0.0);
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalContribution = new Money(0.0);
		Money endBalance = forecastItems.get(forecastItems.size() - 1).getEndAmount();
		for (ForecastItem forecastItem : forecastItems) {
			totalInterest = totalInterest.add(forecastItem.getInterest());
			totalDeposits = totalDeposits.add(forecastItem.getDeposit());
			totalWithdrawals = totalWithdrawals.add(forecastItem.getWithdrawal());
			totalContribution = totalContribution.add(((BondForecastItem) forecastItem).getRepayment());
		}
		return getSummary(totalInterest, totalDeposits, totalWithdrawals, totalContribution, endBalance);
	}

}
