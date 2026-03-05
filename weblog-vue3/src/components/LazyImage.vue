<template>
  <div class="lazy-image-container" :style="containerStyle">
    <!-- 加载占位符 -->
    <div v-if="!loaded && !error" class="lazy-image-placeholder" :style="placeholderStyle">
      <div v-if="showLoading" class="lazy-image-loading">
        <svg class="w-10 h-10 animate-spin text-blue-500" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
          <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
        </svg>
      </div>
    </div>
    
    <!-- 错误占位符 -->
    <div v-else-if="error" class="lazy-image-error" :style="placeholderStyle">
      <div class="lazy-image-error-content">
        <img
          :src="props.defaultSrc"
          :alt="alt"
          class="lazy-image-default"
          :style="{
            width: '80%',
            height: '80%',
            objectFit: 'cover',
            borderRadius: '4px'
          }"
        />
        <p class="text-gray-400 mt-2">图片加载失败</p>
        <button class="mt-4 text-sm text-blue-500 hover:text-blue-700" @click="retryLoad">重试</button>
      </div>
    </div>
    
    <!-- 图片元素 -->
    <img
      ref="imageRef"
      :src="loaded ? src : ''"
      :data-src="src"
      :alt="alt"
      class="lazy-image"
      :style="imageStyle"
      @load="handleLoad"
      @error="handleError"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue'

// 组件属性
const props = defineProps({
  // 图片源地址
  src: {
    type: String,
    required: true
  },
  // 图片替代文本
  alt: {
    type: String,
    default: ''
  },
  // 图片宽度
  width: {
    type: [String, Number],
    default: '100%'
  },
  // 图片高度
  height: {
    type: [String, Number],
    default: 'auto'
  },
  // 是否显示加载动画
  showLoading: {
    type: Boolean,
    default: true
  },
  // 加载阈值
  threshold: {
    type: Number,
    default: 0.1
  },
  // 占位符背景色
  placeholderColor: {
    type: String,
    default: '#f3f4f6'
  },
  // 默认图片地址（当图片加载失败时显示）
  defaultSrc: {
    type: String,
    default: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=minimal%20abstract%20geometric%20pattern%20background%20in%20neutral%20colors&image_size=square'
  }
})

// 图片状态
const loaded = ref(false)
const error = ref(false)
const imageRef = ref(null)
const observer = ref(null)

// 计算样式
const containerStyle = computed(() => ({
  width: props.width,
  height: props.height,
  position: 'relative',
  overflow: 'hidden'
}))

const placeholderStyle = computed(() => ({
  width: '100%',
  height: '100%',
  backgroundColor: props.placeholderColor,
  position: 'absolute',
  top: 0,
  left: 0,
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center'
}))

const imageStyle = computed(() => ({
  width: '100%',
  height: '100%',
  objectFit: 'cover',
  opacity: loaded.value ? 1 : 0,
  transition: 'opacity 0.3s ease',
  position: 'relative',
  zIndex: 1
}))

// 图片加载完成处理
const handleLoad = () => {
  loaded.value = true
  error.value = false
}

// 图片加载失败处理
const handleError = () => {
  loaded.value = false
  error.value = true
}

// 重试加载图片
const retryLoad = () => {
  error.value = false
  loaded.value = false
  // 重新初始化观察者
  initObserver()
}

// 初始化IntersectionObserver
const initObserver = () => {
  if ('IntersectionObserver' in window) {
    observer.value = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            // 图片进入视口，开始加载
            loaded.value = true
            // 停止观察
            if (observer.value) {
              observer.value.unobserve(entry.target)
            }
          }
        })
      },
      {
        threshold: props.threshold
      }
    )

    if (imageRef.value) {
      observer.value.observe(imageRef.value)
    }
  } else {
    // 浏览器不支持IntersectionObserver，直接加载图片
    loaded.value = true
  }
}

// 组件挂载时初始化
onMounted(() => {
  initObserver()
})

// 组件卸载时清理
onUnmounted(() => {
  if (observer.value && imageRef.value) {
    observer.value.unobserve(imageRef.value)
  }
})
</script>

<style scoped>
.lazy-image-container {
  border-radius: 0.375rem;
}

.lazy-image-loading {
  color: #9ca3af;
}

.lazy-image {
  display: block;
}

.lazy-image.error {
  display: none;
}

.lazy-image-error-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
}
</style>