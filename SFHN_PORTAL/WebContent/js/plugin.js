/**
 * Created by jimmy on 16/5/30.
 */
/**
 * Created by jimmy on 15/12/16.
 */
function _rollBigPic(t, e, i, o, n) {
    var s = parseInt($(t).css("margin-left"));
    if (s % o == 0) {
        var a = -(s / o);
        "right" == e ? s > (bigImgNum - 1) * -o && (s -= o, a += 1) : "left" == e ? -1 > s && (s += o, a -= 1) : "index" == e && (s = -n * o, a = n), $(t).animate({marginLeft: s + "px"}), $(i).removeClass("active"), $(i + ":eq(" + a + ")").addClass("active")
    }
}
var trendyCall = function (t) {
    if (!t)return null;
    if ("object" != typeof t)return null;
    if (!("command"in t))return t;
    if (0 == t.command.indexOf("userMessage") && te$.business.login.showUserMessage(t), 0 == t.command.indexOf("addCart") && te$.business.cart.afterAdd(t), 0 == t.command.indexOf("getCartInfo") && te$.business.cart.updateCartNum(), 0 == t.command.indexOf("deleteCartItem") && ("mobile"in te$ ? te$.mobile.cart.afterRemove(t) : te$.business.cart.afterRemove(t)), 0 == t.command.indexOf("modifyCart") && ("mobile"in te$ ? te$.mobile.cart.afterModify(t) : te$.business.cart.afterModify(t)), 0 == t.command.indexOf("cancelOrder") && ("mobile"in te$ ? te$.mobile.cart.afterCancelOrder(t) : te$.business.cart.afterCancelOrder(t)), 0 == t.command.indexOf("removeFavorite") && ("mobile"in te$ ? te$.mobile.favorite.afterRemove(t) : te$.business.favorite.afterRemove(t)), 0 == t.command.indexOf("addFavorite") && te$.business.favorite.afterAdd(t), 0 == t.command.indexOf("getProInfo") && te$.business.stock.reset(t), 0 == t.command.indexOf("myTopOrder") && te$.business.login.showMyNewOrder(t), 0 == t.command.indexOf("showSpu") && te$.business.cart.rechooseShow(t), 0 == t.command.indexOf("removeMessage") && te$.message.removeItem(t), 0 == t.command.indexOf("userScore") && ("mobile"in te$ ? te$.mobile.showUserScore(t) : te$.business.login.showUserScore(t)), 0 == t.command.indexOf("reflashCart") && window.location.href.indexOf("cart.do") >= 0 && (-1 == t.code ? te$.ui.msgBox.show("操作失败，请稍候重试", [["确定", function () {
            window.location.reload()
        }]]) : window.location.reload()), 0 == t.command.indexOf("pointExchange") && (1 == t.code ? te$.ui.msgBox.show("兑换成功，请到我的优惠券查看兑换详情") : t.message ? te$.ui.msgBox.show(t.message) : te$.ui.msgBox.show("兑换未成功，可能因为积分不足")), 0 == t.command.indexOf("couponVerify")) {
        if (window.location.href.indexOf("confirm.do") >= 0 && 1 == t.code && 1 == t.content.length)return void("mobile"in te$ ? te$.mobile.cart.useCouponCode(t.content[0].couponCode) : te$.business.cart.useCouponCode(t.content[0].couponCode));
        te$.ui.msgBox.show("优惠码未能通过有效性验证，请检查是否输入错误。")
    }
    0 == t.command.indexOf("getNewInItems") && te$.business.login.showNewInItems(t), 0 == t.command.indexOf("userCouponNum") && te$.business.login.showUserCouponNum(t), 0 == t.command.indexOf("userVipStatus") && ("mobile"in te$ ? te$.mobile.showVipStatus(t) : te$.business.login.showVipStatus(t))
}, t$ = function (t) {
    var e = "string" == typeof t ? document.getElementById(t) : t;
    return null != e ? e : null
}, showInfo = function (t) {
    var e = "false^^^^G^^";
    "object" == typeof t && ("true" == t.isLogon ? (te$.business.user.userName = t.userLoginId, te$.business.user.userType = "A", te$.business.user.logonId = t.userId, te$.business.user.userLevel = "B", te$.business.user.userId = t.userId, te$.business.user.hasLogin = !0, te$.business.user.shortName = t.userLoginId.length > 7 ? t.userLoginId.substr(0, 6) + ".." : t.userLoginId, te$.business.login.loginTime = 0, e = "true^" + te$.business.user.logonId + "^" + te$.business.user.userName + "^" + te$.business.user.userLevel + "^" + te$.business.user.userType + "^" + te$.business.user.logonId + "^" + te$.business.user.shortName, te$.cookie("client_info", e, {
        domain: te$.domainName,
        path: "/"
    }), te$.cookie("sui", t.userId, {
        domain: te$.domainName,
        path: "/"
    })) : te$.cookie("client_info", e, {
        domain: te$.domainName,
        path: "/"
    })), te$.business.login.showRelateInfo(), te$.business.cart.fill(!0)
}, te$ = te$ || {};
te$.stringToJson = function (t) {
    return JSON ? JSON.parse(t) : new Function("return " + t)()
}, te$.regVerify = {
    loginName: {
        reg: /(^([-_A-Za-z0-9\.]+)@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$)|(^1[3|4|5|8|7][0-9]\d{8,8}$)/,
        tip: "请输入正确的邮箱或者手机号码"
    },
    passwordLength: {reg: /^\w{6,16}$|^\w{32}$/, tip: "密码需6~16位"},
    password: {reg: /^\w{6,16}$|^\w{32}$/, tip: "密码需6~16位"},
    kaptcha: {reg: /^[a-zA-Z0-9]{4}$/, tip: "请填写4位验证码"},
    notNull: {reg: /[\w\W]+/, tip: "不能为空"},
    name: {reg: /[\w\W]+/, tip: "请填写姓名"},
    address: {reg: /[\w\W]+/, tip: "请输入具体地址"},
    city: {reg: /[\w\W]+/, tip: "请选择省市区县"},
    email: {reg: /^([-_A-Za-z0-9\.]+)@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/, tip: "请输入正确的邮箱地址"},
    url: {reg: /^((http:[\/][\/])?\w+([.]\w+|[\/]\w*)*)?$/, tip: "请输入正确的url地址"},
    english: {reg: /^[A-Za-z]+$/, tip: "只允许大小写英文"},
    chinese: {reg: /^[\u4e00-\u9fa5]+$/, tip: "只允许中文字符"},
    number: {reg: /^[0-9]+$/, tip: "只允许数字"},
    zip: {reg: /^[0-9]{6}$/, tip: "请填写6位邮政编码"},
    mobile: {reg: /^1[3|4|5|8|7][0-9]\d{8,8}$/, tip: "手机号码格式不正确"},
    phone: {reg: /^0([1-9]{2,3}-)?[0-9]{7,8}$/, tip: "座机号码格式不正确"},
    date: {reg: /^(\d+)-(\d{1,2})-(\d{1,2})$/, tip: "请输入正确的日期格式"},
    shortTime: {reg: /^(\d{1,2}):(\d{1,2})$/, tip: "请输入正确的时间格式"},
    longTime: {reg: /^(\d{1,2}):(\d{1,2}):(\d{1,2})$/, tip: "请输入正确的时间格式"},
    dateTime: {reg: /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/, tip: "请输入正确时间的日期格式"}
}, te$.orderStatus = {
    APPROVED: "已审核",
    PRINTED: "已打单",
    PICKED: "已拣货",
    PACKAGED: "已打包",
    SENT: "已发货",
    SENT_PART: "部分发货",
    SENT_FAIL: "发货失败",
    FINISHED: "已送达",
    APPLY_CANCEL: "申请取消",
    CANCELLED: "已取消",
    APPLY_REFUND: "申请退款",
    REFUNDING: "退款中",
    REFUNDED: "已退款",
    LACK: "缺货",
    WAIT_PAY: "未支付",
    PAYED: "已支付",
    APPROVED_SUSPEND: "审核挂起",
    EDIT: "正在处理",
    SUSPEND: "挂起",
    OVERSOLD: "超卖"
}, te$.checkTest = function () {
    var t = window.location.host.split(".");
    return "test" == t[0] ? !0 : !1
}, te$.isWWW = function () {
    var t = window.location.host.split(".");
    return "www" == t[0] ? !0 : !1
}, te$.storeId = function () {
    return te$.checkTest() ? {ochirly: 2, fiveplus: 5, trendiano: 6} : {ochirly: 1, fiveplus: 2, trendiano: 6}
}(), te$.newInCatalogId = function () {
    return te$.checkTest() ? {ochirly: 3, fiveplus: 21, trendiano: 206} : {ochirly: 23, fiveplus: 21, trendiano: 205}
}(), te$.getCurrHost = function (t, e) {
    var i = window.location.host.split(":"), o = i[0].split("."), n = t || "", s = "", a = 1, r = "", c = (e ? e : "http") + "://", l = 2 == i.length ? ":" + i[1] : "";
    te$.checkTest() && (a = 2, r = "test.");
    for (var d = a; d < o.length; d++)s += "." + o[d];
    if ("sso" == t && (s = ".ochirly.com.cn"), "img2" == t) {
        if (te$.checkTest())return "//test.static.t-e-shop.com/trendy/global/v1";
        r = "", c = "//", n = "https:" == window.location.protocol ? "img2s" : "img2"
    }
    return c + r + n + s + l
}, te$.getCurrBrand = function () {
    var t = null, e = window.location.host.toLowerCase();
    return e.indexOf("fiveplus.com") >= 0 && (t = "fiveplus"), e.indexOf("ochirly.com") >= 0 && (t = "ochirly"), e.indexOf("trendiano.com") >= 0 && (t = "trendiano"), t
}, te$.getBrandName = function () {
    var t = "", e = window.location.host.toLowerCase();
    return e.indexOf("fiveplus.com") >= 0 && (t = "Five Plus"), e.indexOf("ochirly.com") >= 0 && (t = "ochirly"), e.indexOf("trendiano.com") >= 0 && (t = "TRENDIANO"), t
}, te$.cookie = function (t, e, i) {
    if ("undefined" == typeof e) {
        var o = null;
        if (document.cookie && "" != document.cookie)for (var n = document.cookie.split(";"), s = 0; s < n.length; s++) {
            var a = jQuery.trim(n[s]);
            if (a.substring(0, t.length + 1) == t + "=") {
                o = decodeURIComponent(a.substring(t.length + 1));
                break
            }
        }
        return o
    }
    i = i || {}, null === e && (e = "", i.expires = -1);
    var r = "";
    if (i.expires && ("number" == typeof i.expires || i.expires.toUTCString)) {
        var c;
        "number" == typeof i.expires ? (c = new Date, c.setTime(c.getTime() + 24 * i.expires * 60 * 60 * 1e3)) : c = i.expires, r = "; expires=" + c.toUTCString()
    }
    var l = i.path ? "; path=" + i.path : "", d = i.domain ? "; domain=" + i.domain : "", u = i.secure ? "; secure" : "";
    document.cookie = [t, "=", encodeURIComponent(e), r, l, d, u].join("")
}, te$.trendySite = {
    www: te$.getCurrHost("www"),
    my: te$.getCurrHost("my"),
    passport: te$.checkTest() ? te$.getCurrHost("passport", "http") : te$.getCurrHost("passport", "http"),
    httpsPassport: te$.getCurrHost("passport", "http"),
    sso: function () {
        var t;
        return t = te$.checkTest() ? "https:" == window.location.protocol ? te$.getCurrHost("sso", "http") : te$.getCurrHost("sso", "http") : te$.getCurrHost("sso", "http")
    }(),
    httpsSso: te$.getCurrHost("sso", "http"),
    portal: "http://test02.teadmin.net:9080",
    act: te$.getCurrHost("act"),
    img2: te$.getCurrHost("img2")
}, te$.isCheckin = function () {
    for (var t = window.location.href, e = ["UserAccountLogin.do", "UserAccountRegister.do", "UserPasswordRegain.do", "UserPasswordRegain.do"], i = !1, o = 0, n = e.length; n > o; o++)t.indexOf(e[o]) >= 0 && (i = !0);
    return i
}, te$.getUrl = function (t) {
    var e = "";
    switch (t) {
        case"userScore":
            e = te$.trendySite.passport + "/user/UserProfileListJson.do";
            break;
        case"userMessage":
            e = te$.trendySite.act + "/applets/UserMessagePopUp.do";
            break;
        case"myMessage":
            e = te$.trendySite.act + "/applets/UserMessageInfoList.do";
            break;
        case"myOrder":
            e = te$.trendySite.my + "/order/list.do";
            break;
        case"cancelOrder":
            e = te$.trendySite.my + "/order/cancel.do";
            break;
        case"myCoupon":
            e = te$.trendySite.my + "/coupon/list.do";
            break;
        case"myInfo":
            e = te$.trendySite.passport + "/user/UserInfoManage.do";
            break;
        case"cartURL":
            e = te$.trendySite.my + "/order/cart.do";
            break;
        case"cartAdd":
            e = te$.trendySite.my + "/order/cartAdd.do";
            break;
        case"cartModify":
            e = te$.trendySite.my + "/order/cartMod.do";
            break;
        case"cartColorModify":
            e = te$.trendySite.my + "/order/cartModItem.do";
            break;
        case"cartDelete":
            e = te$.trendySite.my + "/order/cartDel.do";
            break;
        case"sso":
            e = te$.trendySite.sso + "/passport/info.do";
            break;
        case"login":
            e = te$.trendySite.passport + "/user/Logon.do";
            break;
        case"logout":
            e = te$.trendySite.passport + "/user/Logout.do";
            break;
        case"account":
            e = te$.trendySite.my + "/user/Home.do";
            break;
        case"wishlist":
            e = te$.trendySite.my + "/order/favorite.do";
            break;
        case"onlineservice":
            e = "http://183.63.71.221:8081/client.jsp?companyId=7&style=324&locate=cn&username=";
            break;
        case"host":
            e = te$.trendySite.www;
            break;
        case"proInfo":
            e = te$.trendySite.www + "/m/CommodityInventoryQuery.action";
            break;
        case"favoriteAdd":
            e = te$.trendySite.my + "/order/favoriteAdd.do";
            break;
        case"favoriteDel":
            e = te$.trendySite.my + "/order/favoriteDel.do";
            break;
        case"orderConfirm":
            e = te$.trendySite.my + "/order/confirm.do";
            break;
        case"address":
            e = te$.trendySite.passport + "/user/UserAddressManageJson.do";
            break;
        case"addressCountry":
            e = ("https:" == document.location.protocol ? te$.trendySite.passport : te$.trendySite.www) + "/m/CountryList.action";
            break;
        case"addressProvince":
            e = "http://www.lpmas.com/m/ProvinceList.action";
            break;
        case"addressCity":
            e = "http://www.lpmas.com/m/CityList.action";
            break;
        case"addressRegion":
            e = "http://www.lpmas.com/m/RegionList.action"
            break;
        case"couponVerify":
            e = te$.trendySite.my + "/coupon/verify.do";
            break;
        case"removeMessage":
            e = te$.trendySite.act + "/applets/UserMessageInfoDel.do";
            break;
        case"pointExchange":
            e = te$.trendySite.my + "/order/virtualProductBuy.do";
            break;
        case"catalogItemList":
            e = te$.trendySite.www + "/m/ProductList.action";
            break;
        case"couponAvailAble":
            e = te$.trendySite.my + "/coupon/CouponUserAvailAble.do";
            break;
        case"vipStatus":
            e = te$.trendySite.passport + "/user/UserWeixinGradeJson.do";
            break;
        default:
            e = ""
    }
    return e
}, te$.domainName = location.host.indexOf("fiveplus.com") >= 0 ? ".fiveplus.com" : location.host.indexOf("ochirly.com") >= 0 ? ".ochirly.com.cn" : ".trendiano.com", te$.sizeList = {
    1: "XS",
    2: "S",
    3: "M",
    4: "L",
    5: "XL",
    6: "XXL",
    7: "XXXL",
    9: "均",
    all: "",
    XS: "XS",
    S: "S",
    M: "M",
    L: "L",
    XL: "XL",
    "均码": "均",
    30: "30",
    31: "31",
    32: "32",
    33: "33",
    34: "34",
    35: "35",
    36: "36",
    37: "37",
    38: "38",
    39: "39",
    40: "40",
    41: "41",
    42: "42",
    43: "43",
    44: "44",
    45: "45"
}, te$.sizeListArray = ["1", "2", "3", "4", "5", "6", "7", "9", "all", "XS", "S", "M", "L", "XL", "均码", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45"], te$.place = "test" == location.hostname.split(".")[0] ? location.hostname.split(".")[1] : location.hostname.split(".")[0], te$.myStyle = {
    ochirly: "my_ochirly.css",
    trendiano: "my_trendiano.css"
}, te$.myWapStyle = {
    ochirly: "my_och.css",
    trendiano: "my_trendiano.css",
    fiveplus: "my_fp.css"
}, te$.setMyStyle = function () {
    var t, e;
    te$.getCurrBrand()in te$.myStyle && (t = document.createElement("link"), e = (te$.checkTest() ? "//test.static.t-e-shop.com/trendy/global/v1/css/" : te$.trendySite.img2 + "/rs/common/v1/web/css/") + te$.myStyle[te$.getCurrBrand()], t.rel = "stylesheet", t.type = "text/css", t.href = e, document.getElementsByTagName("head")[0].appendChild(t))
}, te$.setMyWapStyle = function () {
    var t = document.createElement("link"), e = (te$.checkTest() ? "//test.static.t-e-shop.com/trendy/global/v1/wap/css/" : te$.trendySite.img2 + "/rs/common/v1/wap/css/") + te$.myWapStyle[te$.getCurrBrand()];
    te$.getCurrBrand()in te$.myWapStyle && (t.rel = "stylesheet", t.href = e, document.getElementsByTagName("head")[0].appendChild(t))
}, te$.myShowQrcode = function () {
    "trendiano" != te$.getCurrBrand() && t$("navQrcode") && (t$("navQrcode").onmouseover = function () {
        this.parentNode.getElementsByTagName("div")[0].style.display = "block"
    }, t$("navQrcode").onmouseout = function () {
        this.parentNode.getElementsByTagName("div")[0].style.display = "none"
    })
}, te$.wash = {
    ochirly: {
        "自然风干": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhVFNohBEIAAAAAAAKd5WvRmoAAM-7QHqLoQAAAqP965.png",
        "专业护理": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhU1NohBEIAAAAAAAHw2GGsTEAAM-7QH64o4AAAfb646.png",
        "用湿布擦拭即可": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhU1NohBEIAAAAAAAMY6PeD00AAM-7QIIyWUAAAx7483.png",
        "中温蒸汽熨烫": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhVFNohCgIAAAAAAAMOsdkMx0AAM-7gP_15EAAAxS446.png",
        "专业干洗": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhVFNohCgIAAAAAAAOFVZaI_MAAM-7gP_4-MAAA4t850.png",
        "低温蒸汽熨烫": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhVFNohCkIAAAAAAAMdNDyN5gAAM-7gP_8hAAAAyM936.png",
        "反面洗涤": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhU1NohCkIAAAAAAAOIudbRjIAAM-7wEd6FIAAA46602.png",
        "撑开平放干衣": "http://img10.360buyimg.com/imgzone/g14/M0A/02/13/rBEhVVNohCkIAAAAAAAIUYRsQssAANFkgP_o3EAAAhp773.png",
        "高温熨烫": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhVFNohCoIAAAAAAALqYSBQwoAAM-7wE-AsMAAAvB091.png",
        "悬挂晾干": "http://img10.360buyimg.com/imgzone/g14/M0A/02/13/rBEhVVNohCoIAAAAAAAJvLjIsRYAANFkgP_wVgAAAnU020.png",
        "不可曝晒": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhU1NohCoIAAAAAAALG_w4pq4AAM-7wFVav8AAAsz862.png",
        "不可漂白": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhVFNohCoIAAAAAAAJ1AaXvVUAAM-7wFbHgQAAAns332.png",
        "不可水洗": "http://img10.360buyimg.com/imgzone/g14/M0A/02/13/rBEhVlNohCsIAAAAAAAKmA_wOOIAANFkgP_1jUAAAqw081.png",
        "不可熨烫": "http://img10.360buyimg.com/imgzone/g14/M0A/02/13/rBEhV1NohCwIAAAAAAALlDze-NgAANFkgP_4OUAAAus674.png",
        "不可干洗": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhUlNohCwIAAAAAAAMJBfNTHkAAM-7wHbwwIAAAw8512.png",
        "30℃以下网袋缓和机洗": "http://img10.360buyimg.com/imgzone/g14/M0A/02/13/rBEhV1NohC0IAAAAAAANvoHZQ8kAANFkgP_7JEAAA3W643.png",
        "30℃以下网袋机洗": "http://img10.360buyimg.com/imgzone/g14/M0A/02/13/rBEhV1NohC0IAAAAAAAMr3bs3FwAANFkwEQzGUAAAzH385.png",
        "30℃以下轻柔手洗": "http://img10.360buyimg.com/imgzone/g14/M0A/02/13/rBEhVlNohC0IAAAAAAAHD7PwEQMAANFkQP_-AcAAAf5478.png",
        "30℃以下常规洗涤": "http://img10.360buyimg.com/imgzone/g14/M0A/02/13/rBEhVlNohC4IAAAAAAAMpJsYXE0AANFkwE8m3sAAAy8327.png",
        "低温熨烫": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhVFNohC4IAAAAAAALdkj9MsUAAM-7wJsSeIAAAuO760.png",
        "请勿浸泡": "http://img10.360buyimg.com/imgzone/g14/M0A/02/13/rBEhV1NohC4IAAAAAAALAyv0ZQ0AANFkwFHy_oAAAsb045.png",
        "请与同类颜色衣物洗涤": "http://img10.360buyimg.com/imgzone/g13/M0A/01/0C/rBEhVFNohC4IAAAAAAAOPeMO5KQAAM-7wJ8_PoAAA5V856.png"
    },
    fiveplus: {
        "专业护理": "http://img01.taobaocdn.com/imgextra/i1/685140573/T2bu94XfRbXXXXXXXX_!!685140573.gif",
        "自然风干": "http://img04.taobaocdn.com/imgextra/i4/685140573/T2HzcmXlhbXXXXXXXX_!!685140573.gif",
        "专业干洗": "http://img02.taobaocdn.com/imgextra/i2/685140573/T2ow.zXjtXXXXXXXXX_!!685140573.gif",
        "中温蒸汽熨烫": "http://img02.taobaocdn.com/imgextra/i2/685140573/T27_UyXeFaXXXXXXXX_!!685140573.gif",
        "悬挂晾干": "http://img01.taobaocdn.com/imgextra/i1/685140573/T22.v7Xh4aXXXXXXXX_!!685140573.gif",
        "用湿布擦拭即可": "http://img03.taobaocdn.com/imgextra/i3/685140573/T2E6KEXhhcXXXXXXXX_!!685140573.gif",
        "请勿浸泡": "http://img03.taobaocdn.com/imgextra/i3/685140573/T2y2cyXgNaXXXXXXXX_!!685140573.gif",
        "请与同类颜色衣物洗涤": "http://img04.taobaocdn.com/imgextra/i4/685140573/T2r87yXmNaXXXXXXXX_!!685140573.gif",
        "高温熨烫": "http://img04.taobaocdn.com/imgextra/i4/685140573/T2ClwzXhxXXXXXXXXX_!!685140573.gif",
        "低温蒸汽熨烫": "http://img01.taobaocdn.com/imgextra/i1/685140573/T26g.zXXRaXXXXXXXX_!!685140573.gif",
        "反面洗涤": "http://img01.taobaocdn.com/imgextra/i1/685140573/T26AgzXglXXXXXXXXX_!!685140573.gif",
        "不可熨烫": "http://img02.taobaocdn.com/imgextra/i2/685140573/T2rvIyXoFaXXXXXXXX_!!685140573.gif",
        "低温熨烫": "http://img02.taobaocdn.com/imgextra/i2/685140573/T2KV.zXf4aXXXXXXXX_!!685140573.gif",
        "不可水洗": "http://img02.taobaocdn.com/imgextra/i2/685140573/T2jbIzXfxaXXXXXXXX_!!685140573.gif",
        "撑开平放干衣": "http://img04.taobaocdn.com/imgextra/i4/685140573/T2uS3yXcdaXXXXXXXX_!!685140573.gif",
        "不可漂白": "http://img02.taobaocdn.com/imgextra/i2/685140573/T2N2AlXo4aXXXXXXXX_!!685140573.gif",
        "不可暴晒": "http://img03.taobaocdn.com/imgextra/i3/685140573/T2gByUXeRbXXXXXXXX_!!685140573.gif",
        "不可曝晒": "http://img03.taobaocdn.com/imgextra/i3/685140573/T2gByUXeRbXXXXXXXX_!!685140573.gif",
        "不可干洗": "http://img02.taobaocdn.com/imgextra/i2/685140573/T26O7zXoXXXXXXXXXX_!!685140573.gif",
        "30℃以下网袋机洗": "http://img03.taobaocdn.com/imgextra/i3/685140573/T2mkY5Xk8aXXXXXXXX_!!685140573.gif",
        "30℃以下网袋缓和机洗": "http://img02.taobaocdn.com/imgextra/i2/685140573/T2XzZyXlXaXXXXXXXX_!!685140573.gif",
        "30℃以下轻柔手洗": "http://img01.taobaocdn.com/imgextra/i1/685140573/T24YXSXlpeXXXXXXXX_!!685140573.gif",
        "30℃以下常规洗涤": "http://img01.taobaocdn.com/imgextra/i1/685140573/T2eDCzXk8cXXXXXXXX_!!685140573.gif"
    },
    trendiano: {
        "自然风干": "http://img02.taobaocdn.com/imgextra/i2/1778137875/T2NAs5XeJaXXXXXXXX_!!1778137875.png",
        "专业干洗": "http://img02.taobaocdn.com/imgextra/i2/1778137875/T2FNU5XcdaXXXXXXXX_!!1778137875.png",
        "专业护理": "http://img03.taobaocdn.com/imgextra/i3/1778137875/T2eCQ6XgpXXXXXXXXX_!!1778137875.png",
        "中温蒸汽熨烫": "http://img04.taobaocdn.com/imgextra/i4/1778137875/T2pc37XXVXXXXXXXXX_!!1778137875.png",
        "用湿布擦拭即可": "http://img04.taobaocdn.com/imgextra/i4/1778137875/T2nNU6XlxXXXXXXXXX_!!1778137875.png",
        "低温蒸汽熨烫": "http://img04.taobaocdn.com/imgextra/i4/1778137875/T295duXaROXXXXXXXX_!!1778137875.png",
        "反面洗涤": "http://img04.taobaocdn.com/imgextra/i4/1778137875/T2BiE6Xi0XXXXXXXXX_!!1778137875.png",
        "撑开平放干衣": "http://img02.taobaocdn.com/imgextra/i2/1778137875/T23BA4XltaXXXXXXXX_!!1778137875.png",
        "高温熨烫": "http://img01.taobaocdn.com/imgextra/i1/1778137875/T2lEc6XfJXXXXXXXXX_!!1778137875.png",
        "悬挂晾干": "http://img03.taobaocdn.com/imgextra/i3/1778137875/T2.tw7XXlXXXXXXXXX_!!1778137875.png",
        "不可曝晒": "http://img04.taobaocdn.com/imgextra/i4/1778137875/T2i4xVXjXeXXXXXXXX_!!1778137875.png",
        "不可漂白": "http://img04.taobaocdn.com/imgextra/i4/1778137875/T2KF6jXhBbXXXXXXXX_!!1778137875.png",
        "不可水洗": "http://img02.taobaocdn.com/imgextra/i2/1778137875/T2GJc7XXJXXXXXXXXX_!!1778137875.png",
        "不可熨烫": "http://img04.taobaocdn.com/imgextra/i4/1778137875/T2OHE6XbRaXXXXXXXX_!!1778137875.png",
        "不可干洗": "http://img01.taobaocdn.com/imgextra/i1/1778137875/T2UAZ5XiJaXXXXXXXX_!!1778137875.png",
        "30℃以下网袋缓和机洗": "http://img04.taobaocdn.com/imgextra/i4/1778137875/T2yVk5XihaXXXXXXXX_!!1778137875.png",
        "30℃以下网袋机洗": "http://img03.taobaocdn.com/imgextra/i3/1778137875/T2NCs5XaRaXXXXXXXX_!!1778137875.png",
        "30℃以下轻柔手洗": "http://img02.taobaocdn.com/imgextra/i2/1778137875/T2CEA5XcpaXXXXXXXX_!!1778137875.png",
        "30℃以下常规洗涤": "http://img04.taobaocdn.com/imgextra/i4/1778137875/T2AaMqXXVaXXXXXXXX_!!1778137875.png",
        "低温熨烫": "http://img04.taobaocdn.com/imgextra/i4/1778137875/T2Vy7MXk4aXXXXXXXX_!!1778137875.png",
        "请勿浸泡": "http://img01.taobaocdn.com/imgextra/i1/1778137875/T2DAI6XgxXXXXXXXXX_!!1778137875.png",
        "请与同类颜色衣物洗涤": "http://img02.taobaocdn.com/imgextra/i2/1778137875/T2Fkf7XoJaXXXXXXXX_!!1778137875.png"
    }
}, te$.traffic = function () {
    var t = new Date, e = "", i = function () {
        var t = "";
        return t = te$.cookie("sui") ? te$.cookie("sui") : "-1002"
    }();
    if (!te$.checkTest()) {
        if (window._gaq = window._gaq || [], void 0 == te$.cookie("gaqUnique") && te$.cookie("gaqUnique", "trendy_" + parseInt(9e14 * Math.random() + 1e14), {
                expires: 730,
                path: "/",
                domain: te$.domainName
            }), e += t.getFullYear(), e += 1 == (t.getMonth() + 1).toString().length ? "0" + (t.getMonth() + 1).toString() : (t.getMonth() + 1).toString(), e += 1 == t.getDate().toString().length ? "0" + t.getDate().toString() : t.getDate().toString(), e += 1 == t.getHours().toString().length ? "0" + t.getHours().toString() : t.getHours().toString(), e += 1 == t.getMinutes().toString().length ? "0" + t.getMinutes().toString() : t.getMinutes().toString(), e += 1 == t.getSeconds().toString().length ? "0" + t.getSeconds().toString() : t.getSeconds().toString(), e += 1 == t.getMilliseconds().toString().length ? "00" + t.getMilliseconds() : 2 == t.getMilliseconds().toString().length ? "0" + t.getMilliseconds() : t.getMilliseconds(), _gaq.push(["_setAccount", "UA-38772164-1"]), _gaq.push(["_setDomainName", te$.domainName.substr(1)]), _gaq.push(["_setAllowLinker", !0]), _gaq.push(["_setCustomVar", "1", "uservisits", e + "_" + te$.cookie("__utma") + "_" + i + "_" + te$.cookie("gaqUnique"), "3"]), _gaq.push(["_trackPageview"]), "undefined" != typeof _gaViewPoint1) {
            _gaq.push(["_addTrans", _gaOrderInfo.id + "_" + e, _gaOrderInfo.shop, _gaOrderInfo.total, 0, _gaOrderInfo.ship]);
            for (var o = 0; o < _gaSkuItem.length; o++)_gaq.push(["_addItem", _gaOrderInfo.id + "_" + e, _gaSkuItem[o][0], _gaSkuItem[o][1], "", _gaSkuItem[o][2].substr(1), _gaSkuItem[o][3]]);
            _gaq.push(["_trackTrans"])
        }
        var n = document.createElement("script");
        n.type = "text/javascript", n.async = !0, n.src = ("https:" == document.location.protocol ? "https://ssl" : "http://www") + ".google-analytics.com/ga.js";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(n, s), window._hmt = window._hmt || [];
        try {
            if (".trendiano.com" != te$.domainName) {
                var a = document.createElement("script");
                a.type = "text/javascript", a.async = !0, ".ochirly.com.cn" == te$.domainName && (a.src = "https:" == document.location.protocol ? "https://hm.baidu.com/h.js?b0adbe94cc9ad3e2220d23cda6f79273" : "http://hm.baidu.com/h.js?b0adbe94cc9ad3e2220d23cda6f79273"), ".fiveplus.com" == te$.domainName && (a.src = "https:" == document.location.protocol ? "https://hm.baidu.com/h.js?ee21e2133af66bfcae5a0dfed24c75ed" : "http://hm.baidu.com/h.js?ee21e2133af66bfcae5a0dfed24c75ed");
                var r = document.getElementsByTagName("script")[0];
                r.parentNode.insertBefore(a, r)
            }
        } catch (c) {
        }
        if (".trendiano.com" != te$.domainName) {
            window._mvq = window._mvq || [], ".fiveplus.com" == te$.domainName ? _mvq.push(["$setAccount", "m-29247-0"]) : _mvq.push(["$setAccount", "m-29245-0"]), "undefined" != typeof proInfo && (_mvq.push(["$setGeneral", "goodsdetail", "", "", ""]), _mvq.push(["$logConversion"]), _mvq.push(["$addGoods", "", te$.getCurrBrand(), proInfo.proName, proInfo.sku, proInfo.price, document.getElementsByTagName("img")[0].src, "", te$.getCurrBrand(), "", proInfo.listPrice, "", ""])), ("http://www.ochirly.com.cn/" == window.location.href || "http://www.fiveplus.com/" == window.location.href) && _mvq.push(["$setGeneral", "index", "", "", ""]), window.location.href.indexOf("/search/") >= 0 && -1 == window.location.href.indexOf("target") && (_mvq.push(["$setGeneral", "searchresult", "", "", ""]), _mvq.push(["$addSearchResult", pageParams.name, ""])), window.location.href.indexOf("list") >= 0 && window.location.href.indexOf(".shtml") >= 0 && -1 == window.location.href.indexOf("target") && (_mvq.push(["$setGeneral", "category", "", "", ""]), _mvq.push(["$addCategory", pageParams.name, ""])), window.location.href.indexOf("/user/Home.do") >= 0 && -1 == window.location.href.indexOf("target") && _mvq.push(["$setGeneral", "memberindex", "", "", ""]), window.location.href.indexOf("/order/list.do") >= 0 && _mvq.push(["$setGeneral", "memberorder", "", "", ""]), window.location.href.indexOf("/user/UserScoreList.do") >= 0 && _mvq.push(["$setGeneral", "memberpoint", "", "", ""]), window.location.href.indexOf("/order/favorite.do") >= 0 && _mvq.push(["$setGeneral", "memberfavorite", "", "", ""]), window.location.href.indexOf("/applets/UserReviewInfoList.do") >= 0 && _mvq.push(["$setGeneral", "comment", "", "", ""]), window.location.href.indexOf("/user/UserAccountLogin.do") >= 0 && _mvq.push(["$setGeneral", "login", "", "", ""]), window.location.href.indexOf("/user/UserAccountRegister.do") >= 0 && _mvq.push(["$setGeneral", "register", "", "", ""]), _mvq.push(["$logData"]), _mvq.push(["$logConversion"]);
            var l = document.createElement("script");
            l.type = "text/javascript", l.async = !0, l.src = "https:" == document.location.protocol ? "https://static-ssl.mediav.com/mvl.js" : "http://static.mediav.com/mvl.js";
            var d = document.getElementsByTagName("script")[0];
            d.parentNode.insertBefore(l, d)
        }
    }
}, $(document).ready(function () {
    te$.traffic(), window.location.href.indexOf("/user/UserInfoManage.do") >= 0 && t$("userMobile") && t$("userMobile").setAttribute("readonly", !0)
}), te$.message = function (t, e, i, o) {
    var n = function (t) {
        i.ajax({
            url: e.getUrl("removeMessage"),
            dataType: "jsonp",
            cache: !1,
            data: {action: "removeMessage:" + t, messageId: t},
            jsonpCallback: "trendyCall"
        })
    }, s = function (t) {
        var o;
        if ("code"in t) {
            if (1 == t.code && (o = t.command.split(":"), 2 == o.length))return void i(t$("messageItem" + o[1])).fadeOut(200);
            e.ui.msgBox.show("删除未能成功，再试一次看看。")
        }
    }, a = function () {
        i("b.btn_delete").each(function () {
            this.getAttribute("data-id") && i(this).bind("click", function () {
                n(this.getAttribute("data-id"))
            })
        })
    };
    return {setRemove: a, removeItem: s}
}(window, te$, $), te$.c$ = function (t) {
    return document.createElement(t)
}, te$.detectMobile = function () {
    var t = navigator.userAgent || navigator.vendor || window.opera;
    return /(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(t) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(t.substr(0, 4)) ? !0 : !1
}, te$.trim = function (t) {
    try {
        return t.replace(/^\s+|\s+$/g, "")
    } catch (e) {
        return t
    }
}, te$.hasClass = function (t, e) {
    return t.className.match(new RegExp("(\\s|^)" + e + "(\\s|$)"))
}, te$.addClass = function (t, e) {
    te$.hasClass(t, e) || (t.className += " " + e)
}, te$.removeClass = function (t, e) {
    if (te$.hasClass(t, e)) {
        var i = new RegExp("(\\s|^)" + e + "(\\s|$)");
        t.className = t.className.replace(i, " ")
    }
}, te$.getByClass = function (t, e, i) {
    if (t = te$.trim(t), e = e || "*", !i)return [];
    for (var o = [], n = i.getElementsByTagName(e), s = 0, a = n.length; a > s; ++s)te$.hasClass(n[s], t) && (o[o.length] = n[s]);
    return o
}, te$.subStitute = function (t, e) {
    return "[object String]" !== Object.prototype.toString.call(t) ? "" : "[object Object]" === Object.prototype.toString.call(e) && "isPrototypeOf"in e ? t.replace(/\{([^{}]+)\}/g, function (t, i) {
        var o = e[i];
        return void 0 !== o ? "" + o : ""
    }) : t
}, te$.getQueryParam = function (t) {
    if (!t)return "";
    var e = new RegExp("(^|&)" + t + "=([^&]*)(&|$)"), i = window.location.search.substr(1).match(e);
    return null != i ? i[2] : ""
}, te$.gloGetSize = function (t) {
    return t ? t in te$.sizeList ? te$.sizeList[t] : t : null
}, te$.formatMoney = function (t, e, i) {
    e = void 0 !== e ? e : "", i = void 0 !== i ? i : "";
    var o = 2, n = ".", s = 0 > t ? "-" : "", a = parseInt(t = Math.abs(+t || 0).toFixed(o), 10) + "", r = (r = a.length) > 3 ? r % 3 : 0;
    return e + s + (r ? a.substr(0, r) + i : "") + a.substr(r).replace(/(\d{3})(?=\d)/g, "$1" + i) + (o ? n + Math.abs(t - a).toFixed(o).slice(2) : "")
}, te$.codeToBrand = function (t) {
    var e = {1: "ochirly", 2: "fiveplus", 3: "trendiano", c: "covengarden"};
    return t.toLowerCase()in e ? e[t.toLowerCase()] : t
}, te$.codeToYear = function (t) {
    var e = {
        y: "2015",
        h: "2016",
        j: "2017",
        g: "2018",
        z: "2019",
        r: "2020",
        n: "2021",
        w: "2022",
        t: "2023",
        l: "2024",
        s: "2025",
        m: "2026"
    };
    return t.toLowerCase().substr(0, 1)in e ? e[t.toLowerCase().substr(0, 1)] : "20" + t
}, te$.codeToRange = function (t) {
    return String.fromCharCode(96 + parseInt(t))
}, te$.skuToImgUrl = function (t, e, i) {
    var o = "//img1.ochirly.com.cn/wcsstore/TrendyCatalogAssetStore/images/trendy/", n = te$.codeToBrand(t.substr(0, 1)), s = te$.codeToYear(t.substr(1, 2)), a = te$.codeToRange(t.substr(3, 1));
    return o + n + "/" + s + "/" + a + "/" + t + "/" + t + "_" + e + "_" + i + ".jpg"
}, te$.getSizeDesc = function (t) {
    return t in te$.sizeList ? te$.sizeList[t] : t
}, te$.showWashedImg = function (t) {
    var e, i = null, o = "";
    if (t) {
        if (e = t$(t)) {
            i = e.innerHTML.split("\n");
            for (var n = 0, s = i.length; s > n; n++)i[n]in te$.wash[te$.getCurrBrand()] && (o += '<img src="' + te$.wash[te$.getCurrBrand()][i[n]] + '" />')
        }
        e.innerHTML = o
    }
}, te$.point = function (t, e, i, o) {
    var n = function (t) {
        t.bind("click", function () {
            var t = this.getAttribute("data-point");
            t && s(t)
        })
    }, s = function (t) {
        i.ajax({
            url: e.getUrl("pointExchange"),
            dataType: "jsonp",
            cache: !1,
            data: {productItemId: t, action: "pointExchange"},
            jsonpCallback: "trendyCall"
        })
    };
    return {setPointExchangeBtn: n}
}(window, te$, $), te$.ui = function (t, e, i, o) {
    var n = {
        div: null, visible: !1, color: "#666666", opacity: 4, css: null, count: 0, init: function () {
            this.div = e.c$("div"), this.css = this.div.style, this.css.position = "absolute", this.css.zIndex = "600", this.css.left = "0", this.css.top = "0", this.css.backgroundColor = this.color, this.css.opacity = (this.opacity / 10).toFixed(1), this.css.filter = "alpha(opacity=" + 10 * this.opacity + ")", this.resize(), this.css.display = "none", document.body.appendChild(this.div), i(t).resize(function () {
                ui.mask.resize()
            })
        }, resize: function () {
            this.css.width = i(document.body).width() + "px", this.css.height = i(document.body).height() + 20 + "px"
        }, show: function () {
            0 == this.count && (this.div || this.init(), this.visible || (this.css.display = "block", this.resize(), this.visible = !0, this.count++))
        }, hide: function () {
            this.count > 0 && this.count--, 0 == this.count && this.visible && (this.css.display = "none", this.visible = !1)
        }
    }, a = {
        html: '<div class="msg_info normal_pop" id="msgBox"><div class="msg_info_tit normal_tit"><span>温馨提示</span><em class="info_close"  onclick="te$.ui.msgBox.hide();">关闭</em></div><div class="msg_info_con"><div class="con"><div class="txt">{msg}</div><div class="msg_btns" id="msgBtns"></div></div></div></div>',
        div: null,
        init: function () {
            this.div = e.c$("div"), this.div.style.position = "absolute", this.div.style.top = "0", this.div.style.left = "0", this.div.style.zIndex = "1000", this.div.style.display = "none", this.div.style.width = "100%", this.div.className = "message_pop_frame", document.body.appendChild(this.div)
        },
        show: function (o, n) {
            var s;
            this.div || this.init(), this.div.style.height = document.body.scrollHeight + "px", this.div.innerHTML = e.subStitute(this.html, {msg: o});
            var a = null;
            if (n) {
                s = n.length;
                for (var r = 0; r < n.length; r++)a = e.c$("button"), a.innerHTML = n[r][0], a.onclick = n[r][1], 3 == n[r].length ? a.className = n[r][2] : a.className = "btn_my_link btn_my_link_" + s, t$("msgBtns").appendChild(a)
            } else a = '<button onclick="te$.ui.msgBox.hide();" class="btn_my_link">关闭</button>', t$("msgBtns").innerHTML = a;
            e.ui.msgBox.div.style.display = "block";
            var c = i(this.div.getElementsByTagName("div")[0]), l = i("body").width() - c.width() > 0 ? (i("body").width() - c.width()) / 2 : 0, d = i(t).height() - c.height() > 0 ? i(document).scrollTop() + (i(t).height() - c.height()) / 2 : i(document).scrollTop() + 4;
            c.css({left: l + "px", top: d + "px"}), e.ui.drager.init("msgTitle", "msgBox")
        },
        hide: function () {
            this.div.innerHTML = "", this.div.style.display = "none", e.ui.dialog.id && (t$(e.ui.dialog.id).style.visibility = "visible")
        }
    }, r = {
        id: null, position: function (t) {
        }, show: function (e, o) {
            var n = i("#" + e), s = i(document).width() - n.width() > 0 ? (i(document).width() - n.width()) / 2 : 0, a = i(t).height() - n.height() > 0 ? i(document).scrollTop() + (i(t).height() - n.height()) / 2 : i(document).scrollTop() + 4;
            n.css({left: s + "px", top: a + "px"}), this.id = e, i("#" + e).show()
        }, hide: function (t) {
            this.id = null, i("#" + t).hide()
        }
    }, c = {
        html: '<div class="scroller" id="scroller{id}"><span class="scroller_up">up</span><span class="scroller_down">down</span><div class="scroller_track"><div class="scroller_bar" id="scrollerBar{id}">&lt;&gt;</div></div></div>',
        list: {}, oX: 0, oY: 0, oP: 0, stepSize: {}, remove: function (t) {
            this.list[t] && delete this.list[t]
        }, makeOne: function (o, n, s, a, r, c) {
            if (!this.list[o]) {
                var l = i("#" + o), d = t$(o);
                if (d) {
                    var u = d.parentNode;
                    if (!(d.scrollHeight < n)) {
                        var m = (1e6 * Math.random()).toFixed();
                        s && (d.style.width = s + "px"), d.style.height = n + "px", d.style.overflow = "hidden", d.scrollTop = 0, d.scrollId = "scr" + m, "absolute" != l.css("position") && "relative" != l.css("position") && l.css("position", "relative"), "absolute" != i(u).css("position") && "relative" != i(u).css("position") && (u.style.position = "relative"), i(u).append(e.subStitute(this.html, {id: m}));
                        var h = i("#scroller" + m), p = i("#scrollerBar" + m), g = i("#scroller" + m + " span:first"), f = i("#scroller" + m + " span:last");
                        p.css("top", 0), "show" == r ? (g.show(), f.show(), h.height(n - g.height() - f.height()), h.css({
                            paddingTop: g.css("height"),
                            paddingBottom: f.css("height")
                        })) : (g.hide(), f.hide(), h.height(n));
                        var v = c ? l.position().left + d.clientWidth - h.width() - 1 + parseInt(l.css("marginLeft")) + parseInt(l.css("marginRight")) : l.position().left + d.clientWidth + parseInt(l.css("marginLeft")) + parseInt(l.css("marginRight"));
                        h.css({
                            top: l.position().top + parseInt(l.css("marginTop")),
                            left: v
                        }), p.height(n / t$(o).scrollHeight * (n - g.height() - f.height())), a && (this.stepSize[o] = a);
                        var a = a || 8;
                        p.mouseenter(function () {
                            i(this).addClass("scroller_bar_active")
                        }).mouseleave(function () {
                            i(this).removeClass("scroller_bar_active")
                        }).mousedown(function (o) {
                            e.ui.scroller.oX = o.clientX, e.ui.scroller.oY = o.clientY, e.ui.scroller.oP = parseInt(this.style.top), i("body").mousemove(function (t) {
                                e.ui.scroller.move(t, t$("scrollerBar" + m), d, n)
                            }).mouseup(function (t) {
                                i(t$("scrollerBar" + m)).removeClass("scroller_bar_move"), i(this).unbind("mousemove")
                            }), i(this).addClass("scroller_bar_move"), i("body").css("-moz-user-select", "none"), document.body.onselectstart = function (t) {
                                return !1
                            }, t.getSelection ? t.getSelection().removeAllRanges() : document.selection.empty()
                        }).mouseup(function (t) {
                            i("body").css("-moz-user-select", "-moz-all"), document.body.onselectstart = function (t) {
                                return !0
                            }, i("body").unbind("mousemove"), i(this).removeClass("scroller_bar_move")
                        }), g.click(function () {
                            return e.ui.scroller.move(-a, t$("scrollerBar" + m), d, n), !1
                        }), f.click(function () {
                            return e.ui.scroller.move(a, t$("scrollerBar" + m), d, n), !1
                        }), t.addEventListener ? d.addEventListener(i.browser.mozilla ? "DOMMouseScroll" : "mousewheel", e.ui.scroller.wheel, !1) : d.onmousewheel = e.ui.scroller.wheel, e.ui.scroller.list[o] = "scroller" + m
                    }
                }
            }
        }, wheel: function (i) {
            var i = i || t.event, o = 0, n = document.getElementById("scrollerBar" + this.scrollId.substr(3));
            if (this.id in e.ui.scroller.stepSize)var s = this.scrollTop % e.ui.scroller.stepSize[this.id] == 0 ? e.ui.scroller.stepSize[this.id] : e.ui.scroller.stepSize[this.id] - this.scrollTop % e.ui.scroller.stepSize[this.id], a = this.scrollTop % e.ui.scroller.stepSize[this.id] == 0 ? e.ui.scroller.stepSize[this.id] : this.scrollTop % e.ui.scroller.stepSize[this.id]; else var s = 12, a = 12;
            o = "wheelDelta"in i ? i.wheelDelta > 0 ? -s : a : i.detail > 0 ? s : -a, this.scrollTop += o, n.style.top = this.scrollTop * (n.parentNode.clientHeight - n.clientHeight) / (this.scrollHeight - this.clientHeight) + "px", i.preventDefault ? i.preventDefault() : i.returnValue = !1
        }, move: function (t, o, n, a) {
            var r, c;
            r = isNaN(t) ? t.clientY - e.ui.scroller.oY : t, c = e.ui.scroller.oP + r, c = 0 > c ? 0 : c, c = c > i(o).parent(".scroller_track").height() - i(o).height() ? i(o).parent(".scroller_track").height() - i(o).height() : c, o.style.top = c + "px", s = (n.scrollHeight - n.clientHeight) * c / (i(o).parent(".scroller_track").height() - i(o).height()), n.scrollTop = s > n.scrollHeight - n.clientHeight ? n.scrollHeight - n.clientHeight : s, e.ui.scroller.oX = t.clientX, e.ui.scroller.oY = t.clientY, e.ui.scroller.oP = c
        }
    }, l = {
        make: function (t, o, n, s) {
            for (var a = e.getByClass("detail_content_tit", "dt", t$(t)), r = (i("#" + t + " dt"), e.getByClass(n, "dd", t$(o))), c = 0; c < a.length; c++)!function (t, e) {
                t.onclick = function () {
                    i(this).addClass("active"), r[e].style.display = "block";
                    for (var t = 0; t < a.length; t++)e != t && (i(a[t]).removeClass("active"), r[t].style.display = "none");
                    return !1
                }
            }(a[c], c)
        }
    }, d = {
        init: function (t) {
            var i = {
                tab: null,
                tabChip: null,
                target: null,
                contentChip: null,
                activeTabClass: "",
                activeContentClass: ""
            }, o = "", n = null;
            if (t$(t) && (i.tab = t$(t), i.tab.getAttribute("data-config") && (o = i.tab.getAttribute("data-config"), o = o.replace(/\'/g, '"'), n = e.stringToJson(o), "object" == typeof n && (i.tabChip = function () {
                    var t = null;
                    return t = "tab"in n && i.tab.getElementsByTagName(n.tab).length > 0 ? i.tab.getElementsByTagName(n.tab) : i.tab.getElementsByTagName("span").length > 0 ? i.tab.getElementsByTagName("span") : null
                }(), i.tabChip && i.tabChip.length && (i.target = "target"in n && t$(n.target) ? t$(n.target) : null, i.target && (i.contentChip = function () {
                    var t = null;
                    return t = "content"in n && i.target.getElementsByTagName(n.content).length > 0 ? i.target.getElementsByTagName(n.content) : i.target.getElementsByTagName("p").length > 0 ? t = i.target.getElementsByTagName("p") : null
                }(), i.contentChip && i.contentChip.length)))))) {
                if (i.tabChip.length != i.contentChip.length)return void(t$(t).style.display = "none");
                "activeTabClass"in n && (i.activeTabClass = n.activeTabClass), "activeContentClass"in n && (i.activeContentClass = n.activeContentClass), i.l = i.tabChip.length, this.makeActive(i)
            }
        }, makeActive: function (t) {
            for (var e = 0, o = t.l; o > e; e++)0 == e ? (t.activeTabClass && (i(t.tabChip[e]).hasClass(t.activeTabClass) || i(t.tabChip[e]).addClass(t.activeTabClass)), t.activeContentClass && (i(t.contentChip[e]).hasClass(t.activeContentClass) || i(t.contentChip[e]).addClass(t.activeContentClass)), t.contentChip[e].style.display = "block") : (t.activeTabClass && i(t.tabChip[e]).removeClass(t.activeTabClass), t.activeContentClass && i(t.contentChip[e]).removeClass(t.activeContentClass), t.contentChip[e].style.display = "none"), t.tabChip[e].onclick = function () {
                for (var e = 0, o = 0, n = t.l; n > o; o++)this == t.tabChip[o] && (e = o), t.activeTabClass && i(t.tabChip[o]).removeClass(t.activeTabClass), t.activeContentClass && i(t.contentChip[o]).removeClass(t.activeContentClass), t.contentChip[o].style.display = "none";
                t.activeTabClass && (i(t.tabChip[e]).hasClass(t.activeTabClass) || i(t.tabChip[e]).addClass(t.activeTabClass)), t.activeContentClass && (i(t.contentChip[e]).hasClass(t.activeContentClass) || i(t.contentChip[e]).addClass(t.activeContentClass)), t.contentChip[e].style.display = "block"
            }
        }
    }, u = {
        newOne: {frame: null, handle: null, show: null, bigImg: null, pic: {}}, init: function (t, e, i) {
            this.newOne.frame = t$(t), this.newOne.handle = t$(e), this.newOne.show = t$(i), this.newOne.bigImg = t$(i).getElementsByTagName("img")[0], this.newOne.pic = {}, this.newOne.pic = new Image, this.newOne.pic.src = this.newOne.bigImg.src, this.newOne.imgSize = {
                width: this.newOne.bigImg.width,
                height: this.newOne.bigImg.height
            }
        }, setSrc: function (t, o, n) {
            for (var s = document.getElementById("productImgTab").getElementsByTagName("img"), a = 0; a < s.length; a++)s[a].className = "";
            if (s[o - 1].className = "active", "s" == n)this.newOne.handle.getElementsByTagName("img")[0].src = e.skuToImgUrl(proInfo.sku, "m", o), this.newOne.handle.getElementsByTagName("img")[0].setAttribute("jqimg", e.skuToImgUrl(proInfo.sku, "b", o)); else if ("b" != n)return !1;
            i(this.newOne.bigImg).load(function () {
                u.newOne.imgSize = {width: this.width, height: this.height}
            })
        }
    }, m = {
        init: function (e, o) {
            var n, s, a, r, c, l, d = !1, u = 8e3, m = "lr", h = !1, p = "";
            if (0 == arguments.length)return !1;
            if (e && ("string" == typeof e && (n = t$(e)), "object" == typeof e && (n = "box"in e ? t$(e.box) : null, s = "proArr"in e ? t$(e.proArr) : null, a = "nextArr"in e ? t$(e.nextArr) : null, d = "autoplay"in e ? e.autoplay : !1, h = "cycle"in e ? e.cycle : !1, u = "speed"in e ? e.speed : 8e3, m = "way"in e ? e.way : "lr", "lr" != m && "tb" != m && (m = "lr"))), o && "object" == typeof o && (p = "html"in o ? o.html : "", "" != p && (n.innerHTML = p), r = "nodeType"in o ? o.nodeType : "", l = "group"in o ? t$(o.group) : null), !n)return !1;
            if (n.timerId = "timer" + (1e6 * Math.random()).toFixed(), n.currId = 0, n.way = m, n.speed = u, n.proArr = s, n.nextArr = a, n.speed = u, n.autoplay = d, n.moving = !1, !r)return !1;
            if (!l)return !1;
            if (c = l.getElementsByTagName(r), c.length <= 1)return !1;
            n.nodes = c, n.contentGroup = l;
            for (var g = 0; g < c.length; g++)c[g].style.position = "absolute", 0 == g ? "lr" == m ? (c[g].style.left = "0", c[g].style.top = "0") : (c[g].style.left = "0", c[g].style.top = "0") : "lr" == m ? (c[g].style.left = n.clientWidth + "px", c[g].style.top = "0") : (c[g].style.left = 0, c[g].style.top = n.clientHeight + "px");
            s && i(s).click(function () {
                n.autoplay ? n.moving || (ui.slide2011.stop(n), ui.slide2011.move(n, -1), n.timerId = t.setTimeout(function () {
                    ui.slide2011.start(n, 1)
                }, u)) : ui.slide2011.move(n, -1)
            }), a && i(a).click(function () {
                n.autoplay ? n.moving || (ui.slide2011.stop(n), ui.slide2011.start(n, 1)) : ui.slide2011.move(n, 1)
            }), d && (n.timerId = t.setTimeout(function () {
                ui.slide2011.start(n, 1)
            }, n.speed))
        }, start: function (e, o) {
            if (ui.slide2011.move(e, o), i(e.nodes[e.currId]).queue("fx").length <= 1)e.timerId = t.setTimeout(function () {
                ui.slide2011.start(e, o)
            }, e.speed); else {
                var n;
                o > 0 && (n = e.currId >= e.nodes.length - 1 ? 0 : e.currId + 1), 0 > o && (n = e.currId <= 0 ? e.nodes.length - 1 : e.currId - 1), i(e.nodes[e.currId]).clearQueue(), i(e.nodes[n]).clearQueue(), e.timerId = t.setTimeout(function () {
                    ui.slide2011.start(e, o)
                }, e.speed)
            }
        }, stop: function (e) {
            t.clearTimeout(e.timerId)
        }, move: function (t, e) {
            var o;
            e > 0 && (o = t.currId >= t.nodes.length - 1 ? 0 : t.currId + 1), 0 > e && (o = t.currId <= 0 ? t.nodes.length - 1 : t.currId - 1);
            var n = i(t.nodes[t.currId]), s = i(t.nodes[o]);
            t.moving = !0, e > 0 ? ("lr" == t.way && (t.nodes[o].style.left = t.clientWidth + "px", n.animate({left: "-" + t.clientWidth + "px"}, function () {
                t.moving = !1
            }), s.animate({left: "0"})), "tb" == t.way && (t.nodes[o].style.top = t.clientHeight + "px", n.animate({top: "-" + t.clientHeight + "px"}, function () {
                t.moving = !1
            }), s.animate({top: "0"}))) : ("lr" == t.way && (t.nodes[o].style.left = "-" + t.clientWidth + "px", n.animate({left: t.clientWidth + "px"}, function () {
                t.moving = !1
            }), s.animate({left: "0"})), "tb" == t.way && (t.nodes[o].style.top = "-" + t.clientHeight + "px", n.animate({top: t.clientHeight + "px"}, function () {
                t.moving = !1
            }), s.animate({top: "0"}))), e > 0 && (t.currId = t.currId >= t.nodes.length - 1 ? 0 : t.currId + 1), 0 > e && (t.currId = t.currId <= 0 ? t.nodes.length - 1 : t.currId - 1)
        }
    }, h = {
        oLeft: 0, oTop: 0, eX: 0, eT: 0, init: function (o, n) {
            var s = i(t$(o)), a = i(t$(n));
            s.mousedown(function (o) {
                i("body").css("-moz-user-select", "none"), document.body.onselectstart = function (t) {
                    return !1
                }, t.getSelection ? t.getSelection().removeAllRanges() : document.selection.empty(), e.ui.drager.oLeft = a.position().left, e.ui.drager.oTop = a.position().top, e.ui.drager.eX = o.clientX, e.ui.drager.eY = o.clientY, i("body").mousemove(function (o) {
                    var n = -e.ui.drager.eX + o.clientX, s = -e.ui.drager.eY + o.clientY, r = a.position().left + n, c = a.position().top + s;
                    4 > r && (r = 4), r + a.outerWidth() > i(t).width() - 4 && (r = i(t).width() - a.outerWidth() - 4), c < i(document).scrollTop() + 4 && (c = i(document).scrollTop() + 4), c + a.outerHeight() > i(t).height() + i(document).scrollTop() - 4 && (c = i(document).scrollTop() + i(t).height() - a.outerHeight() - 4), a.css({
                        left: r,
                        top: c
                    }), e.ui.drager.eX = o.clientX, e.ui.drager.eY = o.clientY
                }).mouseup(function (t) {
                    i("body").unbind("mousemove"), i("body").css("-moz-user-select", "-moz-all"), document.body.onselectstart = function (t) {
                        return !0
                    }
                })
            }).mouseup(function (t) {
                i("body").unbind("mousemove"), i("body").css("-moz-user-select", "-moz-all"), document.body.onselectstart = function (t) {
                    return !0
                }
            })
        }
    }, p = {
        hideTimer: null, init: function (o, n) {
            if (2 != arguments.length)return !1;
            if (!t$(o) || !t$(n))return !1;
            var s = t$(o), a = i(s), r = t$(n), c = i(r);
            c.show(), c.css({
                left: a.position().left + a.outerWidth() - c.innerWidth(),
                top: a.offset().top + a.outerHeight() - i(t).scrollTop()
            }), c.mouseenter(function () {
                t.clearTimeout(e.ui.pup.hideTimer)
            }), c.mouseleave(function () {
                c.hide(), c.unbind()
            })
        }, showTxt: function () {
            i(".landscape").mouseover(function () {
                i(this).children(".land_tip") && i(this).children(".land_tip").fadeTo(300, .75)
            }), i(".landscape").mouseleave(function () {
                i(this).children(".land_tip") && i(this).children(".land_tip").fadeOut(300)
            })
        }
    }, g = {
        proImgs: null, init: function (o) {
            if (!document.getElementById(o))return !1;
            if (this.proImgs = document.getElementById(o).getElementsByTagName("img"), this.proImgs.length < 1)return !1;
            for (var n = 0, s = this.proImgs.length; s > n; n++)this.proImgs[n].hasLazy = !1;
            var a = i(document).scrollTop(), r = i(t).height();
            this.show(a, r), i(t).scroll(function () {
                var o = i(document).scrollTop(), n = i(t).height();
                e.ui.lazy.show(o, n)
            })
        }, show: function (t, o) {
            for (var n = 0, s = this.proImgs.length; s > n; n++)this.proImgs[n].hasLazy || i(this.proImgs[n]).offset().top - t - o < 50 && (this.proImgs[n].getAttribute("data-sku") ? this.proImgs[n].src = e.skuToImgUrl(this.proImgs[n].getAttribute("data-sku"), "list", 1) : this.proImgs[n].src = this.proImgs[n].getAttribute("datasrc"), this.proImgs[n].hasLazy = !0)
        }
    }, f = function (t) {
        var i = t$(t);
        if (i)for (var o = i.getElementsByTagName("img"), n = 0; n < o.length; n++)o[n].onmouseover = function () {
            var t = null, i = "", o = "";
            if (t = this.getAttribute("data-rollover"), t && (t = t.split(","), 2 == t.length)) {
                if ("sku" == t[0]) {
                    if (!this.getAttribute("data-sku"))return;
                    if (t[1] = t[1].split("-"), 2 != t[1].length)return;
                    i = e.skuToImgUrl(this.getAttribute("data-sku"), t[1][0], t[1][1])
                }
                "url" == t[0] && (i = t[1]), o = this.getAttribute("src"), i && o && (this.src = i, this.onmouseout = function () {
                    this.src = o
                })
            }
        }
    }, v = {
        dateFormat: function () {
            var t = new Date, e = new Array(12);
            e[0] = "JAN", e[1] = "FEB", e[2] = "MAR", e[3] = "APR", e[4] = "MAY", e[5] = "JUNE", e[6] = "JULY", e[7] = "AUG", e[8] = "SEPT", e[9] = "OCT", e[10] = "NOV", e[11] = "DEC", t$("footerMonth").innerHTML = e[t.getMonth()] + "<em>" + t.getDate() + "</em>"
        }
    }, y = {
        showList: function () {
            var t = document.getElementById("floatList"), e = document.getElementById("mixMask"), o = document.getElementById("showActive");
            t.style.display = "none", e.style.display = "none", i("#showActive").click(function () {
                "none" == t.style.display ? (t.style.display = "block", e.style.display = "block", o.className = "active") : (t.style.display = "none", e.style.display = "none", o.className = "")
            })
        }, showPopImg: function () {
            i("#rollImg1Big").css("display", "block"), i("#roll2013 li").click(function () {
                i("#rollImgBig div").fadeOut();
                var t = i(this), e = "#" + t.attr("id") + "Big", o = i(e + " img");
                o.size() && o.attr("src", o.attr("imgSrc")), i(e).fadeIn(), i("#roll2013 li div").removeClass("fashion_act"), i(t).find("div").addClass("fashion_act")
            })
        }, showTrePopImg: function (t, e) {
            i(t + ":eq(0)").css("display", "block"), i(e).click(function () {
                i(t).fadeOut();
                var o = i(this), n = t + ":eq(" + i(this).index() + ")", s = i(n + " img");
                s.size() && s.attr("src", s.attr("imgSrc")), i(n).fadeIn(), i(e).removeClass("fashion_act"), i(o).addClass("fashion_act")
            })
        }
    }, b = function (e) {
        var o = document.createElement("div");
        o.className = "top_float", o.id = "topFloat", o.style.display = "none", document.body.appendChild(o);
        var n = '<div class="tre_top_float"><div class="top_float_frame"><a href="javascript:;" id="linkServiceOnline" class="server_online">联系客服</a><a class="gotop_icon" id="backToTop">TOP</a></div></div>';
        o.innerHTML = n, i("#backToTop").click(function () {
            i("html,body").animate({scrollTop: 0}, 200)
        }), i(t).scroll(function () {
            var e = document.getElementById("topFloat"), o = 80;
            if (e) {
                var n = i(document).scrollTop();
                -[1] || t.XMLHttpRequest ? n >= o ? (e.style.display = "block", e.style.bottom = "10px") : e.style.display = "none" : n >= o ? (e.style.display = "block", e.style.bottom = "auto", e.style.top = n + i(t).height() - 10 - e.clientHeight + "px") : e.style.display = "none"
            }
        })
    }, $ = {
        init: function (t) {
            i(t).each(function () {
                var e = this.getElementsByTagName("input")[0], o = this.getElementsByTagName("p")[0], n = this.getElementsByTagName("li"), s = null;
                e && o && n.length > 0 && (s = n[0].parentNode, o.onclick = function () {
                    var e = o.clientHeight + 8, n = i(t);
                    s.style.top = e + "px", n.find("ul").hide(), n.parent().parent().css("zIndex", 1), "block" == s.getAttribute("data-display") ? (i(s).hide(), s.setAttribute("data-display", "none")) : (i(s).show(), i(s).parent().parent().parent().css("zIndex", 2), s.setAttribute("data-display", "block"))
                }, i(n).each(function () {
                    "selected" == this.getAttribute("class") && (o.innerHTML = this.innerHTML, e.value = this.getAttribute("data-value"))
                }).bind("click", function () {
                    o.innerHTML = this.innerHTML, e.value = this.getAttribute("data-value"), i(s).hide(), i(n).removeClass("selected"), i(this).addClass("selected")
                }))
            })
        }
    }, A = {
        items: {}, itemMoveFlag: !1, openPop: function (t, e) {
            var o = document.getElementById(t);
            o && (e ? ("string" == typeof e && (o.style.display = e), "object" == typeof e && "show"in e && i(o).slideDown("fast")) : o.style.display = "block", this.openedPop.hasOwnProperty(t) || (this.openedPop[t] = o))
        }, closePop: function (t, e) {
            var o = document.getElementById(t);
            o && (e ? ("string" == typeof e && (o.style.display = e), "object" == typeof e && "close"in e && i(o).slideUp("fast")) : o.style.display = "none", this.openedPop.hasOwnProperty(t) && delete this.openedPop[t])
        }, closeAllPop: function () {
            for (var t in this.openedPop)this.openedPop[t].style.display = "none", delete this.openedPop[t]
        }, checkInputDefault: function (t, e, i) {
            "focus" == t.type && e.value == i && (e.value = ""), "blur" == t.type && (e.value || (e.value = i))
        }, checkException: function (t) {
            var e = t.target, i = !1;
            return ("em" == e.tagName.toLowerCase() || "tx_edit" == e.className || "b" == e.tagName.toLowerCase() || e.className.indexOf("no_touch_move") >= 0) && (i = !0), i
        }, itemMove: function (t) {
            if (!A.checkException(t)) {
                var e = "touches"in t ? t.touches[0].screenX : t.screenX, i = "touches"in t ? t.touches[0].screenY : t.screenY, o = -(A.items.startPageX - e), n = -(A.items.startPageY - i);
                if (!isNaN(o) && !isNaN(n)) {
                    if (Math.abs(o) > 3 * Math.abs(n)) {
                        t.preventDefault();
                        var s = A.items.currLeft + o > 0 ? 0 : A.items.currLeft + o;
                        "style"in A.items.currItem && (A.items.currItem.style.left = s + "px")
                    }
                    A.itemMoveFlag = !0
                }
            }
        }, itemMoveStart: function (t) {
            var e = document.getElementById("cartWrap");
            if (e) {
                var i = t.target;
                if (!A.checkException(t)) {
                    for (var o = "true" == i.getAttribute("data-move-top") ? !0 : !1; !o && (i = i.parentNode, i.parentNode);)o = "true" == i.getAttribute("data-move-top") ? !0 : !1;
                    A.closeAllPop();
                    var n = "touches"in t ? t.touches[0] : t;
                    i && (A.items.openedItem && A.items.openedItem != i && (A.items.openedItem.style.left = "0", A.items.openedItem.style.transition = "left 0.1s linear 0"), "style"in i && (i.style.transition = "none"), A.items.startPageX = n.screenX, A.items.startPageY = n.screenY, A.items.currItem = i, i != document && (A.items.currLeft = "auto" == document.defaultView.getComputedStyle(i, null).left ? 0 : parseInt(document.defaultView.getComputedStyle(i, null).left))), A.itemMoveFlag = !1
                }
            }
        }, itemMoveEnd: function (t) {
            if (!A.checkException(t)) {
                if (A.items.currItem && A.items.currItem != document) {
                    var e = parseInt(document.defaultView.getComputedStyle(A.items.currItem, null).left);
                    e >= -37 && (A.items.currItem.style.left = "0", A.items.currItem.style.transition = "left 0.1s linear 0", A.items.openedItem = null), -37 > e && (A.items.currItem.style.left = "-15%", A.items.currItem.style.transition = "left 0.1s linear 0", A.items.openedItem = A.items.currItem)
                }
                if (delete A.items.startPageX, delete A.items.startPageY, delete A.items.currItem, !A.itemMoveFlag) {
                    var i = t.target, n = !1;
                    if (i) {
                        for (; "my_line order_list_row" != i.className && (("order_img" == i.className || "order_txt" == i.className) && (n = !0), i = i.parentNode, i != o && null != i););
                        if (i && "my_line order_list_row" == i.className && n) {
                            var s = i.getAttribute("sku");
                            13 == s.length && A.openFromCart(s)
                        }
                    }
                }
                A.itemMoveFlag = !1
            }
        }, setCartItemMove: function (e) {
            if (e) {
                var i = document.getElementById(e);
                i && (t.navigator.userAgent.indexOf("Windows NT") < 0 ? (i.addEventListener("touchstart", this.itemMoveStart, !1), i.addEventListener("touchmove", this.itemMove, !1), i.addEventListener("touchend", this.itemMoveEnd, !1)) : (i.addEventListener("mousedown", this.itemMoveStart, !1), i.addEventListener("mousemove", this.itemMove, !1), i.addEventListener("mouseup", this.itemMoveEnd, !1)))
            }
        }
    };
    return {
        mask: n,
        msgBox: a,
        scroller: c,
        tab: l,
        zoom: u,
        slide2011: m,
        drager: h,
        pup: p,
        lazy: g,
        dialog: r,
        goTop: b,
        fDate: v,
        pageEffect: y,
        imgRollover: f,
        switching: d,
        select: $,
        touchOut: A
    }
}(window, te$, $);
var _bigSlide = function (t, e, i, o, n, s, a, r) {
    var c, l = this, d = !1;
    if (this.running = !1, this.autoplay = r, this.contentBox = document.getElementById(e), !this.contentBox)return !1;
    if (this.contents = this.contentBox.getElementsByTagName(a), this.step = this.contents.length, this.step <= 1)return !1;
    if (t) {
        var u;
        this.tabBox = document.getElementById(t);
        for (var c = 0; c < this.contents.length; c++)0 == c ? u = '<div class="first"></div>' : u += "<div></div>";
        if (this.tabBox.innerHTML = u, this.tabs = this.tabBox.getElementsByTagName("div"), this.tabs.length != this.contents.length)return !1;
        this.tabBox.scrollTop = 0
    }
    this.up = document.getElementById(i.up), this.down = document.getElementById(i.down), this.step > n ? o ? (this.up.style.display = "block", this.down.style.display = "block", d = !0) : (this.up.style.display = "none", this.down.style.display = "none", d = !1) : (this.up.style.display = "none", this.down.style.display = "none");
    var m = (this.step - 1) * l.contents[0].clientWidth, h = (this.step - 1) * l.contents[0].clientHeight;
    if ("moveleft" == s || "moveup" == s) {
        if (d) {
            var p;
            "moveleft" == s ? (p = "scrollLeft", this.up.onclick = function () {
                l.contentBox.scrollLeft > 0 && $(l.contentBox).animate({scrollLeft: l.contentBox.scrollLeft - l.contents[0].clientWidth})
            }, this.down.onclick = function () {
                l.contentBox.scrollLeft <= m - l.contents[0].clientWidth * n && $(l.contentBox).animate({scrollLeft: l.contentBox.scrollLeft + l.contents[0].clientWidth})
            }) : "moveup" == s && (p = "scrollTop", this.up.onclick = function () {
                l.contentBox.scrollTop > 0 && $(l.contentBox).animate({scrollTop: l.contentBox.scrollTop - l.contents[0].clientHeight})
            }, this.down.onclick = function () {
                parseInt(l.contentBox.scrollTop) <= parseInt(h - l.contents[0].clientHeight * n) && $(l.contentBox).animate({scrollTop: l.contentBox.scrollTop + l.contents[0].clientHeight})
            })
        }
    } else if ("fade" == s) {
        for (this.timer = null, this.gap = 6e3, this.currStep = 0, c = 0; c < this.step; c++)!function (t) {
            l.tabs[t].onmouseover = function () {
                l.stopAt(t)
            }, l.tabs[t].onmouseout = function () {
                l.autoplay && l.start()
            }
        }(c);
        if (this.autoplay && this.start(), d) {
            var p;
            "fade" == s && (l.step <= n && (t$(l.up.id + "Mask").style.display = "block", t$(l.down.id + "Mask").style.display = "block"), this.up.onclick = function () {
                window.clearTimeout(l.timer), l.move(l, "up")
            }, this.down.onclick = function () {
                window.clearTimeout(l.timer), l.move(l, "down")
            })
        }
    }
};
_bigSlide.prototype.start = function () {
    var t = this;
    this.running || (this.running = !0, this.timer = window.setTimeout(function () {
        t.move(t)
    }, this.gap))
}, _bigSlide.prototype.stopAt = function (t) {
    t == this.currStep ? window.clearTimeout(this.timer) : (window.clearTimeout(this.timer), $(this.contents[this.currStep]).fadeOut("slow"), $(this.tabs[this.currStep]).css("background-color", "#999999"), $(this.contents[t]).fadeIn("slow", function () {
        self.running = !1
    }), $(this.tabs[t]).css("background-color", "#000000")), this.currStep = t
}, _bigSlide.prototype.move = function (t, e) {
    var i, o = t.currStep;
    i = "up" == e ? t.currStep - 1 < 0 ? t.step - 1 : t.currStep - 1 : t.currStep + 1 >= t.step ? 0 : t.currStep + 1, t.contents[o].style.zIndex = 2, t.contents[i].style.zIndex = 1, $(t.contents[o]).fadeOut("slow"), $(t.tabs[o]).css("background-color", "#999999"), $(t.contents[i]).fadeIn("slow", function () {
        t.running = !1
    }), $(t.tabs[i]).css("background-color", "#000000"), t.currStep = i, t.autoplay && (t.timer = window.setTimeout(function () {
        t.move(t)
    }, t.gap))
}, _bigSlide.prototype.showIndexTip = function () {
    var t = document.getElementById("indexTipTxt");
    if (t) {
        var e = t.getElementsByTagName("p");
        if (!(e.length < 1)) {
            for (var i = [], o = 0; o < e.length; o++)e[o].innerHTML.length > 0 && i.push(e[o].innerHTML);
            if (!(i.length < 1)) {
                for (var n = document.createElement("div"), s = "", o = 0; o < i.length; o++)s += "<p>" + i[o] + "</p>";
                n.className = "site_index_tip", n.innerHTML = '<div class="tip_txt">' + s + '</div><div class="tip_bg">-</div><span title="关闭">-</span>', t.parentNode.appendChild(n), _maq(n)
            }
        }
    }
}, te$.mobile = function (t, e, i, o) {
    var n = (e.detectMobile(), "click"), s = {
        books: {}, fillCheckoutAddress: function (t) {
            if (t$("checkoutAddress"))if (t)t$("checkoutAddress").innerHTML = this.books[t].province + this.books[t].city + this.books[t].region + this.books[t].address, t$("checkoutReceiver").innerHTML = this.books[t].receiverName + " " + this.books[t].mobile; else for (var e in this.books)0 != this.books[e].isDefault && (t$("checkoutAddress").innerHTML = this.books[e].province + this.books[e].city + this.books[e].region + this.books[e].address, t$("checkoutReceiver").innerHTML = this.books[e].receiverName + " " + this.books[e].mobile)
        }, fillOrderForm: function (t) {
            for (var e in this.books[t])t$(e) && (t$(e).value = this.books[t][e])
        }, resetSelected: function (t) {
            i(".address_item").each(function () {
                i(this).removeClass("selected"), this.getElementsByTagName("input")[0].value == t && i(this).addClass("selected")
            })
        }
    }, a = {
        cartShowTimer: null, cartIds: "", cartData: {}, useCouponCode: function (t) {
            c.useCoupon(t)
        }, uploadCart: function (e) {
            this.cartShowTimer && t.clearTimeout(this.cartShowTimer), this.cartShowTimer = t.setTimeout(function () {
                a.modify(e)
            }, 500)
        }, modify: function (t) {
            try {
                i.ajax({
                    url: e.getUrl("cartModify"),
                    dataType: "jsonp",
                    cache: !1,
                    data: {quantity: t.num, cartId: t.cartId, action: "modifyCart:" + t.itemId},
                    jsonpCallback: "trendyCall"
                })
            } catch (o) {
                e.ui.msgBox.show("删除购物袋过程中发生错误，请和我们的客服联系并反映以下错误信息：" + o.name + ":" + o.message)
            }
        }, afterModify: function (t) {
            if (t.hasOwnProperty("code"))if (t.code > 0) {
                var i = t.command.split(":"), o = a.cartData[i[1]];
                o.quantity = parseInt(t$("qty" + i[1]).value), t$("spPrice" + i[1]).innerHTML = e.formatMoney(o.price * o.quantity, "￥"), a.countCart("myBag")
            } else e.ui.msgBox.show("操作未能完成，请再试一次。"); else e.ui.msgBox.show("发生错误，请再试一次。")
        }, remove: function (t) {
            if (!t)return !1;
            try {
                i.ajax({
                    url: e.getUrl("cartDelete"),
                    dataType: "jsonp",
                    cache: !1,
                    data: {itemIds: t, action: "deleteCartItem:" + t},
                    jsonpCallback: "trendyCall"
                })
            } catch (o) {
                e.ui.msgBox.show("删除购物袋过程中发生错误，请和我们的客服联系并反映以下错误信息：" + o.name + ":" + o.message)
            }
        }, afterRemove: function (t) {
            if (t.hasOwnProperty("code"))if (t.code > 0) {
                for (var o = t.command.split(":"), n = o[1].split(","), s = "", r = 0; r < n.length; r++)s += 0 == r ? "#item" + n[r] : ", #item" + n[r], delete a.cartData[n[r]];
                i(s).animate({opacity: 0}, 500, function () {
                    var t = e.cookie("CART_NUM") ? parseInt(e.cookie("CART_NUM")) : 0;
                    i(s).remove(), 0 == t ? a.clearCart("myBay") : a.countCart("myBag")
                })
            } else e.ui.msgBox.show("操作未能完成，请再试一次。"); else e.ui.msgBox.show("发生错误，请再试一次。")
        }, cancelOrder: function (t, o) {
            t && e.ui.msgBox.show("确定要取消此订单吗？", [["确定", function () {
                try {
                    i.ajax({
                        url: e.getUrl("cancelOrder"),
                        dataType: "jsonp",
                        data: {orderId: t, action: "cancelOrder:" + t + (o ? ":d" : "")},
                        jsonpCallback: "trendyCall"
                    })
                } catch (n) {
                    e.ui.msgBox.show("删除异常，请稍候再试")
                }
            }], ["取消", function () {
                e.ui.msgBox.hide()
            }]])
        }, afterCancelOrder: function (o) {
            var n;
            "code"in o && 1 == o.code ? (n = o.command.split(":"), 3 == n.length ? (e.ui.msgBox.hide(), document.referrer ? t.location.href = document.referrer : t.history.go(-1)) : t$("orderItem" + n[1]) ? (i(t$("orderItem" + n[1])).fadeOut(), e.ui.msgBox.hide()) : t.location.reload()) : e.ui.msgBox.show("删除异常，请稍候再试")
        }, setCancelOrder: function () {
            i(".linkCancelOrder").bind("click", function () {
                var t = this.getAttribute("data-id");
                t && a.cancelOrder(t)
            })
        }, initCart: function () {
            i("#btnCartCheckout").bind(n, function () {
                e.cookie("couponTip", null), a.checkoutCart()
            }), i(".btn_num_cut").each(function () {
                i(this).bind(n, function () {
                    var t = i(this).attr("data-item-id"), e = t$("qty" + t), o = this.getAttribute("data-product-id"), n = this.getAttribute("data-cart-id");
                    return parseInt(e.value) <= 1 ? !1 : (e.value = parseInt(e.value) - 1, void a.uploadCart({
                        cartId: n,
                        productId: o,
                        num: e.value,
                        itemId: t
                    }))
                })
            }), i(".link_del_cart_item").each(function () {
                i(this).bind(n, function () {
                    a.remove(i(this).attr("data-action-param"))
                })
            }), i(".btn_num_plus").each(function () {
                i(this).bind(n, function () {
                    var t = i(this).attr("data-item-id"), e = t$("qty" + t), o = this.getAttribute("data-product-id"), n = this.getAttribute("data-cart-id");
                    e.value = parseInt(e.value) + 1, a.uploadCart({cartId: n, productId: o, num: e.value, itemId: t})
                })
            }), this.countCart("myBag")
        }, countCart: function (t) {
            var i = t$(t);
            if (!i)return !1;
            var o = e.getByClass("ck_select_item", "input", i), n = 0, s = 0;
            this.cartIds = "";
            for (var a = 0; a < o.length; a++)o[a].checked && (n += this.cartData[o[a].value].price * this.cartData[o[a].value].quantity, s += this.cartData[o[a].value].quantity, this.cartIds += this.cartData[o[a].value].itemId + "," + this.cartData[o[a].value].quantity + ";");
            this.cartIds.length > 1 && (this.cartIds = this.cartIds.substr(0, this.cartIds.length - 1)), t$("cartItemCount").innerHTML = s, t$("cartTotalPrice").innerHTML = e.formatMoney(n), 0 == s ? t$("btnCartCheckout").disabled = !0 : t$("btnCartCheckout").disabled = !1
        }, clearCart: function () {
            this.cartIds = "", a.cartData = {}, i("#myBag").parent().append('<div class="blank_tip"><b class="blank_tip_no">no</b>购物袋为空啦：（</div>'), i("#myBag").remove()
        }, checkoutCart: function () {
            return "" == this.cartIds || this.cartIds.length < 1 ? (e.ui.msgBox.show("还未选中任何一件商品。"), !1) : void(t.location.href = e.getUrl("orderConfirm") + "?orderItemStr=" + this.cartIds + "&_=" + (new Date).valueOf())
        }, initCheckout: function () {
            var t, o = !1, a = function () {
                var t = t$("orderInfo");
                return t.country.value && t.province.value && t.city.value && t.region.value && t.address.value && t.mobile.value && t.receiverName.value ? !0 : !1
            };
            if (i("#btnCheckout").bind(n, function () {
                    t$("userMessage").value.length > 0 && (t$("userComment").value = t$("userMessage").value), a() ? t$("orderInfo").submit() : e.ui.msgBox.show("请填写好收货信息。")
                }), i("#btnShowAllAddress").bind(n, function () {
                    i("#addressSelect .address_item").each(function () {
                        i(this).hasClass("selected") || ("true" == this.getAttribute("data-opend") ? (i(this).fadeOut(), this.removeAttribute("data-opend")) : (i(this).fadeIn(), this.setAttribute("data-opend", "true")))
                    })
                }), i('#addressSelect input[type="radio"]').each(function () {
                    var t = t$("addressId").value;
                    this.value == t && (this.checked = !0, t$("countryModify").value = this.getAttribute("data-country"), t$("provinceModify").value = this.getAttribute("data-province"), t$("cityModify").value = this.getAttribute("data-city"))
                }).bind(n, function () {
                    var t = i(this).attr("value");
                    t$("addressId") && (t$("addressId").value = t), t$("countryModify") && (t$("countryModify").value = s.books[t].country), t$("cityModify") && (t$("cityModify").value = s.books[t].city), t$("provinceModify") && (t$("provinceModify").value = s.books[t].province), t$("checkoutModify") && t$("checkoutModify").submit()
                }), i(".address_item").bind(n, function () {
                    var t = this.getElementsByTagName("input")[0].value, e = t$("addressId").value;
                    t != e && this.getElementsByTagName("input")[0].click()
                }), t$("couponCodeStrModify") && t$("couponCodeStrModify").value.length > 0 && (t = t$("couponCodeStrModify").value.split(";"), c.queryCoupon = t, t.length > 1)) {
                for (var r = t.length - 1; r >= 1; r--)for (var l = r - 1; l >= 0; l--)if (t[r] == t[l]) {
                    t.splice(r, 1), o = !0;
                    break
                }
                o && (t$("orderInfo").value = t$("couponCodeStrModify").value = t.join(";"), c.queryCoupon = t)
            }
            i(".link_private_coupon").each(function () {
                for (var t = i(this).attr("data-coupon-code"), e = !1, o = 0; o < c.queryCoupon.length; o++)if (c.queryCoupon[o] == t) {
                    e = !0;
                    break
                }
                e ? (i(this).bind(n, function () {
                    c.useCoupon(t, !0)
                }), i(this).html("取消")) : i(this).bind(n, function () {
                    c.useCoupon(t)
                })
            }), i("#btnUseCoupon").bind(n, function () {
                var t = t$("userCouponCode").value, i = !1;
                return t ? (i = c.checkCoupon(t), void(i ? e.ui.msgBox.show("优惠券已使用。") : c.verifyCode(t))) : void e.ui.msgBox.show("需要输入优惠码", [["确定", function () {
                    t$("userCouponCode").focus(), e.ui.msgBox.hide()
                }]])
            }), i("#couponList").find("b").each(function () {
                var t = this.getAttribute("data-coupon-code"), i = this.getAttribute("data-promotion-id"), o = c.checkCoupon(t), s = c.checkValidity(t, i);
                o ? s ? (this.innerHTML = this.innerHTML.substr(2, 1), this.title = "取消使用", this.className = "btn_used", this.setAttribute("data-action", "delete")) : ("done" != e.cookie("couponTip") && e.ui.msgBox.show("您选择的优惠券暂不能使用。"), t$("couponCodeStr").value = c.removeCoupon(t$("couponCodeStr").value, t), this.innerHTML = "∅", this.title = "此券无效", this.setAttribute("class", "btn_disable"), this.setAttribute("data-action", "disable")) : (this.innerHTML = this.innerHTML.substr(0, 1), this.title = "使用", this.setAttribute("data-action", "add")), "btn_disable" != this.getAttribute("class") && this.addEventListener(n, function () {
                    var t = this.getAttribute("data-coupon-code"), e = "delete" == this.getAttribute("data-action") ? !0 : !1;
                    c.useCoupon(t, e)
                })
            }), s.fillCheckoutAddress()
        }
    }, r = {
        initPayChannel: function () {
            var t = t$("payForm"), o = t$("payChannelChoose"), n = null;
            t && (n = function () {
                return t.payId.value && t.channelId.value ? !0 : (e.ui.msgBox.show("请选择支付方式"), !1)
            }, i("#payChannelChoose input").click(function () {
                t.channelId.value = this.value, i(o).find("p").each(function () {
                    this.removeAttribute("class")
                }), this.parentNode.parentNode.setAttribute("class", "selected"), t$("imgPayChannel").src = this.parentNode.getElementsByTagName("img")[0].src, t$("imgPayChannel").parentNode.parentNode.style.display = "inline"
            }), i("#btnGotoPay").click(function () {
                n() && t.submit()
            }))
        }, waitWecharQrcodeScan: function (e) {
            var o = "/pay/CheckItemPayStatus.do";
            e && t.setInterval(function () {
                i.ajax({
                    url: o, dataType: "json", cache: !1, type: "GET", data: e, success: function (e) {
                        "code"in e && 1 == e.code && "PAYED" == e.content.payStatus && (t.location.href = e.content.callBackUrl)
                    }
                })
            }, 1e4)
        }
    }, c = {
        queryCoupon: [], useCoupon: function (t, e) {
            var i;
            return t ? void(t$("couponCodeStrModify") && (i = t$("couponCodeStrModify"), e ? (i.value = i.value.replace(t, ""), i.value = i.value.replace(";;", ";"), 0 == i.value.indexOf(";") && (i.value = i.value.substr(1)), ";" == i.value.substr(i.value.length - 1) && (i.value = i.value.substr(0, i.value.length - 1))) : i.value.length > 0 ? i.value += ";" + t : i.value += t,
                t$("userCommentModify").value = t$("userMessage").value, t$("checkoutModify").submit())) : !1
        }, verifyCode: function (t) {
            try {
                i.ajax({
                    url: e.getUrl("couponVerify"),
                    dataType: "jsonp",
                    cache: !1,
                    data: {couponCode: t, action: "couponVerify"},
                    jsonpCallback: "trendyCall"
                })
            } catch (o) {
                e.ui.msgBox.show("发生错误，请和我们的客服联系并反映以下错误信息：" + o.name + ":" + o.message)
            }
        }, checkCoupon: function (t) {
            if (!t$("couponCodeStr") || !t$("couponCodeStr").value)return !1;
            for (var e = t$("couponCodeStr").value.split(";"), i = !1, o = 0, n = e.length; n > o; o++)if (e[o] == t) {
                i = !0;
                break
            }
            return i
        }, checkPromotion: function (t) {
            if (!t$("userCouponCode") || !t$("userCouponCode").getAttribute("data-used-promotion-id"))return !1;
            for (var e = (t$("userCouponCode").getAttribute("data-used-promotion-id") ? t$("userCouponCode").getAttribute("data-used-promotion-id") : "").split(";"), i = !1, o = 0, n = e.length; n > o; o++)if (e[o] == t) {
                i = !0;
                break
            }
            return i
        }, checkValidity: function (t, e) {
            return this.checkCoupon(t) && this.checkPromotion(e)
        }, removeCoupon: function (t, e) {
            for (var i = t.split(";"), o = [], n = 0, s = i.length; s > n; n++)i[n] != e && o.push(i[n]);
            return o.join(";")
        }
    }, l = {
        initFavorite: function () {
            i(".link_del_favorite_item").each(function () {
                i(this).bind(n, function () {
                    l.remove(i(this).attr("data-action-param"))
                })
            })
        }, add: function (t) {
            try {
                i.ajax({
                    url: e.getUrl("favoriteAdd"),
                    dataType: "jsonp",
                    cache: !1,
                    data: {productId: t, action: "addFavorite:" + t},
                    jsonpCallback: "trendyCall"
                })
            } catch (o) {
                e.ui.msgBox.show("添加收藏夹过程中发生错误，请和我们的客服联系并反映以下错误信息：" + o.name + ":" + o.message)
            }
        }, afterAdd: function (t) {
            t.hasOwnProperty("code") ? t.code > 0 ? e.ui.msgBox.show("所选商品已放入收藏夹。") : e.ui.msgBox.show("已经放入收藏夹的商品不需要重复添加。") : e.ui.msgBox.show("发生错误，请再试一次。")
        }, remove: function (t) {
            try {
                i.ajax({
                    url: e.getUrl("favoriteDel"),
                    dataType: "jsonp",
                    cache: !1,
                    data: {productId: t, action: "removeFavorite:" + t},
                    jsonpCallback: "trendyCall"
                })
            } catch (o) {
                e.ui.msgBox.show("发生错误，请和我们的客服联系并反映以下错误信息：" + o.name + ":" + o.message)
            }
        }, afterRemove: function (t) {
            if (t.hasOwnProperty("code"))if (t.code > 0) {
                for (var o = t.command.split(":"), n = o[1].split(","), s = "", a = 0; a < n.length; a++)s += 0 == a ? "#fav" + n[a] : ", #fav" + n[a];
                i(s).animate({opacity: 0}, 500, function () {
                    i(s).remove(), i(".favorite_item").length < 1 && i("#myFavorite").append('<div class="blank_tip"><b class="blank_tip_no">no</b>收藏夹空啦：（</div>')
                })
            } else e.ui.msgBox.show("操作未能完成，请再试一次。"); else e.ui.msgBox.show("发生错误，请再试一次。")
        }
    };
    return {cart: a, address: s, pay: r, favorite: l}
}(window, te$, $), te$.mobile.getUserScore = function () {
    $.ajax({
        url: te$.getUrl("userScore"),
        dataType: "jsonp",
        cache: !1,
        data: {action: "userScore"},
        jsonpCallback: "trendyCall",
        complete: function () {
            te$.mobile.getUserVip()
        }
    })
}, te$.mobile.getUserVip = function () {
    $.ajax({
        url: te$.getUrl("vipStatus"),
        dataType: "jsonp",
        cache: !1,
        data: {action: "userVipStatus"},
        jsonpCallback: "trendyCall"
    })
}, te$.mobile.homeInfo = function () {
    window.navigator.userAgent.toLowerCase().indexOf("micromessenger") >= 0 && $(".mc_addition").find("ul").eq($(".mc_addition").find("ul").size() - 1).hide(), t$("userQrCode").onclick = function () {
        t$("userQrCodePic").style.webkitTransform = "scale(0)", t$("userQrCodePic").style.transform = "scale(0)", this.style.visibility = "hidden"
    }, t$("btnShowQrCode").onclick = function () {
        t$("userQrCode").style.visibility = "visible", t$("userQrCodePic").style.webkitTransform = "scale(1, 1)", t$("userQrCodePic").style.transform = "scale(1, 1)"
    }, t$("userName").innerHTML = te$.cookie("suli"), t$("memberName").innerHTML = te$.cookie("suli"), this.getUserScore()
}, te$.mobile.showUserScore = function (t) {
    var e;
    window.location.href.indexOf("/user/Home.do") >= 0 && "code"in t && 1 == t.code && (e = t.content, e.userProfileBeanList.length > 0 ? t$("txUserScore") && (t$("txUserScore").innerHTML = parseInt(e.userProfileBeanList[0].userScore)) : t$("txUserScore") && (t$("txUserScore").innerHTML = 0), t$("txProgress") && (t$("txProgress").getElementsByTagName("span")[0].style.width = e.userInfoCompleted + "%", t$("txProgress").getElementsByTagName("b")[0].innerHTML = parseInt(e.userInfoCompleted) + "%"))
}, te$.mobile.showVipStatus = function (t) {
    var e = "message"in t ? t.message.toLowerCase() : "general", i = null;
    0 == e.indexOf("vip") && (i = e.split(":"), t$("txUserVipLevel") && (t$("txUserVipLevel").innerHTML = "vip会员：" + i[1]), $(".my_name").eq(0).find("p").eq(1).html("vip会员"), t$("linkBindVip") && (t$("linkBindVip").style.display = "none"))
}, window.addEventListener("load", function () {
    var t, e;
    window.location.href.indexOf("/order/detail.do") >= 0 && (t = document.getElementsByTagName("a"), t.length > 3 && t[2].getAttribute("onclick") && (e = t[2].getAttribute("onclick"), e.indexOf("te$.business.cart.cancelOrder") >= 0 && t[2].setAttribute("onclick", e.replace("business", "mobile")))), window.location.href.indexOf("/order/list.do") >= 0 && te$.mobile.cart.setCancelOrder()
}), te$.form = function (t, e, i, o) {
    var n, s = {}, a = "", r = null, c = e.checkTest() ? 5 : 60, l = function (t) {
        var i = t$(t[0]).value;
        for (var o in t[1])t[1].hasOwnProperty(o) && (t$(t[1][o]).value = "", o in e.regVerify && e.regVerify[o].reg.test(i) && (t$(t[1][o]).value = i))
    }, d = function (t) {
        for (var e, i = !1, o = 0, n = t.length; n > o; o++)e = g(t$(t[o])), e.pass && (i = !0);
        return i || h(a, "至少正确填写一项"), i
    }, u = function (t, o) {
        t$(t) && ("form" == t$(t).tagName.toLowerCase() ? t$(t).onsubmit = function () {
            var e = !0;
            if ("password"in o && 2 == o.password.length) {
                if (s.password = !1, t$(o.password[0]).value !== t$(o.password[1]).value)return s.password = !1, h(a, "两次密码输入不符"), !1;
                s.password = !0, p(a, "两次密码输入不符")
            }
            if ("detach"in o && l(o.detach), "mustHaveOne"in o)return d(o.mustHaveOne);
            for (var i in s)s.hasOwnProperty(i) && (s[i] || (e = !1));
            return m(t, o) && e
        } : m(t, o)), o && (a = "contentId"in o ? o.contentId : null, "regPhone"in o && t$(o.regPhone) && (t$(o.regPhone).onblur = function () {
            var t, n = this.value, c = this.id;
            n && (t = g(this), t.pass ? (p(a, t.tip), s[c] = !1, i.ajax({
                type: "post",
                url: "/user/UserAccountCheckJson.do",
                data: {userMobileNumber: n},
                dataType: "json",
                success: function (t) {
                    "code"in t && 1 == t.code ? (a ? h(a, "手机号码已注册") : e.ui.msgBox.show("手机号码已注册"), i(t$(o.regPhone)).addClass("verify_error"), t$("sendCodeBtn") && (t$("sendCodeBtn").setAttribute("disabled", !0), t$("sendCodeBtn").setAttribute("class", "btn_disabled btn col-3"))) : (s[c] = !0, p(a, "手机号码已注册"), t$("sendCodeBtn") && (r || (t$("sendCodeBtn").removeAttribute("disabled"))))
                },
                error: function () {
                }
            })) : h(a, t.tip))
        }), "regEmail"in o && t$(o.regEmail) && (t$(o.regEmail).onblur = function () {
            var t, n = this.value, r = this.id;
            n && (t = g(this), t.pass ? (p(a, t.tip), s[r] = !1, i.ajax({
                type: "post",
                url: "/user/UserAccountCheckJson.do",
                data: {userEmailAddress: n},
                dataType: "json",
                success: function (t) {
                    "code"in t && 1 == t.code ? (a ? h(a, "邮箱地址已注册") : e.ui.msgBox.show("邮箱地址已注册"), i(t$(o.regEmail)).addClass("verify_error")) : (s[r] = !0, p(a, "邮箱地址已注册"))
                },
                error: function () {
                }
            })) : h(a, t.tip))
        }))
    }, m = function (t, e) {
        var i, o, n, s = [], r = !1, c = !0, l = !0, d = !1;
        if (t$(t)) {
            i = t$(t).getElementsByTagName("input"), e && (r = "atOnce"in e ? e.atOnce : !1, c = "together"in e ? e.together : !0);
            for (var u = 0, m = i.length; m > u; u++) {
                if ("text,password,tel,number,email,date".indexOf(i[u].type) >= 0 && i[u].getAttribute("data-verify"))if (n = g(i[u]), o = n.tip, n.pass)p(a, o); else {
                    l = !1, d = !1;
                    for (var f = 0, v = s.length; v > f; f++)if (s[f] == o) {
                        d = !0;
                        break
                    }
                    if (d || s.push(o), r)return a && h(a, s), !1
                }
                o = ""
            }
            return l ? !0 : (a && h(a, s), !1)
        }
    }, h = function (t, e) {
        var i = t$(t), o = null, s = null;
        if (i) {
            o = "string" == typeof e ? [e] : e;
            for (var a = 0, r = o.length; r > a; a++)s = i.innerHTML, n = new RegExp("<p>" + o[a] + "</p>"), n.test(s) || (i.innerHTML += "<p>" + o[a] + "</p>")
        }
    }, p = function (t, e) {
        var i, o = t$(t), s = null;
        if (o) {
            i = o.innerHTML, s = "string" == typeof e ? [e] : e;
            for (var a = 0, r = s.length; r > a; a++)n = new RegExp("<p>" + s[a] + "</p>", "ig"), i = i.replace(n, "");
            o.innerHTML = i
        }
    }, g = function (t) {
        var o, n, s = t.value, a = "", r = !0;
        if (!t.getAttribute("data-verify"))return {tip: "", pass: !0};
        o = t.getAttribute("data-verify").split(",");
        for (var c = 0, l = o.length; l > c; c++)if (o[c].indexOf("+") >= 0)for (n = o[c].split("+"), j = 0, k = n.length; j < k; j++)n[k]in e.regVerify && (a += "," + e.regVerify[n[k]].tip, e.regVerify[n[k]].reg.test(s) || (i(t).addClass("verify_error"), r = !1)); else if (o[c]in e.regVerify && (a = e.regVerify[o[c]].tip, !e.regVerify[o[c]].reg.test(s))) {
            i(t).addClass("verify_error"), r = !1;
            break
        }
        return r && i(t).removeClass("verify_error"), {tip: a, pass: r}
    }, f = function () {
        t$("kaptchaImage").onclick = function () {
            var t = t$("sessionId") ? t$("sessionId").value : "";
            this.src = "/kaptcha.jpg?sessionId=" + t + "&" + Math.floor(100 * Math.random())
        }, t$("kaptchaCode").onfocus = function () {
            t$("kaptchaImage").style.display = "inline"
        }
    }, v = function (o, n, s) {
        var a = c;
        i("#" + o).bind("click", function () {
            var l = t$(n).value, d = "", u = {};
            if (!e.regVerify.mobile.reg.test(l))return void e.ui.msgBox.show(e.regVerify.mobile.tip);
            if (u.userMobileNumber = l, s) {
                if (d = t$(s) ? t$(s).value : "", !e.regVerify.kaptcha.reg.test(d))return void e.ui.msgBox.show(e.regVerify.kaptcha.tip);
                u.kaptchaCode = d, t$("sessionId") && (u.sessionId = t$("sessionId").value)
            }
            this.setAttribute("data-txt", this.innerHTML), i.ajax({
                type: "post",
                url: _verifyCodeUrl,
                data: u,
                dataType: "json",
                success: function (t) {
                    "code"in t && 1 == t.code ? (t$("verifyCodeLine") && (t$("verifyCodeLine").style.display = "block"), t$("btnBackPass") && (t$("btnBackPass").style.display = "block"), t$("kaptchaCodeLine") && (t$("kaptchaCodeLine").style.display = "none"), e.ui.msgBox.show("验证码已发送至您手机")) : (t$("verifyCodeLine") && (t$("verifyCodeLine").style.display = "none"), t$("btnBackPass") && (t$("btnBackPass").style.display = "none"), t$("kaptchaCodeLine") && (t$("kaptchaCodeLine").style.display = "block"), t$("kaptchaImage") && t$("kaptchaImage").click(), e.ui.msgBox.show("手机验证未通过，请确认手机是否已注册及图片验证码是否正确"))
                },
                error: function () {
                    e.ui.msgBox.show("出现错误，稍候再试")
                }
            }), this.innerHTML = "剩余" + a-- + "秒", this.setAttribute("disabled", !0), this.setAttribute("class", "btn_disabled btn col-3"), t$(n).setAttribute("readonly", !0), r = t.setInterval(function () {
                //t$(o).innerHTML = t$(o).getAttribute("data-txt") + "(" + a-- + ")", 0 >= a && (t$(o).innerHTML = t$(o).getAttribute("data-txt"), i("#sendCodeBtn").removeAttr("disabled"), t$(o).removeAttribute("class"), t$(o).removeAttribute("disabled"), t$(n).removeAttribute("readonly"), a = c, clearInterval(r))
                t$(o).innerHTML = "剩余" + a-- + "秒", 0 >= a && (t$(o).innerHTML = t$(o).getAttribute("data-txt"), i("#sendCodeBtn").removeAttr("disabled"), t$(o).removeAttribute("disabled"), t$(n).removeAttribute("readonly"), a = c, clearInterval(r))

            }, 1e3)
        })
    };
    return {setVerify: u, sendVerifyCode: v, setVerifyImg: f}
}(window, te$, $), te$.passport = function (t, e, i, o) {
    var n = function () {
        "placeholder"in document.createElement("input") || i("label.lb_placeholder").each(function () {
            var t = this, e = t$(this.getAttribute("for"));
            e && (e.value || i(this).css("display", "block"), i(e).bind("focus", function () {
                t.style.display = "none"
            }).bind("blur", function () {
                this.value || (t.style.display = "block")
            }))
        })
    };
    return {setPlaceholder: n}
}(window, te$, $), te$.address = function (t, e, i, o) {
    var n = {}, s = {}, a = {}, r = {}, c = null, l = null, d = null, u = {
        province: [e.getUrl("addressProvince"), "countryName"],
        city: [e.getUrl("addressCity"), "provinceName"],
        region: [e.getUrl("addressRegion"), "cityName"]
    }, m = !1, h = !1, p = null, g = function (t) {
        t.id in s || (s[t.id] = t)
    }, f = function (t, o) {
        var n = t$(t), s = t$(o), u = n.parentNode.getElementsByTagName("input"), g = s.getElementsByTagName("div"), f = s.getElementsByTagName("button")[0], v = function () {
            for (var t = 0, e = l.length; e > t; t++)this == l[t] ? (i(l[t]).addClass("selected"), g[t].style.display = "block") : (i(l[t]).removeClass("selected"), g[t].style.display = "none")
        };
        l = s.getElementsByTagName("b"), a.province = g[0], a.city = g[1], a.region = g[2], r.province = u[0], r.city = u[1], r.region = u[2], c = u[4], p = s, n.onclick = function () {
            h || (b("province", "中国"), h = !0), s.style.display = "block"
        }, f.onclick = function () {
            s.style.display = "none"
        }, d = f;
        for (var y = 0, $ = l.length; $ > y; y++)l[y].onclick = v;
        m = !0, t$("addressEditForm") && X.init("addressEditForm"), i(".link_delete_address").each(function () {
            i(this).bind("click", function () {
                var t = this.getAttribute("data-id"), i = {userId: e.business.user.userId};
                return t && (i.addressId = t, i.status = 0, X.post(i)), !1
            })
        })
    }, v = function (t) {
        for (var e = l.length - 1; e >= 0; e--)i(l[e]).removeClass("selected"), l[e].getAttribute("data-content") == a[t].id && (e < l.length - 1 ? i(l[e + 1]).addClass("selected") : i(l[e]).addClass("selected"))
    }, y = function (t, e) {
        for (var o = "", n = null, s = null, l = 0, d = e.list.length; d > l; l++)n = e.list[l].split("-"), o += '<span data-value="' + n[0] + '" data-id="' + n[1] + '" data-type="' + t + '">' + n[0] + "</span>";
        a[t].innerHTML = o, s = a[t].getElementsByTagName("span");
        for (var l = 0, d = s.length; d > l; l++)s[l].onclick = function (o) {
            for (var n = e.id + "-" + this.getAttribute("data-id"), l = 0, d = s.length; d > l; l++)s[l].removeAttribute("class");
            i(this).addClass("selected"), r[t] && (r[t].value = this.getAttribute("data-value"), "province" == t && (b("city", this.getAttribute("data-value"), n), r.city.value = "", r.region.value = "", a.province.style.display = "none", a.city.style.display = "block", a.region.style.display = "none", a.region.innerHTML = ""), "city" == t && (b("region", this.getAttribute("data-value"), n), r.region.value = "", a.province.style.display = "none", a.city.style.display = "none", a.region.style.display = "block"), "region" == t && (p.style.display = "none"), c.value = r.province.value + r.city.value + r.region.value, v(t))
        }
    }, b = function (t, e, o) {
        var n = {};
        if (m) {
            if (o && o in s)return void $(s[o], t, !0);
            n[u[t][1]] = e, n.action = t, a[t].setAttribute("class", "loading"), a[t].innerHTML = "", i.ajax({
                url: u[t][0],
                dataType: "jsonp",
                cache: !1,
                data: n,
                jsonp: "jsoncallback",
                jsonpCallback: "te$.address.getData"
            })
        }
    }, $ = function (t, e, i) {
        var o, n, s = [];
        if (i)return void y(e, t);
        for (var a = 0, r = t.length; r > a; a++)"city" == e && s.push(t[a].cityName + "-" + t[a].cityId), "region" == e && s.push(t[a].regionName + "-" + t[a].regionId), "province" == e && s.push(t[a].provinceName + "-" + t[a].provinceId);
        "city" == e && (0 == t.length ? "" : t[0].countryId + "-" + t[0].provinceId), "region" == e && (0 == t.length ? "" : t[0].countryId + "-" + t[0].provinceId + "-" + t[0].cityId), "province" == e && (0 == t.length ? "" : t[0].countryId), o = {
            type: e,
            id: n,
            list: s
        }, g(o), y(e, o)
    }, A = function (t) {
        var i, o = "";
        if ("code"in t && 1 == t.code) {
            if (i = "provinceList"in t.content ? t.content.provinceList : "cityList"in t.content ? t.content.cityList : "regionList"in t.content ? t.content.regionList : [], i.length > 0)return "regionName"in i[0] && (o = "region"), "cityName"in i[0] && (o = "city"), "provinceName"in i[0] && (o = "province"), a[o].removeAttribute("class"), void $(i, o);
            if (t.command in a && (a[t.command].innerHTML = ""), "region" == t.command)return a[t.command].removeAttribute("class"), r[t.command].value = "(辖区)", void d.onclick()
        }
        e.ui.msgBox.show("未能获取合适的数据")
    }, X = {
        hasInit: !1, frame: null, btnSubmit: null, check: function (t) {
            for (var i = "", o = 0, n = t.length; n > o; o++)if (i = t[o].getAttribute("data-verify")) {
                if (!(i in e.regVerify))continue;
                if (!e.regVerify[i].reg.test(t[o].value)){
                    alert(e.regVerify[i].tip);
                    return t[o].parentNode.parentNode.getElementsByTagName("i")[0].innerHTML = "<b>*</b>" + e.regVerify[i].tip, "city" == i ? t$("addressDetail").click() : t[o].focus(), !1;
                }
                t[o].parentNode.parentNode.getElementsByTagName("i")[0].innerHTML = "<b>*</b>"
            }
            return !0
        }, init: function (t) {
            t$(t) && (this.frame = t$(t), this.btnSubmit = t$("btnAddressEdit"), this.btnSubmit.onclick = function () {

                var t = X.frame.getElementsByTagName("input");
                return X.check(t) ? (X.post(X.getData(t)), !1) : !1
            }, i(".link_set_default_address").bind("click", function () {
                var t = this.getAttribute("data-id");
                return n[t].addressId = t, n[t].isDefault = 1, X.post(n[t]), !1
            }))
        }, getData: function (t) {
            for (var e = {}, i = 0, o = t.length; o > i; i++)"text" == t[i].getAttribute("type") || "hidden" == t[i].getAttribute("type") || "tel" == t[i].getAttribute("type") ? e[t[i].getAttribute("name")] = t[i].value : "checkbox" == t[i].getAttribute("type") && (t[i].checked ? e[t[i].getAttribute("name")] = 1 : e[t[i].getAttribute("name")] = 0);
            return e
        }, post: function (t) {
            i.ajax({
                url: e.getUrl("address"),
                dataType: "jsonp",
                type: "POST",
                data: t,
                cache: !1,
                jsonpCallback: "te$.address.reflash"
            })
        }
    }, w = function (i) {
        var o = t.location.protocol + "//" + t.location.host + t.location.pathname;
        "code"in i && (1 == i.code ? (t.location.pathname.indexOf("UserAddressManage") >= 0 && (t.location.search.indexOf("addressId") >= 0 ? e.ui.msgBox.show("地址修改成功", [["确定", function () {
            t.location.href = o
        }]]) : e.ui.msgBox.show("地址操作成功", [["确定", function () {
            t.location.reload()
        }]])), t.location.pathname.indexOf("confirm.do") >= 0 && (t$("addressId") && (t$("addressId").value = i.content.addressId), t$("countryModify") && (t$("countryModify").value = i.content.country), t$("cityModify") && (t$("cityModify").value = i.content.city), t$("provinceModify") && (t$("provinceModify").value = i.content.province), t$("checkoutModify") && t$("checkoutModify").submit())) : e.ui.msgBox.show("地址添加未能成功，请稍候再试"))
    };
    return {books: n, map: s, getInfo: b, getData: A, initPca: f, reflash: w}
}(window, te$, $);