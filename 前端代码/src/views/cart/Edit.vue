<template>
    <el-form label-position="left" label-width="100px" ref="formRef" :model="form" style="max-width: 600px" :rules="rules">
                                                                             <el-form-item label="用户" prop="userid">
                        <x-select-table
                            disabled                                :header="[{label:'用户',field:'username',width:'100%'}]"
                                        v-model="form.userid"
                                        :queryParams="[{name:'username',label:'用户'}]"
                                        api="/user/list"
                                        labelField="username"
                                        valueField="id"
                        ></x-select-table>
                    </el-form-item>
                                                                                     <el-form-item label="商品" prop="goodsid">
                        <x-select-table
                            disabled                                :header="[{label:'商品信息',field:'showtitle',width:'100%'}]"
                                        v-model="form.goodsid"
                                        :queryParams="[{name:'showtitle',label:'商品信息'}]"
                                        api="/goods/list"
                                        labelField="showtitle"
                                        valueField="id"
                        ></x-select-table>
                    </el-form-item>
                                                                                    <el-form-item label="商品图片" prop="tustpFile">
                        <x-uploader disabled :count="1" v-model="tustpFile"></x-uploader>
                    </el-form-item>
                                                                                    <el-form-item label="价格" prop="price">
                        <el-input disabled  v-model.number="form.price" type="number"/>
                    </el-form-item>
                                                                                    <el-form-item label="数量" prop="count">
                        <el-input   v-model.number="form.count" type="number"/>
                    </el-form-item>
                                                                                                        <el-form-item label="加购时间" prop="createtime">
                        <el-date-picker disabled value-format="YYYY-MM-DD HH:mm:ss" v-model="form.createtime" type="datetime" placeholder="选择时间" :shortcuts="shortcuts"/>
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
        userid:{required: true, message: "用户必填", trigger: "blur"},
        goodsid:{required: true, message: "商品必填", trigger: "blur"},
        tustp:{required: true, message: "商品图片必填", trigger: "blur"},
        price:{required: true, message: "价格必填", trigger: "blur"},
        count:{required: true, message: "数量必填", trigger: "blur"},
        createtime:{required: true, message: "加购时间必填", trigger: "blur"},
    })

    const tustpFile = ref([]) //商品图片

    const render = async (id,loadThisPage) => {
        Msg.loading("加载中..")
        let {data} = await Http.get(`/cart/detail`, {id: id});
        form.value = data
        callBack = loadThisPage
        //回显文件：商品图片
        if(form.value.tustp){
            tustpFile.value.push({fileId:form.value.tustp})
        }
        Msg.loading(false)
    }
    const submit = async () => {
    //文件：商品图片
    form.value.tustp = tustpFile.value.length>0 ? tustpFile.value[0].fileId : ''

        await formRef.value.validate(async (isValid, invalidFields) => {
            if(! isValid) {
                Msg.error(Helper.getFirstMessage(invalidFields));
                return;
            }
            let {success,message} = await Http.post(`/cart/save`, form.value);
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

