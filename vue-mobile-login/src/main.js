import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router'
import store from './store'
import '@/assets/css/reset.css'
import '@/assets/css/border.css'
import fastCLick from 'fastclick'
fastCLick.attach(document.body);
import http from '@/utils/http'
import { Tabbar, TabbarItem, NavBar, Tab, Tabs, Grid, GridItem, Divider, DropdownMenu, Toast,
  DropdownItem, Switch, Button, List, Cell, CellGroup, PullRefresh, Row, Col, Field, ActionSheet, Icon, Image} from "vant";
import 'vant/lib/index.css'
import VueResource from 'vue-resource'
import VueScroller from 'vue-scroller'
// import VueWechatTitle from 'vue-wechat-title'
// https://blog.csdn.net/qq_41810005/article/details/90699102
import 'vue-easytable/libs/themes-base/index.css'			// 引入样式
import {VTable, VPagination} from 'vue-easytable'			// 导入 table 和 分页组件
// https://blog.csdn.net/weixin_38673922/article/details/107189317

// import ViewUI from 'view-design';
// import 'view-design/dist/styles/iview.css';
import Message from "./components/Message";
// 引入轮播插件：vue-awesome-swiper
import VueAwesomeSwiper from 'vue-awesome-swiper'
// https://blog.csdn.net/cxwtsh123/article/details/79468146

Vue.use(VueAwesomeSwiper)
Vue.use(Message) // 因为我们的对象上定义了 install 方法, 所以可以直接调用 Vue 的 use 方法
// https://blog.csdn.net/qq_42991509/article/details/103927293

Vue.use(ElementUI)
// https://blog.csdn.net/weixin_44868863/article/details/90454112
Vue.use(VueScroller)
// Vue.use(VueAwesomeSwiper);
// Vue.use(VueWechatTitle)
Vue.component(VTable.name, VTable)							// 注册到全局
Vue.component(VPagination.name, VPagination)
Vue.use(Tabbar).use(TabbarItem).use(NavBar).use(Tab).use(Tabs).use(Grid).use(GridItem).use(Divider).use(DropdownMenu)
    .use(DropdownItem).use(Switch).use(Button).use(List).use(Cell).use(CellGroup).use(PullRefresh).use(Row).use(Col)
    .use(Field).use(ActionSheet).use(Icon).use(Image).use(Toast);

Vue.config.productionTip = false;

Vue.prototype.$http = http;
// 前后端交互跨域问题解决方案，跨域资源共享（CORS）
// Vue.http.options.credentials = true;

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
  components: { App }
}).$mount('#app');
