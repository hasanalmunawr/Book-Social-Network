<script setup>
import { ref } from 'vue';
import axios from '../../axiosConfig.js';
import { useRouter } from 'vue-router';

const router = useRouter();

const username = ref("");
const email = ref("");
const password = ref("");
const confirmPassword = ref("");
const role = ref("user");

const errorMessage = ref("");

const register = async () => {
  try {
    const response = await axios.post('/api/v1/auth/register', {
      username: username.value,
      email: email.value,
      password: password.value,
      role: role.value
    });
    await router.push({name: 'activate-account'})
  } catch (error) {
    console.error('Registration error:', error);

    if (error.response && error.response.data) {
      const { businessErrorDescription, error: defaultError } = error.response.data;
      errorMessage.value = defaultError || businessErrorDescription || "An unexpected error occurred.";
    } else {
      errorMessage.value = "An unexpected error occurred.";
    }
  }
}
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full space-y-8 p-10 bg-white shadow-md rounded-lg">
      <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">Register</h2>

      <div v-if="errorMessage" class="bg-red-100 border border-red-400 text-red-600 rounded-lg p-4 mb-4">
        <div class="flex items-center">
          <font-awesome-icon icon="exclamation-circle" class="mr-2" />
          <p class="text-center">{{ errorMessage }}</p>
        </div>
      </div>

      <!-- Registration Form -->
      <form @submit.prevent="register" class="mt-6 space-y-6">
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
          <div class="mt-1">
            <input id="username" type="text" v-model="username" required
                   class="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                   placeholder="Your Name"/>
          </div>
        </div>
        <div>
          <label for="email" class="block text-sm font-medium text-gray-700">Email address</label>
          <div class="mt-1">
            <input id="email" type="email" v-model="email" required
                   class="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                   placeholder="you@example.com"/>
          </div>
        </div>
        <div>
          <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
          <div class="mt-1">
            <input id="password" type="password" v-model="password" required
                   class="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                   placeholder="Password"/>
          </div>
        </div>
        <div>
          <label for="confirm-password" class="block text-sm font-medium text-gray-700">Confirm Password</label>
          <div class="mt-1">
            <input id="confirm-password" type="password" v-model="confirmPassword" required
                   class="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                   placeholder="Confirm Password"/>
          </div>
        </div>
        <div>
          <button type="submit"
                  class="w-full py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-500">
            Register
          </button>
        </div>
      </form>

      <!-- Divider -->
      <div class="flex items-center justify-center mt-6">
        <div class="w-full border-t border-gray-300"></div>
        <span class="absolute px-4 text-gray-500">Or continue with</span>
      </div>

      <!-- OAuth Buttons -->
      <div class="flex flex-col gap-4 mt-4">
        <a href="/auth/google"
           class="flex items-center justify-center py-3 px-6 border border-gray-400 rounded-md shadow-sm text-lg font-medium text-black bg-white hover:bg-gray-200 transition duration-200">
          <img class="h-8 w-8 mr-2" src="../../assets/images/7123025_logo_google_g_icon%20(1).png" alt="Google Icon">
          <span>Register with Google</span>
        </a>
      </div>

      <div class="mt-6 text-center">
        <p class="text-sm text-gray-600">Already have an account?
          <a class="underline" href="/login">Log in</a>
        </p>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>