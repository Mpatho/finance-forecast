package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;

import javax.ejb.Local;

import psybergate.grad2018.javafnds.finance.entity.Investment;

@Local
public interface InvestmentService {

	Collection<Investment> getInvestments();

	void save(Investment investment);

	void delete(Investment investment);
}
