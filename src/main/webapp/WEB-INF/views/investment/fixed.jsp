<%@ page
	import="psybergate.grad2018.javafnds.finance.bean.*, java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE = edge">
<meta name="viewport" content="width = device-width, initial-scale = 1">
<link rel="stylesheet" href="/finance-1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/finance-1.0/css/font-awesome.min.css">
</head>
<body>
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" href="/finance-1.0/investment/forecasts">Forecasts</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="/finance-1.0/investment/fixed">Fixed Investment</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="/finance-1.0/investment/forecast?type=monthly">Monthly Investment</a>
     </li>
  </ul>
  <div class="container-fluid">
    <form action="fixed">
      <input type="hidden" name="type" value="fixed" />
      <div class="form-group">
        <label for="initialAmount">Initial Amount</label> <input type="number" min="0" step=".01"
          class="form-control" id="initialAmount" name="initialAmount" value='${initialAmount}'
          placeholder="0.00"
        >
      </div>
      <div class="form-group">
        <label for="rate">Interest Rate(%)</label> <input type="number" min="0" step="any"
          class="form-control" id="rate" name="rate" value='${rate}' placeholder="0.00"
        >
      </div>
      <div class="form-group">
        <label for="months">Investment Term</label> <input type="number" min="0" step="1"
          class="form-control" id="months" name="months" value='${months}'
          placeholder="Number of Months"
        >
      </div>
      <button type="submit" class="btn btn-primary">Calculate</button>
      <br>
      <c:choose>
        <c:when test="${not empty param.initialAmount}">
          <div class="form-group">
            <label for="name">Name: </label> <input type="text" class="form-control" id="name"
              name="name" placeholder="Forecast Name"
            >
          </div>
          <button type="submit" class="btn btn-primary" formaction="/finance-1.0/investment/save">Save</button>
        </c:when>
        <c:otherwise>
          <div class="form-group">
            <label for="name">Name: </label> <input type="text" class="form-control" id="name"
              name="name" placeholder="Forecast Name" disabled
            >
          </div>
          <button type="submit" class="btn btn-primary" disabled>Save</button>
        </c:otherwise>
      </c:choose>
    </form>
    <c:if test="${not empty forecastItems}">
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
          			monthCount++;
          %>
          <tr>
            <th scope="row"><%=monthCount%></th>
            <td><%=forecastItem.getInitialAmount().stringValue()%></td>
            <td><%=forecastItem.getInterest().stringValue()%></td>
            <td><%=forecastItem.getEndAmount().stringValue()%></td>
          </tr>
          <%
          	}
          %>
        </tbody>
      </table>
    </c:if>
  </div>
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>