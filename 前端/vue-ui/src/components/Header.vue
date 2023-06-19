<template>
  <div style="height: 50px;line-height: 50px;border-bottom: 1px solid #ccc;display: flex">
    <div style="width: 200px;padding-left: 30px;font-weight: bold;color: dodgerblue">迷宫小游戏</div>
    <div style="flex: 1"></div>
    <div>
      <el-button type="text" @click="get">金币:{{user2.gold}}</el-button>
      <el-button type="text" @click="get">体力:{{user2.coin}}</el-button>
    </div>
    <div style="padding-top: 10px">
      <el-avatar shape="square" :size="30" :fit="'fill'" :src="'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'" />
    </div>
    <div style="width: 120px">
         <el-dropdown style="padding-top: 20px">

    <span class="el-dropdown-link">

    {{ user.name }}

      <el-icon class="el-icon--right">
<!--
        <arrow-down />
-->
      </el-icon>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
<!--            <el-dropdown-item>个人信息</el-dropdown-item>-->
            <el-dropdown-item @click="logout">退出</el-dropdown-item>
            <el-dropdown-item  @click="get">金币：{{user2.gold}}</el-dropdown-item>
            <el-dropdown-item  @click="get">体力：{{user2.coin}}</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
     </div>
  </div>
</template>

<script >
// eslint-disable-next-line vue/no-export-in-script-setup
import request from "@/utils/request";

export default {

  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",

  data() {
    return {
      user: sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {},
      user2:{},
    }},
  created() {
    this.get()
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
.demo-tabs > {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}


</style>