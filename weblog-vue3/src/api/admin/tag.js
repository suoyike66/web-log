import axios from "@/axios";

// 获取标签分页数据
export function getTagPageList(data) {
  return axios.post("/admin/tag/list", data)
}

// 添加标签
export function addTag(data) {
  return axios.post("/admin/tag/add", data)
}

// 删除标签
export function deleteTag(id) {
  return axios.post("/admin/tag/delete", { id })
}

// 根据标签名模糊查询
export function searchTags(key) {
  return axios.post("/admin/tag/search", { key })
}

// 获取标签 select 列表数据（带缓存）
export function getTagSelectList() {
  return axios.post("/admin/tag/select/list", {}, { cache: true, expireTime: 10 * 60 * 1000 }) // 缓存10分钟
}

// 清除标签相关缓存
export function clearTagCache() {
  const { clearCache } = require('@/composables/useApiCache');
  clearCache();
}
