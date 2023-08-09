<template>
  <nav class="navbar">
    <div class="nav-logo">
      <router-link to="/" class="logo-link">
        <img src="@/assets/logo.png" alt="Logo" />
      </router-link>
    </div>
    <div class="nav-links">
      <a href="#why" class="nav-link">Why MailFlock?</a>
      <router-link to="/services" class="nav-link">Services</router-link>
      <router-link to="/templates" class="nav-link">Templates</router-link>
      <router-link to="/newsletters" class="nav-link">Newsletters</router-link>
      <router-link to="/contact" class="nav-link">Contact</router-link>
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

.navbar > .nav-links > .nav-link {
  color: #222;
  text-decoration: none;
  font-size: 20px;
  font-weight: 500;
  position: relative;
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

.navbar > .nav-links > .nav-link::before {
  content: "";
  position: absolute;
  width: 88%;
  height: 3px;
  border-radius: 5px;
  background-color: #4caf50;
  bottom: 0;
  left: 0;
  transform-origin: right;
  transform: scaleX(0);
  transition: transform 0.25s ease-in-out;
}

.navbar > .nav-links > .nav-link:hover::before {
  transform-origin: right;
  transform: scaleX(1);
}

.navbar > .nav-links > .nav-link::after {
  content: "";
  position: absolute;
  width: 8%;
  height: 3px;
  border-radius: 20px;
  background-color: #4caf50;
  bottom: 0;
  right: 0;
  transform-origin: left;
  transform: scaleX(0);
  transition: transform 0.25s ease-in-out;
}

.navbar > .nav-links > .nav-link:hover::after {
  transform-origin: left;
  transform: scaleX(1);
}
</style>
