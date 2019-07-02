define(['vue','components/user/logout','text!./navigation.html'], function (Vue,logout,componentTemplate) {	
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
 					this.updateRouterView("/components/user/updateUserPassword");
 				},
 				logout:function(){ 				
 					 this.$refs.logout.show();
 				}
		       	
		    },		
			created: function () {				 
				  			
		    },
		    mounted: function () {
		    	
								
		    },
		};
  		
		return Vue.component('navigation', componentProperties);
		
}); 