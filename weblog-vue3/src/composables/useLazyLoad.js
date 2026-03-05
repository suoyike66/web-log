import { nextTick } from 'vue'

// 图片懒加载composable函数
export function useLazyLoad() {
  // 初始化图片懒加载
  const initLazyLoad = (container) => {
    if (!container) return

    // 获取所有图片元素
    const images = container.querySelectorAll('img')

    // 为图片添加占位符样式
    images.forEach((img) => {
      // 添加基础样式
      img.style.transition = 'opacity 0.3s ease'
      img.style.opacity = '0'
      
      // 如果没有设置宽度和高度，添加默认值
      if (!img.style.width && !img.width) {
        img.style.width = '100%'
      }
      if (!img.style.height && !img.height) {
        img.style.height = 'auto'
      }
      
      // 添加加载中占位符
      if (!img.classList.contains('lazy-loaded')) {
        img.classList.add('lazy-loading')
        
        // 添加占位符背景
        if (!img.style.backgroundColor) {
          img.style.backgroundColor = '#f3f4f6'
        }
      }
    })

    // 检查浏览器是否支持IntersectionObserver
    if ('IntersectionObserver' in window) {
      // 创建IntersectionObserver实例，用于预加载
      const preloadObserver = new IntersectionObserver(
        (entries) => {
          entries.forEach((entry) => {
            if (entry.isIntersecting) {
              const img = entry.target
              // 检查是否有data-src属性
              if (img.dataset.src) {
                // 预加载图片
                const preloadImg = new Image()
                preloadImg.src = img.dataset.src
                preloadImg.onload = () => {
                  // 图片加载完成后，应用到实际图片
                  img.src = img.dataset.src
                  img.removeAttribute('data-src')
                  img.style.opacity = '1'
                  img.classList.remove('lazy-loading')
                  img.classList.add('lazy-loaded')
                }
                preloadImg.onerror = () => {
                  // 图片加载失败处理
                  img.style.opacity = '1'
                  img.classList.remove('lazy-loading')
                  img.classList.add('lazy-loaded')
                }
              }
              // 停止观察
              preloadObserver.unobserve(img)
            }
          })
        },
        {
          threshold: 0.01,
          rootMargin: '200px 0px' // 提前200px开始预加载
        }
      )

      // 观察所有图片
      images.forEach((img) => {
        // 如果图片已经有src属性，移到data-src
        if (img.src && !img.dataset.src) {
          img.dataset.src = img.src
          img.src = ''
        }
        preloadObserver.observe(img)
      })
    } else {
      // 浏览器不支持IntersectionObserver，直接加载所有图片
      images.forEach((img) => {
        if (img.dataset.src) {
          const preloadImg = new Image()
          preloadImg.src = img.dataset.src
          preloadImg.onload = () => {
            img.src = img.dataset.src
            img.removeAttribute('data-src')
            img.style.opacity = '1'
            img.classList.remove('lazy-loading')
            img.classList.add('lazy-loaded')
          }
          preloadImg.onerror = () => {
            img.style.opacity = '1'
            img.classList.remove('lazy-loading')
            img.classList.add('lazy-loaded')
          }
        }
      })
    }
  }

  // 处理动态内容的图片懒加载
  const handleDynamicContentLazyLoad = (contentRef) => {
    nextTick(() => {
      if (contentRef.value) {
        initLazyLoad(contentRef.value)
      }
    })
  }

  return {
    initLazyLoad,
    handleDynamicContentLazyLoad
  }
}
