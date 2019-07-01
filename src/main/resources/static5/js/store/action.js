define(function () {
	return {
		getState: function (context, value) {
			context.commit('SET_VSTATE', value)
		}
	}
})