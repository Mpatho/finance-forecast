<form action="forecast" id="form" class="small">
  <input type="hidden" name="id" value="${id}" />
  <div class="form-group">
    <label>Purchase Price</label>
    <div class="input-group">
      <span class="input-group-text">R</span>
      <input type="number" min="0" form="form" class="form-control" name="price" value='${price}'
        placeholder="Rands"
      >
    </div>
    <label>Deposit</label>
    <div class="input-group">
      <span class="input-group-text">R</span>
      <input type="number" min="0" form="form" class="form-control" name="deposit"
        value='${deposit}' placeholder="Rands"
      >
    </div>
    <label>Interest Rate</label>
    <div class="input-group">
      <input type="number" min="0" form="form" class="form-control" id="rate" name="rate"
        value='${rate}' placeholder="0"
      >
      <div class="input-group-append">
        <span class="input-group-text" id="basic-addon2">%</span>
      </div>
    </div>
    <label>Investment Term</label>
    <div class="input-group">
      <input type="number" min="0" form="form" class="form-control" id="months" name="months"
        value='${months}' placeholder="0"
      >
      <div class="input-group-append">
        <span class="input-group-text" id="basic-addon2">months</span>
      </div>
    </div>
    <label class="input-group-text">Include cash required in bond</label>
    <div class="form-group">
      <input type="checkbox" form="form" class="form-control" name="include_cash_required"
        ${checked}
      >
    </div>
    <button type="button" class="btn btn-primary fa fa-save" data-toggle="modal"
      data-target="#saveModal"
    ></button>
    <button form="form" type="submit" class="btn btn-primary fa">Generate</button>
  </div>
</form>