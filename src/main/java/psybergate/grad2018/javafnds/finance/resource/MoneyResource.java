package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

import javax.enterprise.context.Dependent;

import psybergate.grad2018.javafnds.finance.entity.Money;

@Dependent
public class MoneyResource extends AbstractResource<Money> {

	@Override
	public Money getById(Long id) {
		return em.find(Money.class, id);
	}

	@Override
	public Collection<Money> getAll() {
		return getAll(Money.class);
	}
}
