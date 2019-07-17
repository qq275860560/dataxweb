/**
 * @author jiangyuanlin@163.com
 */
define(['vue','components/navigation/navigation','text!./updateInput.html'], function (Vue,navigation,componentTemplate) {	
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
					if(newValue=="mysqlreader"){
						this.updateRouterView( "/components/input/updateMysqlReader",query);	
					}else if(newValue=="txtfilereader"){
						this.updateRouterView( "/components/input/updateTxtFileReader",query);	
					}else if(newValue=="httpreader"){
						this.updateRouterView( "/components/input/updateHttpReader",query);	
					}else if(newValue=="ftpreader"){
						this.updateRouterView( "/components/input/updateFtpReader",query);	
					}
				}
			},
			methods:{
				updateRouterView:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},
				updateInput:function(){					
					if(this.check()==false) return false;
					let query={name:this.query.name,type:this.query.type};
				    this.$refs.updateInputChild.updateInput(query);						 					
				},
				check:function(){					
					//$("#form").bootstrapValidator("validate");
					$("#form").data("bootstrapValidator").resetForm();
					$("#form").data("bootstrapValidator").validate();
					return $("#form").data("bootstrapValidator").isValid();		
				},
				back:function(){					 
					//this.$router.go(-1);
					this.updateRouterView("/components/input/pageInput");	
				},				
			},	
			created: function () {	
				let tmpVue=this;
				this.query=this.$route.query;					
				
				let url=this.$store.state.BASE_PATH+"/api/input/getInput?id="+this.query.id;
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
		                    message: '插件名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '插件名称不能为空'
		                        }
		                    }
		                },	
		                type: {
		                    message: '输入流类型验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '输入流类型不能为空'
		                        },
		                        stringLength: {
		                            min: 6,
		                            max: 18,
		                            message: '输入流类型长度必须在6到18位之间'
		                        },
		                        regexp: {
		                            regexp: /^[a-zA-Z0-9_]+$/,
		                            message: '输入流类型只能包含大写、小写、数字和下划线'
		                        }
		                    }
		                },		               
		            }
		        });			
			}			
	 	};
	 	
	 	return Vue.component('updateInput',  componentProperties);
	 	
		 
}); 


