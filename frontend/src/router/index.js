import Vue from 'vue'
import Router from 'vue-router'

import home from "@/components/home"
import login from "@/components/login"
import logout from "@/components/logout"
import pageInput from "@/components/pageInput"
import saveInput from "@/components/saveInput"

Vue.use(Router)

export default new Router({
    routes: [   
		{
		    path: '/',
		    name: 'home',
		    component: home
		},	
	      {
	          path: '/components/home.html',
	          name: 'home',
	          component: home
	      },
	      {
	          path: '/components/login.html',
	          name: 'login',
	          component: login
	      },
	      {
	          path: '/components/logout.html',
	          name: 'logout',
	          component: logout
	      },
	      {
	    	  path: '/components/pageInput.html',
	          name: 'pageInput',
	          component: pageInput
	      },
	      {
	    	  path: '/components/saveInput.html',
	    	  name: 'saveInput',
	    	  component: saveInput
	      },    
    ]
})