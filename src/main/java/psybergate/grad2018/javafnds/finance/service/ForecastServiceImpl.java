package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateful;

@Stateful
public abstract class ForecastServiceImpl implements ForecastService{

	private BigDecimal initialAmount;

	private BigDecimal rate;

	private Integer months;

	/* (non-Javadoc)
	 * @see psybergate.grad2018.javafnds.finance.service.ForecastService#getForecastItems()
	 */
	@Override
	public abstract List<ForecastItem> getForecastItems();

	/* (non-Javadoc)
	 * @see psybergate.grad2018.javafnds.finance.service.ForecastService#getInitialAmount()
	 */
	@Override
	public BigDecimal getInitialAmount() {
		return initialAmount;
	}

	/* (non-Javadoc)
	 * @see psybergate.grad2018.javafnds.finance.service.ForecastService#setInitialAmount(java.math.BigDecimal)
	 */
	@Override
	public void setInitialAmount(BigDecimal initialAmount) {
		this.initialAmount = initialAmount;
	}

	/* (non-Javadoc)
	 * @see psybergate.grad2018.javafnds.finance.service.ForecastService#getRate()
	 */
	@Override
	public BigDecimal getRate() {
		return rate;
	}

	/* (non-Javadoc)
	 * @see psybergate.grad2018.javafnds.finance.service.ForecastService#setRate(java.math.BigDecimal)
	 */
	@Override
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/* (non-Javadoc)
	 * @see psybergate.grad2018.javafnds.finance.service.ForecastService#getMonths()
	 */
	@Override
	public Integer getMonths() {
		return months;
	}

	/* (non-Javadoc)
	 * @see psybergate.grad2018.javafnds.finance.service.ForecastService#setMonths(java.lang.Integer)
	 */
	@Override
	public void setMonths(Integer months) {
		this.months = months;
	}

}
