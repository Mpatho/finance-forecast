<%@ page
	import="psybergate.grad2018.javafnds.finance.bean.*,psybergate.grad2018.javafnds.finance.entity.*, java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table table-hover table-bordered">
	<thead class="forecast">
		<tr>
			<th class="text-center">Month</th>
			<th class="text-center">Opening Balance</th>
			<th class="text-center">Repayment</th>
			<th class="text-center">Deposit</th>
			<th class="text-center">Withdrawal</th>
			<th class="text-center">Interest</th>
			<th class="text-center">Rate</th>
			<th class="text-center">Closing Balance</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty forecastItems}">
			<%
      	List<ForecastItem> forecastItems = (List<ForecastItem>) request.getAttribute("forecastItems");
      		int monthCount = 0;
      		for (ForecastItem forecastItem : forecastItems) {
      			monthCount++;
      %>
			<tr class="event" data-toggle="modal" data-target="#eventModal"
				data-rate="<%=forecastItem.getRate()%>"
				data-withdrawal="<%=forecastItem.getWithdrawal().doubleValue()%>"
				data-deposit="<%=forecastItem.getDeposit().doubleValue()%>"
				data-month="<%=monthCount%>"
				data-amount-change="<%=((BondForecastItem) forecastItem).getRepayment().doubleValue() %>">
				<td class="text-right"><%=monthCount%></td>
				<td class="text-right"><%=forecastItem.getInitialAmount().stringValue()%></td>
				<td class="text-right"><%=((BondForecastItem) forecastItem).getRepayment().stringValue()%></td>
				<td class="text-right"><%=forecastItem.getDeposit().stringValue()%></td>
				<td class="text-right"><%=forecastItem.getWithdrawal().stringValue()%></td>
				<td class="text-right"><%=forecastItem.getInterest().stringValue()%></td>
				<td class="text-right"><%=forecastItem.getRate()%></td>
				<td class="text-right"><%=forecastItem.getEndAmount().stringValue()%></td>
			</tr>
			<%
      	}
      %>
		</c:if>
	</tbody>
</table>
