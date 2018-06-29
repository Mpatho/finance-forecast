<form action="forecast" id="form" class="small" method="post">
  <input type="hidden" name="id" value="${id}" />
  <div class="form-group">
    <label for="type">Type</label>
    <div class="input-group">
      <select class="form-control" name="type" id="type" data-type="${type}">
        <option value="fixed" selected>fixed</option>
        <option value="monthly">monthly</option>
      </select>
    </div>
    <label for="amount">Amount</label>
    <div class="input-group">
      <span class="input-group-text">R</span>
      <input type="number" min="0" step="0.01" form="form" class="form-control" name="amount"
        id="amount" value='${amount}' placeholder="0.0"
      >
    </div>
    <label for="rate">Interest Rate</label>
    <div class="input-group">
      <input type="number" min="0" step="0.01" max="100" form="form" class="form-control" id="rate"
        name="rate" value='${rate}' placeholder="0.0"
      >
      <div class="input-group-append">
        <span class="input-group-text" id="basic-addon2">%</span>
      </div>
    </div>
    <label for="months">Investment Term</label>
    <div class="input-group">
      <input type="number" min="1" form="form" class="form-control" id="months" name="months"
        value='${months}' placeholder="0"
      >
      <div class="input-group-append">
        <span class="input-group-text" id="basic-addon2">months</span>
      </div>
    </div>
  </div>
  <button type="button" class="btn btn-primary fa fa-save" data-toggle="modal"
    data-target="#saveModal"
  ></button>
  <button form="form" type="submit" class="btn btn-primary fa">Generate</button>
  <button type="button" class="btn btn-primary fa fa-line-chart"></button>
</form>