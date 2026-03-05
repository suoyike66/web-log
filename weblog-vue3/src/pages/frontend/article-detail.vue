<template>
    <Header></Header>

    <!-- 主内容区域 -->
    <main class="container max-w-screen-xl mx-auto p-4">
        <!-- grid 表格布局，分为 4 列 -->
        <div class="grid grid-cols-4 gap-7">
            <!-- 左边栏，占用 3 列 -->
            <div class="col-span-4 md:col-span-3 mb-3">
                <!-- 文章卡片父容器 -->
                <div
                    class="w-full p-5 mb-3 bg-white border border-gray-200 rounded-lg dark:bg-gray-800 dark:border-gray-700">
                    <!-- 面包屑 -->
                    <nav class="flex text-gray-500" aria-label="Breadcrumb">
                        <ol class="inline-flex items-center space-x-1 md:space-x-3">
                            <li class="inline-flex items-center">
                                <a href="/"
                                    class="inline-flex items-center text-sm font-medium hover:text-blue-600 dark:text-gray-400 dark:hover:text-white">
                                    <svg class="w-3 h-3 mr-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                                        fill="currentColor" viewBox="0 0 20 20">
                                        <path
                                            d="m19.707 9.293-2-2-7-7a1 1 0 0 0-1.414 0l-7 7-2 2a1 1 0 0 0 1.414 1.414L2 10.414V18a2 2 0 0 0 2 2h3a1 1 0 0 0 1-1v-4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v4a1 1 0 0 0 1 1h3a2 2 0 0 0 2-2v-7.586l.293.293a1 1 0 0 0 1.414-1.414Z" />
                                    </svg>
                                    首页
                                </a>
                            </li>
                            <li>
                                <div class="flex items-center">
                                    /
                                    <a href="#"
                                        class="ml-1 text-sm font-medium md:ml-3 dark:text-gray-400 dark:hover:text-white">正文</a>
                                </div>
                            </li>
                        </ol>
                    </nav>

                    <!-- 加载状态 -->
                    <div v-if="loading" class="py-5">
                        <Skeleton type="article" />
                    </div>
                    
                    <!-- 文章 -->
                    <article v-else>
                        <!-- 文章标题 -->
                        <h1 class="mt-4 font-bold text-3xl">{{ article.title }}</h1>
                        <!-- 文章 meta 信息，如发布时间等 -->
                        <div class="text-gray-400 flex items-center mt-5 text-sm">
                            <!-- 发布时间 -->
                            <svg class="inline w-3 h-3 mr-2 dark:text-white" aria-hidden="true"
                                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M5 1v3m5-3v3m5-3v3M1 7h18M5 11h10M2 3h16a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1Z" />
                            </svg>
                            <span class="mr-1 hidden md:inline">发表于</span> {{ article.createTime }}

                            <!-- 分类 -->
                            <svg class="inline w-3 h-3 ml-5 mr-2 dark:text-white" aria-hidden="true"
                                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 18 18">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M1 5v11a1 1 0 0 0 1 1h14a1 1 0 0 0 1-1V6a1 1 0 0 0-1-1H1Zm0 0V2a1 1 0 0 1 1-1h5.443a1 1 0 0 1 .8.4l2.7 3.6H1Z" />
                            </svg>
                            <span class="hidden md:inline">分类于</span>
                            <a @click="goCategoryArticleListPage(article.categoryId, article.categoryName)"
                                class="cursor-pointer mr-1 hover:underline">{{ article.categoryName }}</a>

                            <!-- 阅读量 -->
                            <svg class="inline w-3 h-3 ml-5 mr-2 dark:text-white" aria-hidden="true"
                                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 14">
                                <g stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
                                    <path d="M10 10a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z" />
                                    <path d="M10 13c4.97 0 9-2.686 9-6s-4.03-6-9-6-9 2.686-9 6 4.03 6 9 6Z" />
                                </g>
                            </svg>
                            <span class="mr-1 hidden md:inline">阅读量</span> {{ article.readNum }}
                        </div>

                        <!-- 正文 -->
                        <div ref="articleContentRef" class="mt-5 article-content" v-viewer v-html="article.content"></div>

                        <!-- 标签集合 -->
                        <div v-if="article.tags && article.tags.length > 0" class="mt-5">
                            <span @click="goTagArticleListPage(tag.id, tag.name)" v-for="(tag, index) in article.tags"
                                :key="index"
                                class="inline-block mb-1 cursor-pointer bg-green-100 text-green-800 text-xs font-medium mr-2 px-3 py-1 rounded-full hover:bg-green-200 hover:text-green-900 dark:bg-green-900 dark:text-green-300">
                                # {{ tag.name }}
                            </span>
                        </div>

                        <!-- 上下篇 -->
                        <nav class="flex flex-row mt-7">
                            <!-- basis-1/2 用于占用 flex 布局的一半空间 -->
                            <div class="basis-1/2">
                                <!-- h-full 指定高度占满 -->
                                <a v-if="article.preArticle"
                                    @click="router.push('/article/' + article.preArticle.articleId)"
                                    class="cursor-pointer flex flex-col h-full p-4 mr-3 text-base font-medium text-gray-500 bg-white border border-gray-300 rounded-lg hover:border-blue-500 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                                    <div>
                                        <svg class="inline w-3.5 h-3.5 mr-2 mb-1" aria-hidden="true"
                                            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                                stroke-width="2" d="M13 5H1m0 0 4 4M1 5l4-4"></path>
                                        </svg>
                                        上一篇
                                    </div>
                                    <div>{{ article.preArticle.articleTitle }}</div>
                                </a>
                            </div>

                            <div class="basis-1/2">
                                <!-- text-right 指定文字居右显示 -->
                                <a v-if="article.nextArticle"
                                    @click="router.push('/article/' + article.nextArticle.articleId)"
                                    class="cursor-pointer flex flex-col h-full text-right p-4 text-base font-medium text-gray-500 bg-white border border-gray-300 rounded-lg hover:border-blue-500 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                                    <div>
                                        下一篇
                                        <svg class="inline w-3.5 h-3.5 ml-2 mb-1" aria-hidden="true"
                                            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                                stroke-width="2" d="M1 5h12m0 0L9 1m4 4L9 9"></path>
                                        </svg>
                                    </div>
                                    <div>{{ article.nextArticle.articleTitle }}</div>
                                </a>
                            </div>
                        </nav>
                    </article>


                </div>
            </div>

            <!-- 右边侧边栏，占用一列 -->
            <aside class="col-span-4 md:col-span-1">
                <!-- 博主信息 -->
                <UserInfoCard></UserInfoCard>

                <!-- 分类 -->
                <CategoryListCard></CategoryListCard>

                <!-- 标签 -->
                <TagListCard></TagListCard>

                <!-- 文章目录 -->
                <Toc></Toc>
            </aside>
        </div>

    </main>

    <Footer></Footer>
    <ScrollToTopButton />
</template>

<script setup>
import Header from '@/layouts/frontend/components/Header.vue'
import Footer from '@/layouts/frontend/components/Footer.vue'
import UserInfoCard from '@/layouts/frontend/components/UserInfoCard.vue'
import TagListCard from '@/layouts/frontend/components/TagListCard.vue'
import CategoryListCard from '@/layouts/frontend/components/CategoryListCard.vue'
import ScrollToTopButton from '@/layouts/frontend/components/ScrollToTopButton.vue'
import Skeleton from '@/components/Skeleton.vue'
import { getArticleDetail } from '@/api/frontend/article'
import { useRoute, useRouter } from 'vue-router'
import { ref, watch, nextTick, onMounted } from 'vue'
import Viewer from 'viewerjs'
// 使用全局注册的 highlight.js，避免重复引入
import { useLazyLoad } from '@/composables/useLazyLoad'
import Toc from '@/layouts/frontend/components/Toc.vue'

const route = useRoute()
const router = useRouter()
// 路由传递过来的文章 ID
console.log('路由参数:', route.params)
console.log('文章 ID:', route.params.articleId)

// 使用图片懒加载composable
const { handleDynamicContentLazyLoad } = useLazyLoad()

// 文章数据
const article = ref({})
// 加载状态
const loading = ref(true)
// 文章内容容器引用
const articleContentRef = ref(null)
// 图片查看器实例
const viewerInstance = ref(null)

// 初始化代码语法高亮
function highlightCode() {
    nextTick(() => {
        if (articleContentRef.value) {
            const codeBlocks = articleContentRef.value.querySelectorAll('pre code')
            codeBlocks.forEach((block) => {
                // 应用语法高亮
                window.hljs.highlightElement(block)
                
                // 添加复制按钮
                addCopyButton(block)
                
                // 添加行号
                addLineNumbers(block)
            })
        }
    })
}

// 添加复制按钮
function addCopyButton(block) {
    const pre = block.parentElement
    if (!pre.querySelector('.copy-button')) {
        const button = document.createElement('button')
        button.className = 'copy-button'
        button.textContent = '复制'
        button.style.position = 'absolute'
        button.style.top = '8px'
        button.style.right = '8px'
        button.style.padding = '4px 8px'
        button.style.fontSize = '12px'
        button.style.backgroundColor = '#f0f0f0'
        button.style.border = '1px solid #ddd'
        button.style.borderRadius = '4px'
        button.style.cursor = 'pointer'
        button.style.zIndex = '10'
        
        button.addEventListener('click', () => {
            const code = block.textContent
            navigator.clipboard.writeText(code).then(() => {
                button.textContent = '已复制'
                setTimeout(() => {
                    button.textContent = '复制'
                }, 2000)
            }).catch(err => {
                console.error('复制失败:', err)
                button.textContent = '复制失败'
                setTimeout(() => {
                    button.textContent = '复制'
                }, 2000)
            })
        })
        
        pre.style.position = 'relative'
        pre.appendChild(button)
    }
}

// 添加行号
function addLineNumbers(block) {
    const pre = block.parentElement
    if (!pre.querySelector('.line-numbers')) {
        const code = block.textContent
        const lines = code.split('\n')
        const lineCount = lines.length
        
        // 创建行号容器
        const lineNumbers = document.createElement('div')
        lineNumbers.className = 'line-numbers'
        lineNumbers.style.position = 'absolute'
        lineNumbers.style.left = '0'
        lineNumbers.style.top = '0'
        lineNumbers.style.width = '40px'
        lineNumbers.style.height = '100%'
        lineNumbers.style.backgroundColor = '#f5f5f5'
        lineNumbers.style.borderRight = '1px solid #ddd'
        lineNumbers.style.padding = '16px 0'
        lineNumbers.style.fontSize = '14px'
        lineNumbers.style.lineHeight = '1.5'
        lineNumbers.style.textAlign = 'center'
        lineNumbers.style.color = '#999'
        lineNumbers.style.userSelect = 'none'
        
        // 添加行号
        for (let i = 1; i <= lineCount; i++) {
            const lineNumber = document.createElement('div')
            lineNumber.textContent = i
            lineNumber.style.height = '21px'
            lineNumbers.appendChild(lineNumber)
        }
        
        // 调整代码块位置
        block.style.paddingLeft = '50px'
        pre.style.position = 'relative'
        pre.appendChild(lineNumbers)
    }
}

// 初始化图片放大功能
function initImageViewer() {
    nextTick(() => {
        if (articleContentRef.value) {
            // 销毁之前的实例
            if (viewerInstance.value) {
                viewerInstance.value.destroy()
            }
            // 创建新的查看器实例
            viewerInstance.value = new Viewer(articleContentRef.value, {
                inline: false,
                button: true,
                navbar: true,
                title: true,
                toolbar: true,
                tooltip: true,
                movable: true,
                zoomable: true,
                rotatable: true,
                scalable: true,
                transition: true,
                fullscreen: true,
                keyboard: true,
                url: 'src',
                error: function() {
                    // 图片加载失败处理
                    console.log('图片加载失败')
                }
            })
        }
    })
}

// 获取文章详情
function refreshArticleDetail(articleId) {
    console.log('获取文章详情，articleId:', articleId)
    // 设置加载状态为 true
    loading.value = true
    
    if (!articleId || articleId === 'null' || articleId === 'undefined') {
        console.error('文章 ID 为空:', articleId)
        article.value = {
            title: '文章不存在',
            content: '<p>抱歉，您访问的文章不存在或已被删除。</p>',
            createTime: new Date().toLocaleString()
        }
        // 设置加载状态为 false
        loading.value = false
        // 初始化代码语法高亮
        highlightCode()
        return
    }
    getArticleDetail(articleId).then((res) => {
        console.log('API 返回结果:', res)
        if (res.success) {
            article.value = res.data
            console.log('更新后的 article:', article.value)
        } else {
            console.error('API 调用失败:', res.message)
            article.value = {
                title: '文章不存在',
                content: `<p>抱歉，${res.message}</p>`,
                createTime: new Date().toLocaleString()
            }
        }
        // 设置加载状态为 false
        loading.value = false
        // 初始化图片放大功能
        initImageViewer()
        // 初始化代码语法高亮
        highlightCode()
        // 初始化图片懒加载
        handleDynamicContentLazyLoad(articleContentRef)
    }).catch((error) => {
        console.error('请求失败:', error)
        article.value = {
            title: '网络错误',
            content: '<p>抱歉，网络请求失败，请稍后再试。</p>',
            createTime: new Date().toLocaleString()
        }
        // 设置加载状态为 false
        loading.value = false
        // 初始化图片放大功能
        initImageViewer()
        // 初始化代码语法高亮
        highlightCode()
    })
}
refreshArticleDetail(route.params.articleId)


// 跳转分类文章列表页
const goCategoryArticleListPage = (id, name) => {
    // 跳转时通过 query 携带参数（分类 ID、分类名称）
    router.push({ path: '/category/article/list', query: { id, name } })
}

// 跳转标签文章列表页
const goTagArticleListPage = (id, name) => {
    // 跳转时通过 query 携带参数（标签 ID、标签名称）
    router.push({ path: '/tag/article/list', query: { id, name } })
}

// 监听路由
watch(route, (newRoute, oldRoute) => {
    // 重新渲染文章详情
    refreshArticleDetail(newRoute.params.articleId)
})
</script>

<style>
/* h1, h2, h3, h4, h5, h6 标题样式 */
.article-content h1,
.article-content h2,
.article-content h3,
.article-content h4,
.article-content h5,
.article-content h6 {
    color: #292525;
    line-height: 150%;
    font-family: PingFang SC, Helvetica Neue, Helvetica, Hiragino Sans GB, Microsoft YaHei, "\5FAE\8F6F\96C5\9ED1", Arial, sans-serif;
}

.article-content h2 {
    line-height: 1.5;
    font-weight: 700;
    font-synthesis: style;
    font-size: 24px;
    margin-top: 40px;
    margin-bottom: 26px;
    line-height: 140%;
    border-bottom: 1px solid rgb(241 245 249);
    padding-bottom: 15px;
}

.article-content h3 {
    font-size: 20px;
    margin-top: 40px;
    margin-bottom: 16px;
    font-weight: 600;
}

.article-content h4 {
    font-size: 18px;
    margin-top: 30px;
    margin-bottom: 16px;
    font-weight: 600;
}

.article-content h5,
h6 {
    font-size: 16px;
    margin-top: 30px;
    margin-bottom: 14px;
    font-weight: 600;
}

/* p 段落样式 */
.article-content p {
    letter-spacing: .3px;
    margin: 0 0 20px;
    line-height: 30px;
    color: #4c4e4d;
    font-weight: 400;
    word-break: normal;
    word-wrap: break-word;
    font-family: -apple-system, BlinkMacSystemFont, PingFang SC, Hiragino Sans GB, Microsoft Yahei, Arial, sans-serif;
}

/* blockquote 引用样式 */
.article-content blockquote {
    border-left: 2.3px solid rgb(52, 152, 219);
    quotes: none;
    background: rgb(236, 240, 241);
    color: #777;
    font-size: 16px;
    margin: 2em 0;
    padding: 24px;
}

/* 设置 blockquote 中最后一个 p 标签的 margin-bottom 为 0 */
.article-content blockquote p:last-child {
    margin-bottom: 0;
}

/* 斜体样式 */
.article-content em {
    color: #c849ff;
}

/* 超链接样式 */
.article-content a {
    color: #167bc2;
}

.article-content a:hover {
    text-decoration: underline;
}

/* ul 样式 */
.article-content ul {
    padding-left: 2rem;
}

.article-content ul li {
    list-style-type: disc;
    padding-top: 5px;
    padding-bottom: 5px;
    font-size: 16px;
}

/* ol 样式 */
.article-content ol {
    list-style-type: decimal;
    padding-left: 2rem;
}

/* 图片样式 */
.article-content img {
    max-width: 100%;
    overflow: hidden;
    display: block;
    margin: 0 auto;
    border-radius: 8px;
    transition: all 0.3s ease;
}

.article-content img:hover,
.article-content img:focus {
    box-shadow: 2px 2px 10px 0 rgba(0, 0, 0, .15);
    transform: scale(1.02);
}

/* 懒加载图片样式 */
.article-content img.lazy-loading {
    background: linear-gradient(
        90deg,
        rgba(226, 226, 226, 0.6) 0%,
        rgba(226, 226, 226, 0.2) 50%,
        rgba(226, 226, 226, 0.6) 100%
    );
    background-size: 200% 100%;
    animation: skeleton-loading 1.5s infinite ease-in-out;
}

.article-content img.lazy-loaded {
    animation: fade-in 0.3s ease-in-out;
}

/* 加载动画 */
@keyframes skeleton-loading {
    0% {
        background-position: -200% 0;
    }
    100% {
        background-position: 200% 0;
    }
}

/* 淡入动画 */
@keyframes fade-in {
    from {
        opacity: 0;
        transform: scale(0.98);
    }
    to {
        opacity: 1;
        transform: scale(1);
    }
}

/* 图片描述文字 */
.image-caption {
    min-width: 20%;
    max-width: 80%;
    min-height: 43px;
    display: block;
    padding: 10px;
    margin: 0 auto;
    font-size: 13px;
    color: #999;
    text-align: center;
}

/* code 样式 */
.article-content code {
    padding: 2px 4px;
    margin: 0 2px;
    font-size: 95% !important;
    border-radius: 4px;
    color: rgb(41, 128, 185);
    background-color: rgba(27, 31, 35, 0.05);
    font-family: Operator Mono, Consolas, Monaco, Menlo, monospace;
}</style>
