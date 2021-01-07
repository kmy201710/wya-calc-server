// https://segmentfault.com/a/1190000021193974?utm_source=tag-newest
<template>
  <!-- 无缝滚动效果 -->
  <div class="marquee-wrap">
    <ul class="marquee-list" :class="{'animate-up': animateUp}">
      <li v-for="(item, index) in marqueeList" :key="index">{{item}}</li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: "homeMarquee",
    props:['marqueeList'],
    data() {
      return {
        animateUp: false,
        listData: [
          '12***ve 成功邀请12人 已获奖金60元', 
          'l***e 成功邀请5人 已获奖金40元', 
          '2***e 成功邀请1人 已获奖金5元',
          '3***e 成功邀请5人 已获奖金40元', 
          '4***e 成功邀请1人 已获奖金5元',
          '5***e 成功邀请5人 已获奖金40元',
          ],
        timer: null
      }
    },
    mounted() {
      this.timer = setInterval(this.scrollAnimate, 1500);
    },
    methods: {
      scrollAnimate() {
        this.animateUp = true
        setTimeout(() => {
          this.marqueeList.push(this.marqueeList[0])
          this.marqueeList.shift()
          this.animateUp = false
        }, 500)
      }
    },
    destroyed() {
      clearInterval(this.timer)
    }
  };
</script>

<style lang="less" scoped>
  .marquee-wrap  {
    width: 80%;
    height: 30px;
    border-radius: 20px;
    // background: rgba(#000000, 0.6);
    background-color: rgba(#120000, 0.3);
    margin: auto auto;
    // overflow: hidden;
  }
  .marquee-list li {
    width: 100%;
    height: 100%;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    padding: 0 10px;
    list-style: none;
    line-height: 30px;
    text-align: center;
    color: #fff;
    font-size: 18px;
    font-weight: 400;
  }
  .animate-up {
    transition: all 0.5s ease-in-out;
    transform: translateY(-30px);
  }

</style>
