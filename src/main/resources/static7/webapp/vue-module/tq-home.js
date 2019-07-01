
define(['jquery','vue','../js/ajaxhelp'], function($,Vue) {
	var profileData = {
            name: 'Hello World!',
			profile_img: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1140958647,2055517506&fm=116&gp=0.jpg',
			profile:'头像指肩部以上的人像，在QQ、YY、贴吧、微博、微信等网络站点里表现自己个性、品位、喜好等的图片也叫头像。可截取大小自定义上传。或者规定头像，例如250*250的，就是规定头像。'
        }
		// 创建一个 Vue 实例或 "ViewModel"
        // 它连接 View 与 Model
        new Vue({
            el: '#vue-profile',
            data: profileData
        });
	callback = function(data) {
        new Vue({
            el: '#app',
            data: data
        });
	}
	var ajaxHelper=new AjaxHelper();
	var param={apiurl:'homepage',memberid:1271,cid:2,page:1}
	var apiurl="homepage";
	ajaxHelper.post(apiurl, param, callback);
});