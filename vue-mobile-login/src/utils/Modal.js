import Modal from '@/components/Modal.vue'
Modal.install=function(Vue){
  Vue.component(Modal.name,Modal)
}
export default Modal