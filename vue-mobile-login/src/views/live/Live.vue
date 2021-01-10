<template>
  <van-tabs
          v-model="active"
          animated
          swipeable
          color="#1296db"
  >
    <van-tab title="答题">
     <calcEdit></calcEdit>
    </van-tab>
    <van-tab title="任务">
      <Notify></Notify>
    </van-tab>
    <van-tab title="经验值">
      <Notify></Notify>
    </van-tab>
  </van-tabs>
</template>

<script>
  import calcEdit from "@/views/info/calcEdit";
  import Notify from "@/views/news/Notify";
  export default {
    name: "index",
    data() {
      return {
        active: 0
      };
    },
    components: {
      calcEdit,
      Notify
    }
  }
</script>

<style scoped>

</style>
