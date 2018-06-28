<%@page import="psybergate.grad2018.javafnds.finance.service.ForecastService"%>
<%@ page
  import="psybergate.grad2018.javafnds.finance.bean.*,psybergate.grad2018.javafnds.finance.entity.*, java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Map<String, Money> summary = (Map<String, Money>) request.getAttribute("summary");
%>
<table class="table">
  <thead>
    <tr>
      <th class="text-right">Total Contributions</th>
      <th class="text-right">Total Deposits</th>
      <th class="text-right">Total Withdrawals</th>
      <th class="text-right">Total Interest</th>
      <th class="text-right">End Balance</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td class="text-right"><%=summary.get(ForecastService.TOTAL_CONTRIBUTION)%></td>
      <td class="text-right"><%=summary.get(ForecastService.TOTAL_DEPOSITS)%></td>
      <td class="text-right"><%=summary.get(ForecastService.TOTAL_WITHDRAWALS)%></td>
      <td class="text-right"><%=summary.get(ForecastService.TOTAL_INTEREST)%></td>
      <td class="text-right"><%=summary.get(ForecastService.END_BALANCE)%></td>
    </tr>
  </tbody>
</table>
