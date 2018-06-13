package psybergate.grad2018.javafnds.finance.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private EventType type;
	
	private Integer month;
	
	private Double value;

}
