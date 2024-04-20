<template>
  <div>
    <el-row :gutter="40" style="margin: 0;" type="flex" justify="center">

      <el-col style="flex: 1;max-width: 300px" class="hidden-md-and-down">
        <el-card class="box-card" style="width: 150px;float: right">
          <div class="category-item" :class="{'category-item-active': item.name === current}" v-for="item in categoryList" :key="item.id" >{{ item.name }}</div>
        </el-card>
      </el-col>

      <el-col style="flex: 3;max-width: 800px">
        <ul class="infinite-list" v-infinite-scroll="nextPageLoad" :infinite-scroll-disabled="disabled" style="overflow:auto;height:calc(100vh - 100px)">
          <li v-for="article in articleList" :key="article.id" class="infinite-list-item">
            <el-card style="display: flex;width: 100%; margin-bottom: 20px" class="article box-card">
              <div style="flex: 1; width: 0" class="article_title_content">
                <div style="font-size: 16px; font-weight: bold;margin-bottom: 10px" class="article_title">
                  {{ article.title }}
                </div>
                <div class="article_content" style="color: #666666;margin-bottom: 30px;">
                  {{ article.description }}
                </div>
                <div style="display: flex">
                  <div style="flex: 1">
                    <span style="color: #666666;margin-right: 20px"><i class="el-icon-user"></i>{{ article.userName }}</span>
                    <span style="color: #666666;margin-right: 20px"><i class="el-icon-eye"></i>{{ article.readCount }}</span>
                    <span style="color: #666666;margin-right: 20px"><i class="el-icon-like"></i>{{ article.likes }}</span>
                  </div>
                  <div style="width: fit-content">
                    <el-tag v-for="tag in JSON.parse(article.tags||'[]')" :key="tag" type="primary" style="margin-right: 10px">{{ tag }}</el-tag>
                  </div>
                </div>
              </div>
              <div style="width: 100px; margin-left: 20px" class="article_image">
                <el-image v-if="article.cover" :src="article.cover" style="border-radius: 5px"></el-image>
                <el-image v-else :src="require('@/assets/img/暂无图片.jpg')" style="border-radius: 5px"></el-image>
              </div>
            </el-card>
          </li>
          <p v-if="noMore" style="text-align: center">没有更多了</p>
        </ul>
      </el-col>

      <el-col style="flex: 1; max-width: 300px" class="hidden-md-and-down">
        <el-card class="box-card">

        </el-card>
      </el-col>
    </el-row>
  </div>

</template>

<script>
export default {
  name: "Article",
  data(){
    return{
      pageNum: 1,
      pageSize: 5,
      total: 0,
      current: '',
      categoryList:[],
      count: 10,
      articleList: [],
    }
  },
  created() {
    this.load()
  },
  computed:{
    noMore(){
      return this.articleList.length>=this.total
    },
    disabled(){
      return this.noMore
    }
  },
  mounted() {
    window.addEventListener('scroll', this.windowScroll,true) //监听页面滚动
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.windowScroll);//销毁滚动事件
  },
//删除滚动监听器，建议使用beforeRouteLeave，因为destroyed()钩子在路由跳转时不会触发
  beforeRouteLeave() {
    window.removeEventListener("scroll", this.windowScroll);//销毁滚动事件
  },
  methods: {
    //获取当前可视范围的高度
    getClientHeight() {
      var clientHeight = 0;
      if (document.body.clientHeight && document.documentElement.clientHeight) {
        clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight)
      } else {
        clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight)
      }
      return clientHeight
    },

    //获取文档完整的高度
    getScrollHeight() {
      return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight)
    },

    //获取当前滚动条的位置
    getScrollTop() {
      var scrollTop = 0;
      //window.pageYOffset = document.documentElement.scrollTop
      if (document.documentElement && document.documentElement.scrollTop) {
        scrollTop = document.documentElement.scrollTop
      } else if (document.body) {
        scrollTop = document.body.scrollTop
      }
      return scrollTop
    },
    //回调函数
    windowScroll() {
      //获取三个值
      var scrollTop = this.getScrollTop()
      var clientHeight = this.getClientHeight()
      var scrollHeight = this.getScrollHeight()
      console.log(scrollHeight)
      console.log(clientHeight)
      console.log(scrollHeight)
      //如果满足公式则，确实到底了
      if(scrollTop+clientHeight === scrollHeight){
        //发送异步请求请求数据，同时携带offset并自增offset
        //noMore是自定义变量，如果是最后一批数据则以后都不加载
        if(!this.noMore) {
          // console.log("111")
          this.pageNum += 1
          this.request.post('/article/page', {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          }).then(res => {
            if (res.code === '200') {
              this.articleList = this.articleList.concat(res.data.records)
            }
          })
        }
      }
    },

    load() {
      this.request.post('/article/page', {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      }).then(res => {
        if (res.code === '200') {
          this.articleList = res.data.records
          this.total = res.data.total
        }
      })
      this.request.get('/category/selectAll/').then(res => {
        if (res.code === '200') {
          this.categoryList = res.data
          this.categoryList.unshift({name: "全部博客"})
        }
      })
      // this.count += 2
    },
    nextPageLoad() {
      // console.log("111")
      this.pageNum += 1
      this.request.post('/article/page', {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      }).then(res => {
        if (res.code === '200') {
          this.articleList = this.articleList.concat(res.data.records)
        }
      })
    }
  }
}
</script>

<style>
.category-item{
  padding: 10px 0;
  text-align: center;
  font-size: 18px;
  cursor: pointer;
}
.category-item-active{
  background-color: #3F5EFB;
  color: #ffffff;
  border-radius: 5px;

}
.box-card.article .el-card__body{
  display: flex;
  width: 100%;
}
.article_content{
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

</style>