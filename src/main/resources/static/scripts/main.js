//Vue实例
var app = new Vue({
	el: '#app',
	data: {
		isCollapse: false,
		dialogs: [
			{ header: 'images/b_header.jpg', content: '我有一个Style样式需要您帮忙处理以下。', time: '3' },
			{ header: 'images/b_header1.jpg', content: '中午一起吃个饭吧，我请客。', time: '15' },
			{ header: 'images/b_header.jpg', content: '需要处理一下POP展示的白边样式。', time: '18' },
			{ header: 'images/b_header.jpg', content: '下午3点开需求会议，准时参加。', time: '24' },
			{ header: 'images/b_header.jpg', content: '晚上我开车送你回家，你请我吃晚饭。', time: '45' },
		],
		currentTab: '首页',
		currentMenu:'tabmain',
		mainTabs: [
			{ id:'tabmain', name: '首页', url:'home.html' },
			//{ id:'tabuser', name: '用户管理', url:'admin/user' },
			//{ id:'tabrole', name: '角色管理', url:'admin/role' },
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
		addTab(id,tabname,url) {
			let newtab=this.mainTabs.find(t=>t.id==id)
			if(newtab){  //如果存在
				this.currentTab=newtab.name
				return
			}
			newtab={id,name:tabname,url}
			this.mainTabs.push(newtab)
			//TODO: 去异步加载html渲染,   --没想出来怎么实现,只好用iframe实现加载
			this.currentTab=tabname
		},
		removeTab(targetName) {
			if(targetName=='首页'){   //首页不可关闭
				return;
			}
			let tabs = this.mainTabs
			let activeName = this.currentTab
			let activeMenu=this.currentMenu
			if (activeName === targetName) {
			  tabs.forEach((tab, index) => {
				if (tab.name === targetName) {
				  let nextTab = tabs[index + 1] || tabs[index - 1];
				  if (nextTab) {
					activeName = nextTab.name
					activeMenu=nextTab.id
				  }
				}
			  });
			}
			
			this.currentTab = activeName
			this.currentMenu=activeMenu
			this.mainTabs = tabs.filter(tab => tab.name !== targetName)
		},
		clickTab(tab){
			this.currentMenu=this.mainTabs[tab.index*1].id
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