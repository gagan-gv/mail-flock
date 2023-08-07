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
  // eslint-disable-next-line vue/multi-word-component-names
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
  max-width: 500px;
}
</style>
