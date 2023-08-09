<template>
  <main class="error-page">
    <img
      src="@/assets/error404.png"
      alt="404 Page Not Found"
      class="error-image"
    />
    <router-view />
  </main>
</template>

<script>
export default {
  name: "PageNotFound",
};
</script>

<style scoped>
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
}

.error-image {
  max-width: 700px;
}
</style>
