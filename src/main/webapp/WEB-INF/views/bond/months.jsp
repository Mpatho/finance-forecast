<%@ page
  import="psybergate.grad2018.javafnds.finance.bean.*,psybergate.grad2018.javafnds.finance.entity.*, java.util.List"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty forecastItems}">
  <div class="row">
    <%
    	List<ForecastItem> forecastItems = (List<ForecastItem>) request.getAttribute("forecastItems");
    		int monthCount = 0;
    		Double prevRate = forecastItems.get(0).getRate();
    		for (ForecastItem forecastItem : forecastItems) {
    			monthCount++;
    %>
    <div class="col-sm-3" style="margin-top: 1em;">
      <div class="card bg-light mb-3 small">
        <div class="card-body">
          Month
          <span class="badge badge-secondary"><%=monthCount%></span>
        </div>
        <ul class="list-group list-group-flush">
          <li class="list-group-item">
            <span class="badge badge-pill badge-light">Opening Balance</span>
            <span class="pull-right">
              <%=forecastItem.getInitialAmount().stringValue()%>
            </span>
          </li>
          <li class="list-group-item">
            <span class="badge badge-pill badge-light">Interest</span>
            <span class="pull-right">
              <%=forecastItem.getInterest().stringValue()%>
            </span>
          </li>
          <li class="list-group-item">
            <span class="badge badge-pill badge-light">Repayment</span>
            <span class="pull-right">
              <%=((BondForecastItem) forecastItem).getRepayment().stringValue()%>
            </span>
          </li>
          <li class="list-group-item">
            <span class="badge badge-pill badge-light">Rate</span>
            <span class="pull-right">
              <%=forecastItem.getRate()%>
            </span>
          </li>
          <li class="list-group-item">
            <span class="badge badge-pill badge-light">Closing Balance</span>
            <span class="pull-right">
              <%=forecastItem.getEndAmount().stringValue()%>
            </span>
          </li>
        </ul>
        <div class="card-footer">
          <%
          	if (!forecastItem.getDeposit().equals(new Money(0.0))) {
          %>
          <div class="input-group event-form-child">
            <input type="hidden" min="0" form="form" class="form-control" id="month"
              name="eventMonth" value="<%=monthCount%>"
            >
            <select name="eventType" form="form" class="form-control" id="eventType">
              <option value="DEPOSIT">Deposit</option>
            </select>
            <input type="number" min="0" step="0.01" form="form" class="form-control"
              id="depositValue" name="eventValue" placeholder="0.00"
              value="<%=forecastItem.getDeposit().doubleValue()%>"
            >
          </div>
          <%
          	}
          			if (!forecastItem.getWithdrawal().equals(new Money(0.0))) {
          %>
          <div class="input-group event-form-child">
            <input type="hidden" min="0" form="form" class="form-control" id="month"
              name="eventMonth" value="<%=monthCount%>"
            >
            <select name="eventType" form="form" class="form-control" id="eventType">
              <option value="WITHDRAW">Withdrawal</option>
            </select>
            <input type="number" min="0" step="0.01" form="form" class="form-control"
              id="withdrawValue" name="eventValue" placeholder="0.00"
              value="<%=forecastItem.getWithdrawal().doubleValue()%>"
            >
          </div>
          <%
          	}
          			if (forecastItem.getRate() != prevRate) {
          %>
          <div class="input-group event-form-child">
            <input type="hidden" min="0" form="form" class="form-control" id="month"
              name="eventMonth" value="<%=monthCount%>"
            >
            <select name="eventType" form="form" class="form-control" id="eventType">
              <option value="RATE_CHANGE">Change Rate</option>
            </select>
            <input type="number" min="0" step="0.01" form="form" class="form-control"
              id="rateChangeValue" name="eventValue" placeholder="0.00"
              value="<%=forecastItem.getRate()%>"
            >
          </div>
          <%
          	}
          			prevRate = forecastItem.getRate();
          %>
          <button type="button" class="btn btn-primary fa event" data-toggle="modal"
            data-target="#eventModal" data-month="<%=monthCount%>"
            data-rate="<%=forecastItem.getRate()%>"
            data-withdrawal="<%=forecastItem.getWithdrawal()%>"
            data-deposit="<%=forecastItem.getDeposit()%>"
          >events</button>
        </div>
      </div>
    </div>
    <%
    	}
    %>
  </div>
</c:if>
<div class="modal fade" id="eventModal" tabindex="-1" role="dialog"
  aria-labelledby="exampleModalLabel" aria-hidden="true"
>
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Fixed Investment</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <jsp:include page="../template/events.jsp"></jsp:include>
      </div>
      <div class="modal-footer">
        <button form="form" type="submit" class="btn btn-primary fa" id="generate">generate</button>
      </div>
    </div>
  </div>
</div>
