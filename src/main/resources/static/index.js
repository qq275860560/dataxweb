const BASE_PATH ="http://localhost:8080";
const router = new VueRouter() ;

require.config({paths: {text: 'https://cdn.bootcss.com/require-text/2.0.12/text',}});

require.config({paths: {navigation: 'components/navigation'}});
require(['navigation']); 

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
  
define(['navigation'], function () {
const app = new Vue({
 		el: '#app', 		
 		methods: {	
 			    updateRouterView:function(path) {
	         		console.log("path",path);
	         		this.$router.push({path:path});	     
				},
				
 		},
		created:function(){		
		},
 	    router
 	});
});