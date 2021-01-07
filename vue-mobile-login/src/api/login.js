import http from "@/utils/http";

// 发送登录验证码
export const sendLoginCode = (params) => http.get('/login/code', params);
// 发送修改手机号验证码
export const sendModifyPasswordCode = (params) => http.get('/login/modifyPasswordCode', params);
// 验证码登录
export const loginByCode = (params) => http.post('/login/code', params);
// 密码登录
export const loginByPwd = (params) => http.post('/login/password', params);
// 密码修改
export const modifyPassword= (params) => http.post('/login/modifyPassword', params);


// 测试发送请求
// export const testSend = (params) => http.post('http://192.168.0.102:7080/user/pageList', params, {emulateJSON:true});

// export const home = '/home/getList';
// export const home = '/home/getList';
export const manager = (params) => http.post('/webapp/manager/getList', params);
export const user = '/user/pageList';
// export const calc = '/calc/pageList';
export const calc = (params) => http.post('/webapp/calc/pageList', params);

export const userCalc = '/userCalc/pageList';
                      
