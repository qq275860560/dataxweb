define(['vue','components/navigation/navigation','text!./home.html'], function (Vue,navigation,componentTemplate) {	 	
	 
        return Vue.component('home',{
			  template: componentTemplate,			 
		});
			  

}); 

