var index = new Vue({
	el: '#index',
	data: {	
		home:{name:"数据交换平台",url:"/pages/home/index.html"},
		menuArray:[	
			{name:"插件管理",url:"/plugin.html"},
			{name:"输入流管理",url:"/pages/input/pageInput.html"},
			{name:"输出流管理",url:"/output.html"},
			{name:"任务管理",url:"/job.html"},
			{name:"执行日志管理",url:"/build.html"},
		],
		updateUserPassword:{name:"修改密码",url:"/pages/user/updateUserPassword.html"},
	},
	
	methods: {		
		clickMenu(url) {
			$("#content").load(url);
		},		
		logout: function () {	
			localStorage.clear();
			window.top.location.replace("/login.html"); 
			//发送请求告知服务器			
		}		
	},
	created: function () {			
		
    }
});
index.clickMenu(index.home.url);



