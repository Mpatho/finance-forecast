package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

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

	private boolean validate(Investment investment) {
		String name = investment.getName();
		Money initialAmount = investment.getInitialAmount();
		Integer months = investment.getMonths();
		BigDecimal rate = investment.getRate();
		if (name == null || name.isEmpty()) return false;
		else if (initialAmount == null || initialAmount.compareTo(new Money(0)) <= 0) return false;
		else if (months == null || months <= 0) return false;
		else if (rate == null || rate.doubleValue() <= 0 || rate.doubleValue() > 100) return false;
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

}
