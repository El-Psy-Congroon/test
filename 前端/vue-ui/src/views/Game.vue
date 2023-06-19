<template>
  <div>
    <Header />
  </div>

  <el-image style="width: 90%; height:80%" :src="url" />

  <div class="a">
  <el-descriptions title="" :column="3" border>
    <el-descriptions-item
        label="房间名"
        label-align="right"
        align="center"
        label-class-name="my-label"
        class-name="my-content"
        width="150px"
    >{{location}}</el-descriptions-item
    >
    <el-descriptions-item label="gold" label-align="right" align="center"
    >{{gold}}</el-descriptions-item
    >
    <el-descriptions-item label="cost" label-align="right" align="center"
    >{{cost}}</el-descriptions-item
    >
    <el-descriptions-item label="Remarks" label-align="right" align="center">
      <el-tag size="small">Room</el-tag>
    </el-descriptions-item>
    <el-descriptions-item label="房间描述信息" label-align="right" align="center"
    >{{descriptions}}</el-descriptions-item
    >
  </el-descriptions>
  </div>
  <div class="b">
      <el-row class="mb-4">
        <el-button type="success" plain @click="west">WEST </el-button>
        <el-button type="success" plain @click="east">EAST </el-button>
        <el-button type="success" plain @click="north">NORTH</el-button>
        <el-button type="success" plain @click="south">SOUTH</el-button>
        <el-button type="success" plain @click="back">BACK</el-button>
        <el-button type="warning" plain @click="take">TAKE</el-button>
        <el-button type="primary" plain @click="open">rule</el-button>
<!--        <el-button type="primary" plain @click="open">SAVE</el-button>-->
      </el-row>
  </div>
  <div>
    <el-dialog
        v-model="dialogVisible3"
        title="巨洞出口"
        width="30%"
        :before-close="handleClose"
    >
      <span>恭喜你，获得了离开洞穴的钥匙，并离开了洞穴</span>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="gameWin()">
          确定
        </el-button>
      </span>
      </template>
    </el-dialog>
      <el-dialog
          v-model="dialogVisible4"
          title="game over"
          width="30%"
          :before-close="handleClose"
      >
        <span>你体力不支，倒在了洞穴里</span>
        <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="gameOver()">
          确定
        </el-button>
      </span>
        </template>
      </el-dialog>
  </div>

<!--  <el-empty-->
<!--      image="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"-->
<!--  >-->
<!--  </el-empty>-->
<!--  <div class="demo-image">-->
<!--      <el-image style="width: 100px; height: 100px" :src="url" />-->
<!--  </div>-->
</template>

<script>
import request from "@/utils/request";
import Header from "@/components/Header.vue";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "product",
  components: {Header},
  data(){
    return{
      url:'',
      form:{},
      dialogVisible:false,
      dialogVisible2:false,
      dialogVisible3:false,
      dialogVisible4:false,
      currentPage:1,
      pageSize:10,
      total:10,
      search:'',
      tableData:[],
      rooms:[{'name':'outside','id':1,'gold':'1','cost':'-1','descriptions':'outside the main entrance of the colossal cave','picture':'assets/images/outside.jpg'},
        {'name':'theater','id':2,'gold':'2','cost':'-1','descriptions':'in a lecture theater','picture':'assets/images/theater.jpg'},
        {'name':'pub','id':3,'gold':'4','cost':'-3','descriptions':'in the pub','picture':'assets/images/pub.jpg'},
        {'name':'lab','id':4,'gold':'3','cost':'-2','descriptions':'in a computing lab','picture':'assets/images/lab.jpg'},
        {'name':'treasure','id':5,'gold':'5','cost':'-1','descriptions':'in the treasure room, maybe there has some treasure','picture':'assets/images/treasure.jpg'},
        {'name':'randomRoom','id':6,'gold':'0','cost':'0','descriptions':'in a randomRoom','picture':'assets/images/randomroom.jpg'},
        {'name':'exit','id':7,'gold':'0','cost':'0','descriptions':'in the exit','picture':'assets/images/exit.jpg'}],
      items:[{'name':'cookie','id':1,'roomId':5,'descriptions':'增加5体力'},
        {'name':'key','id':2,'roomId':7,'descriptions':'通关的关键物品'}],
      location:'',
      gold:'',
      cost:'',
      descriptions:'',
      id:'',
      lastroom:'',
      takeFlag:false,
      powerFlag:false,
      maps:[[0,2,3], [4,6,5], [7,1,0]]
    }
  },
  created() {
    this.load()
  },
  methods:{
    open(){
      this.$notify({
        title: '游戏规则说明',
        message: '巨洞探险：一个精彩的、充满想像力的复杂游戏，包括要在一个复杂的洞穴系统中找到出路、寻找隐藏的财宝、使用暗语和其他一些神秘工具，最终的目的是获得高分。',
        duration: 4500
      });
    },
    getRandomInt1(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
     },
    load(){
      request.get("/user"
      ).then(res=>{
        this.tableData=res.data.records
        this.total=res.data.total
        let user=JSON.parse(sessionStorage.getItem("user"))
        for(let data in res.data.records){
          if(res.data.records[data].id===user["id"]){
            this.location=res.data.records[data].currentroom
            this.lastroom=res.data.records[data].lastroom
          }
        }
        for(let index in this.rooms){
            let map=this.rooms[index]
            if(map['name']===this.location){
              this.gold=map['gold']
              this.cost=map['cost']
              this.descriptions="Now,you are "+map['descriptions']
              this.id=map['id']
              this.url=require("@/"+this.rooms[this.id-1]['picture'])
          }
        }
        if(this.location==="randomRoom"){
          alert("您处于随机房间，将会被传送离开")
          let a=this.getRandomInt1(1,5)
          // alert(a)
          if(a===1){
            this.west()
          }else if(a===2){
            this.east()
          }else if(a===3){
            this.north()
          }else if(a===4){
            this.south()
          }
        }
      })
    },
    take(){
      if(this.takeFlag)
        alert("您已经拿过了")
      else{
        this.takeFlag=true
        // this.load()
        let oldgold
        let oldcoin
        request.get("/user"
        ).then(res=>{
          let user=JSON.parse(sessionStorage.getItem("user"))
          for(let data in res.data.records){
            if(res.data.records[data].id===user["id"]){
              oldgold=res.data.records[data].gold
              oldcoin=res.data.records[data].coin
            }
          }
          // oldgold=res.data.records[0].gold
          // oldcoin=res.data.records[0].coin
          let gold=(parseInt(oldgold)+parseInt(this.gold)).toString()
          let coin=(parseInt(oldcoin)+parseInt(this.cost)).toString()

          let mesage="gold+"+this.gold+"  power"+this.cost
          for(let index in this.items){
            if(this.items[index].roomId===parseInt(this.id)){
              if(parseInt(coin)<0){
                this.$message({
                  type:"error",
                  message:"您的体力不足"
                })
                return
              }else{
                coin=(parseInt(coin)+5).toString()
                mesage=mesage+" you get a"+this.items[index].name+"your power +5"
              }
            }
          }
          let data={"id":user["id"],"name":user["name"],"password":user["password"],"gender":user["gender"],"currentroom":this.location,"gold":gold,"coin":coin}
          request.put("/user",data).then(res => {
            console.log(res)
            if(res.code==="0"){
              this.$message({
                type:"success",
                message:mesage
              })
            }else {
              this.$message({
                type:"error",
                message:res.msg
              })
            }
            if(this.location==="exit"){
              this.dialogVisible3=true

            }
            this.load()
          })
        })

      }
    },
    west(){
      this.takeFlag = false
      let id=this.id
      let x;
      let y;
      for(let i=0;i<3;i++){
        for(let j=0;j<3;j++){
          if(this.maps[i][j]===id){
            x=i;
            y=j;
          }
        }
      }
      for(let index in this.rooms) {
        if(x>=0&&y-1>=0&&x<=2&&y-1<=2&&this.maps[x][y-1]!==0){
          let map = this.rooms[index]
          if (map['id'] === this.maps[x][y]) {
            this.lastroom = map['name']
          }
        }
      }

      y=y-1
      if(x>=0&&y>=0&&x<=2&&y<=2&&this.maps[x][y]!==0){
        for(let index in this.rooms){
          let map=this.rooms[index]
          if(map['id']===this.maps[x][y]){
            this.location=map['name']
            let oldgold
            let oldcoin
            request.get("/user"
            ).then(res=>{
              let user=JSON.parse(sessionStorage.getItem("user"))
              for(let data in res.data.records){
                if(res.data.records[data].id===user["id"]){
                  oldgold=res.data.records[data].gold
                  oldcoin=res.data.records[data].coin
                }
              }
              oldcoin=(parseInt(oldcoin)-1).toString()
              if(parseInt(oldcoin)<=0){
                this.dialogVisible4=true
              }else{

                let data={"id":user["id"],"name":user["name"],"password":user["password"],"gender":user["gender"],"currentroom":this.location,"gold":oldgold,"coin":oldcoin,"lastroom":this.lastroom}
                request.put("/user",data).then(res => {
                  if(res.code==="0"){
                    this.$message({
                      type:"success",
                      message:"成功"
                    })
                  }else {
                    this.$message({
                      type:"error",
                      message:res.msg
                    })
                  }
                  this.load()
                })
              }
            })
          }
        }
        // this.load()
      }else {
        alert("此路不通")
      }


    },
    east(){
      this.takeFlag = false

      let id=this.id
      let x;
      let y;
      for(let i=0;i<3;i++){
        for(let j=0;j<3;j++){
          if(this.maps[i][j]===id){
            x=i;
            y=j;
          }
        }
      }
      for(let index in this.rooms) {
        if(x>=0&&y+1>=0&&x<=2&&y+1<=2&&this.maps[x][y+1]!==0){
          let map = this.rooms[index]
          if (map['id'] === this.maps[x][y]) {
            this.lastroom = map['name']
          }
        }
      }
      y=y+1
      if(x>=0&&y>=0&&x<=2&&y<=2&&this.maps[x][y]!==0){
        for(let index in this.rooms){
          let map=this.rooms[index]
          if(map['id']===this.maps[x][y]){
            // this.gold=map['gold']
            // this.coin=map['coin']
            // this.descriptions=map['descriptions']
            this.location=map['name']
            // this.id=map['id']
            let oldgold
            let oldcoin
            request.get("/user"
            ).then(res=>{
              let user=JSON.parse(sessionStorage.getItem("user"))
              for(let data in res.data.records){
                if(res.data.records[data].id===user["id"]){
                  oldgold=res.data.records[data].gold
                  oldcoin=res.data.records[data].coin
                }
              }
              oldcoin=(parseInt(oldcoin)-1).toString()
              if(parseInt(oldcoin)<=0){
                this.dialogVisible4=true
              }else{
                let data={"id":user["id"],"name":user["name"],"password":user["password"],"gender":user["gender"],"currentroom":this.location,"gold":oldgold,"coin":oldcoin,"lastroom":this.lastroom}
                request.put("/user",data).then(res => {
                  if(res.code==="0"){
                    this.$message({
                      type:"success",
                      message:"成功"
                    })
                  }else {
                    this.$message({
                      type:"error",
                      message:res.msg
                    })
                  }
                  this.load()
                })
              }
            })
          }
        }
        // this.load()
      }else {
        alert("此路不通")
      }

    },
    north(){
      this.takeFlag = false

      let id=this.id
      let x;
      let y;
      for(let i=0;i<3;i++){
        for(let j=0;j<3;j++){
          if(this.maps[i][j]===id){
            x=i;
            y=j;
          }
        }
      }
      for(let index in this.rooms) {
        if(x-1>=0&&y>=0&&x-1<=2&&y<=2&&this.maps[x-1][y]!==0){
          let map = this.rooms[index]
          if (map['id'] === this.maps[x][y]) {
            this.lastroom = map['name']
          }
        }
      }
      x=x-1
      if(x>=0&&y>=0&&x<=2&&y<=2&&this.maps[x][y]!==0){
        for(let index in this.rooms){
          let map=this.rooms[index]
          if(map['id']===this.maps[x][y]){
            // this.gold=map['gold']
            // this.coin=map['coin']
            // this.descriptions=map['descriptions']
            this.location=map['name']
            // this.id=map['id']
            let oldgold
            let oldcoin
            request.get("/user"
            ).then(res=>{
              let user=JSON.parse(sessionStorage.getItem("user"))
              for(let data in res.data.records){
                if(res.data.records[data].id===user["id"]){
                  oldgold=res.data.records[data].gold
                  oldcoin=res.data.records[data].coin
                }
              }
              oldcoin=(parseInt(oldcoin)-1).toString()
              if(parseInt(oldcoin)<=0){
                this.dialogVisible4=true
              }else{
                let data={"id":user["id"],"name":user["name"],"password":user["password"],"gender":user["gender"],"currentroom":this.location,"gold":oldgold,"coin":oldcoin,"lastroom":this.lastroom}
                request.put("/user",data).then(res => {
                  if(res.code==="0"){
                    this.$message({
                      type:"success",
                      message:"成功"
                    })
                  }else {
                    this.$message({
                      type:"error",
                      message:res.msg
                    })
                  }
                  this.load()
                })
              }

            })
          }
        }
        // this.load()
      }else {
        alert("此路不通")
      }

    },
    south(){
      this.takeFlag = false

      let id=this.id
      let x;
      let y;
      for(let i=0;i<3;i++){
        for(let j=0;j<3;j++){
          if(this.maps[i][j]===id){
            x=i;
            y=j;
          }
        }
      }
      for(let index in this.rooms) {
        if(x+1>=0&&y>=0&&x+1<=2&&y<=2&&this.maps[x+1][y]!==0){
          let map = this.rooms[index]
          if (map['id'] === this.maps[x][y]) {
            this.lastroom = map['name']
          }
        }
      }
      x=x+1
      if(x>=0&&y>=0&&x<=2&&y<=2&&this.maps[x][y]!==0){
        for(let index in this.rooms){
          let map=this.rooms[index]
          if(map['id']===this.maps[x][y]){
            // this.gold=map['gold']
            // this.coin=map['coin']
            // this.descriptions=map['descriptions']
            this.location=map['name']
            // this.id=map['id']
            let oldgold
            let oldcoin
            request.get("/user"
            ).then(res=>{
              let user=JSON.parse(sessionStorage.getItem("user"))
              for(let data in res.data.records){
                if(res.data.records[data].id===user["id"]){
                  oldgold=res.data.records[data].gold
                  oldcoin=res.data.records[data].coin
                }
              }
              oldcoin=(parseInt(oldcoin)-1).toString()
              if(parseInt(oldcoin)<=0){
                this.dialogVisible4=true
              }else{
                let data={"id":user["id"],"name":user["name"],"password":user["password"],"gender":user["gender"],"currentroom":this.location,"gold":oldgold,"coin":oldcoin,"lastroom":this.lastroom}
                request.put("/user",data).then(res => {
                  if(res.code==="0"){
                    this.$message({
                      type:"success",
                      message:"成功"
                    })
                  }else {
                    this.$message({
                      type:"error",
                      message:res.msg
                    })
                  }
                  this.load()
                })
              }

            })
          }
        }
        // this.load()
      }else {
        alert("此路不通")
      }

    },
    back(){
      let lastroom = this.lastroom
      this.lastroom=this.location
      this.location=lastroom
      let oldgold
      let oldcoin
      request.get("/user"
      ).then(res=>{
        let user=JSON.parse(sessionStorage.getItem("user"))
        for(let data in res.data.records){
          if(res.data.records[data].id===user["id"]){
            oldgold=res.data.records[data].gold
            oldcoin=res.data.records[data].coin
          }
        }
        let data={"id":user["id"],"name":user["name"],"password":user["password"],"gender":user["gender"],"currentroom":this.location,"gold":oldgold,"coin":oldcoin,"lastroom":this.lastroom}
        request.put("/user",data).then(res => {
          if(res.code==="0"){
            this.$message({
              type:"success",
              message:"成功"
            })
          }else {
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()
        })
      })
    },
    add(){
      this.dialogVisible=true
      this.form={}
    },
    save(){
      if(this.form.id){
        request.put("/user",this.form).then(res => {
          console.log(res)
          if(res.code==="0"){
            this.$message({
              type:"success",
              message:"更新成功"
            })
          }else {
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()
          this.dialogVisible=false
        })
      }
    },
    // eslint-disable-next-line no-unused-vars
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleEdit2(){
      this.dialogVisible2 = true
    },
    handleDelete(id){
      request.delete("/product/"+id).then(res=>{
        if(res.code==="0"){
          this.$message({
            type:"success",
            message:"删除成功"
          })
        }else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()
      })
    },
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      this.load()
    },
    gameWin(){
      this.dialogVisible3 = false
      this.reset()
    },
    gameOver(){
      this.dialogVisible4 = false
      this.reset()
    },
    reset(){
      this.takeFlag=false
      request.get("/user"
      ).then(res=>{
        let user=JSON.parse(sessionStorage.getItem("user"))
        let oldgold
        let oldcoin
        for(let data in res.data.records){
          if(res.data.records[data].id===user["id"]){
            oldgold=res.data.records[data].gold
            oldcoin=res.data.records[data].coin
          }
        }
        oldgold = "0"
        oldcoin = "20"
        let data={"id":user["id"],"name":user["name"],"password":user["password"],"gender":user["gender"],"currentroom":"outside","gold":oldgold,"coin":oldcoin}
        request.put("/user",data).then(res => {
          console.log(res)
          if(res.code==="0"){
            this.$message({
              type:"success",
              message:res.msg
            })
          }else {
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()
        })
      })
    }
  }
}
</script>


<style scoped>
.a{
  margin: auto;
  position: absolute;
  top: 50px;
  left: 0;
  right: 0;
  bottom: 0;
}
.b{
  margin: auto;
  position: absolute;
  top: 160px;
  left: 400px;
  right: 0;
  bottom: 0;
}
</style>