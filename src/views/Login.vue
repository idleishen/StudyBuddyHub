<template>
  <div class="login-page">
    <div class="login-box">
      <div class="title-area">
        <div class="icon">📚</div>
        <h1>学友圈</h1>
        <p>学习交流平台</p>
      </div>

      <div class="tabs">
        <div :class="['tab',{active: currentTab === 'login'}]" @click="currentTab = 'login'">登录</div>
        <div :class="['tab',{active: currentTab === 'register'}]" @click="currentTab = 'register'">注册</div>
        </div>

        <!-- 登录表单 -->
        <div v-if="currentTab === 'login'" class="form-area">
          <input v-model="loginForm.account" type="text" placeholder="请输入账号" class="input" />
          <input v-model="loginForm.password" type="password" placeholder="请输入密码" class="input" @keyup.enter="handleLogin" />
          <button class="btn login-btn" @click="handleLogin" :disabled="loading">
            {{ loading ? '登录中...' : '登 录' }}
          </button>
        </div>

        <!-- 注册表单 -->
        <div v-if="currentTab === 'register'" class="form-area">
          <input v-model="registerForm.account" type="text" placeholder="请输入账号" class="input" />
          <input v-model="registerForm.password" type="password" placeholder="请输入密码(至少6位)" class="input" @keyup.enter="handleRegister" />
          <button class="btn register-btn" @click="handleRegister" :disabled="loading">
            {{ loading ? '注册中...' : '注 册' }}
            </button>
        </div>

         <div v-if="message" :class="['message', messageType]">{{ message }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import api from '../utils/request'

const router = useRouter()
const currentTab = ref('login')
const loading = ref(false)
const message = ref('')
const messageType = ref('success')

const loginForm = reactive({ account: '', password: '' })
const registerForm = reactive({ account: '', password: '', nickname: '' })

const showMessage = (msg, type = 'error') => {
  message.value = msg
  messageType.value = type
  setTimeout(() => { message.value = '' }, 3000)
}

const handleLogin = async () => {
  if (!loginForm.account || !loginForm.password) {
    showMessage('请输入账号和密码')
    return
  }
  loading.value = true
  try {
    const res = await api.post('/user/login', loginForm)
    if (res.code === 200) {
      localStorage.setItem('token', res.data.token)
      localStorage.setItem('userId', res.data.userId)
      localStorage.setItem('nickname', res.data.nickname)
      localStorage.setItem('role', res.data.role)
      showMessage('登录成功！', 'success')
      setTimeout(() => router.push('/'), 500)
    } else {
      showMessage(res.message || '登录失败')
    }
  } catch {
    showMessage('网络错误，请检查后端是否启动')
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  if (!registerForm.account || !registerForm.password || !registerForm.nickname) {
    showMessage('请填写完整信息')
    return
  }
  if (registerForm.password.length < 6) {
    showMessage('密码至少6位')
    return
  }
  loading.value = true
  try {
    const res = await api.post('/user/register', registerForm)
    if (res.code === 200) {
      showMessage('注册成功，请登录！', 'success')
      currentTab.value = 'login'
      loginForm.account = registerForm.account
      registerForm.account = ''
      registerForm.password = ''
      registerForm.nickname = ''
    } else {
      showMessage(res.message || '注册失败')
    }
  } catch {
    showMessage('网络错误，请检查后端是否启动')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea, #764ba2);
}
.login-box {
  width: 400px;
  background: #fff;
  border-radius: 16px;
  padding: 40px 30px;
  box-shadow: 0 20px 60px rgba(0,0,0,0.3);
}
.title-area { text-align: center; margin-bottom: 25px; }
.icon { font-size: 48px; }
.title-area h1 { font-size: 26px; color: #333; }
.title-area p { font-size: 14px; color: #999; }
.tabs { display: flex; border-bottom: 2px solid #eee; margin-bottom: 20px; }
.tab { flex: 1; text-align: center; padding: 12px; cursor: pointer; color: #999; border-bottom: 2px solid transparent; margin-bottom: -2px; }
.tab.active { color: #667eea; border-bottom-color: #667eea; font-weight: bold; }
.form-area { display: flex; flex-direction: column; gap: 15px; }
.input { padding: 14px; border: 1px solid #ddd; border-radius: 8px; font-size: 15px; outline: none; }
.input:focus { border-color: #667eea; }
.btn { padding: 14px; border: none; border-radius: 8px; font-size: 16px; color: #fff; cursor: pointer; font-weight: bold; }
.btn:disabled { opacity: 0.6; cursor: not-allowed; }
.login-btn { background: linear-gradient(135deg, #667eea, #764ba2); }
.register-btn { background: linear-gradient(135deg, #43e97b, #38f9d7); }
.message { text-align: center; margin-top: 18px; padding: 10px; border-radius: 6px; font-size: 14px; }
.message.error { background: #fff2f0; color: #ff4d4f; }
.message.success { background: #f6ffed; color: #52c41a; }
</style>