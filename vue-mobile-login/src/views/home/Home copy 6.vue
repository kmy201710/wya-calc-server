<template>
  <div style="padding:15px 10px;">
    <div class="header">管理中心</div>
    <info></info>
  </div>
</template>

<script>
  // import info from '../info/user'
  import info from '../info/management'
  
  export default {
    name: 'home',
    components:{
      info
    }
  }
</script>

<style>
  .header{
    position: fixed;
    padding: 10px 10px;
    top: 0;
    left: 0;
    right: 0;
    color: rgb(55, 155, 155)
  }
</style>