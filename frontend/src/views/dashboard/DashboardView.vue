<template>
  <main>
    <h2>Hey There,</h2>
    <h1>@{{ username }}</h1>
    <DashboardActions
      v-for="action in actions"
      :key="action.id"
      :action="action.action"
      :icon-class="action.iconClass"
      :route-to="action.routeTo"
    />
  </main>
</template>

<script>
import DashboardActions from "@/components/dashboard/DashboardActions.vue";

export default {
  name: "DashboardView",
  components: {
    DashboardActions,
  },
  data() {
    return {
      actions: [
        {
          id: 1,
          routeTo: "/send-mail",
          iconClass: "fa-regular fa-envelope",
          action: "Send a Mail",
        },
        {
          id: 2,
          routeTo: "/templates/create",
          iconClass: "fa-regular fa-file-lines",
          action: "Create a template",
        },
        {
          id: 3,
          routeTo: "/profile",
          iconClass: "fa-regular fa-user",
          action: "My Profile",
        },
      ],
    };
  },
};
</script>
