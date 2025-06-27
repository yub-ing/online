<template>
    <el-form label-position="left" label-width="100px" ref="formRef" :model="form" style="max-width: 600px" :rules="rules">
                                                                                                <el-form-item label="用户名" prop="username">
                        <el-input disabled maxlength="255" v-model="form.username" />
                    </el-form-item>
                                                                                    <el-form-item label="用户角色" prop="rolech">
                        <el-input disabled maxlength="255" v-model="form.rolech" />
                    </el-form-item>
                                                                                    <el-form-item label="内容" prop="content">
                        <el-input disabled maxlength="255" v-model="form.content" />
                    </el-form-item>
                                                                                    <el-form-item label="发布时间" prop="createtime">
                        <el-date-picker disabled value-format="YYYY-MM-DD HH:mm:ss" v-model="form.createtime" type="datetime" placeholder="选择时间" :shortcuts="shortcuts"/>
                    </el-form-item>
                                                                                                                            <el-form-item label="状态" prop="status">
                        <el-select  filterable v-model="form.status" clearable placeholder="Select">
                            <el-option v-for="item in statusOptionList" :key="item.name" :label="item.name" :value="item.name"/>
                        </el-select>
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
        username:{required: true, message: "用户名必填", trigger: "blur"},
        rolech:{required: true, message: "用户角色必填", trigger: "blur"},
        content:{required: true, message: "内容必填", trigger: "blur"},
        createtime:{required: true, message: "发布时间必填", trigger: "blur"},
    })

     let statusOptionList = ref([  { name:'正常'}, { name:'违规'}, ]) //状态 下拉框数据

    const render = async (id,loadThisPage) => {
        Msg.loading("加载中..")
        let {data} = await Http.get(`/userComment/detail`, {id: id});
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
            let {success,message} = await Http.post(`/userComment/save`, form.value);
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

