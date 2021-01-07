<template>
  <div style="padding:10px 5px;">
    <marqueeUp></marqueeUp>
    <slider></slider>
    <van-divider></van-divider>
    <manager></manager>
    <van-divider></van-divider>
  </div>
</template>

<script>
  import marqueeUp from './marqueeUp'
  import slider from './slider'
  import manager from "./manager";

  export default {
    name: 'home',
    components:{
      marqueeUp, slider, manager
    },
    data() {
      return {

      }

    }
  }
</script>