//$.get("components/updateUserPassword.html", function(componentTemplate) {		
//fetch("components/updateUserPassword.html").then(function(response) {return response.text();}).then(function(componentTemplate){
define(['text!updateUserPassword.html'], function (componentTemplate) {	 	
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
				updateContainer:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},
				updateUserPassword:function(){
					if(this.check()==false) return false;
					let tmpVue=this;
					let url=BASE_PATH+"/api/github/qq275860560/user/updateUser?oldPassword="+this.query.oldPassword+"&password="+this.query.password;
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
							   tmpVue.updateContainer( "/components/home.html");
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
					 //this.updateContainer("/components/home.html");
					 this.$router.go(-1)				 
				},				
			},	
			created: function () {	
				let tmpVue=this;
				//this.query=this.$route.query;				
		    },
			mounted:function(){
				//TODO 远程校验名称唯一性
				//TODO 校验新密码和重复输入新密码是否一致
				$('#form').bootstrapValidator({
		            message: 'This value is not valid',
		            feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		            fields: {		               
		                oldPassword: {
		                    message: '旧密码验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '旧密码不能为空'
		                        },
		                        stringLength: {
		                            min: 6,
		                            max: 18,
		                            message: '旧密码长度必须在6到18位之间'
		                        },
		                        regexp: {
		                            regexp: /^[a-zA-Z0-9_]+$/,
		                            message: '旧密码只能包含大写、小写、数字和下划线'
		                        }
		                    }
		                },
		                password: {
		                    message: '新密码验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '新密码不能为空'
		                        },
		                        stringLength: {
		                            min: 6,
		                            max: 18,
		                            message: '新密码长度必须在6到18位之间'
		                        },
		                        regexp: {
		                            regexp: /^[a-zA-Z0-9_]+$/,
		                            message: '新密码只能包含大写、小写、数字和下划线'
		                        }
		                    }
		                },
		                newPassword: {
		                    message: '重复输入新密码验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '重复输入新密码不能为空'
		                        },
		                        stringLength: {
		                            min: 6,
		                            max: 18,
		                            message: '重复输入新密码长度必须在6到18位之间'
		                        },
		                        regexp: {
		                            regexp: /^[a-zA-Z0-9_]+$/,
		                            message: '重复输入新密码只能包含大写、小写、数字和下划线'
		                        }
		                    }
		                },
		                
		            }
		        });			
			}			
	 	};
	 	
	 	let component = Vue.component('updateUserPassword',  componentProperties);
	 	
		router.addRoutes([
			{ path: '/components/updateUserPassword.html', component: component }
		])
}); 


