import nprogress from 'nprogress';
import { ElMessage, ElMessageBox } from 'element-plus';
import 'element-plus/es/components/message-box/style/css';
import 'element-plus/es/components/message/style/css';
// 消息提示
export function showMessage(
  message = '提示内容',
  type = 'success',
  customClass = ''
) {
  return ElMessage({
    type: type,
    message,
    customClass,
  });
}

// 显示页面加载 Loading
export function showPageLoading() {
  nprogress.start();
}

// 隐藏页面加载 Loading
export function hidePageLoading() {
  nprogress.done();
}

// 弹出确认框
export function showModel(content = '提示内容', type = 'warning', title = '') {
  return ElMessageBox.confirm(content, title, {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type,
  });
}
