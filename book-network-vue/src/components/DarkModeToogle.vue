<!-- DarkModeToggle.vue -->
<template>
  <div>
    <input
        type="checkbox"
        class="peer sr-only opacity-0"
        id="toggle"
        v-model="isDarkMode"
        @change="toggleDarkMode"
    />
    <label
        for="toggle"
        class="relative flex h-6 w-11 cursor-pointer items-center rounded-full bg-gray-400 px-0.5 outline-gray-400 transition-colors before:h-5 before:w-5 before:rounded-full before:bg-white before:shadow before:transition-transform before:duration-300 peer-checked:bg-green-500 peer-checked:before:translate-x-full peer-focus-visible:outline peer-focus-visible:outline-offset-2 peer-focus-visible:outline-gray-400 peer-checked:peer-focus-visible:outline-green-500"
    >
      <span class="sr-only">Enable Dark Mode</span>
    </label>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const isDarkMode = ref(false);

const toggleDarkMode = () => {
  document.documentElement.setAttribute('data-theme', isDarkMode.value ? 'dark' : 'light');
  localStorage.setItem('darkMode', isDarkMode.value);
};

onMounted(() => {
  const savedMode = localStorage.getItem('darkMode');
  if (savedMode === 'true') {
    isDarkMode.value = true;
    document.documentElement.setAttribute('data-theme', 'dark');
  }
});
</script>

<style scoped>
/* You can add styles for the toggle switch here if needed */
</style>
