<!-- eslint-disable vue/no-unused-components -->
<template>
    <section class="login-container">
        <h2>Login</h2>
        <form @submit.prevent="login">
            <label for="username">Username</label>
            <input type="text" v-model="username" id="username" required />
            <label for="password">Password</label>
            <input type="password" v-model="password" id="password" required />
            <button type="submit">Login</button>
        </form>
    </section>
</template>

<script>
import axios from 'axios'
import 'vue-toast-notification/dist/theme-sugar.css'
import VueToast from 'vue-toast-notification'

export default {
  name: 'LoginForm',
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    async login () {
      try {
        const response = await axios.post('/api/login', {
          username: this.username,
          password: this.password
        })

        const accessToken = response.data.access_token
        const refreshToken = response.data.refresh_token

        if (accessToken) {
          localStorage.setItem('mail_flock_access_token', accessToken)
          localStorage.setItem('mail_flock_refresh_token', refreshToken)
          this.$router.push('/dashboard')
        } else {
          this.showErrorToast('Invalid Credentials or Internal Error')
        }
      } catch (error) {
        this.showErrorToast(error.toString())
      }
    },
    showErrorToast (message) {
      this.$toast.error(message, { position: 'top' })
    }
  },
  components: {
    'vue-toast': VueToast
  }
}
</script>
