<template>
  <main>
    <ContactForm />
  </main>
</template>

<script>
import ContactForm from "@/components/contact/ContactForm.vue";

export default {
  name: "ContactView",
  components: {
    ContactForm,
  },
};
</script>

<style scoped>
main {
  display: flex;
  justify-content: center;
  align-items: center;
  background: url("@/assets/contact.jpg");
  background-size: cover;
  height: 82vh;
}
</style>
