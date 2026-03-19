import axios from '@/axios';
import { clearCache } from '@/composables/useApiCache';

// 获取分类分页数据
export function getCategoryPageList(data) {
  return axios.post('/admin/category/list', data);
}

// 添加分类
export function addCategory(data) {
  return axios.post('/admin/category/add', data);
}

// 删除分类
export function deleteCategory(id) {
  return axios.post('/admin/category/delete', { id });
}

// 获取分类 select 数据（带缓存）
export function getCategorySelectList() {
  return axios.post(
    '/admin/category/select/list',
    {},
    { cache: true, expireTime: 10 * 60 * 1000 }
  ); // 缓存10分钟
}

// 清除分类相关缓存
export function clearCategoryCache() {
  clearCache();
  return Promise.resolve();
}
