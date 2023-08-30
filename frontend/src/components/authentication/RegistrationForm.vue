<template>
  <section class="registration-container">
    <h2>Register</h2>
    <form @submit.prevent="register">
      <label for="name">Name</label>
      <input
        type="text"
        v-model="name"
        name="name"
        id="name"
        required
        autocomplete="name"
      />
      <label for="username">Username</label>
      <input
        type="text"
        v-model="username"
        id="username"
        name="username"
        required
        autocomplete="username"
      />
      <label for="email">Email</label>
      <input
        type="email"
        v-model="email"
        name="email"
        id="email"
        required
        autocomplete="email"
      />
      <label for="password">Password</label>
      <input
        type="password"
        v-model="password"
        name="password"
        id="password"
        required
      />
      <label for="confirm-password">Confirm Password</label>
      <input
        type="password"
        v-model="confirmPassword"
        name="confirm-password"
        id="confirm-password"
        required
      />
      <section>
        <input
          type="checkbox"
          v-model="subscribe"
          name="subscribe"
          id="subscribe"
          checked
        />
        <label for="subscribe">Subscribe to our newsletters and updates</label>
      </section>
      <button type="submit" class="prm-btn">Register</button>
    </form>
    <router-link to="/login" class="link redirect"
      >Already have an account? Login</router-link
    >
  </section>
</template>

<style scoped>
.registration-container,
form {
  display: flex;
  flex-direction: column;
}

.registration-container {
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
  width: 350px;
  line-height: 20px;
  background-color: #f8f8f8;
  -webkit-transition: 0.5s;
  transition: 0.5s;
  outline: none;
  font-weight: 600;
  color: #222;
}

form > section {
  line-height: 15px;
  padding: 10px 0;
}

input[type="checkbox"] {
  accent-color: #4caf50;
  color: #f8f8f8;
  width: 20px;
  height: 12px;
}

input:focus {
  border: 2px solid #4caf50;
}

button {
  border: none;
  width: 350px;
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

<script>
import { AUTH_API } from "@/utils/constants.js";
import { errorToast } from "@/utils/toastSetup.js";
import axios from "axios";

export default {
  name: "RegistrationForm",
  data() {
    return {
      name: "",
      username: "",
      email: "",
      password: "",
      confirmPassword: "",
      subscribe: "",
    };
  },
  methods: {
    register() {
      try {
        if (this.password != this.confirmPassword) {
          errorToast("Passwords don't match");
          return;
        }

        axios.post(AUTH_API + "/register", {
          name: this.name,
          username: this.username,
          emailId: this.email,
          password: this.password,
          subscribe: this.subscribe,
        });

        this.$router.push("/verification");
      } catch (error) {
        errorToast(error.data.message);
      }
    },
  },
};
</script>
