// 定义依赖
require.config(
        {
        	baseUrl: "",
            paths: {
                jquery: 'https://cdn.bootcss.com/jquery/3.4.1/jquery',
                bootstrap : 'https://cdn.bootcss.com/twitter-bootstrap/3.3.7/js/bootstrap',
                bootstrapValidator:'https://cdn.bootcss.com/bootstrap-validator/0.5.3/js/bootstrapValidator.js',
                moment:'https://cdn.bootcss.com/moment.js/2.24.0/moment-with-locales.js',
                bootstrapDatetimepicker:'https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js',
				vue: 'https://cdn.bootcss.com/vue/2.6.10/vue',
				vueRouter: 'https://cdn.bootcss.com/vue-router/3.0.6/vue-router',
				vuex: 'https://cdn.bootcss.com/vuex/3.1.1/vuex',
				text: 'https://cdn.bootcss.com/require-text/2.0.12/text',
				css: 'https://cdn.bootcss.com/require-css/0.1.10/css',// 写到map中不能使用，不知道为何
			},
            map: {
            	  
            },
            shim: {
                vue : [
                	'css!https://cdn.bootcss.com/twitter-bootstrap/3.3.7/css/bootstrap.css',
                	'css!https://cdn.bootcss.com/bootstrap-validator/0.5.3/css/bootstrapValidator.css',
                	'css!https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css'
                ]
            }
        }
    ); 
 


// 定义路由
define("router",function (require) {	
	var Vue = require('vue');  
	var VueRouter = require('vueRouter');  
	Vue.use(VueRouter); 
	
    var router = new VueRouter();
	router.addRoutes([	{ path: '/components/home/home', component: resolve => require(['./components/home/home'],resolve) }	]);
    return router ;
 
});

// 定义状态管理
define("store",function (require) {	
	var Vue = require('vue');  
	var store = require("vuex")
	Vue.use(store); 
	return store;
});

//初始化
define(['require','vue','router','store'],function (require,Vue,router,store) { 
	Vue.config.debug = true;
    Vue.config.devtools = true;	
   new Vue({
			el: '#app', 
			store:store,
			template: '<div><a href="javascript:void(0)" @click="home"><i class="fa fa-table"></i>home</a><router-view></router-view></div>',	
			methods: {
				 updateRouterView:function(path) {
	         		console.log("path",path);            
	         		this.$router.push({path:path});	     
				},
				home:function(){
					this.updateRouterView("/components/home/home");		
				},
					
			},
			created:function(){		
			},
            mounted:function(){
				// updateRouterView("/components/home/home");
			},
			router
	});
});

 
