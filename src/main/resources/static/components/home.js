//$.get("components/home.html", function(componentTemplate) {
//fetch("components/home.html").then(function(response) {return response.text();}).then(function(componentTemplate){
define(['text!home.html'], function (componentTemplate) {	 	
let componentProperties = {
			  template: componentTemplate,
			  data: function () {
			    return {
			      
			    }
			  },
			  methods: {
			    
			  },
		};
        let component = Vue.component('home',componentProperties);
		router.addRoutes([				
			{ path: '/components/home.html', component: component },
			{ path: '/', component: component }
		])
}); 