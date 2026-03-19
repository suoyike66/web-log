import { ref, reactive } from 'vue';
import { showMessage } from './util';

/**
 * 表格通用逻辑
 * @param {Function} fetchData - 获取数据的API函数
 * @param {Object} initialParams - 初始查询参数
 */
export function useTable(fetchData, initialParams = {}) {
  // 表格加载状态
  const tableLoading = ref(false);
  // 表格数据
  const tableData = ref([]);
  // 当前页码
  const current = ref(1);
  // 总数据量
  const total = ref(0);
  // 每页显示的数据量
  const size = ref(10);
  // 查询参数
  const queryParams = reactive({ ...initialParams });

  /**
   * 获取表格数据
   */
  const getTableData = async () => {
    tableLoading.value = true;
    try {
      const response = await fetchData({
        current: current.value,
        size: size.value,
        ...queryParams,
      });

      if (response.success) {
        tableData.value = response.data;
        current.value = response.current;
        size.value = response.size;
        total.value = response.total;
      } else {
        showMessage(response.message || '获取数据失败', 'error');
      }
    } catch (error) {
      showMessage('获取数据失败', 'error');
    } finally {
      tableLoading.value = false;
    }
  };

  /**
   * 处理分页大小变更
   * @param {number} pageSize - 选择的每页显示数量
   */
  const handleSizeChange = (pageSize) => {
    size.value = pageSize;
    getTableData();
  };

  /**
   * 处理页码变更
   * @param {number} page - 选择的页码
   */
  const handleCurrentChange = (page) => {
    current.value = page;
    getTableData();
  };

  /**
   * 重置查询参数
   * @param {Object} defaultParams - 默认参数
   */
  const resetParams = (defaultParams = {}) => {
    Object.keys(queryParams).forEach((key) => {
      if (defaultParams[key] !== undefined) {
        queryParams[key] = defaultParams[key];
      } else {
        queryParams[key] = '';
      }
    });
    current.value = 1;
    getTableData();
  };

  /**
   * 更新查询参数
   * @param {Object} params - 要更新的参数
   */
  const updateParams = (params) => {
    Object.assign(queryParams, params);
    current.value = 1;
    getTableData();
  };

  // 初始加载数据
  getTableData();

  return {
    tableLoading,
    tableData,
    current,
    total,
    size,
    queryParams,
    getTableData,
    handleSizeChange,
    handleCurrentChange,
    resetParams,
    updateParams,
  };
}
