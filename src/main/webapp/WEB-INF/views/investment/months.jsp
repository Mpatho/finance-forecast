<%@ page
  import="psybergate.grad2018.javafnds.finance.bean.*,psybergate.grad2018.javafnds.finance.entity.*, java.util.List"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table table-hover" style="z-index: -100">
  <thead>
    <tr>
      <th>Month</th>
      <th>Opening Balance</th>
      <th>Deposit</th>
      <th>Withdrawal</th>
      <th>Interest</th>
      <th>Rate</th>
      <th>Closing Balance</th>
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
      >
        <td><%=monthCount%></td>
        <td><%=forecastItem.getInitialAmount().stringValue()%></td>
        <td><%=forecastItem.getDeposit().stringValue()%></td>
        <td><%=forecastItem.getWithdrawal().stringValue()%></td>
        <td><%=forecastItem.getInterest().stringValue()%></td>
        <td><%=forecastItem.getRate()%></td>
        <td><%=forecastItem.getEndAmount().stringValue()%></td>
      </tr>
      <%
      	}
      %>
    </c:if>
  </tbody>
</table>
