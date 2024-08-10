import axios from '../utils/request.js'

export async function captcha() {
    const resp = await axios.get('/captcha-img', {
        responseType: 'blob',
    });

    const data = {
        url: URL.createObjectURL(resp.data),
        key: resp.headers['captcha-key']
    }

    console.log(data);

    return data;
}

export async function checkCaptcha(data) {
    const resp = await axios.post('/check-captcha', data);
    return resp.data.code == 200;
}