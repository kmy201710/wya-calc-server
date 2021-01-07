// https://blog.csdn.net/weixin_38673922/article/details/107189317
// https://blog.csdn.net/yaojie5519/article/details/104773440/
<template>
      <!-- :title-rows="titleRows" -->
 		  <!-- @sort-change="sortChange" -->
   <div id="user">
    <v-table is-horizontal-resize style="width:100%" 
    	:columns="columns" 
    	:table-data="tableData" 
    	row-hover-color="#eee" 
    	row-click-color="#edf7ff" 
    	:cell-edit-done="cellEditDone"
        :paging-index="(pageIndex-1)*pageSize"   	
    	></v-table>
       <div class="mt20 mb20 bold" style="margin-top:10px;">
			<v-pagination @page-change="pageChange" @page-size-change="pageSizeChange" :total="total" size='small' :page-size="pageSize" :layout="['prev', 'pager', 'next']"></v-pagination>
		</div>
  </div>
</template>

<script>
  import Vue from 'vue'
  // import Vue from 'vue'
  import {Toast} from 'vant'
  // 导入共用组件
  import {httpUrl} from "@/utils/commons";

  export default {
    name: 'user',
    data(){
      return{
        total: 0,
        pageIndex: 1,
        pageSize: 10,
        tableData: [],
        columns: [
              {
                field: '', title: '', width: 30, titleAlign: 'center', columnAlign: 'center',
                formatter: function(rowData, rowIndex, pagingIndex, field) {
                    return `<span style="color:#4078c0;">${rowIndex+1}</span>`;
                  },
                isResize: true,
                isFrozen: true,
              },
              {
                field: 'name', title: '用户', width: 130, titleAlign: 'center', columnAlign: 'center',
                formatter: function (rowData,rowIndex,pagingIndex,field) {
                    return `<span class='cell-edit-color'>${rowData[field]}</span>`;
                  },
                isEdit:true,
                isResize: true,
              },
              {
                field: 'phoneNo', title: '手机号', width: 130, titleAlign: 'center', columnAlign: 'center',
                isResize: true
              },
              {
                field: 'avatar', title: '头像',  width: 230, titleAlign: 'center', columnAlign: 'center',
                isResize: true
              },
              {
                field: 'user-adv', title: '操作',width: 110, titleAlign: 'center', columnAlign:'center',
                formatter: function (rowData,rowIndex,pagingIndex,field) {
                    return `<span>
                              <a href="javascript:void(0);">编辑</a>&nbsp;
                              <a href="javascript:void(0);">删除</a>
                            </span>`
                  },
                isResize:true
              }
          ]
      }
    },
    created() {
      //your code
      let url = httpUrl + '/user/pageList'
      let params = {
        shopId: 1,
        pageNum: this.pageIndex,
        pageSize: this.pageSize
      }
      this.$ajax.post(url, params, { emulateJSON: true })
        .then(res => { 
          console.log(res.data.pageInfo) 
          if (res.status === 200) {
            if (this.count !== 0) {
              Toast("刷新成功");
            }
            this.count++;
            this.tableData = res.data.pageInfo.list;
          }
        }) 
        .catch(err => { 
          console.log(err); 
        })
    },
    methods:{
      update(rowData, index){
          // 参数根据业务场景随意构造
          let params = {type:'edit',index:this.index,rowData:this.rowData};
          // this.$emit('on-custom-comp',params);
          alert(params)
      },
      deleteRow(rowData, index){
          // 参数根据业务场景随意构造
          // let params = {type:'delete',index:this.index};
          // this.$emit('on-custom-comp',params);
          alert(params)
      },
      
      // 单元格编辑回调
      cellEditDone(newValue,oldValue,rowIndex,rowData,field){
        this.tableData[rowIndex][field] = newValue;
        // 接下来处理你的业务逻辑，数据持久化等...
        console.log('cellEditDone ')
        setTimeout(()=>{
        },500)
      },
      sortChange(){
        setTimeout(()=>{
          console.log('sortChange ')
        },500)
      },
      pageChange(){
        setTimeout(()=>{
          console.log('pageChange ')
        },500)
      },
      pageSizeChange(){
        setTimeout(()=>{
          console.log('pageSizeChange ')
        },500)
      }
    }
  }
</script>

<style>
  .cell-edit-color{
      color:#2db7f5;
      font-weight: bold;
  }
</style>
