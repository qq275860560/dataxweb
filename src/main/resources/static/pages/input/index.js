 var vm = new Vue({
		el: '#vm',
		data: {											
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
			page:function(pageNum,pageSize){
				let url="http://localhost:8080/api/github/qq275860560/input/pageInput?pageNum="+pageNum+"&pageSize="+pageSize;
				let token_type=localStorage.getItem('token_type'); 
				let access_token=localStorage.getItem('access_token');
				if(token_type==null || access_token==null){				
					window.top.location.replace("/login.html"); 
				}
				fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
				}).then(function(response) {return response.json();}).then(function(result){
					 if(result.code==200){
						    console.log("receive=",result );	
						    vm.data.pageNum=pageNum;
						    vm.data.pageSize=pageSize;
							vm.data.total=result.data.total;	
							vm.data.pageList=result.data.pageList;					
							vm.repaint();						
					   }if(result.code==401){						
						   window.location.href = "/login.html";
					   }if(result.code==403){
						   vm.msg="用户未授权";					
					   }else{
						   vm.msg=result.msg;
					   }
					
				});			
				
			},
			repaint:function(){
				vm.array=[];			 
				let pageCount = Math.ceil((vm.data.total+1)/vm.data.pageSize);
				for(let i=0;i<pageCount;i++){		
					let pageNum=i+1;
					let background;
					if(pageNum==vm.data.pageNum){
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
			},
			add(){		
				window.location.href = "/pages/input/add.html";
			}
		
		},		
		created: function () {
	        this.page(this.data.pageNum,this.data.pageSize);
	    }

	});