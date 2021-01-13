<template>
  <div style="padding: 10px 5px" >
    <nav-bar></nav-bar>
    <!-- <div style="text-align: center" v-show="isShow">
      <span v-on:click="back">返回</span>
    </div> -->
    <!-- <div class="title">查询数据：</div> -->
    <div class="scrollerWrap">
      <scroller class="scroller" 
          :on-infinite="infinite"
          :on-refresh="refresh"
          :noDataText="'上拉加载更多数据'"
          refresh-layer-color="red"
          loading-layer-color="red"
          ref="myscroller">
        <!-- <scroller :on-refresh="refresh" :on-infinite="infinite" ref="scrollerBottom"> -->
        <!-- <van-pull-refresh v-model="loading" @refresh="onLoad">
            <van-list
                v-model="loading"
                :finished="finished"
                finished-text="没有更多了"
                @load="onLoad"
            > -->
        <div v-for="(item,index) in list" :key="index">
          <!-- {{index+1}} -->
          <van-row gutter="20">
            <van-col span="6">
              <van-image width="80" height="80" :src="preUrl + item.avatar"></van-image>
              <van-divider></van-divider>
            </van-col>
            <van-col span="18">
              <van-row gutter="15">
                <van-col span="10">{{ item.name === null ? 'null' : item.name }}</van-col>
                <van-col span="6">￥ {{ item.sex === null ? '0' : item.sex }}</van-col>
                <van-col span="8">{{ item.shortDate === null ? '' : item.shortDate }}</van-col>
              </van-row>
              <van-divider></van-divider>
              <van-row type="flex" justify="space-between">
                <van-col span="18">{{ item.phoneNo }}</van-col>
                <van-col span="6">
                  <a href="#">购买</a>&nbsp;
                  <!-- <a href="" @click.stop.prevent="">购买2</a> -->
                </van-col>
              </van-row>
            </van-col>
          </van-row>
        </div>
      </scroller>
    </div>
  </div>
</template>

<script>
  import NavBar from "@/components/NavBar"

  // 导入共用组件
  import { Toast } from 'vant'
  import { preUrl, resTrue } from "@/utils/commons"
  import { list } from "@/api/user"

  export default {
    name: 'user',
    components:{
      NavBar
    },
    computed: {
      classOption () {
        return {
          step: 0.5, // 数值越大速度滚动越快
          limitMoveNum: 10, // 开始无缝滚动的数据量 this.dataList.length
          hoverStop: true, // 是否开启鼠标悬停stop
          direction: 1, // 0向下 1向上 2向左 3向右
          openWatch: true, // 开启数据实时监控刷新dom
          singleHeight: 0, // 单步运动停止的高度(默认值0是无缝不停止的滚动) direction => 0/1
          singleWidth: 0, // 单步运动停止的宽度(默认值0是无缝不停止的滚动) direction => 2/3
          waitTime: 1000, // 单步运动停止的时间(默认值1000ms)
        }
      }
    },
    data() {
      return {
        shop: this.$store.state.shop,
        preUrl: preUrl,
        isShow: true,
        isBottom: false,
        loading: false,
        finished: false,
        isLoading: false,
        count: 0,
        list: [],
        page: {
          total: 0,
          pageIndex: 1,
          pageSize: 10,
        },
      }
    },
    /* 这里我是在使用接口请求后，对返回的数据进行判断 */
    created() {

    },
    methods:{
      back(){
        this.$router.go(-1);//返回上一层
      },
      // post请求数据
      loadData() {
        this.loading = true;
        let params = {
          shopId: this.shop,
          pageNum: this.page.pageIndex,
          pageSize: this.page.pageSize
        }
        list(params)
        // this.$http.post('/user/pageList', params)
        // this.$ajax.post(url, params, { emulateJSON: true })
          .then(res => {
            // console.log(params)
            if (resTrue(res) === 1) {
              if (this.count !== 0) {
                Toast("刷新成功");
              }
              this.count++;
              res.pageInfo.list.forEach(item => {
                this.list.push(item)
              })
              if (!res.pageInfo.hasNextPage) {
                this.isBottom = true;
              }
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
            this.loading = false;
          })
      },
      
      refresh(done){//下拉刷新
        console.log('refresh');
        let self = this;
        setTimeout(() => {
          this.isBottom = false; 
          this.count = 0;
          this.list = [];
          this.page.pageIndex = 1;
          self.$refs.myscroller.finishPullToRefresh();//停止下拉刷新动效
    　　},1000) 
      },
      infinite(done){//上拉加载
        console.log('infinite');
        let self = this;
        if(this.isBottom){//当没有更多数据的时候结束加载
          this.loadingTips ="无更多数据"
          setTimeout(()=>{
            console.log(this.loadingTips);
            done&&done(true);
          },100);
        }else{//有更多数据时进行数据分页显示
          setTimeout(() => {
            console.log(this.count)
            if (this.count !== 0) {
              this.page.pageIndex++;
            } 
            this.loadData();
            this.$nextTick(() => {
              console.log('nextTick' + this.count)
              if (this.count === 0) {
                this.page.pageIndex++; 
                this.loadData();
              }
              // this.$refs.scrollerBottom.reset()
              // self.$refs.myscroller.finishInfinite(true);
            })
          },100)
      　　setTimeout(()=>{
      　　　　this.$refs.myscroller.finishInfinite(true);//停止上拉加载的动效，出现没有“"没有更多数据了”的提示，当传里面的布尔值代表isNoMoreData :Boolean，是否没有更多数据，true：没有更多数据，false：还有数据，至于有什么区别，传一下对比一下就行了
      　　},1000)
        }
      },

    }
  }
</script>

<style>
  .title {
    font-weight: 600;
    font-size: 18px;
  }
  .van-pull-refresh {
    height: 90%;
  }
  .scrollerWrap{
    position: absolute;
    width: 100%;
    height: 90%;
    top:50px;
    bottom:20px;
  }
</style>
