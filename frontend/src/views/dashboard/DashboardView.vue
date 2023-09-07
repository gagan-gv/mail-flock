<template>
  <main>
    <h2>Hey There,</h2>
    <h1>@{{ username }}</h1>
    <section>
      <DashboardActions
        v-for="action in actions"
        :key="action.id"
        :action="action.action"
        :icon-class="action.iconClass"
        :route-to="action.routeTo"
      />
    </section>
  </main>
</template>

<script>
import DashboardActions from "@/components/dashboard/DashboardActions.vue";
import { errorToast } from "@/utils/toastSetup.js";

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
          action: "Profile Settings",
        },
      ],
      username: this.getUsername(),
    };
  },
  methods: {
    getUsername() {
      // Get the JWT token from localStorage
      const token = localStorage.getItem("access_token");
      if (token) {
        try {
          // Split the token into its three parts: header, payload, and signature
          const parts = token.split(".");

          // Decode the payload (the second part) from base64
          const payload = JSON.parse(atob(parts[1]));

          // Access the username from the payload
          const username = payload.sub; // Replace 'username' with the actual field name

          return username;
        } catch (error) {
          errorToast("Error decoding JWT token:", error);
        }
      } else {
        errorToast("JWT token not found in localStorage");
      }
    },
  },
};
</script>

<style scoped>
main {
  padding: 20px;
  text-align: left;
  margin: 45px;
}

h2 {
  font-size: 56px;
}

h1 {
  font-size: 80px;
  font-weight: 600;
  color: #4caf50;
}

main > section {
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin: 20px;
  padding: 30px;
}
</style>
