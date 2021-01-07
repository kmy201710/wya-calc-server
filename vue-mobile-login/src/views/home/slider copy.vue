<template>
  <swiper :options="swiperOption" class="swiper-wrap"  ref="mySwiper" v-if="banner.length!=0">
  <swiper-slide v-for="(item,index) in banner" :key="index" >
    <img width="100" height="80" :src="item" alt="" />

  </swiper-slide>
  <!-- 常见的小圆点 -->
  <div class="swiper-pagination"  v-for="(item,index) in banner" :key="index" slot="pagination" ></div>
</swiper>
<!-- 显示数字 -->
<!-- <div class="number">{{imgIndex}}/{{detailimages.length}}</div> -->
</template>

<script>
import { Swiper, SwiperSlide, directive } from 'vue-awesome-swiper'
/* require styles */
// import 'swiper/dist/css/swiper.css'
import 'swiper/swiper-bundle.css'

  export default {
    components: {
      Swiper,
      SwiperSlide
    },
    directives: {
      Swiper: directive
    },
    data() {
      return {
        banner:[
          'http://192.168.0.102/image/home/wya1.jpg',
          'http://192.168.0.102/image/home/wya2.jpg',
          'http://192.168.0.102/image/home/wya3.jpg'
        ],
        imgIndex: 1,
        swiperOption: {
          //是一个组件自有属性，如果notNextTick设置为true，组件则不会通过NextTick来实例化swiper，也就意味着你可以在第一时间获取到swiper对象，假如你需要刚加载遍使用获取swiper对象来做什么事，那么这个属性一定要是true
          notNextTick: true,
          //循环
          loop: true,
          //设定初始化时slide的索引
          initialSlide: 0,
          //自动播放
          autoplay: {
            delay: 1500,
            stopOnLastSlide: false,
            /* 触摸滑动后是否继续轮播 */
            disableOnInteraction: false
          },
          //滑动速度
          speed: 800,
          //滑动方向
          direction: "horizontal",
          //小手掌抓取滑动
          grabCursor: true,
          on: {
            //滑动之后回调函数
            slideChangeTransitionStart: function() {
              /* realIndex为滚动到当前的slide索引值 */
              this.imgIndex= this.realIndex - 1;
            },
          },
          //分页器设置
          pagination: {
            el: ".swiper-pagination",
            clickable: true,
            type: "bullets"
          }
        }
      };
    },
    /* 这里我是在使用接口请求后，对返回的数据进行判断 */
    created() {
      console.log('created')

      // this.banner = [
      //   'http://192.168.0.102/image/home/wya1.jpg',
      //   'http://192.168.0.102/image/home/wya2.jpg',
      //   'http://192.168.0.102/image/home/wya3.jpg'
      // ]
      // this.$Request({
      //   url: '',
      //   method: 'get',
      //   success: res => {
      //     this.swiperOption.autoplay = res.result.data.length != 1 ? {
      //       delay: 1500,
      //       stopOnLastSlide: false,
      //       disableOnInteraction: false
      //       } : false;
      //   }
      // })
    }
    
  }
</script>