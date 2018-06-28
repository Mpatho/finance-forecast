var type = $("#type").attr("data-type");
$("[value=" + type + "]").attr("selected", "selected");

function toggleAmountChange() {
	if ($("[value=fixed]").is(":selected") || $("type").attr("data-type") == "") {
		$("[value='Amount change']").hide();
	} else if ($("[value=monthly]").is(":selected")) {
		$("[value='Amount change']").show();
	}
}

$("select").change(function() {
	toggleAmountChange();
});

toggleAmountChange();
