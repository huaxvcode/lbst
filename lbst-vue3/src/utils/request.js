import axios from "axios";
import router from '../router'
import { ElMessageBox } from "element-plus";

const baseUrl = "http://localhost:8090"

const req = axios.create({
    baseURL: baseUrl,
    timeout: 10000,
})

// 请求拦截器
req.interceptors.request.use(
    config => {
        const tokenName = localStorage.getItem("tokenName");
        const tokenValue = localStorage.getItem("tokenValue");
        config.headers[tokenName] = tokenValue;
        return config
    },
    error => {
        return Promise.reject(error);
    }
)

// // 响应拦截器
// req.interceptors.response.use(
//     resp => { return resp; }
// );

export default req;
