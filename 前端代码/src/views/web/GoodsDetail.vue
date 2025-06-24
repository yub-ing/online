<!--web的模板-->
<template>
    <x-web ref="webLayout">
        <div class="main-content">
            <div class="m-body main-detail">
                <!--        详情页头部-->
                <div class="detail-head">
                    <div class="detail-head-box1">
                        <img :src="filePath + detail.showpic">
                    </div>
                    <div class="detail-head-box2">
                        <h1>{{detail.showtitle}}</h1>
                        <p class="detail-head-box2-desc">{{detail.showdesc}}</p>
                        <p class="detail-head-box2-tip">发布时间：{{detail.publishtime}}</p>
                        <p class="detail-head-box2-tip">收藏：{{ starCount }}，点赞{{praiseCount}}</p>
                        <button v-show="startBtnShow" type="button" @click="star(`${detail.id}`)">收 藏</button>
                        <button v-show="!startBtnShow" type="button" @click="cancelStar(`${detail.id}`)">取消收藏</button>
                        <button v-show="praiseBtnShow" type="button" @click="star(`${detail.id}`,'点赞')">点赞</button>
                        <button v-show="!praiseBtnShow" type="button" @click="cancelStar(`${detail.id}`,'点赞')">取消点赞</button>

                      <!--<button v-show="userInfo && userInfo.role === 'user'" type="button" @click="ddd(`${ detail.id}`)">动作</button>-->
                    </div>
                </div>
                <!--详情页主体-->
                <div class="detail-body">
                    <div class="detail-body1">
                        <p>热 门 推 荐</p>
                        <div class="detail-hot" v-for="i in hotList">
                            <a  @click="loadData(`${i.itemid}`)">
                                <img :src="filePath + i.showpic">
                                <p :title="i.showtitle">{{ i.showtitle}}</p>
                                <p >{{ i.showdesc}}</p>
                                <p >收藏：{{ i.count}}</p>
                            </a>
                        </div>
                    </div>
                    <div class="detail-body2">
                        <h2>内容详情</h2>
                    <template v-if="detail.leixFrn">
                    <h3>类型</h3>
                    <p>{{ detail.leixFrn.name}}</p>
                </template>
                        <template v-if="detail.midFrn">
                    <h3>商家</h3>
                    <p>{{ detail.midFrn.name}}</p>
                </template>
                        <h3>价格</h3>
                <p>{{ detail.price}}</p>

                    <h3>商品内容</h3>
                <p>{{ detail.showdetail}}</p>

                    <h3>库存数量</h3>
                <p>{{ detail.count}}</p>

                    <h3>发布时间</h3>
                <p>{{detail.publishtime}}</p>
                        <h3>展示</h3>
                <template v-if="detail.vv">
                    <img v-if="match(detail.vv,['.jpg','.JPG','.png','.gif','.bmp','.tiff'])"
                         style="width:100%" :src="filePath + detail.vv"/>
                    <video v-if="match(detail.vv,['.mp4','.avi','.wmv','.WMV','.flv'])"
                           :src="filePath + detail.vv" autoplay="autoplay" muted="muted"  loop="loop" controls></video>
                    <audio v-if="match(detail.vv,['.mp3','.wma','.WMA'])"
                           :src="filePath + detail.vv" controls="controls"></audio>
                    <a v-if="!matchMap[detail.vv]" style="color:#208b6b" :href="filePath + detail.vv" target="_blank">
                        下载{{detail.vv}}
                    </a>
                </template>
                            
                    </div>
                </div>
            </div>
        </div>
    </x-web>
</template>

<script setup>
    import xWeb from './WebLayout'
    let commentRef = ref(null)
    const router = useRouter();
    import {GlobalConfig} from 'core';
    import {onMounted} from "vue";
    const {user, msg} = useStore();
    const {appConfig} = GlobalConfig()
    const webLayout = ref(null)
    const userInfo = ref({})
    let entityName = 'goods'//实体类型
    let detail = ref({})//数据
    let starCount = ref(0)//收藏个数
    let praiseCount = ref(0)//点赞个数
    let hotList = ref([])//热门
    let startBtnShow = ref(true)
    let praiseBtnShow = ref(true)
    onMounted(async ()=> {
        console.log("Web Detail onMounted")
        userInfo.value = Cache.getUser()
        let query = router.currentRoute.value.query
        await loadData(query.id)
        webLayout.value.activeNav(entityName);
    })

    //跳转页面
    // import AddPage from "../xxxx/Add"
    // //新增页
    // const ddd = async (id) => {
    //   const op = Dialog.open(AddPage, `我要`).setConfirmText('确认')
    //   op.mounted(c => {
    //     c.render(null,{stuId: id})
    //   })
    //   op.confirm(async (c) => {
    //     c.submit()
    //   })
    // }

    const loadData= async (id) => {
        Msg.loading("正在前往...")
        let {data, success, message} = await Http.get("/welcome", {entityName, id});
        detail.value = data.detail;
        starCount.value = data.starCount;
        praiseCount.value = data.praiseCount;
        hotList.value = data.hotList;
        await hasStar(id)
        Msg.loading(false)
            }
    //收藏
    const star = async (id,op='收藏') => {
        webLayout.value.star(id,entityName,()=>{
            if(op==='收藏') {
                startBtnShow.value = false;
                starCount.value = starCount.value + 1
            }
            else {
                praiseBtnShow.value = false;
                praiseCount.value = praiseCount.value + 1
            }
        },op)
    }
    //取消收藏
    const cancelStar = async (id,op='收藏')=>{
        webLayout.value.cancelStar(id,entityName,()=>{
            if(op==='收藏') {
                startBtnShow.value = true;
                starCount.value = starCount.value - 1
            }
            else {
                praiseBtnShow.value = true;
                praiseCount.value = praiseCount.value - 1
            }
        },op)
    }
    const hasStar = async function(id) {
        {
            let {data, success, message} =await Http.get('/welcome2',{action:'hasStar',id,entityName,op:'收藏'})
            if(data){//已经收藏了
                startBtnShow.value = false;
            }else{//没有收藏
                startBtnShow.value = true;
            }
        }
        {
            let {data, success, message} =await Http.get('/welcome2',{action:'hasStar',id,entityName,op:'点赞'})
            if(data){//已经收藏了
                praiseBtnShow.value = false;
            }else{//没有收藏
                praiseBtnShow.value = true;
            }
        }
    }
    const matchMap = {}
    const match=(name,list)=>{
        for (let i = 0; i < list.length; i++) {
            if(name.endsWith(list[i])){
                console.log(name,list[i])
                matchMap[name] = true
                return true;
            }
        }
        return false
    }

</script>

<style lang="less" scoped>
    /*内容*/
    .main-content{
        width: 100%;
        min-height: 300px;
        padding: 10px 0;
        background: linear-gradient(180deg,rgba(0,0,0,.05), rgba(236, 238, 188, 0.2), rgba(230, 223, 236, 0.2));
    }

    a{
        cursor: pointer;
    }
    /*=============================================================detail详情页*/
    /*详情页*/
    .main-detail{

    }
    /*主体大小*/
    .m-body {
        width: 1280px;
        margin: 0 auto;
    }
    .main-detail .detail-head{
        display: flex;
        min-height: calc(1280px / 2 - 200px);
    }
    .detail-head-box1{
        width: calc(1280px / 2 - 100px);
        padding: 20px;
    }
    .detail-head-box1 img{
        width: calc(1280px / 2 - 140px);
    }
    .detail-head-box2{
        width: calc(1280px / 2 - 100px);
        padding: 30px;
    }
    .detail-head-box2 h1{
        margin: 10px 0 20px;
    }
    .detail-head-box2-desc {
        background-color: rgba(0,1,1,.1);
        padding: 10px;
        line-height: 2rem;
    }
    .detail-head-box2-tip {
        margin: 10px 0;
    }
    .detail-head-box2 button{
        cursor: pointer;
        width: 150px;
        height: 40px;
        background-color: #ecb907;
        border: none;
        border-radius: 15px;
        margin: 30px 15px 30px 0;
        color: white;
        font-weight: 700;
        font-size: 18px;
    }
    /*详情页主体*/
    .detail-body{
        display: flex;
        padding: 30px 0;
        border-top: 1px solid #e1e1e1;
    }
    /*热门推荐*/
    .detail-body1{
        width: calc(1280px * 0.2);
        min-height: 500px;
        padding: 15px 5px;
        border: 1px solid #e1e1e1;
        border-top: 4px solid #ecb907;
        text-align: center;
    }
    .detail-body1 .detail-hot{
        padding: 2px;
        margin: 9px 0;
        border: 1px solid #e1e1e1;
    }
    .detail-body1 .detail-hot img{
        width: calc(1280px * 0.2 - 20px);
    }
    .detail-body1 .detail-hot p{
        font-weight: 600;
        width: calc(1280px * 0.2 - 20px);
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        margin: 3px;
    }
    .detail-body1 .detail-hot p:nth-of-type(2) {
        font-weight: 400;
        font-size: 12px;
    }
    .detail-body1 .detail-hot p:nth-of-type(3) {
        font-weight: 400;
        font-size: 13px;
    }
    /*内容详情*/
    .detail-body2{
        margin: 0 20px;
        width: calc(1280px * 0.8);
        border-top: 4px solid #ecb907;
    }
    .detail-body2 > h2{
        text-align: center;
        letter-spacing: 1rem;
        margin: 20px 10px;
    }
    .detail-body2 > h3{
        padding: 0 15px;
        margin: 20px 10px;
        border-left: 3px solid #ecb907;
    }
    .detail-body2 > p{
        margin: 10px 10px;
        padding: 0 18px;
        white-space: pre-wrap;
    }
    .detail-body2 > video{
        display: block;
        margin: 0 auto;
        max-height: 400px;
    }

    /*=============================================================detail详情页 end*/

</style>


