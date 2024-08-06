import axios from "axios";
import router from '../router'
import {ElMessageBox} from "element-plus";

const baseUrl = "http://localhost:8090"

const instance = axios.create({
    baseURL: baseUrl,
    timeout: 10000,
})

// 请求拦截器
instance.interceptors.request.use(
    config => {
        const tokenName = localStorage.getItem("tokenName");
        const tokenValue = localStorage.getItem("tokenValue");
        if (tokenName && tokenValue) {
            config.headers[tokenName] = tokenValue;
        }
        return config
    },
    error => {
        return Promise.reject(error);
    }
)

// 响应拦截器
instance.interceptors.response.use(
    response => {
        if (response.status === 200) {
            if (response.data.code === 501) {
                localStorage.removeItem("tokenName");
                localStorage.removeItem("tokenValue");

                ElMessageBox.alert(response.data.message, '提示', {
                    confirmButtonText: '确定',
                    showClose: false
                }).then(() => {
                    router.push('/login');
                });
            }
        }
        return response; // 确保返回响应数据
    },
    error => {
        return Promise.reject(error);
    }
);

export default instance;