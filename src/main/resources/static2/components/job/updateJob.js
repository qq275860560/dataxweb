define(['vue','components/navigation/navigation','text!./updateJob.html'], function (Vue,navigation,componentTemplate) {	
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
				updateJob:function(){					
					if(this.check()==false) return false;
					let tmpVue=this;
					let url=this.$store.state.BASE_PATH+"/api/github/qq275860560/job/updateJob?id="+this.query.id+"&name="+this.query.name+"&inputId="+this.query.inputId+"&outputId="+this.query.outputId;
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
							   tmpVue.updateRouterView( "/components/job/pageJob");
						   }else if(result.code==401){						
							   tmpVue.updateRouterView( "/components/user/login");
						   }else if(result.code==403){
							   tmpVue.msg="授权失败";					
						   }else{							   
							   tmpVue.msg=result.msg;
						   }					
					}).catch(function(e) {  				
						tmpVue.msg=e;  					 
					});			
					
				},
				check:function(){					
					//$("#form").bootstrapValidator("validate");
					$("#form").data("bootstrapValidator").validate();
					return $("#form").data("bootstrapValidator").isValid();		
				},
				back:function(){
					 this.updateRouterView("/components/job/pageJob");
					//this.$router.go(-1)	
				},				
			},	
			created: function () {	
				let tmpVue=this;
				this.query=this.$route.query;					
				
				let url=this.$store.state.BASE_PATH+"/api/github/qq275860560/job/getJob?id="+this.query.id;
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
			mounted:function(){
				//TODO 远程校验名称唯一性debugger
				$('#form').bootstrapValidator({
		            message: 'This value is not valid',
		            feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		            fields: {
		            	name: {
		                    message: '任务名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '任务名称不能为空'
		                        }
		                    }
		                },	
		                inputId: {
		                    message: '输入流Id验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '输入流Id不能为空'
		                        }		                        
		                    }
		                },
		                outputId: {
		                    message: '输出流Id验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '输出流Id不能为空'
		                        }		                        
		                    }
		                },	         
		                
		            }
		        });			
			}			
	 	};
	 	
	 	return Vue.component('updateJob',  componentProperties);
	 	
		 
}); 

