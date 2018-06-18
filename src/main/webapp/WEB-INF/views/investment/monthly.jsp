<%@ page import="psybergate.grad2018.javafnds.finance.bean.*, java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="/finance-1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/finance-1.0/css/font-awesome.min.css">
</head>
<body>
  <ul class="nav nav-tabs">
    <li class="nav-item"><a class="nav-link" href="/finance-1.0/investment/forecasts">Forecasts</a>
    </li>
    <li class="nav-item"><a class="nav-link" href="/finance-1.0/investment/fixed">Fixed
        Investment</a></li>
    <li class="nav-item"><a class="nav-link active" href="/finance-1.0/investment/monthly">Monthly
        Investment</a></li>
  </ul>
  <br>
  <div class="container-fluid">
    <button type="button" class="btn btn-primary fa fa-edit" data-toggle="modal"
      data-target="#exampleModal"
    ></button>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">Month</th>
          <th scope="col">Opening Balance</th>
          <th scope="col">Monthly Amount</th>
          <th scope="col">Interest</th>
          <th scope="col">Closing Amount</th>
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
            <th scope="row"><%=monthCount%></th>
            <td><%=forecastItem.getInitialAmount().stringValue()%></td>
            <td><%=forecastItem.getMonthlyAmount().stringValue()%></td>
            <td><%=forecastItem.getInterest().stringValue()%></td>
            <td><%=forecastItem.getEndAmount().stringValue()%></td>
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
              <label for="initialAmount">Initial Amount</label> <input type="number" min="0"
                step=".01" class="form-control" id="initialAmount" name="initialAmount"
                value='${initialAmount}' placeholder="0.00"
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
            <div class="form-group">
              <label for="name">Name</label> <input type="text" class="form-control" id="name"
                name="name" placeholder="Forecast Name"
              >
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button form="form" type="submit" class="btn btn-primary">Calculate</button>
          <button form="form" type="submit" class="btn btn-primary" class="btn btn-primary"
            formaction="/finance-1.0/investment/save"
          >Save changes</button>
        </div>
      </div>
    </div>
  </div>
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.min.js"></script>
</body>
</html>