/**
 * @author jiangyuanlin@163.com
 */
define(['vue','text!./updateMysqlWriter.html'], function (Vue,componentTemplate) {	
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
				updateOutput:function(query){	
					this.query.name=query.name;
					this.query.type=query.type;					
					if(this.check()==false) return false;
					let tmpVue=this;
					let url=this.$store.state.BASE_PATH+"/api/output/updateOutput?id="+this.query.id+"&name="+this.query.name+"&type="+this.query.type+"&parameterUsername="+this.query.parameterUsername+"&parameterPassword="+this.query.parameterPassword+"&parameterWriteMode="+this.query.parameterWriteMode+"&parameterColumn="+this.query.parameterColumn+"&parameterPreSql="+this.query.parameterPreSql+"&parameterConnectionJdbcUrl="+this.query.parameterConnectionJdbcUrl+"&parameterConnectionTable="+this.query.parameterConnectionTable;
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
					//this.$router.go(-1);
					this.updateRouterView("/components/output/pageOutput");	
				},				
			},	
			created: function () {		
				this.query=this.$route.query;		         		    
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
		                parameterUsername: {
		                    message: '用户名验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '用户名不能为空'
		                        }
		                    }
		                },
		                parameterPassword: {
		                    message: '密码验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '密码不能为空'
		                        }
		                    }
		                },
		                parameterWriteMode: {
		                    message: '写模式验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '写模式不能为空'
		                        }
		                    }
		                },		           	                
		                parameterColumn: {
		                    message: '列验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '列不能为空'
		                        }
		                    }
		                },
		                
		                parameterConnectionJdbcUrl: {
		                    message: 'url验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: 'url不能为空'
		                        }
		                    }
		                },
		                parameterConnectionTable: {
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
	 	
	 	return Vue.component('updateMysqlWriter',  componentProperties);
	 	
		 
}); 


