<script setup>
import axios from '../../axiosConfig.js';
import {ref} from 'vue';
import { useRouter } from "vue-router";

const router = useRouter();
const message = ref('');
const errorMessage = ref('');
const isSuccess = ref(false);

const codeArray = ref(Array(6).fill('')); // Initialize an array for the 6-digit code
const handleInput = (index) => {
  // Move focus to the next input field when a digit is entered
  if (codeArray.value[index] && index < 5) {
    const nextInput = document.querySelector(`input:nth-child(${index + 2})`);
    if (nextInput) {
      nextInput.focus();
    }
  }
};

const activateAccount = async () => {
  const activationCode = codeArray.value.join('');

  if (activationCode.length !== 6) {
    errorMessage.value = "Please enter a valid 6-digit activation code.";
    return; // Stop the function execution
  }

  console.log("Activation Code ON AXIOS: ", activationCode); // For testing
  try {
    const response = await axios.post('/api/v1/auth/activate', {
      activationCode: activationCode,
    });
    message.value = response.data.message; // Display success message from backend
    isSuccess.value = true;
    await router.push({ name: 'login' });
  } catch (error) {
    if (error.response && error.response.data) {
      const { businessErrorDescription, error: defaultError } = error.response.data;
      errorMessage.value = businessErrorDescription || defaultError || "An unexpected error occurred.";
    } else {
      errorMessage.value = "An unexpected error occurred.";
    }
    isSuccess.value = false;
  }
};

// Your template and other setup code remain the same...





</script>

<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="max-w-md mx-auto p-6 bg-white mt-10 rounded-lg shadow-lg border border-gray-200">
      <h2 class="text-3xl font-bold text-center mb-6 text-gray-800">Activate Your Account</h2>

      <div v-if="errorMessage" class="bg-red-100 border border-red-400 text-red-600 rounded-lg p-4 mb-4">
        <div class="flex items-center">
          <font-awesome-icon icon="exclamation-circle" class="mr-2" />
          <p class="text-center">{{ errorMessage }}</p>
        </div>
      </div>

      <form @submit.prevent="activateAccount">
        <div class="flex flex-col items-center mb-6">
          <h1 class="text-2xl font-semibold text-gray-800 mb-4">
            6-Digit Code
          </h1>

          <div class="flex gap-2 justify-center mb-4">
            <template v-for="(digit, index) in 6" :key="index">
              <input
                  type="text"
                  maxlength="1"
                  v-model="codeArray[index]"
                  @input="handleInput(index)"
                  class="w-14 h-14 flex items-center justify-center border-2 border-gray-300 rounded-lg shadow-sm bg-white text-center transition duration-200 hover:border-blue-500"
                  :placeholder="index === 0 ? '' : ' '"
              />
            </template>
          </div>

          <p class="text-gray-600 text-sm text-center mb-4">
            Please enter the activation code sent to your email.
          </p>
        </div>

        <button
            type="submit"
            class="w-full py-2 px-4 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 transition duration-200"
        >
          Activate Account
        </button>
      </form>

      <!-- Success or Error Message Display -->
      <div v-if="message" :class="{'text-green-600': isSuccess, 'text-red-600': !isSuccess}" class="mt-4 text-center">
        {{ message }}
      </div>
    </div>
  </div>

</template>

<style scoped>

</style>