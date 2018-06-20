package psybergate.grad2018.javafnds.finance.service;

import java.util.ArrayList;
import java.util.Collection;
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
		if (!validate(investment)) return false;
		moneyResource.save(investment.getInitialAmount());
		investmentResource.save(investment);
		return true;
	}

	public List<ForecastItem> getMonthlyForecastItems(Investment investment) {
		if (investment == null) return null;
		
		List<ForecastItem> forecastItems = new ArrayList<>();
		if (validate(investment)) {
			
			Money currentAmount = investment.getInitialAmount();
			Money monthlyAmount = currentAmount;
			currentAmount = new Money(0.0);
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

	public List<ForecastItem> getForecastItemsByName(String name) {
		Investment investment = investmentResource.getByName(name);
		return getForecastItems(investment);
	}

	public List<ForecastItem> getFixedForecastItems(Investment investment) {
		if (investment == null) return null;
		List<ForecastItem> forecastItems = new ArrayList<>();
		if (validate(investment)) {
			Money currentAmount = investment.getInitialAmount();
			for (int i = 0; i < investment.getMonths(); i++) {
				ForecastItem item = new FixedForecastItem(currentAmount, investment.getRate());
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
		Double rate = investment.getRate();
		if (name == null || name.isEmpty()) return false;
		else if (initialAmount == null || initialAmount.compareTo(new Money(0.0)) <= 0) return false;
		else if (months == null || months <= 0) return false;
		else if (rate == null || rate.doubleValue() <= 0 || rate.doubleValue() > 100) return false;
		return true;
	}

	@Override
	public Investment getInvestmentById(Long id) {
		return investmentResource.getById(id);
	}
}

