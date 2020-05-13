import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import FriendChat from '../views/chat/FriendChat.vue'
import Statistics from '../views/sys/Statistics'
import UserInfo from "../views/UserInfo";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: Login,
    hidden:true
  },
  {
    path: '/home',
    name: 'home',
    component: Home,
    hidden:true,
    children:[
      {
        path: '/chat',
        name: '在线聊天',
        component: FriendChat,
        hidden:true
      },{
        path: '/Statistics',
        name: '访客统计',
        component: Statistics,
        hidden:true
      },{
        path: '/UserInfo',
        name: '个人中心',
        component: UserInfo,
        hidden:true
      }
    ]
  },
  {
    /*解决404问题*/
    path:'*',
    redirect:'/home'
  }
]

const router = new VueRouter({
  routes
})

export default router
