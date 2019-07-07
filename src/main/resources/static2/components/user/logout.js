/**
 * @author jiangyuanlin@163.com
 */
define(['vue','text!./logout.html'], function (Vue,componentTemplate) {			 
	let componentProperties = {
			  template: componentTemplate,
			  data:function() {			
		           return {            	
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
		       	back: function () {	
		       		this.hide();			       	
		       		$('#logoutModal').modal('hide');
		       	},
		       	logout: function () {			       		
		       		//发送请求告知服务器
		       		this.hide();
		       		localStorage.clear();	
		       		this.updateRouterView("/components/user/login");			       					
		       	},  
		        hide:function (){
		    	    $('#logoutModal').modal('hide');		    	    
		    	},
		    	show:function (){
			    	$('#logoutModal').modal('show');
			    	$(".modal-backdrop").removeClass("modal-backdrop");//TODO 如果没有这一行代码则有遮罩，暂时只想到这个办法，
			    }
		    },		
			created: function () {				 
				  			
		    },
		    mounted: function () {			
				 this.hide();	
				 
		    },
		};
  		
		return Vue.component('logout', componentProperties);	
}); 