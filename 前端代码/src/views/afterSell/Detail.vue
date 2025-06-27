<template>
    <el-form
            label-position="left"
            label-width="100px"
            :model="form"
            style="max-width: 600px"
    >
             <el-form-item label="用户">
            <el-button v-if="form.useridFrn" type="primary" text bg @click="useridDetail(form.userid)">{{ form.useridFrn.username }}</el-button>
        </el-form-item>
                 <el-form-item label="订单号">
            <el-button v-if="form.oidFrn" type="primary" text bg @click="oidDetail(form.oid)">{{ form.oidFrn.numb }}</el-button>
        </el-form-item>
                 <el-form-item label="售后类型">
            <el-input disabled v-model="form.typer"  />
        </el-form-item>
                 <el-form-item label="要求售后内容">
            <el-input disabled v-model="form.content"  />
        </el-form-item>
                <el-form-item label="产品问题拍照上传">
            <x-file-view :disabled="true" :list="form.pic"></x-file-view>
        </el-form-item>
                 <el-form-item label="售后申请时间">
            <el-date-picker disabled value-format="YYYY-MM-DD HH:mm:ss" v-model="form.createtime" type="datetime" :shortcuts="shortcuts"/>
        </el-form-item>
                 <el-form-item label="处理状态">
            <el-input disabled v-model="form.state"  />
        </el-form-item>
                 <el-form-item label="处理结果">
            <el-input disabled v-model="form.chuljg"  />
        </el-form-item>
        </el-form>


</template>
<script setup>
    let commentRef = ref(null)
    let form = ref({ useridFrn:null,oidFrn:null, });

    const render = async (id) => {
        Msg.loading("loading...")
        let {data} = await Http.get(`/afterSell/detail`, {id});
        console.log(data)
        form.value = data;
        Msg.loading(false)
    }

     //用户详情页
    import useridDetailPage from "../user/Detail";
    const useridDetail = async (id)=> {
        const op = Dialog.open(useridDetailPage, '详情').setCancelText('').setConfirmText('')
        op.mounted(c => {
            c.render(id)
        })
    }
     //订单号详情页
    import oidDetailPage from "../order/Detail";
    const oidDetail = async (id)=> {
        const op = Dialog.open(oidDetailPage, '详情').setCancelText('').setConfirmText('')
        op.mounted(c => {
            c.render(id)
        })
    }

    defineExpose({render});

</script>

