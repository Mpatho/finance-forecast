package psybergate.grad2018.javafnds.finance.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import psybergate.grad2018.javafnds.finance.entity.Investment;
import psybergate.grad2018.javafnds.finance.resource.Resource;

@Stateless
public class InvestmentServiceImpl implements InvestmentService {

	@Inject
	private Resource<Investment> investmentResource;

}
