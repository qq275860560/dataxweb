<template>
	<div id="container">

		<div >
          <div class="col-sm-10 col-sm-offset-1">
            <ol class="breadcrumb">
              <li><a href="#" @click="updateContainer('/')"><i class="fa fa-dashboard"></i> 主页</a></li>
              <li><a href="#" @click="updateContainer('/components/pageInput.html')"><i class="fa fa-dashboard"></i> 输入流</a></li>
              <li class="active"><i class="fa fa-edit"></i> 新建</li>
            </ol>
            <div  v-if="code && code!=200" class="alert alert-info alert-dismissable">
              <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
             {{msg}}
            </div>
          </div>
        </div>

		<div  >
		<div class="col-sm-10 col-sm-offset-1">
			<form class="form-horizontal " role="form" >

				<div class="form-group form-group-lg">
					<label class="col-sm-2 control-label">插件名称</label>
					<div class="col-sm-4">
						<input class="form-control" v-model="name" type="text">
					</div>
					

					<label class="col-sm-2 control-label">输入流ID</label>
					<div class="col-sm-4">
						<input class="form-control" v-model="readerId" type="text">
					</div>
				</div>

				<div class="form-group form-group-lg">
					<label class="col-sm-2 control-label">输入流名称</label>
					<div class="col-sm-4">
						<input class="form-control" v-model="readerName" type="text">
					</div>

					<label class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-4">
						<input class="form-control" v-model="readerParameterUsername"
							type="text">
					</div>
				</div>

				<div class="form-group form-group-lg">
					<label class="col-sm-2 control-label">密码</label>
					<div class="col-sm-4">
						<input class="form-control" v-model="readerParameterPassword"
							type="text">
					</div>

					<label class="col-sm-2 control-label">列</label>
					<div class="col-sm-4">
						<input class="form-control" v-model="readerParameterColumn"
							type="text">
					</div>
				</div>

				<div class="form-group form-group-lg">
					<label class="col-sm-2 control-label">where条件</label>
					<div class="col-sm-4">
						<input class="form-control" v-model="readerParameterWhere"
							type="text">
					</div>

					<label class="col-sm-2 control-label">url</label>
					<div class="col-sm-4">
						<input class="form-control"
							v-model="readerParameterConnectionJdbcUrl" type="text">
					</div>
				</div>

				<div class="form-group form-group-lg">
					<label class="col-sm-2 control-label">表</label>
					<div class="col-sm-4">
						<input class="form-control"
							v-model="readerParameterConnectionTable" type="text">
					</div>
				</div>




				<div class="form-group form-group-lg">
					<button type="button"
						class="col-sm-1 col-sm-offset-5 btn btn-default" @click="back()">返回</button>
					<button type="button"
						class="col-sm-1 col-sm-offset-1  btn btn-default"
						@click="saveInput()">确定</button>
				
				</div>

			</form>
</div>
		</div>
	</div>
</template>

<script>
export default {
  name:'saveInput',
  data:function() {		
			return {
				name:"inputName",
				readerId:"mysqlreaderId",
				readerName:"mysqlreaderName",
				readerParameterUsername:"root",
				readerParameterPassword:"123456",
				readerParameterColumn:"id,name",
				readerParameterWhere:"name !=null",
				readerParameterConnectionJdbcUrl:"jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
				readerParameterConnectionTable:"job",	
				code:null,
				msg:null,
				data:null,
			}
		},
		methods:{
			updateContainer:function(path) {
          		console.log("path",path);
          		this.$router.push({path:path});
        	},
			saveInput:function(){	
				let saveInput = this;
				let url="http://localhost:8080/api/github/qq275860560/input/saveInput?name="+saveInput.name+"&readerId="+saveInput.readerId+"&readerName="+saveInput.readerName+"&readerParameterUsername="+saveInput.readerParameterUsername+"&readerParameterPassword="+saveInput.readerParameterPassword+"&readerParameterColumn="+saveInput.readerParameterColumn+"&readerParameterWhere="+saveInput.readerParameterWhere+"&readerParameterConnectionJdbcUrl="+saveInput.readerParameterConnectionJdbcUrl+"&readerParameterConnectionTable="+saveInput.readerParameterConnectionTable;
				let token_type=localStorage.getItem('token_type'); 
				let access_token=localStorage.getItem('access_token');
				if(token_type==null || access_token==null){
					this.updateContainer("/components/login.html");									
				}
				fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
				}).then(function(response) {return response.json();}).then(function(result){
					 if(result.code==200){
						   console.log("receive=",result );			
						   saveInput.updateContainer("/components/pageInput.html");
					   }else if(result.code==401){						
						   saveInput.updateContainer("/components/login.html");
					   }else if(result.code==403){
						   saveInput.msg="授权失败";					
					   }else{
						   saveInput.msg=result.msg;
					   }					
				}).catch(function(e) {  				
					saveInput.msg=e;  					 
				});			
				
			},
			check:function(){
				 return true;
			},
			back:function(){
				 this.updateContainer("/components/pageInput.html");
			}
		
		},		
		created: function () {
	        
	    }
}
</script>

<style>

</style>
