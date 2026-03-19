<template>
  <div>
    <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
    <el-card shadow="never" class="mb-5">
      <!-- flex 布局，内容垂直居中 -->
      <div class="flex items-center">
        <el-text>标签名称</el-text>
        <div class="ml-3 w-52 mr-5">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入（模糊查询）"
          />
        </div>

        <el-text>创建日期</el-text>
        <div class="ml-3 w-30 mr-5">
          <!-- 日期选择组件（区间选择） -->
          <el-date-picker
            v-model="pickDate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            size="default"
            :shortcuts="shortcuts"
            @change="datepickerChange"
          />
        </div>

        <el-button
          type="primary"
          class="ml-3"
          :icon="Search"
          @click="getTableData"
          >查询</el-button
        >
        <el-button class="ml-3" :icon="RefreshRight" @click="reset"
          >重置</el-button
        >
      </div>
    </el-card>

    <el-card shadow="never">
      <!-- 新增按钮 -->
      <div class="mb-5">
        <el-button type="primary" @click="addCategoryBtnClick">
          <el-icon class="mr-1">
            <Plus />
          </el-icon>
          新增</el-button
        >
      </div>

      <!-- 分页列表 -->
      <div v-if="!tableLoading">
        <el-table
          :data="tableData"
          border
          stripe
          style="width: 100%"
          class="animate__animated animate__fadeIn"
        >
          <el-table-column prop="name" label="标签名称" width="180">
            <template #default="scope">
              <el-tag class="ml-2" type="success">{{ scope.row.name }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180" />
          <el-table-column label="操作">
            <template #default="scope">
              <el-button
                type="danger"
                size="small"
                @click="deleteTagSubmit(scope.row)"
                :loading="deleteLoading"
              >
                <el-icon class="mr-1">
                  <Delete />
                </el-icon>
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 表格骨架屏 -->
      <div v-else class="py-5">
        <Skeleton type="table" count="5" />
      </div>

      <!-- 分页 -->
      <div class="mt-10 flex justify-center">
        <el-pagination
          v-model:current-page="current"
          v-model:page-size="size"
          :page-sizes="[10, 20, 50]"
          :small="false"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 添加标签 -->
    <FormDialog
      ref="formDialogRef"
      title="添加文章标签"
      destroyOnClose
      @submit="onSubmit"
    >
      <el-form ref="formRef" :model="form">
        <el-form-item prop="name">
          <el-tag
            v-for="tag in dynamicTags"
            :key="tag"
            class="mx-1"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)"
          >
            {{ tag }}
          </el-tag>
          <span class="w-20">
            <el-input
              v-if="inputVisible"
              ref="InputRef"
              v-model="inputValue"
              class="ml-1 w-20"
              size="small"
              @keyup.enter="handleInputConfirm"
              @blur="handleInputConfirm"
            />
            <el-button
              v-else
              class="button-new-tag ml-1"
              size="small"
              @click="showInput"
            >
              + 新增标签
            </el-button>
          </span>
        </el-form-item>
      </el-form>
    </FormDialog>
  </div>
</template>

<script setup>
import { Search, RefreshRight, Plus, Delete } from '@element-plus/icons-vue';
import { ref, reactive, nextTick } from 'vue';
import { getTagPageList, addTag, deleteTag } from '@/api/admin/tag';
import moment from 'moment';
import { showMessage } from '@/composables/util';
import FormDialog from '@/components/FormDialog.vue';
import Skeleton from '@/components/Skeleton.vue';
import { useTable } from '@/composables/useTable';
import { useRequest } from '@/composables/useRequest';

// 日期
const pickDate = ref('');

// 查询条件：开始结束时间
const startDate = ref(null);
const endDate = ref(null);

// 监听日期组件改变事件，并将开始结束时间设置到变量中
const datepickerChange = (e) => {
  startDate.value = moment(e[0]).format('YYYY-MM-DD');
  endDate.value = moment(e[1]).format('YYYY-MM-DD');
  updateQueryParams();
};

const shortcuts = [
  {
    text: '最近一周',
    value: () => {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      return [start, end];
    },
  },
  {
    text: '最近一个月',
    value: () => {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
      return [start, end];
    },
  },
  {
    text: '最近三个月',
    value: () => {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
      return [start, end];
    },
  },
];

// 表格逻辑
const {
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
} = useTable(getTagPageList, {
  name: '',
  startDate: null,
  endDate: null,
});

// 更新查询参数
const updateQueryParams = () => {
  queryParams.startDate = startDate.value;
  queryParams.endDate = endDate.value;
  getTableData();
};

// 重置查询条件
const reset = () => {
  pickDate.value = '';
  startDate.value = null;
  endDate.value = null;
  resetParams({
    name: '',
    startDate: null,
    endDate: null,
  });
};

// 对话框是否显示
const formDialogRef = ref(null);

// 新增分类按钮点击事件
const addCategoryBtnClick = () => {
  formDialogRef.value.open();
};

// 表单引用
const formRef = ref(null);

// 添加文章分类表单对象
const form = reactive({
  tags: [],
});

// 动态标签
const dynamicTags = ref([]);
// 标签输入框值
const inputValue = ref('');
// 标签输入框是否显示
const inputVisible = ref(false);
// 标签输入框的引用
const InputRef = ref('');

const handleClose = (tag) => {
  dynamicTags.value.splice(dynamicTags.value.indexOf(tag), 1);
};

const showInput = () => {
  inputVisible.value = true;
  nextTick(() => {
    InputRef.value.input.focus();
  });
};

const handleInputConfirm = () => {
  if (inputValue.value) {
    dynamicTags.value.push(inputValue.value);
  }
  inputVisible.value = false;
  inputValue.value = '';
};

// 删除请求逻辑
const { execute: executeDelete, loading: deleteLoading } =
  useRequest(deleteTag);

// 删除标签
const deleteTagSubmit = async (row) => {
  try {
    await executeDelete(row.id, {
      showSuccess: true,
      successMessage: '删除成功',
    });
    getTableData();
  } catch (error) {
    // 错误已在useRequest中处理
  }
};

// 提交表单
const onSubmit = () => {
  // 先验证 form 表单字段
  formRef.value.validate((valid) => {
    if (!valid) return;

    // 显示提交按钮 loading
    formDialogRef.value.showBtnLoading();
    form.tags = dynamicTags.value;

    addTag(form)
      .then((res) => {
        if (res.success == true) {
          showMessage('添加成功');
          // 将表单中标签数组置空
          form.tags = [];
          dynamicTags.value = [];
          // 隐藏对话框
          formDialogRef.value.close();
          // 重新请求分页接口，渲染数据
          getTableData();
        } else {
          // 获取服务端返回的错误消息
          let message = res.message;
          // 提示错误消息
          showMessage(message, 'error');
        }
      })
      .finally(() => formDialogRef.value.closeBtnLoading()); // 隐藏提交按钮 loading
  });
};
</script>
