<%@ page import="psybergate.grad2018.javafnds.finance.entity.Investment,java.util.Collection"%>
<html>
<head>
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
    <%
    	Collection<Investment> investments = (Collection<Investment>) request.getAttribute("investments");
    	for (Investment investment : investments) {
    %>
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">Investment Forecast</h3>
      </div>
      <div class="panel-body">
        <%=investment.getName()%>
        <%=investment.getInitialAmount()%><br>
        <%=investment.getMonths()%><br>
        <%=investment.getRate()%>
        <div class="panel-footer">
          <a href="/finance-1.0/investment/delete?name=<%=investment.getName()%>" class="card-link">delete</a>
          <a href="/finance-1.0/investment/forecast?name=<%=investment.getName()%>" class="card-link">view</a>
        </div>
      </div>
      <div class="panel-footer">Panel footer</div>
    </div>
    <%
    	}
    %>
  </div>
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>