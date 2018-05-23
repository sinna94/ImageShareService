function layer_popup(){
	document.title = "업로드";
	
	var $el = $('.upload-layer');        //레이어의 id를 $el 변수에 저장
    var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

    isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

    var left = ( $(window).scrollLeft() + ( $(window).width() - $('.upload-layer').width()) / 2 );
    var top = ( $(window).scrollTop() + ( $(window).height() - $('.upload-layer').height()) / 2 );

    //$('.upload-layer').css({'left':left,'top':top, 'position':'absolute'});	
}

function addPreview(input){
	if(input[0].files){
		for(var fileindex=0;fileindex < input[0].files.length; fileindex++){
			var file = input[0].files[fileindex];
			var reader = new FileReader();
			
			reader.onload = function(img){
				$(".preview").css({'background-image': URL(img.target.result)});
				//$(".preview").append("<img src=\"" + img.target.result + "\"" + "class='img-responsive image'" + "\/>");
			};
			reader.readAsDataURL(file);
		}
	}
	else {
		alert("incalid file input");
	}
}