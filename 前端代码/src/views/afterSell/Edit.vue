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
                                                                                     <el-form-item label="订单号" prop="oid">
                        <x-select-table
                            disabled                                :header="[{label:'用户订单',field:'numb',width:'100%'}]"
                                        v-model="form.oid"
                                        :queryParams="[{name:'numb',label:'用户订单'}]"
                                        api="/order/list"
                                        labelField="numb"
                                        valueField="id"
                        ></x-select-table>
                    </el-form-item>
                                                                                    <el-form-item label="售后类型" prop="typer">
                        <el-input disabled maxlength="255" v-model="form.typer" />
                    </el-form-item>
                                                                                    <el-form-item label="要求售后内容" prop="content">
                        <el-input disabled maxlength="255" v-model="form.content" />
                    </el-form-item>
                                                                                    <el-form-item label="产品问题拍照上传" prop="picFile">
                        <x-uploader disabled :count="1" v-model="picFile"></x-uploader>
                    </el-form-item>
                                                                                    <el-form-item label="售后申请时间" prop="createtime">
                        <el-date-picker disabled value-format="YYYY-MM-DD HH:mm:ss" v-model="form.createtime" type="datetime" placeholder="选择时间" :shortcuts="shortcuts"/>
                    </el-form-item>
                                                                                    <el-form-item label="处理状态" prop="state">
                        <el-select  filterable v-model="form.state" clearable placeholder="Select">
                            <el-option v-for="item in stateOptionList" :key="item.name" :label="item.name" :value="item.name"/>
                        </el-select>
                    </el-form-item>
                                                                                    <el-form-item label="处理结果" prop="chuljg">
                        <el-input  maxlength="255" v-model="form.chuljg" />
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
        oid:{required: true, message: "订单号必填", trigger: "blur"},
        typer:{required: true, message: "售后类型必填", trigger: "blur"},
        content:{required: true, message: "要求售后内容必填", trigger: "blur"},
        pic:{required: true, message: "产品问题拍照上传必填", trigger: "blur"},
        createtime:{required: true, message: "售后申请时间必填", trigger: "blur"},
        state:{required: true, message: "处理状态必填", trigger: "blur"},
    })

     let stateOptionList = ref([  { name:'待处理'}, { name:'已处理'}, ]) //处理状态 下拉框数据
    const picFile = ref([]) //产品问题拍照上传

    const render = async (id,loadThisPage) => {
        Msg.loading("加载中..")
        let {data} = await Http.get(`/afterSell/detail`, {id: id});
        form.value = data
        callBack = loadThisPage
        //回显文件：产品问题拍照上传
        if(form.value.pic){
            picFile.value.push({fileId:form.value.pic})
        }
        Msg.loading(false)
    }
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
            callBack(form.value)

            if(user.id === form.value.id){//用户修改自己的信息后刷新
              window.location.reload();
            }
        });
    }

    defineExpose({render,submit});

</script>

