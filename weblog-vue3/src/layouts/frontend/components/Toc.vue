<template>
    <!-- text-sm/[30px] 表示文字小号，行高为 30px -->
    <div v-if="titles && titles.length > 0"
        ref="tocContainer"
        class="text-sm/[30px] w-full p-5 mb-3 bg-white border border-gray-200 rounded-lg dark:bg-gray-800 dark:border-gray-700 transition-all duration-300 max-h-[70vh] overflow-y-auto opacity-0 animate-fade-in">
        <!-- 目录标题 -->
        <h2 class="flex items-center mb-2 font-bold text-gray-900 uppercase dark:text-white">
            <!-- 目录图标 -->
            <svg t="1699441758495" class="icon w-3.5 h-3.5 mr-2" viewBox="0 0 1024 1024" version="1.1"
                xmlns="http://www.w3.org/2000/svg" p-id="4043" width="200" height="200">
                <path
                    d="M857.6 25.6a76.8 76.8 0 0 1 76.8 76.8v819.2a76.8 76.8 0 0 1-76.8 76.8H166.4a76.8 76.8 0 0 1-76.8-76.8V102.4a76.8 76.8 0 0 1 76.8-76.8h691.2z m-102.4 678.4H473.6l-2.2528 0.064a38.4 38.4 0 0 0 0 76.672L473.6 780.8h281.6l2.2528-0.064a38.4 38.4 0 0 0 0-76.672L755.2 704z m0-230.4H473.6l-2.2528 0.064a38.4 38.4 0 0 0 0 76.672L473.6 550.4h281.6l2.2528-0.064a38.4 38.4 0 0 0 0-76.672L755.2 473.6z m0-230.4H473.6l-2.2528 0.064a38.4 38.4 0 0 0 0 76.672L473.6 320h281.6l2.2528-0.064a38.4 38.4 0 0 0 0-76.672L755.2 243.2z"
                    fill="#6B57FE" p-id="4044"></path>
                <path
                    d="M281.6 691.2a51.2 51.2 0 1 1 0 102.4 51.2 51.2 0 0 1 0-102.4z m0-230.4a51.2 51.2 0 1 1 0 102.4 51.2 51.2 0 0 1 0-102.4z m0-230.4a51.2 51.2 0 1 1 0 102.4 51.2 51.2 0 0 1 0-102.4z"
                    fill="#FFBA00" p-id="4045"></path>
            </svg>
            文章目录
        </h2>
        <div class="border-l-2 border-gray-200">
            <ul>
                <!-- 二级标题 -->
                <li v-for="(h2, index) in titles" :key="index" class="pl-5 transition-all duration-300 cursor-pointer hover:text-sky-500 overflow-hidden" :class="[h2.index == activeHeadingIndex ? 'active text-sky-600 border-l-2 border-sky-600 font-bold' : 'text-gray-500 font-normal']">
                    <span @click="scrollToView(h2.offsetTop)" class="truncate hover:whitespace-normal hover:break-all transition-all duration-300">{{ h2.text }}</span>
                    <!-- 三级标题 -->
                    <ul v-if="h2.children && h2.children.length > 0">
                        <li v-for="(h3, index2) in h2.children" :key="index2" class="pl-5 transition-all duration-300 cursor-pointer hover:text-sky-500 overflow-hidden" :class="[h3.index == activeHeadingIndex ? 'active text-sky-600 border-l-2 border-sky-600 font-bold' : 'text-gray-500 font-normal']">
                            <span @click="scrollToView(h3.offsetTop)" class="truncate hover:whitespace-normal hover:break-all transition-all duration-300">{{ h3.text }}</span>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

/**
 * 文章目录卡片组件
 * 功能：自动生成文章目录，支持滚动高亮、粘性定位、响应式设计等
 */

// 响应式数据
const titles = ref([]) // 存储目录标题数据
const tocContainer = ref(null) // 目录容器DOM引用
const activeHeadingIndex = ref(-1) // 当前激活的标题索引
const isFixed = ref(false) // 是否处于固定定位状态
const originalPosition = ref({
    top: 0,
    left: 0,
    width: 0
}) // 目录容器原始位置信息

// 用于存储需要清理的资源
const cleanupResources = ref({
    mutationObserver: null, // MutationObserver实例
    imageLoadHandlers: [] // 图片load事件处理函数数组
})

/**
 * 防抖函数
 * @param {Function} func - 需要防抖的函数
 * @param {number} delay - 延迟时间（毫秒）
 * @returns {Function} 防抖处理后的函数
 */
function debounce(func, delay) {
    let timer
    return function() {
        const context = this
        const args = arguments
        clearTimeout(timer)
        timer = setTimeout(() => {
            func.apply(context, args)
        }, delay)
    }
}
/**
 * 组件挂载时的初始化操作
 */
onMounted(() => {
    // 获取文章内容容器
    const container = document.querySelector('.article-content')

    // 只有当文章内容容器存在时才进行目录生成
    if (container) {
        // 初始化目录数据
        initTocData(container)
        
        // 使用 MutationObserver 监视文章内容的DOM变化
        const observer = new MutationObserver(mutationsList => {
            for (let mutation of mutationsList) {
                // 当子节点变化时重新生成目录
                if (mutation.type === 'childList' && container) {
                    titles.value = [] // 清空旧目录数据
                    
                    // 清空之前的图片load事件处理函数
                    cleanupResources.value.imageLoadHandlers = []
                    
                    // 监听所有图片加载完成事件，确保图片加载后重新计算标题位置
                    const images = container.querySelectorAll('img');
                    images.forEach(img => {
                        const loadHandler = () => {
                            if (container) {
                                initTocData(container)
                            }
                        }
                        img.addEventListener('load', loadHandler)
                        // 存储事件处理函数，用于后续清理
                        cleanupResources.value.imageLoadHandlers.push({ img, loadHandler })
                    })

                    // 添加滚动事件监听，用于标题高亮
                    window.addEventListener('scroll', debouncedHandleContentScroll);
                    
                    // 重新生成目录数据
                    initTocData(container)
                }
            }
        })

        // 配置MutationObserver，监视子节点和子树的变化
        const config = { childList: true, subtree: true }
        observer.observe(container, config)
        
        // 存储MutationObserver实例，用于后续清理
        cleanupResources.value.mutationObserver = observer
        
        // 如果初始提取没有找到标题，尝试再次提取（处理内容延迟加载的情况）
        if (titles.value.length === 0) {
            setTimeout(() => {
                initTocData(container)
            }, 500)
        }
    }
    
    // 存储目录容器的原始位置信息，用于粘性定位
    if (tocContainer.value) {
        const rect = tocContainer.value.getBoundingClientRect()
        originalPosition.value = {
            top: rect.top + window.scrollY,
            left: rect.left + window.scrollX,
            width: rect.width
        }
        
        // 添加滚动事件监听，用于粘性定位
        window.addEventListener('scroll', debouncedHandleStickyPosition)
        
        // 添加窗口大小变化监听，用于响应式布局调整
        window.addEventListener('resize', debouncedHandleWindowResize)
    }
})

/**
 * 处理页面滚动事件，更新当前激活的标题
 * 功能：根据页面滚动位置，判断当前阅读位置对应的标题，并更新高亮状态
 */
function handleContentScroll() {
    // 当前的滚动位置（考虑视窗高度的30%，使高亮更符合阅读体验）
    const scrollY = window.scrollY + window.innerHeight * 0.3
    
    // 构建所有标题的扁平数组，方便统一处理
    const allHeadings = []
    titles.value.forEach(title => {
        allHeadings.push(title)
        if (title.children && title.children.length > 0) {
            title.children.forEach(child => {
                allHeadings.push(child)
            })
        }
    })
    
    // 按标题在页面中的位置排序
    allHeadings.sort((a, b) => a.offsetTop - b.offsetTop)
    
    // 从下往上查找当前应该高亮的标题
    let currentHeading = null
    for (let i = allHeadings.length - 1; i >= 0; i--) {
        if (scrollY >= allHeadings[i].offsetTop) {
            currentHeading = allHeadings[i]
            break
        }
    }
    
    // 更新高亮状态
    activeHeadingIndex.value = currentHeading ? currentHeading.index : -1
}

/**
 * 处理窗口大小变化事件
 * 功能：在窗口大小变化时确保目录容器的响应式布局正确
 */
function handleWindowResize() {
    if (!tocContainer.value) return
    
    const screenWidth = window.innerWidth
    const container = tocContainer.value
    
    // 在小屏幕上，让目录卡片居中显示
    if (screenWidth < 768) {
        container.style.width = '100%'
    } else {
        container.style.width = ''
    }
}

// 创建防抖版本的事件处理函数，优化性能
const debouncedHandleContentScroll = debounce(handleContentScroll, 100) // 滚动高亮防抖
const debouncedHandleWindowResize = debounce(handleWindowResize, 100) // 窗口大小变化防抖
const debouncedHandleStickyPosition = debounce(handleStickyPosition, 50) // 粘性定位防抖

/**
 * 处理页面滚动事件，实现目录的粘性定位
 * 功能：当页面滚动到一定位置时，将目录卡片固定在视窗中
 */
function handleStickyPosition() {
    if (!tocContainer.value) return
    
    const scrollY = window.scrollY
    const container = tocContainer.value
    const screenWidth = window.innerWidth
    
    // 当滚动超过原始位置时，设置为固定定位
    if (scrollY >= originalPosition.value.top - 90) {
        if (!isFixed.value) {
            isFixed.value = true
            container.style.position = 'fixed'
            container.style.top = '90px' // 头部高度约为90px
            
            // 在小屏幕上，让目录卡片居中显示
            if (screenWidth < 768) {
                container.style.left = '50%'
                container.style.transform = 'translateX(-50%)'
                container.style.width = '90%'
            } else {
                container.style.left = `${originalPosition.value.left}px`
                container.style.transform = 'none'
                container.style.width = `${originalPosition.value.width}px`
            }
            
            container.style.zIndex = '5' // 设置比头部低的z-index，防止遮挡头部
        }
    } else {
        // 恢复原始定位
        if (isFixed.value) {
            isFixed.value = false
            container.style.position = ''
            container.style.top = ''
            container.style.left = ''
            container.style.transform = ''
            container.style.width = ''
            container.style.zIndex = ''
        }
    }
}

/**
 * 组件卸载前的清理操作
 * 功能：移除所有事件监听器，避免内存泄漏
 */
onBeforeUnmount(() => {
    // 移除窗口滚动和大小变化的事件监听器
    window.removeEventListener('scroll', debouncedHandleContentScroll)
    window.removeEventListener('scroll', debouncedHandleStickyPosition)
    window.removeEventListener('resize', debouncedHandleWindowResize)
    
    // 断开MutationObserver连接
    if (cleanupResources.value.mutationObserver) {
        cleanupResources.value.mutationObserver.disconnect()
        cleanupResources.value.mutationObserver = null
    }
    
    // 移除所有图片的load事件监听器
    cleanupResources.value.imageLoadHandlers.forEach(({ img, loadHandler }) => {
        img.removeEventListener('load', loadHandler)
    })
    cleanupResources.value.imageLoadHandlers = []
})

/**
 * 滚动到指定标题位置
 * @param {number} offsetTop - 标题在页面中的偏移量
 */
function scrollToView(offsetTop) {
    window.scrollTo({ top: offsetTop, behavior: "smooth" })
}

/**
 * 初始化目录数据
 * @param {HTMLElement} container - 文章内容容器
 */
function initTocData(container) {
    if (!container) return
    
    // 提取一级、二级标题
    const levels = ['h1', 'h2']
    const headings = container.querySelectorAll(levels)
    
    // 存放组装后的目录标题数据
    const titlesArr = []
    
    // 标题索引计数器
    let index = 1
    
    headings.forEach(heading => {
        // 获取标题等级（h1 -> 1，h2 -> 2）
        const headingLevel = parseInt(heading.tagName.substring(1))
        
        // 标题文字（处理空标题情况，自动生成标题）
        const headingText = heading.innerText.trim() || `无标题 ${index}`
        
        // 标题在页面中的位置（距离顶部的距离，减去95px的偏移量）
        const offsetTop = heading.offsetTop - 95
        
        if (headingLevel === 1) { // 一级标题
            titlesArr.push({
                index,
                level: headingLevel,
                text: headingText,
                offsetTop,
                children: [] // 一级标题下的子标题
            })
        } else if (headingLevel === 2) { // 二级标题
            // 如果没有一级标题，则创建一个默认的一级标题
            if (titlesArr.length === 0) {
                titlesArr.push({
                    index,
                    level: 1,
                    text: '默认标题',
                    offsetTop: 0,
                    children: []
                })
            }
            
            // 将二级标题添加到最后一个一级标题的子标题中
            const parentHeading = titlesArr[titlesArr.length - 1]
            parentHeading.children.push({
                index,
                level: headingLevel,
                text: headingText,
                offsetTop
            })
        }
        
        // 索引计数器自增
        index++
    })
    
    // 更新目录数据
    titles.value = titlesArr
}
</script>

<style scoped>
/* 淡入动画效果 */
@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.animate-fade-in {
    animation: fadeIn 0.5s ease-out forwards;
}

/* 自定义滚动条样式 */
::-webkit-scrollbar {
    width: 6px;
}

::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 3px;
}

::-webkit-scrollbar-thumb {
    background: #888;
    border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
    background: #555;
}

/* 深色模式下的滚动条样式 */
.dark ::-webkit-scrollbar-track {
    background: #374151;
}

.dark ::-webkit-scrollbar-thumb {
    background: #6b7280;
}

.dark ::-webkit-scrollbar-thumb:hover {
    background: #9ca3af;
}
</style>
