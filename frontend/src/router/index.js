import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/authentication/Login.vue";
import PageNotFound from "@/views/PageNotFound.vue";
import RegisterView from "@/views/authentication/Register.vue";
import OTPVerification from "@/views/authentication/OTPVerification.vue";
import ContactView from "@/views/contact/Contact.vue";
import DashboardView from "@/views/dashboard/DashboardView.vue";
import CreateTemplateView from "@/views/templates/CreateTemplateView.vue";

const routes = [
  {
    path: "/",
    name: "default",
    component: HomeView,
    meta: {
      title: "Mail Flock",
    },
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      title: "Mail Flock - Login",
    },
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      title: "Mail Flock - Register",
    },
  },
  {
    path: "/verification/:email",
    name: "verification",
    component: OTPVerification,
    props: true,
    meta: {
      title: "Mail Flock - User Verification",
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
    path: "/dashboard",
    name: "dashboard",
    component: DashboardView,
    meta: {
      title: "Mail Flock - Dashboard",
    },
  },
  {
    path: "/templates/create",
    name: "create template",
    component: CreateTemplateView,
    meta: {
      title: "Mail Flock - Create Template",
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
