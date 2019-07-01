 //require define 函数 start
define(['jquery','vue','../js/ajaxhelp'], function($,Vue) {
	catecallback = function(data) {
		//数据
		var data = {
			list: data,
		};
		//定义组件 模板 数据 方法
		var header = Vue.extend({
			template:  '<div class="header">\
							<div class="header-main">\
								<ul>\
									<li v-for="i in list">\
										<a v-bind:href="i.url">\
											{{i.name}}\
										</a>\
									</li>\
								</ul>\
							</div>\
						</div>',
			data: function() {
				return data;
			},
			methods: {
				show: function() {

				}
			},
		});
		// 注册组件标签 <tq-category> 绑定组件 
		Vue.component('tq-category', header);
		//实例化
		new Vue({
			el: '#cdata'
		});
	}
    var ajaxHelper=new AjaxHelper();
	param={apiurl:'getFristCategorys'}
	var apiurl="getFristCategorys";
	ajaxHelper.post(apiurl, param, catecallback);
});