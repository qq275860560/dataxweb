$.get("/components/saveInput.html", function(componentTemplate) {		
	 	let componentProperties = {
			template: componentTemplate,
			data:function() {
				return {
					name:"inputName",
					readerId:"mysqlreaderId",
					readerName:"mysqlreaderName",
					readerParameterUsername:"root",
					readerParameterPassword:"123456",
					readerParameterColumn:"id,name",
					readerParameterWhere:"name !=null",
					readerParameterConnectionJdbcUrl:"jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
					readerParameterConnectionTable:"job",	
					code:null,
					msg:null,
					data:null,
				}
			},
			methods:{
				updateContainer:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},
				saveInput:function(){
					if(this.check()==false) return false;
					let tmpVue=this;
					let url="http://localhost:8080/api/github/qq275860560/input/saveInput?name="+this.name+"&readerId="+this.readerId+"&readerName="+this.readerName+"&readerParameterUsername="+this.readerParameterUsername+"&readerParameterPassword="+this.readerParameterPassword+"&readerParameterColumn="+this.readerParameterColumn+"&readerParameterWhere="+this.readerParameterWhere+"&readerParameterConnectionJdbcUrl="+this.readerParameterConnectionJdbcUrl+"&readerParameterConnectionTable="+this.readerParameterConnectionTable;
					let token_type=localStorage.getItem('token_type'); 
					let access_token=localStorage.getItem('access_token');
					if(token_type==null || access_token==null){
						this.updateContainer("/components/login.html");					
					}
					fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
					}).then(function(response) {return response.json();}).then(function(result){
						tmpVue.code=result.code;
						 if(result.code==200){
							   console.log("receive=",result );			
							   tmpVue.updateContainer( "/components/pageInput.html");
						   }else if(result.code==401){						
							   tmpVue.updateContainer( "/components/login.html");
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
					//this.updateContainer("/components/pageInput.html");
					this.$router.go(-1)	
				},				
			},	
			created: function () {			
		         		    
		    },
			mounted:function(){
				//TODO 远程校验名称唯一性
				$('#form').bootstrapValidator({
		            message: 'This value is not valid',
		            feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		            fields: {
		                name: {
		                    message: '插件名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '插件名称不能为空'
		                        }
		                    }
		                },	
		                readerName: {
		                    message: '输入流名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '输入流名称不能为空'
		                        },
		                        stringLength: {
		                            min: 6,
		                            max: 18,
		                            message: '输入流名称长度必须在6到18位之间'
		                        },
		                        regexp: {
		                            regexp: /^[a-zA-Z0-9_]+$/,
		                            message: '输入流名称只能包含大写、小写、数字和下划线'
		                        }
		                    }
		                },
		                readerParameterUsername: {
		                    message: '用户名验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '用户名不能为空'
		                        }
		                    }
		                },
		                readerParameterPassword: {
		                    message: '密码验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '密码不能为空'
		                        }
		                    }
		                },
		                readerParameterColumn: {
		                    message: '列验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '列不能为空'
		                        }
		                    }
		                },
		                readerParameterConnectionJdbcUrl: {
		                    message: 'url验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: 'url不能为空'
		                        }
		                    }
		                },
		                readerParameterConnectionTable: {
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
	 	
	 	let component = Vue.component('saveInput',  componentProperties);
	 	
		router.addRoutes([
			{ path: '/components/saveInput.html', component: component }
		])
}); 


