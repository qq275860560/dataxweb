 var vm = new Vue({
		el: '#vm',
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
			save:function(){			
				let url="http://localhost:8080/api/github/qq275860560/input/saveInput?name="+vm.name+"&readerId="+vm.readerId+"&readerName="+vm.readerName+"&readerParameterUsername="+vm.readerParameterUsername+"&readerParameterPassword="+vm.readerParameterPassword+"&readerParameterColumn="+vm.readerParameterColumn+"&readerParameterWhere="+vm.readerParameterWhere+"&readerParameterConnectionJdbcUrl="+vm.readerParameterConnectionJdbcUrl+"&readerParameterConnectionTable="+vm.readerParameterConnectionTable;
				let token_type=localStorage.getItem('token_type'); 
				let access_token=localStorage.getItem('access_token');
				if(token_type==null || access_token==null){
					window.location.href = "/login.html";					
				}
				fetch(url,{method:"GET", mode:"cors",headers:{"Authorization": token_type+" "+access_token }					
				}).then(function(response) {return response.json();}).then(function(result){
					 if(result.code==200){
						   console.log("receive=",result );			
						   window.location.href = "/pages/input/index.html";
					   }if(result.code==401){						
						   window.location.href = "/login.html";
					   }if(result.code==403){
						   vm.msg="用户未授权";					
					   }else{
						   vm.msg=result.msg;
					   }
					
				});			
				
			},
			check:function(){
				 return true;
			}
		
		},		
		created: function () {
	        
	    }

	});