<%@ page import="psybergate.grad2018.javafnds.finance.bean.*, java.util.List"%>
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
      <jsp:include page="paremeters.jsp"></jsp:include>
    </div>
    <div class="col-md-8 offset-md-2">
      <jsp:include page="months.jsp"></jsp:include>
    </div>
    <div class="col-md-2 offset-md-10 position-fixed h-100 d-inline-block border-right">
      <jsp:include page="../template/events/list.jsp"></jsp:include>
    </div>
  </div>
  <jsp:include page="../template/forecast/save.jsp"></jsp:include>
  <jsp:include page="../template/events/add.jsp"></jsp:include>
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.bundle.min.js"></script>
  <script src="/finance-1.0/js/app.js"></script>
  <script src="/finance-1.0/js/events.js"></script>
</body>
</html>