 var vm = new Vue({
		el: '#vm',
		data: {	
			pageNum:1,
			pageSize:10,
			total:null,
			pageList:null,				
			array:[],	
		},
		methods:{
			page:function(pageNum,pageSize){
				_that = this;
				console.log("send="+pageNum+" "+pageSize);
				let url="http://localhost:8080/api/github/qq275860560/input/pageInput?pageNum="+pageNum+"&pageSize="+pageSize;
				let token_type=localStorage.getItem('token_type'); 
				let access_token=localStorage.getItem('access_token');
				if(token_type==null || access_token==null){
					window.location.href = "/login.html";					
				}
				fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
				}).then(function(response) {return response.json();}).then(function(result){
					 if(result.code==200){
						    console.log("receive=",result );
							vm.pageNum=pageNum;
							vm.pageSize=pageSize;
							vm.total=result.data.total;
							vm.pageList = result.data.pageList;	
							vm.repaint();						
					   }if(result.code==401){						
						   window.location.href = "/login.html";
					   }if(result.code==403){
						   msg="用户未授权";					
					   }else{
						   msg=result.msg;
					   }
					
				});			
				
			},
			repaint:function(){
				this.array=[];			 
				let pageCount = Math.ceil((this.total+1)/this.pageSize);
				for(let i=0;i<pageCount;i++){		
					let pageNum=i+1;
					let background;
					if(pageNum==this.pageNum){
						background="yellow";
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
			}
		
		},		
		created: function () {
	        this.page(this.pageNum,this.pageSize);
	    }

	});