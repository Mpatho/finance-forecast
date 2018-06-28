var currentEvent;

function Event(month, type, value) {
	this.month = month;
	this.type = type;
	this.value = value;
}

function getEvents() {
	var row = $("tbody > tr.forecast");
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
		item.find("#month").val(month).parent().append(month);
		item.find("#eventType").val(type).parent().append(type);
		item.find("#value").val(value).parent().append(value);
	}
	template.hide();
}

viewEvents(getEvents())

$('.forecast , .event').css('cursor', 'pointer');

$(".forecast").click(function() {
	var that = $(this);
	var month = that.attr("data-month");
	currentEvent = null;
	$(".modal-body input#month").val(month);
	$(".modal-body [name=eventType]").val(null);
	$(".modal-body [name=eventValue]").val(null);
	$(".modal-footer #delete").hide();
});

$(".modal-footer > #delete").click(function() {
	$(currentEvent).remove();
	$(".modal-body [name=eventMonth]").val(null);
	$(".modal-body [name=eventType]").val(null);
	$(".modal-body [name=eventValue]").val(null);
});

$(".modal-footer > #generate").click(function() {
	$(currentEvent).remove();
});

$(".event").click(function() {
	currentEvent = this;
	$(".modal-footer #delete").show();
	var that = $(this);
	var month = that.find("[name=eventMonth]").val();
	var type = that.find("[name=eventType]").val();
	var value = that.find("[name=eventValue]").val();
	$(".modal-body [name=eventMonth]").val(month);
	$(".modal-body [name=eventType]").val(type);
	$(".modal-body [name=eventValue]").val(value);
});
