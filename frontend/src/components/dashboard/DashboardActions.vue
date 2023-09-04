<template>
  <section class="box" @click="redirectToPage">
    <i :class="iconClass"></i>
    <h4>{{ action }}</h4>
  </section>
</template>

<script>
export default {
  name: "DashboardActions",
  props: ["action", "iconClass", "route"],
};
</script>

<style scoped>
.box {
  position: relative;
  width: 300px;
  height: 300px;
  border: 2px solid #222;
  border-radius: 2px;
  cursor: pointer;
  padding: 20px;
  margin: 10px;
  text-align: center;
}

i {
  font-size: 32px;
  color: #222;
}

h4 {
  color: #222;
}

.box:hover {
  transform: translateY(-5px);
  filter: drop-shadow(0 0 10px #222);
}

.box:hover i,
.box:hover h4 {
  color: #4caf50;
}
</style>
