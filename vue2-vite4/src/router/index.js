import Vue from 'vue'
import VueRouter from 'vue-router'
import Manage from '../views/manage/Manage.vue'
import User from "@/views/manage/User.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import Person from "@/views/manage/Person.vue";
import FrontPerson from "@/views/front/Person.vue";

import File from "@/views/manage/File.vue";
import Role from "@/views/manage/Role.vue";
import Menu from "@/views/manage/Menu.vue";
import NotFound from "@/views/404.vue";
import store from "@/store";
import Password from "@/views/manage/Password.vue";
import Front from "@/views/front/Front.vue";
import Article from "@/views/front/Article.vue";
import Home from "@/views/front/Home.vue";
import guestbook from "@/views/front/Guestbook.vue";
import About from "@/views/front/About.vue";
import ArticleDetail from "@/views/front/ArticleDetail.vue";


Vue.use(VueRouter)
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err)
}

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/404',
    name: '404',
    component: NotFound
  },
  {
    path: '/',
    name: 'front',
    component: Front,
    redirect: '/home',
    children:[
      {
        path: 'home',
        name: '主页',
        component: Home
      },
      {
        path: 'article',
        name: '文章',
        component: Article,
        meta: {
          keepAlive: true //需要被缓存
        }
      },
      {
        path: 'article/detail',
        name: '文章详细',
        component: ArticleDetail,
      },
      {
        path: 'guestbook',
        name: '留言板',
        component: guestbook,
      },
      {
        path: 'about',
        name: '关于',
        component: About,
      },
      {
        path: 'person',
        name: '个人中心',
        component: FrontPerson,
      },

    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: import.meta.env.VITE_API_BASE_URL,
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (to.name === '文章详细') {
      // 如果是从文章列表页面进入文章详细页面，则保存滚动位置
      return { x: 0, y: 0 };
    } else if (from.name === '文章详细'&&to.name==='文章') {
      // 如果是从文章详细页面返回文章列表页面，则恢复滚动位置
      const scrollTop = sessionStorage.getItem('articleScrollTop') || 0;
      return { x: 0, y: parseInt(scrollTop) };
    } else {
      // 其他情况滚动到页面顶部
      return { x: 0, y: 0 };
    }
  }
})

export const resetRouter = () => {
  router.matcher = new VueRouter({
    mode: 'history',
    base: import.meta.env.VITE_API_BASE_URL,
    routes,

  })
}
const modules = import.meta.glob("../views/**/**.vue");

export const setRoutes = () => {
  const storeMenus = localStorage.getItem("menus");
  // 拼装动态路由
  if (storeMenus){
    const manageRoute = {path: '/', name: 'Manage', component: Manage,children:[
        {path: 'manage/person', name: '个人信息', component: Person},
        {path: 'manage/password', name: '修改密码', component: Password},
      ]}
    const menus = JSON.parse(storeMenus)
    menus.forEach(item => {
      if (item.path){
        let itemMenu = { path: item.path.replace("/",""), name: item.name, component: modules['../views/manage/' + item.pagePath+'.vue']}
        manageRoute.children.push(itemMenu)
      }else if(item.children.length){
        item.children.forEach(item =>{
          if (item.path){
            let itemMenu = { path: item.path.replace("/",""), name: item.name, component: modules['../views/manage/' + item.pagePath+'.vue']}
            manageRoute.children.push(itemMenu)
          }
        })
      }
    })
    const currentRouteNames = router.getRoutes().map(v => v.name)
    if (!currentRouteNames.includes('Manage')){
      // 动态添加到现在的路由对象中去
      router.addRoute(manageRoute)
    }
  }
}
// 重置我就再set一次路由
setRoutes()
router.beforeEach((to,from, next)=>{
  localStorage.setItem("currentPathName",to.name) // 设置当前的路由名称
  store.commit("setPath")
  // 未找到路由的情况
  if(!to.matched.length){
    const storeMenus = localStorage.getItem("menus")
    if(storeMenus){
      next("/404")
    }else {
      next("/login")
    }
  }
  // 其他的情况都放行
  next()
})
export default router
