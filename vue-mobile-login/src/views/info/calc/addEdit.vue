// https://blog.csdn.net/weixin_36810906/article/details/87877753
// https://blog.csdn.net/qq_37248504/article/details/110733985
<template>
  <el-dialog :title=" !form.id  ? '练 习' : '修 改' "
             width="90%"
             :close-on-click-modal="false"
             :append-to-body="true"
             :visible.sync="visible">
    <el-form ref="form"
             :model="form"
             label-width="80px">
      <el-form-item label="经验值：">
        <span style="color:#4078c0;">90 / 100 (1级)</span>
      </el-form-item>
      <el-form-item label="类型：">
        <el-select v-model="form.type"
                   placeholder="请选择类型"
                   @change="getSelected">
                   <!-- :disabled="true" -->
          <el-option label="简单（问）"
                     value="0"></el-option>
          <el-option label="中等（问）"
                     value="1"></el-option>
          <el-option label="困难（问）"
                     value="2"></el-option>
          <el-option label="自定义（解）"
                     value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="表达式：">
        <el-input v-model="form.content"></el-input>
      </el-form-item>
      <el-form-item label="提示：">
        <el-input v-model="form.calculations"></el-input>
        <!-- <el-input v-model="form.submitText"></el-input> -->
      </el-form-item>
      <el-form-item label="结果：">
        <el-input v-model="form.submitText"></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer"
         class="dialog-footer">
      <el-button @click="skipForm()">跳 过</el-button>
      <el-button type="primary"
                 @click="submitForm()">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
  // 导入共用组件
  import { Toast } from 'vant'
  import { resTrue } from "@/utils/commons"
  import { next, compute, saveCalcAdv } from "@/api/calc"

  export default {
    name: 'baseform',
    data: function () {
      return {
        shopId: this.$store.state.user.shop,
        userId: this.$store.state.user.id,
        userName: this.$store.state.user.name,
        visible: false,
        form: {
          shopId: '',
          userId: '',
          userName: '',
          id: '',
          type: '',
          nums: '',
          content: '',
          calculations: '',
          submitText: '',
          // id: '',
          // taskcode: '',
          // taskname: '',
          // url: '',
          // corn: '',
          // method: ''
        }
      }
    },
    methods: {
      // 编辑和弹出的初始化页面
      init (id) {
        // debugger
        this.form.id = id || ''
        // 显示弹出层
        this.visible = true
        // 编辑的时候回显
        this.$nextTick(() => {
          Object.keys(this.form).forEach(key => (this.form[key] = ''));
        })
      },
    
      // post请求数据
      loadData() {
        if (this.form.type === '' || this.form.type === '3') {
          return
        }
        let params = {
          size: 1,
          tag: this.form.type
        }
        next(params)
          .then(res => {
            if (resTrue(res) === 1) {
              this.form = res.data[0]
            }
          })
          .catch(err => {
            console.log(err);
          })
      },
      getSelected () {
        setTimeout(() => {
          this.loadData()
        }, 100);
      },
      skipForm () {
        // this.visible = false
        setTimeout(() => {
          this.loadData()
        }, 100);
      },
      submitForm () {
        console.log(this.form)
        if (this.form.type !== '3') {
          if (this.form.calculations === this.form.submitText) {
            this.$message({ type: "success", message: "回答正确" });
          } else {
            this.$message({ type: "error", message: "回答错误" });
          }
          this.save(this.form)
        } else {
          compute(this.form)
            .then(res => {
              if (resTrue(res) === 1) {
                this.form = res.data
                this.save(this.form)
              }
            })
            .catch(err => {
              console.log(err);
            })
        }
      },
      save (params) {
        let calcType = params.type
        params.shopId = this.shopId
        params.userId = this.userId
        params.userName = this.userName
        setTimeout(() => {
          saveCalcAdv(params)
            .then(res => {
              if (resTrue(res) === 1) {
                Object.keys(this.form).forEach(key => (this.form[key] = ''));
                this.form.type = calcType
                this.loadData()
              }
            })
            .catch(err => {
              console.log(err);
            })
        }, 1500);

      }
    }
  }
</script>