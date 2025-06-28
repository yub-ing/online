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
        <p>欢迎进入</p>
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
})
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
