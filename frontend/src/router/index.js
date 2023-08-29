import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/Login.vue";
import PageNotFound from "@/views/PageNotFound.vue";

const routes = [
  {
    path: "/",
    name: "default",
    component: HomeView,
    meta: {
      title: "Home",
    },
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      title: "Login",
    },
  },
  {
    path: "/:pathMatch(.*)*",
    name: "page not found",
    component: PageNotFound,
    meta: {
      title: "Error, Page Not Found",
    },
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach((to) => {
  document.title = to.meta?.title ?? "Mail Flock";
});

export default router;
