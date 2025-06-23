<template>
    <x-main>
        <!--搜索查询表单-->
        <el-row class="search-row">
                                                                                                    <div class="search-item">
                            <span>标题</span>
                                                            <div style="width: 150px">
                                    <el-input clearable placeholder="请输入标题" v-model="searchForm.title" maxlength="255"/>
                                </div>
                                                    </div>
                                                                                                                                        <div class="search-item">
                            <span>发布时间</span>
                            <div style="width: 222px">
                                <el-date-picker placeholder="发布时间起始" value-format="YYYY-MM-DD HH:mm:ss" v-model="searchForm.createtimeL" type="datetime" :shortcuts="shortcuts"/>
                            </div>
                            <span>至</span>
                            <div style="width: 222px">
                                <el-date-picker placeholder="发布时间结束" value-format="YYYY-MM-DD HH:mm:ss" v-model="searchForm.createtimeR" type="datetime" :shortcuts="shortcuts"/>
                            </div>
                        </div>
                                                
            <button class="btn btn-m" type="primary" @click="loadThisPage">查询</button>
            <button class="btn btn-success btn-m" v-if="user && ( user.role.toLowerCase() == 'admin'   )" @click="onAdd">发布公告</button>
            <button class="btn btn-m" v-if=conf.EnableExcel @click="onExcel">导出excel</button>
            <button class="btn btn-m" v-if="user && (user.role.toLowerCase() == '')" @click="importExcel">导入excel</button>
        </el-row>
        <!--搜索查询表单 end-->

        <div class="statistic-info" v-if="pager.statisticInfo">
            {{pager.statisticInfo}}
        </div>

        <!--数据表-->
        <el-table :data="pager.records" style="width: 100%">
              <el-table-column label="标题">
            <template #default="scope">
                <span >{{scope.row.title}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="内容">
            <template #default="scope">
                <span >{{scope.row.content}}</span>
            </template>
        </el-table-column>
                 <el-table-column sortable label="发布时间">
            <template #default="scope">
                <el-icon><timer/></el-icon><span>{{scope.row.createtime}}</span>
            </template>
        </el-table-column>
    
            <el-table-column fixed="right" label="操作">
                <template #default="scope">
                    <button class="btn" @click="onDetail(scope.row.id)">详情</button>
                    <button class="btn btn-warn" v-if=" user.role.toLowerCase() == 'admin'  " @click="onEdit(scope.row.id)">修改</button>
                    <button class="btn btn-error" v-if="  user.role.toLowerCase() == 'admin'  " @click="onDelete(scope.row.id)">删除</button>

                  <!--                  <button v-if="user.role.toLowerCase() == 'user' && scope.row.state == '待缴纳'" -->
                  <!--                          class="btn" @click="ups(scope.row.id,'确认支付','state','已缴纳')">立即支付</button>-->
                </template>
            </el-table-column>
        </el-table>

        <el-pagination
                v-model:current-page="pager.current"
                v-model:page-size="pager.size"
                background
                :page-sizes="[5, 10, 15, 20,50]"
                layout="total,sizes, prev, pager, next, jumper"
                :total="pager.total"
                @size-change="loadThisPage"
                @current-change="loadThisPage"
        />
    </x-main>
</template>

<script setup name="notice">
    let conf = codeying
    import {Helper} from "core";
    let user = Cache.getUser()//当前登录用户
    import {Timer,ChatDotSquare,Delete,Edit,InfoFilled} from '@element-plus/icons-vue'
    import {onMounted} from "vue";
    //搜索条件表单
    let searchForm = ref({
            title : "",
            createtimeL : "",
        createtimeR : "",
        })
    //分页信息 默认查询第一页，20条数据
    let pager = ref({current: 1, size: 20})

    //钩子函数，挂载
    onMounted(() => {
        loadThisPage()
    })

    //加载本页数据
    const loadThisPage = async () => {
        Msg.loading("加载中...")
        //表单查询参数和分页参数
        let params = Object.assign(searchForm.value, {
            current: pager.value.current,
            size: pager.value.size
        });
        let {data,message} = await Http.get(`/notice/list`, params);
        console.log("查询条件")
        console.log(params)
        console.log("查询结果")
        console.log(data)
        if (data) {
            pager.value = data
        }else {
            Msg.error(message);
        }
        Msg.loading(false)
    }
    import DetailPage from "./Detail"
    //详情页
    const onDetail = async (id) => {
        const op = Dialog.open(DetailPage, '详情').setCancelText('').setConfirmText('')
        op.mounted(c => {
            c.render(id)
        })
    }
    import EditPage from "./Edit"
    //编辑页
    const onEdit = async (id) => {
        const op = Dialog.open(EditPage, `修改`).setConfirmText('保存')
        op.mounted(c => {
            c.render(id, loadThisPage)
        })
        op.confirm(async (c) => {
            c.submit()
        })
    }
    //列表页更新数据通用方法
    const ups = async (id, msg, field, value) => {
      const op = Msg.confirm(msg)
      op.then(async () => {
        let upsForm = {id: id, [field]: value}
        let {success, message} = await Http.post(`/notice/save`, upsForm);
        if (!success) {
          Msg.error(message);
        }else{
          Msg.success(message);
          await loadThisPage()
        }
      })
    }
    import AddPage from "./Add"
    //新增页
    const onAdd = async () => {
        const op = Dialog.open(AddPage, `发布公告`).setConfirmText('确认')
        op.mounted(c => {
            c.render(loadThisPage)
        })
        op.confirm(async (c) => {
            c.submit()
        })
    }
    //onExcel
    const onExcel = async () => {
        Http.download({},false,"/notice/excel")
    }
    //删除
    const onDelete = async (id) => {
        const op = Msg.confirm('确定删除？')
        op.then(async () => {
            let {success, message} = await Http.post(`/notice/delete?id=` + id);
            if (!success) {
                Msg.error(message);
            } else {
                Msg.success(message);
                await loadThisPage()
            }
        })
    }



    import ImportExcel from "../../components/lib/ImportExcel";
    const importExcel = ()=>{
        const op = Dialog.open(ImportExcel, `导入公告数据`).setCancelText('').setConfirmText('')
        op.mounted(c => {
            c.render('notice',loadThisPage)
        })
    }
</script>


<style>
    /*分页右对齐*/
    .el-pagination {
        float: right;
    }
    .el-form--inline .el-form-item{
        margin-right: 0!important;
    }
    .search-row{
        display: flex;
        margin-bottom: 10px;
    }
    .search-item{
        display: flex;
    }
    .search-item span{
        white-space: nowrap;
        line-height: 32px;
        margin: 0 5px;
    }
</style>

