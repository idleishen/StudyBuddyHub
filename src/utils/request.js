const BASE_URL = '/api';

async function request(url, options = {}) {
    const token = localStorage.getItem('token');

    const headers = {
        'Content-Type': 'application/json',
    }

    if (token) {
        headers['token'] = token;
    }   

    const config = {  
        ...options,
        headers:{
            ...headers,
            ...options.headers,
        },
    }

    if (options.body && typeof options.body === 'object') {
        config.body = JSON.stringify(options.body);
    }

    try {
        const response = await fetch(BASE_URL + url, config);
        
        if (response.status === 401) {
            localStorage.clear();
            window.location.href = '/login';
            return;
        }

        const data = await response.json();
        return data
    } catch (error) {
        console.error('请求出错:', error);
        throw error;
    }
}

export default {
    get(url, params) {
        if (params) {
            const query = new URLSearchParams(params).toString();
            url = url + '?' + query;
        }
        return request(url, { method: 'GET' });
    },
    post(url, data) {
        return request(url, { method: 'POST', body: data });
    },
    delete(url) {
        return request(url, { method: 'DELETE' });
    },
}