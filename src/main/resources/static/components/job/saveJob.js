/**
 * @author jiangyuanlin@163.com
 */
define(['vue','components/navigation/navigation','components/input/selectInput','components/output/selectOutput','components/transformer/selectTransformer','text!./saveJob.html'], function (Vue,navigation,selectInput,selectOutput,selectTransformer,componentTemplate) {	
	let componentProperties = {
			template: componentTemplate,
			data:function() {
				return {
					query:{ 
						name:"job"+ this.formateDate(new Date(),"yyyyMMddHHmmss"),
						inputId:null,
						inputName:null,
						outputId:null,	
						outputName:null,	
						transformerId:null,	
						transformerName:null,	
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
				saveJob:function(){					
					if(this.check()==false) return false;
					let tmpVue=this;
					let url=this.$store.state.BASE_PATH+"/api/job/saveJob?name="+this.query.name+"&inputId="+this.query.inputId+"&outputId="+this.query.outputId+"&transformerId="+this.query.transformerId;
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
							   tmpVue.updateRouterView("/components/user/login");
						   }else if(result.code==403){
							   tmpVue.msg="授权失败";					
						   }else{							   
							   tmpVue.msg=result.msg;
							   console.log(tmpVue.msg);
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
					this.updateRouterView("/components/job/pageJob");	
				},	
				selectInput:function(query){
					 this.$refs.selectInput.show(query);
				},
				setInput:function(id,name){			
					this.query.inputId=id;
					this.query.inputName=name;
				},
				selectOutput:function(query){
					 this.$refs.selectOutput.show(query);
				},
				setOutput:function(id,name){				 
					this.query.outputId=id;
					this.query.outputName=name;
				},
				selectTransformer:function(query){
					 this.$refs.selectTransformer.show(query);
				},
				setTransformer:function(id,name){				 
					this.query.transformerId=id;
					this.query.transformerName=name;
				},
			},	
			created: function () {			
		         		    
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
		                    message: '构建任务名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '构建任务名称不能为空'
		                        }
		                    }
		                },	
		                inputName: {
		                    message: '输入流名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '输入流名称不能为空'
		                        }		                        
		                    }
		                },
		                outputName: {
		                    message: '输出流名称验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '输出流名称不能为空'
		                        }		                        
		                    }
		                },	                
		                
		            }
		        });			
			}			
	 	};
	 	
	 	return Vue.component('saveJob',  componentProperties);
	 	
	
}); 


