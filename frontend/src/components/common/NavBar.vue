<template>
  <nav class="nav" v-if="showNavbar">
    <section class="nav-img">
      <router-link to="/" class="nav-img-link">
        <img src="@/assets/logo.png" alt="Mail Flock Logo" />
      </router-link>
    </section>
    <section class="nav-auth" v-if="isNotLoggedIn">
      <router-link class="link" to="/contact">Contact Us</router-link>
      <router-link class="link" to="/demo">Book a Demo</router-link>
      <router-link class="link sec-btn" to="/login">Login</router-link>
      <router-link class="link prm-btn" to="/register">Register</router-link>
    </section>
    <section class="nav-auth" v-else>
      <router-link class="link" to="/dashboard">Dashboard</router-link>
      <router-link class="link" to="/templates">Templates</router-link>
      <button class="link prm-btn" @click="logout">Logout</button>
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

button {
  border: none;
  font-family: "Work Sans", sans-serif;
  font-weight: 500;
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
    isNotLoggedIn() {
      const accessToken = localStorage.getItem("access_token");

      return !accessToken;
    },
  },
  methods: {
    logout() {
      localStorage.removeItem("access_token");
      localStorage.removeItem("refresh_token");

      this.isNotLoggedIn = true;

      setInterval(() => {
        window.location.reload();
      }, 100);
      this.$router.push("/");
    },
  },
};
</script>
