<template>
	<div id="container" >
	   <div >
          <div class="col-sm-10 col-sm-offset-1">
            <ol class="breadcrumb">
              <li><a href="#" @click="updateContainer('/')"><i class="fa fa-dashboard"></i> 主页</a></li>
              <li class="active"><i class="fa fa-edit"></i> 输入流</li>
            </ol>
            
            <div v-if="code && code!=200" class="alert alert-info alert-dismissable">
              <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
             {{msg}}
            </div>
          </div>
        </div><!-- /.row -->

        <div >
          <div class="col-sm-10 col-sm-offset-1">
            <form>
				<div class="form-group ">
					<label class="col-sm-2 control-label">插件名称</label>
					<div class="col-sm-2">
						<input class="form-control" v-model="name" type="text">
					</div>
					<button type="button" class="btn btn-default"
						@click="pageInput(1,data.pageSize)">搜索</button>
					<button type="button" class="btn btn-default" @click="updateContainer('/components/saveInput.html')">新建</button>
				</div>
			</form>
            <h2>分页搜索结果</h2>
            <div class="table-responsive">
              <table class="table table-bordered table-hover tablesorter    table-striped table-responsive">
                <thead>
                  <tr>
                    <th class="header">输入流名称 <i class="fa fa-sort"></i></th>
                    <th class="header">用户名 <i class="fa fa-sort"></i></th>
                    <th class="header">列 <i class="fa fa-sort"></i></th>
                    <th class="header">表 <i class="fa fa-sort"></i></th>
                     <th class="header">创建人 <i class="fa fa-sort"></i></th>
                      <th class="header">创建时间 <i class="fa fa-sort"></i></th>
                  </tr>
                  
                </thead>
                <tbody>
                  <tr v-for="item of data.pageList"  >
					<td>{{item.readerName}}{{item.id}}</td>
					<td>{{item.readerParameterUsername}}</td>
					<td>{{item.readerParameterColumn}}</td>
					<td>{{item.readerParameterConnectionTable}}</td>
					<td>{{item.createUserName}}</td>
					<td>{{item.createTime}}</td>
				</tr>
                </tbody>
              </table>
            </div>
            <ul class="pagination" style="float:right ">
				<li><span>共{{Math.ceil((data.total+1)/data.pageSize)}}页{{data.total}}条记录</span>
				</li>
				<li><a href="#" @click="pageInput(1,data.pageSize)">首页</a></li>
				<li><a href="#"
					@click="pageInput(data.pageNum>1?data.pageNum-1:data.pageNum,data.pageSize)"
					class="previous disabled">&laquo;上一页</a></li>
				<li><a :style="item.style" v-for="item of array"
					href='javascript:void(0)'
					@click="pageInput( item.pageNum,data.pageSize )">{{item.pageNum}}&nbsp;</a></li>
				<li><a href="#"
					@click="pageInput(data.pageNum<Math.ceil((data.total+1)/data.pageSize)?data.pageNum+1:data.pageNum,data.pageSize)"
					class="next ">下一页&raquo;</a></li>
				<li><a href="#"
					@click="pageInput(Math.ceil((data.total+1)/data.pageSize),data.pageSize)">尾页</a>
				</li>
				<li><span>跳转至<input
						style="width: 24px; padding-top: 0px; padding-bottom: 0px"
						type="text" v-model="data.pageNum" />页
				</span></li>
				<li><span>每页<input
						style="width: 24px; padding-top: 0px; padding-bottom: 0px"
						type="text" v-model="data.pageSize" />条
				</span></li>
				<li><a href='javascript:void(0)'
					@click="pageInput(data.pageNum,data.pageSize)">确定</a></li>
			</ul>
          </div>
          
        </div><!-- /.row -->      
        
        
       
	</div>	
</template>

<script>
export default {
  name:'pageInput',
  data() {		
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
			    let pageInput=this;
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
						    pageInput.data.pageNum=pageNum;
						    pageInput.data.pageSize=pageSize;
							pageInput.data.total=result.data.total;	
							pageInput.data.pageList=result.data.pageList;					
							pageInput.repaint();						
					   }else if(result.code==401){						
						   pageInput.updateContainer("/components/login.html");
					   }else if(result.code==403){
						   pageInput.msg="授权失败";					
					   }else{
						   pageInput.msg=result.msg;
					   }					
				}).catch(function(e) {  				
					pageInput.msg=e;  					 
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
			}	
		},		
		created: function () {
	        this.pageInput(this.data.pageNum,this.data.pageSize);
	    }
}
</script>

<style>

</style>
