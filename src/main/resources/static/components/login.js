$.ajax({url:"components/login.html", type: "GET", async: false}).done(function(componentTemplate) { 
//fetch("components/login.html").then(function(response) {return response.text();}).then(function(componentTemplate){  		 
	let componentProperties = {
			  template: componentTemplate,
			  data:function() {			
		           return {
		        	    fromPath:null,
		        	    username:"admin",
			           	password:"123456",
			       		code:null,
			       		msg:null,
			       		data:{
			       			pageNum:1,
			       			pageSize:10,
			       			total:null,
			       			pageList:null,
			       		}    			
		   		}
		   	},
		    methods: {
		    	updateContainer:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				}, 
		       	login:function() {
		       		let tmpVue=this;
		        	let username=this.username;
		            let password=this.password;                            
		            let url = API_DOMAIN+"/login?username="+username+"&password="+password;                             
		            fetch(url).then(function(response) {return response.json();}).then(function(result){
		            	tmpVue.code=result.code;	
					   if(result.code==200){
					   		console.log(result);
						    localStorage.setItem("access_token",result.access_token );
						    localStorage.setItem("token_type",result.token_type );						
						    tmpVue.hide();								
				       		if(tmpVue.fromPath=="/components/logout.html"){					       			
				       			tmpVue.updateContainer("/");
				       		}else{					       	
				       			//tmpVue.updateContainer(tmpVue.fromPath);
				       			tmpVue.$router.go(-1);
				       		}						    
					   }else if(result.code==400){						
							   tmpVue.msg="登录失败";
					   }else if(result.code==401){						
						   tmpVue.msg="认证失败";
					   }else if(result.code==403){
						   tmpVue.msg="授权失败";					
					   }else{
						   tmpVue.msg=result.msg;
					   }  				
					   }).catch(function(e) {  				
						   tmpVue.msg=e;  					 
					   });
		                
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
		    	 //取出token，如果不为空，校验token，如果校验成功，重定向到/index.html,				
		    	 this.show();
		    },
		    beforeRouteEnter(to, from, next) {		    					
			    next(vm=>{
			    	vm.fromPath=from.path;
			    	console.log("login beforeRouteEnter fromPath",vm.fromPath);	
			    });
			}
		};
  		
		let component = Vue.component('login', componentProperties);
		router.addRoutes([
			{ path: '/components/login.html', component: component }
		])
}); 