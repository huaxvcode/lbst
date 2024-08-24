<template>
  <div class="book">
    <div class="button">
      <el-button type="danger" @click="deleteSelect">批量删除</el-button>
      <el-button type="primary" @click="onAddItem">新增</el-button>
      <el-input v-model="keyword" placeholder="搜索" class="search-input" />

      <el-select v-model="selectKey" style="width: 8rem; padding-left: 0.6rem">
        <el-option
          v-for="item in selectList"
          :key="item.id"
          :label="item.type"
          :value="item.type"
        />
      </el-select>

      <el-button type="primary" @click="searchClick">搜索</el-button>
    </div>
    <el-table
      ref="multipleTableRef"
      :data="tableData"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" fixed="left" width="55" />
      <el-table-column fixed="left" label="书名">
        <template #default="scope">
          <template v-if="isEdit != scope.$index">
            {{ scope.row.name }}
          </template>
          <template v-else>
            <el-input v-model="inputData.name" />
          </template>
        </template>
      </el-table-column>
      <el-table-column label="类型">
        <template #default="scope">
          <template v-if="isEdit != scope.$index">
            {{ scope.row.typeId }}
          </template>
          <template v-else>
            <el-select v-model="inputData.typeId">
              <el-option
                v-for="item in bookTypeList"
                :key="item.id"
                :label="item.type"
                :value="item.type"
              />
            </el-select>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="上架时间">
        <template #default="scope">
          <template v-if="isEdit != scope.$index">
            {{ scope.row.time }}
          </template>
          <template v-else>
            <el-date-picker
              v-model="inputData.time"
              type="datetime"
              :shortcuts="shortcuts"
            />
          </template>
        </template>
      </el-table-column>
      <el-table-column label="数量">
        <template #default="scope">
          <template v-if="isEdit != scope.$index">
            {{ scope.row.nums }}
          </template>
          <template v-else>
            <el-input v-model="inputData.nums" />
          </template>
        </template>
      </el-table-column>
      <el-table-column label="价格">
        <template #default="scope">
          <template v-if="isEdit != scope.$index">
            {{ scope.row.price }}
          </template>
          <template v-else>
            <el-input v-model="inputData.price" />
          </template>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="150">
        <template #default="scope">
          <el-button
            size="small"
            @click="handleEdit(scope.$index, scope.row)"
            v-if="isEdit != scope.$index"
          >
            Edit
          </el-button>
          <template v-else>
            <el-button
              size="small"
              type="primary"
              @click="handleUpdate(scope.$index, scope.row)"
            >
              确定
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
            >
              取消
            </el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <div class="demo-pagination-block">
      <el-pagination
        v-model:current-page="page"
        v-model:page-size="pageSize"
        :page-sizes="[10, 15, 20]"
        :size="size"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      />
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, watch, watchEffect } from "vue";
import {
  deleteByIdList,
  getBookList,
  getBookType,
  saveOrUpdate,
} from "../../api/BookApi";
import { ElMessage } from "element-plus";
import { dateFormat } from "../../utils/timeFormat";

const multipleTableRef = ref();
const list = ref([]);

const handleSelectionChange = (selectList) => {
  list.value = selectList;
};

let tableData = ref([]);

const onAddItem = () => {
  if (isEdit.value != -1) {
    ElMessage.error("当前还有未保存的信息");
    return;
  }
  tableData.value.unshift({
    id: null,
    name: "",
    typeId: "",
    time: "",
    nums: "",
    price: "",
  });
  isEdit.value = 0;
};

const inputData = reactive({
  name: "",
  typeId: "",
  time: "",
  nums: "",
  price: "",
});

const clearInputData = () => {
  inputData.name = "";
  inputData.typeId = "";
  inputData.time = "";
  inputData.nums = "";
  inputData.price = "";
};

const isEdit = ref(-1);

const handleEdit = (index, row) => {
  isEdit.value = index;

  inputData.name = row.name;
  inputData.typeId = row.typeId;
  inputData.time = row.time;
  inputData.nums = row.nums;
  inputData.price = row.price;
};

const bookTypeList = ref([]);
const selectList = ref([
  {
    id: "-1",
    type: "全部",
  },
]);

getBookType().then((res) => {
  if (res == null || res.code != 200) {
    ElMessage.error("书籍类型获取失败，咨询后端人员！");
  } else {
    bookTypeList.value = res.data;
    selectList.value = [];
    selectList.value.push({
      id: "-1",
      type: "全部",
    });
    res.data.forEach((item, value) => {
      selectList.value.push(item);
    });
  }
});

const getBookTypeId = (type) => {
  let res = 0;
  if (type == "全部") return -1;
  bookTypeList.value.forEach((item) => {
    if (item.type == type) {
      res = item.id;
    }
  });
  return res;
};

const handleUpdate = (index, row) => {
  console.log(row);

  if (!(inputData.nums == "0" || /^[1-9][0-9]*$/.test(inputData.nums))) {
    ElMessage.error("数量异常");
    return;
  }

  if (
    !(
      inputData.nums == "0" ||
      /^[1-9][0-9]*$/.test(inputData.price) ||
      /^[0-9]+\.[0-9]{0,2}$/.test(inputData.price)
    )
  ) {
    ElMessage.error("价格异常");
    return;
  }

  row.name = inputData.name;
  row.typeId = inputData.typeId;
  try {
    row.time = dateFormat(inputData.time);
  } catch (e) {
    console.log(inputData.time);
    row.time = inputData.time;
  }
  row.nums = inputData.nums;
  row.price = inputData.price;

  let uploadData = {
    id: row.id,
    name: row.name,
    typeId: getBookTypeId(row.typeId),
    time: row.time,
    price: row.price,
    nums: row.nums,
  };

  console.log(uploadData);

  saveOrUpdate(uploadData).then((res) => {
    if (res == null) {
      ElMessage.error("后端报错，修改失败！");
    } else if (res.code != 200 || res.data == false) {
      ElMessage.error("修改失败！");
    } else {
      ElMessage({
        message: "修改成功",
        type: "success",
      });
      isEdit.value = -1;
      clearInputData();
      window.location.reload();
    }
  });
};

const handleDelete = (index, row) => {
  isEdit.value = -1;
  console.log(row);
  clearInputData();
  tableData.value.shift();
};

const shortcuts = [
  {
    text: "当前时间",
    value: new Date(),
  },
];

const total = ref(0);
const page = ref(1);
const pageSize = ref(10);
const keyword = ref(null);
const pageList = ref([]);
const selectKey = ref("全部");

watch(
  [page, pageSize],
  (newData, oldData) => {
    getBookList(
      page.value,
      pageSize.value,
      keyword.value,
      getBookTypeId(selectKey.value)
    ).then((res) => {
      if (res == null || res.code != 200) {
        ElMessage.error("信息获取失败");
      } else {
        total.value = res.data.total;
        pageList.value = res.data.list;
        pageList.value.forEach((item) => {
          item.time = item.time.replace("T", " ");
        });
        tableData.value = pageList.value;
      }
    });
  },
  {
    immediate: true,
  }
);

const deleteSelect = () => {
  ElMessageBox.confirm("确定删除吗？", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      let idList = [];
      list.value.forEach((value, index) => {
        idList.push(value.id);
      });
      // console.log(idList);
      deleteByIdList(idList).then((res) => {
        if (res) {
          ElMessage({
            type: "success",
            message: "删除成功",
          });
          window.location.reload();
        } else {
          ElMessage.error("删除失败，请联系后端人员");
        }
      });
    })
    .catch(() => {});
};

const searchClick = () => {
  if (keyword.value == "") {
    keyword.value = null;
  }
  if (keyword.value != null && /^\s+$/.test(keyword.value)) {
    keyword.value = null;
  }
  getBookList(
    page.value,
    pageSize.value,
    keyword.value,
    getBookTypeId(selectKey.value)
  ).then((res) => {
    if (res == null || res.code != 200) {
      ElMessage.error("信息获取失败");
    } else {
      total.value = res.data.total;
      pageList.value = res.data.list;
      pageList.value.forEach((item) => {
        item.time = item.time.replace("T", " ");
      });
      tableData.value = pageList.value;
    }
  });
};

// setInterval(() => {
//   console.log(inputData);
// }, 2000);
</script>

<style scope>
.book {
  @apply bg-light-50 w-full min-h-full shadow-lg rounded-lg p-5;
}

.button {
  @apply flex;
}

.search-input {
  @apply w-80 px-3;
}
</style>
