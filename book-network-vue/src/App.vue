<script setup>
import { computed } from "vue";
import { RouterView, useRoute } from 'vue-router'
import Navbar from './components/Navbar.vue'

const route = useRoute();

const isLoginPage = computed(() =>  route.path === "/login" || route.path === "/register" || route.path === "/activate-account");

</script>

<template>
  <Navbar v-if="!isLoginPage" />
  <RouterView />
</template>

<style scoped>
</style>
