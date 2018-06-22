<div class="panel panel-primary">
  <div class="panel-heading">Forecast Events</div>
  <div class="panel-body event-form">
    <div class="event-form-input" style="max-height: 200px; display: block; overflow-y: scroll;">
      <div class="input-group event-form-child">
        <input type="hidden" min="0" form="form" class="form-control" id="month" name="eventMonth">
        <select name="eventType" form="form" class="form-control" id="eventType">
          <option value="">Select</option>
          <option value="DEPOSIT">Deposit</option>
          <option value="WITHDRAW">Withdrawal</option>
          <option value="RATE_CHANGE">Change Rate</option>
        </select>
        <input type="number" min="0" step="0.01" form="form" class="form-control" id="depositValue"
          name="eventValue" placeholder="0.00"
        >
      </div>
    </div>
  </div>
</div>
