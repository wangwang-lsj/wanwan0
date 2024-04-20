<template>
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane label="轮播图管理" name="first"><HomeSliderShow></HomeSliderShow></el-tab-pane>
    <!--<el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>-->
  </el-tabs>
</template>

<script>
import HomeSliderShow from "@/views/manage/HomeSliderShow.vue";

export default {
  name: "Home",
  data() {
    return {
      activeName: 'first'
    };
  },
  components:{
    HomeSliderShow
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
}
</script>

<style scoped>

</style>