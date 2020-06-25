import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";
Vue.config.productionTip = false;
Vue.prototype.$ajax=axios;
// vue入口文件，用来初始化vue实例并集成所需要的插件
new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
