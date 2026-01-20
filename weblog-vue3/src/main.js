import '@/assets/main.css'
// 引入 animate.css 动画库
import 'animate.css';
// 引入进度条样式
import 'nprogress/nprogress.css'
// 引入 Element Plus 样式
import 'element-plus/dist/index.css'


import { createApp } from 'vue'
import App from '@/App.vue'
// 导入路由
import router from '@/router'
// 导入全局路由守卫
import '@/permission'
// 导入 Element Plus
import ElementPlus from 'element-plus'
// 导入 Element Plus 图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// 引入全局状态管理 Pinia
import pinia from '@/stores'


const app = createApp(App)
app.use(router)
// 应用 Pinia
app.use(pinia)
// 应用 Element Plus
app.use(ElementPlus)
// 引入图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.mount('#app')