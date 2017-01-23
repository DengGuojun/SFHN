function strLength(str) {
	return str.replace(/[^\x00-\xff]/gi, "xx").length;
}

function strTrim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

function clearInput(inputId) {
	jQuery("#" + inputId).val("");
}

function isHtmlStr(str) {
	re = /<\/?[^>]+>/;
	return re.test(str);
}

function isMailStr(str) {
	var re = /^([A-Za-z0-9_|-]+[.]*[A-Za-z0-9_|-]+)+@[A-Za-z0-9|-]+([.][A-Za-z0-9|-]+)*[.][A-Za-z0-9]+$/ig;
	return re.test(str);
}

function isNumberStr(str) {
	var re = /^-?\d+(\.\d+)?$/;
	return re.test(str);
}

function isDigitStr(str) {
	// 是否为数字
	var re = /^(\d)+$/;
	return re.test(str);
}

function isCodeStr(str) {
	// 是否为A-Z字母
	var re = /^[a-zA-Z0-9_]+?$/;
	return re.test(str);
}

function isCharStr(str) {
	// 是否全部为字符 不包含汉字
	var re = /^[a-zA-Z]+?$/;
	return re.test(str);
}

function isDecimal(str) {
	// 是否为小数
	var re = /^-?\d+(\.\d+)?$/g;
	return re.test(str);
}

function isDouble(str) {
	// 是否为数字或者小数
	return isDecimal(str) || isDigitStr(str);
}

function isIpAddr(str) {
	// 是否IP地址
	var re = /^([1-9]|[1-9]\d|1\d{2}|2[0-1]\d|22[0-3])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}$/;
	return re.test(str);
}

function isChinese(str) {
	// 是否为中文
	var re = /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0])*$/gi;
	return re.test(str);
}

function isDate(str) {
	// 是否为日期 yyyy-MM-dd HH:mm:ss
	var re = /^((\d{2}(([02468][048])|([13579][26]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|([1-2][0-9])))))|(\d{2}(([02468][1235679])|([13579][01345789]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\s(((0?[0-9])|([1-2][0-3]))\:([0-5]?[0-9])((\s)|(\:([0-5]?[0-9])))))?$/;
	return re.test(str);
}

function isMobile(str) {
	// 是否为手机号码
	var re1 = /^13\d{5,9}$/;
	var re2 = /^14\d{5,9}$/;
	var re3 = /^15\d{5,9}$/;
	var re4 = /^0\d{10,11}$/;
	return re1.test(str) || re2.test(str) || re3.test(str) || re4.test(str);
}

function isTel(str) {
	// 是否为电话号码
	var re = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	return re.test(str);
}

function isUniCard(str) {
	// 是否为15位或18位身份证号码
	var re1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
	var re2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/;
	return re1.test(str) || re2.test(str);
}

function checkForm(formId) {
	var msg = "";
	var result = true;

	// 验证具有checkStr属性的表单
	jQuery("#" + formId + " :input[checkStr]").each(function() {
		msg = checkData(jQuery(this).attr("checkStr"), this);
		if (msg != "") {
			alert(msg);
			this.focus();
			result = false;
			return false;
		}
	});

	return result;
}

function checkData(checkStr, objElement) {
	var msg = "";
	var value = strTrim(objElement.value);

	if (checkStr == "") {
		return "";
	}

	// 分割标签中的需要验证的值
	var checkArray = checkStr.split(";");

	var title = "本项";
	if (checkArray[0] != "") {
		title = checkArray[0];
	}

	// 录入项不能为空
	if (checkArray[2] == "true") {
		if (value == "") {
			if(checkArray[1] == "file"){
				return title + "必须上传";
			}else{
				return title + "必须填写";
			}
			
		}
	}

	// 检查录入长度
	if (checkArray[3] != "") {
		if (strLength(value) <= parseInt(checkArray[3])) {
			return title + "最少需要录入" + checkArray[3] + "个字符（一个汉字等于2个字符）";
		}
	}

	if (checkArray[4] != "") {
		if (strLength(value) > parseInt(checkArray[4])) {
			return title + "最大只能录入" + checkArray[4] + "个字符（一个汉字等于2个字符）";
		}
	}

	if (strTrim(value) != "") {
		switch (checkArray[1].toLowerCase()) {
		case "num": // 数字，包含小数点
			if (!isNumberStr(value))
				return title + "只能录入数字！"
			break;
		case "digit": // 数字
			if (!isDigitStr(value))
				return title + "只能录入数字！"
			break;
		case "code": // 字符
			if (!isCodeStr(value))
				return title + "只能录入字母、数字、下划线！"
			break;
		case "character": // 字母
			if (!isCharStr(value))
				return title + "只能录入字母！"
			break;
		case "mail":
			if (!isMailStr(value))
				return title + "只能录入正确的电子信箱地址！";
			break;
		case "txt": // 纯文本，验证是否有录入HTML
			if (isHtmlStr(value))
				return title + "录入文字中不能包含HTML代码";
			break;
		case "mobile": // 手机号码，验证是否手机号码
			if (!isMobile(value))
				return title + "手机号码格式不正确！";
			break;
		case "tel": // 电话号码，验证是否电话号码
			if (!isTel(value))
				return title + "电话号码格式不正确！";
			break;
		case "html": // 允许录入HTML代码
			break;
		}
	}

	return msg;
}

function goPage(formId, pageNum) {
	document.forms[formId].pageNum.value = pageNum;
	document.forms[formId].submit();
}

function goInputPage(formId, maxNum) {
	var pageNum = jQuery("#goPageNum").val();
	if (isDigitStr(pageNum)) {
		if (parseInt(pageNum) > 0 && parseInt(pageNum) <= maxNum) {
			goPage(formId, pageNum);
		} else {
			alert("翻页大于最大页数！");
			jQuery("#goPageNum").focus();
		}
	} else {
		alert("翻页只能输入正整数！");
		jQuery("#goPageNum").focus();
	}
}

function tabChange(className, dom) {// 内容页tab跳转
	$(className + ' ' + dom).each(function() {
		var fileName = location.pathname;
		var urlName = $(this).attr('href');
		urlName = ((urlName.split('?'))[0]).split('/');
		urlName = urlName[urlName.length - 1];
		fileName = fileName.split('/');
		fileName = fileName[fileName.length - 1];
		if (urlName == fileName)
			$(this).addClass('active');
	});
}

function selectAllCheckbox(checkboxName) {// 全选
	jQuery("[name='" + checkboxName + "']").prop("checked", true);
}

function removeAllCheckbox(checkboxName) {// 取消全选
	jQuery("[name='" + checkboxName + "']").prop("checked",false);
}

function reverseSelectCheckbox(checkboxName) {// 反选
	jQuery("[name='" + checkboxName + "']").each(function() {
		if (jQuery(this).prop('checked')) {
			jQuery(this).prop("checked",false);
		} else {
			jQuery(this).prop("checked", true);
		}
	});
}

function changeChiefCheckbox(chiefCheckboxName, elementCheckboxName) {
	if (jQuery(chiefCheckboxName).prop('checked')) {
		selectAllCheckbox(elementCheckboxName);
	} else {
		removeAllCheckbox(elementCheckboxName);
	}
}

function changeElementCheckbox(chiefCheckboxName, elementCheckboxName) {
	var flag = true;
	jQuery("[name='" + elementCheckboxName + "']").each(function() {
		if (!jQuery(this).attr("checked")) {
			flag = false;
		}
	});
	if (flag) {
		jQuery("[name='" + chiefCheckboxName + "']").attr("checked", "true");
	} else {
		jQuery("[name='" + chiefCheckboxName + "']").removeAttr("checked");
	}
}

function getCheckboxValue(checkboxName, regex) {// 取值
	var result = "";
	jQuery("input[name='" + checkboxName + "']:checked").each(function() {
		result += (result.length > 0 ? regex : "") + jQuery(this).val();
	});
	return result;
}
