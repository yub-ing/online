import { createRouter, createWebHistory } from 'vue-router';
import Index from '../views/Index.vue';
import Login from '@/views/Login'
const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  //前台页面router
  {
    path: '/',
    name: 'Web',
    component: () => import('../views/web/WebIndex.vue'),//网站首页router-view
    children: [
      {
        path: '/welcome',
        name: 'Welcome',
        component: () => import('../views/web/Web.vue') //首页
      },{
        path: '/welcome/star',
        name: 'WelcomeStar',
        component: () => import('../views/web/WebStar.vue') //收藏页
      },
      {
        path: '/welcome/goods',
        name: 'WelcomeGoodsList',
        component: () => import('../views/web/Web.vue')//商品信息  首页
      },
      {
        path: '/welcome/goods/detail',
        name: 'WelcomeGoodsDetail',
        component: () => import('../views/web/GoodsDetail.vue')//商品信息  详情页
      },
    ]
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
          meta: {history: true, title: "商品信息"},
          path: '/hello/codeying/goods',
          name: 'goods',
          component: () => import('../views/goods/Index.vue')
        },
        {
          meta: {history: true, title: "商品类型"},
          path: '/hello/codeying/stype',
          name: 'stype',
          component: () => import('../views/stype/Index.vue')
        },
        {
          meta: {history: true, title: "购物车"},
          path: '/hello/codeying/cart',
          name: 'cart',
          component: () => import('../views/cart/Index.vue')
        },
        {
          meta: {history: true, title: "订单详情"},
          path: '/hello/codeying/orderdetail',
          name: 'orderdetail',
          component: () => import('../views/orderdetail/Index.vue')
        },
        {
          meta: {history: true, title: "用户订单"},
          path: '/hello/codeying/order',
          name: 'order',
          component: () => import('../views/order/Index.vue')
        },
        {
          meta: {history: true, title: "公告"},
          path: '/hello/codeying/notice',
          name: 'notice',
          component: () => import('../views/notice/Index.vue')
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

