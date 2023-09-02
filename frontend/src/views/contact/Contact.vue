<template>
  <main>
    <ContactForm v-if="showContact" />
    <DemoForm v-else />
  </main>
</template>

<script>
import ContactForm from "@/components/contact/ContactForm.vue";
import DemoForm from "@/components/contact/DemoForm.vue";

export default {
  name: "ContactView",
  components: {
    ContactForm,
    DemoForm,
  },
  computed: {
    showContact() {
      return this.$route.path == "/contact";
    },
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
