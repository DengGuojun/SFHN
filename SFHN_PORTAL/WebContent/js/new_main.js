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
        "applywayInput":"#applyway-input",
        "personInput":"#person-input",
        "farmInput":"#farm-input",
        "farmgradeInput":"#farmgrade-input",
        "farmtypeInput":"#farmtype-input",
        "workInput":"#work-input",
        "teckgradeInput":"#teckgrade-input"
    }

    var inputEles = $.extend({},options)
    $(".info-table").find("li").on("click",function(){


        var cls = $(this)[0].id;
        var layerCls = "#layer-" +cls  ;
        currentActiveLayer = $(layerCls);
        if(currentActiveLayer.length > 0){
            window.step ++ ;

            //$(".footer-50").show();
            $(".footer-all").hide();
            if($(layerCls).hasClass("hide-1")){
                $(layerCls).removeClass("hide-1").addClass("show-1");
            }
            if(currentActiveLayer[0].id == "layer-experience" || currentActiveLayer[0].id == "layer-certification"|| currentActiveLayer[0].id == "layer-farm"){

                $(".footer-all").show();
            }

            (function(layer){
                clickAction(layer);
            })(currentActiveLayer)

        }

    });

    function clickAction(layer,opts){

        layer.find('li').click(function(){
            $(".footer-all").hide();
            //$('.footer-50').hide();
            if(layer.length > 0){
                if(layer.hasClass("show-1")){
                    //如果当前层设置自动掩藏,则执行这段
                    if(!layer.hasClass("no-auto")) {
                        setTimeout(function () {
                            if(layer[0].id == "layer-farmtype" ||layer[0].id == "layer-teckgrade"|| layer[0].id == "layer-farmgrade" || layer[0].id == "layer-grade" || layer[0].id == "layer-time"){
                                window.step  = 2;
                            }
                            else{
                                window.step  = 1;
                            }
                            layer.removeClass("show-1").addClass("hide-1");
                            $(".footer-all").show();

                        }, 500);
                    }
                    else{
                        $(".footer-all").show();
                    }

                }

                //如果当前点击的li有子窗口并且ID不是"认定时间"
                if($(this).hasClass("hasSecondChild") && $(this).attr("id") != "time"&&$(this).attr("id") != "farm"){
                    $(".footer-all").hide();
                }

                //如果是动态生成
                if($(this).hasClass("dynamic")){
                    //点中的li的值
                    var value = $(this).find("input").val();
                    var name = $(this).data("name");
                    if(name == 'works'){
                    	$('#jobType').val(value);
                    }
                    //子层级
                    var childLayer = $("#layer-" + name);
                    var data;
                    var options;
                    var html = "";
                    if(childLayer.length > 0){
                        if(childLayer.hasClass("hide-1")){
                            childLayer.removeClass("hide-1").addClass("show-1");
                        }
                        //如果第二层窗口是开的,则关闭.
                        if(layer.hasClass("show-1") && childLayer.hasClass("back-root")){
                            window.step = 1;
                            layer.removeClass("show-1").addClass("hide-1")
                        }
                        data = dynamidData()[name];
                        options = data[value];
                        for(var key in options){
                            html += " <li> <input type='radio' name='" + name + value + "'   value='" + key + "'> <label>"+ options[key] +"</label> </li>";
                        }
                        childLayer.find("ul").html(html);
                        clickAction(childLayer,options);
                    }
                    return;
                }

                chooseOption(layer,opts);

            }
          
        });
    }


    function chooseOption(layer,ops){
        var inp;
        var inpVal;
        var layerId;
        var fill = new Fill();

        inp = layer.find("input");
        inp.each(function(){
            if($(this).is(':checked')){
                inpVal = $(this).val();
                layerId = layer[0].id;

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
                    //申请方式
                    case "layer-applyway":
                        fill.dic["applyway"] = {'input':inputEles.applywayInput,options:applyway};
                        fill.init("applyway",inpVal);
                        break;
                    //人员类别
                    case "layer-person":
                        fill.dic["person"] = {'input':inputEles.personInput,options:person};
                        fill.init("person",inpVal);
                        //如果是家庭农场经营类型
                        if(inpVal == "FAMILY_FARMER"){
                            reset();
                            $("#farm").show();
                            $(".address_add_pca").show();
                            $("#industry").show();
                            $("#income").show();
                        }
                        //农业社会化服务组织能手
                        else if(inpVal == "AGRICULTURAL_SERVICE_ORGANIZER"){
                            reset();
                            $("#worktype").show();
                            $(".jobAddress").show();
                            $(".jobAddress").find("label").html(" <i class='star'>*</i>工作地点:");
                            $("#size").show();
                            $("#worktime").show();
                            $("#yearincome").show();
                        }
                        else{
                            reset();
                            $(".address_add_pca").show();
                            $("#industry").show();
                            $("#income").show();
                        }


                        break;
                    //农场
                    case "layer-farm":
                        fill.dic["farm"] = {'input':inputEles.farmInput,options:farm};
                        fill.init("farm",inpVal);
                        break;
                    //农场级别
                    case "layer-farmgrade":
                        fill.dic["farmgrade"] = {'input':inputEles.farmgradeInput,options:farmgrade};
                        fill.init("farmgrade",inpVal);
                        break;
                    //农场类型
                    case "layer-farmtype":
                        fill.dic["farmtype"] = {'input':inputEles.farmtypeInput,options:farm};
                        fill.init("farmtype",inpVal);
                        break;
                    //工作岗位
                    case "layer-works":
                        fill.dic["work"] = {'input':inputEles.workInput,options:ops};
                        fill.init("work",inpVal);
                        $('#jobId').val(inpVal);
                        break;
                    //农民技术等级
                    case "layer-teckgrade":
                        fill.dic["teckgrade"] = {'input':inputEles.teckgradeInput,options:teckgrade};
                        fill.init("teckgrade",inpVal);
                        break;
                    default:
                        break;
                }
            }
        })
    }

    function reset(){
        $("#farm").hide();
        $(".address_add_pca").hide();
        $("#industry").hide();
        $("#income").hide();
        $("#worktype").hide();
        $("#size").hide();
        $("#worktime").hide();
        $("#yearincome").hide();
    }

    //保存操作



    var Fill = function (){
        this.dic ={'sex':{'input':inputEles.sexInput,options:{'1':'男','2':'女'}},
            'edu':{'input':inputEles.eduInput,options:edu},
            'personal':{'input':inputEles.personalInput,options:personal},
            'area':{'input':inputEles.arealInput,options:area},
            'economic':{'input':inputEles.economicInput,options:economic},
            'nation':{'input':inputEles.nationInput,options:nation},
            'political':{'input':inputEles.politicalInput,options:political},
            'work':{'input':inputEles.workInput,options:work},
            'grade':{'input':inputEles.gradeInput,options:grade},
            'work2':{'input':inputEles.workInput2,options:work2},
            'grade2':{'input':inputEles.gradeInput2,options:grade2},
            'companytype':{'input':inputEles.companytypeInput,options:companytype},
            'teckgrade':{'input':inputEles.teckgradeInput,options:teckgrade},
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