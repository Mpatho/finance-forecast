package psybergate.grad2018.javafnds.finance.entity;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

public class MonthlyInvestment extends Investment implements Iterable<Event> {

	@OneToMany
	@JoinColumn
	private List<Event> events;

	protected MonthlyInvestment() {}

	public MonthlyInvestment(String name, BigDecimal initailAmount, Integer months, BigDecimal rate) {
		super(name, initailAmount, months, rate);
	}

	@Override
	public Iterator<Event> iterator() {
		return events.iterator();
	}

	public boolean addEvent(Event event) {
		return events.add(event);
	}
}
