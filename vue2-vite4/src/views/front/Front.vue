<template>
  <div>
    <div style="height: 60px;line-height: 60px;border-bottom: 1px solid #ccc; display: flex;margin-bottom: 10px;">
      <div style="width: 250px; display: flex;flex: 1; padding-left: 30px">
        <div style="height: 60px; width: 60px">
          <img src="../../assets/dragon.webp" width="60px">
        </div>
        <div style="flex: 1;overflow: hidden">欢迎来到xx小站</div>
      </div>
      <div style="flex:3">
        <el-menu class="el-menu-demo" mode="horizontal" router style="display: flex;justify-content: center">
          <el-menu-item index="/home">主页</el-menu-item>
          <el-menu-item index="/article">文章</el-menu-item>
          <el-menu-item index="/video">视频</el-menu-item>
          <el-submenu index="">
            <template slot="title">我的工作台</template>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
            <el-menu-item index="2-3">选项3</el-menu-item>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项1</el-menu-item>
              <el-menu-item index="2-4-2">选项2</el-menu-item>
              <el-menu-item index="2-4-3">选项3</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-menu-item index="/guestbook">留言板</el-menu-item>

        </el-menu>
      </div>
      <div style="width: 200px; text-align: right;flex: 1">
        <el-dropdown v-if="JSON.stringify(user)!=='{}'" style="width: 100px;height: 100% ;cursor: pointer;" >
          <div style="display: inline-block; width: 100%; height: 60px">
            <div style="height: 40px;width: 40px;margin: 0 auto">
              <img :src="user.avatarUrl" alt=""
                   style="width: 40px; border-radius: 50%;position: relative;">
            </div>
            <div style="width: 100%;height: 20px; line-height: 20px;text-align: center">
              <span>{{ user.nickname }}</span><i class="el-icon-arrow-down"></i>
            </div>
          </div>
          <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center;">
            <el-dropdown-item style="font-size: 14px; padding: 5px 0">
              <router-link to="/manage/backhome">前往后台</router-link>
            </el-dropdown-item>
            <el-dropdown-item style="font-size: 14px; padding: 5px 0">
              <span @click="logout()" style="text-decoration: none">退出</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <div v-else style="width: 100px;height: 100% ;cursor: pointer; text-align: center;float: right">
          <router-link to="/login">登陆</router-link>
        </div>
      </div>
    </div>
    <div style="display: flex;justify-content: center; min-height: 100%">
      <div style="flex: 1" v-if="showComponent">
        <Left></Left>
      </div>
      <div style="width: 1000px;min-height: 1000px;margin: 0 10px;border: 1px solid #666666;padding: 10px">
        <router-view/>
      </div>
      <div style="flex: 1;" v-if="showComponent">
        <Right></Right>
      </div>
    </div>
  </div>

</template>

<script>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";
import Left from "@/components/Left.vue";
import Right from "@/components/Right.vue";
import user from "@/views/manage/User.vue";

export default {
  name: "Front",
  components:{
    Left,
    Right,
  },
  data(){
    return{
      user: localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      showComponent: true
    }
  },
  mounted() {
    window.addEventListener('resize', this.handleResize);
    this.handleResize();
  },
  destroyed() {
    window.removeEventListener('resize', this.handleResize);
  },
  methods:{
    logout(){
      this.$store.commit("logout")
      this.$message.success("退出成功")
    },
    handleResize() {
      this.showComponent = window.innerWidth > 1500;
    },
  }
}
</script>

<style scoped>

</style>