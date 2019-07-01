import Vue = require("vue");

import { Todo } from "./todo";
import { TodoComponent } from "./todo-component";

type Instance = Vue & Data;

class Data {
    message = "";
    todos: Todo[] = [];
    currentView: "all" | "completed" | "remaining" = "all";
}

function isCompleted(todo: Todo) {
    return todo.completed;
}

function isNotCompleted(todo: Todo) {
    return !todo.completed;
}

const v = new Vue({
    el: "#app",
    data: new Data,
    methods: {
        createTodo(this: Instance) {
            const message = this.message && this.message.trim();
            if (!message) {
                return;
            }
            this.todos.push(new Todo((message)));
        },
        removeTodo(this: Instance, index: number) {
            this.todos.splice(index, 1);
        },
    },
    computed: {
        completed(this: Instance) {
            return this.todos.filter(isCompleted);
        },
        remaining(this: Instance) {
            return this.todos.filter(isNotCompleted);
        },
        shouldRender(this: Instance): (t: Todo) => boolean {
            switch (this.currentView) {
                case "all":
                    return () => true;
                case "completed":
                    return isCompleted;
                case "remaining":
                    return isNotCompleted;
            }
        },
    },
    components: {
        // Automatically gets registered as 'todo-component'.
        TodoComponent,
    }
});
