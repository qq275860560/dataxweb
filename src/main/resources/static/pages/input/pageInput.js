 var pageInput = new Vue({
		el: '#pageInput',
		data: {		
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
		},
		methods:{
			pageInput:function(pageNum,pageSize){
				let url="http://localhost:8080/api/github/qq275860560/input/pageInput?pageNum="+pageNum+"&pageSize="+pageSize+"&name="+this.name;
				let token_type=localStorage.getItem('token_type'); 
				let access_token=localStorage.getItem('access_token');
				if(token_type==null || access_token==null){				
					window.top.location.replace("/login.html"); 
				}
				fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
				}).then(function(response) {return response.json();}).then(function(result){
					 if(result.code==200){
						    console.log("receive=",result );	
						    pageInput.data.pageNum=pageNum;
						    pageInput.data.pageSize=pageSize;
							pageInput.data.total=result.data.total;	
							pageInput.data.pageList=result.data.pageList;					
							pageInput.repaint();						
					   }else if(result.code==401){						
						   window.location.href = "/login.html";
					   }else if(result.code==403){
						   pageInput.msg="授权失败";					
					   }else{
						   pageInput.msg=result.msg;
					   }					
				}).catch(function(e) {  				
					pageInput.msg=result.msg;  					 
				 });			
				
			},
			repaint:function(){
				pageInput.array=[];			 
				let pageCount = Math.ceil((pageInput.data.total+1)/pageInput.data.pageSize);
				for(let i=0;i<pageCount;i++){		
					let pageNum=i+1;
					let background;
					if(pageNum==pageInput.data.pageNum){
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
			saveInput(){		
				$("#content").load( "/pages/input/saveInput.html");
			}		
		},		
		created: function () {
	        this.pageInput(this.data.pageNum,this.data.pageSize);
	    }

	});