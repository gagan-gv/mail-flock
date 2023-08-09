<template>
  <nav class="navbar">
    <div class="nav-logo">
      <router-link to="/" class="logo-link">
        <img src="@/assets/logo.png" alt="Logo" />
      </router-link>
    </div>
    <div class="nav-links">
      <a href="#why" class="link">Why MailFlock?</a>
      <router-link to="/services" class="link">Services</router-link>
      <router-link to="/templates" class="link">Templates</router-link>
      <router-link to="/newsletters" class="link">Newsletters</router-link>
      <router-link to="/contact" class="link">Contact</router-link>
    </div>
    <div class="nav-auth">
      <router-link to="/login" class="auth-links sec-btn">Login</router-link>
      <router-link to="/register" class="auth-links prm-btn"
        >Register</router-link
      >
    </div>
  </nav>
</template>

<script>
export default {
  name: "NavBar",
};
</script>

<style scoped>
.navbar {
  height: 80px;
  padding: 10px 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f3f3f3;
  backdrop-filter: blur(20px);
  opacity: 0.975;
  position: sticky;
  top: 0;
}

.navbar > .nav-logo > .logo-link {
  text-decoration: none;
}

.navbar > .nav-logo > .logo-link > img {
  max-height: 50px;
}

.navbar > .nav-links {
  display: flex;
  flex-direction: row;
  gap: 25px;
}

.navbar > .nav-auth {
  display: flex;
  justify-content: center;
  align-content: center;
  flex-direction: row;
  gap: 25px;
}

.navbar > .nav-auth > .auth-links {
  font-size: 20px;
  font-weight: 500;
  text-decoration: none;
  position: relative;
}
</style>
