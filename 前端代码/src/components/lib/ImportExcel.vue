<template>
    <template v-if="type === 'admin'">
        <p style="color: red">提示：按照以下格式上传,第一行是表头</p>
        <table class="modal-table">
            <tr>
                                                                                  <td style="background-color: #0FB25F">用户名</td>
                                                             <td style="background-color: #0FB25F">密码</td>
                                                             <td style="background-color: #0FB25F">姓名</td>
                                                             <td style="background-color: #0FB25F">电话</td>
                            </tr>
            <tr>
                                                                                                              <td>用户名...</td>
                                                                                         <td>密码...</td>
                                                                                         <td>姓名...</td>
                                                                                         <td>电话...</td>
                            </tr>
            <tr>
                                                                                  <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                            </tr>
        </table>
        <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="#"
                :limit="1"
                :auto-upload="false"
                :on-exceed="handleExceed"
                :http-request="upload"
        >
            <template #trigger>
                <el-button type="primary">选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                立即导入
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    请按照以上格式上传excel文件
                </div>
            </template>
        </el-upload>
    </template>
    <template v-if="type === 'merchant'">
        <p style="color: red">提示：按照以下格式上传,第一行是表头</p>
        <table class="modal-table">
            <tr>
                                                                                  <td style="background-color: #0FB25F">用户名</td>
                                                             <td style="background-color: #0FB25F">密码</td>
                                                             <td style="background-color: #0FB25F">商家名</td>
                                                             <td style="background-color: #0FB25F">头像</td>
                                                             <td style="background-color: #0FB25F">电话</td>
                                                             <td style="background-color: #0FB25F">小店简介</td>
                                                             <td style="background-color: #0FB25F">主营内容</td>
                            </tr>
            <tr>
                                                                                                              <td>用户名...</td>
                                                                                         <td>密码...</td>
                                                                                         <td>商家名...</td>
                                                                                         <td>头像...</td>
                                                                                         <td>电话...</td>
                                                                                         <td>小店简介...</td>
                                                                                         <td>主营内容...</td>
                            </tr>
            <tr>
                                                                                  <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                            </tr>
        </table>
        <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="#"
                :limit="1"
                :auto-upload="false"
                :on-exceed="handleExceed"
                :http-request="upload"
        >
            <template #trigger>
                <el-button type="primary">选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                立即导入
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    请按照以上格式上传excel文件
                </div>
            </template>
        </el-upload>
    </template>
    <template v-if="type === 'user'">
        <p style="color: red">提示：按照以下格式上传,第一行是表头</p>
        <table class="modal-table">
            <tr>
                                                                                  <td style="background-color: #0FB25F">用户名</td>
                                                             <td style="background-color: #0FB25F">密码</td>
                                                             <td style="background-color: #0FB25F">姓名</td>
                                                             <td style="background-color: #0FB25F">头像</td>
                                                             <td style="background-color: #0FB25F">性别</td>
                                                             <td style="background-color: #0FB25F">年龄</td>
                                                             <td style="background-color: #0FB25F">电话</td>
                                                             <td style="background-color: #0FB25F">邮箱</td>
                                                             <td style="background-color: #0FB25F">地址</td>
                            </tr>
            <tr>
                                                                                                              <td>用户名...</td>
                                                                                         <td>密码...</td>
                                                                                         <td>姓名...</td>
                                                                                         <td>头像...</td>
                                                                                         <td>
                                                                    男、                                                                    女                                                            </td>
                                                                                                 <td>年龄...</td>
                                                                                         <td>电话...</td>
                                                                                         <td>邮箱...</td>
                                                                                         <td>地址...</td>
                            </tr>
            <tr>
                                                                                  <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                            </tr>
        </table>
        <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="#"
                :limit="1"
                :auto-upload="false"
                :on-exceed="handleExceed"
                :http-request="upload"
        >
            <template #trigger>
                <el-button type="primary">选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                立即导入
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    请按照以上格式上传excel文件
                </div>
            </template>
        </el-upload>
    </template>
    <template v-if="type === 'goods'">
        <p style="color: red">提示：按照以下格式上传,第一行是表头</p>
        <table class="modal-table">
            <tr>
                                                                                  <td style="background-color: #0FB25F">首页图片</td>
                                                             <td style="background-color: #0FB25F">商品名称</td>
                                                             <td style="background-color: #0FB25F">简介</td>
                                                             <td style="background-color: #0FB25F">类型</td>
                                                             <td style="background-color: #0FB25F">商家</td>
                                                             <td style="background-color: #0FB25F">价格</td>
                                                             <td style="background-color: #0FB25F">商品内容</td>
                                                             <td style="background-color: #0FB25F">库存数量</td>
                                                             <td style="background-color: #0FB25F">发布时间</td>
                                                             <td style="background-color: #0FB25F">展示</td>
                            </tr>
            <tr>
                                                                                                              <td>首页图片...</td>
                                                                                         <td>商品名称...</td>
                                                                                         <td>简介...</td>
                                                                                         <td>确保该类型在商品类型表中存在</td>
                                                                                                 <td>确保该商家在商家表中存在</td>
                                                                                                 <td>价格...</td>
                                                                                         <td>商品内容...</td>
                                                                                         <td>库存数量...</td>
                                                                                         <td>格式例：1999年06月05日 12:15:00</td>
                                                                                                 <td>展示...</td>
                            </tr>
            <tr>
                                                                                  <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                            </tr>
        </table>
        <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="#"
                :limit="1"
                :auto-upload="false"
                :on-exceed="handleExceed"
                :http-request="upload"
        >
            <template #trigger>
                <el-button type="primary">选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                立即导入
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    请按照以上格式上传excel文件
                </div>
            </template>
        </el-upload>
    </template>
    <template v-if="type === 'stype'">
        <p style="color: red">提示：按照以下格式上传,第一行是表头</p>
        <table class="modal-table">
            <tr>
                                                                                  <td style="background-color: #0FB25F">类型名</td>
                                                             <td style="background-color: #0FB25F">说明</td>
                            </tr>
            <tr>
                                                                                                              <td>类型名...</td>
                                                                                         <td>说明...</td>
                            </tr>
            <tr>
                                                                                  <td>...</td>
                                                             <td>...</td>
                            </tr>
        </table>
        <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="#"
                :limit="1"
                :auto-upload="false"
                :on-exceed="handleExceed"
                :http-request="upload"
        >
            <template #trigger>
                <el-button type="primary">选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                立即导入
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    请按照以上格式上传excel文件
                </div>
            </template>
        </el-upload>
    </template>
    <template v-if="type === 'cart'">
        <p style="color: red">提示：按照以下格式上传,第一行是表头</p>
        <table class="modal-table">
            <tr>
                                                                                  <td style="background-color: #0FB25F">用户</td>
                                                             <td style="background-color: #0FB25F">商品</td>
                                                             <td style="background-color: #0FB25F">商品图片</td>
                                                             <td style="background-color: #0FB25F">价格</td>
                                                             <td style="background-color: #0FB25F">数量</td>
                                                             <td style="background-color: #0FB25F">总价</td>
                                                             <td style="background-color: #0FB25F">加购时间</td>
                            </tr>
            <tr>
                                                                                                              <td>确保该用户在用户表中存在</td>
                                                                                                 <td>确保该商品在商品信息表中存在</td>
                                                                                                 <td>商品图片...</td>
                                                                                         <td>价格...</td>
                                                                                         <td>数量...</td>
                                                                                         <td>总价...</td>
                                                                                         <td>格式例：1999年06月05日 12:15:00</td>
                                    </tr>
            <tr>
                                                                                  <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                            </tr>
        </table>
        <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="#"
                :limit="1"
                :auto-upload="false"
                :on-exceed="handleExceed"
                :http-request="upload"
        >
            <template #trigger>
                <el-button type="primary">选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                立即导入
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    请按照以上格式上传excel文件
                </div>
            </template>
        </el-upload>
    </template>
    <template v-if="type === 'orderdetail'">
        <p style="color: red">提示：按照以下格式上传,第一行是表头</p>
        <table class="modal-table">
            <tr>
                                                                                  <td style="background-color: #0FB25F">用户</td>
                                                             <td style="background-color: #0FB25F">商品</td>
                                                             <td style="background-color: #0FB25F">商家</td>
                                                             <td style="background-color: #0FB25F">商品图片</td>
                                                             <td style="background-color: #0FB25F">价格</td>
                                                             <td style="background-color: #0FB25F">数量</td>
                                                             <td style="background-color: #0FB25F">总价</td>
                                                             <td style="background-color: #0FB25F">时间</td>
                            </tr>
            <tr>
                                                                                                              <td>确保该用户在用户表中存在</td>
                                                                                                 <td>确保该商品在商品信息表中存在</td>
                                                                                                 <td>确保该商家在商家表中存在</td>
                                                                                                 <td>商品图片...</td>
                                                                                         <td>价格...</td>
                                                                                         <td>数量...</td>
                                                                                         <td>总价...</td>
                                                                                         <td>格式例：1999年06月05日 12:15:00</td>
                                    </tr>
            <tr>
                                                                                  <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                            </tr>
        </table>
        <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="#"
                :limit="1"
                :auto-upload="false"
                :on-exceed="handleExceed"
                :http-request="upload"
        >
            <template #trigger>
                <el-button type="primary">选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                立即导入
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    请按照以上格式上传excel文件
                </div>
            </template>
        </el-upload>
    </template>
    <template v-if="type === 'order'">
        <p style="color: red">提示：按照以下格式上传,第一行是表头</p>
        <table class="modal-table">
            <tr>
                                                                                  <td style="background-color: #0FB25F">用户</td>
                                                             <td style="background-color: #0FB25F">订单号</td>
                                                             <td style="background-color: #0FB25F">收货人</td>
                                                             <td style="background-color: #0FB25F">收货人电话</td>
                                                             <td style="background-color: #0FB25F">收货人地址</td>
                                                             <td style="background-color: #0FB25F">订单备注</td>
                                                             <td style="background-color: #0FB25F">下单时间</td>
                                                             <td style="background-color: #0FB25F">商家</td>
                                                             <td style="background-color: #0FB25F">订单内容</td>
                                                             <td style="background-color: #0FB25F">订单详情</td>
                                                             <td style="background-color: #0FB25F">总价格</td>
                                                             <td style="background-color: #0FB25F">状态</td>
                                                             <td style="background-color: #0FB25F">物流单号</td>
                                                             <td style="background-color: #0FB25F">物流信息</td>
                                                             <td style="background-color: #0FB25F">评价</td>
                            </tr>
            <tr>
                                                                                                              <td>确保该用户在用户表中存在</td>
                                                                                                 <td>订单号...</td>
                                                                                         <td>收货人...</td>
                                                                                         <td>收货人电话...</td>
                                                                                         <td>收货人地址...</td>
                                                                                         <td>订单备注...</td>
                                                                                         <td>格式例：1999年06月05日 12:15:00</td>
                                                                                                 <td>确保该商家在商家表中存在</td>
                                                                                                 <td>订单内容...</td>
                                                                                         <td>订单详情...</td>
                                                                                         <td>总价格...</td>
                                                                                         <td>
                                                                    已下单、                                                                    已支付、                                                                    已送达、                                                                    已退款、                                                                    售后中、                                                                    售后完成                                                            </td>
                                                                                                 <td>物流单号...</td>
                                                                                         <td>物流信息...</td>
                                                                                         <td>评价...</td>
                            </tr>
            <tr>
                                                                                  <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                            </tr>
        </table>
        <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="#"
                :limit="1"
                :auto-upload="false"
                :on-exceed="handleExceed"
                :http-request="upload"
        >
            <template #trigger>
                <el-button type="primary">选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                立即导入
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    请按照以上格式上传excel文件
                </div>
            </template>
        </el-upload>
    </template>
    <template v-if="type === 'afterSell'">
        <p style="color: red">提示：按照以下格式上传,第一行是表头</p>
        <table class="modal-table">
            <tr>
                                                                                  <td style="background-color: #0FB25F">用户</td>
                                                             <td style="background-color: #0FB25F">订单号</td>
                                                             <td style="background-color: #0FB25F">售后类型</td>
                                                             <td style="background-color: #0FB25F">要求售后内容</td>
                                                             <td style="background-color: #0FB25F">产品问题拍照上传</td>
                                                             <td style="background-color: #0FB25F">售后申请时间</td>
                                                             <td style="background-color: #0FB25F">处理状态</td>
                                                             <td style="background-color: #0FB25F">处理结果</td>
                            </tr>
            <tr>
                                                                                                              <td>确保该用户在用户表中存在</td>
                                                                                                 <td>确保该订单号在用户订单表中存在</td>
                                                                                                 <td>售后类型...</td>
                                                                                         <td>要求售后内容...</td>
                                                                                         <td>产品问题拍照上传...</td>
                                                                                         <td>格式例：1999年06月05日 12:15:00</td>
                                                                                                 <td>
                                                                    待处理、                                                                    已处理                                                            </td>
                                                                                                 <td>处理结果...</td>
                            </tr>
            <tr>
                                                                                  <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                            </tr>
        </table>
        <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="#"
                :limit="1"
                :auto-upload="false"
                :on-exceed="handleExceed"
                :http-request="upload"
        >
            <template #trigger>
                <el-button type="primary">选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                立即导入
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    请按照以上格式上传excel文件
                </div>
            </template>
        </el-upload>
    </template>
    <template v-if="type === 'notice'">
        <p style="color: red">提示：按照以下格式上传,第一行是表头</p>
        <table class="modal-table">
            <tr>
                                                                                  <td style="background-color: #0FB25F">标题</td>
                                                             <td style="background-color: #0FB25F">内容</td>
                                                             <td style="background-color: #0FB25F">发布时间</td>
                            </tr>
            <tr>
                                                                                                              <td>标题...</td>
                                                                                         <td>内容...</td>
                                                                                         <td>格式例：1999年06月05日 12:15:00</td>
                                    </tr>
            <tr>
                                                                                  <td>...</td>
                                                             <td>...</td>
                                                             <td>...</td>
                            </tr>
        </table>
        <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="#"
                :limit="1"
                :auto-upload="false"
                :on-exceed="handleExceed"
                :http-request="upload"
        >
            <template #trigger>
                <el-button type="primary">选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                立即导入
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    请按照以上格式上传excel文件
                </div>
            </template>
        </el-upload>
    </template>
</template>

<script setup>
    import {ref, shallowRef} from 'vue'
    import { genFileId } from 'element-plus'
    let callback //上传成功回调函数
    let type = ref('')
    let uploadRef = shallowRef();

    const render = (type_,callback_) => {
        console.log(type_)
        type.value = type_
        callback = callback_
    }

    const submitUpload = async ()=>{
        uploadRef.value.submit()
    }

    //上传
    const upload = async (options) => {
        Msg.loading("上传中...")
        let {success,message} = await Http.upload(options.file, options.data, `/${type.value}/excel`);
        Msg.loading(false);
        if(success){
            Msg.success(message)
            if(callback){ //上传传功回调
                callback()
            }
        }
        else{
            Msg.error(message)
        }

    }

    //溢出则覆盖
    const handleExceed = (files) => {
        uploadRef.value.clearFiles()
        const file = files[0]
        file.uid = genFileId()
        uploadRef.value.handleStart(file)
    }

    defineExpose({render});
</script>

<style scoped>
    .modal-table td{
        border: 1px solid;
        padding: 3px;
    }
</style>
