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
    // redirect: '/login',
    children: [
        {
          meta: {history: true, title: "我的消息"},
          path: '/hello/chat',
          name: 'chat',
          component: () => import('../components/chat/ChatList.vue')
        },
        {
          meta: {history: true, title: "管理员"},
          path: '/hello/codeying/admin',
          name: 'admin',
          component: () => import('../views/admin/Index.vue')
        },
        {
          meta: {history: true, title: "商家"},
          path: '/hello/codeying/merchant',
          name: 'merchant',
          component: () => import('../views/merchant/Index.vue')
        },
        {
          meta: {history: true, title: "用户"},
          path: '/hello/codeying/user',
          name: 'user',
          component: () => import('../views/user/Index.vue')
        },
        {
          meta: {history: true, title: "商品类型"},
          path: '/hello/codeying/stype',
          name: 'stype',
          component: () => import('../views/stype/Index.vue')
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

