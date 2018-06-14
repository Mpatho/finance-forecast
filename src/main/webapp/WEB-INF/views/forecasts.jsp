<%@ page import="psybergate.grad2018.javafnds.finance.entity.Investment,java.util.Collection"%>
<html>
<head>
<link rel="stylesheet" href="/finance-1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/finance-1.0/css/font-awesome.min.css">
</head>
<body>
  <%
  	Collection<Investment> investments = (Collection<Investment>) request.getAttribute("investments");
  %>
  <%
  	for (Investment investment : investments) {
  		out.println("Name : " + investment.getName());
  		out.println("Initial Amount" + investment.getInitailAmount());
  		out.println(""+investment.getMonths());
  		out.println(investment.getRate());
  	}
  %>
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.min.js"></script>
</body>
</html>