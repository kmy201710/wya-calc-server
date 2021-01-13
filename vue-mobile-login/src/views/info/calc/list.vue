// https://blog.csdn.net/xiaoxionglove/article/details/102243721?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control
// https://www.cnblogs.com/s313139232/p/9182363.html
// http://doc.huangsw.com/vue-easytable/app.html#/table/basic
<template>
  <div style="padding: 10px 5px" >
    <div class="table_btn">
      <el-row>
        <el-button type="warning"
                    size="mini"
                    @click="addHandler()">新增</el-button>
        <el-button type="warning"
                    size="mini">批量删除</el-button>
      </el-row>
    </div>
    <add-or-update v-if="dialogFormVisible"
                   ref="addOrUpdate">
                   <!-- @refreshDataList="getData" -->
    </add-or-update>
    <v-table is-horizontal-resize column-width-drag style="width:100%" 
      row-hover-color="#eee" 
      row-click-color="#edf7ff"
      even-bg-color="#f2f2f2" 
      :table-data="tableData" 
      :columns="columns" 
      :paging-index="(page.pageIndex-1)*page.pageSize" 
      :show-vertical-border="false"
      :is-loading="loading"  
      :sort-change="sortChange"
      :select-change="getSelectObj"
      :row-click="rowClick"
      :column-cell-class-name="columnCellClass"

      :filter-method="filterMethod"
      :cell-edit-done="cellEditDone"
      @on-custom-comp="customCompFunc" ></v-table>
    <div class="mt20 mb20 bold" style="margin-top:10px;">
      <v-pagination @page-change="pageChange" @page-size-change="pageSizeChange" 
      :total="page.total" size='small' 
      :page-size="page.pageSize" 
      :layout="['total']"></v-pagination>
    </div>
      <!-- :layout="['total', 'prev', 'pager', 'next', 'sizer', 'jumper']"></v-pagination> -->
  </div>   
</template>

<script>
  import NavBar from "@/components/NavBar"
  import AddOrUpdate from './addEdit'
  import Vue from 'vue'

  // 导入共用组件
  import { Toast } from 'vant'
  import { resTrue } from "@/utils/commons"
  import { list } from "@/api/calc"

  export default {
    // name: 'vueeasytable',
    name: 'calculation',
    components: {
        NavBar, AddOrUpdate
    },
    data(){
      return{
        dialogFormVisible: false,
        // isShow: true,
        // isNameInputActive:false,
        // nameVal:'',
        // placeholderName:'输入信息',
        /*获取数据*/
        getData:[],
        /*表格信息*/
        loading: true,
        finished: false,
        count:0,
        tableData: [],
        page:{
          total:0,
          pageIndex: 1,
          pageSize: 20,
        },
        columns: [
          // {
          //   field: '', title: '', width: 30, titleAlign: 'center', columnAlign: 'center',
          //   type: 'selection', isResize: true, isFrozen: true,
          // },
          {
            field: 'seq', title: '', width: 30, titleAlign: 'center', columnAlign: 'center',
            formatter: function(rowData, rowIndex, pagingIndex, field) {
                return `<span style="color:#4078c0;">${rowIndex+1}</span>`;
              },
            isResize: true,
            isFrozen: true
          },
          {
            field: 'type', title: '类型', width: 60, titleAlign: 'center', columnAlign: 'center',
            formatter: function(rowData, rowIndex, pagingIndex, field) {
                // return `<span style="color:#4078c0;">${rowData[field]}</span>`;
                return `<span style="color:#4078c0;">${rowData[field] === '0' ? '简单' :
                rowData[field] === '1' ? '中等' : rowData[field] === '2' ? '困难' : '自定义'}</span>`;
              },
            filterMultiple: true,
            filters: [
              {
                  label: '简单',
                  value: '0',
              },{
                  label: '中等',
                  value: '1',
              },{
                  label: '困难',
                  value: '2',
              },{
                  label: '自定义',
                  value: '3',
              }],
            isResize: true,
            isFrozen: true
          },
          {
            field: 'content', title: '表达式', width: 160, titleAlign: 'center', columnAlign: 'center',
            formatter: function (rowData,rowIndex,pagingIndex,field) {
                return `<span class='cell-edit-color'>${rowData[field]}</span>`;
              },
            // filterMultiple: true,
            // filters: [{
            //     label: '孙伟',
            //     value: '孙伟',
            // }, {
            //     label: '吴伟',
            //     value: '吴伟',
            // }, {
            //     label: '周伟',
            //     value: '周伟',
            // }],
            isResize: true
          },
          // {
          //   field: 'info', title: '结果', width: 60, titleAlign: 'center', columnAlign: 'center',
          //   formatter: function (rowData,rowIndex,pagingIndex,field) {
          //       return `<span class='cell-edit-color'></span>`;
          //     },
          //   isEdit:true,
          //   isResize: true,
          //   // orderBy: 'desc'
          // },
          {
            field: 'custome-adv', title: '操作',width: 110, titleAlign: 'center',columnAlign:'center',componentName:'table-operation',isResize:true
          // {
          //   field: 'adv', title: '操作',width: 110, titleAlign: 'center', columnAlign:'center',
          //   formatter: function (rowData,rowIndex,pagingIndex,field) {
          //       return `<span>
          //                 <a href="javascript:void(0);">编辑</a>&nbsp;
          //                 <a href="javascript:void(0);">删除</a>
          //               </span>`
          //     },
          //   isResize:true
          }],
        /*查询条件*/
        sfzhcx:"",/*身份证号*/
        /*选中的对象*/
        SelectObj:[],
        seeobjs:{},
        /*查看单条信息开关*/
        seeModalframe:false,
        /*添加单条信息开关*/
        addModalframe:false,
        /*修改单条信息开关*/
        modifyModalframe:false,
        /*添加的数据model*/
        addModel:{},
        /*修改数据*/
        modifyModel:{},
      }
    },
    /* 这里我是在使用接口请求后，对返回的数据进行判断 */
    created(){
      this.loadData();
    },
    methods:{
      back(){
        this.$router.go(-1);//返回上一层
      },
      // post请求数据
      loadData() {    
        this.loading = true;
        // let url = httpUrl + calc
        let params = {
          pageNum: this.page.pageIndex,
          pageSize: this.page.pageSize
        }
        console.log(params)
        list(params)
        // this.$http.post('/calc/pageList', params)
        // this.$ajax.post(url, params, { emulateJSON: true })
          .then(res => {
            if (resTrue(res) === 1) {
              if (this.count !== 0) {
                Toast("刷新成功");
              }
              this.count++;
              this.tableData = res.pageInfo.list;
              this.page.total = res.pageInfo.total;
            }
            // 加载状态结束
            this.loading = false;
          })
          .catch(err => {
            console.log(err);
            this.loading = false;
          })
      },
      
      // 自定义列
      customCompFunc(params){
          console.log(params);
          if (params.type === 'delete'){ // do delete operation
              this.$delete(this.tableData,params.index);
          }else if (params.type === 'edit'){ // do edit operation
              alert(`行号：${params.index + 1} 提示：${params.rowData['calculations']}`)
          }
      },
      // 新增
      addHandler () {
        // debugger
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init()
        })
      },
      // 单元格样式
      columnCellClass(rowIndex,columnName,rowData){
          // 给三行column为‘hobby’的列设置className
          if (rowIndex % 10 === 0 && columnName==='seq'){
              return 'column-cell-class-name-test';
          }
          // 给第二行设置className
          // if (rowIndex ===1){
          //     return 'column-cell-class-name-test2';
          // }
          // 给姓名为‘周伟’的行设置className
          // if (rowData.name === '周伟'){
          //     return 'column-cell-class-name-test';
          // }
      },
      rowClick(rowIndex,rowData){
          console.log('rowClick');
          alert(`行号：${rowIndex + 1}  提示：${rowData.calculations}`)
      },
      // 数据筛选
      filterMethod(filters){
          if (filters.type === null) {
            this.loadData()
            return
          }
          let tableData = this.tableData;
          console.log('filters')
          console.log(filters)
          console.log(tableData)
          // filter gender
          if (Array.isArray(filters.type)){
              tableData = tableData.filter(item => item.type === filters.type[0])
          }
          // filter name
          if (Array.isArray(filters.name)){
              tableData = tableData.filter(item => filters.name.indexOf(item.name) > -1);
          }
          this.tableData = tableData;
      },
      // update(rowData, index){
      //     // 参数根据业务场景随意构造
      //     let params = {type:'edit',index:this.index,rowData:this.rowData};
      //     // this.$emit('on-custom-comp',params);
      //     alert(params)
      // },
      // deleteRow(rowData, index){
      //     // 参数根据业务场景随意构造
      //     // let params = {type:'delete',index:this.index};
      //     // this.$emit('on-custom-comp',params);
      //     alert(params)
      // },
      
      // 单元格编辑回调
      cellEditDone(newValue,oldValue,rowIndex,rowData,field){
        this.tableData[rowIndex][field] = newValue;
        // 接下来处理你的业务逻辑，数据持久化等...
        console.log('cellEditDone ' + newValue)
        setTimeout(()=>{
          
        },500)
      },
      
      sortChange(params){
        // 数值排序
        if (params.calculations.length > 0) {
            this.tableData.sort(function (a, b) {
                if (params.calculations === 'desc') {
                    return b.calculations - a.calculations
                } else if (params.calculations === 'asc') {
                    return a.calculations - b.calculations
                } else {
                    return 0
                }
            })
        }
        // 日期排序
        if (params.end_date.length > 0) {
            this.tableData.sort(function (a, b) {
                if (params.end_date === 'desc') {
                  return Date.parse(b.end_date) - Date.parse(a.end_date)
                } else if (params.end_date === 'asc') {
                  return Date.parse(a.end_date) - Date.parse(b.end_date)
                } else {
                  return 0
                }
            })
        }

        // console.log(column);
 
        // //获取字段名称和排序类型
        // var fieldName = column.prop;
        // var sortingType = column.order;
 
        // //如果字段名称为“创建时间”，将“创建时间”转换为时间戳，才能进行大小比较
        // if(fieldName=="createTime"){
        //    this.tableData.map(item => {
        //         item.createTime = this.$moment(item.createTime).valueOf();
        //    });
        // }
                   
        // //按照降序排序
        // if(sortingType == "descending"){
        //     this.tableData = this.tableData.sort((a, b) => b[fieldName] - a[fieldName]);
        // }
        // //按照升序排序
        // else{
        //     this.tableData = this.tableData.sort((a, b) => a[fieldName] - b[fieldName]);
        // }
 
        // //如果字段名称为“创建时间”，将时间戳格式的“创建时间”再转换为时间格式
        // if(fieldName=="createTime"){
        //     this.tableData.map(item => {
        //         item.createTime = this.$moment(item.createTime).format(
        //              "YYYY-MM-DD HH:mm:ss"
        //         );
        //     });
        // }
        
        // console.log(this.tableData);
      },
      /*分页处理*/
      getTableData(){
        console.log('page>> '+JSON.stringify(this.page));
        this.loadData();
      },
      /*页码触发更新*/
      pageChange(pageIndex){
        setTimeout(()=>{
          //your code
          this.page.pageIndex = pageIndex;
          this.getTableData();
        },500)
      },
      /*每页条数触发更新*/
      pageSizeChange(newPageSize){
        setTimeout(()=>{
          //your code
          this.page.pageIndex = 1;
          this.page.pageSize = newPageSize;
          this.getTableData();
        },500)
      },
      /*条件查询*/
      selectData(){
        this.websitesHandle();
        this.tableData =this.getData.slice((this.page.pageIndex-1)*this.page.pageSize,(this.page.pageIndex)*this.page.pageSize);
      },
      /*获取选中对象*/
      getSelectObj(obj){
        this.SelectObj=[];
        this.SelectObj = this.SelectObj.concat(obj);
      },
      /*查看单条记录详细信息*/
      seeData(){
        /*判断已经选择数据的条数*/
        if(this.SelectObj.length ==0){
          this.$Modal.alert({
            title: '错误信息',
            content: '请先选择一条记录'
          })
        }
        else if(this.SelectObj.length >1){
          this.$Modal.alert({
            title: '错误信息',
            content: '每次只可以查看一条记录'
          })
        }
        else if(this.SelectObj.length ==1){
          this.seeModalframe=true;
          var Data;
          $.ajax({
            type: 'post',
            async: false,
            url: 'http://localhost:47953/Home/UploadJtnc',
            data:{id:this.SelectObj[0].ID},
            success:function (response) {
              Data =  response;
            },
            error:function (error) {
              console.log(error)
            }
          });
          this.seeobjs = Data;
        }
      }
    }
  }
  // 自定义列组件
  Vue.component('table-operation',{
      template:`<span>
      <a href="" @click.stop.prevent="deleteRow(rowData,index)">删除</a>
      </span>`,
      // <a href="" @click.stop.prevent="update(rowData,index)">编辑</a>&nbsp;
      props:{
          rowData:{
              type:Object
          },
          field:{
              type:String
          },
          index:{
              type:Number
          }
      },
      methods:{
          update(){
              // 参数根据业务场景随意构造
              let params = {type:'edit',index:this.index,rowData:this.rowData};
              this.$emit('on-custom-comp',params);
          },

          deleteRow(){
              // 参数根据业务场景随意构造
              let params = {type:'delete',index:this.index};
              this.$emit('on-custom-comp',params);
          }
      }
  })
</script>

<style>
  .title-cell-class-name-test{
      background-color: #f60;
      color:#fff;
  }
  .column-cell-class-name-test{
      background-color: #187;
  }
  .column-cell-class-name-test .v-table-body-cell{
      border-color: #187;
  }
  .column-cell-class-name-test2{
      background-color: #2db7f5;
      font-weight: bold;
  }
  .column-cell-class-name-test2 .v-table-body-cell{
      border-color: #2db7f5;
  }
  .cell-edit-color{
      color:#2db7f5;
      font-weight: bold;
      
      /* success: "#4caf50";
      error: "#ff5252";
      info: "#2196f3"; */
  }
  
  .table_btn {
    float: right;
  }
</style>
