import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout'

const routes = [
    {
        path: '/',
        name: 'Layout',
        component: Layout,
        redirect: "/login",
        children: [


            ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import("@/views/Login")
    },
    {
        path: '/user',
        name: 'User',
        component: () => import("@/views/User"),
        meta: {
            requireAuth: true
        }
    },
    {
        path: '/game',
        name: 'Game',
        component: () => import("@/views/Game.vue"),
        meta: {
            requireAuth: true
        }
    }
    // {
    //     path: '/register',
    //     name: 'Register',
    //     component: () => import("@/views/Register")
    // },
    // {
    //     path: '/person',
    //     name: 'Person',
    //     component: () => import("@/views/Person")
    // },
    // {
    //     path: '/password',
    //     name: 'Password',
    //     component: () => import("@/views/Password")
    // }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router