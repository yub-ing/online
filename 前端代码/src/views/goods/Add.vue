<template>
    <el-form label-position="left" label-width="100px" ref="formRef" :model="form" style="max-width: 600px" :rules="rules">
        <el-form-item label="首页图片" prop="showpicFile">
            <x-uploader :count="1" v-model="showpicFile"></x-uploader>
        </el-form-item>
        <el-form-item label="商品名称" prop="showtitle">
            <el-input maxlength="255" v-model="form.showtitle" />
        </el-form-item>
        <el-form-item label="简介" prop="showdesc">
            <el-input maxlength="20000" v-model="form.showdesc" />
        </el-form-item>
         <el-form-item label="类型" prop="leix">
            <x-select-table
                                :header="[{label:'商品类型',field:'name',width:'100%'}]"
                v-model="form.leix"
                :queryParams="[{name:'name',label:'商品类型'}/*,{name:'state',label:'状态',defaultValue:'上架',type:'hide'}*/]"
                api="/stype/list"
                labelField="name"
                valueField="id"
            ></x-select-table>
        </el-form-item>
         <el-form-item label="商家" prop="mid">
            <x-select-table
                :disabled="user.role==='merchant'"                :header="[{label:'商家',field:'name',width:'100%'}]"
                v-model="form.mid"
                :queryParams="[{name:'name',label:'商家'}/*,{name:'state',label:'状态',defaultValue:'上架',type:'hide'}*/]"
                api="/merchant/list"
                labelField="name"
                valueField="id"
            ></x-select-table>
        </el-form-item>
        <el-form-item label="价格" prop="price">
            <el-input  v-model.number="form.price" type="number"/>
        </el-form-item>
        <el-form-item label="商品内容" prop="showdetail">
            <el-input v-model="form.showdetail" type="textarea"/>
        </el-form-item>
        <el-form-item label="库存数量" prop="count">
            <el-input  v-model.number="form.count" type="number"/>
        </el-form-item>
        <el-form-item label="展示" prop="vvFile">
            <x-uploader :count="1" v-model="vvFile"></x-uploader>
        </el-form-item>
    </el-form>
</template>
<script setup>
    import {  reactive, ref } from 'vue'
    let user = Cache.getUser()//当前登录用户
    const formRef = ref();
    let form = ref({
        showpic : '',
        showtitle : '',
        showdesc : '',
        leix : '',
         mid : user.id,
        price : '',
        showdetail : '',
        count : '',
        vv : '',
    });
    let callBack;//保存成功回调函数
    const rules = reactive({
        showtitle:{required: true, message: "商品名称必填", trigger: "blur"},
        showdesc:{required: true, message: "简介必填", trigger: "blur"},
        price:{required: true, message: "价格必填", trigger: "blur"},
        showdetail:{required: true, message: "商品内容必填", trigger: "blur"},
        count:{required: true, message: "库存数量必填", trigger: "blur"},
    })

    //初始化
    const render = (loadThisPage,params) => {
      callBack = loadThisPage
      if(params){
        //将params的所有属性拷贝给form.value
        Object.assign(form.value,params)
      }
    }

    const showpicFile = ref([]) //首页图片
    const vvFile = ref([]) //展示

    //提交
    const submit = async () => {
    //文件：首页图片
    form.value.showpic = showpicFile.value.length>0 ? showpicFile.value[0].fileId : ''
    //文件：展示
    form.value.vv = vvFile.value.length>0 ? vvFile.value[0].fileId : ''

        await formRef.value.validate(async (isValid, invalidFields) => {
            if(! isValid) {
                Msg.error(Helper.getFirstMessage(invalidFields));
                return;
            }
            let {success,message} = await Http.post(`/goods/save`, form.value);
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

