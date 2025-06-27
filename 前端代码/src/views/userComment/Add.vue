<template>
    <el-form label-position="left" label-width="100px" ref="formRef" :model="form" style="max-width: 600px" :rules="rules">
        <el-form-item label="用户编号" prop="userid">
            <el-input maxlength="255" v-model="form.userid" />
        </el-form-item>
        <el-form-item label="用户名" prop="username">
            <el-input maxlength="255" v-model="form.username" />
        </el-form-item>
        <el-form-item label="用户角色" prop="rolech">
            <el-input maxlength="255" v-model="form.rolech" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
            <el-input maxlength="255" v-model="form.content" />
        </el-form-item>
        <el-form-item label="内容编号" prop="ctid">
            <el-input maxlength="255" v-model="form.ctid" />
        </el-form-item>
        <el-form-item label="内容类型" prop="type">
            <el-input maxlength="255" v-model="form.type" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
            <el-select filterable v-model="form.status" clearable placeholder="Select">
                <el-option v-for="item in statusOptionList" :key="item.name" :label="item.name" :value="item.name"/>
            </el-select>
        </el-form-item>
    </el-form>
</template>
<script setup>
    import {  reactive, ref } from 'vue'
    let user = Cache.getUser()//当前登录用户
    const formRef = ref();
    let form = ref({
        userid : '',
        username : '',
        rolech : '',
        content : '',
        ctid : '',
        type : '',
        status : '',
    });
    let callBack;//保存成功回调函数
    const rules = reactive({
        userid:{required: true, message: "用户编号必填", trigger: "blur"},
        username:{required: true, message: "用户名必填", trigger: "blur"},
        rolech:{required: true, message: "用户角色必填", trigger: "blur"},
        content:{required: true, message: "内容必填", trigger: "blur"},
        ctid:{required: true, message: "内容编号必填", trigger: "blur"},
        type:{required: true, message: "内容类型必填", trigger: "blur"},
    })

     let statusOptionList = ref([  { name:'正常'}, { name:'违规'}, ]) //状态 下拉框数据
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
            let {success,message} = await Http.post(`/userComment/save`, form.value);
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

