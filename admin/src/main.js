import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false
// vue入口文件，用来初始化vue实例并集成所需要的插件
new Vue({
  render: h => h(App),
}).$mount('#app')
