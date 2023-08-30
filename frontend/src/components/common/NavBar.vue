<template>
  <nav class="nav" v-if="showNavbar">
    <section class="nav-img">
      <router-link to="/" class="nav-img-link">
        <img src="@/assets/logo.png" alt="Mail Flock Logo" />
      </router-link>
    </section>
    <section class="nav-auth">
      <router-link class="link sec-btn" to="/login">Login</router-link>
      <router-link class="link prm-btn" to="/register">Register</router-link>
    </section>
  </nav>
</template>

<style scoped>
.nav {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 80px;
  padding: 10px 30px;
  background-color: #f3f3f3;
  backdrop-filter: blur(20px);
  position: sticky;
  top: 0;
  border-bottom: 2px solid #4caf50;
}
.nav > .nav-img > .nav-img-link {
  text-decoration: none;
}
.nav > .nav-img > .nav-img-link > img {
  max-height: 50px;
}
.nav > .nav-auth {
  display: flex;
  gap: 30px;
}
</style>

<script>
export default {
  name: "NavBar",
  computed: {
    showNavbar() {
      return (
        this.$route.path !== "/login" &&
        this.$route.path !== "/register" &&
        this.$route.name !== "verification"
      );
    },
  },
};
</script>
