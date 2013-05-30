seajs.config({

	alias: {
		'jquery': {src:'/assets/js/jquery-1.9.1.min.js', exports:'$'},
		'bootstrap': '/assets/js/bootstrap.min.js',
		'fineuploader': '/assets/js/fineuploader/jquery.fineuploader-3.4.1.min.js',
		'uploadify': '/assets/js/jquery.uploadify.min.js',
		'uploadifive': '/assets/js/jquery.uploadifive.min.js'
	},

	preload: ['jquery','bootstrap']

});