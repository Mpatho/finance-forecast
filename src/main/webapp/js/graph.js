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

function getEndAmounts() {
	var row = $("tbody > tr.forecast");
	var list = [];
	
	while (row.is("tr")) {
		var endAmount = row.attr("data-end-amount");
		list.push(endAmount);
		row = row.next();
	}
	return list;
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

	var y = d3.scaleLinear()
    .domain(d3.extent(data, function(d) { return d.endAmount; }))
    .range([280, 0]);
	axis = d3.axisLeft(y);
	axis.tickValues(d3.range(0, d3.max(getEndAmounts()), d3.max(getEndAmounts()) / 20))
	graph.append("g")
    .attr("transform", "translate(50,0)")
    .call(axis);
    
	var line = d3.line().x(function(d) {
		return x(d.month);
	}).y(function(d) {
		return y(d.endAmount);
	});
// graph.data(data);
	 graph.append("path")
	 .attr("d", line(data))
	 .attr("stroke", "blue")
	 .attr("stroke-width", 2)
	 .attr("fill", "none");
	 $("#graphModal").modal("show");
});
