<template>
    <div>
        <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
        <el-card shadow="never" class="mb-5">
            <!-- flex 布局，内容垂直居中 -->
            <div class="flex items-center">
                <el-text>知识库标题</el-text>
                <div class="ml-3 w-52 mr-5"><el-input v-model="searchWikiTitle" placeholder="请输入（模糊查询）" clearable /></div>

                <el-text>创建日期</el-text>
                <div class="ml-3 w-30 mr-5">
                    <!-- 日期选择组件（区间选择） -->
                    <el-date-picker v-model="pickDate" type="daterange" range-separator="至" start-placeholder="开始时间"
                        end-placeholder="结束时间" size="default" :shortcuts="shortcuts" @change="datepickerChange" />
                </div>

                <el-button type="primary" class="ml-3" :icon="Search" @click="getTableData">查询</el-button>
                <el-button class="ml-3" :icon="RefreshRight" @click="reset">重置</el-button>
            </div>
        </el-card>

        <el-card shadow="never">
            <!-- 新增知识库按钮 -->
            <div class="mb-5">
                <el-button type="primary">
                    <el-icon class="mr-1">
                        <Plus />
                    </el-icon>
                    新增知识库</el-button>
            </div>

            <!-- 分页列表 -->
            <el-table :data="tableData" border stripe v-loading="tableLoading" table-layout="auto">
                <el-table-column type="index" label="序号" width="60" />
                <el-table-column prop="title" label="标题"  />
                <el-table-column prop="cover" label="封面" >
                    <template #default="scope">
                        <el-image style="width: 100px;" :src="scope.row.cover" />
                    </template>
                </el-table-column>
                <el-table-column prop="isTop" label="是否置顶" >
                    <template #default="scope">
                        <el-switch
                            v-model="scope.row.isTop"
                            inline-prompt
                            :active-icon="Check"
                            :inactive-icon="Close"
                        />
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="发布时间" />
                <el-table-column prop="isPublish" label="是否发布" >
                    <template #default="scope">
                        <el-switch
                            v-model="scope.row.isPublish"
                            inline-prompt
                            :active-icon="Check"
                            :inactive-icon="Close"
                        />
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="160">
                    <template #default="scope">
                        <el-tooltip class="box-item" effect="dark" content="编辑" placement="bottom">
                            <el-button size="small" :icon="Edit" circle >
                            </el-button>
                        </el-tooltip>
                        
                        <el-tooltip class="box-item" effect="dark" content="编辑目录" placement="bottom">
                            <el-button size="small" :icon="Tickets" circle>
                            </el-button>
                        </el-tooltip>
                        
                        <el-tooltip class="box-item" effect="dark" content="预览" placement="bottom">
                            <el-button size="small" :icon="View" circle>
                            </el-button>
                        </el-tooltip>
                            
                        <el-tooltip class="box-item" effect="dark" content="删除" placement="bottom">
                            <el-button type="danger" size="small" :icon="Delete" circle>
                            </el-button>
                        </el-tooltip>
                        
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页 -->
            <div class="mt-10 flex justify-center">
                <el-pagination v-model:current-page="current" v-model:page-size="size" :page-sizes="[10, 20, 50]"
                    :small="false" :background="true" layout="total, sizes, prev, pager, next, jumper" :total="total"
                    @size-change="handleSizeChange" @current-change="getTableData" />
            </div>

        </el-card>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { Search, RefreshRight, Check, Close, Delete, Edit, Tickets, View } from '@element-plus/icons-vue'
import moment from 'moment'
import { getWikiPageList } from '@/api/admin/wiki'

// 模糊搜索的知识库标题
const searchWikiTitle = ref('')
// 日期
const pickDate = ref('')

// 查询条件：开始结束时间
const startDate = reactive({})
const endDate = reactive({})

// 监听日期组件改变事件，并将开始结束时间设置到变量中
const datepickerChange = (e) => {
    startDate.value = moment(e[0]).format('YYYY-MM-DD')
    endDate.value = moment(e[1]).format('YYYY-MM-DD')

    console.log('开始时间：' + startDate.value + ', 结束时间：' + endDate.value)
}

const shortcuts = [
    {
        text: '最近一周',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            return [start, end]
        },
    },
    {
        text: '最近一个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            return [start, end]
        },
    },
    {
        text: '最近三个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            return [start, end]
        },
    },
]

// 重置
const reset = () => {
    pickDate.value = ''
    startDate.value = null
    endDate.value = null
    searchWikiTitle.value = ''
}
// 表格加载 Loading
const tableLoading = ref(false)
// 表格数据
const tableData = ref([])
// 当前页码，给了一个默认值 1
const current = ref(1)
// 总数据量，给了个默认值 0
const total = ref(0)
// 每页显示的数据量，给了个默认值 10
const size = ref(10)


// 获取分页数据
function getTableData() {
    // 显示表格 loading
    tableLoading.value = true
    // 调用后台分页接口，并传入所需参数
    getWikiPageList({ current: current.value, size: size.value, startDate: startDate.value, endDate: endDate.value, title: searchWikiTitle.value })
        .then((res) => {
            if (res.success == true) {
                tableData.value = res.data
                current.value = res.current
                size.value = res.size
                total.value = res.total
            }
        })
        .finally(() => tableLoading.value = false) // 隐藏表格 loading
}
getTableData()

// 每页展示数量变更事件
const handleSizeChange = (chooseSize) => {
    console.log('选择的页码' + chooseSize)
    size.value = chooseSize
    getTableData()
}
</script>
