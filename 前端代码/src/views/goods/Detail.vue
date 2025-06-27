<template>
    <el-form
            label-position="left"
            label-width="100px"
            :model="form"
            style="max-width: 600px"
    >
            <el-form-item label="首页图片">
            <x-file-view :disabled="true" :list="form.showpic"></x-file-view>
        </el-form-item>
                 <el-form-item label="商品名称">
            <el-input disabled v-model="form.showtitle"  />
        </el-form-item>
                 <el-form-item label="简介">
            <el-input disabled v-model="form.showdesc"  />
        </el-form-item>
                 <el-form-item label="类型">
            <el-button v-if="form.leixFrn" type="primary" text bg @click="leixDetail(form.leix)">{{ form.leixFrn.name }}</el-button>
        </el-form-item>
                 <el-form-item label="商家">
            <el-button v-if="form.midFrn" type="primary" text bg @click="midDetail(form.mid)">{{ form.midFrn.name }}</el-button>
        </el-form-item>
                 <el-form-item label="价格">
            <el-input disabled v-model="form.price"  />
        </el-form-item>
                 <el-form-item label="商品内容">
            <el-input disabled v-model="form.showdetail"  autosize type="textarea" />
        </el-form-item>
                 <el-form-item label="库存数量">
            <el-input disabled v-model="form.count"  />
        </el-form-item>
                 <el-form-item label="发布时间">
            <el-date-picker disabled value-format="YYYY-MM-DD HH:mm:ss" v-model="form.publishtime" type="datetime" :shortcuts="shortcuts"/>
        </el-form-item>
                <el-form-item label="展示">
            <x-file-view :disabled="true" :list="form.vv"></x-file-view>
        </el-form-item>
        </el-form>

    <x-comment ref="commentRef"></x-comment>

</template>
<script setup>
    let commentRef = ref(null)
    let form = ref({ leixFrn:null,midFrn:null, });

    const render = async (id) => {
        Msg.loading("loading...")
        let {data} = await Http.get(`/goods/detail`, {id});
        console.log(data)
        form.value = data;
        commentRef.value.init(form.value.id,'goods')
        Msg.loading(false)
    }

     //类型详情页
    import leixDetailPage from "../stype/Detail";
    const leixDetail = async (id)=> {
        const op = Dialog.open(leixDetailPage, '详情').setCancelText('').setConfirmText('')
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

