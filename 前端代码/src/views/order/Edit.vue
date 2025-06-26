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
                                                                                    <el-form-item label="订单号" prop="numb">
                        <el-input disabled maxlength="255" v-model="form.numb" />
                    </el-form-item>
                                                                                    <el-form-item label="收货人" prop="sname">
                        <el-input disabled maxlength="20" v-model="form.sname" />
                    </el-form-item>
                                                                                    <el-form-item label="收货人电话" prop="stele">
                        <el-input disabled maxlength="11" v-model="form.stele" />
                    </el-form-item>
                                                                                    <el-form-item label="收货人地址" prop="saddr">
                        <el-input disabled maxlength="255" v-model="form.saddr" />
                    </el-form-item>
                                                                                    <el-form-item label="订单备注" prop="dingdbz">
                        <el-input disabled maxlength="255" v-model="form.dingdbz" />
                    </el-form-item>
                                                                                    <el-form-item label="下单时间" prop="createtime">
                        <el-date-picker disabled value-format="YYYY-MM-DD HH:mm:ss" v-model="form.createtime" type="datetime" placeholder="选择时间" :shortcuts="shortcuts"/>
                    </el-form-item>
                                                                                     <el-form-item label="商家" prop="mid">
                        <x-select-table
                            disabled                                :header="[{label:'商家',field:'name',width:'100%'}]"
                                        v-model="form.mid"
                                        :queryParams="[{name:'name',label:'商家'}]"
                                        api="/merchant/list"
                                        labelField="name"
                                        valueField="id"
                        ></x-select-table>
                    </el-form-item>
                                                                                    <el-form-item label="订单内容" prop="content">
                        <el-input disabled maxlength="255" v-model="form.content" />
                    </el-form-item>
                                                                                    <el-form-item label="订单详情" prop="details">
                        <el-input disabled v-model="form.details" autosize type="textarea"/>
                    </el-form-item>
                                                                                    <el-form-item label="总价格" prop="price">
                        <el-input disabled  v-model.number="form.price" type="number"/>
                    </el-form-item>
                                                                                    <el-form-item label="状态" prop="state">
                        <el-select disabled filterable v-model="form.state" clearable placeholder="Select">
                            <el-option v-for="item in stateOptionList" :key="item.name" :label="item.name" :value="item.name"/>
                        </el-select>
                    </el-form-item>
                                                                                    <el-form-item label="物流单号" prop="wuliu">
                        <el-input  maxlength="255" v-model="form.wuliu" />
                    </el-form-item>
                                                                                    <el-form-item label="物流信息" prop="wulxx">
                        <el-input  v-model="form.wulxx" autosize type="textarea"/>
                    </el-form-item>
                                                                                    <el-form-item label="评价" prop="pingj">
                        <el-input  maxlength="255" v-model="form.pingj" />
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
        numb:{required: true, message: "订单号必填", trigger: "blur"},
        sname:{required: true, message: "收货人必填", trigger: "blur"},
        stele:{required: true, message: "收货人电话必填", trigger: "blur"},
        saddr:{required: true, message: "收货人地址必填", trigger: "blur"},
        dingdbz:{required: true, message: "订单备注必填", trigger: "blur"},
        createtime:{required: true, message: "下单时间必填", trigger: "blur"},
        mid:{required: true, message: "商家必填", trigger: "blur"},
        content:{required: true, message: "订单内容必填", trigger: "blur"},
        details:{required: true, message: "订单详情必填", trigger: "blur"},
        price:{required: true, message: "总价格必填", trigger: "blur"},
        state:{required: true, message: "状态必填", trigger: "blur"},
    })

     let stateOptionList = ref([  { name:'已下单'}, { name:'已支付'}, { name:'已送达'}, { name:'已退款'}, ]) //状态 下拉框数据

    const render = async (id,loadThisPage) => {
        Msg.loading("加载中..")
        let {data} = await Http.get(`/order/detail`, {id: id});
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
            let {success,message} = await Http.post(`/order/save`, form.value);
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

