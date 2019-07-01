require(['config'], function () {
	require(['vue','jquery', 'path', 'api/promise', 'vuex', 'store'], function (Vue, $, apidata, promise, Vuex, store) {
		Vue.use(Vuex)

		new Vue({
			el: '#song',
			store: store.store,
			data: {
				songs: 'VueX状态处理数据',
				state: '',
				mutation: '',
				getter: '',
				action: ''
			},
			methods: {
				onState() {
					// this.$store.commit('SET_TOKEN', 123)

					this.state = this.$store.state.Vstate
					// console.log(this.$store.getters.token)
				},
				onMutation () {
					this.$store.commit('SET_VSTATE', '使用mutations改变后的state')
					this.mutation = this.$store.state.Vstate
				},
				onGetter() {
					this.getter = this.$store.getters.Vstate
				},
				onAction() {
					this.$store.dispatch('getState', '使用action改变后的数据')
					this.action = this.$store.state.Vstate
				}
			}
		})
	})
})