package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

import javax.enterprise.context.Dependent;

import psybergate.grad2018.javafnds.finance.entity.Event;

@Dependent
public class EventResource extends AbstractResource<Event> {

	@Override
	public Event getById(Long id) {
		return em.find(Event.class, id);
	}

	@Override
	public Collection<Event> getAll() {
		return getAll(Event.class);
	}
}
