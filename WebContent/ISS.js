function test() {
    if ($(".login").css("display") != "none") {
    	$(".login").hide();
        $(".signin").show();
    } else {
    	$(".login").show();
        $(".signin").hide();
    }
};