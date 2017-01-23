//点击显示菜单
$('.u-menu').click(function(){
	if($('.g-header ul').css('display') == 'none'){
		$('.g-header ul').slideDown(400);
		$('#ul-mask').show();
	}else{
		$('.g-header ul').slideUp(400);
		$('#ul-mask').hide();
	}
})

//选项
$('.select-box select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-box').find('.select-txt').text(val);
});

var totalPage = 18;   //虚拟数据

$('.page-num select').change(function(){
	var val = $(this).children('option:selected').val();
	$(this).parents('.page-num').find('.page-txt').text(val+"／"+ totalPage);
})