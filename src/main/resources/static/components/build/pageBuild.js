/**
 * @author jiangyuanlin@163.com
 */
define(['vue','components/navigation/navigation','components/build/getBuild','text!./pageBuild.html'], function (Vue,navigation,deleteBuild,componentTemplate) {	
	
	let componentProperties = {
			template: componentTemplate,
			
			data:function() {
				return {
					query:{						
						jobName:"",
						number:"",
						startCreateTime:"",
						endCreateTime:"",
						pageNum:1,
						pageSize:10,
					},										
					array:[],	
					selectAllItemId:[],
					selectItemIds:[],
					code:null,
					msg:null,
					data:{						
						total:null,
						pageList:null,
					}
				}
			},
			methods:{
				updateRouterView:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},
				pageBuild:function(pageNum,pageSize){	
					let tmpVue=this;
					let url=this.$store.state.BASE_PATH+"/api/build/pageBuild?pageNum="+pageNum+"&pageSize="+pageSize+"&jobName="+this.query.jobName+"&number="+this.query.number+"&startCreateTime="+this.query.startCreateTime+"&endCreateTime="+this.query.endCreateTime;
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
							    tmpVue.query.pageNum=pageNum;
							    tmpVue.query.pageSize=pageSize;
							    tmpVue.data.total=result.data.total;	
							    tmpVue.data.pageList=result.data.pageList;					
							    tmpVue.repaint();	
							    //如果有任务正在构建中，每隔5秒钟刷新一次列表START
							    for(let item of tmpVue.data.pageList){
									if(item.status==2){
										console.log("有任务正在运行");
										setTimeout(	function(){
							    			tmpVue.pageBuild(tmpVue.query.pageNum,tmpVue.query.pageSize);
							    		}, 5000 );										
							    		break;
									}
								}	
							    //如果有任务正在构建中，每隔5秒钟刷新一次列表END	
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
				repaint:function(){					
					this.array=[];			 
					let pageCount = Math.ceil((this.data.total+1)/this.query.pageSize);
					for(let i=0;i<pageCount;i++){		
						let pageNum=i+1;
						let background;
						if(pageNum==this.query.pageNum){
							background="#ddd";
						}else{
							background="";
						}
						this.array.push(
								{
									pageNum:pageNum,
									style:{background:background}
								}						
						);				 
					}	
					this.selectAllItemId=[];
					this.selectItemIds=[];
				},
				setCodeAndMsg(code,msg){
					this.code=code;
					this.msg=msg;					
				},				 
			},	
			watch: {
				 			
				
			 },
			created: function () {			
				this.pageBuild(this.query.pageNum,this.query.pageSize);			    
		    },
			mounted:function(){		
				 let tmpVue=this;
				 var startCreateTimePicker = $('#startCreateTime').datetimepicker({
				        format: 'YYYY-MM-DD HH:mm:ss',
				        viewMode: 'days',
				        locale: moment.locale('zh-cn'),				   
				        sideBySide: true,
				        toolbarPlacement: "bottom",
				        showClose:true	,
				        showTodayButton:true,
				        showClear:true, 
				        //minDate: '1970-01-01 00:00:00'
				    });
				    var endCreateTimePicker = $('#endCreateTime').datetimepicker({
				        format: 'YYYY-MM-DD HH:mm:ss',
				        viewMode: 'days',
				        locale: moment.locale('zh-cn'),				        
				        sideBySide: true,
				        toolbarPlacement: "bottom",
				        showClose:true	,
				        showTodayButton:true,
				        showClear:true, 				        
				    });
				  
				    startCreateTimePicker.on('dp.change', function (e) {				    
				    	tmpVue.query.startCreateTime = $("#startCreateTime").find("input").val();
				        endCreateTimePicker.data('DateTimePicker').minDate(e.date);	
				    });
				      endCreateTimePicker.on('dp.change', function (e) {
				        startCreateTimePicker.data('DateTimePicker').maxDate(e.date);	
				        tmpVue.query.endCreateTime = $("#endCreateTime").find("input").val();
				      });
			        
					      
				      
			}
	 	};
	 	
	 	return Vue.component('pageBuild',  componentProperties);
	
}); 


