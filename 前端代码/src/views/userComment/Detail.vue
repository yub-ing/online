<template>
    <el-form
            label-position="left"
            label-width="100px"
            :model="form"
            style="max-width: 600px"
    >
             <el-form-item label="用户编号">
            <el-input disabled v-model="form.userid"  />
        </el-form-item>
                 <el-form-item label="用户名">
            <el-input disabled v-model="form.username"  />
        </el-form-item>
                 <el-form-item label="用户角色">
            <el-input disabled v-model="form.rolech"  />
        </el-form-item>
                 <el-form-item label="内容">
            <el-input disabled v-model="form.content"  />
        </el-form-item>
                 <el-form-item label="发布时间">
            <el-date-picker disabled value-format="YYYY-MM-DD HH:mm:ss" v-model="form.createtime" type="datetime" :shortcuts="shortcuts"/>
        </el-form-item>
                 <el-form-item label="内容编号">
            <el-input disabled v-model="form.ctid"  />
        </el-form-item>
                 <el-form-item label="内容类型">
            <el-input disabled v-model="form.type"  />
        </el-form-item>
                 <el-form-item label="状态">
            <el-input disabled v-model="form.status"  />
        </el-form-item>
        </el-form>


</template>
<script setup>
    let commentRef = ref(null)
    let form = ref({  });

    const render = async (id) => {
        Msg.loading("loading...")
        let {data} = await Http.get(`/userComment/detail`, {id});
        console.log(data)
        form.value = data;
        Msg.loading(false)
    }


    defineExpose({render});

</script>

