<form action="forecast" id="form" class="small" method="post">
  <input type="hidden" name="id" value="${id}" />
  <div class="form-group">
    <label>Purchase Price</label>
    <div class="input-group">
      <span class="input-group-text">R</span>
      <input type="number" min="0" step="0.01" form="form" class="form-control" name="price"
        value='${price}' placeholder="0.0"
      >
    </div>
    <label>Deposit</label>
    <div class="input-group">
      <span class="input-group-text">R</span>
      <input type="number" min="0" step="0.01" form="form" class="form-control" name="deposit"
        value='${deposit}' placeholder="0.0"
      >
    </div>
    <label>Interest Rate</label>
    <div class="input-group">
      <input type="number" min="0" step="0.01" max="100" form="form" class="form-control" id="rate"
        name="rate" value='${rate}' placeholder="0" required="required"
      >
      <div class="input-group-append">
        <span class="input-group-text" id="basic-addon2">%</span>
      </div>
    </div>
    <label>Investment Term</label>
    <div class="input-group">
      <input type="number" min="1" form="form" class="form-control" id="months" name="months"
        value='${months}' placeholder="0"
      >
      <div class="input-group-append">
        <span class="input-group-text" id="basic-addon2">months</span>
      </div>
    </div>
    <label for="capitalize">Include cash required in bond</label>
    <div class="input-group">
      <input type="checkbox" form="form" class="form-control" name="include_cash_required"
        id="capitalize" ${checked}
      >
    </div>
    <button type="button" class="btn btn-primary fa fa-save" data-toggle="modal"
      data-target="#saveModal"
    ></button>
    <button form="form" type="submit" class="btn btn-primary fa">Generate</button>
  </div>
</form>
<div class="form-group small">
  <label class="small">Bond Cost</label>
  <input type="text" readonly="readonly" class="form-control" value='${bondCost}'>
  <label class="small">Transfer Cost</label>
  <input type="text" readonly="readonly" class="form-control" value='${transferCost}'>
  <label class="small">Legal Cost</label>
  <input type="text" readonly="readonly" class="form-control" value='${legalCost}'>
  <label class="small">Cash Required</label>
  <input type="text" readonly="readonly" class="form-control" value='${cashRequired}'>
</div>
