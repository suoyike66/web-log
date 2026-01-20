import axios from "@/axios";

// 获取文章列表
export function getArticlePageList(data) {
  return axios.post("/article/list", data)
}

// 获取文章详情
export function getArticleDetail(articleId) {
  console.log('调用 getArticleDetail，articleId:', articleId, '类型:', typeof articleId)
  return axios.post("/article/detail", { id: Number(articleId) })
}


