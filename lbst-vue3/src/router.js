import { createRouter, createWebHistory } from 'vue-router';
import { checkLoginStatus } from './api/loginApi';
import component from 'element-plus/es/components/tree-select/src/tree-select-option.mjs';

const routes = [
    {
        path: '/login',
        name: 'login',
        component: () => import('./views/login/Login.vue'),
    },
    {
        path: '/',
        component: () => import('./views/home/Home.vue'),
        redirect: '/person-center',
        children: [
            {
                path: 'person-center',
                name: 'person-center',
                component: () => import('./views/person/Person.vue')
            },
            {
                path: 'library',
                name: 'library',
                children: [
                    {
                        path: 'books',
                        name: 'books',
                        component: () => import('./views/library/Books.vue')
                    },
                    {
                        path: 'borrow',
                        name: 'borrow',
                        component: () => import('./views/library/Borrow.vue')
                    },
                    {
                        path: 'shop',
                        name: 'shop',
                        component: () => import('./views/library/Shop.vue')
                    }
                ]
            },
            {
                path: 'member',
                name: 'member',
                component: () => import('./views/member/Member.vue')
            },
            {
                path: 'log',
                children: [
                    {
                        path: 'login-log',
                        name: 'login-log',
                        component: () => import('./views/log/LoginLog.vue')
                    },
                    {
                        path: 'libary-log',
                        name: 'libary-log',
                        component: () => import('./views/log/LibraryLog.vue')
                    },
                    {
                        path: 'operation-log',
                        name: 'operation-log',
                        component: () => import('./views/log/OptLog.vue')
                    },
                    {
                        path: 'wrong-log',
                        name: 'wrong-log',
                        component: () => import('./views/log/WrongLog.vue')
                    }
                ]
            }
        ]
    }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach(async (to, from) => {
    if (to.path == '/login') return true;
    let resp = await checkLoginStatus();
    let data = resp.data;
    if (data.data) return true;
    return { name: 'login' };
})

export default router;
