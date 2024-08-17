<template>
  <div class="person-div">
    <div class="person-header">
      <el-avatar :src="headImgUrl" />
    </div>
    <div class="person-info">
      <div class="person-card">
        <p>id</p>
        <p>昵称</p>
        <p>出生年份</p>
        <p>学历</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { flushHeadImg } from "../../api/personApi";
const headImgUrl = ref(null);
flushHeadImg().then((res) => (headImgUrl.value = res));
</script>

<style scoped>
.person-div {
  @apply w-full h-full p-1;
  position: relative;
}

.person-header {
  @apply w-50 h-50 absolute;
}

.person-info {
  @apply w-full h-full border-solid border-50 border-transparent;

  background-clip: padding-box; /* 背景只覆盖到内边距区域 */
}

.person-card {
  @apply w-full h-full rounded-2xl shadow-xl bg-light-50;
}

::v-deep .el-avatar {
  @apply w-full h-full;
}
</style>
