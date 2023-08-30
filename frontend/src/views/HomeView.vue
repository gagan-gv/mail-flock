<template>
  <main class="landing">
    <section class="landing-content">
      <div class="text-container">
        <h1>
          Cold Mailing made Effortless:
          <span class="animated-word">{{ word }}</span>
        </h1>
      </div>
      <router-link to="/register" class="link prm-btn">Register</router-link>
      <router-link to="/contact" class="link sec-btn">Get in Touch</router-link>
    </section>
    <section class="landing-img">
      <img src="@/assets/landing.png" alt="Landing Image" />
    </section>
  </main>
  <router-view />
</template>

<script>
import axios from "axios";
export default {
  name: "HomeView",
  data() {
    return {
      words: ["Reach", "Connect", "Convert"],
      currentIndex: 0,
      intervalId: null,
      animationDuration: 1500,
      accessToken: localStorage.getItem("access_token"),
      refreshToken: localStorage.getItem("refresh_token"),
    };
  },
  created() {
    this.checkAccessToken();
  },
  computed: {
    word() {
      return this.words[this.currentIndex];
    },
  },
  methods: {
    startAnimation() {
      this.intervalId = setInterval(this.changeWords, this.animationDuration);
    },
    changeWords() {
      this.currentIndex = (this.currentIndex + 1) % this.words.length;
    },
    async checkAccessToken() {
      if (this.accessToken) {
        // Access Token is not expired
        this.$router.push("/dashboard");
      } else if (!this.accessToken && this.refreshToken) {
        // Access token is expired and can be refreshed
        await this.refreshAccessToken();
      } else {
        // Access & Refresh Tokens are expired
        this.$router.push("/");
      }
    },
    async refreshAccessToken() {
      if (this.refreshToken) {
        try {
          const response = await axios.post("/api/renew", {
            refresh_token: this.refreshToken,
          });
          this.accessToken = response.data.accessToken;
          localStorage.setItem("access_token", this.accessToken);
          this.checkAccessToken();
        } catch (error) {
          // Refresh token has expired
          this.$router.push("/");
        }
      } else {
        this.$router.push("/");
      }
    },
  },
  mounted() {
    this.startAnimation();
  },
  beforeUnmount() {
    clearInterval(this.intervalId);
  },
};
</script>

<style scoped>
.landing {
  display: flex;
  justify-content: center;
  align-content: center;
  padding: 40px 0;
}
.landing > .landing-content {
  font-weight: 600;
  font-size: 40px;
  width: 50%;
  text-align: left;
  padding: 160px 30px;
}
.landing > .landing-content > .text-conatiner {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100px;
  overflow: hidden;
}
.landing > .landing-content > .text-conatiner > .animated-word {
  display: inline-block;
  animation: moveUpAnimation 2s linear infinite;
}
.landing > .landing-content > .link:first-of-type {
  margin-right: 20px;
}
.landing > .landing-content span {
  color: #4caf50;
}
.landing > .landing-img {
  padding: 0 30px;
}
.landing > .landing-img > img {
  max-height: 70vh;
}
@keyframes moveUpAnimation {
  from,
  to {
    opacity: 0;
    transform: translateY(0);
  }
  50% {
    opacity: 1;
    transform: translateY(-100%);
  }
}
</style>
