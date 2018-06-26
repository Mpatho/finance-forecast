package psybergate.grad2018.javafnds.finance.service;

import java.util.HashMap;
import java.util.Map;

import psybergate.grad2018.javafnds.finance.entity.Money;

public abstract class AbstractForecastService<T> implements ForecastService<T> {

	protected Map<String, Money> getSummary(Money totalInterest, Money totalDeposits, Money totalWithdrawals,
			Money totalContribution, Money endBalance) {
		Map<String, Money> summary = new HashMap<>();
		summary.put(TOTAL_INTEREST, totalInterest);
		summary.put(TOTAL_DEPOSITS, totalDeposits);
		summary.put(TOTAL_WITHDRAWALS, totalWithdrawals);
		summary.put(TOTAL_CONTRIBUTION, totalContribution);
		summary.put(END_BALANCE, endBalance);
		return summary;
	}

}