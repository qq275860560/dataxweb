 //require define 函数 start
define(['vue'], function(Vue) {
 //数据
    var data = {
        list: [{
            name: "购物",
            url: "./index.html",
        }, {
            name: "扫码",
            url: "#"
        }, {
            name: "购物车",
            url: "#"
        }, {
            name: "活动",
            url: "#"
        }, {
            name: "我的",
            url: "#"
        }],
    };
    //定义组件 模板 数据 方法
    var header = Vue.extend({
        template:  '<div class="header">\
                        <div class="header-main">\
                            <ul>\
                                <li v-for="i in list">\
                                    <a v-bind:href="i.url">\
                                        {{i.name}}\
                                    </a>\
                                </li>\
                            </ul>\
                        </div>\
                    </div>',

        data: function() {
            return data;
        },

        methods: {
            show: function() {

            }
        },
    });
    // 注册组件标签 <tq-header> 绑定组件 
    Vue.component('tq-header', header);
    //实例化
    new Vue({
        el: '#header'
    });
});