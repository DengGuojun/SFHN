//tabs 切换
$('#js-tabs .nav-tabs li').click(function(){
	var index = $(this).index();
	$(this).addClass('active').siblings().removeClass('active');
	$("#js-tabs .tabs-content .tabs-item").eq(index).removeClass('dn').siblings().addClass('dn');
})
