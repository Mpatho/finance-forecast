package psybergate.grad2018.javafnds.finance.service;

import java.util.ArrayList;
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
public class InvestmentForecastServiceImpl extends AbstractForecastService<Investment> implements
		InvestmentForecastService {

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
		if (!validate(investment)) return false;
		moneyResource.save(investment.getAmount());
		investmentResource.save(investment);
		return true;
	}

	public List<ForecastItem> getMonthlyForecastItems(Investment investment) {
		if (investment == null) return null;

		List<ForecastItem> forecastItems = new ArrayList<>();
		if (validate(investment)) {

			Money currentAmount = investment.getAmount();
			Money monthlyAmount = currentAmount;
			currentAmount = new Money(0.0);
			Double currentRate = investment.getRate();
			for (int month = 1; month <= investment.getMonths(); month++) {
				Money deposit = new Money(0.0);
				Money withdrawal = new Money(0.0);
				for (Event event : investment.getEvents(month)) {
					switch (event.getType()) {
						case Event.DEPOSIT:
							deposit = new Money(event.getValue().doubleValue());
							break;
						case Event.WITHDRAW:
							withdrawal = new Money(event.getValue().doubleValue());
							break;
						case Event.RATE_CHANGE:
							currentRate = event.getValue().doubleValue();
							break;
						case Event.AMOUNT_CHANGE:
							monthlyAmount = new Money(event.getValue().doubleValue());
							break;
					}

				}
				ForecastItem item = new MonthlyForecastItem(currentAmount, currentRate, monthlyAmount, deposit, withdrawal);
				forecastItems.add(item);
				currentAmount = item.getEndAmount();
			}
		}
		else {
			throw new RuntimeException("Invalid investment");
		}
		return forecastItems;
	}

	public List<ForecastItem> getForecastItemsByName(String name) {
		Investment investment = investmentResource.getByName(name);
		return getForecastItems(investment);
	}

	public List<ForecastItem> getFixedForecastItems(Investment investment) {
		if (investment == null) return null;
		List<ForecastItem> forecastItems = new ArrayList<>();
		if (validate(investment)) {
			Money currentAmount = investment.getAmount();
			Double currentRate = investment.getRate();
			for (int month = 1; month <= investment.getMonths(); month++) {
				Money deposit = new Money(0.0);
				Money withdrawal = new Money(0.0);
				for (Event event : investment.getEvents(month)) {

					switch (event.getType()) {
						case Event.DEPOSIT:
							deposit = new Money(event.getValue().doubleValue());
							break;
						case Event.WITHDRAW:
							withdrawal = new Money(event.getValue().doubleValue());
							break;
						case Event.RATE_CHANGE:
							currentRate = event.getValue().doubleValue();
							break;

					}
				}
				ForecastItem item = new FixedForecastItem(currentAmount, currentRate, deposit, withdrawal);
				forecastItems.add(item);
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
		if (!investmentResource.contains(investment)) return false;
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
	public List<ForecastItem> getForecastItems(Investment investment) {
		if (investment.getType().equals(Investment.FIXED)) { return getFixedForecastItems(investment); }
		if (investment.getType().equals(Investment.MONTHLY)) { return getMonthlyForecastItems(investment); }
		return new LinkedList<>();
	}

	private boolean validate(Investment investment) {
		String name = investment.getName();
		Money initialAmount = investment.getAmount();
		Integer months = investment.getMonths();
		Double rate = investment.getRate();
		if (name == null || name.isEmpty()) return false;
		if (initialAmount == null || initialAmount.compareTo(new Money(0.0)) <= 0) return false;
		if (months == null || months <= 0) return false;
		if (rate == null || rate.doubleValue() <= 0 || rate.doubleValue() > 100) return false;
		if (name == null || name.isEmpty()) return false;
		if (initialAmount == null || initialAmount.compareTo(new Money(0.0)) <= 0) return false;
		if (months == null || months <= 0) return false;
		else if (rate == null || rate.doubleValue() <= 0 || rate.doubleValue() > 100) return false;
		return true;
	}

	@Override
	public Investment getInvestmentById(Long id) {
		return investmentResource.getById(id);
	}

	@Override
	public Map<String, Money> getSummary(Investment investment) {
		List<ForecastItem> forecastItems = getForecastItems(investment);
		Money totalInterest = new Money(0.0);
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalContribution = new Money(0.0);
		Money endBalance = forecastItems.get(forecastItems.size() - 1).getEndAmount();
		for (ForecastItem forecastItem : forecastItems) {
			totalInterest = totalInterest.add(forecastItem.getInterest());
			totalDeposits = totalDeposits.add(forecastItem.getDeposit());
			totalWithdrawals = totalWithdrawals.add(forecastItem.getWithdrawal());
			if (investment.getType().equals(Investment.MONTHLY)) {
				totalContribution = totalContribution.add(((MonthlyForecastItem) forecastItem).getMonthlyAmount());
			}
		}
		if (investment.getType().equals(Investment.FIXED)) {
			totalContribution = investment.getAmount();
		}
		return getSummary(totalInterest, totalDeposits, totalWithdrawals, totalContribution, endBalance);
	}
}
