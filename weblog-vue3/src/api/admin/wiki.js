import axios from "@/axios";

// 获取文章分页数据
export function getWikiPageList(data) {
  return axios.post("/admin/wiki/list", data)
}

// 新增知识库
export function addWiki(data) {
  return axios.post("/admin/wiki/add", data)
}