import '@/assets/main.css'
// 引入 animate.css 动画库
import 'animate.css';
// 引入进度条样式
import 'nprogress/nprogress.css'
// 引入 Element Plus 样式
import 'element-plus/dist/index.css'
// 引入 highlight.js 样式
import 'highlight.js/styles/github.css'
// 引入 highlight.js 核心功能
import hljs from 'highlight.js'
import javascript from 'highlight.js/lib/languages/javascript'
import python from 'highlight.js/lib/languages/python'
import java from 'highlight.js/lib/languages/java'
import xml from 'highlight.js/lib/languages/xml'
import css from 'highlight.js/lib/languages/css'
import typescript from 'highlight.js/lib/languages/typescript'
import php from 'highlight.js/lib/languages/php'
import cpp from 'highlight.js/lib/languages/cpp'
import csharp from 'highlight.js/lib/languages/csharp'
import ruby from 'highlight.js/lib/languages/ruby'

// 注册语言
hljs.registerLanguage('javascript', javascript)
hljs.registerLanguage('python', python)
hljs.registerLanguage('java', java)
hljs.registerLanguage('html', xml)
hljs.registerLanguage('css', css)
hljs.registerLanguage('typescript', typescript)
hljs.registerLanguage('php', php)
hljs.registerLanguage('cpp', cpp)
hljs.registerLanguage('csharp', csharp)
hljs.registerLanguage('ruby', ruby)


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
// 图片点击放大
import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'

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
// 应用图片点击放大
app.use(VueViewer)

// 全局注册 highlight.js 方法
app.config.globalProperties.$hljs = hljs
app.mount('#app')