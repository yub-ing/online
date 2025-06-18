import { createRouter, createWebHistory } from 'vue-router';
import Index from '../views/Index.vue';
import Login from '@/views/Login'
const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/',
    name: 'Web',
    component: Index
  },

  //后台页面router
  {
    path: '/hello',
    name: 'Index',
    component: Index,
    children: [
        {
          meta: {history: true, title: "管理员"},
          path: '/hello/codeying/admin',
          name: 'admin',
          component: () => import('../views/admin/Index.vue')
        },
        {
          meta: {history: true, title: "用户"},
          path: '/hello/codeying/user',
          name: 'user',
          component: () => import('../views/user/Index.vue')
        },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})


router.beforeEach((to, from, next) => {
  if(to.meta.history === true) {
    Cache.dispatch("menu", 'addHistory', to);
  }
  next()
})

export default router

