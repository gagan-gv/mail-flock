<template>
  <footer class="footer">
    <section class="footer-logo">
      <img src="@/assets/logo.png" alt="Logo" />
    </section>
    <section class="footer-links">
      <h3>Links</h3>
      <ul>
        <li>
          <router-link to="/services" class="link">Services</router-link>
        </li>
        <li>
          <router-link to="/templates" class="link">Templates</router-link>
        </li>
        <li>
          <router-link to="/newsletters" class="link">Newsletters</router-link>
        </li>
        <li>
          <router-link to="/contact" class="link">Contact</router-link>
        </li>
      </ul>
    </section>
    <section class="subscribe">
      <h3>Subscribe to our Newsletter</h3>
      <form>
        <input
          type="email"
          name="email"
          id="input-email"
          class="input"
          placeholder="Enter your Email ID"
        />
        <button type="submit" class="btn prm-btn">Subscribe</button>
      </form>
    </section>
  </footer>
  <p>Copyright &copy; Mail Flock 2023</p>
</template>

<script>
export default {
  name: "FooterComponent",
};
</script>

<style scoped>
.footer {
  background: #f3f3f3;
  position: relative;
  display: flex;
  justify-content: space-around;
  align-items: center;
  bottom: 0;
  border-top: 2px solid #222;
  padding: 25px 0;
}

.footer h3 {
  font-size: 24px;
  margin: 0 0 10px 0;
}

.footer > .footer-links > ul {
  display: block;
  list-style: none;
}

.footer > .footer-links > ul > li {
  padding: 10px 0;
}

.footer > .subscribe > form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.footer > .subscribe > form > button {
  border: none;
  width: 150px;
  font-size: 18px;
  font-family: "Work Sans", sans-serif;
  font-weight: 500;
  margin: 5px 0;
}

.footer > .subscribe > form > .input {
  margin: 10px 0 20px 0;
  align-self: stretch;
  padding: 15px 10px;
  border: 2px solid #222;
  border-radius: 10px;
  font-size: 16px;
}

p {
  padding: 10px 0;
}
</style>
