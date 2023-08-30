<template>
  <section class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <label for="username">Username</label>
      <input
        type="text"
        v-model="username"
        id="username"
        name="username"
        required
        autocomplete="username"
      />
      <label for="password">Password</label>
      <input
        type="password"
        v-model="password"
        id="password"
        name="password"
        required
      />
      <button type="submit" class="prm-btn">Login</button>
    </form>
    <router-link to="/register" class="link redirect"
      >Don't have an account? Register</router-link
    >
  </section>
</template>

<script>
import axios from "axios";
import { AUTH_API } from "@/utils/constants.js";
import { errorToast } from "@/utils/toastSetup.js";

export default {
  name: "LoginForm",
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post(AUTH_API + "/login", {
          username: this.username,
          password: this.password,
        });

        const accessToken = response.data.accessToken;
        const refreshToken = response.data.refreshToken;

        if (accessToken) {
          localStorage.setItem("access_token", accessToken);
          localStorage.setItem("refresh_token", refreshToken);
          this.$router.push("/dashboard");
        } else {
          errorToast("Invalid Creendtials");
        }
      } catch (error) {
        errorToast("Invalid Credentials");
      }
    },
  },
};
</script>

<style scoped>
.login-container,
form {
  display: flex;
  flex-direction: column;
}
.login-container {
  background-color: #f8f8f8;
  align-items: center;
  border: 2px solid #222;
  border-radius: 5px;
  padding: 20px;
  width: max-content;
  color: #222;
  margin: 0 auto;
}
form {
  align-items: start;
  font-weight: 500;
}
label,
input {
  margin: 5px 0;
}
label {
  font-weight: 500;
}
input {
  padding: 10px 20px;
  border-radius: 3px;
  border: 2px solid #222;
  width: 300px;
  line-height: 20px;
  background-color: #f8f8f8;
  -webkit-transition: 0.5s;
  transition: 0.5s;
  outline: none;
  font-weight: 600;
  color: #222;
}
input:focus {
  border: 2px solid #4caf50;
}
button {
  border: none;
  width: 300px;
  padding: 10px 20px;
  margin: 10px auto;
  font-weight: 600;
  border-radius: 20px;
}
.redirect {
  font-size: 16px;
  margin: 5px 0;
}
</style>
