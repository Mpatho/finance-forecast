package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

import psybergate.grad2018.javafnds.finance.entity.Event;

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
