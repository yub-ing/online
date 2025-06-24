<template>
    <x-web ref="webLayout" >
        <div class="main-content">
            <div class="m-body">
                <div class="content-webtitle">
                    <h2>我的收藏</h2>
                </div>
                <div class="content-l">
                    <div class="content-l-item" v-for="i in stars">
                        <a  @click="goto(`/welcome/${i.itemtype}/detail?id=${i.itemid}`)">
                            <img :src="filePath + i.showpic">
                            <p class="content-l-item-title" :title="i.showtitle">{{ i.showtitle}}</p>
                            <p class="content-l-item-tip" >{{ i.showdesc}}</p>
                        </a>
                        <a @click="cancelStar(`${i.itemid}`,`${i.itemtype}`)">
                            <p class="content-l-item-tip">取消收藏</p>
                        </a>
                    </div>
                </div>
            </div>
        </div>

    </x-web>
</template>

<script setup>
    import xWeb from './WebLayout'
    import {Helper} from "core";
    let conf = codeying
    const router = useRouter();
    import {GlobalConfig} from 'core';
    import {onMounted} from "vue";
    const webLayout = ref(null)
    const {appConfig} = GlobalConfig()
    let user
    let stars = ref([])
    onMounted(() => {
        console.log("WebStart onMounted")
        user = Cache.getUser()
        if (!user || !user.id) {
            router.push("/login")
        }
        webLayout.value.activeNav('star');
        getData();
    })
    const getData= async () => {
        let {data, success} = await Http.get("/welcome?action=stars")
        if (!success) {
            router.push("/login")
        }
        stars.value = data.stars;
    }
    const goto = (url)=>{
        webLayout.value.goto(url)
    }
    //取消收藏
    const cancelStar = async (id,entityName)=>{
        webLayout.value.cancelStar(id,entityName,()=>{getData();})
    }

</script>

<style lang="less" scoped>
    /*=============================================================main*/
    a{
        cursor: pointer;
    }
    /*主体大小*/
    .m-body {
        width: 1280px;
        margin: 0 auto;
    }
    .banner{
        width: 1080px;
        height: 400px;
        border: 1px solid #e1e1e1;
        border-radius: 10px;
        /*margin: 0 auto;*/
        position: relative;
        overflow: hidden;
    }
    .banner img{
        width: 1080px;
        height: 400px;
    }
    .main-banner{
        width: 100%;
        padding: 30px 0;
        background: rgba(0,0,0,.05);
        display: flex;
        justify-content: center;
    }
    /*通知公告*/
    .main-banner-notice{
        width: 190px;
        height: 400px;
        border: 1px solid #e1e1e1;
        margin-left: 10px;
        border-radius: 10px;
        color: #767474;
        /*CSS实现隐藏滚动条同时又可以滚动*/
        overflow-y: scroll;
        -ms-overflow-style: none;  /* IE and Edge */
        scrollbar-width: none;  /* Firefox */
    }
    /* 隐藏 Chrome、Safari 和 Opera 的滚动条 */
    .main-banner-notice::-webkit-scrollbar {
        display: none;
    }

    .main-banner-notice >div:nth-of-type(1){
        text-align: center;
    }
    .main-banner-notice >div{
        padding: 5px 6px;
    }
    .main-banner-notice p{
        font-size: 12px;
    }
    /*通知公告*/

    /*内容*/
    .main-content{
        width: 100%;
        min-height: 300px;
        padding: 10px 0;
        background: linear-gradient(180deg,rgba(0,0,0,.05), rgba(236, 238, 188, 0.2), rgba(230, 223, 236, 0.2));
    }
    /*标题*/
    .content-webtitle{
        margin: 10px 0;
        padding: 0 10px;
        border-left: 3px solid black;
        background: rgba(255,255,255,.2);
    }
    /*内容*/
    .main-content .content-l{
        display: flex;
        flex-wrap: wrap;
    }
    @keyframes animated_div{
        0%		{margin-top: 15px;}
        100%	{margin-top: 0;}
    }
    .main-content .content-l .content-l-item:hover{
        animation: animated_div 0.5s forwards;
        cursor: pointer;
    }
    .main-content .content-l .content-l-item{
        width: 200px;
        height: 264px;
        box-shadow:0 0 6px 2px rgba(12, 110, 232, 0.15);
        text-align: center;
        margin-right: 3px;
        margin-bottom: 10px;
        margin-top: 15px;
        padding: 5px;
    }
    .main-content .content-l .content-l-item:nth-child(n){
        border-top: 1px solid #217AFD;
    }
    .main-content .content-l .content-l-item:nth-child(n+1){
        border-top: 1px solid #4ADEFF;
    }
    .main-content .content-l .content-l-item:nth-child(n+2){
        border-top: 1px solid #FD7021;
    }
    .main-content .content-l .content-l-item:nth-child(n+3){
        border-top: 1px solid #62D05F;
    }
    .main-content .content-l .content-l-item:nth-child(n+4){
        border-top: 1px solid #217AFD;
    }
    .main-content .content-l .content-l-item:nth-child(n+5){
        border-top: 1px solid #4ADEFF;
    }

    .main-content .content-l .content-l-item img{
        max-width: calc(200px - 10px);
        height: 150px;
        margin-bottom: 10px;
    }

    .main-content .content-l .content-l-item .content-l-item-webtitle{
        font-size: 18px;
        margin-bottom: 10px;
        width: calc(200px - 10px);
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .main-content .content-l .content-l-item .content-l-item-tip{
        font-size: 10px;
        color: #999999;
        width: 100%;
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
    }

    /*=============================================================main end*/

</style>
