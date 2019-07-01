define(function () {
	return function (fun, data) {
		if (data) {
			return new Promise((resolve, reject) => {
				fun(data).then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		} else {
			return new Promise((resolve, reject) => {
				fun().then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		}
	}
})