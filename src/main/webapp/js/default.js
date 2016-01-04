
function slideToggle()
{
	$(".dropdown img.flag").addClass("flagvisibility");

	$(".dropdown dt a").click(function() {
	    $(".dropdown dd ul").slideToggle();
	});
	            
	$(".dropdown dd ul li a").click(function() {
	    var text = $(this).html();
	    $(".dropdown dt a span").html(text);
	    $(".dropdown dd ul").hide();
	});
	            
	function getSelectedValue(id) {
	    return $("#" + id).find("dt a span.value").html();
	}

	$(document).bind('click', function(e) {
	    var $clicked = $(e.target);
	    if (! $clicked.parents().hasClass("dropdown"))
	        $(".dropdown dd ul").hide();
	});


	$("#flagSwitcher").click(function() {
	    $(".dropdown img.flag").toggleClass("flagvisibility");
	});
}
