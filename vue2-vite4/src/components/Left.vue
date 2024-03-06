<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="clearfix" style="text-align: center">
        <span style="font-size: xx-large">公告</span>
      </div>
      <div v-for="o in 10" :key="o" class="text item">
        {{'列表内容 ' + o }}
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Left"
}
</script>

<style scoped>

</style>