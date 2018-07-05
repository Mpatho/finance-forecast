package psybergate.grad2018.javafnds.finance.entity;

import java.util.List;
import java.util.Set;

public interface ForecastEntity {

	void setRate(Double rate);

	Long getId();

	Integer getMonths();

	boolean addEvent(Event event);

	Set<Event> getEvents();

	List<Event> getEvents(Integer months);

	void setEvents(Set<Event> arrayList);

}