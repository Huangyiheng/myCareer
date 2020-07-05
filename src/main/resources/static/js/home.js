/*首页滚动时执行对应窗口的动画*/
$(function() {
	function iskeshi(ft) {
		//指定元素距离文档顶部高度
		var sTop = $(this).scrollTop(); //获取滚动条的位置
		var sh = $(window).innerHeight(); //视口高度


		var ktop = ft - sh;
		// 滚动条位置 大于 指定元素减去 视口高度时
		if(sTop > ktop && sTop < ft) { //当底部出现在视口时 触发
			return true;
		} else {
			return false;
		}
	}

	function checkAn(hh) {
		console.log(hh);
		var gjkc = $('.gjkc').offset().top;

		//console.log("gjkc:" + gjkc);
		/*设置课程体系*/
		if(iskeshi(gjkc+20)) {
			!$('.gjkc').hasClass('on') && $('.gjkc').addClass('on')
		}
		/*设置状态栏的颜色改变*/
		var sTop = $(this).scrollTop(); //获取滚动条的位置
		if(sTop>gjkc) {
			!$('.eeo-nav').hasClass('eeo-nav-in') && $('.eeo-nav').addClass('eeo-nav-in'); /*下拉时修改导航栏颜色*/
		}else{
			$('.eeo-nav').removeClass('eeo-nav-in');
		}
		/*课程体系动画*/
		var gjkc = $('.gjkc').offset().top;
		if(iskeshi(gjkc)) {
			!$('.gjkc').hasClass('on') && $('.gjkc').addClass('on');

		}
		/*师资描述动画*/
		var shizi = $('.shizi').offset().top;
		if(iskeshi(shizi)) {
			!$('.shizi').hasClass('on') && $('.shizi').addClass('on');

		}
		/*线上线下动画*/
		var dong1 = $('.dong1').offset().top;
		if(iskeshi(dong1)) {
			!$('.dong1').hasClass('on') && $('.dong1').addClass('on');

		}
		/*双师课程动画*/
		var dong2 = $('.dong2').offset().top;
		if(iskeshi(dong2)) {
			!$('.dong2').hasClass('on') && $('.dong2').addClass('on');

		}
		/*幼儿全天动画*/
		var dong3 = $('.dong3').offset().top;
		if(iskeshi(dong3)) {
			!$('.dong3').hasClass('on') && $('.dong3').addClass('on');

		}
		/*幼儿常规动画*/
		var dong4 = $('.dong4').offset().top;
		if(iskeshi(dong4)) {
			!$('.dong4').hasClass('on') && $('.dong4').addClass('on');

		}
		/*小学课程动画*/
		var dong5 = $('.dong5').offset().top;
		if(iskeshi(dong5)) {
			!$('.dong5').hasClass('on') && $('.dong5').addClass('on');

		}
		/*地球动画*/
		var feiji = $('.feiji').offset().top;
		if(iskeshi(feiji+200)) {

			$('.feiji').css("background-image","url(static/img/shouye_shizi_05.gif)");
		}
		/*体系优势动画*/
		var tixiitem = $('.tixiitem').offset().top;
		if(iskeshi(tixiitem)) {
			!$('.tixiitem').hasClass('on') && $('.tixiitem').addClass('on');

		}
		/*双师课堂*/
		var doubleclass = $('.doubleclass').offset().top+300;
		if(iskeshi(doubleclass)) {
			!$('.doubleclass').hasClass('on') && $('.doubleclass').addClass('on');

		}
		/*线上线下*/
		var otox = $('.otox').offset().top;
		if(iskeshi(otox)) {
			!$('.otox').hasClass('on') && $('.otox').addClass('on');

		}
	/*	宣传片*/
		var xuanchuan = $('.xuanchuan').offset().top;
		if(iskeshi(xuanchuan)) {
			!$('.xuanchuan').hasClass('on') && $('.xuanchuan').addClass('on');

		}





	}
	checkAn($(document).scrollTop());

	$(window).scroll(function() {
		var hh = $(window).scrollTop();
		checkAn(hh);
	});

})
