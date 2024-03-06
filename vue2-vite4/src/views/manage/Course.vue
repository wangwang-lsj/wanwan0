<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px;margin-right: 10px" placeholder="请输入课程名称" prefix-icon="el-icon-search" v-model="name"></el-input>
      <el-input style="width: 200px;margin-right: 10px" placeholder="请输入老师名称" prefix-icon="el-icon-search" v-model="teacher"></el-input>
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
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="课程名称"></el-table-column>
      <el-table-column prop="score" label="学分"></el-table-column>
      <el-table-column prop="times" label="课时"></el-table-column>

      <el-table-column prop="teacher" label="授课老师"></el-table-column>
      <el-table-column prop="enable" label="开课">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.state" active-color="#13ce66" inactive-color="#ccc" @change="changeState(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="280" align="center">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
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
    <el-dialog title="课程信息" :visible.sync="dialogFormVisible" width="20%">
      <el-form label-width="80px" size="small">
        <el-form-item label="课程名称" >
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学分" >
          <el-input v-model="form.score" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课时" >
          <el-input v-model="form.times" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="老师">
          <el-select clearable v-model="form.teacherId" placeholder="请选择">
            <el-option v-for="item in teachers" :key="item.id" :label="item.nickname" :value="item.id"></el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      teacher: "",
      dialogFormVisible: false,
      form:{},
      multipleSelection:[],
      menuData: [],
      props: {
        label: 'name',
      },
      checks:[],
      expands:[],
      roleId: 0,
      roleFlag: '',
      teachers:[],
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/course/page",{
            params:{
              pageNum:this.pageNum,
              pageSize:this.pageSize,
              name:this.name,
              teacher: this.teacher,
            }
          }
      ).then(res=>{
        if(res.code==='200'){
          this.tableData = res.data.records
          this.total = res.data.total
        }
      })
      this.request.get("/user/role/TEACHER").then(res=>{
        if(res.code==='200'){
          this.teachers = res.data
        }
      })
    },
    reset(){
      this.name=""
      this.teacher=""
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum=pageNum
      this.load()
    },
    handleAdd(){
      this.dialogFormVisible=true
      this.form={}
    },
    handleEdit(row){
      this.form = row
      this.dialogFormVisible = true
      this.load()
    },
    handleDelete(id){
      this.request.delete("/course/"+id).then(res=>{
        if (res.code === '200'){
          this.$message.success("删除成功");
        }else{
          this.$message.error("删除失败")
        }
        this.load()
      })
    },
    handleDeleteBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      this.request.delete("/course/del/batch", {data:ids}).then(res=>{
        if (res.code === '200'){
          this.$message.success("批量删除成功");
        }else{
          this.$message.error("批量删除失败")
        }
        this.load()
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    save(){
      this.request.post("/course",this.form).then(res=>{
        if (res.code === '200'){
          this.$message.success("添加成功");
          this.dialogFormVisible=false
        }else{
          this.$message.error("添加失败")
        }
        this.load()
      })
    },
    changeState(row){
      this.request.post("/course/update",row).then(res=>{
        if(res.code === '200'){
          this.$message.success("操作成功")
        }
      })
    },
  }
}
</script>

<style>
</style>