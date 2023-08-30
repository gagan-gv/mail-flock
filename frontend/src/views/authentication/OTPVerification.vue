<template>
  <main>
    <OTPForm :email="email" />
  </main>
</template>

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

<script>
import OTPForm from "@/components/authentication/OTPForm.vue";

export default {
  name: "OTPVerification",
  components: {
    OTPForm,
  },
  computed: {
    email() {
      return this.$route.params.email;
    },
  },
};
</script>
