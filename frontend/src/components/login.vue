<template>
    <div id="container">
	<form id="login" class="form-signin" style="max-width: 250px; margin: 0 auto;">
		<h2 class="form-signin-heading">密码登录</h2>
		<div>
			<input type="text" v-model="username" class="form-control" placeholder="用户名" required autofocus>
		</div>
		<div>
			<input type="password" v-model="password" class="form-control" placeholder="密码" required>
		</div>
		<div class="checkbox">
			<label>
				<input type="checkbox" value="remember-me"> 记住登录
			</label>
		</div>
 
		<button type="button" class="btn btn-primary" @click="login()">登录</button>
		  
		<div v-if="code && code!=200" class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true" @click="code=null;msg=null">
                &times;
            </button>
            	{{msg}}
        </div>
	</form>	
	</div>
</template>

<script>
export default {
	name:'login',
  	data:function() {			
            return {											
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
        	updateContainer:function(path) {
          		console.log("path",path);
          		this.$router.push({path:path});
        	},
            login:function() {
                let login=this;
            	let username=this.username;
                let password=this.password;                            
                let url = "http://localhost:8080/login?username="+username+"&password="+password;                             
                fetch(url).then(function(response) {return response.json();}).then(function(result){
                   login.code=result.code;	
				   if(result.code==200){
				   		console.log(result);
					    localStorage.setItem("access_token",result.access_token );
					    localStorage.setItem("token_type",result.token_type );		  
					    login.updateContainer("/components/index.html");
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
}
</script>

<style>

</style>
