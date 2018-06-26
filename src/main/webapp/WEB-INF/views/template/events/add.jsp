<div class="modal fade" id="eventModal" tabindex="-1" role="dialog"
  aria-labelledby="exampleModalLabel" aria-hidden="true"
>
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Event</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="form-group">
          <input type="hidden" min="0" form="form" class="form-control" id="month" name="eventMonth">
          <label for="eventType" class="badge badge-pill badge-light">Event Type</label>
          <select name="eventType" form="form" class="form-control" id="eventType">
            <option value="">Select</option>
            <option value="DEPOSIT">Deposit</option>
            <option value="WITHDRAW">Withdrawal</option>
            <option value="RATE_CHANGE">Change Rate</option>
            <option value="AMOUNT_CHANGE">Amount CHANGE</option>
          </select>
          <label for="value" class="badge badge-pill badge-light">Value</label>
          <input type="number" min="0" step="0.01" form="form" class="form-control" id="value"
            name="eventValue" placeholder="0.00"
          >
        </div>
      </div>
      <div class="modal-footer">
        <button form="form" type="submit" class="btn btn-primary fa fa-save" id="generate"></button>
      </div>
    </div>
  </div>
</div>
