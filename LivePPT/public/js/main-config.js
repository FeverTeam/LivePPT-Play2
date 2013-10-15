seajs.config({
	
	alias: {
		'jquery': {src:'/assets/js/jquery-1.9.1.min.js', exports:'$'},
		'cookies': '/assets/js/jquery.cookie.js',
		'bootstrap': 'http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js',
		'fineuploader': '/assets/js/fineuploader/jquery.fineuploader-3.4.1.min.js',
		'uploadify': '/assets/js/jquery.uploadify.min.js',
		'uploadifive': '/assets/js/jquery.uploadifive_customized.js',
		'jquery-knob': '/assets/js/jquery.knob.js',
		'login': '/assets/js/login.js',
		'hmac': '/assets/js/cypto-js/3.1.2/hmac-sha1.js',
		'aes': '/assets/js/cypto-js/3.1.2/aes.js',
		'ecb': '/assets/js/cypto-js/3.1.2/mode-ecb-min.js'
	},

	preload: ['bootstrap', 'login', 'hmac']

});
