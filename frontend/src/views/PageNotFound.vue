<template>
    <main class="error-page">
        <img src="@/assets/error404.png" alt="Page Not Found" class="error-img" />
    </main>
</template>

<script>
export default {
  name: 'PageNotFound'
}
</script>

<style scoped>
.error-page {
    display: flex;
    justify-content: center;
    align-items: center;
}

.error-page > .error-img {
    max-width: 650px;
}
</style>
