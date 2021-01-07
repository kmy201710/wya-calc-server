// https://segmentfault.com/a/1190000018901351
<template>
  <div id="manage">
    <!-- <marqueeUp :marqueeList='marqueeList'></marqueeUp> -->
    <slider :swiperList='swiperList'></slider>
    <van-divider></van-divider>
    <managerList :listData='managerList'></managerList>
    
    <van-action-sheet
            v-model="show"
            :actions="actions"
            cancel-text="取消"
            @cancel="onCancel"
            @select="onSelect"
            />
  </div>
</template>

<script>
  // import marqueeUp from './marqueeUp'
  import slider from './slider'
  import managerList from "./managerList";
  // 导入共用组件
  import {Toast} from 'vant'
  import {resTrue} from "@/utils/commons";
  
  // let caseHouse = require("@/assets/icon/user.png");
  // let feedBack = require("@/assets/icon/user.png");
  // let local = require("@/assets/icon/user.png");
  // let material = require("@/assets/icon/user.png");
  let local = '/image/home/user.png';

  export default {
    name: "homeManager",
    components: {
      // marqueeUp, 
      slider, 
      managerList
    },
    data() {
      return {
        shop: this.$store.state.user.shop,
        role: this.$store.state.user.role,
        marqueeList: [],
        swiperList: [],
        // managerList: [],
        managerList: [
          { icon: local, title: "数学表达式", url: "/info/calc"},
          { icon: local, title: "动态信息", url: "/info/user"},
        //   { icon: local, title: "商品列表", url: "/info/user"},
        //   { icon: local, title: "订单列表", url: "/info/user"},
        //   { icon: local, title: "===麻辣香锅1", url: "/info/managerCenter"},
        //   { icon: local, title: "===麻辣香锅2", url: "/info/managerCenter"},
        //   { icon: local, title: "===麻辣香锅3", url: "/info/managerCenter"},
        //   { icon: local, title: "===麻辣香锅3", url: "/info/managerCenter"},
        //   { icon: material, title: "===油焖大虾4", url: "/info/managerCenter"},
        //   { icon: material, title: "===油焖大虾5", url: "/info/managerCenter"},
        //   { icon: material, title: "===油焖大虾6", url: "/info/managerCenter"},
        //   { icon: caseHouse, title: "===焦糖玛奇朵", url: "/info/managerCenter"},
        //   { icon: feedBack, title: "===黑糖玛奇朵", url: "/info/managerCenter"},
        ],        
        show: false,
        actions: [
          // {name: 'QQ登录', index: 0},
          // {name: '微信登录', index: 1},
          // {name: '微博登录', index: 2}
          {name: '小学生', index: 10},
          {name: '中学生', index: 20},
          {name: '大学生', index: 30},
          {name: '自由人', index: 40},
          {name: '自主创业', index: 50},
          {name: '自由职业', index: 60},
        ]
      }
    },
    /* 这里我是在使用接口请求后，对返回的数据进行判断 */
    created(){
      this.loadData();
    },
    methods:{
      // post请求数据
      loadData() {
        console.log('loadData');
        let params = {
          shopId: this.shop,
          roleId: this.role,
        }
        this.$message({ type: "info", message: params });
        this.$http.get('/manager/getList', params)
        // this.$ajax.get(url, params)
          .then(res => {
            if (resTrue(res)) {
              if (this.shop === null || this.shop === '1') {
                // 显示角色选择框
                if (this.role === null || this.role === '99') {
                  this.show = true; 
                }
              }

              this.marqueeList = res.data.marqueeList
              this.swiperList = res.data.swiperList
              this.managerList = res.data.managerList
            }
          })
          .catch(err => {
            console.log(err);
            this.loading = false;
          })
      },
      onCancel() {
        let params = {
          shop: 1,
          role: 99
        };
        this.$store.commit('updateUser', params);
        this.show = false;
      },
      onSelect(item) {
        let params = {
          shop: 1,
          role: item.index
        };
        this.$store.commit('updateUser', params);
        this.show = false;
      }
    }

  }
</script>
