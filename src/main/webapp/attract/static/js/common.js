$(function(){
	//ie兼容性
	if (window.PIE) {
		$('.radius').each(function() {
		  PIE.attach(this);
		});
	}
	window.scrollTo(0,100);

	//动态添加的pop元素ie兼容
	function popIe(){
		if (window.PIE) {
			$('.pop .radius').each(function() {
			  PIE.attach(this);
			});
		}
		if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/7./i)=="7."){
			$('.pop .sub_container').each(function(){
				$(this).css({'padding-top':($(window).innerHeight()-$(this).find('.content').outerHeight())/2+'px'});
			});
		}
	}


	$(document).on('click','.menu_btn',function(e){
		$('.menu').slideToggle();
	});
	$('.table_list li').each(function(i){
		if(i%2==1){
			$(this).addClass('odd');
		}
	});
	$('select').on('change',function(){
		$(this).parent().find('.input_txt').text($(this).val());
	})
});