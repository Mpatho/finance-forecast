package psybergate.grad2018.javafnds.finance.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
	
	private String name;

	@OneToMany
	@JoinColumn(name = "bond_id")
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
		this.price = price;
		this.deposit = deposit;
		this.rate = rate;
		this.months = months;
		this.name = name;
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

}
