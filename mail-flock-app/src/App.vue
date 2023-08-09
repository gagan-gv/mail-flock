<template>
  <NavBar />
  <router-view />
  <FooterComponent />
</template>

<style>
#app {
  font-family: "Work Sans", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #222;
  background-color: #f3f3f3;
}

/* Primary and Secondary Buttons */

.prm-btn {
  display: inline-block;
  text-align: center;
  background: #4caf50;
  color: #f3f3f3;
  margin: -10px 0;
  padding: 10px 30px;
  border-radius: 30px;
  min-width: 30px;
}

.prm-btn:hover {
  filter: drop-shadow(0 0 20px #ccc);
}

.sec-btn {
  color: #222;
  text-decoration: none;
}

.sec-btn::before {
  content: "";
  position: absolute;
  width: 88%;
  height: 3px;
  border-radius: 5px;
  background-color: #4caf50;
  bottom: 0;
  left: 0;
  transform-origin: right;
  transform: scaleX(0);
  transition: transform 0.25s ease-in-out;
}

.sec-btn:hover::before {
  transform-origin: right;
  transform: scaleX(1);
}

.sec-btn::after {
  content: "";
  position: absolute;
  width: 8%;
  height: 3px;
  border-radius: 20px;
  background-color: #4caf50;
  bottom: 0;
  right: 0;
  transform-origin: left;
  transform: scaleX(0);
  transition: transform 0.25s ease-in-out;
}

.sec-btn:hover::after {
  transform-origin: left;
  transform: scaleX(1);
}

/* Links */
.link {
  color: #222;
  text-decoration: none;
  font-size: 20px;
  font-weight: 500;
  position: relative;
}

.link::before {
  content: "";
  position: absolute;
  width: 88%;
  height: 3px;
  border-radius: 5px;
  background-color: #4caf50;
  bottom: 0;
  left: 0;
  transform-origin: right;
  transform: scaleX(0);
  transition: transform 0.25s ease-in-out;
}

.link:hover::before {
  transform-origin: right;
  transform: scaleX(1);
}

.link::after {
  content: "";
  position: absolute;
  width: 8%;
  height: 3px;
  border-radius: 20px;
  background-color: #4caf50;
  bottom: 0;
  right: 0;
  transform-origin: left;
  transform: scaleX(0);
  transition: transform 0.25s ease-in-out;
}

.link:hover::after {
  transform-origin: left;
  transform: scaleX(1);
}
</style>

<script>
import NavBar from "@/components/NavBar.vue";
import FooterComponent from "@/components/Footer.vue";

export default {
  components: {
    NavBar,
    FooterComponent,
  },
};
</script>
