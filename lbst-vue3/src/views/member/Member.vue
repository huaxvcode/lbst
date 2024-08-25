<template>
  <div class="member">
    <el-table :data="tableData"
              style="width: 100%"
              table-layout="fixed"
              :row-class-name="tableRowClassName">
      <el-table-column prop="headImg" label="头像">
        <template #default="scope">
          <el-avatar :size="50" :icon="UserFilled" v-if="!scope.row.headImg"/>
          <el-avatar :size="50" :src="scope.row.headImg" v-else/>
        </template>
      </el-table-column>
      <el-table-column prop="uid" label="uid"/>
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="nickName" label="昵称"/>
      <el-table-column prop="yearOfBirth" label="出生年份"/>
      <el-table-column prop="education" label="学历"/>
      <el-table-column fixed="right" prop="roleType" label="用户类型">
        <template #default="scope">
          <template v-if="editLine != scope.$index">
            {{ roleTypeList[scope.row.roleType] }}
          </template>
          <template v-else>
            <el-select
                v-model="roleSelect"
                placeholder="选择用户类型"
            >
              <el-option
                  v-for="(item, index) in roleTypeList"
                  :key="index"
                  :label="item"
                  :value="item"
              />
            </el-select>
          </template>
        </template>
      </el-table-column>
      <template v-if="role == 0">
        <el-table-column fixed="right" label="编辑" width="200">
          <template #default="scope">
            <template v-if="editLine != scope.$index">
              <el-button type="primary" @click="editLine = scope.$index">编辑</el-button>
            </template>
            <template v-else>
              <el-button type="success" @click="updateRoleType(scope.row.uid)">保存</el-button>
              <el-button type="danger" @click="editLine = -1">取消</el-button>
            </template>
          </template>
        </el-table-column>
      </template>
    </el-table>
  </div>
</template>

<script setup>
import {UserFilled} from "@element-plus/icons-vue";
import {getRoleByUid, getUserList, setRoleType} from "../../api/userApi.js";
import {ElMessage} from "element-plus";
import {getHeadImgUrl} from "../../api/personApi.js";

const tableData = ref([]);

const educationList = [
  "小学", "初中", "高中", "大学", "硕士", "博士", "博士后",
];
const roleTypeList = [
  "超级管理员", "管理员", "普通用户",
];

const role = ref(2);

const getRole = () => {
  const userId = localStorage.getItem("userId");
  getRoleByUid(userId).then((res) => {
    if (res && res.code == 200) {
      role.value = res.data;
    }
  });
};

getRole();

const getTableData = () => {
  getUserList().then(res => {
    if (res == null) {
      return;
    } else {
      tableData.value = res;
      tableData.value.forEach((item, index) => {
        item.education = educationList[item.education];
        if (!(item.roleType == 0 || item.roleType == 1 || item.roleType == 2)) {
          item.roleType = 2;
        }
        if (item.headImg) {
          getHeadImgUrl(item.headImg).then((res) => {
            item.headImg = res;
          });
        }
      });
    }
  });
};

getTableData();


const roleSelect = ref();

const getRoleId = (type) => {
  let id = 2;
  roleTypeList.forEach((item, index) => {
    if (item == type) {
      id = index;
      return;
    }
  });
  return id;
};

const editLine = ref(-1);

const updateRoleType = (uid) => {
  setRoleType(uid, getRoleId(roleSelect.value)).then((res) => {
    if (res) {
      ElMessage({
        type: "success",
        message: "更改成功",
      });
      editLine.value = -1;
      getTableData();
    } else {
      ElMessage.error("更改失败");
    }
  });
};

const tableRowClassName = (scope, index) => {
  if (scope.row.roleType == "0") {
    return "warning-row";
  } else if (scope.row.roleType == "1") {
    return "success-row";
  }
};

</script>
<style scope>
.member {
  @apply w-full min-h-full bg-light-100 shadow-lg rounded-lg p-5;
}

.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>