define(['vue','text!./deleteJob.html'], function (Vue,componentTemplate) {	
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
		    methods: {
		    	updateRouterView:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},	 
		       	back: function () {	
		       		this.hide();		       	
		       		$('#deleteJobModal').modal('hide');
		       	},
		       	deleteJob: function () {			       		
		       		//发送请求告知服务器
		       		this.hide();		
		       		
		       		let tmpVue=this;
		       		let url=this.$store.state.BASE_PATH+"/api/github/qq275860560/job/deleteJob?id="+this.query.id;
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
							   tmpVue.$emit('pageJob',tmpVue.query.pageNum,tmpVue.query.pageSize);							
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
		        hide:function (){
		    	   $('#deleteJobModal').modal('hide');
		    	},
		    	show:function (query){
		    		this.query=query;
		    		console.log("query",query);
		    		$('#deleteJobModal').modal('show');
			    	$(".modal-backdrop").removeClass("modal-backdrop");//TODO 如果没有这一行代码则有遮罩，暂时只想到这个办法，

			    }
		    },		
			created: function () {				 
						
		    },
		    mounted: function () {			
		    	this.hide();
				
		    },
		};
  		
		return Vue.component('deleteJob', componentProperties);	
}); 