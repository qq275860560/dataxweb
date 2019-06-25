$.get("/components/pageInput.html", function(componentTemplate) {		
	 	let componentProperties = {
			template: componentTemplate,
			data:function() {
				return {
					name:"",
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
				updateContainer:function(path) {
	         		console.log("path",path);
	         		this.$router.push({path:path});	     
				},
				pageInput:function(pageNum,pageSize){
					let tmpVue=this;
					let url="http://localhost:8080/api/github/qq275860560/input/pageInput?pageNum="+pageNum+"&pageSize="+pageSize+"&name="+this.name;
					let token_type=localStorage.getItem('token_type'); 
					let access_token=localStorage.getItem('access_token');
					if(token_type==null || access_token==null){				
						this.updateContainer("/components/login.html"); 
					}
					fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
					}).then(function(response) {return response.json();}).then(function(result){
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
		    }
	 	};
	 	
	 	let component = Vue.component('pageInput',  componentProperties);
	 	
		router.addRoutes([
			{ path: '/components/pageInput.html', component: component }
		])
}); 


