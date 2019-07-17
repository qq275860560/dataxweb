/**
 * @author jiangyuanlin@163.com
 */
// 定义依赖
require.config(
        {
        	baseUrl: "",
            paths: {             
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
                	'css!index.css',
                	'css!https://cdn.bootcss.com/bootstrap-validator/0.5.3/css/bootstrapValidator.css',
                	'css!https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css',
                	
                	                	
                	
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
	router.addRoutes([	
		{ 
			path: '/components/home/home', 
			component: resolve => require(['./components/home/home'],resolve) 
		},
		{ 
			path: '/components/user/login', 
			component: resolve => require(['./components/user/login'],resolve) 
		},
		
		{ 
			path: '/components/input/pageInput', 
			component: resolve => require(['./components/input/pageInput'],resolve) 
		},
		{ 
			path: '/components/input/saveInput', 
			component: resolve => require(['./components/input/saveInput'],resolve),
			children: [
		          {
		            path: '/components/input/saveMysqlReader',		   
		            component: resolve => require(['./components/input/saveMysqlReader'],resolve),
		          },
		          {
		        	  path: '/components/input/saveTxtFileReader',		   
			          component: resolve => require(['./components/input/saveTxtFileReader'],resolve),
			      },
			      {
		        	  path: '/components/input/saveHttpReader',		   
			          component: resolve => require(['./components/input/saveHttpReader'],resolve),
			      },
			      {
		        	  path: '/components/input/saveFtpReader',		   
			          component: resolve => require(['./components/input/saveFtpReader'],resolve),
			      },
		    ],
		},
		{ 
			path: '/components/input/updateInput', 
			component: resolve => require(['./components/input/updateInput'],resolve),
			children: [
		          {
		            path: '/components/input/updateMysqlReader',		   
		            component: resolve => require(['./components/input/updateMysqlReader'],resolve),
		          },
		          {
			            path: '/components/input/updateTxtFileReader',		   
			            component: resolve => require(['./components/input/updateTxtFileReader'],resolve),
			      },
			      {
			            path: '/components/input/updateHttpReader',		   
			            component: resolve => require(['./components/input/updateHttpReader'],resolve),
			      },
			      {
			            path: '/components/input/updateFtpReader',		   
			            component: resolve => require(['./components/input/updateFtpReader'],resolve),
			      },
		    ],
		},		
		{ 
			path: '/components/output/pageOutput', 
			component: resolve => require(['./components/output/pageOutput'],resolve) 
		},
		{ 
			path: '/components/output/saveOutput', 
			component: resolve => require(['./components/output/saveOutput'],resolve),
			children: [
		          {
		            path: '/components/output/saveMysqlWriter',		   
		            component: resolve => require(['./components/output/saveMysqlWriter'],resolve),
		          },
		          {
			            path: '/components/output/saveTxtFileWriter',		   
			            component: resolve => require(['./components/output/saveTxtFileWriter'],resolve),
			      },
			      {
			            path: '/components/output/saveHttpWriter',		   
			            component: resolve => require(['./components/output/saveHttpWriter'],resolve),
			      },
			      {
			            path: '/components/output/saveFtpWriter',		   
			            component: resolve => require(['./components/output/saveFtpWriter'],resolve),
			      },
		    ],
		},		
		{ 
			path: '/components/output/updateOutput', 
			component: resolve => require(['./components/output/updateOutput'],resolve), 
			children: [
		          {
		            path: '/components/output/updateMysqlWriter',		   
		            component: resolve => require(['./components/output/updateMysqlWriter'],resolve),
		          },
		          {
			            path: '/components/output/updateTxtFileWriter',		   
			            component: resolve => require(['./components/output/updateTxtFileWriter'],resolve),
			      },
			      {
			            path: '/components/output/updateHttpWriter',		   
			            component: resolve => require(['./components/output/updateHttpWriter'],resolve),
			      },
			      {
			            path: '/components/output/updateFtpWriter',		   
			            component: resolve => require(['./components/output/updateFtpWriter'],resolve),
			      },
		    ],
		},
		
		{ 
			path: '/components/transformer/pageTransformer', 
			component: resolve => require(['./components/transformer/pageTransformer'],resolve) 
		},
		{ 
			path: '/components/transformer/saveTransformer', 
			component: resolve => require(['./components/transformer/saveTransformer'],resolve) 
		},
		{ 
			path: '/components/transformer/updateTransformer', 
			component: resolve => require(['./components/transformer/updateTransformer'],resolve) 
		},
		
		
		{ 
			path: '/components/job/pageJob', 
			component: resolve => require(['./components/job/pageJob'],resolve) 
		},
		{ 
			path: '/components/job/saveJob', 
			component: resolve => require(['./components/job/saveJob'],resolve) 
		},
		{ 
			path: '/components/job/updateJob', 
			component: resolve => require(['./components/job/updateJob'],resolve) 
		},
		
		
		{ 
			path: '/components/build/pageBuild', 
			component: resolve => require(['./components/build/pageBuild'],resolve) 
		},
		{ 
			path: '/components/build/getBuild', 
			component: resolve => require(['./components/build/getBuild'],resolve) 
		},
			
	
	]);
    return router ;
 
});

// 定义状态管理
define("store",function (require) {	
	var Vue = require('vue');  
	var Vuex = require("vuex")
	Vue.use(Vuex); 
	
	
	let store=new Vuex.Store({
	    state:{
	    	BASE_PATH:""
	    },
	    mutations:{
	      
	    },
	    actions:{
	      
	    },
	    getters:{
	       
	    }
	})
	return store;
});

//初始化
define(['vue','router','store'],function (Vue,router,store) { 
	Vue.config.debug = true;
    Vue.config.devtools = true;	
   new Vue({
			el: '#app', 
			store:store,
			template: '<router-view/>',	
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
				this.updateRouterView("/components/home/home");
            	//alert( this.$store.state.BASE_PATH);
			},
			router
	});
});
 


 