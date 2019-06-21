//Vue实例
var app = new Vue({
	el: '#app',
	data: {
		isCollapse: false,	
		tabId:'home',
		tabName: '首页',				
		tabs: [
			{ id:'home', name: '首页', url:'home.html' }	
		]
	},
	mounted() {

	},
	methods: {
		handleOpen(key, keyPath) {
			//console.log(key, keyPath);
		},
		handleClose(key, keyPath) {
			//console.log(key, keyPath);
		},
		toggleCallapse() {  //左侧菜单的展开和折叠
			this.isCollapse = !this.isCollapse;
		},
		addTab(id,tabName,url) {
			let tmpTab=this.tabs.find(tab=>tab.id==id);
			if(!tmpTab){  
				tmpTab={id,name:tabName,url};
				this.tabs.push(tmpTab);				 
			}			
			//TODO: 去异步加载html渲染 
			this.tabName=tabName;
		},
		removeTab(tabName) {
			if(tabName=='首页'){   
				return;
			}			
			if (  tabName != this.tabName) {
				this.tabs = this.tabs.filter(tab => tab.name !== tabName)
				return;
			}
	 
			let index ;	 
			for(let i=0;i<this.tabs.length;i++){
				if(this.tabs[i].name==tabName){
					index=i;
					break;
				}
			}
			
			let nextActiveIndex;
			if(index<this.tabs.length-1){
				nextActiveIndex=index+1;				 
			}else{
				nextActiveIndex=index-1;
			}
			this.tabName = this.tabs[nextActiveIndex].name;
			this.tabId=this.tabs[nextActiveIndex].id;
			this.tabs.splice(index,1);
			// this.tabs = this.tabs.filter(tab => tab.name !== tabName)			
		},
		clickTab(tab){
			this.tabId=this.tabs[tab.index*1].id
		},
		//退出登录
		logout: function () {			
			var _this = this;		
			this.$confirm('确认退出吗?', '提示', {
				//type: 'warning'
			}).then(() => {
				axios.get('/logout?t=' + (+new Date)).then(function (data) {
					window.top.location.replace("login.html");  
				});
			}).catch(() => {
			});
		}
	},
	computed: {

	},
	watch: {

	}
});