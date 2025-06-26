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
            <el-input disabled v-model="form.numb"  />
        </el-form-item>
                 <el-form-item label="收货人">
            <el-input disabled v-model="form.sname"  />
        </el-form-item>
                 <el-form-item label="收货人电话">
            <el-input disabled v-model="form.stele"  />
        </el-form-item>
                 <el-form-item label="收货人地址">
            <el-input disabled v-model="form.saddr"  />
        </el-form-item>
                 <el-form-item label="订单备注">
            <el-input disabled v-model="form.dingdbz"  />
        </el-form-item>
                 <el-form-item label="下单时间">
            <el-date-picker disabled value-format="YYYY-MM-DD HH:mm:ss" v-model="form.createtime" type="datetime" :shortcuts="shortcuts"/>
        </el-form-item>
                 <el-form-item label="商家">
            <el-button v-if="form.midFrn" type="primary" text bg @click="midDetail(form.mid)">{{ form.midFrn.name }}</el-button>
        </el-form-item>
                 <el-form-item label="订单内容">
            <el-input disabled v-model="form.content"  />
        </el-form-item>
                 <el-form-item label="订单详情">
            <el-input disabled v-model="form.details"  autosize type="textarea" />
        </el-form-item>
                 <el-form-item label="总价格">
            <el-input disabled v-model="form.price"  />
        </el-form-item>
                 <el-form-item label="状态">
            <el-input disabled v-model="form.state"  />
        </el-form-item>
                 <el-form-item label="物流单号">
            <el-input disabled v-model="form.wuliu"  />
        </el-form-item>
                 <el-form-item label="物流信息">
            <el-input disabled v-model="form.wulxx"  autosize type="textarea" />
        </el-form-item>
                 <el-form-item label="评价">
            <el-input disabled v-model="form.pingj"  />
        </el-form-item>
        </el-form>


</template>
<script setup>
    let commentRef = ref(null)
    let form = ref({ useridFrn:null,midFrn:null, });

    const render = async (id) => {
        Msg.loading("loading...")
        let {data} = await Http.get(`/order/detail`, {id});
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
     //商家详情页
    import midDetailPage from "../merchant/Detail";
    const midDetail = async (id)=> {
        const op = Dialog.open(midDetailPage, '详情').setCancelText('').setConfirmText('')
        op.mounted(c => {
            c.render(id)
        })
    }

    defineExpose({render});

</script>

