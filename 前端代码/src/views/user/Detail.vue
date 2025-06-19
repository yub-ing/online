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
                <el-form-item label="头像">
            <x-file-view :disabled="true" :list="form.avatar"></x-file-view>
        </el-form-item>
                 <el-form-item label="性别">
            <el-input disabled v-model="form.gender"  />
        </el-form-item>
                 <el-form-item label="年龄">
            <el-input disabled v-model="form.age"  />
        </el-form-item>
                 <el-form-item label="电话">
            <el-input disabled v-model="form.tele"  />
        </el-form-item>
                 <el-form-item label="邮箱">
            <el-input disabled v-model="form.youx"  />
        </el-form-item>
                 <el-form-item label="地址">
            <el-input disabled v-model="form.place"  />
        </el-form-item>
        </el-form>


</template>
<script setup>
    let commentRef = ref(null)
    let form = ref({  });

    const render = async (id) => {
        Msg.loading("loading...")
        let {data} = await Http.get(`/user/detail`, {id});
        console.log(data)
        form.value = data;
        Msg.loading(false)
    }


    defineExpose({render});

</script>

