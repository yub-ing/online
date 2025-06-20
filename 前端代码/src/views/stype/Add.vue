<template>
    <el-form label-position="left" label-width="100px" ref="formRef" :model="form" style="max-width: 600px" :rules="rules">
        <el-form-item label="类型名" prop="name">
            <el-input maxlength="18" v-model="form.name" />
        </el-form-item>
        <el-form-item label="说明" prop="describtion">
            <el-input maxlength="255" v-model="form.describtion" />
        </el-form-item>
    </el-form>
</template>
<script setup>
    import {  reactive, ref } from 'vue'
    let user = Cache.getUser()//当前登录用户
    const formRef = ref();
    let form = ref({
        name : '',
        describtion : '',
    });
    let callBack;//保存成功回调函数
    const rules = reactive({
        name:{required: true, message: "类型名必填", trigger: "blur"},
    })

    //初始化
    const render = (loadThisPage,params) => {
      callBack = loadThisPage
      if(params){
        //将params的所有属性拷贝给form.value
        Object.assign(form.value,params)
      }
    }


    //提交
    const submit = async () => {

        await formRef.value.validate(async (isValid, invalidFields) => {
            if(! isValid) {
                Msg.error(Helper.getFirstMessage(invalidFields));
                return;
            }
            let {success,message} = await Http.post(`/stype/save`, form.value);
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

