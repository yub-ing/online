<template>
    <el-form label-position="left" label-width="100px" ref="formRef" :model="form" style="max-width: 600px" :rules="rules">
         <el-form-item label="用户" prop="userid">
            <x-select-table
                :disabled="user.role==='user'"                :header="[{label:'用户',field:'username',width:'100%'}]"
                v-model="form.userid"
                :queryParams="[{name:'username',label:'用户'}/*,{name:'state',label:'状态',defaultValue:'上架',type:'hide'}*/]"
                api="/user/list"
                labelField="username"
                valueField="id"
            ></x-select-table>
        </el-form-item>
        <el-form-item label="收货人" prop="sname">
            <el-input maxlength="20" v-model="form.sname" />
        </el-form-item>
        <el-form-item label="收货人电话" prop="stele">
            <el-input maxlength="11" v-model="form.stele" />
        </el-form-item>
        <el-form-item label="收货人地址" prop="saddr">
            <el-input maxlength="255" v-model="form.saddr" />
        </el-form-item>
        <el-form-item label="订单备注" prop="dingdbz">
            <el-input maxlength="255" v-model="form.dingdbz" />
        </el-form-item>
    </el-form>
</template>
<script setup>
    import {  reactive, ref } from 'vue'
    let user = Cache.getUser()//当前登录用户
    const formRef = ref();
    let form = ref({
         userid : user.id,
        sname : '',
        stele : '',
        saddr : '',
        dingdbz : '',
    });
    let callBack;//保存成功回调函数
    const rules = reactive({
        userid:{required: true, message: "用户必填", trigger: "blur"},
        sname:{required: true, message: "收货人必填", trigger: "blur"},
        stele:{required: true, message: "收货人电话必填", trigger: "blur"},
        saddr:{required: true, message: "收货人地址必填", trigger: "blur"},
        dingdbz:{required: true, message: "订单备注必填", trigger: "blur"},
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
            let {success,message} = await Http.post(`/order/save`, form.value);
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

