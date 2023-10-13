<template>
  <SendMailForm />
</template>

<script>
import SendMailForm from "@/components/mailing/SendMailForm.vue";

export default {
  name: "SendMailView",
  components: {
    SendMailForm,
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
