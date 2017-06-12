
//添加任务模块
function addModel(taskId){
	var name = $(".taskName").val();
	var ptime = $(".taskPtime").val();
	var etime = $(".taskEtime").val();
	var desc = $(".taskDesc").val();
	var price = $(".taskPrice").val();
	
	ptime = new Date(ptime);
	etime = new Date(etime);
	
	var url = "/page/task_model/add";
	var datas = {
		"taskId":taskId,
		"title":name,
		"ptime":ptime,
		"etime":etime,
		"desc":desc,
		"price":price
	};
	var result = function(data){
		Popups(data.msg,function(){
			if(data.code == 200){
				$(".popup-mokuaijia,.zhezhao").addClass("dis-none");
				window.history.go(0);
			}
		});
		
	}
	sendPost(url,datas,result);
}


//发布任务信息
function fabugonggao(taskId){
	var contents = $(".contents").val();
	var url = "/page/taskmsg/add";
	var datas = {
		"taskId":taskId,
		"contents":contents
	};
	var result = function(data){
		Popups(data.msg,function(){
			if(data.code == 200){
				$(".fbgg-popup,.zhezhao").addClass("dis-none");
				window.history.go(0);
			}
		});
	}
	sendPost(url,datas,result);
}

//添加任务评价信息
function epcs(taskId){
	
	var evaluation = $('.evaluation[name="ping"]:checked').val();
	var epcsReason = $(".epcsReason").val();
	var eContents = $(".eContents").val();
	var url = "/page/epcs/pcs";
	var datas = {
		"taskId":taskId,
		"evaluation":evaluation,
		"epcsReason":epcsReason,
		"eContent":eContents
	};
	
	var result = function(data){
		Popups(data.msg,function(){
			if(data.code == 200 || data.code == 502){
				$(".popup-evaluate,.zhezhao").addClass("dis-none");
			}else{
				window.location.href = "/index";
			}
		});
	}
	sendPost(url,datas,result);
}

//添加服务商评论信息
function spcs(taskId){
	var svaluation = $('.svaluation[name="ping"]:checked').val();
	var spcsReason = $(".spcsReason").val();
	var sContents = $(".sContents").val();
	
	var url = "/page/spcs/pcs";
	var datas = {
		"svaluation":svaluation,
		"spcsReason":spcsReason,
		"sContents":sContents,
		"taskId":taskId
	}
	var result = function(data){
		Popups(data.msg,function(){
			if(data.code == 200){
				
			}
		});
	}
	sendPost(url,datas,result);
}


//添加任务付款模块
function addTaskPM(taskId){
	var name = $(".taskPmName").val();
	var price = $(".taskPmPrice").val();
	
	if(name == null || name == ""){
		Popups("请输入付款说明！");
		return;
	}
	if(price == ""){
		Popups("请输入金额！");
		return;
	}
	var zx = /^[0-9]*$/;
	if(!zx.test(price)){
		Popups("金额输入有误！");
		return;
	}
	
	var url = "/page/taskPM/insert";
	var datas = {
		"taskId":taskId,
		"name":name,
		"description":name,
		"price":price
	}
	var result = function(data){
		Popups(data.msg,function(){
			if(data.code == 200){
				$(".popup-fukuanjia,.zhezhao").addClass("dis-none");
				window.history.go(0);
			}
		});
	}
	sendPost(url,datas,result);
}

//服务商向雇主申请托管资金
function stgsq(userId,taskId,tpmId){
	if(window.confirm("确定提交申请吗？")){
		var url = "/page/taskPM/hostingApplication";
		var datas = {
			"userId":userId,
			"taskId":taskId,
			"tpmId":tpmId
		}
		var result = function(data){
			Popups(data.msg,function(){
				if(data.code == 200){
					window.history.go(0);
				}
			});
		}
		sendPost(url,datas,result);
	}
}

function payBalance(taskId) {
	var url = "/page/task/payBalance";

	var price = 0;
	var phone = "";
	var code = "";
	var payPwd = "";

	var datas = {
		"taskId" : taskId,
		"price" : price,
		"phone" : phone,
		"code" : code,
		"payPwd" : payPwd
	}
	var result = function(data) {
		Popups(data.msg, function() {
			if (data.code == 200) {
				
			}
		});
	}
	sendPost(url, datas, result);
}

var tpmId = 0;
//雇主添加节点信息并托管节点佣金按钮效果
function showTgf(id,price){
	tpmId = id;
	$(".tgPrice").val(price);
	$(window).scrollTop(1);
	$(".popup-fukuan0,.zhezhao").removeClass("dis-none");
}
function closeTgf(){
	$(".popup-fukuan0,.zhezhao").addClass("dis-none");
}

//确认托管节点佣金按钮效果

function showTg(id,price){
	tpmId = id;
	$(".tgPrice").val(price);
	$(window).scrollTop(1);
	$(".popup-fukuan1,.zhezhao").removeClass("dis-none");
}
function closeTg(){
	$(".popup-fukuan1,.zhezhao").addClass("dis-none");
}
//确认付款节点佣金按钮效果
function showPay(id,price){
	tpmId = id;
	$(".payPrice").val(price);
	$(window).scrollTop(1);
	$(".popup-fukuan2,.zhezhao").removeClass("dis-none");
}
function closePay(){
	$(".popup-fukuan2,.zhezhao").addClass("dis-none");
}

//雇主添加付款节点信息并托管节点佣金
function tg(userId,taskId){
	var desc = $(".desc").val();
	var price = $(".price").val();
	var phone = $(".phone").val();
	var phoneCode = $(".phoneCode").val();
	var payPwd = $(".payPwd").val();
	var isRemainSum = $(".isRemainSum");
	if(isRemainSum.is(":checked")){
		
		var url = "/page/contract/tg";
		var datas = {
			"userId":userId,
			"taskId":taskId,
			"desc":desc,
			"price":price,
			"phone":phone,
			"phoneCode":phoneCode,
			"payPwd":payPwd
		}
		var result = function(data){
			Popups(data.msg);
			if(data.code == 200){
				$(".popup-fukuan,.zhezhao").addClass("dis-none");	
			}
		}
		sendPost(url,datas,result);
	}else{
		Popups("使用第三方支付功能暂未开放，请选用余额支付！");
		$(".zhezhao").removeClass("dis-none");
	}
}

//雇主确认托管节点佣金
function sureTg(userId,taskId){
	var desc = $(".desc").val();
	var price = $(".price").val();
	var phone = $(".phone").val();
	var phoneCode = $(".phoneCode").val();
	var payPwd = $(".payPwd").val();
	var priceNow = $(".priceNow").html();
	var isRemainSum = $(".isRemainSum");
	if(isRemainSum.is(":checked")){
		
		var url = "/page/contract/tg";
		var datas = {
			"userId":userId,
			"taskId":taskId,
			"desc":desc,
			"price":price,
			"phone":phone,
			"phoneCode":phoneCode,
			"payPwd":payPwd,
			"priceNow":priceNow
		}
		var result = function(data){
			Popups(data.msg);
			if(data.code == 200){
				$(".popup-fukuan,.zhezhao").addClass("dis-none");	
			}else{
				
			}
		}
		sendPost(url,datas,result);
	}else{
		Popups("使用第三方支付功能暂未开放，请选用余额支付！");
		$(".zhezhao").removeClass("dis-none");
	}
}

//使用第三方支付面板显示(支付宝、微信、银联图标)中的点击上一步
function back(){
	$(".popup-fukuan").removeClass("dis-none");
    $(".popup-zhifu").addClass("dis-none");
}

//使用第三方支付面板显示(支付宝、微信、银联图标)
function dsftb(){
	$(".popup-fukuan").addClass("dis-none");
    $(".popup-zhifu").removeClass("dis-none");
}

//雇主拒绝托管节点佣金
function refuseHost(userId,taskId,tpmId){
	if(window.confirm("你确认拒绝托管该节点佣金吗？")){
		var reason = prompt('原因', '');
		if(reason == null || reason == 0){
			reason == '无';
		}
		var url = "/page/taskPM/refuseHost";
		var datas = {
			"userId":userId,
			"taskId":taskId,
			"tpmId":tpmId,
			"reason":reason
		}
		var result = function(data){
			Popups(data.msg,function(){
				if(data.code == 200){
					window.history.go(0);
				}
			});
		}
		sendPost(url,datas,result);
	}
}

//雇主拒绝支付节点佣金
function refusePay(userId,taskId,tpmId){
	if(window.confirm("你确认拒绝支付该节点佣金吗？")){
		var reason = prompt('原因', '');
		if(reason == null || reason == 0){
			reason == '无';
		}
		var url = "/page/taskPM/refusePay";
		var datas = {
			"userId":userId,
			"taskId":taskId,
			"tpmId":tpmId,
			"reason":reason
		}
		var result = function(data){
			Popups(data.msg,function(){
				if(data.code == 200){
					window.history.go(0);
				}
			});
		}
		sendPost(url,datas,result);
	}
}

//雇主托管节点佣金
function sureHost(userId,taskId){
	var phone = $(".tgPhone").val();
	var phoneCode = $(".tgPhoneCode").val();
	var payPwd = $(".tgPayPwd").val();
	var isRemainSum = $(".tgIsRemainSum");
	/*alert(userId+"\n"+taskId+"\n"+tpmId+"\n"+phone+"\n"+phoneCode+"\n"+payPwd+"\n"+isRemainSum);*/
	if(phoneCode == ""){
		Popups("请输入手机验证码！");
		return;
	}
	if(payPwd == ""){
		Popups("请输入支付密码！");
		return;
	}
	if(isRemainSum.is(":checked")){
		
		var url = "/page/taskPM/sureHost";
		var datas = {
			"userId":userId,
			"taskId":taskId,
			"tpmId":tpmId,
			"phone":phone,
			"phoneCode":phoneCode,
			"payPwd":payPwd
		}
		var result = function(data){
			Popups(data.msg,function(){
				if(data.code == 200){
					$(".popup-fukuan1,.zhezhao").addClass("dis-none");	
					window.history.go(0);
				}
			});
		}
		sendPost(url,datas,result);
	}else{
		dsftb();
	}
}


//雇主确认支付项目节点佣金
function surePay(userId,taskId){
	var phone = $(".payPhone").val();
	var phoneCode = $(".payPhoneCode").val();
	var payPwd = $(".payPayPwd").val();
	if(phoneCode == ""){
		Popups("请输入验证码！");
		return;
	}
	if(payPwd == ""){
		Popups("请输入支付密码！");
		return;
	}
	var url = "/page/taskPM/surePay";
	var datas = {
		"userId":userId,
		"taskId":taskId,
		"tpmId":tpmId,
		"phone":phone,
		"phoneCode":phoneCode,
		"payPwd":payPwd
	}
	var result = function(data){
		Popups(data.msg,function(){
			if(data.code == 200){
				$(".popup-fukuan2,.zhezhao").addClass("dis-none");
				window.history.go(0);
			}
		});
	}
	sendPost(url,datas,result);
}
