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

		if (rate != currentRate) {
			currentRate = rate;
			list.push(new Event(month, "RATE_CHANGE", rate));
		}

		if (deposit != 0) {
			list.push(new Event(month, "DEPOSIT", deposit));
		}

		if (withdrawal != 0) {
			list.push(new Event(month, "WITHDRAWAL", withdrawal));
		}
		row = row.next();
	}
	return list;
}

function viewEvents(events) {
	var template = $("#events").find("li:first");
	for ( var index in events) {
		var month = events[index].month;
		var type = events[index].type;
		var value = events[index].value;
		var item = template.clone().appendTo("#events");
		item.find("#month").val(month);
		item.find("#eventType > option").html(type).val(type);
		item.find("#value").val(value);
	}
	template.hide();
}

viewEvents(getEvents())
