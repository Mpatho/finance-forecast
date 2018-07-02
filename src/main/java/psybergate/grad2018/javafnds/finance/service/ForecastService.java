package psybergate.grad2018.javafnds.finance.service;

public interface ForecastService<T> {

	String END_BALANCE = "endBalance";

	String TOTAL_CONTRIBUTION = "totalContribution";

	String TOTAL_WITHDRAWALS = "totalWithdrawals";

	String TOTAL_DEPOSITS = "totalDeposits";

	String TOTAL_INTEREST = "totalInterest";

	boolean save(T entity);

	boolean delete(T entity);

	T getById(Long id);

}