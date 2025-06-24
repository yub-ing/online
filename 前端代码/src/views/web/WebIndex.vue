<template>
  <router-view v-slot="{ Component, route }">
    <transition name="fade-transform" mode="out-in">
      <keep-alive :include="menu.cacheList">
        <component v-if="!route.meta.link" :is="Component" :key="route.path"/>
      </keep-alive>
    </transition>
  </router-view>
</template>

<script setup>
import {useStore} from 'core';
import {useRouter} from "vue-router";
import {onMounted} from "vue";
const router = useRouter()
const {menu} = useStore();

onMounted(()=>{
  console.log("web ...")
  // 默认访问地址
  if(router.currentRoute.value.fullPath==='/'){
    router.push('/welcome')
  }
})

</script>

<style scoped lang="less">

</style>
