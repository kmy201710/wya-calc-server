<template>
  <van-tabs
          v-model="active"
          animated
          swipeable
          color="#1296db"
  >
    <van-tab title="问答">
     <calcAdv></calcAdv>
    </van-tab>
    <van-tab title="任务">
      <Notify></Notify>
    </van-tab>
    <van-tab title="排行榜">
      <Notify></Notify>
    </van-tab>
  </van-tabs>
</template>

<script>
  import calcAdv from "@/views/info/calc/calcAdv";
  import Notify from "@/views/news/Notify";
  export default {
    name: "index",
    data() {
      return {
        active: 0
      };
    },
    components: {
      calcAdv,
      Notify
    }
  }
</script>

<style scoped>

</style>
