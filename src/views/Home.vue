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

        <div class="container">
        <!--发帖区域-->
        <div class="post-form">
            <input v-model="postForm.title" type="text" placeholder="请输入帖子标题" class="input" />
            <textarea v-model="postForm.content" placeholder="分享你的学习心得..." class="textarea" rows="3"></textarea>
            <button class="submit-btn" @click="handleCreatePost" :disabled="posting">
                {{ posting ? '发布中...' : '发布帖子' }}
            </button>
        </div>

        <!--帖子列表-->
        <div class="post-list">
            <div v-if="posts.length === 0" class="empty">暂无帖子，快来分享你的学习心得吧！</div>
            <div v-for="post in posts" :key="post.id" class="post-card">
                <div class="post-title">{{ post.title }}</div>
                <div class="post-meta">
                    <span>👤 {{ post.authorNickname }}</span>
                    <span>🕐 {{ formatTime(post.createTime) }}</span>
                </div>
                <div class="post-actions">
                    <button v-if="role === 'ADMIN'" class="delete-btn" @click="handleDelete(post.id)">删除</button>
                </div>
            </div>
        </div>

        <!--分页-->
        <div class="pagination" v-if="total > pageSize">
            <button :disabled="currentPage === 1" @click="currentPage--">上一页</button>
            <span>{{ currentPage }} / {{ totalPages }}页</span>
            <button :disabled="currentPage === totalPages" @click="currentPage++">下一页</button>
        </div>
        </div>
        </div>
</template>

<script setup>
import { formatter } from 'element-plus';
import { computed, onMounted, queuePostFlushCb, ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '../utils/request';

const router = useRouter();
const nickname = ref(localStorage.getItem('nickname') || '用户');
const role = ref(localStorage.getItem('role') || 'USER');

const handleLogout = () => {
    localStorage.clear();
    router.push('/login');
}

onMounted(() => {
    fetchPosts();
});

const posts = ref([]);
const currentPage = ref(1);
const pageSize = 10;
const total = ref(0);
const posting = ref(false);

const postForm = ref({
    title: '',
    content: ''
});

const postPages = computed(() => Math.ceil(total.value / pageSize) || 1);

const formatTime = (time) => {
    if (!time) return '';
    return new Date(time).toLocaleString('zh-CN');
}

// 获取帖子列表
const fetchPosts = async () => {
    try {
        const res = await api.get('/posts/list', {
            page: currentPage.value, pageSize: pageSize
        });
        if (res.code === 200) {
            posts.value = res.data.records;
            total.value = res.data.total;
        }
    } catch (error) {
        console.error('获取帖子列表失败:', error);
    }
}

//发布帖子
const handleCreatePost = async () => {
    if (!postForm.value.title || !postForm.content) {
        alert('请填写帖子标题和内容');
        return;
    }
    posting.value = true;
    try {
        const res = await api.post('/posts/create', postForm);
        if (res.code === 200) {
            alert('发布成功');
            postForm.value.title = '';
            postForm.value.content = '';
            currentPage.value = 1;
            fetchPosts();
        } else {
            alert(res.message);
        }
    } catch (error) {
        alert('发布失败');
    } finally {
        posting.value = false;
    }
}

//删除帖子
const handleDelete = async (id) => {
    if (!confirm('确定要删除这条帖子吗？')) return;
    try {
        const res = await api.delete('/posts/delete/' + id);
        if (res.code === 200) {
            alert('删除成功');
            fetchPosts();
        } else {
            alert(res.message);
        }
    } catch (error) {
        alert('删除失败');
    }
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
.container { 
    max-width: 700px; 
    margin: 30px auto; 
    padding: 0 20px; 
}
.post-form {
    background: #fff; 
    padding: 20px; 
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.06); 
    margin-bottom: 20px;
    display: flex; 
    flex-direction: column; 
    gap: 12px;
}
.input { 
    padding: 12px; 
    border: 1px solid #ddd; 
    border-radius: 8px; 
    font-size: 15px; 
    outline: none; 
}
.textarea { 
    padding: 12px; 
    border: 1px solid #ddd; 
    border-radius: 8px; 
    font-size: 15px; 
    outline: none; 
    resize: vertical; 
}
.submit-btn {
  padding: 12px; 
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: #fff; 
  border: none; 
  border-radius: 8px; 
  font-size: 16px; 
  cursor: pointer;
}
.submit-btn:disabled { 
    opacity: 0.6; 
    cursor: not-allowed; 
}
.post-list { 
    display: flex; 
    flex-direction: column; 
    gap: 10px; 
}
.empty { 
    text-align: center; 
    color: #999; 
    padding: 40px; 
}
.post-card {
  background: #fff; 
  padding: 16px 20px; 
  border-radius: 10px;
  box-shadow: 0 1px 4px rgba(0,0,0,0.06); 
  cursor: pointer;
}
.post-card:hover { 
    box-shadow: 0 2px 12px rgba(0,0,0,0.1); 
}
.post-title { 
    font-size: 16px; 
    font-weight: bold; 
    color: #333; 
    margin-bottom: 8px; 
}
.post-meta { 
    display: flex; 
    gap: 20px; 
    color: #999; 
    font-size: 13px; 
}
.post-actions { 
    margin-top: 8px; 
}
.delete-btn { 
    padding: 4px 12px; 
    background: #ff4d4f; 
    color: #fff; 
    border: none; 
    border-radius: 4px; 
    cursor: pointer; 
    font-size: 12px; 
}
.pagination { 
    display: flex; 
    justify-content: center; 
    align-items: center; 
    gap: 16px; 
    margin-top: 20px; }
.pagination button { 
    padding: 8px 16px; 
    border: 1px solid #ddd; 
    background: #fff; 
    border-radius: 6px; 
    cursor: pointer; 
}
.pagination button:disabled { 
    opacity: 0.4; 
    cursor: not-allowed; 
    }
</style>