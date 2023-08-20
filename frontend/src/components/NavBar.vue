<template>
    <nav class="nav">
        <section class="nav-img">
            <router-link to="/" class="nav-img-link">
                <img src="@/assets/logo.png" alt="Mail Flock Logo">
            </router-link>
        </section>
        <section class="nav-auth">
            <router-link class="link sec-btn" to="/login">Login</router-link>
            <router-link class="link prm-btn" to="/register">Register</router-link>
        </section>
    </nav>
</template>

<style scoped>
.nav {
    display: flex;
    justify-content: space-around;
    align-items: center;
    height: 80px;
    padding: 10px 30px;
    background-color: #f3f3f3;
    opacity: 0.8;
    backdrop-filter: blur(20px);
}

.nav > .nav-img > .nav-img-link {
    text-decoration: none;
}

.nav > .nav-img > .nav-img-link > img {
    max-height: 50px;
}

.nav > .nav-auth {
    display: flex;
    gap: 30px;
}
</style>

<script>
export default {
  name: 'NavBar'
}
</script>
