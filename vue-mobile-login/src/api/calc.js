import http from "@/utils/http";

export const list = (params) => http.post('/app/calc/pageList', params);

export const save = (params) => http.post('/app/calc/save', params);

export const next = (params) => http.get('/app/calc/next', params);

export const compute = (params) => http.post('/app/calc/compute', params);

export const listCalcAdv = (params) => http.post('/app/calcAdv/pageList', params);

export const saveCalcAdv = (params) => http.post('/app/calcAdv/save', params);
