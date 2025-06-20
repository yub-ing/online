<template>
    <el-form
            label-position="left"
            label-width="100px"
            :model="form"
            style="max-width: 600px"
    >
             <el-form-item label="类型名">
            <el-input disabled v-model="form.name"  />
        </el-form-item>
                 <el-form-item label="说明">
            <el-input disabled v-model="form.describtion"  />
        </el-form-item>
        </el-form>


</template>
<script setup>
    let commentRef = ref(null)
    let form = ref({  });

    const render = async (id) => {
        Msg.loading("loading...")
        let {data} = await Http.get(`/stype/detail`, {id});
        console.log(data)
        form.value = data;
        Msg.loading(false)
    }


    defineExpose({render});

</script>

