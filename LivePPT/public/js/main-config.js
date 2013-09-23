seajs.config({

	alias: {
		'jquery': {src:'/assets/js/jquery-1.9.1.min.js', exports:'$'},
		'bootstrap': '/assets/js/bootstrap.min.js',
		'fineuploader': '/assets/js/fineuploader/jquery.fineuploader-3.4.1.min.js',
		'uploadify': '/assets/js/jquery.uploadify.min.js',
		'uploadifive': '/assets/js/jquery.uploadifive.min.js',
		'jquery-knob': '/assets/js/jquery.knob.js',
		'login': '/assets/js/login.js',
		'crypto': 'http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/hmac-sha1.js'
	},

	preload: ['jquery','bootstrap', 'login', 'crypto']

});