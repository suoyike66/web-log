import { ref, reactive } from 'vue'
import { showMessage } from './util'

/**
 * 表单通用逻辑
 * @param {Object} initialForm - 初始表单数据
 * @param {Object} rules - 表单验证规则
 * @param {Function} submitFn - 提交函数
 */
export function useForm(initialForm = {}, rules = {}, submitFn) {
  // 表单引用
  const formRef = ref(null)
  // 表单数据
  const form = reactive({ ...initialForm })
  // 提交按钮加载状态
  const submitLoading = ref(false)
  // 表单验证规则
  const formRules = ref(rules)
  
  /**
   * 重置表单
   */
  const resetForm = () => {
    if (formRef.value) {
      formRef.value.resetFields()
    }
    // 重置表单数据
    Object.keys(initialForm).forEach(key => {
      form[key] = initialForm[key]
    })
  }
  
  /**
   * 提交表单
   * @param {Function} customSubmitFn - 自定义提交函数
   */
  const submitForm = async (customSubmitFn = submitFn) => {
    if (!formRef.value) {
      showMessage('表单引用未定义', 'error')
      return false
    }
    
    return new Promise((resolve, reject) => {
      formRef.value.validate(async (valid) => {
        if (!valid) {
          reject(new Error('表单验证失败'))
          return false
        }
        
        submitLoading.value = true
        
        try {
          const result = await customSubmitFn(form)
          submitLoading.value = false
          resolve(result)
        } catch (error) {
          submitLoading.value = false
          showMessage('提交失败', 'error')
          reject(error)
        }
      })
    })
  }
  
  /**
   * 验证表单
   */
  const validateForm = () => {
    if (!formRef.value) {
      return Promise.resolve(false)
    }
    
    return new Promise((resolve) => {
      formRef.value.validate((valid) => {
        resolve(valid)
      })
    })
  }
  
  /**
   * 更新表单数据
   * @param {Object} data - 要更新的表单数据
   */
  const updateFormData = (data) => {
    Object.assign(form, data)
  }
  
  return {
    formRef,
    form,
    submitLoading,
    formRules,
    resetForm,
    submitForm,
    validateForm,
    updateFormData
  }
}
