require.config({
	urlArgs: "bust=" +  (new Date()).getTime(),
    baseUrl:'lib',
    shim:{
        'vue':{
            exports:'vue'
        }
    },
    paths:{
        'vue':'../lib/vue',
        'header':'../vue-module/tq-header',
        'footer':'../vue-module/tq-footer',
		'home':'../vue-module/tq-home',
		'category':'../vue-module/tq-category'
    },
});

require(['vue','header','footer','home','category'],function(vue,header,footer,home,category){

});