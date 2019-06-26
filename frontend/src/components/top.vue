<template>
       
    <nav id="top" class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">          
          <a class="navbar-brand" href="javascript:void(0)" @click="updateContainer('/components/home.html')" >数据交换平台</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav side-nav">
            <li class="active">
              <a class="navbar-brand" href="javascript:void(0)" @click="updateContainer('/components/pageInput.html')" >输入流管理</a>
            </li>          
          </ul>

          <ul class="nav navbar-nav navbar-right navbar-user">    
         
            <li class="dropdown user-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> 管理员 <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="javascript:void(0)" @click="updateContainer('/components/logout.html')">退出</a></li>            
              </ul>
            </li>
          </ul>
        </div><!-- /.navbar-collapse -->        
      </nav>    
    
    
</template>
<script>
import logout from "@/components/logout"
export default {
  name:"top",  
  components:{
	 logout
  },
  methods: {	
	  updateContainer:function(path) {
	  		console.log("path",path);
	  		this.$router.push({path:path});
		}
	},
	created: function () {			
		
	}
}
</script>
<style>

</style>

