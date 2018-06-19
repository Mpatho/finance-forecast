<%@ page import="psybergate.grad2018.javafnds.finance.bean.*, java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="/finance-1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/finance-1.0/css/font-awesome.min.css">
</head>
<body>
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link" href="/finance-1.0/investment/forecasts">Forecasts</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="/finance-1.0/investment/fixed">Fixed Investment</a>
    </li>
    <li class="nav-item">
      <a class="nav-link active" href="/finance-1.0/investment/monthly">Monthly Investment</a>
    </li>
  </ul>
  <br>
  <div class="container-fluid">
    <div class="input-group">
      <span class="input-group-text">Monthly Amount</span>
      <input type="number" min="0" form="form" class="form-control" id="initialAmount"
        name="initialAmount" value='${initialAmount}' placeholder="0.00"
      >
      <span class="input-group-text"> Interest Rate</span>
      <input type="number" min="0" form="form" class="form-control" id="rate" name="rate"
        value='${rate}' placeholder="%"
      >
      <span class="input-group-text">Investment Term</span>
      <input type="number" min="0" step="1" form="form" class="form-control" id="months"
        name="months" value='${months}' placeholder="Number of Months"
      >
    </div>
    <button type="button" class="btn btn-primary fa fa-save" data-toggle="modal"
      data-target="#exampleModal"
    ></button>
    <button form="form" type="submit" class="btn btn-primary fa">Generate</button>
    <table class="table table-bordered">
      <thead>
        <tr>
          <th style="width: 8%;" class="text-center">Month</th>
          <th style="width: 23%;" class="text-center">Opening Balance</th>
          <th style="width: 23%;" class="text-center">Monthly Amount</th>
          <th style="width: 23%;" class="text-center">Interest</th>
          <th style="width: 23%;" class="text-center">Closing Amount</th>
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
          <tr>
            <th style="width: 8%;" class="text-right"><%=monthCount%></th>
            <td style="width: 23%;" class="text-right"><%=forecastItem.getInitialAmount().stringValue()%></td>
            <td style="width: 23%;" class="text-right"><%=forecastItem.getMonthlyAmount().stringValue()%></td>
            <td style="width: 23%;" class="text-right"><%=forecastItem.getInterest().stringValue()%></td>
            <td style="width: 23%;" class="text-right"><%=forecastItem.getEndAmount().stringValue()%></td>
          </tr>
          <%
          	}
          %>
        </c:if>
      </tbody>
    </table>
  </div>
  <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
    aria-labelledby="exampleModalLabel" aria-hidden="true"
  >
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Monthly Investment</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form action="monthly" id="form">
            <input type="hidden" name="type" value="monthly" />
            <div class="form-group">
              <label for="name">Name</label>
              <input type="text" class="form-control" id="name" name="name"
                placeholder="Forecast Name"
              >
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button form="form" type="submit" class="btn btn-primary fa fa-save"
            formaction="/finance-1.0/investment/save"
          ></button>
        </div>
      </div>
    </div>
  </div>
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>