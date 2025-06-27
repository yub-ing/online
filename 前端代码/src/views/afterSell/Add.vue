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
         <el-form-item label="订单号" prop="oid">
            <x-select-table
                                :header="[{label:'用户订单',field:'numb',width:'100%'}]"
                v-model="form.oid"
                :queryParams="[{name:'numb',label:'用户订单'}/*,{name:'state',label:'状态',defaultValue:'上架',type:'hide'}*/]"
                api="/order/list"
                labelField="numb"
                valueField="id"
            ></x-select-table>
        </el-form-item>
        <el-form-item label="售后类型" prop="typer">
            <el-input maxlength="255" v-model="form.typer" />
        </el-form-item>
        <el-form-item label="要求售后内容" prop="content">
            <el-input maxlength="255" v-model="form.content" />
        </el-form-item>
        <el-form-item label="产品问题拍照上传" prop="picFile">
            <x-uploader :count="1" v-model="picFile"></x-uploader>
        </el-form-item>
    </el-form>
</template>
<script setup>
    import {  reactive, ref } from 'vue'
    let user = Cache.getUser()//当前登录用户
    const formRef = ref();
    let form = ref({
         userid : user.id,
        oid : '',
        typer : '',
        content : '',
        pic : '',
    });
    let callBack;//保存成功回调函数
    const rules = reactive({
        userid:{required: true, message: "用户必填", trigger: "blur"},
        oid:{required: true, message: "订单号必填", trigger: "blur"},
        typer:{required: true, message: "售后类型必填", trigger: "blur"},
        content:{required: true, message: "要求售后内容必填", trigger: "blur"},
        pic:{required: true, message: "产品问题拍照上传必填", trigger: "blur"},
    })

    //初始化
    const render = (loadThisPage,params) => {
      callBack = loadThisPage
      if(params){
        //将params的所有属性拷贝给form.value
        Object.assign(form.value,params)
      }
    }

    const picFile = ref([]) //产品问题拍照上传

    //提交
    const submit = async () => {
    //文件：产品问题拍照上传
    form.value.pic = picFile.value.length>0 ? picFile.value[0].fileId : ''

        await formRef.value.validate(async (isValid, invalidFields) => {
            if(! isValid) {
                Msg.error(Helper.getFirstMessage(invalidFields));
                return;
            }
            let {success,message} = await Http.post(`/afterSell/save`, form.value);
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

