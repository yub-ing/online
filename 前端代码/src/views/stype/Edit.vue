<template>
    <el-form label-position="left" label-width="100px" ref="formRef" :model="form" style="max-width: 600px" :rules="rules">
                                                                            <el-form-item label="类型名" prop="name">
                        <el-input  maxlength="18" v-model="form.name" />
                    </el-form-item>
                                                                                    <el-form-item label="说明" prop="describtion">
                        <el-input  maxlength="255" v-model="form.describtion" />
                    </el-form-item>
                                        </el-form>
</template>
<script setup>
    import {  reactive, ref } from 'vue'
    const formRef = ref();
    let user = Cache.getUser()//当前登录用户
    let form = ref({});
    let callBack;//保存成功回调函数
    const rules = reactive({
        name:{required: true, message: "类型名必填", trigger: "blur"},
    })


    const render = async (id,loadThisPage) => {
        Msg.loading("加载中..")
        let {data} = await Http.get(`/stype/detail`, {id: id});
        form.value = data
        callBack = loadThisPage
        Msg.loading(false)
    }
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
            callBack(form.value)

            if(user.id === form.value.id){//用户修改自己的信息后刷新
              window.location.reload();
            }
        });
    }

    defineExpose({render,submit});

</script>

