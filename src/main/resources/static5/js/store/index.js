define(['vue','vuex','state','mutation','getter','action'], function (Vue, Vuex, state, mutations, getters, actions) {
	Vue.use(Vuex)

	const store = new Vuex.Store({
		state,
		mutations,
		getters,
		actions
	})

	return {
		store: store
	}
})