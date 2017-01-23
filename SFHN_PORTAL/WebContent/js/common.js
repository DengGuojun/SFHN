/**
 * Created by XQL on 5/28/16.
 */
$(".qualifications li input[name='hasNoCertification']").on('click',function(){
    if($(this).prop("checked")){
        $(".qualifications li input").removeAttr("checked");
        $('.qualifications li').eq(0).find('input').prop("checked",'true');
    }
});
$('.qualifications li input[name="hasNewTypeTrainingCertification"],.qualifications li input[name="hasNewTypeCertification"]').on('click',function(){
    if($(this).prop("checked")){
        $(".qualifications li input[name='hasNoCertification']").removeAttr("checked");
    }
});
$('.show-1 .councity-book').on('click',function(){
    $('.show-1,.footer-all').hide();
    $('.hide-1,.footer-50').show();
    $("#nationalCertificationId1 option").eq(0).prop("selected",true);
    $('#nationalCertificationGrade1').val('');
    $('#nationalCertificationGrade1').val('');
});
$('.footer-w50r').on('click',function(){
    var re = /^[1-9]+[0-9]*]*$/;
    var number = $('#nationalCertificationGrade1').val();
    if (!re.test(number))
    {
        alert("请输入数字");
        return false;
    }else {
        var option_text = $("#nationalCertificationId1 option:selected").text();
        var grade_text = $("input[name='nationalCertificationGrade1']").val();
        html = '<li><div><p class="new_p"><span class="option_text">' + option_text + '</span><span class="grade_text">' + grade_text + '</span></p><span class="cancel"></span></div></li>';
        $('.qualifications').append(html);
        $('.show-1,.footer-all').show();
        $('.hide-1,.footer-50').hide();
        var numAdd = $('.new_p').length;
        if (numAdd >= 2) {
            $('.councity-book').hide();
        }
    }
});
$('div').delegate('.cancel','click',function(){
    event.stopPropagation();
    $(this).parents('li').remove();
    var numAdd = $('.new_p').length;
    if(numAdd < 2){
        $('.councity-book').show();
    }
});
$('div').delegate('.new_p','click',function(){
    $('.show-1,.footer-all').hide();
    $('.hide-1,.footer-50').show();
    var text=$(this).find('.option_text').text();
    var grade_t = $(this).find('.grade_text').text();
    var o_um = $("#nationalCertificationId1 option").length;
    $(this).parents('li').remove();
    for(var i = 0;i < o_um; i++)
    {
        var val = $("#nationalCertificationId1 option").eq(i).text();
        if (val == text) {
            $("#nationalCertificationId1 option").eq(i).prop("selected",true);
            $('#nationalCertificationGrade1').val(grade_t);
            return false;
        }
    }

});


function ajaxAuthCheck(data){
	var code = data.code;
	if(code!='403'){
		return data;
	}else{
		//没有登陆
		window.location = data.message;
	}
}