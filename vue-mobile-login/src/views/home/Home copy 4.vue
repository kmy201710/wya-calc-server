<template>
  <div style="padding: 20px;">
    <!-- <div class="title">列表：总计 {{ pageInfo.total }}</div> -->
    <div class="title">列表：总计</div>
    <van-divider></van-divider>
    <div class="jumbotron">
      <h1>这个是路由对应的页面,下面就是一个表格组件</h1>
      <table-com/>
    </div>
    </div>
  </div>
</template>
<script>
    import table from '@/components/home_table.vue'
    export default {
      name: 'Home',
      data() {
        return {
          
        }
      },
      components: {
        tableCom: table
      }
    }
</script>