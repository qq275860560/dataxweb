define(['vue','text!../components/nav.html'],function(Vue, navTpl){
    Vue.component('my-nav',{
        template:  navTpl,
        props: ['num'],
        data: function(){
            return {
                text: '导航'
            }
        },
        created: function(){
            console.log(this.num)
        },
        methods: {
            handleClick: function(nav){
                alert('即将跳转到' + nav)
            }
        }
    });
});
