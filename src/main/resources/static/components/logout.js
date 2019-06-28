$.ajax({url:"components/logout.html", type: "GET", async: false}).done(function(componentTemplate) { 
//fetch("components/logout.html").then(function(response) {return response.text();}).then(function(componentTemplate){
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
		    	updateContainer:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},	 
		       	back: function () {	
		       		this.hide();		       	
		       		this.$router.go(-1);
		       	},
		       	logout: function () {			       		
		       		//发送请求告知服务器
		       		this.hide();
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
				 this.show();				
		    },
		};
  		
		let component = Vue.component('logout', componentProperties);
		router.addRoutes([
			{ path: '/components/logout.html', component: component }
		])
}); 