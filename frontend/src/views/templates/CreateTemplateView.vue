<template>
  <main>
    <CreateTemplateForm />
  </main>
</template>

<script>
import CreateTemplateForm from "@/components/templates/CreateTemplateForm.vue";

export default {
  name: "CreateTemplateView",
  components: {
    CreateTemplateForm,
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
  background-size: cover;
  height: 82vh;
}
</style>
