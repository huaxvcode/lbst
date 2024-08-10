<template>
  <div class="login-view">
    <el-card style="width: 400px; height: 550px; border-radius: 10px; padding: 30px;" shadow="hover">
      <h1 class="title">图书管理系统</h1>
      <el-form :rules="rules" :model="data" status-icon label-width="auto">
        <el-form-item prop="username" style="padding-bottom: 15px;">
          <el-input v-model="data.username" placeholder="用户名" style="height: 45px; font-size: 18px"
            autocomplete="off" />
        </el-form-item>
        <el-form-item prop="password" style="padding-bottom: 20px;">
          <el-input v-model="data.password" placeholder="密码" style="height: 45px; font-size: 15px" show-password
            type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item prop="code" style="padding-bottom: 15px;">
          <div class="captcha">
            <el-input v-model="data.code" placeholder="验证码" style="height: 45px; font-size: 18px; margin-right: 5px;"
              autocomplete="off" />
            <el-image style="height: 45px; width: 160px;" :src="captchaUrl" :fit="fit" @click="changeCaptcha" />
          </div>
        </el-form-item>
        <el-form-item style="padding-bottom: 10px;">
          <el-button type="primary" @click="loginButton" style="width: 100%; height: 38px;">登录
          </el-button>
        </el-form-item>
        <el-form-item style="padding-bottom: 15px;">
          <div class="button">
            <el-button type="danger" @click="regButton" style="width: 100px;">注册
            </el-button>
            <el-button @click="clear" style="width: 100px;">清空
            </el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-card>

  </div>
</template>

<script setup>
import { reactive, ref, onMounted, onUnmounted } from "vue";
import { isUsernameValid, isPasswordValid, isCaptchaValid } from "../../utils/uservalid.js";
import { captcha, checkCaptcha } from "../../api/captchaApi.js";
import { ElMessage, ElMessageBox } from "element-plus";
import { login, reg } from "../../api/loginApi.js";
import router from "../../router.js";


const data = reactive({
  username: '',
  password: '',
  key: '',
  code: ''
});

const captchaUrl = ref();

/**
 * 登录状态，0->默认值、1->用户名或密码错误、2->登录成功
 * @type {Ref<any>}
 */
const loginStatus = ref();

// 检查用户名格式
const checkUsername = (rule, usersname, callback) => {
  if (!usersname) {
    return callback(new Error('请输入用户名'))
  }
  setTimeout(() => {
    if (isUsernameValid(usersname)) {
      if (loginStatus.value === 1) {
        callback(new Error('用户名或密码错误'))
      } else callback()
    } else {
      callback(new Error('用户名只能由大小写字母、数字、下划线组成，长度在 3~20'));
    }
  }, 500)
}

// 检查密码格式
const checkPassword = (rule, password, callback) => {
  if (!password) {
    return callback(new Error('请输入密码'))
  }
  setTimeout(() => {
    if (isPasswordValid(password)) {
      if (loginStatus.value === 1) {
        callback(new Error('用户名或密码错误'))
      } else callback()
    } else {
      callback(new Error('密码只能由大小写字母、数字、下划线、@$!%*?&#组成，长度在 3~20'));
    }
  }, 500)
}

// 检查验证码格式
const checkCode = (rule, code, callback) => {
  if (!code) {
    return callback(new Error('请输入验证码'))
  }
  setTimeout(async () => {
    if (isCaptchaValid(code)) {
      let t = await checkCaptcha({ key: data.key, code: data.code });
      if (t) {
        callback()
      } else {
        callback(new Error('验证码错误'))
      }

    } else {
      callback(new Error('验证码错误'));
    }
  }, 500)
}

const rules = reactive({
  username: [{ validator: checkUsername, trigger: 'blur' }],
  password: [{ validator: checkPassword, trigger: 'blur' }],
  code: [{ validator: checkCode, trigger: 'blur' }],
})

onMounted(async () => {
  const cpt = await captcha();
  captchaUrl.value = cpt.url;
  data.key = cpt.key
})

onUnmounted(() => {
  if (captchaUrl.value) {
    URL.revokeObjectURL(captchaUrl.value)
  }
})

const changeCaptcha = async () => {
  if (captchaUrl.value) {
    URL.revokeObjectURL(captchaUrl.value)
  }
  const cpt = await captcha();
  captchaUrl.value = cpt.url;
  data.key = cpt.key
}

const clear = () => {
  data.key = null;
  data.code = null;
  data.username = null;
  data.password = null;
}

const alertMessage = (title, content, type, reCaptcha = true) => {
  ElMessageBox.confirm(
    content,
    title,
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      showCancelButton: false,
      type: type,
      center: true,
    }
  )
  if (reCaptcha) {
    changeCaptcha()
  }
}

const checkInfoAndAlert = () => {
  if (!data.key || !data.code) {
    alertMessage('警告', '请输入验证码', 'warning', false)
    return false
  } else if (!data.username || !data.password) {
    alertMessage('警告', '请输入用户名或密码', 'warning', false)
    return false
  } else if (!isUsernameValid(data.username) || !isPasswordValid(data.password)) {
    alertMessage('警告', '用户名或密码的格式不正确', 'warning', false)
    return false
  } else {
    return true
  }
}

const loginButton = () => {
  if (checkInfoAndAlert()) {
    login(data).then(res => res.data).then(res => {
      if (res.data.status == 0 || res.data.status == 3) {
        loginStatus.value = 2; // 登录成功
        localStorage.setItem('tokenName', res.data.token.tokenName);
        localStorage.setItem('tokenValue', res.data.token.tokenValue);
        localStorage.setItem('userId', res.data.token.loginId);
        router.push('/') // 登录成功后去到主页
      } else if (res.data.status == 2) {
        loginStatus.value = 1;
        alertMessage('警告', '用户名或密码错误，请重新输入', 'warning')
      } else if (res.data.status == 6) {
        alertMessage('警告', '验证码错误', 'warning')
      } else {
        alertMessage('警告', '登录失败', 'warning')
      }
    })
  }
}

const regButton = () => {
  if (checkInfoAndAlert()) {
    reg(data).then(res => res.data).then(res => {
      if (res.data == 0) {
        alertMessage('提示', '注册成功，请去登录', 'success')
      } else if (res.data == 1) {
        alertMessage('警告', '用户名已被占用', 'warning')
      } else if (res.data == 6) {
        alertMessage('警告', '验证码错误', 'warning')
      } else {
        alertMessage('警告', '注册失败', 'warning')
      }
    })
  }
}
</script>

<style scoped>
.login-view {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.title {
  text-align: center;
  margin-bottom: 30px;
  color: cornflowerblue;
}

.captcha {
  display: flex;
  width: 100%;
  height: 100%;
}

.button {
  display: flex;
  width: 100%;
  justify-content: space-between;
}
</style>