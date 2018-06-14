<%@ page import="psybergate.grad2018.javafnds.finance.entity.Investment,java.util.Collection"%>
<html>
<head>
<link rel="stylesheet" href="/finance-1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/finance-1.0/css/font-awesome.min.css">
</head>
<body>
  <div class="container-fluid">
    <table class="table table-bordered">
      <thead>
        <tr class="blue-grey lighten-4">
          <th>Name</th>
          <th>Initial Amount</th>
          <th>Months</th>
          <th>Rate</th>
        </tr>
      </thead>
      <tbody>
        <%
        	Collection<Investment> investments = (Collection<Investment>) request.getAttribute("investments");
        %>
        <%
        	for (Investment investment : investments) {
        		out.print("<tr><a href='/finance-1.0/investment/view?name=" + investment.getName() + "'>");
        		out.print("<td>" + investment.getName() + "</td>");
        		out.print("<td>" + investment.getInitialAmount() + "</td>");
        		out.print("<td>" + investment.getMonths() + "</td>");
        		out.print("<td>" + investment.getRate() + "</td>");
        		out.print("</a></tr>");
        	}
        %>
      </tbody>
    </table>
  </div>
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.min.js"></script>
</body>
</html>