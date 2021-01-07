<template>
  <div id="list">
    <ul>
      <li v-for="(data,index) in listData" :key="index" @click="toRouter(data.url)">
        <img class="img" :src="preUrl + data.icon">
        <div class="content vux-1px-b">
          <span class="title">{{data.title}}</span>
          <span class="arr-r"></span>
          <span v-if="data.num" class="tg-n">{{data.num}}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
  import { preUrl } from "@/utils/commons"
  export default {
    name: "list",
    props:['listData'],
    data() {
      return {
        preUrl: preUrl,
      }
    },
    methods: {
      toRouter(url) {
        this.$router.push(url);
      }
    }
  };
</script>
<style lang="less" scoped>
#list {
  ul {
    padding: 0 .24rem;
    li {
      height: 1rem;
      display: flex;
      display: -webkit-flex;
      line-height: 1rem;
      .img {
        width: 0.44rem;
        height: 0.44rem;
        margin: 0.28rem 0;
        overflow: hidden;
      }
      .content {
        width: 100%;
        margin-left: 0.32rem;
        .title {
          display: inline-block;
          line-height: 1rem;
          text-decoration: none;
        }
        .tg-n {
          float: right;
          display: flow-root;
          width: .34rem;
          height: .34rem;
          color: #fff;
          background-color: red;
          font-size: .24rem;
          border-radius: 50%;
          text-align: center;
          margin: .29rem .1rem 0 0;
          line-height: 0.4rem;
        }
        .arr-r {
          width: 0.2rem;
          height: 0.2rem;
          display: inline-block;
          float: right;
          border-right: 0.04rem solid #bbb;
          border-top: 0.04rem solid #bbb;
          margin: 0.4rem 0.2rem 0 0;
          transform: rotate(45deg);
          -ms-transform: rotate(45deg);
          -moz-transform: rotate(45deg);
          -webkit-transform: rotate(45deg);
          -o-transform: rotate(45deg);
        }
      }
    }
  }
}
</style>
