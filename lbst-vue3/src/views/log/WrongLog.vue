<template>
  <div class="loginLog">
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="time" label="时间"/>
      <el-table-column prop="content" label="日志内容"/>
    </el-table>
  </div>
</template>

<script setup>
const tableData = ref([]);

const flushTableData = () => {
  for (let i = 0; i < 10; i++) {
    tableData.value.push({
      id: 1,
      time: "2024-08-25 00:00:00",
      content: "日志内容日志内容日志内容日志内容日志内容日志内容日志内容日志内容日志内容日志内容日志内容日志内容日志内容日志内容日志内容日志内容",
    });
  }
};

flushTableData();
</script>

<style scope>
.loginLog {
  @apply w-full min-h-full shadow-lg rounded-lg p-5 bg-light-100;
}
</style>