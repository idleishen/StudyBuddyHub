import { createRouter,createWebHistory } from "vue-router";

const routes = [
    {
        path:'/Login',
        name:'login',
        component:()=>import('../views/Login.vue')
    },
    {     
        path:'/',
        name:'home',
        component:()=>import('../views/Home.vue')
    },
]

const router = createRouter({
    history:createWebHistory(),
    routes,
})

router.beforeEach((to,from,next)=>{
    const token = localStorage.getItem('token');
    if(to.path !== '/Login'&& !token){
        next('/Login');
    } else {
        next();
    }
});

export default router;