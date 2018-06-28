function Event(month, type, value) {
	this.month = month;
	this.type = type;
	this.value = value;
}

function getEvents() {
	var row = $("tbody > tr");
	var list = [];
	var currentRate = row.attr("data-rate");

	while (row.is("tr")) {
		var rate = row.attr("data-rate");
		var deposit = row.attr("data-deposit");
		var withdrawal = row.attr("data-withdrawal");
		var month = row.attr("data-month");
		var repayment = row.attr("data-repayment");
		var repaymentChange = row.attr("data-repayment-change");

		if (rate != currentRate) {
			currentRate = rate;
			list.push(new Event(month, "Rate change", rate));
		}

		if (deposit && deposit != 0) {
			list.push(new Event(month, "Deposit", deposit));
		}

		if (withdrawal && withdrawal != 0) {
			list.push(new Event(month, "Withdraw", withdrawal));
		}

		if (repaymentChange && repaymentChange == "true") {
			list.push(new Event(month, "Amount change", repayment));
		}
		row = row.next();
	}
	return list;
}

function viewEvents(events) {
	var template = $("#events").find("tr:first");
	for ( var index in events) {
		var month = events[index].month;
		var type = events[index].type;
		var value = events[index].value;
		var item = template.clone().appendTo("#events");
		item.find("#month").val(month);
		item.find("#eventType").val(type);
		item.find("#value").val(value);
	}
	template.hide();
}

viewEvents(getEvents())
