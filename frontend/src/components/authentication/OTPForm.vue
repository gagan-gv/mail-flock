<template>
  <section class="otp-container">
    <h2>OTP Verification</h2>
    <p>OTP has been sent to: {{ email }}</p>
    <div>
      <input
        type="number"
        v-model="otp[0]"
        maxlength="1"
        @input="focusNextInput(1)"
        ref="otpInput0"
        required
      />
      <input
        type="number"
        v-model="otp[1]"
        maxlength="1"
        @input="focusNextInput(2)"
        ref="otpInput1"
        required
      />
      <input
        type="number"
        v-model="otp[2]"
        maxlength="1"
        @input="focusNextInput(3)"
        ref="otpInput2"
        required
      />
      <input
        type="number"
        v-model="otp[3]"
        maxlength="1"
        @input="focusNextInput(4)"
        ref="otpInput3"
        required
      />
      <input
        type="number"
        v-model="otp[4]"
        maxlength="1"
        @input="focusNextInput(5)"
        ref="otpInput4"
        required
      />
      <input
        type="number"
        v-model="otp[5]"
        maxlength="1"
        @input="verifyOTP()"
        ref="otpInput5"
        required
      />
    </div>
  </section>
</template>

<style scoped>
.otp-container {
  display: flex;
  flex-direction: column;
  background-color: #f8f8f8;
  align-items: center;
  border: 2px solid #222;
  border-radius: 5px;
  padding: 20px;
  width: max-content;
  color: #222;
  margin: 0 auto;
}

input {
  padding: 10px 20px;
  border-radius: 3px;
  border: 2px solid #222;
  width: 50px;
  line-height: 20px;
  background-color: #f8f8f8;
  -webkit-transition: 0.5s;
  transition: 0.5s;
  outline: none;
  font-weight: 600;
  color: #222;
  margin: 10px;
}

input:focus {
  border: 2px solid #4caf50;
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
}
</style>

<script>
import { AUTH_API } from "@/utils/constants";
import { errorToast } from "@/utils/toastSetup";
import axios from "axios";

export default {
  name: "OTPForm",
  props: ["email"],
  data() {
    return {
      otp: ["", "", "", "", "", ""],
    };
  },
  methods: {
    focusNextInput(index) {
      if (index < this.otp.length) {
        this.$refs[`otpInput${index}`].focus();
      }
    },
    verifyOTP() {
      try {
        const enteredOTP = this.otp.join("");
        axios.patch(
          AUTH_API + "/verify?emailId=" + this.email + "&otp=" + enteredOTP
        );

        this.$router.push("/login");
      } catch (error) {
        errorToast(error.data.message);
      }
    },
  },
};
</script>
