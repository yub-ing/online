<template>
    <div class="box">
        <div style="height: 50px"></div>
        <div class="head">
            <p style="line-height: 50px;font-size: 1.2rem;margin-left:15px;color: white;text-decoration: none">消息列表</p>
        </div>
        <div class="contentBox">
            <div v-for="i in chatList" class="chat" @click="chatDetail(user.id == i.sid ? i.rid : i.sid,user.id == i.sid ? i.rtype : i.stype, i.hisName)">
                <div>
                    <img :src="`${publicPath}images/users/other.png`" alt="">
                </div>
                <div >
                    <div style="display: flex;justify-content: space-between">
                        <p style="line-height: 30px;width: 290px;margin-top: 10px">
                            <span style="font-size: 1.3rem">{{ i.hisName }}</span>
                            <span style="font-size: 0.4rem;margin: 0 5px">
                                {{user.id == i.sid ? i.rtype : i.stype}}
                            </span>
                            <span style="font-size: 0.4rem;color:white;background-color: red;margin-left: 5px">
                                {{i.unreadCount == 0 ? '' : i.unreadCount+'条未读消息'}}
                            </span>
                        </p>
                        <p style="margin-top: 15px;">
                            {{i.createtime}}
                        </p>
                    </div>
                    <p style="line-height: 30px">{{ i.content }}</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup name="chat">
    import {  reactive, ref } from 'vue'
    let user = Cache.getUser()//当前登录用户
    let chatList = ref([])
    import {onMounted} from "vue";
    import Chat from './Chat'
    const router = useRouter();
    let fullPath = router.currentRoute.value.fullPath
    //钩子函数，挂载
    onMounted(async () => {
        getChatList()
    })

    const getChatList = async ()=>{
        //只有当前页面显示才去获取聊天列表
        if(router.currentRoute.value.fullPath === fullPath){
            let {data} = await Http.get('/wechat')
            if(data){
                console.log(`与${data.length}人有聊天记录`)
                chatList.value = data
            }
        }
        //获取消息列表
        setTimeout(function () {
            getChatList()
        },5000)
    }

    //打开聊天详情
    const chatDetail=(rid,rtype,hisName)=>{
        const op = Dialog.open(Chat, `与"${hisName}"的聊天`).setCancelText('').setConfirmText('')
        op.mounted(c => {
            c.render(rid,rtype,hisName)
        })
    }

</script>

<style scoped>
    *{
        margin: 0;padding: 0;
    }
    .box{
        width: 600px;
        margin: 0 auto;
    }
    .head {
        height: 50px;
        background-color: #1D6FA3;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }
    .contentBox{
        background-color: white;
        min-height: 500px;
        border: 1px solid #e1e1e1;
    }
    .chat{
        width: 100%;
        height: 80px;
        display: flex;
        flex-direction: row;
    }
    .chat:hover{
        background-color: #e1e1e1;
    }
    .chat img{
        width: 50px;
        height: 50px;
        margin: 15px;
        border-radius: 50%;
    }
</style>
