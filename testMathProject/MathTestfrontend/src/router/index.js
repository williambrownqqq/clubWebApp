import Vue from 'vue'
import Router from 'vue-router'
import Math from '@/components/Math'
import SearchEq from '@/components/SearchEq'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Math',
      component: Math
    },
    {
      path: '/find',
      name: 'SearchEq',
      component: SearchEq
    }
  ]
})
