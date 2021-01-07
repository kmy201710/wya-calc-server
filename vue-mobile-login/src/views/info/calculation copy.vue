// https://blog.csdn.net/weixin_38673922/article/details/107189317
<template>
  <div style="padding:15px 5px">
    <v-table is-horizontal-resize style="width:100%" 
    	:columns="columns" 
    	:table-data="tableData" 
    	row-hover-color="#eee" 
    	row-click-color="#edf7ff" 
    	:cell-edit-done="cellEditDone"
 		@sort-change="sortChange"
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
  import {Toast} from 'vant'
  // 导入共用组件
  import {httpUrl} from "@/utils/commons";

  export default {
    name: 'calculation',
    data(){
      return{
        tableData: [],
        columns: [{
              field: 'tag',
              title: '标签',
              width: 50,
              titleAlign: 'center',
              columnAlign: 'center',
              formatter: function(rowData, rowIndex, pagingIndex, field) {
                return `<span style="color:#4078c0;">${rowData[field]}</span>`;
              },
              isResize: true,
              // isFrozen: true,
            },
            {
              field: 'nums',
              title: '数组',
              width: 50,
              titleAlign: 'center',
              columnAlign: 'center',
              isResize: true
            },
            {
              field: 'calculations',
              title: '结果',
              width: 20,
              titleAlign: 'center',
              columnAlign: 'center',
              isResize: true
            },
            {
              field: 'content',
              title: '数学表达式',
              width: 150,
              titleAlign: 'center',
              columnAlign: 'center',
              isResize: true
            }
        ]
      }
    },
    created() {
      //your code
      // httpUrl = 'http://192.168.0.102:80/webapp'
      let url = httpUrl + '/calculation/generator?num=1&size=5'
      let params = {
        num: 1,
        size: 5
      }
      this.$ajax.get(url)
        .then(res => { 
          console.log(res.data.data);   
          if (res.status === 200) {
            if (this.count !== 0) {
              Toast("刷新成功");
            }
            this.count++;
            this.tableData = res.data.data;
          }
        }) 
        .catch(err => { 
          console.log(err); 
        })
    }
};
</script>