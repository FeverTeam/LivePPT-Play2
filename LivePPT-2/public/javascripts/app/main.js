define(function(require, exports, module){
	require('jquery');
	require('underscore');
    require('backbone');

	var indexPage = Backbone.View.extend({
    	el: $("body"),
    	events: {
    		"click #test": "toggleSidebar"
    	},
 		toggleSidebar: function(){
 			var left = $("#xs-innerFrame").css("left");
			if(left == "0px"){
				$("#xs-innerFrame").animate({left: "-22%"}, 1000).animate({left: "-20.9%"}, 300);
                $("#test").text("Hide Me");
            }
			else {
                $("#xs-innerFrame").animate({left: "0"}, 1000);
                $("#test").text("Menu");
            }
 		}
	});  
    var app = new indexPage;
});