requirejs.config({
    paths:{
        text: './lib/text.2.0.15',
        vue: './lib/vue.2.1.8',
        vueRouter: './lib/vue-route',
        vuex: './lib/vuex',
        jquery: './lib/jquery-3.2.1',
        bootstrap: './lib/bootstrap'
    },
    map: {
        '*':{
            css: './lib/css'
        }
    },
    shim:{
        bootstrap:{
            deps:[
                'jquery',
                'css!./lib/bootstrap.css'
            ]
        }
    }
})


require(['bootstrap', './app', 'css!./layout.css'],function( bootstrap, app){
    var _app = app.createApp();
    _app.registerGlobalComponents(['title', 'route']).done(function(){
        var vue = _app.createVue();
        var cxt = app.getVue().createContext();
        var r = {
            state: {
                childs: []
            },
            mutations: {
                childs: function(state, data){
                    state.childs = data;
                }
            },
            actions: {
                childs: function(state, data){
                    state.commit('childs', data);
                }
            }
        }
        vue.$store.registerModule('router', r);
        vue.$mount('#app');
    });
});
