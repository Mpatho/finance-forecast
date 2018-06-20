package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

import javax.enterprise.context.Dependent;

import psybergate.grad2018.javafnds.finance.entity.Bond;

@Dependent
public class BondResource extends AbstractResource<Bond> implements ForecastResource<Bond> {

	@Override
	public Collection<Bond> getAll() {
		return getAll(Bond.class);
	}

	@Override
	public Bond getById(Long id) {
		return em.find(Bond.class, id);
	}

	@Override
	public void save(Bond entity) {
		if (entity.getId() == null) {
			super.save(entity);
		}
		else {
			Bond bond = getById(entity.getId());
			bond.setPrice(entity.getPrice());
			bond.setDeposit(entity.getDeposit());
			bond.setMonths(entity.getMonths());
			bond.setName(entity.getName());
			bond.setRate(entity.getRate());
			super.save(bond);
		}
	}

	@Override
	public Bond getByName(String name) {
		// need modification
		if (name == null)
			return null;
		for (Bond bond : this) {
			if (bond.getName().equals(name))
				return bond;
		}
		return null;
	}

}
