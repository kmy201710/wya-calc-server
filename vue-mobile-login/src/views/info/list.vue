<template>
  <div class="vant-table">
    <table cellspacing="0" class="table">
      <tr>
        <th class="th" v-for="(item, index) in option.column" :key="index">{{ item.label }}</th>
      </tr>
      <tr v-for="(item, index) in tableData" :key="index" class="list-tr">
        <td v-for="(context, i) in option.column" :key="i">{{ item[context.prop] }}</td>
      </tr>
    </table>
  </div>
</template>
<script>
  export default {
    name: 'list',
    data(){
      return{
        tableData: [
              {
                closedAccount: 40,
                givePrice: 30,
                noAccount: 20,
                roomType: "BOSS卡座",
                totalPrice: 10
              },
              {
                closedAccount: 110,
                givePrice: 220,
                noAccount: 330,
                roomType: "BOSS卡座",
                totalPrice: 440
              },
              {
                closedAccount: 0,
                givePrice: 0,
                noAccount: 0,
                roomType: "BOSS卡座",
                totalPrice: 0
              },
              {
                closedAccount: 0,
                givePrice: 0,
                noAccount: 0,
                roomType: "BOSS卡座",
                totalPrice: 0
              },
            ],
        //th
        option: {
              column: [
                {
                  label: '房台类型',
                  prop: 'roomType'
                },
                {
                  label: '已结',
                  prop: 'closedAccount'
                },
                {
                  label: '未收款',
                  prop: 'noAccount'
                },
                {
                  label: '赠送',
                  prop: 'givePrice'
                },
                {
                  label: '合计',
                  prop: 'totalPrice'
                }
              ]
            }
      }
    },
    created() {}
};
</script>

<style lang="less" scoped>
.vant-table {
  .table {
      background-color: rgb(22, 255, 158); 
      width:100%;
      border-radius: .185185rem;
    .th {
      height: 1.074074rem;
      line-height: 1.074074rem;
      background-color: rgb(255, 102, 102);
      text-align: center;
      border-top-left-radius: .185185rem;
      border-top-right-radius: .185185rem;
    }
    .list-tr {
      height: 1.074074rem;
      line-height: 1.074074rem;
    }
    .list-tr:nth-child(2n) {
      background-color: rgb(55, 129, 37);
    }
  }
}
</style>