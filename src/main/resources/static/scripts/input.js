 var vm = new Vue({
		el: '#vm',
		data: {	
			pageNum:1,
			pageSize:10,
			total:null,
			pageList:null,				
			array:[]			
		},
		methods:{
			page:function(pageNum,pageSize){
				console.log("send="+pageNum+" "+pageSize);
				axios.get("http://admin:123456@localhost:8080/api/github/qq275860560/input/pageInput?pageNum="+pageNum+"&pageSize="+pageSize).then(function (response) {
					console.log("receive=",response.data.data );
					vm.pageNum=pageNum;
					vm.pageSize=pageSize;
					vm.total=response.data.data.total;
					vm.pageList = response.data.data.pageList;	
					vm.repaint();
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