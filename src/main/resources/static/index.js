const BASE_PATH ="http://localhost:8080";
const router = return new vueRouter();

require.config({paths: {text: 'https://cdn.bootcss.com/require-text/2.0.12/text',}});

require.config({paths: {home: 'components/home'}});
require(['home']); 

require.config({paths: {login: 'components/login'}});
require(['login']);

require.config({paths: {logout: 'components/logout'}});
require(['logout']);

require.config({paths: {pageInput: 'components/pageInput'}});
require(['pageInput']);

require.config({paths: {saveInput: 'components/saveInput'}});
require(['saveInput']);

require.config({paths: {deleteInput: 'components/deleteInput'}});
require(['deleteInput']);

require.config({paths: {updateInput: 'components/updateInput'}});   
require(['updateInput']);

require.config({paths: {updateUserPassword: 'components/updateUserPassword'}});	
require(['updateUserPassword']);	
  

const app = new Vue({
 		el: '#app', 		
 		methods: {	
 			    updateContainer:function(path) {
	         		console.log("path",path);
	         		this.$router.push({path:path});	     
				},
				updateUserPassword:function(){
 					this.updateContainer("/components/updateUserPassword.html");
 				},
 				logout:function(){
 					//this.$refs.logout.show();    //如果使用此方式，主页面先引入 <logout  ref="logout"></logout>，之后去除退出组件back中的this.$router.go(-1);和created 
 					this.updateContainer("/components/logout.html");
 				}
 		},
		created:function(){
			//获取用户名，头像，菜单及其权限等		
		},
 	    router
 	});