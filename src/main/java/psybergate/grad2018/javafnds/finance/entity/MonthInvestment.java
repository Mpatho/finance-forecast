package psybergate.grad2018.javafnds.finance.entity;

import java.util.Iterator;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

public class MonthInvestment extends Investment implements Iterable<Event> {

	@OneToMany
	@JoinColumn
	private List<Event> events;

	@Override
	public Iterator<Event> iterator() {
		return events.iterator();
	}

	public boolean addEvent(Event event) {
		return events.add(event);
	}
}
