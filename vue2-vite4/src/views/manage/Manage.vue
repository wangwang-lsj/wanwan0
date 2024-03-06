<template>
  <el-container style="min-height: 100vh;">
    <el-aside :width="sideWidth+'px'" style="box-shadow: 2px 0 6px rgb(0 21 41)">
      <Aside :isCollapse="isCollapse"></Aside>
    </el-aside>

    <el-container style="min-height: 100vh">
      <el-header style="border-bottom:1px solid gray; height: 60px">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"></Header>
      </el-header>

      <el-main>
        <router-view @refreshUser="getUser"/>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";
export default {
  components:{
    Aside,
    Header,
  },
  data() {
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    };
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse:false,
      sideWidth: 200,
      user: localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},

    }
  },
  created() {
    this.getUser()
  },
  methods:{
    collapse(){
      this.isCollapse=!this.isCollapse
      if(this.isCollapse){
        this.sideWidth=64
        this.collapseBtnClass='el-icon-s-unfold'
      }else {
        this.sideWidth=200
        this.collapseBtnClass='el-icon-s-fold'
      }
    },
    getUser(){
      this.request.get("/user/username/"+this.user.username).then((res)=>{
        if(res.code === '200'){
          this.user = res.data
        }
      })
    },

  }
};
</script>
<style>

</style>