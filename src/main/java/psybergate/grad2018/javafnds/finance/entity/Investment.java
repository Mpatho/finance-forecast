
package psybergate.grad2018.javafnds.finance.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

@Entity
public class Investment implements Iterable<Event>, ForecastEntity {

	public static final String FIXED = "fixed";

	public static final String MONTHLY = "monthly";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String name;

	private String type;

	@OneToOne(cascade = CascadeType.ALL)
	private Money amount;

	private Integer months;

	private Double rate;

	@OrderBy("month")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "investment_id")
	private List<Event> events = new ArrayList<>();

	public Investment() {}

	public Investment(String name, String type, Money amount, Integer months, Double rate) {
		this.name = name;
		this.type = type;
		this.amount = amount;
		this.months = months;
		this.rate = rate;
	}

	public Investment(String type, Money initialAmount, Integer months, Double rate) {
		this(null, type, initialAmount, months, rate);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public Double getRate() {
		return rate;
	}

	@Override
	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Iterator<Event> iterator() {
		return events.iterator();
	}

	@Override
	public boolean addEvent(Event event) {
		return events.add(event);
	}

	@Override
	public List<Event> getEvents() {
		return events;
	}

	@Override
	public List<Event> getEvents(Integer months) {
		List<Event> events = new LinkedList<>();
		for (Event event : this.events) {
			if (event.getMonth().equals(months)) {
				events.add(event);
			}
		}
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "Investment [id=" + id + ", name=" + name + ", type=" + type + ", initialAmount=" + amount + ", months="
				+ months + ", rate=" + rate + ", events=" + Arrays.deepToString(events.toArray()) + "]";
	}

}
