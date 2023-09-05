<template>
  <section class="box" @click="redirectToPage">
    <i :class="iconClass"></i>
    <h4>{{ action }}</h4>
  </section>
</template>

<script>
export default {
  name: "DashboardActions",
  props: ["action", "iconClass", "routeTo"],
  methods: {
    redirectToPage() {
      this.$router.push(this.routeTo);
    },
  },
};
</script>

<style scoped>
.box {
  position: relative;
  background-color: #f3f3f3;
  width: 250px;
  height: 250px;
  border: 2px solid #222;
  border-radius: 3px;
  cursor: pointer;
  padding: 20px;
  margin: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

i {
  font-size: 56px;
  color: #222;
}

h4 {
  margin: 10px;
  font-size: 18px;
  color: #222;
}

.box:hover {
  border: 2px solid #4caf50;
}

.box:hover i,
.box:hover h4 {
  color: #4caf50;
}
</style>
