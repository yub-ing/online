<template>
    <el-form
            label-position="left"
            label-width="100px"
            :model="form"
            style="max-width: 600px"
    >
             <el-form-item label="用户名">
            <el-input disabled v-model="form.username"  />
        </el-form-item>
                 <el-form-item label="姓名">
            <el-input disabled v-model="form.name"  />
        </el-form-item>
                 <el-form-item label="电话">
            <el-input disabled v-model="form.tele"  />
        </el-form-item>
        </el-form>


</template>
<script setup>
    let commentRef = ref(null)
    let form = ref({  });

    const render = async (id) => {
        Msg.loading("loading...")
        let {data} = await Http.get(`/admin/detail`, {id});
        console.log(data)
        form.value = data;
        Msg.loading(false)
    }


    defineExpose({render});

</script>

