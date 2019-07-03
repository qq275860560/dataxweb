define(['vue','components/navigation/navigation','text!./saveInput.html'], function (Vue,navigation,componentTemplate) {	
	let componentProperties = {
			template: componentTemplate,
			data:function() {
				return {
					query:{ 
						name:"inputName"+ this.formateDate(new Date(),"yyyyMMddHHmmss"),
						readerId:"mysqlreader",
						readerName:"mysqlreader",
						readerParameterUsername:"root",
						readerParameterPassword:"123456",
						readerParameterColumn:"id,name",
						readerParameterWhere:"name !=null",
						readerParameterConnectionJdbcUrl:"jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
						readerParameterConnectionTable:"job",	
					},
					code:null,
					msg:null,
					data:null,
				}
			},
			methods:{
				formateDate:function(date,fmt){
					var o = {         
						    "M+" : date.getMonth()+1, //月份         
						    "d+" : date.getDate(), //日         
						    "h+" : date.getHours()%12 == 0 ? 12 : date.getHours()%12, //小时         
						    "H+" : date.getHours(), //小时         
						    "m+" : date.getMinutes(), //分         
						    "s+" : date.getSeconds(), //秒         
						    "q+" : Math.floor((date.getMonth()+3)/3), //季度         
						    "S" : date.getMilliseconds() //毫秒         
						    };         
						    var week = {         
						    "0" : "/u65e5",         
						    "1" : "/u4e00",         
						    "2" : "/u4e8c",         
						    "3" : "/u4e09",         
						    "4" : "/u56db",         
						    "5" : "/u4e94",         
						    "6" : "/u516d"        
						    };         
						    if(/(y+)/.test(fmt)){         
						        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));         
						    }         
						    if(/(E+)/.test(fmt)){         
						        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[date.getDay()+""]);         
						    }         
						    for(var k in o){         
						        if(new RegExp("("+ k +")").test(fmt)){         
						            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
						        }         
						    }         
						    return fmt;         
				},
				updateRouterView:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},
				saveInput:function(){					
					if(this.check()==false) return false;
					let tmpVue=this;
					let url=this.$store.state.BASE_PATH+"/api/github/qq275860560/input/saveInput?name="+this.query.name+"&readerId="+this.query.readerId+"&readerName="+this.query.readerName+"&readerParameterUsername="+this.query.readerParameterUsername+"&readerParameterPassword="+this.query.readerParameterPassword+"&readerParameterColumn="+this.query.readerParameterColumn+"&readerParameterWhere="+this.query.readerParameterWhere+"&readerParameterConnectionJdbcUrl="+this.query.readerParameterConnectionJdbcUrl+"&readerParameterConnectionTable="+this.query.readerParameterConnectionTable;
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
							   tmpVue.updateRouterView( "/components/input/pageInput");
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
					$("#form").data("bootstrapValidator").validate();
					return $("#form").data("bootstrapValidator").isValid();		
				},
				back:function(){
					//this.updateRouterView("/components/input/pageInput");
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
	 	
	 	return Vue.component('saveInput',  componentProperties);
	 	
	
}); 


