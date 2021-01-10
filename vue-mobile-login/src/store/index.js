import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const state = {
  tabbarShow: true,
  // token
  token: localStorage.getItem("token"),
  phone: localStorage.getItem("phone"),
  
  user: {
    id: localStorage.getItem("id"),
    name: localStorage.getItem("name"),
    shop: localStorage.getItem("shop"),
    role: localStorage.getItem("role"),
  }
};

import mutations from "@/store/mutations";
import actions from "@/store/actions";
import getters from "@/store/getters";


export default new Vuex.Store({
  state,
  mutations,
  actions,
  getters
})
