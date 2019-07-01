//定义要加载的js文件，简称：相对/完整路径,paths还有一个重要的功能，就是可以配置多个路径，如果远程cdn库没有加载成功，可以加载本地的库
require.config({
	
	//baseUrl : "../../modules/",
	shim: {
        "underscore" : {
            exports : "_"
        },
        "jquery.form" : ["jquery"]
    },
	/*
	// data-main="script/module/app" 配置以后这里不配置也可以，所有模块和js文件与app入口文件在同一目录即可
	paths : {
		"jquery" : ["jquery"],
		"says":["says"],
		"hello":["hello"]
	}
	*/
	
})