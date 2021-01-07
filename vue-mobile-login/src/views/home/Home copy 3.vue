<template>
  <div style="padding: 20px;">
    <div class="title">列表：总计 {{ pageInfo.total }}，页码 {{ pageInfo.pageNum }}，每页 {{ pageInfo.pageSize }}条数据</div>
    <van-divider></van-divider>
    <div class="scrollerWrap">
      <scroller :on-refresh="refresh" :on-infinite="infinite" ref="scrollerBottom">
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
      </scroller>
    </div>
  </div>
</template>

<script>
import {Toast} from 'vant'
import vueSeamlessScroll from 'vue-seamless-scroll'

// https://blog.csdn.net/qq_32971359/article/details/108407905
export default {
  name: 'Home',
  data() {
    return {
      list: [],
      loading: false,
      finished: false,
      isLoading: false,
      count: 0
    }
  },
  methods: {
    refresh(done){//下拉刷新
      //your code
      let data = {
        shopId: 1
      }
      // post 请求  
      this.$ajax.post('http://192.168.0.102:7080/user/pageList', data, {emulateJSON:true})
        .then(res => { 
          console.log('2 post ' + this.count); // 1 post
          console.log(res.data.pageInfo);   
          if (res.status === 200) {
            if (this.count !== 0) {
              Toast("刷新成功");
            }
            this.count++;
            this.pageInfo = res.data.pageInfo;
            this.list = res.data.pageInfo.list;
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
    },
    infinite(done){//上拉加载
      if(this.isBottom){//当没有更多数据的时候结束加载
        this.loadingTips ="无更多数据"
        setTimeout(()=>{
          done&&done(true);
        },100);
      }else{//有更多数据时进行数据分页显示
        this.refresh(done);
        setTimeout(() => {
          this.page++;
          // this.getDataList(this.page);
          // this.$nextTick(() => {
          //   this.$refs.scrollerBottom.reset()
          // })
        },1500)
      }
    },


    onLoad() {
      // get 请求
      // this.$ajax.get('http://localhost:7080/math/generator' + '?mark=add&nums=2,3,4')  // url格式,如:?type=xxx&key=value
      // // this.$ajax.get('http://localhost:7080/math/generator', data)  // url格式,如:?type=xxx&key=value
      //   .then(res => {
      //     console.log('1 get'); // 1 get
      //     console.log(res); 
      //     console.log(res.data); 
      //     if (res.status === 200) {
      //       if (this.count !== 0) {
      //         Toast("刷新成功");
      //       }
      //       this.count++;
      //       this.list = res.data.data;
      //     }
      //     // 加载状态结束
      //     this.loading = false;

      //     // 数据全部加载完成
      //     if (this.list.length >= 1) {
      //       this.finished = true;
      //     }
      //   }) 
      //   .catch(err => { 
      //     console.log(err); 
      //   })
      let data = {
        shopId:2
      }
      // post 请求  
      this.$ajax.post('http://192.168.0.102:7080/user/getList', data, {emulateJSON:true})
        .then(res => { 
          console.log('2 post ' + this.count); // 1 post
          console.log(res.data.data);    
          if (res.status === 200) {
            if (this.count !== 0) {
              Toast("刷新成功");
            }
            this.count++;
            this.list = res.data.data;
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
      // let data = {
      //   shopId:2
      // }
      // this.$http.post("http://localhost:7080/user/getList", data, { emulateJSON: true })
      //     .then(res => {
      //       console.log('1 then'); // 1 then
      //       console.log('1 res: '+res); // 1 res: undefined
      //       if (res.code === 10000) {
      //         if (this.count !== 0) {
      //           Toast("刷新成功");
      //         }
      //         this.count++;
      //         this.list = res.data;
      //       }
      //       // 加载状态结束
      //       this.loading = false;

      //       // 数据全部加载完成
      //       if (this.list.length >= 1) {
      //         this.finished = true;
      //       }
      //     }, err => {
      //       Message.error({
      //           message: '2 请求错误或服务器异常!请联系管理员！'
      //       });
      //       reject(err);
      //     })
      //     .catch(res => {
      //       console.log('2 catch'); // 2 catch
      //       console.log('2 res: '+res); // 2 res: TypeError: Cannot read property 'code' of undefined
      //       reject(err);
      //     });
     },
    // 滚动到底部
    // scrollToBottom: function () {
    //   this.$nextTick(() => {
    //     let container = this.$el.querySelector(".content");
    //     container.scrollTop = container.scrollHeight;
    //   })
    // },
  },
  // updated(){
  //   this.scrollToBottom()
  // }
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

/* .van-pull-refresh /deep/ .van-pull-refresh__track { */
.van-pull-refresh .van-pull-refresh__track {
  height: inherit;
}

.seamless-warp{
  width: 100%;
  height: calc(100% - 16px);
  overflow: hidden;
}

.winners-list-cont{
  width: 95%;
  height: 8.16rem;
  margin: 0 auto;
  overflow: hidden;
}
.scrollerWrap{
  position: absolute;
  width: 100%;
  height: 90%;
  top:50px;
  bottom:20px;
}
</style>