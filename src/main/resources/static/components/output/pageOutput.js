/**
 * @author jiangyuanlin@163.com
 */
define(['vue','components/navigation/navigation','components/output/deleteOutput','text!./pageOutput.html'], function (Vue,navigation,deleteOutput,componentTemplate) {	
	
	let componentProperties = {
			template: componentTemplate,
			
			data:function() {
				return {
					query:{
						name:"",
						writerName:"",
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
				pageOutput:function(pageNum,pageSize){	
					let tmpVue=this;
					let url=this.$store.state.BASE_PATH+"/api/output/pageOutput?pageNum="+pageNum+"&pageSize="+pageSize+"&name="+this.query.name+"&writerName="+this.query.writerName+"&startCreateTime="+this.query.startCreateTime+"&endCreateTime="+this.query.endCreateTime;
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
				deleteOutput:function(query){
					 this.$refs.deleteOutput.show(query);
				},
				batchDeleteOutput:function(){
					if(this.selectItemIds.length<=0){
						this.setCodeAndMsg(400,"请选中要删除的数据");
						return;
					}
					let items = [] 
					for(tmpId of this.selectItemIds){
						for(let item of this.data.pageList){
							if(tmpId==item.id){
								items.push({id:tmpId,name:item.name});
							}
						}
					}
					let query = {};
					query.items=items;
					query.pageNum=this.query.pageNum;
					query.pageSize=this.query.pageSize;
					this.$refs.deleteOutput.show(query);					 
				},
			},	
			watch: {
				selectAllItemId(newValue, oldValue) {      
				      if(newValue.length>0){						
						for(let item of this.data.pageList){							
							let flag = false;
							for(tmpId of this.selectItemIds){
								if(tmpId==item.id){
									flag=true;
									break;
								}								
							}
							if(flag==false){
								this.selectItemIds.push(item.id);	
							}
						}						
					}else{
						this.selectItemIds=[];
					}				
				},				
				
			 },
			created: function () {			
				this.pageOutput(this.query.pageNum,this.query.pageSize);			    
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
	 	
	 	return Vue.component('pageOutput',  componentProperties);
	
}); 


