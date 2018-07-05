package psybergate.grad2018.javafnds.finance.service.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.transaction.Transactional;

import psybergate.grad2018.javafnds.finance.bean.FixedInvestmentForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.bean.MonthlyInvestmentForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Event;
import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.interceptor.LoggerInterceptor;
import psybergate.grad2018.javafnds.finance.resource.Resource;
import psybergate.grad2018.javafnds.finance.service.AbstractForecastService;
import psybergate.grad2018.javafnds.finance.service.InvestmentForecastService;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors({LoggerInterceptor.class})
public class InvestmentForecastServiceImpl extends AbstractForecastService<Investment> implements
		InvestmentForecastService {

	@Inject
	private Resource<Investment> investmentResource;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Collection<Investment> getInvestments() {
		return investmentResource.getAll();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean save(Investment investment) {
		String name = investment.getName();
		if (name == null || name.isEmpty()) return false;
		if (!validate(investment)) return false;
		investmentResource.save(investment);
		return true;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean delete(Investment investment) {
		if (!investmentResource.contains(investment)) return false;
		investmentResource.remove(investment);
		return true;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ForecastItem> getForecastItems(Investment investment) {
		List<ForecastItem> forecastItems = new ArrayList<>();
		if (investment == null) return forecastItems;
		if (!validate(investment)) throw new RuntimeException("Invalid investment");
		if (investment.getType().equals(Investment.FIXED)) {
			loadFixedForecastItems(investment, forecastItems);
		}
		else if (investment.getType().equals(Investment.MONTHLY)) {
			loadMonthlyForecastItems(investment, forecastItems);
		}
		return forecastItems;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Investment getById(Long id) {
		return investmentResource.getById(id);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<String, Money> getSummary(Investment investment) {
		List<ForecastItem> forecastItems = getForecastItems(investment);
		Money totalInterest = new Money(0.0);
		Money totalDeposits = new Money(0.0);
		Money totalWithdrawals = new Money(0.0);
		Money totalContribution = new Money(0.0);
		Money endBalance = new Money(0.0);
		for (ForecastItem forecastItem : forecastItems) {
			totalInterest = totalInterest.add(forecastItem.getInterest());
			totalDeposits = totalDeposits.add(forecastItem.getDeposit());
			totalWithdrawals = totalWithdrawals.add(forecastItem.getWithdrawal());
			if (investment.getType().equals(Investment.MONTHLY)) {
				totalContribution = totalContribution.add(((MonthlyInvestmentForecastItem) forecastItem).getMonthlyAmount());
			}
		}
		if (!forecastItems.isEmpty()) {
			endBalance = forecastItems.get(forecastItems.size() - 1).getEndAmount();
		}
		if (investment != null && investment.getType().equals(Investment.FIXED)) {
			totalContribution = investment.getAmount();
		}
		return getSummary(totalInterest, totalDeposits, totalWithdrawals, totalContribution, endBalance);
	}

	private void loadMonthlyForecastItems(Investment investment, List<ForecastItem> forecastItems) {
		Money currentAmount = new Money(0.0);
		Double currentRate = investment.getRate();
		Money fixedRepayment = investment.getAmount();
		for (int month = 1; month <= investment.getMonths(); month++) {
			ForecastItem item = new MonthlyInvestmentForecastItem(currentAmount, currentRate, investment.getMonths(), month);
			forecastItems.add(item);
			item.setFixedRepayment(fixedRepayment);
			for (Event event : investment.getEvents(month)) {
				item.addEvent(event);
			}
			currentRate = item.getRate();
			currentAmount = item.getEndAmount();
			fixedRepayment = item.getFixedRepayment();
		}
	}

	private void loadFixedForecastItems(Investment investment, List<ForecastItem> forecastItems) {
		Money currentAmount = investment.getAmount();
		Double currentRate = investment.getRate();
		Money fixedRepayment = currentAmount;
		for (int month = 1; month <= investment.getMonths(); month++) {
			ForecastItem item = new FixedInvestmentForecastItem(currentAmount, currentRate, investment.getMonths());
			forecastItems.add(item);
			item.setFixedRepayment(fixedRepayment);
			for (Event event : investment.getEvents(month)) {
				item.addEvent(event);
			}
			currentRate = item.getRate();
			currentAmount = item.getEndAmount();
			fixedRepayment = item.getFixedRepayment();
		}
	}

	private boolean validate(Investment investment) {
		Money initialAmount = investment.getAmount();
		Integer months = investment.getMonths();
		Double rate = investment.getRate();
		if (rate == null || rate.doubleValue() <= 0 || rate.doubleValue() > 100) return false;
		if (initialAmount == null || initialAmount.compareTo(new Money(0.0)) <= 0) return false;
		if (months == null || months <= 0) return false;
		return true;
	}

}
