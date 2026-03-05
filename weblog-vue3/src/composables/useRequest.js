import { ref } from 'vue'
import { showMessage } from './util'

/**
 * API请求通用逻辑
 * @param {Function} requestFn - API请求函数
 */
export function useRequest(requestFn) {
  // 请求加载状态
  const loading = ref(false)
  // 请求错误
  const error = ref(null)
  
  /**
   * 执行请求
   * @param {any} params - 请求参数
   * @param {Object} options - 配置选项
   * @param {boolean} options.showSuccess - 是否显示成功消息
   * @param {string} options.successMessage - 成功消息
   * @param {boolean} options.showError - 是否显示错误消息
   */
  const execute = async (params, options = {}) => {
    const { showSuccess = false, successMessage = '操作成功', showError = true } = options
    
    loading.value = true
    error.value = null
    
    try {
      const result = await requestFn(params)
      loading.value = false
      
      if (result.success) {
        if (showSuccess) {
          showMessage(successMessage, 'success')
        }
        return result
      } else {
        if (showError) {
          showMessage(result.message || '操作失败', 'error')
        }
        return Promise.reject(new Error(result.message || '操作失败'))
      }
    } catch (err) {
      loading.value = false
      error.value = err
      
      if (showError) {
        showMessage(err.message || '请求失败', 'error')
      }
      return Promise.reject(err)
    }
  }
  
  /**
   * 执行删除操作
   * @param {any} params - 请求参数
   * @param {string} confirmMessage - 确认消息
   * @param {Function} confirmFn - 确认函数，默认为showModel
   */
  const executeDelete = async (params, confirmMessage = '确定要执行此操作吗？', confirmFn) => {
    if (!confirmFn) {
      const { showModel } = await import('./util')
      confirmFn = showModel
    }
    
    try {
      await confirmFn(confirmMessage)
      return await execute(params, { showSuccess: true, successMessage: '删除成功' })
    } catch (err) {
      return Promise.reject(err)
    }
  }
  
  return {
    loading,
    error,
    execute,
    executeDelete
  }
}
