<template>
    <!-- 固定位置按钮，带平滑显示/隐藏动画 -->
    <div 
        :class="[
            'cursor-pointer fixed inline rounded transition-all duration-300 ease-in-out transform',
            'border hover:shadow-md',
            showScrollToTopBtn ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-10 pointer-events-none',
            'bottom-2 right-2 md:bottom-10 md:right-10',
            'bg-white hover:bg-gray-100 border-gray-200 p-3',
        ]"
        :style="{
            ...customStyle,
            bottom: `${position.bottom}rem`,
            right: `${position.right}rem`,
        }"
        @click="scrollToTop"
    >
        <svg 
            class="w-4 h-4 text-gray-500 dark:text-white" 
            aria-hidden="true" 
            xmlns="http://www.w3.org/2000/svg" 
            fill="none"
            viewBox="0 0 10 14"
        >
            <path 
                stroke="currentColor" 
                stroke-linecap="round" 
                stroke-linejoin="round" 
                stroke-width="2"
                d="M5 13V1m0 0L1 5m4-4 4 4"
            ></path>
        </svg>
    </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

// 可配置的属性
const props = defineProps({
    // 滚动阈值（像素）
    scrollThreshold: {
        type: Number,
        default: 300
    },
    // 位置配置
    position: {
        type: Object,
        default: () => ({
            bottom: 2,
            right: 2
        })
    },
    // 自定义样式
    customStyle: {
        type: Object,
        default: () => ({})
    }
})

// 是否展示返回顶部按钮
const showScrollToTopBtn = ref(false)

// 添加滚动监听
onMounted(() => window.addEventListener('scroll', handleScroll))

// 移除滚动监听
onBeforeUnmount(() => window.removeEventListener('scroll', handleScroll))

const handleScroll = () => {
    // 如果页面滚动超过阈值，显示回到顶部按钮，否则隐藏
    showScrollToTopBtn.value = window.scrollY > props.scrollThreshold
}

// 滚动到顶部
const scrollToTop = () => {
    window.scrollTo({
        top: 0, // 距离顶部位置
        behavior: 'smooth' // 平滑滚动效果
    });
}
</script>

<style scoped>
/* 确保动画效果正常工作 */
.transition-all {
    transition-property: all;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
}

.duration-300 {
    transition-duration: 300ms;
}

.ease-in-out {
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
}

/* 确保在暗黑模式下也能正常显示 */
@media (prefers-color-scheme: dark) {
    .dark\:text-white {
        color: white;
    }
}
</style>

