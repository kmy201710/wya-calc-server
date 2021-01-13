function isPhone(phone){
  let reg = /^[1][34578]\d{9}$/;
  return reg.test(phone)
}

function isCode(code) {
  return code.length === 4;
}
// https://segmentfault.com/a/1190000015842187
// https://blog.51cto.com/longlei/2142957
// 定义一些公共的属性和方法
// const preUrl = 'http://localhost'

const preUrl = 'http://8.136.103.138:80'
// const preUrl = 'http://8.136.103.138:81'
// const preUrl = 'http://192.168.0.101'
// const preUrl = 'http://192.168.0.102'


function commonFun() {
  console.log("公共方法")
}

function resTrue(res) {
  console.log(res);
  if(res.code === '10000'){
    return 1;
  }
  return 0;
}

// 暴露出这些属性和方法
export {
    isPhone,
    isCode,
    preUrl,
    commonFun,
    resTrue
}