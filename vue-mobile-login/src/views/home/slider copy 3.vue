// https://blog.csdn.net/panchang199266/article/details/90146819?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control
<template>
  <swiper :options="swiperOption" class="swiper-wrap"  ref="mySwiper" v-if="swiperList.length!=0">
  <swiper-slide v-for="(item,index) in swiperList" :key="index" >
    <img width="100" height="100" :src="item.imgUrl" alt="" />

  </swiper-slide>
  <!-- 常见的小圆点 -->
  <div class="swiper-pagination"  v-for="(item,index) in swiperList" :key="index" slot="pagination" ></div>

<!-- 显示数字 -->
<div class="number">{{imgIndex}} {{swiperList.length}}</div>
</swiper>
</template>

<script>
import { Swiper, SwiperSlide, directive } from 'vue-awesome-swiper'
/* require styles */
// import 'swiper/dist/css/swiper.css'
import 'swiper/swiper-bundle.css'

  export default {
    name: "HomeSwiper",
    components: {
      Swiper,
      SwiperSlide
    },
    directives: {
      Swiper: directive
    },
    data() {
      return {
        imgIndex: 1,
        // 轮播图数据源   
        swiperList: [
          {
              id: '0001',
              imgUrl: 'http://mp-piao-admincp.qunarzz.com/mp_piao_admin_mp_piao_admin/admin/20195/dc11f0bb94057224b104a2017f313e21.jpg_750x200_feee379d.jpg'
          },
          {
              id: '0002',
              imgUrl: 'http://mp-piao-admincp.qunarzz.com/mp_piao_admin_mp_piao_admin/admin/20195/64beebd2f4e2447db0891e8b890b5a07.png_750x200_ab33275f.png'
          }
        ],
        swiperOption: {
          //配置项：出现轮播图下面的滑动点   
          pagination: '.swiper-pagination',
          // 循环播放  
          loop: true,
          // 自动轮播，单位ms   
          autoplay: 5000,
          on: {
            slideChangeTransitionStart: function() {
                this.imgIndex = this.realIndex + 1;
            }
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