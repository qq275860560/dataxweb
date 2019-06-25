$.ajax({url:"/components/logout.html", type: "GET", async: false}).done(function(componentTemplate) { 
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
		    	updateContainer:function(path) {
	         		console.log("path",path);
	         		this.$router.push({path:path});	     
				},	 
		       	cancel: function () {	
		       		$('#myModal').modal('hide');		       	
		       		this.$router.go(-1);
		       	},
		       	logout: function () {			       		
		       		//发送请求告知服务器
		       		$('#myModal').modal('hide');
		       		localStorage.clear();	
		       		this.updateContainer("/components/login.html");			       					
		       	},  
		        hide:function (){
		    	   $('#myModal').modal('hide');
		    	},
		    	show:function (){
			    	$('#myModal').modal('show');
			    }
		    },		
			created: function () {				 
				  			
		    },
		    mounted: function () {			
				 //this.show();				
		    },
		};
  		
		let component = Vue.component('logout', componentProperties);
		router.addRoutes([
			{ path: '/components/logout.html', component: component }
		])
}); 