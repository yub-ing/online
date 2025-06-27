<template>
    <x-main>
        <!--搜索查询表单-->
        <el-row class="search-row">
                                                                                                     <div v-if="user.role!=='user'" class="search-item">
                            <span>用户</span>
                            <div style="width: 150px">
                                <x-select-table :header="[{label:'用户',field:'username',width:'100%'}]"
                                                v-model="searchForm.userid"
                                                :queryParams="[{name:'username',label:'用户'}]"
                                                api="/user/list"
                                                labelField="username"
                                                valueField="id"
                                ></x-select-table>
                            </div>
                        </div>
                                                                                                             <div v-if="user.role!=='order'" class="search-item">
                            <span>订单号</span>
                            <div style="width: 150px">
                                <x-select-table :header="[{label:'用户订单',field:'numb',width:'100%'}]"
                                                v-model="searchForm.oid"
                                                :queryParams="[{name:'numb',label:'用户订单'}]"
                                                api="/order/list"
                                                labelField="numb"
                                                valueField="id"
                                ></x-select-table>
                            </div>
                        </div>
                                                                                                            <div class="search-item">
                            <span>售后类型</span>
                                                            <div style="width: 150px">
                                    <el-input clearable placeholder="请输入售后类型" v-model="searchForm.typer" maxlength="255"/>
                                </div>
                                                    </div>
                                                                                                            <div class="search-item">
                            <span>要求售后内容</span>
                                                            <div style="width: 150px">
                                    <el-input clearable placeholder="请输入要求售后内容" v-model="searchForm.content" maxlength="255"/>
                                </div>
                                                    </div>
                                                                                                                                        <div class="search-item">
                            <span>售后申请时间</span>
                            <div style="width: 222px">
                                <el-date-picker placeholder="售后申请时间起始" value-format="YYYY-MM-DD HH:mm:ss" v-model="searchForm.createtimeL" type="datetime" :shortcuts="shortcuts"/>
                            </div>
                            <span>至</span>
                            <div style="width: 222px">
                                <el-date-picker placeholder="售后申请时间结束" value-format="YYYY-MM-DD HH:mm:ss" v-model="searchForm.createtimeR" type="datetime" :shortcuts="shortcuts"/>
                            </div>
                        </div>
                                                                                                            <div class="search-item">
                            <span>处理状态</span>
                            <div style="width: 150px">
                                <el-select filterable v-model="searchForm.state" clearable placeholder="请选择">
                                    <el-option v-for="item in stateOptionList" :key="item.name" :label="item.name" :value="item.name"/>
                                </el-select>
                            </div>
                        </div>
                                                                            
            <button class="btn btn-m" type="primary" @click="loadThisPage">查询</button>
            <button class="btn btn-success btn-m" v-if="user && ( user.role.toLowerCase() == ''   )" @click="onAdd">申请售后</button>
            <button class="btn btn-m" v-if=conf.EnableExcel @click="onExcel">导出excel</button>
            <button class="btn btn-m" v-if="user && (user.role.toLowerCase() == '')" @click="importExcel">导入excel</button>
        </el-row>
        <!--搜索查询表单 end-->

        <div class="statistic-info" v-if="pager.statisticInfo">
            {{pager.statisticInfo}}
        </div>

        <!--数据表-->
        <el-table :data="pager.records" style="width: 100%">
               <el-table-column label="用户">
            <template #default="scope">
                <el-button v-if="scope.row.useridFrn" type="primary" text bg @click="useridDetail(scope.row.userid)">{{ scope.row.useridFrn.username }}</el-button>
            </template>
        </el-table-column>
                  <el-table-column label="订单号">
            <template #default="scope">
                <el-button v-if="scope.row.oidFrn" type="primary" text bg @click="oidDetail(scope.row.oid)">{{ scope.row.oidFrn.numb }}</el-button>
            </template>
        </el-table-column>
                 <el-table-column label="售后类型">
            <template #default="scope">
                <span >{{scope.row.typer}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="要求售后内容">
            <template #default="scope">
                <span >{{scope.row.content}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="产品问题拍照上传">
            <template #default="scope">
                <el-tooltip class="box-item" effect="dark" :content="scope.row.pic" placement="top-start">
                    <x-file-view :disabled="true" :list="scope.row.pic"></x-file-view>
                </el-tooltip>
            </template>
        </el-table-column>
                 <el-table-column sortable label="售后申请时间">
            <template #default="scope">
                <el-icon><timer/></el-icon><span>{{scope.row.createtime}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="处理状态">
            <template #default="scope">
                <span >{{scope.row.state}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="处理结果">
            <template #default="scope">
                <span >{{scope.row.chuljg}}</span>
            </template>
        </el-table-column>
    
            <el-table-column fixed="right" label="操作">
                <template #default="scope">
                    <button class="btn" @click="onDetail(scope.row.id)">详情</button>
                    <button class="btn btn-warn" v-if=" user.role.toLowerCase() == 'merchant'  " @click="onEdit(scope.row.id)">处理售后申请</button>
                    <button class="btn btn-error" v-if="  user.role.toLowerCase() == ''  " @click="onDelete(scope.row.id)">删除</button>

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

<script setup name="afterSell">
    let conf = codeying
    import {Helper} from "core";
    let user = Cache.getUser()//当前登录用户
    import {Timer,ChatDotSquare,Delete,Edit,InfoFilled} from '@element-plus/icons-vue'
    import {onMounted} from "vue";
    //搜索条件表单
    let searchForm = ref({
            userid : "",
            oid : "",
            typer : "",
            content : "",
            createtimeL : "",
        createtimeR : "",
                state : "",
    })
    //分页信息 默认查询第一页，20条数据
    let pager = ref({current: 1, size: 20})

    let stateOptionList = ref([  { name:'待处理'}, { name:'已处理'}, ]) //处理状态 下拉框数据
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
        let {data,message} = await Http.get(`/afterSell/list`, params);
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
        const op = Dialog.open(EditPage, `处理售后申请`).setConfirmText('保存')
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
        let {success, message} = await Http.post(`/afterSell/save`, upsForm);
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
        const op = Dialog.open(AddPage, `申请售后`).setConfirmText('确认')
        op.mounted(c => {
            c.render(loadThisPage)
        })
        op.confirm(async (c) => {
            c.submit()
        })
    }
    //onExcel
    const onExcel = async () => {
        Http.download({},false,"/afterSell/excel")
    }
    //删除
    const onDelete = async (id) => {
        const op = Msg.confirm('确定删除？')
        op.then(async () => {
            let {success, message} = await Http.post(`/afterSell/delete?id=` + id);
            if (!success) {
                Msg.error(message);
            } else {
                Msg.success(message);
                await loadThisPage()
            }
        })
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


    import ImportExcel from "../../components/lib/ImportExcel";
    const importExcel = ()=>{
        const op = Dialog.open(ImportExcel, `导入售后申请数据`).setCancelText('').setConfirmText('')
        op.mounted(c => {
            c.render('afterSell',loadThisPage)
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

