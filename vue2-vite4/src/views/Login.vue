<template>
  <div class="wrapper">
    <div style="margin: 200px auto;width: 350px;height: 300px;background-color: #fff;border-radius: 10px;padding: 20px">
      <div style="text-align: center; font-size: 30px;margin: 20px auto;font-family: 'Microsoft YaHei UI'">登陆</div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" @keydown.enter.native="login" auto-complete="on" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" @keydown.enter.native="login" auto-complete="on" size="medium" style="margin: 10px 0" :show-password="true" prefix-icon="el-icon-lock" v-model="user.password" ></el-input>
        </el-form-item>
        <div style="margin: 10px 0; text-align: right">
          <el-form-item>
            <el-button type="warning" size="small" @click="$router.push('/register')">前去注册</el-button>
            <el-button type="primary" size="small" @click="login">登陆</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import user from "@/views/manage/User.vue";
import {setRoutes} from "@/router";

export default {
  name: "Login",
  data(){
    return{
      user:{
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名!', trigger: 'blur'},
          {min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码!', trigger: 'blur'},
          {min: 5, max: 12, message: '长度在 5 到 12 个字符', trigger: 'blur'}
        ],
      }

    }
  },
  methods:{
    login(){
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          this.request.post("/user/login",this.user).then(res=>{
            if(res.code === "200"){
              //res是对象
              localStorage.setItem("user",JSON.stringify(res.data))
              localStorage.setItem("menus",JSON.stringify(res.data.menus))
              setRoutes()
              this.$message.success("登陆成功")
              this.$router.push("/home")
            }else{
              this.$message.error(res.msg);
            }
          }).catch((error)=>{
            console.log(error)
          })
        } else {
          console.log('error submit!!');
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
    background-image: linear-gradient(to bottom right ,#FC4668,#3F5EFB);
  }
</style>