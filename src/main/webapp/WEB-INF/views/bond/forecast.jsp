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
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link" href="/finance-1.0/forecast/forecasts">Forecasts</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="/finance-1.0/investment/fixed">Fixed Investment</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="/finance-1.0/investment/monthly">Monthly Investment</a>
    </li>
    <li class="nav-item">
      <a class="nav-link active" href="/finance-1.0/bond/forecast">Bond</a>
    </li>
  </ul>
  <br>
  <div class="container-fluid">
    <div class="input-group">
      <span class="input-group-text">Bond Cost</span>
      <input type="text" readonly="readonly" class="form-control" value='${bondCost}'>
      <span class="input-group-text">Transfer Cost</span>
      <input type="text" readonly="readonly" class="form-control" value='${transferCost}'>
      <span class="input-group-text">Legal Cost</span>
      <input type="text" readonly="readonly" class="form-control" value='${legalCost}'>
      <label class="input-group-text">Cash Required</label>
      <input type="text" readonly="readonly" class="form-control" value='${cashRequired}'>
    </div>
    <div class="input-group">
      <span class="input-group-text">Purchase Price</span>
      <input type="number" min="0" form="form" class="form-control" name="price" value='${price}'
        placeholder="Rands"
      >
      <span class="input-group-text">Deposit</span>
      <input type="number" min="0" form="form" class="form-control" name="deposit"
        value='${deposit}' placeholder="Rands"
      >
      <span class="input-group-text">Interest Rate</span>
      <input type="number" min="0" form="form" class="form-control" id="rate" name="rate"
        value='${rate}' placeholder="%"
      >
      <label class="input-group-text">Investment Term</label>
      <input type="number" min="0" form="form" class="form-control" id="months" name="months"
        value='${months}' placeholder="Number of Months"
      >
      <label class="input-group-text">Include cash required in bond</label>
      <input type="checkbox" form="form" class="form-control" name="include_cash_required"
        ${checked}
      >
    </div>
    <button type="button" class="btn btn-primary fa fa-save" data-toggle="modal"
      data-target="#exampleModal"
    ></button>
    <button form="form" type="submit" class="btn btn-primary fa">Generate</button>
  </div>
  <jsp:include page="months.jsp"></jsp:include>
  <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
    aria-labelledby="exampleModalLabel" aria-hidden="true"
  >
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Fixed Investment</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form action="forecast" id="form">
            <input type="hidden" name="id" value="${id}" />
            <div class="form-group">
              <label for="name">Name</label>
              <input type="text" class="form-control" id="name" name="name"
                placeholder="Forecast Name"
              >
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button form="form" type="submit" class="btn btn-primary fa fa-save" formaction="save"></button>
        </div>
      </div>
    </div>
  </div>
  <script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
  <script src="/finance-1.0/js/bootstrap.bundle.min.js"></script>
  <script src="/finance-1.0/js/app.js"></script>
  <script>
			function generateEventInput() {
				$(".event-form-input").children(":first").clone().appendTo(
						".event-form-input");
			}
		</script>
</body>
</html>