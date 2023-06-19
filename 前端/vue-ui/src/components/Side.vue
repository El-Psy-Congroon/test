<template>
    <div>
      <el-row class="tac">
        <el-col :span="12">
          <el-menu
              style="width: 200px;min-height: calc(100vh - 50px)"
              default-active="product"
              class="el-menu-vertical-demo"
              @open="handleOpen"
              @close="handleClose"
              router
          >
            <el-sub-menu index="1">
              <template #title>基础管理</template>
                <el-menu-item index="user" :disabled=level>用户管理</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="2">
              <template #title>功能管理</template>
              <el-menu-item index="product">迷宫小游戏</el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-col>
      </el-row>
    </div>
</template>

<script>
import request from "@/utils/request";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Side",
  data() {
  return {
   level:true
  }},
  created() {
    request.get("/user"
    ).then(res=>{
      let user=JSON.parse(sessionStorage.getItem("user"))
      for(let data in res.data.records){
        if(res.data.records[data].id===user["id"]){
          if(res.data.records[data].level===1){
            this.level=false
          }
        }
      }
    })
  },
  methods:{
  get(){
    request.get("/user"
    ).then(res=>{
      let user=JSON.parse(sessionStorage.getItem("user"))
      for(let data in res.data.records){
        if(res.data.records[data].id===user["id"]){
          this.user2=res.data.records[data]
        }
      }
    })
  },

  logout() {
    this.$router.push("/login")
    sessionStorage.removeItem("user")
    this.$message.success("退出成功")
  }
},
}
</script>

<style scoped>

</style>