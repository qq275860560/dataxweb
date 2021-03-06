/**
 * @author jiangyuanlin@163.com
 */
define(['vue','text!./saveTxtFileWriter.html'], function (Vue,componentTemplate) {	
	let componentProperties = {
			template: componentTemplate,
			data:function() {
				return {
					query:{ 
						name:"",					
						type:"",									
						parameterPath:"D:/tmp/tmp3",
						parameterFileName:"test.txt",											
					},
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
				saveOutput:function(query){	
					this.query.name=query.name;
					this.query.type=query.type;
					if(this.check()==false) return false;
					let tmpVue=this;
					let url = this.$store.state.BASE_PATH+"/api/output/saveOutput?name="+this.query.name+"&type="+this.query.type+"&parameterPath="+encodeURIComponent(this.query.parameterPath)+"&parameterFileName="+this.query.parameterFileName+"&parameterWriteMode=truncate";
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
				check:function(){					
					//$("#form").bootstrapValidator("validate");
					$("#form").data("bootstrapValidator").resetForm();
					$("#form").data("bootstrapValidator").validate();
					return $("#form").data("bootstrapValidator").isValid();		
				},							
			},	
			created: function () {
				this.query.name=this.$route.query.name;
				this.query.type=this.$route.query.type;		         		    
		    },
			mounted:function(){	
				$('#form').bootstrapValidator({
		            message: 'This value is not valid',
		            feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		            fields: {		               
		            	parameterPath: {
		                    message: '所在文件夹路径验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '所在文件夹路径不能为空'
		                        }
		                    }
		                },
		                parameterFileName: {
		                    message: '文件名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '文件名称不能为空'
		                        }
		                    }
		                },		                
		            }
		        });			
			}			
	 	};
	 	
	 	return Vue.component('saveTxtFileWriter',  componentProperties);
	 	
	
}); 


