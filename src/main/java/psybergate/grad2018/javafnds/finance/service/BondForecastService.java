package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import psybergate.grad2018.javafnds.finance.bean.BondForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Money;

@Stateless
public class BondForecastService implements ForecastService<Bond> {

	protected Money getRepayment(Bond bond) {
		BigDecimal one = BigDecimal.ONE;
		BigDecimal rate = bond.getRate().divide(new BigDecimal(1200));
		BigDecimal fraction = one.divide(one.add(rate), 10, RoundingMode.UP).pow(bond.getMonths());
		BigDecimal factor = rate.divide(one.subtract(fraction), 10, RoundingMode.UP);
		Money subtract = bond.getPrice().subtract(bond.getDeposit());
		return subtract.multiply(factor);
	}

	@Override
	public List<ForecastItem> getForecastItems(Bond bond) {
		List<ForecastItem> forectastItems = new LinkedList<>();
		Money repaymentMoney = getRepayment(bond);
		Money currentMoney = bond.getPrice().subtract(bond.getDeposit());
		ForecastItem item;
		for (int i = 0; i < bond.getMonths(); i++) {
			item = new BondForecastItem(currentMoney, bond.getRate(), repaymentMoney);
			forectastItems.add(item);
			currentMoney = item.getEndAmount();
		}
		return forectastItems;
	}

	@Override
	public List<ForecastItem> getForecastItems(String name) {
		return null;
	}

}
