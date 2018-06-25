<%@ page
  import="psybergate.grad2018.javafnds.finance.bean.*,psybergate.grad2018.javafnds.finance.entity.*, java.util.List"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="input-group">
  <span class="input-group-text">Bond Cost</span>
  <input type="text" readonly="readonly" class="form-control" value='${bondCost}'>
  <span class="input-group-text">Transfer Cost</span>
  <input type="text" readonly="readonly" class="form-control" value='${transferCost}'>
  <span class="input-group-text">Legal Cost</span>
  <input type="text" readonly="readonly" class="form-control" value='${legalCost}'>
  <label class="input-group-text">Cash Required</label>
  <input type="text" readonly="readonly" class="form-control" value='${cashRequired}'>
</div>
<table class="table">
  <thead>
    <tr>
      <th>Month</th>
      <th>Opening Balance</th>
      <th>Repayment</th>
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
      <tr data-rate="<%=forecastItem.getRate()%>"
        data-withdrawal="<%=forecastItem.getWithdrawal().doubleValue()%>"
        data-deposit="<%=forecastItem.getDeposit().doubleValue()%>" data-month="<%=monthCount%>"
      >
        <td><%=monthCount%></td>
        <td><%=forecastItem.getInitialAmount().stringValue()%></td>
        <td><%=((BondForecastItem) forecastItem).getRepayment().stringValue()%></td>
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
