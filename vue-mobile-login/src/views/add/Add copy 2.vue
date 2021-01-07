<template>
  <div style="padding:10px 5px;">
    <confirm :isShowConfirm="isShow" ref="myConfirm"></confirm>
    <van-divider></van-divider>
  </div>
</template>

<script>
  import Confirm from './Confirm'

  export default {
    name: 'add',
    components:{
      Confirm
    },
    data() {
      return {
        isShow: true
      }
    },
    /* 这里我是在使用接口请求后，对返回的数据进行判断 */
    created(){
      console.log('myConfirm');
      console.log(this);
      console.log(this.$refs);
      console.log(this.$refs.Confirm);
      
      // this.$refs.myConfirm.show('注册成功！', {
      //       confirmText: '确定',
      //       titleText: '消息提示',
      //       data: '我是外界传进来的数据'
      //   })
    },
    methods: {
      openConfirm() {
        // let _this = this;
        // _this.ConfirmLayer("确认要跳转吗？", function(flag) {
        //   if (flag) {
        //     console.log('确认');
        //   } else {
        //     console.log('取消');
        //   }
        // })
      }
    }
  }
</script>