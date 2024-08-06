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