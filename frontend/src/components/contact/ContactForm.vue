<template>
  <section class="contact-container">
    <h2>Contact Us</h2>
    <form @submit.prevent="contact">
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
      <label for="subject">Subject</label>
      <input
        type="text"
        v-model="subject"
        name="subject"
        id="subject"
        required
        autocomplete="subject"
      />
      <label for="message">Message</label>
      <textarea
        v-model="message"
        name="message"
        id="message"
        required
      ></textarea>
      <button class="prm-btn" type="submit">Send Mail</button>
    </form>
  </section>
</template>

<script>
import { CONTACT_API } from "@/utils/constants";
import { errorToast, successToast } from "@/utils/toastSetup";
import axios from "axios";

export default {
  name: "ContactForm",
  data() {
    return {
      name: "",
      email: "",
      subject: "",
      message: "",
    };
  },
  methods: {
    async contact() {
      try {
        const response = await axios.post(CONTACT_API, {
          name: this.name,
          emailId: this.email,
          subject: this.subject,
          content: this.message,
        });

        if (response.status == 201) {
          successToast(
            "We've received your message!<br />We'll get back to you soon"
          );
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
.contact-container,
form {
  display: flex;
  flex-direction: column;
}
.contact-container {
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
input,
textarea {
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
textarea {
  height: 200px;
  resize: none;
}
input:focus,
textarea:focus {
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
