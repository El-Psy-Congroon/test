<template>
  <div>
    <!--    头部-->
    <Header />
    <!--    主体-->
    <div style="display: flex">
      <!--    侧边栏-->
      <Side />
      <!--    内容区域-->
      <router-view  style="flex: 1"/>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Side from "@/components/Side";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Layout",
  components: {
    Header,
    // eslint-disable-next-line vue/no-unused-components
    Side
  }
}
</script>

<style scoped>

</style>