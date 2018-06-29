function Item(rate, deposit, withdrawal, month, endAmount) {
	this.month = month;
	this.rate = rate;
	this.deposit = deposit;
	this.withdrawal = withdrawal;
	this.endAmount = endAmount;
}

function getData() {
	var row = $("tbody > tr.forecast");
	var list = [];

	while (row.is("tr")) {
		var rate = row.attr("data-rate");
		var deposit = row.attr("data-deposit");
		var withdrawal = row.attr("data-withdrawal");
		var month = row.attr("data-month");
		var endAmount = row.attr("data-end-amount");
		list.push(new Item(rate, deposit, withdrawal, month, endAmount));
		row = row.next();
	}
	return list;
}

function getMonths() {
	var row = $("tbody > tr.forecast");
	var list = [];
	
	while (row.is("tr")) {
		var month = row.attr("data-month");
		list.push(month);
		row = row.next();
	}
	return list;
}

function getLastMonth() {
	return $("tbody > tr.forecast").last().attr("data-month");
}

function getMaxEndAmount() {
	var row = $("tbody > tr.forecast");
	var amount = 0;
	while (row.is("tr")) {
		var endAmount = row.attr("data-end-amount") * 1;
		if (endAmount > amount) {
			amount = endAmount
		}
		row = row.next();
	}
	return amount;
}

$(".fa-line-chart").click(function() {
	var data = getData();
	var graph = d3.select("#graph");
	var axis;
	var lastMonth = getLastMonth();
	var x = d3.scaleLinear()
    .domain([0, lastMonth])
    .range([50, 750]);
	axis = d3.axisBottom(x);
	axis.tickValues(d3.range(0, lastMonth, 12));
	graph.append("g")
    .attr("transform", "translate(0,280)")
    .call(axis);

	var amount = getMaxEndAmount();
	var y = d3.scaleLinear()
    .domain([0, amount])
    .range([280, 0]);
	axis = d3.axisLeft(y);
	axis.tickValues(d3.range(0, amount, amount / 20));
	graph.append("g")
    .attr("transform", "translate(50,0)")
    .call(axis);
    
	var line = d3.line().x(function(d) {
		return x(d.month);
	}).y(function(d) {
		return y(d.endAmount);
	});
	graph.append("path")
	 .attr("d", line(data))
	 .attr("stroke", "blue")
	 .attr("stroke-width", 2)
	 .attr("fill", "none");
	 $("#graphModal").modal("show");
});
