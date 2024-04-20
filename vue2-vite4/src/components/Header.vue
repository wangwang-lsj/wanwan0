<template>
  <div style="line-height: 60px; display: flex ;height: 100%;">
    <div style="flex: 1; text-align: left;">
      <span :class="collapseBtnClass" style="cursor: pointer;font-size: 20px;" @click="collapse" ></span>
      <el-breadcrumb separator-class="el-icon-arrow-right" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>

      </el-breadcrumb>
    </div>

    <el-dropdown style="width: 100px;height: 100% ;cursor: pointer;" trigger="click">
      <div style="display: inline-block; width: 100%; height: 60px">
        <div style="height: 40px;width: 40px;margin: 0 auto">
          <img :src="user.avatarUrl" alt=""
               style="width: 40px; border-radius: 50%;position: relative;">
        </div>
        <div style="width: 100%;height: 20px; line-height: 20px;text-align: center">
          <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style=""></i>
        </div>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center; ">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/manage/person">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/manage/password">修改密码</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/home">前往主页</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span @click="logout" style="text-decoration: none">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass: String,
    collapse: Function,
    user: Object
  },
  watch:{//监听路由变化
    '$route': function (){
      this.currentPathName = localStorage.getItem("currentPathName")
    }
  },

  data(){
    return {
      currentPathName:''
    }
  },
  methods:{
    logout(){
      this.$store.commit("logout")
      this.$message.success("退出成功")
    },
  }
}
</script>

<style>

</style>