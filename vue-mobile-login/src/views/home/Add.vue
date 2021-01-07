<template>
  <van-button @click="info">Display success prompt</van-button>
</template>

<script>
  export default {
    name: 'add',
    /* 这里我是在使用接口请求后，对返回的数据进行判断 */
    created(){
      // https://blog.csdn.net/qq_42991509/article/details/103927293
      this.$message({ type: "info", message: "什么好" });
      // this.$message({ type: "success", message: "什么好" });
      // this.$message({ type: "warning", message: "什么好" });
      // this.$message({ type: "error", message: "什么好" });

      // https://www.cnblogs.com/vinieo/p/10178612.html
      this.$dialog.alert({
          message: "hello",
      })
      
      // this.$dialog.alert({
      //     title: "hello", //加上标题
      //     message: "hello world", //改变弹出框的内容
      //     showCancelButton: true //展示取水按钮
      // })
      // .then(() => { //点击确认按钮后的调用
      //     console.log("点击了确认按钮噢")
      // })
      // .catch(() => { //点击取消按钮后的调用
      //     console.log("点击了取消按钮噢")
      // })

      // this.$dialog.confirm({
      //     title: "hello",
      //     message: "hello world",
      //     confirmButtonText: "你好", //改变确认按钮上显示的文字
      //     cancelButtonText: "你坏" //改变取消按钮上显示的文字
      // }).then(()=> {
      //     console.log("点击了确认按钮噢")
      // }).catch(() => {
      //     console.log("点击了取消按钮噢")
      // })
    },
    methods: {
        info () {
          
        }
    }
  }
</script>