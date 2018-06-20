package psybergate.grad2018.javafnds.finance.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import psybergate.grad2018.javafnds.finance.bean.BondForecastItem;
import psybergate.grad2018.javafnds.finance.bean.ForecastItem;
import psybergate.grad2018.javafnds.finance.entity.Bond;
import psybergate.grad2018.javafnds.finance.entity.Money;
import psybergate.grad2018.javafnds.finance.resource.ForecastResource;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BondForecastServiceImpl implements BondForecastService {

	@Inject
	private ForecastResource<Bond> bondResource;

	protected Money getRepayment(Bond bond) {
		Double one = 1.00;
		Double rate = bond.getRate() / 1200;
		Double fraction = Math.pow((one / (one + rate )), bond.getMonths());
		Double factor = rate / (one - (fraction));
		Money subtract = bond.getPrice().subtract(bond.getDeposit());
		return subtract.multiply(factor);
	}

	@Override
	public List<ForecastItem> getForecastItems(Bond bond) {
		List<ForecastItem> forectastItems = new LinkedList<>();
		Money repaymentMoney = getRepayment(bond);
		Money currentMoney = bond.getPrice().subtract(bond.getDeposit());
		Double rate = bond.getRate();
		ForecastItem item;
		for (int i = 0; i < bond.getMonths(); i++) {
			item = new BondForecastItem(currentMoney, rate, repaymentMoney);
			forectastItems.add(item);
			currentMoney = item.getEndAmount();
		}
		return forectastItems;
	}

	@Override
	public List<ForecastItem> getForecastItemsByName(String name) {
		Bond bond = bondResource.getByName(name);
		return getForecastItems(bond);
	}

	@Override
	public boolean save(Bond bond) {
		if (validate(bond)) {
			bondResource.save(bond);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Bond bond) {
		bondResource.remove(bond);
		return true;
	}

	@Override
	public Collection<Bond> getBonds() {
		return bondResource.getAll();
	}

	@Override
	public Bond getBondByName(String name) {
		return bondResource.getByName(name);
	}

	@Override
	public boolean deleteBondByName(String name) {
		Bond bond = getBondByName(name);
		if (bond != null) {
			bondResource.remove(bond);
			return true;
		}
		return false;
	}

	private boolean validate(Bond bond) {
		return true;
	}

}
