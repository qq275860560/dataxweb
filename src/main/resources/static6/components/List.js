define(['vue','text!../components/list.html', '../components/nav'],function(Vue, listTpl){
    Vue.component('list',{
        template:listTpl,
        data: function(){
            return {
                list: [2,4,6,8]
            }
        }
    })
});