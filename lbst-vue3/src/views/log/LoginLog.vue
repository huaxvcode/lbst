<template>
  <div class="loginLog">
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="time" label="时间"/>
      <el-table-column prop="ip" label="ip"/>
      <el-table-column prop="uid" label="uid"/>
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="content" label="日志内容"/>
    </el-table>
    <el-pagination
        v-model:page-size="pageSize"
        v-model:current-page="pageNum"
        :page-size="20"
        layout="prev, pager, next"
        :page-count="1000"
    />
  </div>
</template>

<script setup>
import logApi from "../../api/logApi.js";
import {ElMessage} from "element-plus";

const tableData = ref([]);
const pageNum = ref(1);
const pageSize = ref(20);

const flushTableData = () => {
  logApi.getLoginLog(pageNum.value, pageSize.value).then(res => {
    if (res == null) {
      ElMessage.error("后端获取日志列表出错");
      return;
    }
    tableData.value = res;
  });
};

watch([pageNum, pageSize], () => {
  flushTableData();
}, {
  immediate: true,
});

</script>

<style scope>
.loginLog {
  @apply w-full min-h-full shadow-lg rounded-lg p-5 bg-light-100;
}

.number:last-child {
  display: none;
}
</style>