<template>
  <div class="layout">
    <x-header></x-header>
    <x-menu></x-menu>
    <div  class="main" :style="{width: `calc(100% - ${!isCollapse ? 200 : 64}px)`, left: `${!isCollapse ? 200 : 64}px`}">
      <div @click="setExpand(!isCollapse)" style="position: absolute;left: -10px;top: 45%;z-index: 99">
        <img v-if="!isCollapse"   :src="`${publicPath}images/menu-expand-1.png`" style="width: 30px">
        <img v-else :src="`${publicPath}images/menu-expand-0.png`" style="width: 30px">
      </div>
      <x-history></x-history>
      <div v-show="router.currentRoute.value.fullPath==='/'||router.currentRoute.value.fullPath==='/hello'" style="height: 90vh;overflow: scroll">
        <div id="statisticNum" style="display: flex;flex-wrap: wrap;" >

        </div>
        <div id="echarts" style="display: flex;flex-wrap: wrap;">

        </div>
      </div>

      <div style="background: var(--main-bg); background-size: cover;" class="x-p-10" :style="{height: `${mainHeight}px`}">
        <router-view v-slot="{ Component, route }">
          <transition name="fade-transform" mode="out-in">
            <keep-alive :include="menu.cacheList">
              <component v-if="!route.meta.link" :is="Component" :key="route.path"/>
            </keep-alive>
          </transition>
        </router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import XHeader from '@/components/layout/Header';
import XMenu from '@/components/layout/Menu';
import XHistory from '@/components/layout/History'
import {useStore, GlobalConfig} from 'core';
import {useRouter} from "vue-router";
import {onMounted} from "vue";
const router = useRouter()
const config = GlobalConfig();
const {menu} = useStore();
let {isCollapse, mainHeight} = storeToRefs(config)

onMounted(()=>{
  getEcharts()
})
//获取图表
const getEcharts = async ()=> {
  if(!router.currentRoute.value.fullPath==='/'){
    return ;
  }
  let data = await Http.post("/hello")
  if(data.success){
    $("#echarts").html('')//清空
    let l = data.data
    //循环1 统计数据
    for (let i = 0; i < l.length; i++) {
      let e = l[i]
      if(e.type === 'n'){
        let html = `<div style="width: 280px;margin: 10px;padding: 10px;background-color: #bc97e3;text-align: center" >
            <p>`+e.name+`</p>
            <p style="font-size: 22px;font-weight: 600;">`+e.value+`</p>
            </div>`;
        $("#statisticNum").append(html);
      }
    }
    //循环2 echarts
    for (let i = 0; i < l.length; i++) {
      let e = l[i]
      let divi = 'div' + i;
      if(e.type === 'line' || e.type === 'bar' || e.type === 'pie') {
        let html = `<div style="width: 550px;margin: 20px;box-sizing: border-box" >
                      <h2 style="margin: 0">` + e.name + `</h2>
                      <div style="height: 300px" id="` + divi + `">
                      </div>
                  </div>`
        $("#echarts").append(html);
        //渲染数据
        var myChart = echarts.init(document.getElementById(divi));
        let option;
        if(e.type === 'line' || e.type === 'bar')
          option = {
            xAxis: {
              data: e.names,
              axisLabel: {
                rotate: 45, // 设置文字倾斜45度
                interval: 0, // 可选：设置标签的间隔为0，确保每个标签都显示
                // 其他可选属性，如color, fontStyle, fontWeight, fontFamily等，可以根据需要添加
              }
            },
            yAxis: {},
            series: [
              {
                type: e.type,
                data: e.values,
                label: {
                  show: true, // 开启显示
                  position: 'top', // 在上方显示
                  formatter: '{c}', // 显示数据
                }
              }
            ]
          };
        if(e.type === 'pie'){
          option = {
            series: [
              {type: 'pie', // 饼图
                data: e.values.map((value, index) => ({
                  name: e.names[index],
                  value: value
                })),
                label: {
                  show: true,
                  formatter: '{b}: {c} ({d}%)' // 显示名称、值和百分比
                },
                itemStyle: {
                  color: function(params) {
                    var colorList = ['#C1232B','#e3ee0d','#1ad02a','#132ee0', '#12d7d0','#4015c2','#9c16b9','#d37408', '#203b12','#9eece5','#d397d7','#561812'];
                    return colorList[params.dataIndex % colorList.length];
                  }
                }
              }
            ]
          };
        }
        myChart.setOption(option);
        window.addEventListener("resize", function () {
          myChart.resize();
        });
      }//end id
    }
  }else {
    console.log(data.message)
  }
}
const setExpand = (isExpand) => {
  config.setCollapse(isExpand)
}

</script>

<style scoped lang="less">
  .main {
    position: absolute;
    top: 60px;
    height: calc(100vh - 60px);
    transition: left .3s ease-in-out;
    .history {
      padding: 0 8px;
      height: 40px;
      box-sizing: border-box;
      display: flex;
      justify-content: space-between;
      align-items: center;

      .icon {
        width: 20px;
      }

      .scroll {
        width: calc(100% - 60px);
        margin-top: -2px;
        overflow-x: auto;
        white-space: nowrap;
      }
    }

    ::-webkit-scrollbar {
      width: 0px;
      height: 0px;
      cursor: pointer;
    }


    /* fade-transform */
    .fade-transform--move,
    .fade-transform-leave-active,
    .fade-transform-enter-active {
      transition: all .5s;
    }

    .fade-transform-leave-active {
      position: absolute;
    }

    .fade-transform-enter {
      opacity: 0;
      transform: translateX(-30px);
    }

    .fade-transform-leave-to {
      opacity: 0;
      transform: translateX(30px);
    }
  }
</style>
