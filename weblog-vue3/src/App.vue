<template>
	<!-- html 代码 -->
   <!-- 设置语言为中文 -->
   <el-config-provider :locale="locale">
      <!-- 全局路由过渡动画 -->
      <router-view></router-view>
   </el-config-provider>
</template>

<script setup>
// js 代码, setup 标识通常和组合式 API 搭配使用, 用于告诉 Vue 需要在编译时进行一些处理，让我们可以更简洁地使用组合式 API
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import { onMounted } from 'vue'
const locale = zhCn

// 全局错误处理
onMounted(() => {
    // 捕获未处理的 Promise 错误
    window.addEventListener('unhandledrejection', (event) => {
        console.error('未处理的 Promise 错误:', event.reason)
    })
    
    // 捕获全局错误
    window.addEventListener('error', (event) => {
        console.error('全局错误:', event.error)
    })
})
</script>

<style scoped>
 /* css 代码， scoped 表示节点内 css 样式只针对此组件有效，不影响其他组件  */
/* 自定义顶部加载 Loading 颜色 */
#nprogress .bar {
   background: #409eff!important;
}

/* 淡入淡出动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* 滑动动画 */
.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease, opacity 0.3s ease;
}

.slide-enter-from {
  transform: translateX(30px);
  opacity: 0;
}

.slide-leave-to {
  transform: translateX(-30px);
  opacity: 0;
}

/* 缩放动画 */
.zoom-enter-active,
.zoom-leave-active {
  transition: transform 0.3s ease, opacity 0.3s ease;
}

.zoom-enter-from,
.zoom-leave-to {
  transform: scale(0.95);
  opacity: 0;
}
</style>
