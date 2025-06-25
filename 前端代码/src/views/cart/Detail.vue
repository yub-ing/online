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
                 <el-form-item label="商品">
            <el-button v-if="form.goodsidFrn" type="primary" text bg @click="goodsidDetail(form.goodsid)">{{ form.goodsidFrn.showtitle }}</el-button>
        </el-form-item>
                <el-form-item label="商品图片">
            <x-file-view :disabled="true" :list="form.tustp"></x-file-view>
        </el-form-item>
                 <el-form-item label="价格">
            <el-input disabled v-model="form.price"  />
        </el-form-item>
                 <el-form-item label="数量">
            <el-input disabled v-model="form.count"  />
        </el-form-item>
                 <el-form-item label="总价">
            <el-input disabled v-model="form.zongj"  />
        </el-form-item>
                 <el-form-item label="加购时间">
            <el-date-picker disabled value-format="YYYY-MM-DD HH:mm:ss" v-model="form.createtime" type="datetime" :shortcuts="shortcuts"/>
        </el-form-item>
        </el-form>


</template>
<script setup>
    let commentRef = ref(null)
    let form = ref({ useridFrn:null,goodsidFrn:null, });

    const render = async (id) => {
        Msg.loading("loading...")
        let {data} = await Http.get(`/cart/detail`, {id});
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
     //商品详情页
    import goodsidDetailPage from "../goods/Detail";
    const goodsidDetail = async (id)=> {
        const op = Dialog.open(goodsidDetailPage, '详情').setCancelText('').setConfirmText('')
        op.mounted(c => {
            c.render(id)
        })
    }

    defineExpose({render});

</script>

