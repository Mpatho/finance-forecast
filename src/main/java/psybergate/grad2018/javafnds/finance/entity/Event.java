package psybergate.grad2018.javafnds.finance.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Event implements Comparable<Event> {

	public static final String RATE_CHANGE = "RATE_CHANGE";

	public static final String WITHDRAW = "WITHDRAW";

	public static final String DEPOSIT = "DEPOSIT";

	public static final String AMOUNT_CHANGE = "AMOUNT_CHANGE";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String type;

	private Integer month;

	private BigDecimal value;

	@ManyToOne
	@JoinColumn(name = "investment_id", referencedColumnName = "investment_id")
	private Investment investment;

	protected Event() {

	}

	public Event(String type, Integer month, BigDecimal value) {
		super();
		this.type = type;
		this.month = month;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public Investment getInvestment() {
		return investment;
	}

	public void setInvestment(Investment investment) {
		this.investment = investment;
	}

	@Override
	public int compareTo(Event o) {
		return this.getMonth().compareTo(o.getMonth());
	}

}
