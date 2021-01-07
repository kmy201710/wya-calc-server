// https://blog.csdn.net/qq_37248504/article/details/110733985
<template>
  <div>
    <div class="container">
      <div class="table_btn">
        <el-row>
          <el-button type="warning"
                     size="mini"
                     @click="add_Handler()">新增</el-button>
          <el-button type="warning"
                     size="mini">批量删除</el-button>
        </el-row>
      </div>
      <el-table :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
                style="width: 100%">
        <el-table-column label="任务定义编码"
                         prop="taskcode"></el-table-column>
        <el-table-column label="任务定义名称"
                         prop="taskname"></el-table-column>
        <el-table-column label="任务Url"
                         prop="url"></el-table-column>
        <el-table-column label="Url执行方法"
                         prop="method"></el-table-column>
        <el-table-column label="Cron表达式"
                         prop="cron"></el-table-column>
        <el-table-column label="定义时间"
                         prop="definetime"></el-table-column>

        <el-table-column align="right">
          <template slot="header"
                    slot-scope="scope">
            <el-input v-model="search"
                      size="mini"
                      placeholder="输入关键字搜索" />
          </template>
          <template slot-scope="scope">
            <el-button size="mini"
                       @click="handleEdit(scope.$index, scope.row.id)">编辑</el-button>
            <el-button size="mini"
                       type="danger"
                       @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="pageNumber"
                     :page-sizes="[10, 20, 30, 40]"
                     :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="this.totalRow">
      </el-pagination>
    </div>
    <add-or-update v-if="dialogFormVisible"
                   ref="addOrUpdate"
                   @refreshDataList="getData">
    </add-or-update>
  </div>

</template>

<script>
import { getTaskPageList } from '@/api/back/task'
import AddOrUpdate from './addEdit'

export default {
  components: {
    AddOrUpdate
  },
  data: function () {
    return {
      dialogFormVisible: false,
      tableData: [],
      search: '',
      pageNumber: 1, // 当前页
      pageSize: 10, // 每页大小
      totalRow: 0, // 总条数
      totalPage: 0 // 总页数
    }
  },
  methods: {
    // 新增
    add_Handler () {
      debugger
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init()
      })
    },
    handleEdit (index, id) {
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
      console.log(index, id)
    },
    handleDelete (index, row) {
      console.log(index, row)
    },
    // 分页点击方法
    handleSizeChange (val) {
      // console.log(`每页 ${val} 条`);
      this.pageSize = val
      var params = {
        pageNumber: this.pageNumber,
        pageSize: this.pageSize
      }
      this.getPageList(params)
    },
    handleCurrentChange (val) {
      // console.log(`当前页: ${val}`);
      this.pageNumber = val
      var params = {
        pageNumber: this.pageNumber,
        pageSize: this.pageSize
      }
      this.getPageList(params)
    },
    // 获取分页列表
    getPageList (params) {
      getTaskPageList('/taskdefine/page', params, 'post', 'taskcenter').then(
        res => {
          if (res.code === 200) {
            this.tableData = res.data.list
            this.totalRow = res.data.total
          } else {
            this.$message.false(res.message)
          }
        }
      )
    }
  },
  created () {
    var params = {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize
    }
    this.getPageList(params)
  }
}
</script>

<style scoped>
h1 {
  text-align: center;
  margin: 30px 0;
}
p {
  line-height: 30px;
  margin-bottom: 10px;
  text-indent: 2em;
}
.logout {
  color: #409eff;
}
.table_btn {
  float: right;
}
</style>