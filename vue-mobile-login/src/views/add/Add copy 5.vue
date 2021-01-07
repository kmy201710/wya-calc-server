<template>

<Button @click="success">Display success prompt</Button>
</template>
<script>

    export default {
        methods: {
            info () {
                this.$dialog.alert({
                    message: "hello 12",
                })
                // this.$message({ type: "info", message: "什么好" });
                // this.$message({ type: "success", message: "什么好" });
                // this.$message({ type: "warning", message: "什么好" });
                // this.$message({ type: "error", message: "什么好" });
            },
            success () {
                this.$dialog.alert({
                    title: "Hello World!12", //加上标题
                    message: "hello world", //改变弹出框的内容
                    showCancelButton: true //展示取水按钮
                })
                .then(() => { //点击确认按钮后的调用
                    console.log("点击了确认按钮噢")
                })
                .catch(() => { //点击取消按钮后的调用
                    console.log("点击了取消按钮噢")
                })
                // this.$Message.success('This is a success tip');
            },
            warning () {
                this.$Message.warning('This is a warning tip');
            },
            error () {
                this.$Message.error('This is an error tip');
            }
        }
    }
</script>
