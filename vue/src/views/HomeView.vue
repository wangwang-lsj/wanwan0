<template>
  <div class="home" style="height: 100%">
    <el-container style="height: 100%; border: 1px solid #eee">
      <el-aside :width="sideWidth+'px'" style="background-color: rgb(238,241,246); height: 100%; box-shadow: 2px 0 6px rgb(0 21 41)">
        <el-menu :default-openeds="['1', '3']" style="height: 100%;overflow-x: hidden; border-right: solid 0px #e6e6e6"
                 background-color="rgb(48,65,86)"
                 text-color="#fff"
                 active-text-color="ffd04b"
                 class="el-menu-vertical-demo"
                 :collapse="isCollapse"
                 :collapse-transition="false"
        >
          <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" style="width: 21px; vertical-align: middle" >
            <b style="color: white" v-show="!isCollapse">后台管理系统</b>
          </div>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>
              <span slot="title">导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="title">导航三</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container style="height: 100%">
        <el-header style="text-align: right; font-size: 12px; display: flex ; border-bottom:1px solid gray">
          <div style="flex: 1; font-size: 30px; text-align: left; line-height: 60px">
            <span :class="collapseBtnClass" style="cursor: pointer;" @click="collapse"></span>
          </div>

          <el-dropdown style="width: 70px; cursor: pointer;" >
            <span style="margin-left: 5px; position: relative; top: 20px">王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px; position: relative; top: 20px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>

        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>活动管理</el-breadcrumb-item>
            <el-breadcrumb-item>活动列表</el-breadcrumb-item>
            <el-breadcrumb-item>活动详情</el-breadcrumb-item>
          </el-breadcrumb>
          <div style="margin: 10px 0">
            <el-input style="width: 200px;margin-right: 10px" placeholder="请输入用户名" prefix-icon="el-icon-search" v-model="username"></el-input>
            <el-input style="width: 200px;margin-right: 10px" placeholder="请输入昵称" prefix-icon="el-icon-search" v-model="nickname"></el-input>
            <el-input style="width: 200px;margin-right: 10px" placeholder="请输入邮箱" prefix-icon="el-icon-message" v-model="email"></el-input>
            <el-input style="width: 200px;margin-right: 10px" placeholder="请输入手机号" prefix-icon="el-icon-message" v-model="phone"></el-input>
            <el-input style="width: 200px;margin-right: 10px" placeholder="请输入地址" prefix-icon="el-icon-position" v-model="address"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset()">重置</el-button>

          </div>
          <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd" style="margin-right: 5px">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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
            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>
            <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>
          </div>
          <el-table :data="tableData" :header-cell-class-name="headerBg" @selection-change="handleSelectionChange" border stripe>
            <el-table-column
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column prop="id" label="ID" width="60">
            </el-table-column>
            <el-table-column prop="username" label="用户名" width="140">
            </el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120">
            </el-table-column>
            <el-table-column prop="email" label="邮箱">
            </el-table-column>
            <el-table-column prop="phone" label="电话">
            </el-table-column>
            <el-table-column prop="address" label="地址">
            </el-table-column>
            <el-table-column label="操作" width="200" align="center">
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
          <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="20%">
            <el-form label-width="80px" size="small">
              <el-form-item label="用户名" >
                <el-input v-model="form.username" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="昵称" >
                <el-input v-model="form.nickname" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="邮箱" >
                <el-input v-model="form.email" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="电话" >
                <el-input v-model="form.phone" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="地址" >
                <el-input v-model="form.address" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
            </div>
          </el-dialog>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<style>
</style>

<script>
import request from "@/utils/request";
export default {
  data() {
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    };
    return {
      tableData: [],
      msg: "hello",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse:false,
      sideWidth: 200,
      headerBg: 'headerBg',
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      nickname: "",
      email: "",
      phone: "",
      address: "",
      dialogFormVisible: false,
      form:{},
      multipleSelection:[],

    }
  },
  created() {
    // fetch("http://localhost:8080/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize).then(res=>res.json()).then(res=>{
    //   this.tableData = res.data;
    //   this.total = res.total;
    // })
    this.load()
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
    load(){
      request.get("/user/page",{
          params:{
            pageNum:this.pageNum,
            pageSize:this.pageSize,
            username:this.username,
            nickname:this.nickname,
            email:this.email,
            phone:this.phone,
            address:this.address
          }
        }
      ).then(res=>{
        this.tableData = res.records
        this.total = res.total
      })
      // fetch("http://localhost:8080/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize).then(res=>res.json()).then(res=>{
      //   console.log(res)
      //   this.tableData = res.data;
      //   this.total = res.total;
      // })
    },
    reset(){
      this.username=""
      this.nickname=""
      this.email=""
      this.phone=""
      this.address=""
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
      request.delete("http://localhost:8080/user/"+id).then(res=>{
        if (res){
          this.$message.success("删除成功");
        }else{
          this.$message.error("删除失败")
        }
        this.load()
      })
    },
    handleDeleteBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      request.delete("http://localhost:8080/user/del/batch", {data:ids}).then(res=>{
        if (res){
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

    // handleMultiDelete(){
    //   for (let i = 0; i < this.multipleSelection.length;i++){
    //     this.handleDelete(this.multipleSelection[i].id)
    //   }
    //   this.load()
    // },
    save(){
      request.post("/user",this.form).then(res=>{
        if (res){
          this.$message.success("添加成功");
          this.dialogFormVisible=false
        }else{
          this.$message.error("添加失败")
        }
        this.load()
      })
    }

  }
};
</script>

<style>
  .headerBg{
    /* !important增加权重*/
    background-color: #eee !important;
  }
</style>