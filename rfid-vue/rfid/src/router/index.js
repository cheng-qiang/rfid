import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import FriendChat from '../views/chat/FriendChat.vue'

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
