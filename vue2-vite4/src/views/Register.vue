<template>
  <div class="wrapper">
    <div style="margin: 200px auto;width: 350px;height: 350px;background-color: #fff;border-radius: 10px;padding: 20px">
      <div style="text-align: center; font-size: 30px;margin: 20px auto;font-family: 'Microsoft YaHei UI'">注册</div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" :show-password="true" prefix-icon="el-icon-lock" v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请再次输入密码" @keydown.enter.native="handleRegister" size="medium" style="margin: 5px 0" :show-password="true" prefix-icon="el-icon-lock" v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <div style="margin: 10px 0; text-align: right">
          <el-form-item>
            <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登陆</el-button>
            <el-button type="primary" size="small" autocomplete="off" @click="handleRegister">注册</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import userApi from "@/api/userApi.js";
export default {
  name: "Register",
  data(){
    let checkUsername = (rule, value, callback)=>{
      if(!value){
        return callback(new Error('请输入用户名！'))
      }
      setTimeout(()=>{
        if(value.length<5||value.length>12){
          callback(new Error('长度在 5 到 12 个字符'))
        }else{
          callback()
        }
      },500)
    };
    let checkPassword = (rule, value, callback)=>{
      if(!value){
        return callback(new Error('请输入密码！'))
      }
      setTimeout(()=>{
        if(value.length<5||value.length>12){
          callback(new Error('长度在 5 到 12 个字符'))
        }else{
          callback()
        }
      },500)
    };
    let checkConfirmPassword = (rule, value, callback)=>{
      if(!value){
        return callback(new Error('请输入密码！'))
      }else if(value !== this.user.password){
        callback(new Error('两次密码不一致!'))
      }else {
        callback()
      }
    };
    return{
      user:{
        username: '',
        password: '',
        confirmPassword: ''
      },
      rules: {
        username: [
          { validator: checkUsername, trigger: 'blur'},
        ],
        password: [
          { validator: checkPassword, trigger: 'blur'},
        ],
        confirmPassword: [
          { validator: checkConfirmPassword, trigger: 'blur'},
        ],
      }
    }
  },

  methods:{
    handleRegister(){
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          userApi.register(this.user).then(res=>{
            if(res.code === "200"){
              //res是对象
              this.$message.success("注册成功")
              this.$router.push("/login")
            }else{
              this.$message.error(res.msg);
            }
          })
        } else {
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
.wrapper{
  height: 100vh;
  overflow: hidden;
  background-image: linear-gradient(to top right ,#FC4668,#3F5EFB);
}
</style>