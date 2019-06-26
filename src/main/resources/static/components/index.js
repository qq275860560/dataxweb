$.get("/components/index.html", function(componentTemplate) {		
	 	let componentProperties = {
			  template: componentTemplate,
			  data: function () {
			    return {
			      counter: 0
			    }
			  },
			  methods: {
			    incrementHandler: function () {
			      this.counter += 1; 		     
			    }
			  },
		};
        let component = Vue.component('home',componentProperties);
		router.addRoutes([				
			{ path: '/components/index.html', component: component }
		])
}); 