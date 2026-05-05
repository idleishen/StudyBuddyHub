<template>
    <div class="home">
        <!--顶部导航-->
        <header class="header">
            <span class="logo">📚 学友圈</span>
            <div class="header-right">
                 <span>👤 {{ nickname }}</span>
                 <span v-if="role === 'ADMIN'" class="admin-badge">管理员</span>
                 <button class="logout-btn" @click="handleLogout">退出登录</button>
            </div>
        </header>

        <!--内容-->
        <main class="main">
            <h1>🎉 欢迎回来，{{ nickname }}！</h1>
            <p>你已成功登录学友圈</p>
            <p>帖子功能即将上线...</p>
        </main>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const nickname = ref(localStorage.getItem('nickname') || '用户');
const role = ref(localStorage.getItem('role') || 'USER');

const handleLogout = () => {
    localStorage.clear();
    router.push('/login');
}
</script>

<style scoped>
.home {
    min-height: 100vh;
    background: #f0f2f5;
}
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 30px;
    height: 60px;
    background: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}
.logo {
    font-size: 20px;
    font-weight: bold;
}
.header-right {
    display: flex;
    align-items: center;
    gap: 14px;
    font-size: 15px;
}
.admin-badge {
    background: #ff4d4f;
    color: #fff;
    padding: 2px 10px;
    font-size: 15px;
}
.logout-btn {
    padding: 6px 16px;
    background: #ff4d4f;
    color: #fff;
    border: none;
    border-radius: 6px;
    cursor: pointer;
}
.logout-btn:hover {
    background: #ff7875;
}
.main {
    text-align: center;
    padding-top: 100px;
}
.main h1 {
    font-size: 28px;
    margin-bottom: 12px;
}
.main p {
    color: #666;
    margin-bottom: 8px;
}
</style>