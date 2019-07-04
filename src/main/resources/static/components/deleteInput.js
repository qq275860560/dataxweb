//$.ajax({url:"components/deleteInput.html", type: "GET", async: false}).done(function(componentTemplate) { 
//fetch("components/deleteInput.html").then(function(response) {return response.text();}).then(function(componentTemplate){
define(['text!deleteInput.html'], function (componentTemplate) {
	let componentProperties = {
			  template: componentTemplate,
			  data:function() {			
		           return {   
		        	query:null,		           
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
		       		this.$router.go(-1);
		       	},
		       	deleteInput: function () {			       		
		       		//发送请求告知服务器
		       		this.hide();		
		       		
		       		let tmpVue=this;
					let url=BASE_PATH+"/api/github/qq275860560/input/deleteInput?id="+this.query.id;
					let token_type=localStorage.getItem('token_type'); 
					let access_token=localStorage.getItem('access_token');
					if(token_type==null || access_token==null){
						this.updateRouterView("/components/login.html");					
					}
					fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
					}).then(function(response) {return response.json();}).then(function(result){
						tmpVue.code=result.code;
						 if(result.code==200){
							   console.log("receive=",result );			
							   tmpVue.updateRouterView( "/components/pageInput.html");
						   }else if(result.code==401){						
							   tmpVue.updateRouterView( "/components/login.html");
						   }else if(result.code==403){
							   tmpVue.msg="授权失败";					
						   }else{							   
							   tmpVue.msg=result.msg;//此时弹出框可能已经隐藏了
							   tmpVue.$emit('setCodeAndMsg',result.code,result.msg);//如果弹出框已经隐藏了，需要把错误消息显示在父组件中							   
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
				this.query=this.$route.query;				
		    },
		    mounted: function () {			
				 this.show();
				
		    },
		};
  		
		let component = Vue.component('deleteInput', componentProperties);
		router.addRoutes([
			{ path: '/components/deleteInput.html', component: component }
		])
}); 