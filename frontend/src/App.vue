<template>
  <NavBar />
  <router-view/>
  <FooterComponent />
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import FooterComponent from '@/components/Footer.vue'

export default {
  name: 'LoginView',
  components: {
    NavBar,
    FooterComponent
  }
}
</script>

<style>
#app {
  font-family: 'Work Sans', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #222;
  background-color: #f3f3f3;
  height: 100vh;
}

.link,
.link:hover,
.link:visited,
.link:active {
  color: #222;
  text-decoration: none;
  font-size: 20px;
  font-weight: 500;
  position: relative;
}

.prm-btn {
  display: inline-block;
  text-align: center;
  color: #f3f3f3 !important;
  background-color: #4caf50;
  padding: 10px 20px;
  border-radius: 30px;
  min-width: 30px;
  margin: -10px 0;
}

.prm-btn:hover {
  color: #f3f3f3;
  filter: drop-shadow(0 0 20px #ccc);
}

.sec-btn::before {
  content: '';
  position: absolute;
  width: 88%;
  height: 3px;
  border-radius: 5px;
  background-color: #4caf50;
  bottom: 0;
  left: 0;
  transform-origin: right;
  transform: scaleX(0);
  transition: 0.5s all;
}

.sec-btn::after {
  content: '';
  position: absolute;
  width: 8%;
  height: 3px;
  border-radius: 20px;
  background-color: #4caf50;
  bottom: 0;
  right: 0;
  transform-origin: left;
  transform: scaleX(0);
  transition: 0.5s all;
}

.sec-btn:hover::before {
  transform-origin: right;
  transform: scaleX(1);
}

.sec-btn:hover::after {
  transform-origin: left;
  transform: scaleX(1);
}
</style>
