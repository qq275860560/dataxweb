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
			watch:{
				"query.type":function(newValue){
					if(this.check()==false) return false;
					let query=this.query;
					if(newValue=="mysqlwriter"){
						this.updateRouterView( "/components/output/updateMysqlWriter",query);	
					}else if(newValue=="txtfilewriter"){
						this.updateRouterView( "/components/output/updateTxtFileWriter",query);	
					}
				}
			},
			methods:{
				updateRouterView:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},
				updateOutput:function(){					
					if(this.check()==false) return false;
					let query={name:this.query.name,type:this.query.type};
					if(this.query.type=="mysqlwriter"){
						this.$refs.updateOutputChild.updateOutput(query);	
					}else if(this.query.type=="txtfilewriter"){
						this.$refs.updateOutputChild.updateOutput(query);
					}
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
				let tmpVue=this;
				this.query=this.$route.query;					
				
				let url=this.$store.state.BASE_PATH+"/api/output/getOutput?id="+this.query.id;
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
		                type: {
		                    message: '输出流类型验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '输出流类型不能为空'
		                        },
		                        stringLength: {
		                            min: 6,
		                            max: 18,
		                            message: '输出流类型长度必须在6到18位之间'
		                        },
		                        regexp: {
		                            regexp: /^[a-zA-Z0-9_]+$/,
		                            message: '输出流类型只能包含大写、小写、数字和下划线'
		                        }
		                    }
		                },
		                
		            }
		        });			
			}			
	 	};
	 	
	 	return Vue.component('updateOutput',  componentProperties);
	 	
		 
}); 


