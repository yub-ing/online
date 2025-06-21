<template>
    <el-form label-position="left" label-width="100px" ref="formRef" :model="form" style="max-width: 600px" :rules="rules">
                                                                            <el-form-item label="首页图片" prop="showpicFile">
                        <x-uploader  :count="1" v-model="showpicFile"></x-uploader>
                    </el-form-item>
                                                                                    <el-form-item label="商品名称" prop="showtitle">
                        <el-input  maxlength="255" v-model="form.showtitle" />
                    </el-form-item>
                                                                                    <el-form-item label="简介" prop="showdesc">
                        <el-input  maxlength="20000" v-model="form.showdesc" />
                    </el-form-item>
                                                                                     <el-form-item label="类型" prop="leix">
                        <x-select-table
                                                            :header="[{label:'商品类型',field:'name',width:'100%'}]"
                                        v-model="form.leix"
                                        :queryParams="[{name:'name',label:'商品类型'}]"
                                        api="/stype/list"
                                        labelField="name"
                                        valueField="id"
                        ></x-select-table>
                    </el-form-item>
                                                                                     <el-form-item label="商家" prop="mid">
                        <x-select-table
                            :disabled="user.role==='merchant'"                                :header="[{label:'商家',field:'name',width:'100%'}]"
                                        v-model="form.mid"
                                        :queryParams="[{name:'name',label:'商家'}]"
                                        api="/merchant/list"
                                        labelField="name"
                                        valueField="id"
                        ></x-select-table>
                    </el-form-item>
                                                                                    <el-form-item label="价格" prop="price">
                        <el-input   v-model.number="form.price" type="number"/>
                    </el-form-item>
                                                                                    <el-form-item label="商品内容" prop="showdetail">
                        <el-input  v-model="form.showdetail" autosize type="textarea"/>
                    </el-form-item>
                                                                                    <el-form-item label="库存数量" prop="count">
                        <el-input   v-model.number="form.count" type="number"/>
                    </el-form-item>
                                                                                                        <el-form-item label="展示" prop="vvFile">
                        <x-uploader  :count="1" v-model="vvFile"></x-uploader>
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
        showtitle:{required: true, message: "商品名称必填", trigger: "blur"},
        showdesc:{required: true, message: "简介必填", trigger: "blur"},
        price:{required: true, message: "价格必填", trigger: "blur"},
        showdetail:{required: true, message: "商品内容必填", trigger: "blur"},
        count:{required: true, message: "库存数量必填", trigger: "blur"},
    })

    const showpicFile = ref([]) //首页图片
    const vvFile = ref([]) //展示

    const render = async (id,loadThisPage) => {
        Msg.loading("加载中..")
        let {data} = await Http.get(`/goods/detail`, {id: id});
        form.value = data
        callBack = loadThisPage
        //回显文件：首页图片
        if(form.value.showpic){
            showpicFile.value.push({fileId:form.value.showpic})
        }
        //回显文件：展示
        if(form.value.vv){
            vvFile.value.push({fileId:form.value.vv})
        }
        Msg.loading(false)
    }
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
            callBack(form.value)

            if(user.id === form.value.id){//用户修改自己的信息后刷新
              window.location.reload();
            }
        });
    }

    defineExpose({render,submit});

</script>

