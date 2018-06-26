var type = $("#type").attr("data-type");
$("[value=" + type + "]").attr("selected", "selected");

$("select").change(function() {
	if($("[value=fixed]").is(":selected")) {
		$("[value=AMOUNT_CHANGE]").hide();
	} else if($("[value=monthly]").is(":selected")) {
		$("[value=AMOUNT_CHANGE]").show();
	}
		
	
});
	
