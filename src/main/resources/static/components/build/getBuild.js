/**
 * @author jiangyuanlin@163.com
 */
define(['vue','components/navigation/navigation','text!./getBuild.html'], function (Vue,navigation,componentTemplate) {	
	let componentProperties = {
			template: componentTemplate,
			data:function() {
				return {
					query:{},
					code:null,
					msg:null,
					data:null,
				}
			},
			methods:{
				updateRouterView:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},		
				
				back:function(){					 
					//this.$router.go(-1);
					this.updateRouterView("/components/build/pageBuild");		
				},				
			},	
			created: function () {	
				let tmpVue=this;
				this.query=this.$route.query;					
				
				let url=this.$store.state.BASE_PATH+"/api/build/getBuild?id="+this.query.id;
				let token_type=localStorage.getItem('token_type'); 
				let access_token=localStorage.getItem('access_token');
				if(token_type==null || access_token==null){
					this.updateRouterView("/components/user/login");					
				}
				fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
				}).then(function(response) {return response.json();}).then(function(result){
					tmpVue.code=result.code;
					 if(result.code==200){
						   console.log("receive=",result );			
						   tmpVue.query=result.data;
					   }else if(result.code==401){						
						   tmpVue.updateRouterView("/components/user/login");
					   }else if(result.code==403){
						   tmpVue.msg="授权失败";					
					   }else{							   
						   tmpVue.msg=result.msg;
					   }					
				}).catch(function(e) {  				
					tmpVue.msg=e;  					 
				});			
		         		    
		    },
		    watch:{
		    	query(newValue, oldValue) {
		    		console.log("newValue",newValue);
		    	}
		    }
		
				
	 	};
	 	
	 	return Vue.component('getBuild',  componentProperties);
	 	
		 
}); 


