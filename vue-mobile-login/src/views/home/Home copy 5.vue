<template>
    <my-scroll ref="myScroll" :page="page" :on-refresh="onRefresh" :on-pull="onPull"  :get-scroll-top="getTop" :scrollState="scrollState" :noMoreState="noMoreState">
      <div slot="scrollList">
        <div class="header">结果列表：
          总计 {{ pageInfo.total }} 个，每页 {{ pageInfo.pageSize }} 个，当前第 {{ pageInfo.pageNum }} 页</div>
        <div style="padding:20px 10px;">
        <main class="scrollerContent">
          <!-- <div v-for="item in list" :key="item.id"> -->
          <div v-for="(item,index) in list" :key="index">
          <div class="DataList_left">{{index+1}}</div>
          <van-row gutter="20">
            <van-col span="8">
              <!-- item.avatar: require('@/assets/home/gp0.jpg') -->
              <van-image width="80" height="80" :src="item.avatar"></van-image> 
              <!-- <van-image width="80" height="80" :src="require('@/assets/home/gp0.jpg')"></van-image> -->
              <van-divider></van-divider>
            </van-col>
            <van-col span="16">
              <van-row gutter="15">
                <van-col span="16">{{ item.name === null ? 'null' : item.name }}</van-col>
                <van-col span="8">{{ item.sex === 0 ? '男' : '女' }}</van-col>
              </van-row>
              <van-divider></van-divider>
              <van-row type="flex" justify="space-between">
                <van-col span="6">{{ item.phone }}</van-col>
              </van-row>
            </van-col>
          </van-row>
        </div>
        </main>
        </div>
        </div>
      </div>
    </my-scroll>
</template>

<script>
  import {Toast} from 'vant'
  import myScroll from '@/components/LoadMore.vue'
  // 导入共用组件
  import {httpUrl} from "@/utils/commons";
  
  export default {
    components:{
      myScroll
    },
    data(){
      return{
        page:{
          counter:1,  
          pageStart:1,  
          pageEnd:1,  
          total:1,
          pageSize: 5
        },
        scrollState:true,
        noMoreState:false,
        pageInfo:{},
        list:[]
      }
    },
    mounted(){
      console.log('mounted ' + this.page.counter + ' - ' + this.page.total)
      console.log('mounted ' + httpUrl)
      
      //your code
      // httpUrl = 'http://192.168.0.102:80/webapp'
      // let url = httpUrl + '/user/pageList'
      let url = httpUrl + '/user/pageList'
      let params = {
        shopId: 1,
        pageNum: this.page.counter,
        pageSize: this.page.pageSize
      };
      // testSend(params)
      // post 请求 
      // this.$http.post(url, params, {
      //                   "Content-Type": "application/text/html;charset=utf-8"}, {
      //                    emulateJSON: true
      //                   })
      this.$ajax.post(url, params, { emulateJSON: true })
        .then(res => { 
          console.log(res.data.pageInfo);   
          if (res.status === 200) {
            if (this.count !== 0) {
              Toast("刷新成功");
            }
            this.count++;
            this.pageInfo = res.data.pageInfo;
            this.list = res.data.pageInfo.list;
            this.page.total = res.data.pageInfo.lastPage;
          }
          // 加载状态结束
          this.loading = false;

          // 数据全部加载完成
          if (this.pageInfo.list.length >= 1) {
            this.finished = true;
          }
        }) 
        .catch(err => { 
          console.log(err); 
        })
    },
    methods: {
      onRefresh(mun){ //刷新回调
        console.log('onRefresh ' + this.page.counter + ' - ' + this.page.total)
        setTimeout(()=>{
          this.$refs.myScroll.setState(3)
          // 下拉刷新数据重新请求
          if(mun === 1){
            this.page.counter = 1;
            // this.list = [];
            // for(let i=0;i<20;i++){
            //   this.list.push({})
            // }

            //your code
            let url = httpUrl + '/user/pageList'
            let data = {
              shopId: 1,
              pageNum: this.page.counter,
              pageSize: this.page.pageSize
            }
            // post 请求  
            this.$ajax.post(url, data, {emulateJSON:true})
              .then(res => { 
                console.log(res.data.pageInfo);   
                if (res.status === 200) {
                  if (this.count !== 0) {
                    Toast("刷新成功");
                  }
                  this.count++;
                  this.pageInfo = res.data.pageInfo;
                  this.list = [];
                  this.pageInfo.list.forEach(item => {
                    this.list.push(item)
                  })
                }
                // 加载状态结束
                this.loading = false;

                // 数据全部加载完成
                if (this.list.length >= 1) {
                  this.finished = true;
                }
              }) 
              .catch(err => { 
                console.log(err); 
              })
          }
        },500)
      },
      onPull(mun){ //加载回调
        console.log('onPull ' + this.page.counter + ' - ' + this.page.total)
        if(this.page.counter<this.page.total){
          setTimeout(()=>{
            this.page.counter++
            this.$refs.myScroll.setState(5)
            // for(let i=0;i<10;i++){
            //   this.list.push({})
            // }
            
            //your code
            let url = httpUrl + '/user/pageList'
            let data = {
              shopId: 1,
              pageNum: this.page.counter,
              pageSize: this.page.pageSize
            }
            // post 请求  
            this.$ajax.post(url, data, {emulateJSON:true})
              .then(res => { 
                console.log(res.data.pageInfo);   
                if (res.status === 200) {
                  if (this.count !== 0) {
                    Toast("刷新成功");
                  }
                  this.count++;
                  this.pageInfo = res.data.pageInfo;
                  this.pageInfo.list.forEach(item => {
                    this.list.push(item)
                  })
                }
                // 加载状态结束
                this.loading = false;

                // 数据全部加载完成
                if (this.list.length >= 1) {
                  this.finished = true;
                }
              }) 
              .catch(err => { 
                console.log(err); 
              })
          },500)
        }else{
          if(this.page.counter !== 1){
            this.noMoreState = true
          }
          this.$refs.myScroll.setState(7);
        }
      },
      getTop(y) {//滚动条位置
      },
    }
  }
</script>

<style lang="scss" scoped>
  .header{
    position: fixed;
    padding: 10px 10px;
    top: 0;
    left: 0;
    right: 0;
    color: #FF6666
    // background: #F0FFFF;
  }
</style>