<template>
  <section class="demo-container">
    <h2>Book a Demo</h2>
    <form @submit.prevent="book">
      <label for="name">Name</label>
      <input
        type="text"
        v-model="name"
        name="name"
        id="name"
        required
        autocomplete="name"
      />
      <label for="email">Email</label>
      <input
        type="email"
        v-model="email"
        name="email"
        id="email"
        required
        autocomplete="email"
      />
      <label for="date">Date</label>
      <input
        type="date"
        v-model="date"
        name="date"
        id="date"
        required
        autocomplete="date"
      />
      <label for="time">Time</label>
      <input
        type="time"
        v-model="time"
        name="time"
        id="time"
        required
        autocomplete="time"
      />
      <button class="prm-btn" type="submit">Book</button>
    </form>
  </section>
</template>

<script>
import { CONTACT_API } from "@/utils/constants";
import { errorToast, successToast } from "@/utils/toastSetup";
import axios from "axios";

export default {
  name: "DemoForm",
  data() {
    return {
      name: "",
      email: "",
      date: "",
      time: "",
    };
  },
  methods: {
    async book() {
      try {
        const sqlDate = new Date(this.date).toISOString().slice(0, 10);
        const [hours, minutes] = this.time.split(":");

        const timeObj = new Date();
        timeObj.setHours(hours);
        timeObj.setMinutes(minutes);

        const sqlTime = timeObj.toTimeString().slice(0, 8);

        const response = await axios.post(CONTACT_API + "/demo", {
          name: this.name,
          emailId: this.email,
          date: sqlDate,
          time: sqlTime,
        });

        if (response.status == 201) {
          successToast("Success");
        } else {
          errorToast(response.data.message);
        }
      } catch (error) {
        errorToast(error.response.data.message);
      }
    },
  },
};
</script>

<style scoped>
.demo-container,
form {
  display: flex;
  flex-direction: column;
}

.demo-container {
  background-color: #f8f8f8;
  align-items: center;
  border: 2px solid #222;
  border-radius: 5px;
  padding: 20px;
  width: max-content;
  color: #222;
  margin: 0 auto;
}

form {
  align-items: start;
  font-weight: 500;
}

label,
input {
  margin: 5px 0;
}

label {
  font-weight: 500;
}

input {
  padding: 10px 20px;
  border-radius: 3px;
  border: 2px solid #222;
  width: 350px;
  line-height: 20px;
  background-color: #f8f8f8;
  -webkit-transition: 0.5s;
  transition: 0.5s;
  outline: none;
  font-weight: 600;
  color: #222;
}

input:focus {
  border: 2px solid #4caf50;
}

button {
  border: none;
  width: 350px;
  padding: 10px 20px;
  margin: 10px auto;
  font-weight: 600;
  border-radius: 20px;
}
</style>
