import axios from "@/axios";

// 获取文章分页数据
export function getWikiPageList(data) {
  return axios.post("/admin/wiki/list", data)
}
