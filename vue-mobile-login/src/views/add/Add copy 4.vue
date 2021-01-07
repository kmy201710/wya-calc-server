<template>
  <div style="padding:10px 5px;">
    <add-or-update v-if="dialogFormVisible" ref="addOrUpdate">
    </add-or-update>
    <van-divider></van-divider>
  </div>
</template>

<script>
  import AddOrUpdate from './addEdit'

  export default {
    name: 'add',
    components:{
      AddOrUpdate
    },
    data() {
      return {
        isShow: true
      }
    },
    /* 这里我是在使用接口请求后，对返回的数据进行判断 */
    created(){
      console.log(this.$refs);
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init()
      })
    },
    methods: {
      openConfirm() {

      }
    }
  }
</script>