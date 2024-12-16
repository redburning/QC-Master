<template>
  <body id="login-page">
    <div style="max-width: 478px; width: calc(100% - 2rem); position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);">
      <el-form class="login-container" label-position="left" label-width="0px">
        <div class="login-title-container">
          <img style="height:70px;" src="@/assets/imgs/logo2.png" alt="QC-Master">
          <div style="text-align:center; margin-top:10px;">
            <h1 class="login-title">Welcome to QC-Master</h1>
          </div>
        </div>
        <el-form-item>
          <div class="form-item-label">Username</div>
          <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="username" style="height:32px;"></el-input>
        </el-form-item>
        <el-form-item>
          <div class="form-item-label">Password</div>
          <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="password"></el-input>
        </el-form-item>
        <el-form-item style="width:100%">
          <el-button class="login-btn" @click="login"><span style="color: rgb(255, 255, 255);">Log in</span></el-button>
        </el-form-item>
      </el-form>
    </div>
  </body>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      this.axios({
        method: 'post',
        url: 'api/login',
        data: this.loginForm
      }).then(successResp => {
        if (successResp.data.code === 200) {
          this.$router.replace({path: '/'})
        }
      }).catch(failResp => {
        console.log(failResp)
      })
    }
  }
}
</script>

<style scoped>
#login-page {
  background: url("../assets/imgs/login_dark.svg") center top / auto no-repeat;
  background-position: center center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}

#login-page::before {
  opacity: 1 !important;
}

#login-page::before {
    content: "";
    position: absolute;
    inset: 0px;
    background: url("../assets/imgs/login_dark.svg") center top / auto no-repeat;
    opacity: 0;
    transition: opacity 3s ease-in-out 0s;
}

body {
  margin: 0px;
}

.login-container {
  border-radius: 8px;
  background-clip: padding-box;
  padding: 35px 35px 15px 35px;
  transition: opacity 0.5s ease-in-out 0s;
  background:  0% 0% / cover rgba(24, 27, 31, 0.7);
}

.login-title-container {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  flex-direction: column;
  padding: 24px 0px;
}

.login-title {
  color: rgb(204, 204, 220);
  margin: 0px 0px 0.45em;
  font-size: 28px;
  line-height: 1.14286;
  font-weight: 400;
  letter-spacing: -0.00893em;
  font-family: Inter, Helvetica, Arial, sans-serif;
}

.form-item-label {
  font-size: 12px;
  font-weight: 500;
  line-height: 1.25;
  color: rgb(204, 204, 220);
  max-width: 480px;
  letter-spacing: 0.01071em;
}

.el-input /deep/ .el-input__inner {
  background-color: #111217 !important;
  border-color: rgb(46, 47, 53) !important;
  line-height: 1.57143;
  font-size: 14px;
  color: rgb(204, 204, 220);
  height: 32px;
}

.el-input /deep/ .el-input__inner::placeholder {
  color:#7d7c89;
}

.el-input input::-webkit-input-placeholder {
  color:#7d7c89;
}
.el-input input::-moz-input-placeholder {
  color:#7d7c89;
}
.el-input input::-ms-input-placeholder {
  color:#7d7c89;
}

.el-input /deep/ .el-input__inner:focus {
  border: 2px solid #3d71d9 !important;
}

.login-btn {
  width:100%;
  border:none;
  background-color: rgb(61, 113, 217);
  height: 32px;
  line-height: 0px;
}

.login-btn:hover {
  background-color: #5a86de;
}
</style>
