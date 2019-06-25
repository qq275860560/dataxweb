<template>
   <div id="container">
   <div >         
 
			<div class="col-lg-10">			
			    <a href="#" data-toggle="modal" data-target="#myModal">退出</a>
				

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">提示</h4>
	            </div>
	            <div class="modal-body">确认退出吗?</div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" class="btn btn-primary" @click="logout">退出</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	
	
			</div><!-- /.col -->
        </div><!-- /.row -->	
</div>

</template>

<script>
export default {
	name:'logout',
  	data() {			
            return {            	
    			code:null,
    			msg:null,
    			data:null,    			
    		}
    	},
        methods: {
        	updateContainer:function(path) {
          		console.log("path",path);
          		this.$router.push({path:path});
        	},	 
        	logout: function () {	
        		//发送请求告知服务器
        		localStorage.clear();
        		this.updateContainer("/components/login.html");		
        					
        	},           
        },		
		created: function () {
			console.log(" init ");
			console.log($('#myModal'));
			$('#myModal').modal("show");
			
	    }
}
</script>

<style>

</style>
