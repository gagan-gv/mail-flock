<template>
  <main>
    <RegistrationForm />
  </main>
</template>

<script>
import RegistrationForm from "@/components/authentication/RegistrationForm.vue";
export default {
  name: "RegisterView",
  components: {
    RegistrationForm,
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
