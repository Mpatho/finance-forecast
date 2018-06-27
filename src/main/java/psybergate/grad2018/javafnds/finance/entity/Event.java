package psybergate.grad2018.javafnds.finance.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	protected Event() {

	}

	public Event(String type, Integer month, BigDecimal value) {
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

	@Override
	public int compareTo(Event o) {
		return this.getMonth().compareTo(o.getMonth());
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", type=" + type + ", month=" + month + ", value=" + value + "]";
	}

}
