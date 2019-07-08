/**
 * @author jiangyuanlin@163.com
 */
define(['vue','text!./selectOutput.html'], function (Vue,componentTemplate) {	
	let componentProperties = {
			  template: componentTemplate,
			  data:function() {			
		           return {   
		        	query:{},   
		        	name:"",
					startCreateTime:"",
					endCreateTime:"",
					array:[],					
					selectItemId:null,
					selectItemName:null,
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
		    methods: {
		    	updateRouterView:function(path,query) {
	         		console.log("path",path);
	         		this.$router.push({path:path,query:query});	     
				},
				pageOutput:function(pageNum,pageSize){	
					let tmpVue=this;
					let url=this.$store.state.BASE_PATH+"/api/github/qq275860560/output/pageOutput?pageNum="+pageNum+"&pageSize="+pageSize+"&name="+this.name+"&startCreateTime="+this.startCreateTime+"&endCreateTime="+this.endCreateTime;
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
							    tmpVue.data.pageNum=pageNum;
							    tmpVue.data.pageSize=pageSize;
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
		       	back: function () {	
		       		this.hide();		       	
		       		$('#selectOutputModal').modal('hide');
		       	},
		       	selectOutput: function () {			       		
		       		//发送请求告知服务器
		       		this.hide();		
		       			
		       		this.$emit('setOutput',this.selectItemId,this.selectItemName);		       					       					
		       	},  
		        hide:function (){
		    	   $('#selectOutputModal').modal('hide');
		    	},
		    	show:function (query){
		    		this.query=query;
		    		console.log("query",query);
		    		$('#selectOutputModal').modal('show');
			    	$(".modal-backdrop").removeClass("modal-backdrop");//TODO 如果没有这一行代码则有遮罩，暂时只想到这个办法，
			    	
			    	this.data.pageNum=this.query.pageNum;
			    	this.data.pageSize=this.query.pageSize;
			    	this.pageOutput(this.data.pageNum,this.data.pageSize);	
			    }
		    },	
		    watch: {
		    	selectItemId(newValue, oldValue) {      
		    		if(this.selectItemId){
		       			for(let item of this.data.pageList){
		       				if(item.id == this.selectItemId ){
		       					this.selectItemName = item.name;
		       					break;
		       				}	       				
		       			}		       			
		       		}
				},				
			},
			created: function () {
									
		    },
		    mounted: function () {	
		    	this.hide();				
		    },
		};
  		
		return Vue.component('selectOutput', componentProperties);	
}); 