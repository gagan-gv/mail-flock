<template>
  <section class="create-template-container">
    <h3>Create a Template</h3>
    <form @submit.prevent="createTemplate">
      <label for="template-name">Template Name:</label>
      <input
        type="text"
        v-model="templateName"
        name="template-name"
        id="template-name"
        required
        autocomplete="off"
        autocapitalize="words"
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
      <button class="prm-btn" type="submit">Save Template</button>
    </form>
  </section>
</template>

<script>
import { TEMPLATE_API } from "@/utils/constants";
import { errorToast, successToast } from "@/utils/toastSetup";
import axios from "axios";

export default {
  name: "CreateForm",
  data() {
    return {
      templateName: "",
      subject: "",
      content: "",
      isHTML: "",
    };
  },
  methods: {
    async createTemplate() {
      try {
        const accessToken = localStorage.getItem("access_token");
        axios.defaults.headers.common[
          "Authorization"
        ] = `Bearer ${accessToken}`;

        const response = await axios.post(TEMPLATE_API, {
          name: this.templateName,
          subject: this.subject,
          content: this.content,
          html: this.isHTML,
        });

        console.log(response);

        if (response.status == 201) {
          successToast(response.data.message);
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
.create-template-container,
form {
  display: flex;
  flex-direction: column;
}

.create-template-container {
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
