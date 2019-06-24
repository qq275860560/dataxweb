 var saveInput = new Vue({
		el: '#saveInput',
		data: {	
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
			
		},
		methods:{
			saveInput:function(){			
				let url="http://localhost:8080/api/github/qq275860560/input/saveInput?name="+saveInput.name+"&readerId="+saveInput.readerId+"&readerName="+saveInput.readerName+"&readerParameterUsername="+saveInput.readerParameterUsername+"&readerParameterPassword="+saveInput.readerParameterPassword+"&readerParameterColumn="+saveInput.readerParameterColumn+"&readerParameterWhere="+saveInput.readerParameterWhere+"&readerParameterConnectionJdbcUrl="+saveInput.readerParameterConnectionJdbcUrl+"&readerParameterConnectionTable="+saveInput.readerParameterConnectionTable;
				let token_type=localStorage.getItem('token_type'); 
				let access_token=localStorage.getItem('access_token');
				if(token_type==null || access_token==null){
					window.location.href = "/login.html";					
				}
				fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
				}).then(function(response) {return response.json();}).then(function(result){
					 if(result.code==200){
						   console.log("receive=",result );			
						   $("#content").load( "/pages/input/pageInput.html");
					   }else if(result.code==401){						
						   window.location.href = "/login.html";
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
				 $("#content").load("/pages/input/pageInput.html");
			}
		
		},		
		created: function () {
	        
	    }

	});