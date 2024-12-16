import Vue from 'vue'
import Router from 'vue-router'
import QCMaster from '@/components/QCMaster'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'QCMaster',
      component: QCMaster
    }
  ]
})
