<%@ page import="psybergate.grad2018.javafnds.finance.bean.*, java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE = edge">
<meta name="viewport" content="width = device-width, initial-scale = 1">
<link rel="stylesheet" href="/finance-1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/finance-1.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/finance-1.0/css/events.css">
<link rel="stylesheet" href="/finance-1.0/css/forecast.css">
</head>
<body>
  <jsp:include page="../template/navs.jsp"></jsp:include>
  <div class="container-fluid" id="bond-body">
    <div class="row">
      <div class="col-sm-2 h-100 d-inline-block position-fixed bg-light">
        <jsp:include page="paremeters.jsp"></jsp:include>
      </div>
      <div class="col-sm-7 offset-sm-2 d-inline-block bg-light">
        <jsp:include page="months.jsp"></jsp:include>
      </div>
      <div class="col-sm-3 h-100 offset-sm-9 d-inline-block position-fixed bg-light">
        <jsp:include page="../template/events/list.jsp"></jsp:include>
      </div>
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