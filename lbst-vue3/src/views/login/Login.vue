<template>
  <div class="login-view">
    <el-card style="width: 400px; height: 500px; border-radius: 10px" shadow="hover">
      <el-form ref="data">
        <el-form-item prop="username">
          <el-input v-model="data.username"
                    placeholder="用户名"
                    autocomplete="off"/>
        </el-form-item>
      </el-form>
    </el-card>

  </div>
</template>

<script setup>
import {reactive} from "vue";
import {isUsernameValid, isPasswordValid, isCaptchaValid} from "../../utils/uservalid.js";

const data = reactive({
  username: '',
  password: '',
  key: '',
  code: ''
});

/**
 * 0: 无事发生
 * 1: 通过
 * 2: 失败
 * 3: 验证码错误
 */
const status = ref(0);

// 检查用户名格式
const checkUsername = (rule, usersname, callback) => {
  if (!usersname) {
    return callback(new Error('请输入用户名'))
  }
  setTimeout(() => {
    if (isUsernameValid(usersname)) {
      if (status.value === 2) {
        callback(new Error('请输入正确的用户名或密码'))
      } else callback()
    } else {
      callback(new Error('用户名只能由大小写字母、数字、下划线组成，长度在 3~20'));
    }
  }, 1000)
}

// 检查密码格式
const checkPassword = (rule, password, callback) => {
  if (!password) {
    return callback(new Error('请输入密码'))
  }
  setTimeout(() => {
    if (isPasswordValid(password)) {
      if (status.value === 2) {
        callback(new Error('请输入正确的用户名或密码'))
      } else callback()
    } else {
      callback(new Error('密码只能由大小写字母、数字、下划线、@$!%*?&#组成，长度在 3~20'));
    }
  }, 1000)
}

// 检查验证码格式
const checkCode = (rule, code, callback) => {
  if (!code) {
    return callback(new Error('请输入密码'))
  }
  setTimeout(() => {
    if (isCaptchaValid(code)) {
      if (status.value === 3) {
        callback(new Error('验证码错误'))
      } else callback()
    } else {
      callback(new Error('验证码错误'));
    }
  }, 1000)
}

const rules = reactive({
  ckUsername: [{validator: checkUsername, trigger: 'blur'}],
  ckPassword: [{validator: checkPassword, trigger: 'blur'}],
  ckCode: [{validator: checkCode, trigger: 'blur'}],
})
</script>

<style scoped>
.login-view {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>