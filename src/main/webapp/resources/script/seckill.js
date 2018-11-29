//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法)

var seckill ={
		//封装秒杀相关ajax的url
	URL:{
		now : function(){
			return '../time/now';
		},
        exposer: function (seckillId) {
            return '../' + seckillId + '/exposer';
        },
        execution: function (seckillId, md5) {
            return '../' + seckillId + '/' + md5 + '/execution';
        }
	},
//验证手机号
validatePhone: function (phone) {
    if (phone && phone.length == 11 && !isNaN(phone)) {
        return true;//直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
    } else {
        return false;
    }
},
//时间判断方法
countDown : function(seckillId,nowTime,startTime,endTime){
	console.log(seckillId + '_' + nowTime + '_' + startTime + '_' + endTime);
	var seckillBox = $('#seckill-box');//缺了个#号
	if(nowTime > endTime){
		console.log("秒杀结束");
		seckillBox.html("秒杀结束")
	}else if(nowTime < startTime){
		console.log("秒杀倒计时");
		var killTime = new Date(startTime + 1000);//todo 防止时间偏移
        seckillBox.countdown(killTime, function (event) {
            //时间格式
            var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒 ');
            seckillBox.html(format);
        }).on('finish.countdown', function () {
        //时间完成后回调事件
        //获取秒杀地址,控制现实逻辑,执行秒杀
        console.log('______fininsh.countdown');
        seckill.handlerSeckill(seckillId, seckillBox);
        });
	} else{
		//秒杀开始
		seckill.handlerSeckill(seckillId, seckillBox);
	}
	},
		//时间吻合，秒杀开始
	handlerSeckill : function(seckillId,node){
		node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');//添加按钮
		//发送请求，判断是否能够执行秒杀
		$.post(seckill.URL.exposer(seckillId),{},function(result){
			if(result && result['success']){//内部操作是否正常
				console.log("内部正常");
				var exposer = result['data'];
				if(exposer['exposed']){//秒杀是否开启，单词错误
					console.log("秒杀按钮");
					var md5 = exposer['md5'];
					var killUrl = seckill.URL.execution(seckillId, md5);
					console.log("killUrl: " + killUrl);
					//绑定一次点击事件
				    $('#killBtn').one('click', function () {
                        //执行秒杀请求
                        //1.先禁用按钮
                        $(this).addClass('disabled');//,<-$(this)===('#killBtn')->
                        //2.发送秒杀请求执行秒杀
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
					node.show();
				}else{
					console.log("浏览器记时偏差");
	                   //未开启秒杀(浏览器计时偏差)
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countDown(seckillId, now, start, end);
				}
			}else{
				console.log("result"+result)
			}
		});
		//如果能够执行，显示按钮，绑定点击事件(禁用按钮，发送请求，根据请求结果执行对应方法)
	},
//详情页秒杀逻辑
detail: {
	//详情页初始化
    init: function (params) {
        //手机验证和登录,计时交互
        //规划我们的交互流程
        //在cookie中查找手机号
        var userPhone = $.cookie('userPhone');
        //验证手机号
        if (!seckill.validatePhone(userPhone)) {
            //绑定手机 控制输出
            var killPhoneModal = $('#killPhoneModal');
            killPhoneModal.modal({
                show: true,//显示弹出层
            	backdrop: 'static',//禁止位置关闭
                keyboard: false//关闭键盘事件
            });

            $('#killPhoneBtn').click(function () {
                var inputPhone = $('#killPhoneKey').val();
                console.log("inputPhone: " + inputPhone);
                if (seckill.validatePhone(inputPhone)) {
                    //电话写入cookie(7天过期)
                    $.cookie('userPhone', inputPhone, {expires: 7, path: '/seckill'});
                    //验证通过　　刷新页面
                    window.location.reload();
                } else {
                    //todo 错误文案信息抽取到前端字典里
                    $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                }
            });
        	}
        //记时交互
        var startTime = params['startTime'];
        var endTime = params['endTime'];
        var seckillId = params['seckillId'];
        $.get(seckill.URL.now(), {}, function (result) {
            if (result && result['success']) {
            	
                var nowTime = result['data'];
                //时间判断 计时交互
                seckill.countDown(seckillId, nowTime, startTime, endTime);
            } else {
                console.log('result: ' + result);
                alert('result: ' + result);
            }
        });
    }
},
	

	
	
}