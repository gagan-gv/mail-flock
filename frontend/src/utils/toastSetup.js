import "vue-toast-notification/dist/theme-sugar.css";
import { useToast } from "vue-toast-notification";

const toast = useToast();

export function errorToast(msg) {
  toast.error(msg, {
    position: "top",
    duration: 2000,
    dismissible: true,
  });
}

export function successToast(msg) {
  toast.success(msg, {
    position: "top",
    duration: 2000,
    dismissible: true,
  });
}
