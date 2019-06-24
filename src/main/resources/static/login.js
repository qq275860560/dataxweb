let login = new Vue({
            el: '#login',
            data: {											
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
    		},
            methods: {
                login:function() {
                	let username=this.username;
                    let password=this.password;                            
                    let url = "/login?username="+username+"&password="+password;                             
                    fetch(url).then(function(response) {return response.json();}).then(function(result){
                       login.code=result.code;	
  					   if(result.code==200){
  					   		console.log(result);
  						    localStorage.setItem("access_token",result.access_token );
  						    localStorage.setItem("token_type",result.token_type );		  
  						    window.top.location.href = "/index.html";
  					   }else if(result.code==400){						
  						   login.msg="登录失败";
					   }else if(result.code==401){						
  						   login.msg="认证失败";
					   }else if(result.code==403){
						   login.msg="授权失败";					
					   }else{
						   login.msg=result.msg;
					   }  				
  				   }).catch(function(e) {  				
  					 login.msg=e;  					 
  				   });
                        
                }
            },		
    		created: function () {
    	        //取出token，如果不为空，校验token，如果校验成功，重定向到/index.html,
    	    }
        });