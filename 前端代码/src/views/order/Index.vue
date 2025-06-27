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
                                                                                                            <div class="search-item">
                            <span>订单号</span>
                                                            <div style="width: 150px">
                                    <el-input clearable placeholder="请输入订单号" v-model="searchForm.numb" maxlength="255"/>
                                </div>
                                                    </div>
                                                                                                                                                                                                                                                         <div v-if="user.role!=='merchant'" class="search-item">
                            <span>商家</span>
                            <div style="width: 150px">
                                <x-select-table :header="[{label:'商家',field:'name',width:'100%'}]"
                                                v-model="searchForm.mid"
                                                :queryParams="[{name:'name',label:'商家'}]"
                                                api="/merchant/list"
                                                labelField="name"
                                                valueField="id"
                                ></x-select-table>
                            </div>
                        </div>
                                                                                                                                                                                                <div class="search-item">
                            <span>状态</span>
                            <div style="width: 150px">
                                <el-select filterable v-model="searchForm.state" clearable placeholder="请选择">
                                    <el-option v-for="item in stateOptionList" :key="item.name" :label="item.name" :value="item.name"/>
                                </el-select>
                            </div>
                        </div>
                                                                                                            <div class="search-item">
                            <span>物流单号</span>
                                                            <div style="width: 150px">
                                    <el-input clearable placeholder="请输入物流单号" v-model="searchForm.wuliu" maxlength="255"/>
                                </div>
                                                    </div>
                                                                                                        
            <button class="btn btn-m" type="primary" @click="loadThisPage">查询</button>
            <button class="btn btn-success btn-m" v-if="user && ( user.role.toLowerCase() == ''   )" @click="onAdd">确认下单</button>
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
                <span >{{scope.row.numb}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="收货人">
            <template #default="scope">
                <span >{{scope.row.sname}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="收货人电话">
            <template #default="scope">
                <span >{{scope.row.stele}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="收货人地址">
            <template #default="scope">
                <span >{{scope.row.saddr}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="订单备注">
            <template #default="scope">
                <span >{{scope.row.dingdbz}}</span>
            </template>
        </el-table-column>
                 <el-table-column sortable label="下单时间">
            <template #default="scope">
                <el-icon><timer/></el-icon><span>{{scope.row.createtime}}</span>
            </template>
        </el-table-column>
                  <el-table-column label="商家">
            <template #default="scope">
                <el-button v-if="scope.row.midFrn" type="primary" text bg @click="midDetail(scope.row.mid)">{{ scope.row.midFrn.name }}</el-button>
            </template>
        </el-table-column>
                 <el-table-column label="订单内容">
            <template #default="scope">
                <span >{{scope.row.content}}</span>
            </template>
        </el-table-column>
                  <el-table-column label="总价格">
            <template #default="scope">
                <span >{{scope.row.price}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="状态">
            <template #default="scope">
                <span >{{scope.row.state}}</span>
            </template>
        </el-table-column>
                 <el-table-column label="物流单号">
            <template #default="scope">
                <span >{{scope.row.wuliu}}</span>
            </template>
        </el-table-column>
                  <el-table-column label="评价">
            <template #default="scope">
                <span >{{scope.row.pingj}}</span>
            </template>
        </el-table-column>
    
            <el-table-column fixed="right" label="操作">
                <template #default="scope">
                  <!--          TODO-->
                  <button class="btn" @click="onDetail(scope.row.id)">详情</button>
                  <button class="btn btn-warn"
                          v-if=" user.role.toLowerCase() == 'user' &&scope.row.state == '已送达'&&scope.row.pingj == '' "
                          @click="onEdit(scope.row.id)">评价
                  </button>
                  <button class="btn btn-warn"
                          v-if=" user.role.toLowerCase() == 'merchant' &&scope.row.state == '已支付' "
                          @click="onEdit(scope.row.id)">更新物流
                  </button>
                  <button class="btn btn-error" v-if="  user.role.toLowerCase() == 'user'&&scope.row.state == '已下单'  "
                          @click="onDelete(scope.row.id)">取消订单
                  </button>
                  <button class="btn " v-if="  user.role.toLowerCase() == 'user'&&scope.row.state == '已下单'  "
                          @click="ups(scope.row.id,'确认支付','state','已支付')">支付
                  </button>
                  <button class="btn " v-if="  user.role.toLowerCase() == 'user'&&scope.row.state == '已支付'  "
                          @click="ups(scope.row.id,'当前未送达，可申请退款，确认退款？','state','已退款')">我要退款
                  </button>
                  <button class="btn " v-if="  user.role.toLowerCase() == 'merchant'&&scope.row.state == '已支付'  "
                          @click="ups(scope.row.id,'确认已送达','state','已送达')">确认已送达
                  </button>
                  <button class="btn " v-if="  user.role.toLowerCase() == 'user'&& scope.row.state == '已送达'  "
                          @click="afterSell(scope.row.id)">售后申请
                  </button>
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

<script setup name="order">
    let conf = codeying
    import {Helper} from "core";
    let user = Cache.getUser()//当前登录用户
    import {Timer,ChatDotSquare,Delete,Edit,InfoFilled} from '@element-plus/icons-vue'
    import {onMounted} from "vue";
    //搜索条件表单
    let searchForm = ref({
            userid : "",
            numb : "",
            mid : "",
            state : "",
            wuliu : "",
    })
    //分页信息 默认查询第一页，20条数据
    let pager = ref({current: 1, size: 20})

    let stateOptionList = ref([  { name:'已下单'}, { name:'已支付'}, { name:'已送达'}, { name:'已退款'}, { name:'售后中'}, { name:'售后完成'}, ]) //状态 下拉框数据
    //钩子函数，挂载
    onMounted(() => {
        loadThisPage()
    })
    import afterSellPage from "../afterSell/Add"
    //新增页
    const afterSell = async (oid) => {
      const op = Dialog.open(afterSellPage, `售后申请`).setConfirmText('确认提交')
      op.mounted(c => {
        c.render(null,{oid})
      })
      op.confirm(async (c) => {
        c.submit()
      })
    }
    //加载本页数据
    const loadThisPage = async () => {
        Msg.loading("加载中...")
        //表单查询参数和分页参数
        let params = Object.assign(searchForm.value, {
            current: pager.value.current,
            size: pager.value.size
        });
        let {data,message} = await Http.get(`/order/list`, params);
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
        const op = Dialog.open(EditPage, `评价`).setConfirmText('发布评价')
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
        let {success, message} = await Http.post(`/order/save`, upsForm);
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
        const op = Dialog.open(AddPage, `确认下单`).setConfirmText('确认支付')
        op.mounted(c => {
            c.render(loadThisPage)
        })
        op.confirm(async (c) => {
            c.submit()
        })
    }
    //onExcel
    const onExcel = async () => {
        Http.download({},false,"/order/excel")
    }
    //删除
    const onDelete = async (id) => {
        const op = Msg.confirm('确定取消订单？')
        op.then(async () => {
            let {success, message} = await Http.post(`/order/delete?id=` + id);
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
     //商家详情页
    import midDetailPage from "../merchant/Detail";
    const midDetail = async (id)=> {
        const op = Dialog.open(midDetailPage, '详情').setCancelText('').setConfirmText('')
        op.mounted(c => {
            c.render(id)
        })
    }


    import ImportExcel from "../../components/lib/ImportExcel";
    const importExcel = ()=>{
        const op = Dialog.open(ImportExcel, `导入用户订单数据`).setCancelText('').setConfirmText('')
        op.mounted(c => {
            c.render('order',loadThisPage)
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

