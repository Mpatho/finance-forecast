
package psybergate.grad2018.javafnds.finance.entity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Investment implements Iterable<Event> {

	public static final String FIXED = "fixed";

	public static final String MONTHLY = "monthly";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String name;

	private String type;

	@OneToOne(cascade = CascadeType.ALL)
	private Money initialAmount;

	private Integer months;

	private Double rate;

	@OneToMany
	@JoinColumn(name = "investment_id")
	private List<Event> events = new LinkedList<>();

	protected Investment() {}

	public Investment(Long id, String name, String type, Money initialAmount, Integer months, Double rate) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.initialAmount = initialAmount;
		this.months = months;
		this.rate = rate;
	}

	public Investment(String name, String type, Money initialAmount, Integer months, Double rate) {
		this.name = name;
		this.type = type;
		this.initialAmount = initialAmount;
		this.months = months;
		this.rate = rate;
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

	public Money getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(Money initailAmount) {
		this.initialAmount = initailAmount;
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

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Long getId() {
		return id;
	}

	@Override
	public Iterator<Event> iterator() {
		return events.iterator();
	}

	public boolean addEvent(Event event) {
		return events.add(event);
	}

	@Override
	public String toString() {
		return "Investment [id=" + id + ", name=" + name + ", type=" + type + ", initialAmount=" + initialAmount
				+ ", months=" + months + ", rate=" + rate + "]";
	}

}
