<script setup>
import Card from "@/components/Card.vue";
</script>

<template>
  <!-- Developers and Employers -->
  <section class="py-4">
    <div class="container-xl lg:container m-auto">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4 p-4 rounded-lg">
        <Card bg="bg-blue-500"> <!-- Mengubah latar belakang menjadi biru -->
          <h2 class="text-2xl font-bold text-white">For Developers</h2> <!-- Teks putih -->
          <p class="mt-2 mb-4 text-white">
            Browse our Vue jobs and start your career today
          </p>
          <RouterLink
              to="/jobs"
              class="inline-block bg-white text-blue-500 rounded-lg px-4 py-2 hover:bg-gray-200"
          >
            Browse Jobs
          </RouterLink>
        </Card>
        <Card bg="bg-green-500/50"> <!-- Mengubah latar belakang menjadi hijau -->
          <h2 class="text-2xl font-bold text-white">For Employers</h2> <!-- Teks putih -->
          <p class="mt-2 mb-4 text-white">
            List your job to find the perfect developer for the role
          </p>
          <RouterLink
              to="/jobs/add"
              class="inline-block bg-white text-green-500 rounded-lg px-4 py-2 hover:bg-gray-200"
          >
            Add Job
          </RouterLink>
        </Card>
      </div>
    </div>
  </section>
</template>

<style scoped>

</style>