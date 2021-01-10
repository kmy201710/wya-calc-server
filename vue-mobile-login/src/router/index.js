import Vue from 'vue'
import VueRouter from 'vue-router'
// import VueResource from 'vue-resource'
import store from '@/store'

Vue.use(VueRouter);
// Vue.use(VueResource);

// 路由懒加载
const Home = () => import('../views/home/Home.vue');
// const Travel = () => import('../views/travel/Travel.vue');
const Live = () => import('../views/live/Live.vue');
const News = () => import('../views/news/News.vue');
const Mine = () => import('../views/mine/Mine.vue');
const Login = () => import(/* webpackChunkName: "group-login" */ '../views/login/Login.vue');
const LoginByCode = () => import(/* webpackChunkName: "group-login" */ '../views/login/LoginByCode.vue');
const LoginByPwd = () => import(/* webpackChunkName: "group-login" */ '../views/login/LoginByPwd.vue');
const RetrievePwd = () => import(/* webpackChunkName: "group-login" */ '../views/login/RetrievePwd.vue');
const Add = () => import('../views/add/Add.vue');
const User = () => import('../views/info/user.vue');
const Calc = () => import('../views/info/calc.vue');
const UserCalc = () => import('../views/info/userCalc.vue');

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    component: Home,
    meta: {
      title: "网站首页",
      requireAuth: false,
      showTab: true
    }
  },
  {
    path: '/live',
    component: Live,
    meta: {
      title: "动态信息",
      requireAuth: false,
      showTab: true
    }
  },
  {
    path: '/news',
    component: News
  },
  {
    path: '/mine',
    component: Mine
  },
  {
    path: '/login',
    component: Login,
    meta: {
      keepAlive: true,
      requireAuth: false
    }
  },
  {
    path: '/loginByCode',
    name: '/loginByCode',
    component: LoginByCode,
    meta: {
      requireAuth: false
    }
  },
  {
    path: '/loginByPwd',
    name: '/loginByPwd',
    component: LoginByPwd,
    meta: {
      requireAuth: false
    }
  },
  {
    path: '/retrievePwd',
    name: '/retrievePwd',
    component: RetrievePwd,
    meta: {
      requireAuth: false
    }
  },
  {
    path: '/add',
    component: Add,
    meta: {
      // title: "一个有趣的网站",
      requireAuth: false,
      showTab: true
    }
  },
  {
    path: '/info/user',
    component: User,
    meta: {
      title: "用户列表",
      requireAuth: false,
    }
  },
  {
    path: '/info/calc',
    component: Calc,
    meta: {
      title: "数学表达式",
      requireAuth: false,
    }
  },
  {
    path: '/info/userCalc',
    component: UserCalc,
    meta: {
      title: "答题详细列表",
    }
  },
];

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  // 需要权限校验
  if(to.meta.requireAuth === undefined && store.state.token === null){
    store.commit('toggleTabbarShow', false);
    next('/login');
  }
  if(to.path.startsWith('/info')){
    store.commit('toggleTabbarShow', false);
  } else {
    store.commit('toggleTabbarShow', to.meta.showTab === undefined ? false : to.meta.showTab);
  }
  next()
});

router.afterEach((to, from, next) => {
  window.document.title = to.meta.title == undefined ? 'EXP' : to.meta.title;
});

export default router
