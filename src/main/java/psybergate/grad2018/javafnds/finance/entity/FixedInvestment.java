package psybergate.grad2018.javafnds.finance.entity;

import java.math.BigDecimal;

public class FixedInvestment extends Investment {

	public FixedInvestment() {}

	public FixedInvestment(String name, BigDecimal initailAmount, Integer months, Double rate) {
		super(name, initailAmount, months, rate);
	}

}
