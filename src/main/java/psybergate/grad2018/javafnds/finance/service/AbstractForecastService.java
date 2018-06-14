package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateful;

import psybergate.grad2018.javafnds.finance.entity.Money;

@Stateful
public abstract class AbstractForecastService implements ForecastService {

	private Money initialAmount;

	private BigDecimal rate;

	private Integer months;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * psybergate.grad2018.javafnds.finance.service.ForecastService#getForecastItems
	 * ()
	 */
	@Override
	public abstract List<ForecastItem> getForecastItems();

	@Override
	public abstract List<ForecastItem> getForecastItems(String name);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * psybergate.grad2018.javafnds.finance.service.ForecastService#getInitialAmount
	 * ()
	 */
	@Override
	public Money getInitialAmount() {
		return initialAmount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * psybergate.grad2018.javafnds.finance.service.ForecastService#setInitialAmount
	 * (java.math.BigDecimal)
	 */
	@Override
	public void setInitialAmount(Money initialAmount) {
		this.initialAmount = initialAmount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psybergate.grad2018.javafnds.finance.service.ForecastService#getRate()
	 */
	@Override
	public BigDecimal getRate() {
		return rate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * psybergate.grad2018.javafnds.finance.service.ForecastService#setRate(java.
	 * math.BigDecimal)
	 */
	@Override
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psybergate.grad2018.javafnds.finance.service.ForecastService#getMonths()
	 */
	@Override
	public Integer getMonths() {
		return months;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * psybergate.grad2018.javafnds.finance.service.ForecastService#setMonths(java.
	 * lang.Integer)
	 */
	@Override
	public void setMonths(Integer months) {
		this.months = months;
	}

}
