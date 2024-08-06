import {createRouter, createWebHistory} from 'vue-router';

const routes = [
    {
        path: '/login',
        component: () => import('./views/login/Login.vue'),
    },
    {
        path: '/',
        redirect: '/login',
    }
]

const router = createRouter({history: createWebHistory(), routes})

export default router;
