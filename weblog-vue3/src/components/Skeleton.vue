<template>
  <div class="skeleton-wrapper">
    <!-- 卡片类型骨架屏 -->
    <div v-if="type === 'card'" class="skeleton-card" v-for="i in count" :key="'card-' + i">
      <div class="skeleton-image"></div>
      <div class="skeleton-content">
        <div class="skeleton-title"></div>
        <div class="skeleton-text"></div>
        <div class="skeleton-text"></div>
      </div>
    </div>

    <!-- 列表类型骨架屏 -->
    <div v-else-if="type === 'list'" class="skeleton-list" v-for="i in count" :key="'list-' + i">
      <div class="skeleton-list-item">
        <div class="skeleton-list-avatar"></div>
        <div class="skeleton-list-content">
          <div class="skeleton-list-title"></div>
          <div class="skeleton-list-text"></div>
        </div>
      </div>
    </div>

    <!-- 文章类型骨架屏 -->
    <div v-else-if="type === 'article'" class="skeleton-article">
      <div class="skeleton-article-title"></div>
      <div class="skeleton-article-meta"></div>
      <div class="skeleton-article-content">
        <div class="skeleton-article-paragraph" v-for="i in 5" :key="i">
          <div class="skeleton-article-line" v-for="j in 4" :key="j"></div>
        </div>
      </div>
    </div>

    <!-- 表格类型骨架屏 -->
    <div v-else-if="type === 'table'" class="skeleton-table">
      <div class="skeleton-table-header">
        <div class="skeleton-table-cell" v-for="i in 4" :key="'table-header-' + i"></div>
      </div>
      <div class="skeleton-table-row" v-for="i in count" :key="'table-row-' + i">
        <div class="skeleton-table-cell" v-for="j in 4" :key="'table-cell-' + i + '-' + j"></div>
      </div>
    </div>

    <!-- 自定义类型骨架屏 -->
    <div v-else-if="type === 'custom'" class="skeleton-custom">
      <slot></slot>
    </div>

    <!-- 默认骨架屏 -->
    <div v-else class="skeleton-default" v-for="i in count" :key="'default-' + i">
      <div class="skeleton-block"></div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps({
  // 骨架屏类型：card, list, article, table, custom, default
  type: {
    type: String,
    default: 'default'
  },
  // 骨架屏数量
  count: {
    type: Number,
    default: 3
  },
  // 是否显示动画
  animated: {
    type: Boolean,
    default: true
  }
})
</script>

<style scoped>
.skeleton-wrapper {
  width: 100%;
}

/* 基础动画效果 */
@keyframes skeleton-loading {
  0% {
    background-position: -200% 0;
  }
  100% {
    background-position: 200% 0;
  }
}

/* 骨架屏基础样式 */
.skeleton-image,
.skeleton-title,
.skeleton-text,
.skeleton-list-avatar,
.skeleton-list-title,
.skeleton-list-text,
.skeleton-article-title,
.skeleton-article-meta,
.skeleton-article-line,
.skeleton-table-cell,
.skeleton-block {
  background: linear-gradient(
    90deg,
    rgba(226, 226, 226, 0.6) 0%,
    rgba(226, 226, 226, 0.2) 50%,
    rgba(226, 226, 226, 0.6) 100%
  );
  background-size: 200% 100%;
  animation: skeleton-loading 1.5s infinite ease-in-out;
  border-radius: 4px;
}

/* 卡片类型骨架屏 */
.skeleton-card {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.skeleton-image {
  width: 100%;
  height: 200px;
}

.skeleton-content {
  padding: 16px;
}

.skeleton-title {
  width: 60%;
  height: 24px;
  margin-bottom: 12px;
}

.skeleton-text {
  width: 80%;
  height: 16px;
  margin-bottom: 8px;
}

.skeleton-text:last-child {
  width: 60%;
}

/* 列表类型骨架屏 */
.skeleton-list {
  margin-bottom: 16px;
}

.skeleton-list-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.skeleton-list-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  margin-right: 16px;
}

.skeleton-list-content {
  flex: 1;
}

.skeleton-list-title {
  width: 70%;
  height: 20px;
  margin-bottom: 8px;
}

.skeleton-list-text {
  width: 50%;
  height: 14px;
}

/* 文章类型骨架屏 */
.skeleton-article {
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.skeleton-article-title {
  width: 80%;
  height: 32px;
  margin-bottom: 16px;
}

.skeleton-article-meta {
  width: 40%;
  height: 16px;
  margin-bottom: 24px;
}

.skeleton-article-paragraph {
  margin-bottom: 16px;
}

.skeleton-article-line {
  width: 100%;
  height: 16px;
  margin-bottom: 8px;
}

.skeleton-article-line:nth-child(2) {
  width: 90%;
}

.skeleton-article-line:nth-child(3) {
  width: 80%;
}

.skeleton-article-line:nth-child(4) {
  width: 95%;
}

/* 表格类型骨架屏 */
.skeleton-table {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.skeleton-table-header {
  display: flex;
  border-bottom: 1px solid #f0f0f0;
}

.skeleton-table-row {
  display: flex;
  border-bottom: 1px solid #f0f0f0;
}

.skeleton-table-row:last-child {
  border-bottom: none;
}

.skeleton-table-cell {
  flex: 1;
  height: 48px;
  margin: 0 12px;
  align-self: center;
}

/* 默认类型骨架屏 */
.skeleton-default {
  margin-bottom: 16px;
}

.skeleton-block {
  width: 100%;
  height: 200px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .skeleton-card {
    margin-bottom: 12px;
  }

  .skeleton-image {
    height: 150px;
  }

  .skeleton-content {
    padding: 12px;
  }

  .skeleton-article {
    padding: 16px;
  }

  .skeleton-list-item {
    padding: 8px 0;
  }

  .skeleton-list-avatar {
    width: 40px;
    height: 40px;
    margin-right: 12px;
  }
}
</style>