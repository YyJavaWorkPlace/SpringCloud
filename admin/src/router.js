import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login"
import Admin from "./views/admin"
import Welcome from "./views/admin/welcome"
import Chapter from "./views/admin/chapter"
import Section from "./views/admin/sectionVue"
import courseVue from "./views/admin/courseVue";
import categoryVue from "./views/admin/categoryVue";
import teacherVue from "./views/admin/teacherVue";
import fileVue from "./views/admin/fileVue";

Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [
        {
            path: "*",
            redirect: "/login",
        },
        {
            path: "/login",
            component: Login
        },
        {
            path: "/",
            name: "admin",
            component: Admin,
            children: [{
                path: "welcome",
                name: "welcome",
                component: Welcome
            },
                {
                    path: "business/category",
                    name: "business/category",
                    component: categoryVue
                },
                {
                    path: "business/course",
                    name: "business/course",
                    component: courseVue
                },
                {
                    path: "business/chapter",
                    name: "business/chapter",
                    component: Chapter
                },
                {
                    path: "business/section",
                    name: "business/section",
                    component: Section
                },
                {
                    path: "business/teacher",
                    name: "business/teacher",
                    component: teacherVue
                },
                {
                    path: "file/file",
                    name: "file/file",
                    component: fileVue
                },
            ]
        },
    ]
})