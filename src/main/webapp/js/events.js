function Event(month, type, value) {
	this.month = month;
	this.type = type;
	this.value = value;
}

function getEvents() {
	var row = $("tbody > tr");
	var list = [];
	var currentRate = row.attr("data-rate");
	var currentAmount = row.attr("data-amount-change");

	while (row.is("tr")) {
		var rate = row.attr("data-rate");
		var deposit = row.attr("data-deposit");
		var withdrawal = row.attr("data-withdrawal");
		var month = row.attr("data-month");
		var amount_change = row.attr("data-amount-change");
		
		if (rate != currentRate) {
			currentRate = rate;
			list.push(new Event(month, "RATE_CHANGE", rate));
		}

		if (deposit	&& deposit != 0) {
			list.push(new Event(month, "DEPOSIT", deposit));
		}

		if (withdrawal && withdrawal != 0) {
			list.push(new Event(month, "WITHDRAW", withdrawal));
		}
		
		if (amount_change && amount_change != currentAmount) {
			currentAmount = amount_change;
			list.push(new Event(month, "AMOUNT_CHANGE", amount_change));
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
