import Vue = require("vue");

export const TodoComponent = Vue.extend({
    template: "#todo-component-template",
    props: ["index", "todo"],
})

