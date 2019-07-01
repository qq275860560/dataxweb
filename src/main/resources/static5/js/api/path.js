define(['script/fetch', 'api/port'], function (fetch, port) {
	// 完整地址
	function LongURL (url) {
	  return port + url
	}

	return {
		GetLoginCode : (function () {
			return fetch({
				url: LongURL('/api/v2/user/qrcode'),
    			method: 'get'
			})
		})()
	}
})