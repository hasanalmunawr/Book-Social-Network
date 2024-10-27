<script setup>
import { RouterLink, useRoute } from "vue-router";
import { ref } from "vue";
import Logo from "@/assets/images/icon.png";
import DarkModeToggle from "@/components/DarkModeToogle.vue";

const isActiveLink = (pathRoute) => {
  const route = useRoute();
  return route.path === pathRoute;
};

const isDropDownOpen = ref(false);

const toggleDropDown = () => {
  isDropDownOpen.value = !isDropDownOpen.value;
};

const closeDropDown = (event) => {
  const dropDown = event.target.closest(".flex");
  if (!dropDown) {
    isDropDownOpen.value = false;
  }
};

document.addEventListener('click', closeDropDown);
</script>

<template>
  <nav
      :class="{
      'bg-sky-600 dark:bg-sky-800': true,
      'border-b border-sky-700 dark:border-sky-900': true,
    }"
  >
    <div class="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
      <div class="flex h-20 items-center justify-between">
        <!-- Logo Section -->
        <RouterLink class="flex flex-shrink-0 items-center mr-4" to="/">
          <img class="h-10 w-auto" :src="Logo" alt="Book Icon" />
          <span
              class="hidden md:block text-white dark:text-gray-200 text-2xl font-bold ml-2"
          >Book Network</span
          >
        </RouterLink>

        <!-- Menu Section -->
        <div class="flex-grow flex items-center justify-start mx-10 space-x-4">
          <RouterLink
              to="/dashboard"
              :class="[
              isActiveLink('/dashboard')
                ? 'bg-sky-700 dark:bg-sky-900'
                : 'hover:bg-sky-500 dark:hover:bg-sky-700 hover:text-white',
              'text-white dark:text-gray-200',
              'px-3 py-2 rounded-md',
            ]"
          >
            Home
          </RouterLink>
          <RouterLink
              to="/my-books"
              :class="[
              isActiveLink('/my-books')
                ? 'bg-sky-700 dark:bg-sky-900'
                : 'hover:bg-sky-500 dark:hover:bg-sky-700 hover:text-white',
              'text-white dark:text-gray-200',
              'px-3 py-2 rounded-md',
            ]"
          >
            My Books
          </RouterLink>
          <RouterLink
              to="/borrowed-book"
              :class="[
              isActiveLink('/borrowed-book')
                ? 'bg-sky-700 dark:bg-sky-900'
                : 'hover:bg-sky-500 dark:hover:bg-sky-700 hover:text-white',
              'text-white dark:text-gray-200',
              'px-3 py-2 rounded-md',
            ]"
          >
            Borrowed Book
          </RouterLink>
        </div>

        <!-- User Profile Section -->
        <div class="flex items-center relative">
          <button
              @click="toggleDropDown"
              class="text-white bg-sky-600 dark:bg-sky-700 hover:bg-sky-500 dark:hover:bg-sky-600 focus:ring-4 focus:outline-none focus:ring-sky-300 dark:focus:ring-sky-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center"
              type="button"
          >
            <font-awesome-icon :icon="['far', 'user']" class="mr-2" />
            Hasan
            <svg
                class="w-2.5 h-2.5 ms-3"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 10 6"
            >
              <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="m1 1 4 4 4-4"
              />
            </svg>
          </button>

          <!-- Dropdown menu -->
          <div
              v-if="isDropDownOpen"
              class="absolute mt-1 z-10 bg-sky-600 dark:bg-sky-800 divide-y divide-sky-700 dark:divide-sky-900 rounded-lg shadow w-44"
              style="top: 100%; left: 0;"
          >
            <ul class="py-2 text-sm text-white dark:text-gray-200">
              <li>
                <a
                    href="#"
                    class="block px-4 py-2 hover:bg-sky-500 dark:hover:bg-sky-700 dark:hover:text-white"
                >
                  <font-awesome-icon :icon="['far', 'address-card']" class="mr-2" />
                  Profile
                </a>
              </li>
              <li>
                <a
                    href="#"
                    class="block px-4 py-2 hover:bg-sky-500 dark:hover:bg-sky-700 dark:hover:text-white"
                >
                  <font-awesome-icon :icon="['fas', 'gear']" class="mr-2" />
                  Settings
                </a>
              </li>
              <li>
                <a
                    href="#"
                    class="block px-4 py-2 hover:bg-sky-500 dark:hover:bg-sky-700 dark:hover:text-white"
                >
                  <font-awesome-icon :icon="['fas', 'arrow-right-from-bracket']" class="mr-2" />
                  Sign out
                </a>
              </li>
              <!-- Improved Dark Mode Section -->
              <li class="flex items-center px-4 py-2">
                <span class="mr-2">Dark Mode</span>
                <DarkModeToggle />
              </li>
            </ul>
          </div>

        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped>
/* Tambahkan gaya tambahan di sini jika diperlukan */
</style>
