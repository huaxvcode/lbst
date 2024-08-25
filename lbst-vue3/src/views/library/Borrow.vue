<template>
  <div class="borrow">
    <el-table :data="tableData"
              border
              style="width: 100%"
              :row-class-name="tableRowClassName"
              table-layout="auto">
      <el-table-column prop="bookName" label="书名"/>
      <el-table-column prop="bookType" label="类型"/>
      <el-table-column prop="time" label="借阅时间"/>
      <el-table-column prop="deadline" label="截至日期"/>
      <el-table-column label="归还日期">
        <template #default="scope">
          <template v-if="scope.row.status != 1">
            {{ scope.row.returnTime }}
          </template>
          <template v-else>
            <el-button type="danger" @click="returnBookClick(scope.row.id)">归还</el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import {getBorrowList, returnBook} from "../../api/BookApi.js";
import {ElMessage} from "element-plus";

const tableData = ref([]);

const flushTable = () => {
  getBorrowList().then(res => {
    if (res == null) {
      return;
    }
    tableData.value = res;
    tableData.value.forEach((item, index) => {
      if (item.time) item.time = item.time.replace("T", " ");
      if (item.deadline) item.deadline = item.deadline.replace("T", " ");
      if (item.returnTime) item.returnTime = item.returnTime.replace("T", " ");
    });
  });
};

const getTableData = () => {
  flushTable();
};

getTableData();

const tableRowClassName = (scope, index) => {
  console.log(scope.row.status);
  if (scope.row.status == 1) {
    return "warning-row";
  }
  if (scope.row.status == 2) {
    return "success-row";
  }
  return "normal-row";
};

const returnBookClick = (id) => {
  returnBook(id).then(res => {
    if (res) {
      ElMessage({
        type: "success",
        message: "归还成功",
      });
      flushTable();
    } else {
      ElMessage.error("归还失败");
    }
  });
};
</script>

<style scope>
.borrow {
  @apply w-full min-h-full shadow-lg rounded-lg p-5 bg-light-100;
}

.el-table .warning-row {
  --el-table-tr-bg-color: var(--bs-danger-bg-subtle);
}

.el-table .success-row {
  //--el-table-tr-bg-color: var(--bs-success-bg-subtle);
}
</style>