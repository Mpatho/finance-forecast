<%@page import="psybergate.grad2018.javafnds.finance.service.ForecastService"%>
<%@ page
  import="psybergate.grad2018.javafnds.finance.bean.*,psybergate.grad2018.javafnds.finance.entity.*, java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table table-hover table-bordered">
  <thead class="forecast">
    <%
      Map<String, Money> summary = (Map<String, Money>) request.getAttribute("summary");
    %>
    <tr>
      <th class="text-center">Summary</th>
      <th class="text-center"><%=summary.get(ForecastService.TOTAL_CONTRIBUTION)%></th>
      <th class="text-center"><%=summary.get(ForecastService.TOTAL_DEPOSITS)%></th>
      <th class="text-center"><%=summary.get(ForecastService.TOTAL_WITHDRAWALS)%></th>
      <th class="text-center"><%=summary.get(ForecastService.TOTAL_INTEREST)%></th>
      <th class="text-center"></th>
      <th class="text-center"><%=summary.get(ForecastService.END_BALANCE)%></th>
    </tr>
    <tr>
      <th class="text-center">Month</th>
      <th class="text-center">Opening Balance</th>
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
      <tr class="event" data-toggle="modal" data-target="#eventModal" data-month="<%=monthCount%>"
        data-rate="<%=forecastItem.getRate()%>"
        data-withdrawal="<%=forecastItem.getWithdrawal().doubleValue()%>"
        data-deposit="<%=forecastItem.getDeposit().doubleValue()%>"
        <%if (forecastItem.getFixedRepayment() != null) {%>
        data-repayment="<%=forecastItem.getFixedRepayment().doubleValue()%>"
        data-repayment-change="<%=forecastItem.isChangeRepayment()%>" <%}%>
      >
        <td class="text-right"><%=monthCount%></td>
        <td class="text-right"><%=forecastItem.getInitialAmount().stringValue()%></td>
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
