import http from "@/utils/http";

export const list = (params) => http.post('/app/user/pageList', params);

export const save = (params) => http.post('/app/user/save', params);
