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
