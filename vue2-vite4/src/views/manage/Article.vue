<template>
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane label="文章管理" name="first" >
      <div style="margin: 10px 0">
        <el-input style="width: 200px;margin-right: 10px" placeholder="请输入作者名称" prefix-icon="el-icon-search" v-model="userName"></el-input>
        <el-input style="width: 200px;margin-right: 10px" placeholder="请输入标题名称" prefix-icon="el-icon-search" v-model="title"></el-input>
        <el-input style="width: 200px;margin-right: 10px" placeholder="请输入类别名称" prefix-icon="el-icon-search" v-model="categoryName"></el-input>

        <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
        <el-button style="margin-left: 5px" type="warning" @click="reset">重置</el-button>
      </div>
      <div style="margin: 10px 0">
        <el-button type="primary" @click="handleAdd"style="margin-right: 5px">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        <template>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确定批量删除吗？"
              @confirm="handleDeleteBatch"
          >
            <el-button type="danger" slot="reference" style="margin-right: 5px">批量删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </div>

      <el-table :data="tableData" :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange" border stripe>
        <el-table-column type="selection"></el-table-column>
        <el-table-column prop="id" label="ID" width="40"></el-table-column>
        <el-table-column prop="userName" label="作者">

        </el-table-column>
        <el-table-column prop="title" label="标题">

        </el-table-column>
        <el-table-column prop="categoryName" label="类别名称" width="80">
        </el-table-column>
        <el-table-column prop="description" label="描述">

        </el-table-column>

        <el-table-column prop="content" label="内容" width="100">
          <template v-slot="scope">
            <el-button type="primary" @click="view(scope.row.content)">预览</el-button>
          </template>
        </el-table-column>

        <el-table-column prop="cover" label="封面">
          <template v-slot="scope">
            <el-image v-if="scope.row.cover" :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
            <el-image v-else :src="require('@/assets/img/暂无图片.jpg')"></el-image>
          </template>
        </el-table-column>

        <el-table-column prop="tags" label="标签">
          <template v-slot="scope">
            <el-tag v-for="item in JSON.parse(scope.row.tags ||'[]')" :key="item" style="margin-right: 5px">{{item}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="publicDate" label="发布时间" width="90">

        </el-table-column>
        <el-table-column prop="updateDate" label="更新时间" width="90">

        </el-table-column>
        <el-table-column prop="readCount" label="浏览量" width="100"></el-table-column>
        <el-table-column prop="likes" label="点赞" width="100"></el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template v-slot="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
            <el-popconfirm
                style="margin-left: 5px"
                confirm-button-text='确定'
                cancel-button-text='取消'
                icon="el-icon-info"
                icon-color="red"
                title="您确定删除吗？"
                @confirm="handleDelete(scope.row.id)"
            >
              <el-button type="danger" slot="reference">删除 <i class="el-icon-delete"></i></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <div style="padding: 10px 0">
        <el-pagination
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            :current-page="pageNum"
            :page-sizes="[2 ,5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>

      <el-dialog title="添加文章" :visible.sync="addDialogFormVisible" width="60%">
        <el-form label-width="80px" size="small">
          <el-form-item label="标题" >
            <el-input v-model="form.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="描述" >
            <el-input v-model="form.description" autocomplete="off" type="textarea"></el-input>
          </el-form-item>
          <el-form-item label="内容" >
            <WangEditor
              class="editor"
              ref="wangEditorAdd"
              :content="form.content"
              @changeData="hChangeHtml"
              v-if="addDialogFormVisible"
            >
            </WangEditor>
            <!--<mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"/>-->
          </el-form-item>
          <el-form-item label="封面" >
            <el-upload
                class="avatar-uploader"
                :action="uploadURl"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <el-image :src="form.cover" v-if="form.cover" class="avatar"></el-image>
              <el-button v-else>上传封面</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="类别" >
            <el-select v-model="form.categoryId" placeholder="请选择" filterable>
              <el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="标签" >
            <el-select v-model="tagsArr"
                       multiple
                       filterable
                       allow-create
                       default-first-option>
              <el-option value="前端"></el-option>
              <el-option value="Java"></el-option>
              <el-option value="面试"></el-option>
              <el-option value="Vue"></el-option>
              <el-option value="Element"></el-option>
              <el-option value="Axios"></el-option>
              <el-option value="小白"></el-option>
              <el-option value="Springboot"></el-option>
              <el-option value="算法"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addDialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="add">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="编辑文章" :visible.sync="editDialogFormVisible" width="60%">
        <el-form label-width="80px" size="small">
          <el-form-item label="作者" >
            <el-input v-model="form.userName" autocomplete="off" disabled></el-input>
          </el-form-item>
          <el-form-item label="标题" >
            <el-input v-model="form.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="描述" >
            <el-input v-model="form.description" autocomplete="off" type="textarea"></el-input>
          </el-form-item>
          <el-form-item label="内容" >
            <WangEditor
                class="editor"
                ref="wangEditor"
                :content="form.content"
                @changeHtml="hChangeHtml"
                v-if="editDialogFormVisible"
            >
            </WangEditor>
            <!--<mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"/>-->
          </el-form-item>
          <el-form-item label="封面" >
            <el-upload
                class="avatar-uploader"
                :action="''"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <el-image :src="form.cover" v-if="form.cover" class="avatar"></el-image>
              <el-button v-else>上传封面</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="类别" >
            <el-select v-model="form.categoryId" placeholder="请选择" filterable>
              <el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="标签" >
            <el-select v-model="tagsArr"
                       multiple
                       filterable
                       allow-create
                       default-first-option>
              <el-option value="前端"></el-option>
              <el-option value="Java"></el-option>
              <el-option value="面试"></el-option>
              <el-option value="Vue"></el-option>
              <el-option value="Element"></el-option>
              <el-option value="Axios"></el-option>
              <el-option value="小白"></el-option>
              <el-option value="Springboot"></el-option>
              <el-option value="算法"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="浏览量" >
            <el-input v-model="form.readCount" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="点赞" >
            <el-input v-model="form.likes" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="发布时间" >
            <el-input v-model="form.publicDate" autocomplete="off" disabled></el-input>
          </el-form-item>
          <el-form-item label="更新时间" >
            <el-input v-model="form.updateDate" autocomplete="off" disabled></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="editDialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="文章信息" :visible.sync="viewDialogFormVisible">
        <WangEditor :content="this.content" :editable="false" v-if="viewDialogFormVisible"></WangEditor>
        <!--<el-card>-->
        <!--  <mavon-editor-->
        <!--      class="md"-->
        <!--      :value="this.content"-->
        <!--      :subfield="false"-->
        <!--      :default-open="'preview'"-->
        <!--      :toolbarsFlag="false"-->
        <!--      :editable="false"-->
        <!--      :scroll-style="true"-->
        <!--      :ishljs="true"/>-->
        <!--</el-card>-->
      </el-dialog>
    </el-tab-pane>
    <el-tab-pane label="类别管理" name="second">
      <ArticleCategory></ArticleCategory>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import ArticleCategory from "@/views/manage/ArticleCategory.vue";
import WangEditor from "@/components/WangEditor.vue";
import articleApi from "@/api/articleApi.js";
import categoryApi from "@/api/categoryApi.js";
import fileApi from "@/api/fileApi.js";

export default {
  name: "Article",
  components: {
    ArticleCategory,
    WangEditor,
  },
  data() {
    return {
      uploadURl: fileApi.upload(),
      pageNum: 1,
      pageSize: 10,
      userName: '',
      title: '',
      categoryName: '',
      categoryList: [],
      userList:[],
      total: 0,
      ids:[],
      rules: {},
      activeName: 'first',
      tableData: [],
      addDialogFormVisible: false,
      editDialogFormVisible: false,
      viewDialogFormVisible: false,
      multipleSelection: [],
      form: {},
      tagsArr:[],
      content: '',
      reFresh:true,
    }
  },
  watch:{

  },
  created() {
    this.load()
  },
  methods: {
    load() {
      articleApi.page({
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        userName: this.userName,
        title: this.title,
        categoryName: this.categoryName
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.records
          this.total = res.data.total
        }
      })
      categoryApi.getAll().then(res => {
        if (res.code === '200') {
          this.categoryList = res.data
        }
      })
    },
    reset() {
      this.name = ''
      this.userName= ''
      this.title= ''
      this.categoryName= ''
      this.load()
    },
    handleAdd() {
      this.tagsArr = []
      this.form = {}
      this.addDialogFormVisible = true
    },
    add() {
      this.form.tags = JSON.stringify(this.tagsArr)
      articleApi.create(this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("添加成功");
          this.addDialogFormVisible = false
        } else {
          this.$message.error("添加失败")
        }
        this.load()
      })
    },
    handleClick(tab, event) {
      this.load()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleEdit(row) {
      this.tagsArr = JSON.parse(row.tags || '[]')
      this.editDialogFormVisible = true
      this.form = row
    },
    save() {
      this.form.tags = JSON.stringify(this.tagsArr)
      articleApi.save(this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("添加成功");
          this.editDialogFormVisible = false
        } else {
          this.$message.error("添加失败")
        }
        this.load()
      })
    },
    handleDelete(id) {
      articleApi.deleteById(id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功");
        } else {
          this.$message.error("删除失败")
        }
        this.load()
      })
    },
    handleDeleteBatch() {
      let ids = this.multipleSelection.map(v => v.id)
      articleApi.deleteBatch(ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功");
        } else {
          this.$message.error("批量删除失败")
        }
        this.load()
      })
    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    handleAvatarSuccess(res) {
      this.form.cover = res.data;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isPNG = file.type === 'image/png';
      const isGIF = file.type === 'image/gif';
      const isLt10M = file.size / 1024 / 1024 < 20;

      if (!isJPG && !isPNG && !isGIF) {
        this.$message.error('上传封面图片只能是 JPG 格式!');
      }
      if (!isLt10M) {
        this.$message.error('上传封面图片大小不能超过 10MB!');
      }
      return (isJPG || isPNG || isGIF) && isLt10M;
    },
    // 绑定@imgAdd event
    // imgAdd(pos, $file) {
    //   let $vm = this.$refs.md
    //   // 第一步.将图片上传到服务器.
    //   const formData = new FormData();
    //   formData.append('file', $file);
    //   axios({
    //     url: `http://${serverIp}:9090/file/upload`,
    //     method: 'post',
    //     data: formData,
    //     headers: {'Content-Type': 'multipart/form-data'},
    //   }).then((res) => {
    //     console.log(res)
    //     // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
    //     /**
    //      * $vm 指为mavonEditor实例，可以通过如下两种方式获取
    //      * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，
    //      * `$vm`为`mavonEditor`
    //      * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，
    //      * `$vm`为 `this.$refs.md`
    //      */
    //     $vm.$img2Url(pos, res.data);
    //   })
    // },
    view(content) {
      this.content = content
      this.viewDialogFormVisible = true
    },
    //获取到富文本编辑器的内容
    hChangeHtml(editDataHtml) {
      // console.log(editDataHtml);
      // 获取最新的html数据
      this.form.content = editDataHtml;
      // console.log(this.content);
    },
  }
}
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>