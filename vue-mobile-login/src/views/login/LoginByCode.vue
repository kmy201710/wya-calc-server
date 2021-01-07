<template>
  <div>
    <nav-bar/>
    <section style="padding: 30px 30px; display: flex; flex-flow: column nowrap;">
      <div style="font-size: 20px; font-weight: bold">请输入验证码</div>
      <!-- <div>验证码已通过短信发送至&nbsp;{{ tel }}</div> -->
      <div>验证码提示&nbsp;{{ tips }}</div>
      <div>
        <van-cell-group>
          <van-field
                  v-model="sms"
                  center
                  clearable
                  placeholder="请输入验证码"
                  style="background-color: WhiteSmoke"
                  ref="code"
          >
            <van-button slot="button" size="small" type="default" :disabled="disabled" @click="sendCode">{{ text }}</van-button>
          </van-field>
          <!-- <van-dropdown-menu>
            <van-dropdown-item 
              v-model="value"
              :title="title"
              :options="developList"
              @change="changeDevelop"
            />
          </van-dropdown-menu> -->
        </van-cell-group>
        <span style="color: red" v-show="errMsg">{{ errMsg }}</span>
      </div>
      <div style="background-color: DarkGray">
        <van-button :type="type" block :disabled="loginDisabled" @click="login">登录</van-button>
      </div>
    </section>
  </div>
</template>

<script>
  import NavBar from "@/components/NavBar";
  import { sendLoginCode, loginByCode } from "@/api";
  import { resTrue } from '@/utils/commons'

  export default {
    name: "LoginByCode",
    components: {NavBar},
    watch: {
      sms(newVal, oldVal){
        this.sms = this.sms.replace(/\s+/g, '').replace(/\D/g, '');
        // if (isNaN(newVal) || newVal.length > 4) {
        //   this.sms = oldVal;
        // }
        // if(isCode(newVal)){
        if(newVal === this.calculations){
          this.login();
          this.type = 'danger';
          this.loginDisabled = false
        }else{
          this.type = 'default';
          this.loginDisabled = 'disabled'
        }
      }
    },
    mounted(){
      this.$refs.code.focus()
    },
    data(){
      return {
        tel: this.$store.state.phone,
        shop: this.$store.state.user.shop,
        role: this.$store.state.user.role,
        sms: '',
        text: '发送验证码',
        disabled: false,
        loginDisabled: 'disabled',
        type: 'default',
        errMsg: '',
        tips: '',
        calculations: '',
      }
    },
    methods: {
      // 验证码登录
      login(){
        let params = {
          phoneNo: this.tel,
          shopId : this.shop,
          roleId : this.role,
        };
        loginByCode(params)
          .then(res => {
            if (resTrue(res) === 1) {
              this.$store.commit('login', res.data);
            } else {
              this.errMsg = res.message;
              // 密码输入错误时重新聚焦输入框
              this.$refs.code.focus();
            }
          })
      },
      // 发送验证码
      sendCode(){
        let params = {
          phone: this.tel,
        };
        sendLoginCode(params)
          .then(res => {
            this.$message({ type: "info", message: res.data.calculations });
            if (resTrue(res) === 1) {
              this.tips = res.data.content
              this.calculations = res.data.calculations
              this.disabled = 'disabled';
              this.timer()
            }
        });
      },
      // 60s计时器
      timer(){
        let i = 60;
        this.text = i;
        let timerId = setInterval(() => {
          if(i === 0){
            clearInterval(timerId);
            this.text = "重新发送";
            this.disabled = false;
            return
          }
          this.text = --i;
        }, 1000);
      },
      onCancel() {
        this.show = false;
        this.$store.commit('updateRole', '50');
      },
      onSelect(item) {
        this.show = false;
        console.log(item);
      },
    },
    created(){
      this.sendCode();
    }
  }
</script>

<style scoped>
  section div{
    margin-bottom: 16px;
  }
</style>
