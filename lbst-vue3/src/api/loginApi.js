import axios from '../utils/request.js'

// 登录
export function login(data) {
    return axios.post('/login', data)
}

// 登出
export function logout() {
    return axios.get('/logout')
}

// 注册
export function reg(data) {
    return axios.post('/reg', data)
}

// 判断当前是否处于登录状态
export function checkLoginStatus() {
    return axios.get('/check-login-status');
}