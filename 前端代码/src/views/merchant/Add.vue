<template>
    <el-form label-position="left" label-width="100px" ref="formRef" :model="form" style="max-width: 600px" :rules="rules">
        <el-form-item label="用户名" prop="username">
            <el-input maxlength="20" v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input maxlength="20" v-model="form.password" />
        </el-form-item>
        <el-form-item label="商家名" prop="name">
            <el-input maxlength="18" v-model="form.name" />
        </el-form-item>
        <el-form-item label="头像" prop="avatarFile">
            <x-uploader :count="1" v-model="avatarFile"></x-uploader>
        </el-form-item>
        <el-form-item label="电话" prop="tele">
            <el-input maxlength="11" v-model="form.tele" />
        </el-form-item>
        <el-form-item label="小店简介" prop="xiaodjj">
            <el-input maxlength="255" v-model="form.xiaodjj" />
        </el-form-item>
        <el-form-item label="主营内容" prop="zhuynr">
            <el-input maxlength="255" v-model="form.zhuynr" />
        </el-form-item>
    </el-form>
</template>
<script setup>
    import {  reactive, ref } from 'vue'
    let user = Cache.getUser()//当前登录用户
    const formRef = ref();
    let form = ref({
        username : '',
        password : '',
        name : '',
        avatar : '',
        tele : '',
        xiaodjj : '',
        zhuynr : '',
    });
    let callBack;//保存成功回调函数
    const rules = reactive({
        username:{required: true, message: "用户名必填", trigger: "blur"},
        name:{required: true, message: "商家名必填", trigger: "blur"},
        tele:{required: true, message: "电话必填", trigger: "blur"},
        xiaodjj:{required: true, message: "小店简介必填", trigger: "blur"},
        zhuynr:{required: true, message: "主营内容必填", trigger: "blur"},
    })

    //初始化
    const render = (loadThisPage,params) => {
      callBack = loadThisPage
      if(params){
        //将params的所有属性拷贝给form.value
        Object.assign(form.value,params)
      }
    }

    const avatarFile = ref([]) //头像

    //提交
    const submit = async () => {
    //文件：头像
    form.value.avatar = avatarFile.value.length>0 ? avatarFile.value[0].fileId : ''

        await formRef.value.validate(async (isValid, invalidFields) => {
            if(! isValid) {
                Msg.error(Helper.getFirstMessage(invalidFields));
                return;
            }
            let {success,message} = await Http.post(`/merchant/save`, form.value);
            if(! success) {
                Msg.error(message);
                return;
            }
            Msg.success(message);
            Dialog.close();
            if(callBack) {
                callBack()
            }
        });
    }

    defineExpose({render,submit});

</script>

