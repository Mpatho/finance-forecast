package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import psybergate.grad2018.javafnds.finance.bean.FixedForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.bean.MonthlyForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Event;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.resource.ForecastResource;
import psybergate.grad2018.javafnds.finance.resource.Resource;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InvestmentForecastServiceImpl implements InvestmentForecastService {

	@Inject
	private ForecastResource<Investment> investmentResource;

	@Inject
	private Resource<Money> moneyResource;

	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<Investment> getInvestments() {
		return investmentResource.getAll();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean save(Investment investment) {
		if (!validate(investment))
			return false;
		moneyResource.save(investment.getInitialAmount());
		investmentResource.save(investment);
		return true;
	}

	public List<ForecastItem> getMonthlyForecastItems(Investment investment) {
		if (investment == null)
			return null;

		List<ForecastItem> forecastItems = new ArrayList<>();
		if (validate(investment)) {

			Money currentAmount = investment.getInitialAmount();
			Money monthlyAmount = currentAmount;
			currentAmount = new Money(0);
			for (int i = 0; i < investment.getMonths(); i++) {
				ForecastItem item = new MonthlyForecastItem(currentAmount, investment.getRate(), monthlyAmount);
				forecastItems.add(item);
				currentAmount = item.getEndAmount();
			}
		}
		else {
			throw new RuntimeException("Invalid investment");
		}
		return forecastItems;
	}

	public List<ForecastItem> getForecastItems(String name) {
		Investment investment = investmentResource.getByName(name);
		return getForecastItemsByInv(investment);
	}

	public List<ForecastItem> getFixedForecastItems(Investment investment) {
		if (investment == null) {
			return null;
		}
		List<ForecastItem> forecastItems = new ArrayList<>();
		if (validate(investment)) {
			Money currentAmount = investment.getInitialAmount();
			BigDecimal currentRate = investment.getRate();

			// Start
			Collections.sort(investment.getEvents());
			Iterator<Event> iterator = investment.iterator();
			Event event = null;
			if (iterator.hasNext()) {
				event = iterator.next();
			}
			int month;
			if (event == null) {
				month = 0;
			}
			else {
				month = event.getMonth();
			}
			for (int i = 0; i < investment.getMonths(); i++) {
				ForecastItem item = new FixedForecastItem(currentAmount, currentRate);
				forecastItems.add(item);

				while (i == (month - 1)) {
					System.out.println(event.getMonth());
					switch (event.getType()) {
					case Event.DEPOSIT:
						Money deposit = new Money(event.getValue());
						forecastItems.get(i).setDeposit(deposit);
						break;
					case Event.WITHDRAW:
						Money withdrawal = new Money(event.getValue());
						forecastItems.get(i).setWithdrawal(withdrawal);
						break;
					case Event.RATE_CHANGE:
						BigDecimal rate = event.getValue();
						forecastItems.get(i).setRate(rate);
						currentRate = rate;
						break;

					}
					if (iterator.hasNext()) {
						event = iterator.next();
						month = event.getMonth();
					}
					else {
						--month;
					}
				}

				currentAmount = item.getEndAmount();
			}
		}
		else {
			throw new RuntimeException("Invalid investment");
		}
		return forecastItems;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean delete(Investment investment) {
		if (!investmentResource.contains(investment))
			return false;
		investmentResource.remove(investment);
		return true;
	}

	@Override
	public Investment getInvestmentByName(String name) {
		return investmentResource.getByName(name);
	}

	@Override
	public boolean deleteInvestmentByName(String name) {
		for (Investment investment : investmentResource) {
			if (investment.getName().equals(name)) {
				investmentResource.remove(investment);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<ForecastItem> getForecastItemsByInv(Investment investment) {
		if (investment.getType().equals(Investment.FIXED)) {
			return getFixedForecastItems(investment);
		}
		else if (investment.getType().equals(Investment.MONTHLY)) {
			return getMonthlyForecastItems(investment);
		}
		return new LinkedList<>();
	}

	private boolean validate(Investment investment) {
		String name = investment.getName();
		Money initialAmount = investment.getInitialAmount();
		Integer months = investment.getMonths();
		BigDecimal rate = investment.getRate();
		if (name == null || name.isEmpty())
			return false;
		else if (initialAmount == null || initialAmount.compareTo(new Money(0)) <= 0)
			return false;
		else if (months == null || months <= 0)
			return false;
		else if (rate == null || rate.doubleValue() <= 0 || rate.doubleValue() > 100)
			return false;
		return true;
	}
}
