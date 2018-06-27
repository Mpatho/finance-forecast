package psybergate.grad2018.javafnds.finance.entity;

import java.util.Arrays;
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

@Entity
public class Bond {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Money price;

	@OneToOne(cascade = CascadeType.ALL)
	private Money deposit;

	private Double rate;

	private Integer months;

	@Column(unique = true)
	private String name;

	@JoinColumn(name = "bond_id")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Event> events = new LinkedList<>();

	protected Bond() {}

	public Bond(Long id, Money price, Money deposit, Double rate, Integer months, String name) {
		this.id = id;
		this.price = price;
		this.deposit = deposit;
		this.rate = rate;
		this.months = months;
		this.name = name;
	}

	public Bond(Money price, Money deposit, Double rate, Integer months, String name) {
		this(null, price, deposit, rate, months, name);
	}

	public Bond(Money price, Money deposit, Double rate, Integer months) {
		this(null, price, deposit, rate, months, null);
	}
	
	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

	public Money getDeposit() {
		return deposit;
	}

	public void setDeposit(Money deposit) {
		this.deposit = deposit;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Event> getEvents(Integer months) {
		List<Event> events = new LinkedList<>();
		for (Event event : this.events) {
			if (event.getMonth().equals(months)) {
				events.add(event);
			}
		}
		return events;
	}

	public void addEvent(Event event) {
		this.events.add(event); 
	}

	@Override
	public String toString() {
		return "Bond [id=" + id + ", price=" + price + ", deposit=" + deposit + ", rate=" + rate + ", months=" + months
				+ ", name=" + name + ", events=" + Arrays.toString(events.toArray()) + "]";
	}

	
}
