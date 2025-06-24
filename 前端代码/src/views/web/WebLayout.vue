<!--web网站的组件，包括网站头部导航栏和尾部-->
<template>
    <div class="codeyingBox">
        <header class="webheader">
            <div class="m-body">
                <!-- <img class="logo fl" :src="`${publicPath}img/logo.png`"> -->
                <a class="webtitle" @click="router.push('/welcome')"><h1 class="fl">{{ appConfig.name }}</h1></a>
                <!--顶部菜单-->
                <ul class="nav fl">
                    <li :class="i.clazz" v-for="i in navList">
                        <a @click="i.func">{{i.name}}</a>
                    </li>
                </ul>
                <!--用户信息-->
                <div v-if="userInfo && userInfo.id" class="fr login-info">
                    <a @click="goto('/hello')">
                      <img v-if="userInfo.avatar" :src="`/api/file/downloadById?fileid=${userInfo.avatar}`"
                           @click="goto('/hello')" style="height: 30px;position: relative;top: 8px;border-radius: 50%">
                      {{userInfo.name?userInfo.name:userInfo.username}}</a>　
                    <a @click="loginOut">退出登陆</a>　
                </div>
                <div v-if="!(userInfo && userInfo.id)" class="fr login-info">
                    <a @click="goto('/login')">登录</a>　
                </div>
                <!--搜索-->
                <div class="search-div fr">
                    <input class="search-input" v-model="searchTitle" type="text"/>
                    <input class="search-btn" type="button" @click="search" value="搜索">
                </div>
            </div>
        </header>

        <slot></slot>

        <!--footer-->
        <div class="footer">
            <div class="m-body footer_title">
                <div class="footer_flex">
                    <div class="foot_one">
                        <div class="titlesss">联系我们</div>
                        <div class="textss">地址：校内</div>
                        <div class="textss">技术支持：老师和同学</div>
                        <div class="textss">技术热线：0556-8888888</div>
                    </div>
                    <div class="foot_three">
                        <div class="titlesss">友情链接</div>
                        <div class="textss"><a class="hrefother" href="https://www.baidu.com/">百度首页</a></div>
                        <div class="textss"><a  class="hrefother" href="https://yiyan.baidu.com/">文心一言</a></div>
                        <div class="textss"><a  class="hrefother" href="https://www.bilibili.com/">BiliBili</a></div>
                    </div>
                    <div class="foot_four">
                        <div class="textss">版权所有：大学生一枚</div>
                        <div class="textss">版权声明：Copyright©  All Rights Reserved.</div>
                    </div>
                </div>
                <div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup name="x-web">
    const router = useRouter();
    import {GlobalConfig} from 'core';
    import {onMounted, reactive} from "vue";
    const {user, msg} = useStore();
    const {appConfig} = GlobalConfig()
    const searchTitle = ref('')
    let props = defineProps({
        doSearch:{type: Function, default: ()=>{}}, //搜索事件
    })

    //登录用户信息
    const userInfo = ref({})
    onMounted(async ()=> {
        console.log("Web layout onMounted")
        userInfo.value = Cache.getUser()
    })

    //顶部菜单
    let navList = reactive([
        {entityName:'goods',name:'商品信息',clazz:'',func:()=>{goto('/welcome/goods')}},
        {entityName:'star',name:'我的收藏',clazz:'',func:()=>{goto('/welcome/star')}},
    ])

    //设置/获取 导航栏高亮
    let entityName;//导航栏高亮
    const activeNav = (ename)=>{
        if(ename){
            entityName = ename;//设置
            for (let i = 0; i < navList.length; i++) {
                if(navList[i].clazz!=='') navList[i].clazz=''
                if(ename && navList[i].entityName===ename) navList[i].clazz='nav-active'
            }
        }
        return entityName
    }

    //搜索
    const search = ()=>{
        props.doSearch(searchTitle.value)
    }
    //goto
    const goto=(url)=>{
        console.log('goto:'+url)
        router.push(url)
    }
    //登出
    const loginOut = ()=> {
        const op = Msg.confirm('是否退出登录？')
        op.then(async ()=>{
            let result = await user.loginOut()
            userInfo.value = false
            Cache.setUser(null);//必须设置为空，否则有缓存用户
        })

    }
    //取消收藏
    const cancelStar = async (id,entityName,callback,op="收藏")=>{
        let {success}= await Http.get('/welcome2',{action: 'cancelStar', id,entityName,op})
        if(success){

            Msg.success("已取消~")
            if(callback) callback()
        }else {
            Msg.error("失败~")
        }
    }
    //收藏
    const star = async (id,entityName,callback,op="收藏") => {
        let {success, message} = await Http.get('/welcome2', {action: 'star', id, entityName,op})
        if (success) {
            Msg.success(message)
            if(callback)callback()
        } else {
            Msg.error(message)
        }
    }
    defineExpose({
        goto,activeNav,cancelStar,star
    })
</script>

<style lang="less" scoped>
    .codeyingBox{
        height: 100%;
        overflow: scroll;
    }
    .codeyingBox::-webkit-scrollbar {
        display: none;
    }
    /*基本*/
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    input:focus {
        outline: none;
    }
    .fl {
        float: left;
    }

    .fr {
        float: right;
    }
    .clearfix::before, .clearfix::after {
        content: '';
        display: table;
    }
    .clearfix::after {
        clear: both;
    }
    ul li {
        list-style: none;
    }
    a {
        color: #454444;
        text-decoration: none;
    }
    /*手指*/
    a,button,input[type=button],input[type=submit]{
        cursor: pointer;
    }

    /*主体大小*/
    .m-body {
        width: 1280px;
        margin: 0 auto;
    }
    /*========================================webheader*/
    .webheader {
        width: 100%;
        height: 60px;
        box-shadow: 0 0 6px 2px rgba(43, 43, 43, 0.2);
        background:  #f6f6f6;
    }
    .webheader .webtitle{
        color: #ecb907;
    }
    /* 定义动画 */
    @keyframes animate-webtitle{
        to{
            /* 色相旋转过滤镜（设置度数可改变颜色） */
            filter: hue-rotate(50deg);
        }
    }
    .webheader .webtitle h1{
        margin: 0 15px;
        color: #fff;
        letter-spacing: 3px;
        line-height: 60px;
        outline: none;
        /* 自定义属性 --c,可通过 var 函数对其调用 */
        --c: #ecb907;
        /* 调用自定义属性--c，设置文字阴影（发光效果） */
        text-shadow: 0 0 2px var(--c),
        0 0 4px var(--c),
        0 0 6px var(--c),
        0 0 8px var(--c),
        0 0 16px var(--c);
        /* 执行动画：动画名 时长 线性的 无限次播放 */
        animation: animate-webtitle 5s linear infinite;
    }
    .webheader .logo {
        height: 40px;
        margin-top: 10px;
        margin-right: 30px;
    }

    .webheader .nav {
        display: flex;
    }

    .webheader .nav li {
        padding: 18px 20px;
    }

    .webheader .nav li:hover {
        background-color: #eeeeee;
    }

    .webheader .nav .nav-active {
        border-bottom: 4px solid #ecb907;
    }

    /*搜索*/
    .search-div {
        border: 1px solid #e1e1e1;
        height: 30px;
        margin-right: 20px;
        margin-top: 15px;
        border-radius: 15px;
        padding-left: 15px;
    }

    .search-input {
        background-color:  #f6f6f6;
        border: none;
        width: 150px;
        height: 25px;
    }

    .search-btn {
        border: none;
        background-color: #ecb907;
        border-radius: 14px;
        height: 23px;
        color: white;
        padding: 0 5px;
        margin: 2px;
    }

    .login-info {
        line-height: 60px;
    }
    /*========================================webheader end*/

    /*------------------------------尾巴*/
    /*尾部*/
    .footer {
        background: rgb(2,37,89);
        font-size: 14px;
    }

    .footer .hrefother {
        cursor: pointer;
        display: inline-block;
        color: white;
        text-decoration: none;
    }

    .footer .footer_title {
        text-align: center;
        color: white;
        padding-top: 30px;
        padding-bottom: 20px;
    }

    .footer .footer_title .footer_flex {
        display: flex;
        justify-content: space-between;
    }

    .footer .footer_title .footer_flex .foot-img {
        width: 130px;
        height: 130px;
    }

    .footer .footer_title .footer_flex .foot_one {
        width: 30%;
        text-align: left;
    }

    .footer .footer_title .footer_flex .titlesss {
        font-weight: bold;
        margin-bottom: 20px;
    }

    .footer .footer_title .footer_flex .foot_two {
        width: 19%;

    }

    .footer .footer_title .footer_flex .foot_three {
        width: 25%;
        text-align: left;
        padding-left: 30px;
    }

    .footer .footer_title .footer_flex .foot_four {
        flex: 1;
        text-align: left;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .footer .footer_title .main {
        font-size: 20px;
    }
    .footer .textss
    {
        font-size: 13px;
        margin-bottom: 10px;
    }

    /*------------------------------尾巴*/

</style>


