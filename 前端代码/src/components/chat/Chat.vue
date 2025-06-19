<template>
    <div class="main">
        <div class="top">

        </div>
        <div class="box">
            <div class="box-right">
                <div class="recvfrom">
                    <div class="nav-top">
                        <p>{{rname}}</p>
                    </div>
                    <div id="content" class="news-top">
                        <ul id="contentul">
                            <template v-for="i in msgData">
                                <li v-if="i.sid!==user.id" class="other" >
                                    <div class="avatar"><img :src="`${publicPath}images/users/other.png`" alt=""></div>
                                    <div class="msg">
                                        <p class="msg-text">{{i.content}}</p>
                                        <time>{{i.createtime}}</time>
                                    </div>
                                </li>
                                <li v-if="i.sid===user.id" class="self" >
                                    <div class="avatar"><img :src="`${publicPath}images/users/self.png`" alt=""></div>
                                    <div class="msg">
                                        <p class="msg-text">{{i.content}}</p>
                                        <time>{{i.createtime}}</time>
                                    </div>
                                </li>
                            </template>
                        </ul>
                    </div>
                </div>
                <div class="sendto">
                    <div class="but-text">
                        <el-input v-model="mymsg" type="textarea" cols="110" rows="6"/>
                        <a @click="send()" class="button">发送</a>
                        <a v-if="getting===0" @click="gettingContinue" class="button">刷新最新消息</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</template>

<script setup>
    import {  reactive, ref } from 'vue'
    let user = Cache.getUser()
    let rid = ref("")
    let rtype = ref("")
    let rname = ref("")
    let msgData = ref([])
    let mymsg = ref("")//当前输入框未发送消息
    let getting = ref(5)//每次加载连续获取消息次数

    const render = (rid_,rtype_,hisName) => {
        rid.value = rid_;
        rtype.value = rtype_;
        rname.value = hisName;
        getMsg();
    }

    //每次点击刷新可以10次连续监控消息
    const gettingContinue = ()=>{
        getting.value = 10
    }

    //获取消息
    const getMsg = async ()=>{
        if(getting.value>0){
            let {data,success,message} = await Http.post('/wechat',{'action': 'receive', rid:rid.value, rtype:rtype.value})
            if(success){
                console.log(`与${rname.value}聊天的消息条数：${data.length}`)
                if(msgData.value.length !== data.length){
                    msgData.value = data
                    setTimeout(function () {
                        scrollBottom()
                    },200)
                }
            }else {
                Msg.error(message)
            }
            getting.value = getting.value -1
        }
        //递归调用，每隔X秒获取最新消息
        setTimeout(function () {
            getMsg()
        },1500)
    }
    //发送消息
    const send = async ()=>{
        var content = mymsg.value
        let {success,message} = await Http.post('/wechat',{'action': 'send',content, rid:rid.value, rtype:rtype.value})
        if(success){
            //发送成功则附加上去
            msgData.value.push({sid:user.id,rid:rid.value,content,createtime:'刚刚'})
            mymsg.value = ''
        }else {
            Msg.error(message)
        }
    }
    //自动滚动最后一条消息
    function scrollBottom() {
        let div = document.getElementById('content');
        div.scrollTop = div.scrollHeight;
    }

    defineExpose({render});
</script>

<style scoped>
    * {
        list-style: none;
        padding: 0;
        margin: 0;
        font-size: 14px;
        text-decoration: none;
        color: black;
        outline: none;
    }

    html, body {
        width: 100%;
        height: 100%;
    }

    .main {
        height: 700px;
        width: 750px;
        margin: auto;
        box-shadow: 0 0 3px 5px #e1e1e1;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }

    .main .top {
        width: 750px;
        height: 35px;
        background-color: #3bb4f2;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }

    .main .top .top-left .search input {
        display: inline-block;
        width: 110px;
        height: 30px;
        border-radius: 40px;
        border: 1px solid ghostwhite;
        text-indent: 40px;
        background-color: #3bb4f2;

    }

    .main .top .top-left .search input:hover {
        border: 1px solid white;
    }

    .main .box {
        width: 100%;
        height: 740px;
        border-bottom-left-radius: 10px;
        border-bottom-right-radius: 10px;
    }

    .main .box .box-right {
        float: left;
        width: 750px;
        height: 100%;
    }

    .main .box .box-right .recvfrom {
        width: 752px;
        height: 500px;
    }

    .main .box .box-right .recvfrom .nav-top {
        height: 45px;
        width: 100%;
    }

    .main .box .box-right .recvfrom .nav-top p {
        line-height: 45px;
        font-size: 18px;
        font-weight: 600;
        margin-left: 25px;
    }

    .main .box .box-right .recvfrom .news-top {
        width: 99%;
        height: 450px;
        border-top: 1px solid #e1e1e1;
        overflow-y: scroll;
    }

    .main .box .box-right .recvfrom .news-top ul {
        height: 100%;
        width: 100%;
    }

    .main .box .box-right .recvfrom .news-top ul li {
        margin: 10px;
        min-height: 80px;
        position: relative;
    }

    .main .box .box-right .recvfrom .news-top ul li .avatar img {
        height: 30px;
        width: 30px;
        border-radius: 50%;
    }

    .main .box .box-right .recvfrom .news-top ul li .msg {
        top: -10px;
        margin: 8px;
        min-height: 80px;
    }

    .main .box .box-right .recvfrom .news-top ul li::after {
        clear: both;
        content: "";
        display: inline-block;
    }

    .main .box .box-right .recvfrom .news-top ul li .msg .msg-text {
        border: solid 1px gray;
        padding: 8px;
    }

    .other time {
        float: left;
        color: #ccc;
    }

    .self time {
        float: right;
        color: #ccc;
    }

    .main .box .box-right .recvfrom .news-top ul li.other .avatar {
        position: absolute;
        left: 0;
        top: 0;
    }

    .main .box .box-right .recvfrom .news-top ul li.other .msg {
        position: absolute;
        left: 40px;
    }

    .main .box .box-right .recvfrom .news-top ul li.self .avatar {
        position: absolute;
        right: 0;
        top: 0;
    }

    .main .box .box-right .recvfrom .news-top ul li.self .msg {
        position: absolute;
        right: 38px;
    }

    .main .box .box-right .sendto .but-nav ul li {
        float: left;
        height: 22px;
        width: 22px;
        margin: 7px 15px;
        background-size: 100% 100%;
    }

    .main .box .box-right .sendto .but-nav ul li:hover {
        background-color: #dbdbdb;
    }

    .main .box .box-right .sendto .but-text textarea {
        font-size: 20px;
        padding: 15px;
        width: 715px;
        height: 100px;
        resize: none;
        border: solid 1px #e1e1e1;
    }

    .main .box .box-right .sendto .but-text .button {
        float: right;
        padding: 5px 25px;
        background-color: #3bb4f2;
        margin-right: 20px;
    }
    .button{
        cursor: pointer;
    }
</style>
