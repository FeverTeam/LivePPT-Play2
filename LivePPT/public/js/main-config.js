seajs.config({

	alias: {
		'jquery': '/assets/js/jquery-1.9.1.min.js',
		'bootstrap': '/assets/js/bootstrap.min.js',
		'fineuploader': '/assets/js/fineuploader/jquery.fineuploader-3.4.1.min.js',
		'uploadify': '/assets/js/jquery.uploadify.min.js'
	},

	preload: ['jquery','bootstrap', 'fineuploader']
});