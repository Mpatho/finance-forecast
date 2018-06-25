<%@ page import="psybergate.grad2018.javafnds.finance.entity.*,java.util.Collection"%>
<html>
<head>
<link rel="stylesheet" href="/finance-1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/finance-1.0/css/font-awesome.min.css">
</head>
<body>
  <div class="row h-100">
    <div class="col-md-2 position-fixed h-100 d-inline-block border-left">
      <ul class="nav flex-column">
        <li class="nav-item">
          <a class="nav-link" href="/finance-1.0/forecast/forecasts">Forecasts</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/finance-1.0/investment/forecast">Investment</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/finance-1.0/bond/forecast">Bond</a>
        </li>
      </ul>
    </div>
    <div class="col-md-10 offset-md-2">
      <div class="row">
        <%
        	Collection<Investment> investments = (Collection<Investment>) request.getAttribute("investments");
        	for (Investment investment : investments) {
        %>
        <div class="col-sm-4" style="margin-top: 1em;">
          <div class="card bg-light mb-3">
            <div class="card-body">
              <h4 class="card-title">Investment Forecast</h4>
              <h6 class="card-subtitle mb-2 text-muted"><%=investment.getName()%></h6>
            </div>
            <ul class="list-group list-group-flush">
              <li class="list-group-item">
                <span class="badge badge-pill badge-light">Type</span>
                <span class="pull-right">
                  <%=investment.getType()%>
                </span>
              </li>
              <li class="list-group-item">
                <span class="badge badge-pill badge-light">Amount</span>
                <span class="pull-right">
                  <%=investment.getAmount()%>
                </span>
              </li>
              <li class="list-group-item">
                <span class="badge badge-pill badge-light">Months</span>
                <span class="pull-right">
                  <%=investment.getMonths()%>
                </span>
              </li>
              <li class="list-group-item">
                <span class="badge badge-pill badge-light">Interest</span>
                <span class="pull-right">
                  <%=investment.getRate()%>
                </span>
              </li>
            </ul>
            <div class="card-footer">
              <a href="/finance-1.0/investment/delete?name=<%=investment.getName()%>"
                class="card-link btn btn-warning fa fa-trash"
              ></a> <a href="/finance-1.0/investment/forecast?name=<%=investment.getName()%>"
                class="card-link btn btn-primary fa fa-eye"
              ></a>
            </div>
          </div>
        </div>
        <%
        	}
        %>
        <%
        	Collection<Bond> bonds = (Collection<Bond>) request.getAttribute("bonds");
        	for (Bond bond : bonds) {
        %>
        <div class="col-sm-4" style="margin-top: 1em;">
          <div class="card bg-light mb-3">
            <div class="card-body">
              <h4 class="card-title">Bond Forecast</h4>
              <h6 class="card-subtitle mb-2 text-muted"><%=bond.getName()%></h6>
            </div>
            <ul class="list-group list-group-flush">
              <li class="list-group-item">
                <span class="badge badge-pill badge-light">Price</span>
                <span class="pull-right">
                  <%=bond.getPrice()%>
                </span>
              </li>
              <li class="list-group-item">
                <span class="badge badge-pill badge-light">Deposit</span>
                <span class="pull-right">
                  <%=bond.getDeposit()%>
                </span>
              </li>
              <li class="list-group-item">
                <span class="badge badge-pill badge-light">Months</span>
                <span class="pull-right">
                  <%=bond.getMonths()%>
                </span>
              </li>
              <li class="list-group-item">
                <span class="badge badge-pill badge-light">Interest</span>
                <span class="pull-right">
                  <%=bond.getRate()%>
                </span>
              </li>
            </ul>
            <div class="card-footer">
              <a href="/finance-1.0/bond/delete?name=<%=bond.getName()%>"
                class="card-link btn btn-warning fa fa-trash"
              ></a> <a
                href="/finance-1.0/bond/forecast?name=<%=bond.getName()%>&include_cash_required=checked"
                class="card-link btn btn-primary fa fa-eye"
              ></a>
            </div>
          </div>
        </div>
        <%
        	}
        %>
      </div>
    </div>
  </div>
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>