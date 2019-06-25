$.get("/components/saveInput.html", function(componentTemplate) {		
	 	let componentProperties = {
			template: componentTemplate,
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
					let tmpVue=this;
					let url="http://localhost:8080/api/github/qq275860560/input/saveInput?name="+this.name+"&readerId="+this.readerId+"&readerName="+this.readerName+"&readerParameterUsername="+this.readerParameterUsername+"&readerParameterPassword="+this.readerParameterPassword+"&readerParameterColumn="+this.readerParameterColumn+"&readerParameterWhere="+this.readerParameterWhere+"&readerParameterConnectionJdbcUrl="+this.readerParameterConnectionJdbcUrl+"&readerParameterConnectionTable="+this.readerParameterConnectionTable;
					let token_type=localStorage.getItem('token_type'); 
					let access_token=localStorage.getItem('access_token');
					if(token_type==null || access_token==null){
						this.updateContainer("/components/login.html");					
					}
					fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
					}).then(function(response) {return response.json();}).then(function(result){
						 if(result.code==200){
							   console.log("receive=",result );			
							   tmpVue.updateContainer( "/components/pageInput.html");
						   }else if(result.code==401){						
							   tmpVue.updateContainer( "/components/login.html");
						   }else if(result.code==403){
							   tmpVue.msg="授权失败";					
						   }else{
							   tmpVue.msg=result.msg;
						   }					
					}).catch(function(e) {  				
						tmpVue.msg=e;  					 
					});			
					
				},
				check:function(){
					 return true;
				},
				back:function(){
					 this.updateContainer("/components/pageInput.html");
				},				
			},	
			created: function () {			
		         		    
		    }
	 	};
	 	
	 	let component = Vue.component('saveInput',  componentProperties);
	 	
		router.addRoutes([
			{ path: '/components/saveInput.html', component: component }
		])
}); 


