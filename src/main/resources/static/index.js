require.config({
    // baseUrl: '/',
    paths: {
    	
    	home: 'components/home',
    	login: 'components/login',
    	logout: 'components/logout',
    	pageInput: 'components/pageInput',
    	saveInput: 'components/saveInput',
    	deleteInput: 'components/deleteInput',
    	updateInput: 'components/updateInput',    	
    	updateUserPassword: 'components/updateUserPassword',       
    }

});

require([
	'index',
	'login',
	'logout',
	'pageInput',
	'saveInput',
	'saveInput',
	'deleteInput',
	'updateInput',
	
	'updateUserPassword',
	
]);  




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
			//this.updateContainer({path:"/components/pageInput.html"});		
		},
 	    router
 	});