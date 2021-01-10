import router from "@/router";
import {Toast} from "vant";

export default {
  /**
   * 退出登录
   */
  logout(state){
    state.user = null;
    localStorage.removeItem('id');
    localStorage.removeItem('name');

    state.token = null;
    // 清除token
    localStorage.removeItem('token');
    // 跳转到登录页面
    router.replace('/')
        .catch(err => console.warn(err));
    Toast('退出成功')
  },

  /**
   * 登录
   * @param state
   * @param token
   */
  login(state, data){
    state.user.id = data.user.id;
    state.user.name = data.user.name;
    state.user.shop = data.user.shopId;
    state.user.role = data.user.roleId;
    localStorage.setItem('id', data.user.id);
    localStorage.setItem('name', data.user.name);
    localStorage.setItem('shop', data.user.shopId);
    localStorage.setItem('role', data.user.roleId);

    state.token = data.token;
    // 切换tabbar为显示状态
    this.commit('toggleTabbarShow', true);
    // 保存token到localStorage
    localStorage.setItem('token', data.token);
    // 保存refreshToken到localStorage
    localStorage.setItem('refreshToken', data.token);
    // 跳转到首页
    router.replace('/')
        .catch(err => console.warn(err));
    Toast('登录成功');
  },

  /**
   * 切换tabbar显示
   * @param state
   * @param booleanVal
   */
  toggleTabbarShow(state, booleanVal){
    state.tabbarShow = booleanVal
  },

  /**
   * 更新手机号
   * @param state
   * @param phone
   */
  updatePhone(state, phone){
    state.phone = phone;
    localStorage.setItem("phone", phone)
  },

  /**
   * 更新token
   * @param state
   * @param token
   */
  updateToken(state, data){
    state.token = data.token;
    localStorage.setItem("token", data.token)
    localStorage.setItem("refreshToken", data.refreshToken)
  },

  /**
   *
   * @param state
   * @param token
   * @param refreshToken
   */
  deleteToken(state){
    state.token = null;
    localStorage.removeItem("token")
    localStorage.removeItem("refreshToken")
  },
  
  /**
   * 更新
   * @param state
   * @param data
   */
  updateUser(state, data){
    state.user.shop = data.shop;
    state.user.role = data.role;
    localStorage.setItem('shop', data.shop);
    localStorage.setItem('role', data.role);
  },
  /**
   * 更新
   * @param state
   * @param data
   */
  updateShop(state, data){
    // if (this.shop === undefined) {
    //   state.user.shop = 1;
    //   localStorage.setItem('shop', 1);
    // } else {
    //   state.user.shop = data.shop;
    //   localStorage.setItem('shop', data.shop);
    // }
    // state.user = data;
    // localStorage.setItem('user', data);
  }
};

