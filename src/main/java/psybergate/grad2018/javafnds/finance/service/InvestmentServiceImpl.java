package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.resource.Resource;

@Stateless
public class InvestmentServiceImpl implements InvestmentService {

	@Inject
	private Resource<Investment> investmentResource;

	@Override
	public Collection<Investment> getInvestments() {
		return null;
	}

	@Override
	public void save(Investment investment) {}

	@Override
	public void delete(Investment investment) {}

}
