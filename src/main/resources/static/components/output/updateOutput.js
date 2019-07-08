/**
 * @author jiangyuanlin@163.com
 */
define(['vue','components/navigation/navigation','text!./updateOutput.html'], function (Vue,navigation,componentTemplate) {	
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
				updateOutput:function(){					
					if(this.check()==false) return false;
					let tmpVue=this;
					let url=this.$store.state.BASE_PATH+"/api/github/qq275860560/output/updateOutput?id="+this.query.id+"&name="+this.query.name+"&writerId="+this.query.writerId+"&writerName="+this.query.writerName+"&writerParameterUsername="+this.query.writerParameterUsername+"&writerParameterPassword="+this.query.writerParameterPassword+"&writerParameterWriteMode="+this.query.writerParameterWriteMode+"&writerParameterColumn="+this.query.writerParameterColumn+"&writerParameterPreSql="+this.query.writerParameterPreSql+"&writerParameterConnectionJdbcUrl="+this.query.writerParameterConnectionJdbcUrl+"&writerParameterConnectionTable="+this.query.writerParameterConnectionTable;
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
							   tmpVue.updateRouterView( "/components/output/pageOutput");
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
					 this.updateRouterView("/components/output/pageOutput");
					//this.$router.go(-1)	
				},				
			},	
			created: function () {	
				let tmpVue=this;
				this.query=this.$route.query;					
				
				let url=this.$store.state.BASE_PATH+"/api/github/qq275860560/output/getOutput?id="+this.query.id;
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
		                    message: '输出流名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '输出流名称不能为空'
		                        }
		                    }
		                },	
		                writerName: {
		                    message: '输出流类型名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '输出流类型名称不能为空'
		                        },
		                        stringLength: {
		                            min: 6,
		                            max: 18,
		                            message: '输出流类型名称长度必须在6到18位之间'
		                        },
		                        regexp: {
		                            regexp: /^[a-zA-Z0-9_]+$/,
		                            message: '输出流类型名称只能包含大写、小写、数字和下划线'
		                        }
		                    }
		                },
		                writerParameterUsername: {
		                    message: '用户名验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '用户名不能为空'
		                        }
		                    }
		                },
		                writerParameterPassword: {
		                    message: '密码验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '密码不能为空'
		                        }
		                    }
		                },
		                writerParameterWriteMode: {
		                    message: '写模式验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '写模式不能为空'
		                        }
		                    }
		                },		           	                
		                writerParameterColumn: {
		                    message: '列验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '列不能为空'
		                        }
		                    }
		                },
		                
		                writerParameterConnectionJdbcUrl: {
		                    message: 'url验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: 'url不能为空'
		                        }
		                    }
		                },
		                writerParameterConnectionTable: {
		                    message: '表验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '表不能为空'
		                        }
		                    }
		                },             
		                
		            }
		        });			
			}			
	 	};
	 	
	 	return Vue.component('updateOutput',  componentProperties);
	 	
		 
}); 


