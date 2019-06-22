//Vue实例
var app = new Vue({
	el: '#app',
	data: {			
		tabId:'home',
		tabName: '首页',				
		url:'home.html',
		menuArray:[
			{id:"home",name:"首页",url:"/home.html"},
			{id:"plugin",name:"插件管理",url:"/plugin.html"},
			{id:"input",name:"输入流管理",url:"/input.html"},
			{id:"output",name:"输出流管理",url:"/output.html"},
			{id:"job",name:"任务管理",url:"/job.html"},
			{id:"build",name:"执行日志管理",url:"/build.html"},
		]
	},
	
	methods: {		
		clickMenu(url) {	
			$("#content").load(url);
		},	
		//退出登录
		logout: function () {			
			var _this = this;		
			this.$confirm('确认退出吗?', '提示', {
				//type: 'warning'
			}).then(() => {		
				localStorage.clear();
				window.top.location.replace("login.html"); 
				//告知服务器
			}).catch(() => {
			});
		},
		created: function () {
	        
	    }
		
	}
});


