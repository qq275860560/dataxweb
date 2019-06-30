//$.get("components/pageInput.html", function(componentTemplate) {
//fetch("components/pageInput.html").then(function(response) {return response.text();}).then(function(componentTemplate){
define(['text!pageInput.html'], function (componentTemplate) { 	 	
	let componentProperties = {
			template: componentTemplate,
			data:function() {
				return {
					name:"",
					startCreateTime:"",
					endCreateTime:"",
					array:[],				
					code:null,
					msg:null,
					data:{
						pageNum:1,
						pageSize:10,
						total:null,
						pageList:null,
					}
				}
			},
			methods:{
				updateContainer:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},
				pageInput:function(pageNum,pageSize){					
					let tmpVue=this;
					let url=BASE_PATH+"/api/github/qq275860560/input/pageInput?pageNum="+pageNum+"&pageSize="+pageSize+"&name="+this.name+"&startCreateTime="+this.startCreateTime+"&endCreateTime="+this.endCreateTime;
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
							    tmpVue.data.pageNum=pageNum;
							    tmpVue.data.pageSize=pageSize;
							    tmpVue.data.total=result.data.total;	
							    tmpVue.data.pageList=result.data.pageList;					
							    tmpVue.repaint();						
						   }else if(result.code==401){						
							   tmpVue,updateContainer("/components/login.html");
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
					let pageCount = Math.ceil((this.data.total+1)/this.data.pageSize);
					for(let i=0;i<pageCount;i++){		
						let pageNum=i+1;
						let background;
						if(pageNum==this.data.pageNum){
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
				},
				saveInput:function(){		
					updateContainer("/components/saveInput.html");
				},			 		 
			},	
			created: function () {			
				this.pageInput(this.data.pageNum,this.data.pageSize);			    
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
				    	tmpVue.startCreateTime = $("#startCreateTime").find("input").val();
				        endCreateTimePicker.data('DateTimePicker').minDate(e.date);			 
				    });
				      endCreateTimePicker.on('dp.change', function (e) {
				        startCreateTimePicker.data('DateTimePicker').maxDate(e.date);	
				        tmpVue.endCreateTime = $("#endCreateTime").find("input").val();
				    });
			        
					      
				      
			}
	 	};
	 	
	 	let component = Vue.component('pageInput',  componentProperties);
	 	
		router.addRoutes([
			{ path: '/components/pageInput.html', component: component }
		])
}); 


