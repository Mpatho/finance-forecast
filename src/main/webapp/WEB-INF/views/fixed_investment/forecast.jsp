<%@ page import="psybergate.grad2018.javafnds.finance.service.*, java.util.List"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link rel="stylesheet" href="/finance-1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/finance-1.0/css/font-awesome.min.css">
</head>
<body>
	<h1>Fixed Investment Forecast</h1>
	<form action="forecast">
  <div class="form-group">
    <label for="initialAmount">Initial Amount</label>
    <input type="text" class="form-control" id="initialAmount" name="initialAmount" placeholder="0.00">
  </div>
  <div class="form-group">
    <label for="rate">Interest Rate(%)</label>
    <input type="text" class="form-control" id="rate" name="rate" placeholder="0.00">
  </div>
  <div class="form-group">
    <label for="months">Investment Term</label>
    <input type="text" class="form-control" id="months" name="months" placeholder="Number of Months">
  </div>
  
  <button type="submit" class="btn btn-primary">Calculate</button><br>
  
 <c:choose>
  <c:when test="${not empty param.initialAmount}">
  	<div class="form-group">
    <label for="name">Name: </label>
    <input type="text" class="form-control" id="name" name="name" placeholder="Forecast Name">
  </div>
  	<button type="submit" class="btn btn-primary" formaction="save">Save</button>
  </c:when>
  <c:otherwise>
  <div class="form-group">
    <label for="name">Name: </label>
    <input type="text" class="form-control" id="name" name="name" placeholder="Forecast Name" disabled>
  </div>
    <button type="submit" class="btn btn-primary" disabled>Save</button>
  </c:otherwise>
</c:choose>
</form>
	
<c:if test="${not empty param.initialAmount}" >
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
      <td><%=forecastItem.getInitialAmount().stringValue()%></td>
      <td><%=forecastItem.getInterest().stringValue()%></td>
      <td><%=forecastItem.getEndAmount().stringValue()%></td>
    </tr>
  	<%}
  %>
  
    </tbody>
</table>
 </c:if>
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.min.js"></script>
</body>
</html>