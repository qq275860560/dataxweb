/**
 * @author jiangyuanlin@163.com
 */
define(['vue','components/navigation/navigation','text!./updateTransformer.html'], function (Vue,navigation,componentTemplate) {	
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
				updateTransformer:function(){					
					if(this.check()==false) return false;
					let tmpVue=this;
					let url=this.$store.state.BASE_PATH+"/api/transformer/updateTransformer?id="+this.query.id+"&name="+this.query.name+"&type="+this.query.type+"&parameterCode="+this.query.parameterCode+"&parameterExtraPackage="+this.query.parameterExtraPackage;
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
							   tmpVue.updateRouterView( "/components/transformer/pageTransformer");
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
					$("#form").data("bootstrapValidator").resetForm();
					$("#form").data("bootstrapValidator").validate();
					return $("#form").data("bootstrapValidator").isValid();		
				},
				back:function(){					 
					this.$router.go(-1)	
				},				
			},	
			created: function () {	
				let tmpVue=this;
				this.query=this.$route.query;					
				
				let url=this.$store.state.BASE_PATH+"/api/transformer/getTransformer?id="+this.query.id;
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
		                    message: '交换清洗名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '交换清洗名称不能为空'
		                        }
		                    }
		                },	
		                type: {
		                    message: '交换清洗类型验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '交换清洗类型不能为空'
		                        },
		                        stringLength: {
		                            min: 6,
		                            max: 18,
		                            message: '交换清洗类型长度必须在6到18位之间'
		                        },
		                        regexp: {
		                            regexp: /^[a-zA-Z0-9_]+$/,
		                            message: '交换清洗类型只能包含大写、小写、数字和下划线'
		                        }
		                    }
		                },
		                parameterCode: {
		                    message: '用户名验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '用户名不能为空'
		                        }
		                    }
		                },      
		                
		            }
		        });			
			}			
	 	};
	 	
	 	return Vue.component('updateTransformer',  componentProperties);
	 	
		 
}); 


