seajs.config({
	
	alias: {
		'jquery': {src:'/assets/js/jquery-1.9.1.min.js', exports:'$'},
		'cookies': '/assets/js/jquery.cookie.js',
		'bootstrap': '/assets/js/bootstrap.min.js',
		'fineuploader': '/assets/js/fineuploader/jquery.fineuploader-3.4.1.min.js',
		'uploadify': '/assets/js/jquery.uploadify.min.js',
		'uploadifive': '/assets/js/jquery.uploadifive_customized.js',
		'jquery-knob': '/assets/js/jquery.knob.js',
		'login': '/assets/js/login.js',
		'hmac': 'http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/hmac-sha1.js',
		'aes': 'http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/aes.js',
		'ecb': 'http://crypto-js.googlecode.com/svn/tags/3.1.2/build/components/mode-ecb-min.js'
	},

	preload: ['jquery','bootstrap', 'login', 'hmac']

});
