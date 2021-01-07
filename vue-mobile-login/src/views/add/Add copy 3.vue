<template>
  <div style="padding:10px 5px;">
    <Dialog @click="dialogVisible = true">点击打开 Dialog</Dialog>
    <van-divider></van-divider>
  </div>
</template>

<script>
  import Dialog from '../../public/Dialog'

  export default {
    name: 'add',
    components:{
      addEdit
    },
    data() {
      return {
        
      }
    },
    /* 这里我是在使用接口请求后，对返回的数据进行判断 */
    created(){
      console.log('list');

    },
  }
</script>