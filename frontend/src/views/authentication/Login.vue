<template>
  <main>
    <LoginForm />
  </main>
</template>

<script>
import LoginForm from "@/components/authentication/LoginForm.vue";
export default {
  name: "LoginView",
  components: {
    LoginForm,
  },
};
</script>

<style scoped>
main {
  display: flex;
  justify-content: center;
  align-items: center;
  background: url("@/assets/auth.jpg");
  background-size: cover;
  height: 90vh;
}
</style>
