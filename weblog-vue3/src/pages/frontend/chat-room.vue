<template>
    <Header></Header>

    <!-- 主内容区域 -->
    <main class="container max-w-screen-xl mx-auto px-4 md:px-6 py-4">
        <div class="flex h-[calc(100vh-180px)]">
            <!-- 左侧：聊天区域 -->
            <div class="flex-1 flex flex-col">
                <!-- 历史聊天记录区域 -->
                <div class="flex-1 bg-blue-100">
                    历史聊天记录区域
                </div>
                <!-- 输入区域 -->
                <div class="h-32 bg-green-100">
                    输入框区域
                </div>
            </div>

            <!-- 右侧：在线列表 -->
            <div class="w-64 bg-yellow-100">
                在线列表区域
            </div>
        </div>
    </main>

    <Footer></Footer>
</template>

<script setup>
import Header from '@/layouts/frontend/components/Header.vue'
import Footer from '@/layouts/frontend/components/Footer.vue'
</script>

