/**
 * @author jiangyuanlin@163.com
 */
define(['vue','text!./login.html'], function (Vue,componentTemplate) {	
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
		    	updateRouterView:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				}, 
		       	login:function() {
		       		let tmpVue=this;
		        	let username=this.username;
		            let password=this.password;                            
		            let url = this.$store.state.BASE_PATH+"/login?username="+username+"&password="+password;                             
		            fetch(url).then(function(response) {return response.json();}).then(function(result){
		            	tmpVue.code=result.code;	
					   if(result.code==200){
					   		console.log(result);
						    localStorage.setItem("access_token",result.access_token );
						    localStorage.setItem("token_type",result.token_type );						
						    if(!tmpVue.fromPath){
			       				tmpVue.fromPath="/";
			       			}
				       		if(tmpVue.fromPath=="/components/user/logout"){					       			
				       			tmpVue.updateRouterView("/components/home/home");
				       		}else{					       	
				       			//tmpVue.$router.go(-1);				       			
				       			tmpVue.updateRouterView(tmpVue.fromPath);
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
		      
		    },		
			created: function () {				 
				  			
		    },
		    mounted: function () {			
		    	 //取出token，如果不为空，校验token，如果校验成功，重定向到/components/home.html,
			 this.login();//自动登录
		    	
		    },
		    beforeRouteEnter(to, from, next) {		    					
			    next(vm=>{
			    	vm.fromPath=from.path;
			    	console.log("login beforeRouteEnter fromPath",vm.fromPath);	
			    });
			}
		};
  		
		return Vue.component('login', componentProperties);		
}); 