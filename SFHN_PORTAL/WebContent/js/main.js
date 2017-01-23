/**
 * Created by jimmy on 16/5/29.
 */
$(function(){
    var currentActiveLayer;
    var options = {
        //"layerPhoto": "#layer-photo",
        "layerSex": "#layer-sex",
        "sexInput":"#sex-input",
        "eduInput":"#edu-input",
        "personalInput":"#personal-input",
        "arealInput":"#area-input",
        "economicInput":"#economic-input",
        "nationInput":"#nation-input",
        "politicalInput":"#political-input",
        "workInput":"#work-input",
        "gradeInput":"#grade-input",
        "workInput2":"#work2-input",
        "gradeInput2":"#grade2-input",
        "companytypeInput":"#companytype-input",
        "breedInput":"#breed-input",
    }

    var layer = $.extend({},options)
    $(".info-table").find("li").on("click",function(){
        var cls = $(this)[0].id;
        var layerCls = "#layer-" +cls  ;
        currentActiveLayer = $(layerCls);
        if(currentActiveLayer.length > 0){
            //$(".footer-50").show();
            $(".footer-all").hide();
            if($(layerCls).hasClass("hide-1")){
                $(layerCls).removeClass("hide-1").addClass("show-1");
            }
            currentActiveLayer.find('li').on('click',function(){

                $(".footer-all").hide();
                //$('.footer-50').hide();
                var inp;
                var inpVal;
                var layerId;
                var fill = new Fill();
                if(currentActiveLayer.length > 0){
                    if(currentActiveLayer.hasClass("show-1")){
                        setTimeout(function (){currentActiveLayer.removeClass("show-1").addClass("hide-1");$(".footer-all").show();},500);
                        function aa(currentActiveLayer){
                            currentActiveLayer.removeClass("show-1").addClass("hide-1");
                        }
                    }
                    inp = currentActiveLayer.find("input");
                    inp.each(function(){
                        if($(this).is(':checked')){
                            inpVal = $(this).val();
                            layerId = currentActiveLayer[0].id;

                            switch (layerId){
                                //性别选择
                                case "layer-sex":
                                    fill.init("sex",inpVal);
                                    break;
                                //教育选择
                                case "layer-edu":
                                    fill.init("edu",inpVal);
                                    break;
                                //人员类别选择
                                case "layer-personal":
                                    fill.init("personal",inpVal);
                                    break;
                                //地区选择
                                case "layer-area-type":
                                    fill.init("area",inpVal);
                                    break;
                                //经济区域选择
                                case "layer-economic-type":
                                    fill.init("economic",inpVal);
                                    break;
                                //民族选择
                                case "layer-nation":
                                    fill.init("nation",inpVal);
                                    break;
                                //政治面貌
                                case "layer-political":
                                    fill.init("political",inpVal);
                                    break;
                                //工种
                                case "layer-work":
                                    fill.init("work",inpVal);
                                    break;
                                //级别
                                case "layer-grade":
                                    fill.init("grade",inpVal);
                                    break;
                                //工种2
                                case "layer-work2":
                                    fill.init("work2",inpVal);
                                    break;
                                //级别2
                                case "layer-grade2":
                                    fill.init("grade2",inpVal);
                                    break;
                                //单位类别
                                case "layer-companytype":
                                    fill.init("companytype",inpVal);
                                    break;
                                //培育类型选择
                                case "layer-breed":
                                    fill.init("breed",inpVal);
                                    break;
                                default:
                                    break;
                            }
                        }
                    })

                }
            });
        }


    });
    //取消操作z
    /*$('.footer-w50l').on('click',function(){
        $('.footer-all').show();
        $('.footer-50').hide();
        if(currentActiveLayer.length > 0) {
            if (currentActiveLayer.hasClass("show-1")) {
                currentActiveLayer.removeClass("show-1").addClass("hide-1");
            }
        }

    })*/

    //保存操作



    var Fill = function (){
    	this.dic ={'sex':{'input':layer.sexInput,options:{'1':'男','2':'女'}},
                'edu':{'input':layer.eduInput,options:edu},
                'personal':{'input':layer.personalInput,options:personal},
                 'area':{'input':layer.arealInput,options:area},
                'economic':{'input':layer.economicInput,options:economic},
                 'nation':{'input':layer.nationInput,options:nation},
                 'political':{'input':layer.politicalInput,options:political},
                 'work':{'input':layer.workInput,options:work},
                 'grade':{'input':layer.gradeInput,options:grade},
                 'work2':{'input':layer.workInput2,options:work2},
                 'grade2':{'input':layer.gradeInput2,options:grade2},
                 'companytype':{'input':layer.companytypeInput,options:companytype},
                 'breed':{'input':layer.breedInput,options:breed},
     };
    };


    Fill.prototype.init = function(type,val) {
        $(this.dic[type]['input']).val(this.dic[type]['options'][val]);
        $(this.dic[type]['input']).css('color','#444');
    }

function aa(currentActiveLayer){
    currentActiveLayer.removeClass("show-1").addClass("hide-1");
}
})