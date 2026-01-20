import axios from "@/axios";

// 获取分类列表
export function getCategoryList(data) {
  return axios.post("/category/list", data)
}

// 获取分类-文章列表
export function getCategoryArticlePageList(data) {
  return axios.post("/category/article/list", data)
}

// 获取标签列表
export function getTagList(data) {
  return axios.post("/tag/list", data)
}

// 获取标签-文章列表
export function getTagArticlePageList(data) {
  return axios.post("/tag/article/list", data)
}


