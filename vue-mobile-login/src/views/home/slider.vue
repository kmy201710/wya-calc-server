<template>
  <!-- <el-carousel :interval="3000" type="card" arrow="always" height="80px"> -->
  <el-carousel :interval="3000" arrow="always" height="100px">
    <el-carousel-item v-for="(item,index) in swiperList" :key="index">
      <img :src="item.imgUrl" alt="" />
    </el-carousel-item>
  </el-carousel>
</template>

<script>
  export default {
    name: "homeSwiper",
    props:['swiperList'],
    data() {
      return {
        // 轮播图数据源   
        // swiperList: [
        //   {
        //       id: '0001',
        //       imgUrl: 'http://mp-piao-admincp.qunarzz.com/mp_piao_admin_mp_piao_admin/admin/20195/dc11f0bb94057224b104a2017f313e21.jpg_750x200_feee379d.jpg'
        //   },
        //   {
        //       id: '0002',
        //       imgUrl: 'http://mp-piao-admincp.qunarzz.com/mp_piao_admin_mp_piao_admin/admin/20195/64beebd2f4e2447db0891e8b890b5a07.png_750x200_ab33275f.png'
        //   },
        //   {
        //       id: '0003',
        //       imgUrl: 'http://192.168.0.102/image/home/wya.jpg'
        //   }
        // ],
      }
    }
  }
        
  
</script>

<style>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #ce4148;
  }
  .el-carousel__item img{
      position: absolute;
      top: 0;
      width: 100%;
      height: 100%;
  }
</style>