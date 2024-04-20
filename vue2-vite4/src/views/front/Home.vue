<template>
  <div class="HomeBg">
    <div style="margin: 0;display: flex;justify-content: center">
      <!--左-->
      <div style="flex: 1; max-width: 400px;" class="hidden-md-and-down">
        <el-card class="box-card">
          <div slot="header" class="clearfix" style="text-align: center">
            <span style="font-size: xx-large"><i class="iconfont icon-gonggao" style="font-size: 32px;color: green"></i>公告</span>
          </div>
          <div>
            主页不知道放什么了
          </div>
        </el-card>
      </div>
      <!--中-->
      <div style="flex: 3;max-width: 600px;margin: 0 10px;">
        <!--轮播图-->
        <div style="border-bottom: 1px solid #ccc;">
          <!--height=10vh -->
          <el-carousel :interval="4000" height="25vh" style="border-radius: 5px;">
            <el-carousel-item v-for="item in images" :key="item.id">
              <el-image :src="item.url"></el-image>
            </el-carousel-item>
          </el-carousel>
        </div>
        <!--内容-->
        <div style="margin: 10px 0">

            <viewer :images="urls">
              <img v-for="(url,index) in urls" :key="index" :src="url" v-lazy="url" alt="test" width="100%">
            </viewer>

        </div>

      </div>
      <!--右-->
      <div style="flex: 1;max-width: 400px" class="hidden-md-and-down">

      </div>
    </div>
  </div>
</template>

<script>

import homeApi from "@/api/homeApi.js";

export default {
  name: "Home",
  data(){
    return{
      testImages:[
        'http://localhost:9090/api/files/dbf21f06f21b4e67b5f2c8c172353a25.png'
      ],
      images:[],
      browserWidth: window.innerWidth,
      urls: [
        'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
        'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
        'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg',
        'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg',
        'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
        'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
        'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
      ]
    }
  },
  created() {
    this.load()
  },
  mounted() {
    window.addEventListener('resize', this.handleResize);
  },
  destroyed() {
    window.removeEventListener('resize', this.handleResize);
  },

  beforeRouteEnter(to,from,next){
    next(vm => {
      //因为当钩子执行前，组件实例还没被创建
      // vm 就是当前组件的实例相当于上面的 this，所以在 next 方法里你就可以把 vm 当 this 来用了。
      window.addEventListener('resize', vm.handleResize);

    });
  },
  // 删除滚动监听器，建议使用beforeRouteLeave，因为destroyed()钩子在路由跳转时不会触发(加了这个就跳不了路由)
  beforeRouteLeave(to,from,next) {
    window.removeEventListener('resize', this.handleResize);
    next()
  },
  methods:{
    load(){
      homeApi.getAll().then(res=>{
        if(res.code === '200'){
          this.images = res.data
        }
      })
    },
    handleResize() {
      this.browserWidth = window.innerWidth;
    },
  }
}
</script>

<style scoped>
.HomeBg{
  min-height: 100vh;
  padding: 10px 0;
  background-image: url("@/assets/img/mcbg.jpg");
  background-repeat: no-repeat;
  background-attachment: fixed;
}

</style>