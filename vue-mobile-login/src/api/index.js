import http from "@/utils/http";

// 发送登录验证码
export const sendLoginCode = (params) => http.get('/app/login/code', params);
// 发送修改手机号验证码
export const sendModifyPasswordCode = (params) => http.get('/app/login/modifyPasswordCode', params);
// 验证码登录
export const loginByCode = (params) => http.post('/app/login/code', params);
// 密码登录
export const loginByPwd = (params) => http.post('/app/login/password', params);
// 密码修改
export const modifyPassword= (params) => http.post('/app/login/modifyPassword', params);

