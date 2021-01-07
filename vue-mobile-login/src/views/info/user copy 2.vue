// https://blog.csdn.net/weixin_38673922/article/details/107189317
// https://blog.csdn.net/yaojie5519/article/details/104773440/
<template>
   <!-- <v-table :width="1100" :height="280" even-bg-color="#f2f2f2" :title-rows="titleRows" :columns="columns" :table-data="tableData" row-hover-color="#eee" row-click-color="#edf7ff"></v-table> -->
    <!-- <v-table is-horizontal-resize style="width:100%" :columns="columns" :table-data="tableData" row-hover-color="#eee" row-click-color="#edf7ff" @on-custom-comp="customCompFunc"></v-table> -->

  <div style="padding:20px 10px;">
      <!-- :title-rows="titleRows" -->
 		  <!-- @sort-change="sortChange" -->
    <v-table is-horizontal-resize style="width:100%" 
    	:columns="columns" 
    	:table-data="tableData" 
    	row-hover-color="#eee" 
    	row-click-color="#edf7ff" 
      @on-custom-comp="customCompFunc"
    	:cell-edit-done="cellEditDone"
        :paging-index="(pageIndex-1)*pageSize"   	
    	></v-table>
       <div class="mt20 mb20 bold" style="margin-top:10px;">
			<v-pagination @page-change="pageChange" @page-size-change="pageSizeChange" :total="total" size='small' :page-size="pageSize" :layout="['prev', 'pager', 'next']"></v-pagination>
		</div>   
  </div>
<!-- 
  <div class="vant-table">
    <table cellspacing="0" class="table">
      <tr>
        <th class="th" v-for="(item, index) in option.column" :key="index">{{ item.label }}</th>
      </tr>
      <tr v-for="(item, index) in tableData" :key="index" class="list-tr">
        <td v-for="(context, i) in option.column" :key="i">{{ item[context.prop] }}</td>
      </tr>
    </table>
  </div>
  </div> -->
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
        total:0,
        pageIndex:1,
        pageSize:20,
        tableData: [],
        columns: [
              {
                field: '', title: '', width: 20, titleAlign: 'center', columnAlign: 'center',
                formatter: function(rowData, rowIndex, pagingIndex, field) {
                    return `<span style="color:#4078c0;">${rowIndex+1}</span>`;
                  },
                isResize: true,
                isFrozen: true
              },
              {
                field: 'name', title: '用户', width: 50, titleAlign: 'center', columnAlign: 'center',
                formatter: function (rowData,rowIndex,pagingIndex,field) {
                    return `<span class='cell-edit-color'>${rowData[field]}</span>`;
                  },
                isEdit:true,
                isResize: true,
                isFrozen: true
              },
              {
                field: 'phone', title: '手机号', width: 100, titleAlign: 'center', columnAlign: 'center',
                isResize: true
              },
              {
                field: 'avatar', title: '头像',  width: 200, titleAlign: 'center', columnAlign: 'center',
                isResize: true
              },
              {
                field: 'custome-adv', title: '操作',width: 100, titleAlign: 'center', columnAlign:'center',
                componentName: 'table-operation',
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
      customCompFunc(params){
        console.log(params);
        if (params.type === 'delete'){ // do delete operation
          this.$delete(this.tableData,params.index);
        } else if (params.type === 'edit'){ // do edit operation
          alert(`行号：${params.index} 姓名：${params.rowData['name']}`)
        }
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
      pageChange(){ //刷新回调
        setTimeout(()=>{
          console.log('pageChange ')
        },500)
      },
      pageSizeChange(){ //加载回调
        setTimeout(()=>{
          console.log('pageSizeChange ')
        },500)
      }
    }
    
  }  
 
  // 自定义列组件
  Vue.component('table-operation',{
      template:`<span>
      <a href="" @click.stop.prevent="update(rowData,index)">编辑</a>&nbsp;
      <a href="" @click.stop.prevent="deleteRow(rowData,index)">删除</a>
      </span>`,
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
    .cell-edit-color{
        color:#2db7f5;
        font-weight: bold;
    }
</style>
