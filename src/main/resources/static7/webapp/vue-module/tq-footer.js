   //require define 函数 start
define(['vue'], function(Vue) {
   // 定义组件内容，数据，方法
    var footer = Vue.extend({
       //模板
        template: '<div class="footer">原谷生鲜</div>',
        //数据
        data: function() {
            return {
                name: 'test name'
            }
        },
        //方法
        methods: {
            show: function() {
                alert(this.name);
            }
        }
    });

    // 注册组件的标签 <tq-footer> 绑定组件
    Vue.component('tq-footer', footer);

    //实例化
    new Vue({
        el: '#footer',
    });

    //vue组件结束
	});