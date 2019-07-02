define(['require','vue','router','text!components/navigation/navigation.html'], function (require,Vue,router,componentTemplate) {  		 
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
  		
		return Vue.component('navigation', componentProperties);
		
}); 