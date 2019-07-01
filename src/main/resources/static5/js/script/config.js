define(function () {
	require.config({
		baseUrl:'./js',
		paths: {
			'vue': 'lib/vue.min',
			'jquery':'lib/jquery-3.2.1.min',
			'path': 'api/path',

			'vuex':'lib/vuex',
			'state':'store/state',
			'mutation':'store/mutation',
			'getter':'store/getter',
			'action': 'store/action',

			'store': 'store/index'
		}
	})
})