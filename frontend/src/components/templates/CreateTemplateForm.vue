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
export default {
  name: "CreateForm",
};
</script>
