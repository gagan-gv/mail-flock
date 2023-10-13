<template>
  <section class="send-mail-container">
    <h3>Send Mail</h3>
    <form @submit.prevent="sendMail">
      <label for="recipients">Recipient's Email Adresses:</label>
      <input
        type="text"
        name="recipients"
        id="recipients"
        v-model="to_mail"
        @input="parseEmail"
        required
        autocomplete="off"
      />
      <section>
        <input type="checkbox" v-model="isHTML" name="isHTML" id="isHTML" />
        <label for="isHTML">Render to a Web Template?</label>
      </section>
      <label for="subject">Subject:</label>
      <input
        type="text"
        v-model="subject"
        name="subject"
        id="subject"
        required
        autocomplete="off"
        autocapitalize="words"
      />
      <label for="content">Mail Content:</label>
      <textarea
        v-model="content"
        name="content"
        id="content"
        required
        autocomplete="off"
        autocapitalize="words"
      ></textarea>
      <button class="prm-btn" type="submit">Send Mail</button>
    </form>
  </section>
</template>

<script>
import { MAILING_API } from "@/utils/constants";
import { errorToast, successToast } from "@/utils/toastSetup";
import axios from "axios";

export default {
  name: "SendMailForm",
  data() {
    return {
      to_mail: "",
      subject: "",
      content: "",
      isHTML: false,
      to_mail_array: [],
    };
  },
  methods: {
    async sendMail() {
      try {
        const accessToken = localStorage.getItem("access_token");
        axios.defaults.headers.common[
          "Authorization"
        ] = `Bearer ${accessToken}`;

        const response = await axios.post(MAILING_API, {
          to_mail: this.to_mail_array,
          subject: this.subject,
          content: this.content,
          html: this.isHTML,
        });

        if (response.status == 200) {
          successToast(response.data.message);
        } else {
          errorToast(response.data.message);
        }
      } catch (error) {
        errorToast(error.response.data.message);
      }
    },

    parseEmail() {
      this.to_mail_array = this.to_mail.split(",").map((val) => val.trim());
    },
  },
};
</script>

<style scoped>
.send-mail-container,
form {
  display: flex;
  flex-direction: column;
}

.send-mail-container {
  background-color: #f8f8f8;
  align-items: center;
  border: 2px solid #222;
  border-radius: 5px;
  padding: 20px;
  width: max-content;
  color: #222;
  margin: 50px auto;
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
  width: 1080px;
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

form > section {
  line-height: 15px;
  padding: 10px 0;
}

input[type="checkbox"] {
  accent-color: #4caf50;
  color: #f8f8f8;
  width: 20px;
  height: 12px;
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
