seajs.config({
    base: "/assets/js/",
    alias: {
        'aes': 'lib/cypto-js/3.1.2/aes.js',
        'bootstrap': 'lib/bootstrap/bootstrap-3.0.3.js',
        'ecb': 'lib/cypto-js/3.1.2/mode-ecb-min.js',
        'hmac': 'lib/cypto-js/3.1.2/hmac-sha1.js',
        'jquery': "lib/jquery/jquery-1.10.2.js",
        'jquery-knob': 'lib/jquery.knob.js',
        'jquery.mustache': "lib/jquery.mustache.js",
        'uploadifive': 'lib/jquery.uploadifive/jquery.uploadifive_customized.js',
        'mustache': "lib/mustache.js"
    },
    preload: ['bootstrap']
});