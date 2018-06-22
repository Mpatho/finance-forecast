$(".event").click(function() {
	var that = $(this);
	var month = that.attr("data-month");
	$(".modal-body input#month").val(month);
});
