import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/authentication/Login.vue";
import PageNotFound from "@/views/PageNotFound.vue";
import RegisterView from "@/views/authentication/Register.vue";
import OTPVerification from "@/views/authentication/OTPVerification.vue";
import ContactView from "@/views/contact/Contact.vue";

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
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      title: "Register",
    },
  },
  {
    path: "/verification/:email",
    name: "verification",
    component: OTPVerification,
    props: true,
    meta: {
      title: "Verification",
    },
  },
  {
    path: "/contact",
    name: "contact",
    component: ContactView,
    meta: {
      title: "Mail Flock - Contact",
    },
  },
  {
    path: "/demo",
    name: "demo",
    component: ContactView,
    meta: {
      title: "Mail Flock - Book a Demo",
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
  history: createWebHashHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to) => {
  document.title = to.meta?.title ?? "Mail Flock";
});

export default router;
