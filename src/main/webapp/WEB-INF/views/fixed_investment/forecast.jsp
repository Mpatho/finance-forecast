<%@ page import="psybergate.grad2018.javafnds.finance.service.*, java.util.List"%>
<html>
<head>
<link rel="stylesheet" href="/finance-1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/finance-1.0/css/font-awesome.min.css">
</head>
<body>
  <%
  	List<ForecastItem> forecastItems = (List<ForecastItem>) request.getAttribute("forecastItems");
  %>
  <table class="table">
  <thead>
    <tr>
      <th scope="col">Month</th>
      <th scope="col">Begin Amount</th>
      <th scope="col">Interest</th>
      <th scope="col">End Amount</th>
    </tr>
  </thead>
  <tbody>
  <%
  	int monthCount = 0;
  	for (ForecastItem forecastItem : forecastItems) {
  	monthCount++;%>
  	  <tr>
      <th scope="row"><%=monthCount%></th>
      <td><%=forecastItem.getInitialAmount()%></td>
      <td><%=forecastItem.getInterest()%></td>
      <td><%=forecastItem.getEndAmount()%></td>
    </tr>
  	<%}
  %>
  
    </tbody>
</table>
  
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.min.js"></script>
</body>
</html>