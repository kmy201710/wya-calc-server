// https://blog.csdn.net/panchang199266/article/details/90146819?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control
<template>
  <swiper :options="swiperOption" class="swiper-wrap"  ref="mySwiper" v-if="swiperList.length!=0">
    <swiper-slide v-for="(item,index) in swiperList" :key="index" >
      <img width="100%" height="100px" :src="item.imgUrl" alt="" />

    </swiper-slide>
    <!-- 常见的小圆点 -->
    <div class="swiper-pagination"  v-for="(item,index) in swiperList" :key="index" slot="pagination" ></div>

    <!-- 显示数字 -->
    <!-- <div class="number">{{imgIndex}} {{swiperList.length}}</div> -->
  </swiper>
</template>

<script>
import { Swiper, SwiperSlide, directive } from 'vue-awesome-swiper'
/* require styles */
// import 'swiper/dist/css/swiper.css'
import 'swiper/swiper-bundle.css'

  export default {
    name: "homeSwiper",
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
          },
          {
              id: '0003',
              imgUrl: 'http://192.168.0.101/image/home/wya1.jpg'
          }
        ],
        swiperOption: {
          //是一个组件自有属性，如果notNextTick设置为true，组件则不会通过NextTick来实例化swiper，也就意味着你可以在第一时间获取到swiper对象，假如你需要刚加载遍使用获取swiper对象来做什么事，那么这个属性一定要是true
          notNextTick: true,
          //循环
          loop: true,
          //设定初始化时slide的索引
          initialSlide: 0,
          //自动播放
          autoplay: 1500,
          //滑动速度
          speed: 800,
          //滑动方向
          direction: "horizontal",
          //小手掌抓取滑动
          grabCursor: true,
          on: {
            slideChangeTransitionStart: function() {
                this.imgIndex = this.realIndex + 1;
            }
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

    }
  }
</script>


<style>
  /* .swiper-wrap{
    width: 100%;
    height: calc(100% - 16px);
  } */
</style>

