import axios from "@/axios";
import { clearCache } from "@/composables/useApiCache";

// 获取分类列表
export function getCategoryList(data) {
  return axios.post("/category/list", data, { cache: true, expireTime: 10 * 60 * 1000 }) // 缓存10分钟
}

// 获取分类-文章列表
export function getCategoryArticlePageList(data) {
  return axios.post("/category/article/list", data)
}

// 清除分类相关缓存
export function clearCategoryCache() {
  clearCache();
  return Promise.resolve();
}


