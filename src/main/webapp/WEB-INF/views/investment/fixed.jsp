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
		<li class="nav-item"><a class="nav-link"
			href="/finance-1.0/investment/forecasts">Forecasts</a></li>
		<li class="nav-item"><a class="nav-link active"
			href="/finance-1.0/investment/fixed">Fixed Investment</a></li>
		<li class="nav-item"><a class="nav-link"
			href="/finance-1.0/investment/monthly">Monthly Investment</a></li>
	</ul>
	<br>
	<div class="container-fluid">
		<div class="input-group">
			<span class="input-group-text">Initial Amount</span> <input
				type="number" min="0" step="0.01" form="form" class="form-control"
				name="initialAmount" value='${initialAmount}' placeholder="Rands">
			<span class="input-group-text">Interest Rate</span> <input
				type="number" min="0" step="0.01" form="form" class="form-control"
				id="rate" name="rate" value='${rate}' placeholder="%"> <label
				class="input-group-text">Investment Term</label> <input
				type="number" min="0" form="form" class="form-control" id="months"
				name="months" value='${months}' placeholder="Number of Months">
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">Forecast Events</div>
			<div class="panel-body event-form">
				<div class="event-form-input" style="max-height: 200px; display:block;overflow-y: scroll;">
					<div class="input-group event-form-child">
						<span class="input-group-text">Month</span> <input type="number"
							min="0" form="form" class="form-control" id="month"
							name="eventMonth" placeholder="Month"> <span
							class="input-group-text">Select list:</span> <select
							name="eventType" form="form" class="form-control" id="eventType">
							<option value="">Select Type</option>
							<option value="DEPOSIT">Deposit</option>
							<option value="WITHDRAW">Withdrawal</option>
							<option value="RATE_CHANGE">Change Rate</option>
						</select> <span class="input-group-text">Value</span> <input type="number"
							min="0" step="0.01" form="form" class="form-control" id="value"
							name="eventValue" placeholder="0.00">

					</div>
				</div>
				<button class="btn btn-primary fa fa-plus-square"
					onclick="generateEventInput();">Add Forecast Event</button>
			</div>
		</div>
		<button type="button" class="btn btn-primary fa fa-save"
			data-toggle="modal" data-target="#exampleModal"></button>
		<button type="button" class="btn btn-primary fa" data-toggle="modal"
			data-target="#eventModal">Add Events</button>
		<button form="form" type="submit" class="btn btn-primary fa">Generate</button>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th style="width: 10%;" class="text-center">Month</th>
					<th style="width: 15%;" class="text-center">Begin Amount</th>
					<th style="width: 15%;" class="text-center">Interest Rate</th>
					<th style="width: 15%;" class="text-center">Deposit</th>
					<th style="width: 15%;" class="text-center">Withdrawal</th>
					<th style="width: 15%;" class="text-center">Interest</th>
					<th style="width: 15%;" class="text-center">Closing Amount</th>
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
						<th style="width: 10%;" class="text-right"><%=monthCount%></th>
						<td style="width: 15%;" class="text-right"><%=forecastItem.getInitialAmount().stringValue()%></td>
						<td style="width: 15%;" class="text-right"><%=forecastItem.getRate()%></td>
						<td style="width: 15%;" class="text-right"><%=forecastItem.getDeposit().stringValue()%></td>
						<td style="width: 15%;" class="text-right"><%=forecastItem.getWithdrawal().stringValue()%></td>
						<td style="width: 15%;" class="text-right"><%=forecastItem.getInterest().stringValue()%></td>
						<td style="width: 15%;" class="text-right"><%=forecastItem.getEndAmount().stringValue()%></td>
					</tr>
					<%
						}
					%>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Fixed
						Investment</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="fixed" id="form">
						<input type="hidden" name="type" value="fixed" />
						<div class="form-group">
							<label for="name">Name</label> <input type="text"
								class="form-control" id="name" name="name"
								placeholder="Forecast Name">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button form="form" type="submit"
						class="btn btn-primary fa fa-save"
						formaction="/finance-1.0/investment/save"></button>
				</div>
			</div>
		</div>
	</div>


	<script src="/finance-1.0/js/jquery-3.3.1.min.js"></script>
	<script src="/finance-1.0/js/bootstrap.bundle.min.js"></script>
	<script>
		function generateEventInput() {
			$(".event-form-input").children(":first").clone()
					.appendTo(".event-form-input");
		}
	</script>
</body>
</html>