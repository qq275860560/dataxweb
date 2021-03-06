/**
 * @author jiangyuanlin@163.com
 */
define(['vue','components/navigation/navigation','text!./saveInput.html'], function (Vue,navigation,componentTemplate) {	
	let componentProperties = {
			template: componentTemplate,
			data:function() {
				return {
					query:{ 
						name:"input"+ this.formateDate(new Date(),"yyyyMMddHHmmss"),
						type:"",					
					},
					code:null,
					msg:null,
					data:null,
				}
			},
			watch:{
				"query.type":function(newValue){
					if(this.check()==false) return false;
					let query={name:this.query.name,type:this.query.type};
					if(newValue=="mysqlreader"){
						this.updateRouterView( "/components/input/saveMysqlReader",query);	
					}else if(newValue=="txtfilereader"){
						this.updateRouterView( "/components/input/saveTxtFileReader",query);	
					}else if(newValue=="httpreader"){
						this.updateRouterView( "/components/input/saveHttpReader",query);	
					}else if(newValue=="ftpreader"){
						this.updateRouterView( "/components/input/saveFtpReader",query);	
					}
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
					let query={name:this.query.name,type:this.query.type};
					this.$refs.saveInputChild.saveInput(query);						
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
				this.query.type="mysqlreader";//默认输入流为mysql	 		    
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
	 	
	 	return Vue.component('saveInput',  componentProperties);
	 	
	
}); 


