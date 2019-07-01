define(['text!navigation.html'], function (componentTemplate) {  		 
	let componentProperties = {
			  template: componentTemplate,
			  data:function() {			
		           return {
		        	username:"管理员",   
		   			code:null,
		   			msg:null,
		   			data:null,    			
		   		}
		   	},
		    methods: {
		    	updateRouterView:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},	
				updateUserPassword:function(){
 					this.updateRouterView("/components/updateUserPassword.html");
 				},
 				logout:function(){
 					this.updateRouterView("/components/logout.html");
 				}
		       	
		    },		
			created: function () {				 
				  			
		    },
		    mounted: function () {			
								
		    },
		};
  		
		let component = Vue.component('navigation', componentProperties);
		router.addRoutes([
			{ path: '/components/navigation.html', component: component }
		])
}); 