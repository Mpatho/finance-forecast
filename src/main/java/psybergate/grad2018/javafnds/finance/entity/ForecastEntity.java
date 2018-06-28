package psybergate.grad2018.javafnds.finance.entity;

import java.util.List;

public interface ForecastEntity {

	void setRate(Double rate);

	Long getId();

	Integer getMonths();

	boolean addEvent(Event event);

	List<Event> getEvents();

	List<Event> getEvents(Integer months);

	void setEvents(List<Event> arrayList);

}