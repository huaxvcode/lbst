<script setup>
import { ElMessage, ElMessageBox, genFileId } from "element-plus";
import { logout } from "../../api/loginApi.js";
import { ref } from "vue";
import router from "../../router.js";
import { getUserInfo, getHeadImgUrl } from "../../api/personApi.js";
import { getBaseUrl, getHeaders } from "../../utils/base.js";
import { getRoleByUid } from "../../api/userApi.js";

const asideWidth = ref(250);

const logoutClick = () => {
  ElMessageBox.confirm("确定注销吗？", "警告", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
    center: true,
  }).then(async () => {
    ElMessage({
      type: "success",
      message: `注销成功`,
    });
    logout()
      .then(() => {
        console.log("hello world");
        console.log(localStorage);
        localStorage.clear();
      })
      .catch((e) => {
        console.log("error here");
      });
  });
};

let activeNames = ref(["0"]);

// 角色类型：0->超级管理员、1->管理员、2->普通用户
let role = ref(1);

const flushRole = () => {
  let uid = localStorage.getItem("userId");
  getRoleByUid(uid).then((res) => {
    if (res == null || res.code != 200) {
      ElMessage.error("角色权限获取失败");
    } else {
      role.value = res.data;
    }
  });
};

flushRole();

const asideTag = [
  {
    name: "个人中心",
    role: 2,
    icon: "User",
    pathName: "person-center",
  },
  {
    name: "图书中心",
    role: 2,
    icon: "Memo",
    children: [
      {
        name: "所有图书",
        role: 2,
        icon: "Memo",
        pathName: "books",
      },
      {
        name: "借阅记录",
        role: 2,
        icon: "Suitcase",
        pathName: "borrow",
      },
      {
        name: "购买记录",
        role: 2,
        icon: "ShoppingTrolley",
        pathName: "shop",
      },
    ],
  },
  {
    name: "成员",
    role: 1,
    icon: "UserFilled",
    pathName: "member",
  },
  {
    name: "日志",
    role: 1,
    icon: "Odometer",
    children: [
      {
        name: "登录日志",
        role: 1,
        icon: "SwitchButton",
        pathName: "login-log",
      },
      {
        name: "图书日志",
        role: 1,
        icon: "Memo",
        pathName: "libary-log",
      },
      {
        name: "操作日志",
        role: 1,
        icon: "Edit",
        pathName: "operation-log",
      },
      {
        name: "错误日志",
        role: 1,
        icon: "CircleClose",
        pathName: "wrong-log",
      },
    ],
  },
];

const tabDom = ref();

const tabClick = (pathName, event) => {
  if (!pathName) return;
  router.push({ name: pathName });
  const dom = event.currentTarget;
  let focusClass = "item-focus";
  if (tabDom.value) {
    tabDom.value.classList.remove(focusClass);
  }
  dom.classList.add(focusClass);
  tabDom.value = dom;
};

const headImgUrl = ref(null);

const updateHeadImgUrl = async () => {
  const userId = localStorage.getItem("userId");
  await getUserInfo(userId)
    .then((resp) => resp.data)
    .then((data) => {
      if (data.data && data.data.headImg) {
        const headImgName = data.data.headImg;
        getHeadImgUrl(headImgName)
          .then((url) => {
            headImgUrl.value = url;
          })
          .catch((e) => {
            headImgUrl.value = null;
            console.log("获取头像失败！");
          });
      } else {
        headImgUrl.value = null;
      }
    });
};

updateHeadImgUrl();

const getHeadImgUploadUrl = () => {
  return getBaseUrl() + "/upload/head-img";
};

const uploadSuccess = () => {
  updateHeadImgUrl();
  ElMessage({
    message: "头像上传成功",
    type: "success",
  });
};

const uploadError = () => {
  ElMessage.error("头像上传失败");
};

const upload = ref();

const uploadExceed = (files) => {
  upload.value.clearFiles();
  const file = files[0];
  file.uid = genFileId();
  upload.value.handleStart(file);
  upload.value.submit();
};
</script>

<template>
  <div class="home-view">
    <el-container style="height: 100%">
      <el-aside class="aside aside-color" :width="asideWidth + 'px'">
        <el-scrollbar>
          <el-collapse v-model="activeNames" @change="handleChange">
            <template v-for="(item, index) in asideTag" :key="index">
              <el-collapse-item
                :name="index"
                v-if="
                  item.role >= role && item.children && item.children.length > 0
                "
              >
                <template #title>
                  <div class="aside-title-icon">
                    <el-icon>
                      <component :is="item.icon" />
                    </el-icon>
                  </div>
                  {{ item.name }}
                </template>
                <template v-for="(iitem, index) in item.children" :key="index">
                  <div
                    class="item over-mouse-hand"
                    v-if="iitem.role && iitem.role >= role"
                    @click.stop="tabClick(iitem.pathName, $event)"
                  >
                    <div class="item-icon">
                      <el-icon>
                        <component :is="iitem.icon" />
                      </el-icon>
                    </div>
                    <div>{{ iitem.name }}</div>
                  </div>
                </template>
              </el-collapse-item>
              <div
                class="empty-collapse over-mouse-hand"
                v-else-if="item.role >= role"
                @click.stop="tabClick(item.pathName, $event)"
              >
                <div class="aside-title-icon">
                  <el-icon>
                    <component :is="item.icon" />
                  </el-icon>
                </div>
                {{ item.name }}
              </div>
            </template>
          </el-collapse>
        </el-scrollbar>
      </el-aside>
      <el-container>
        <el-header style="height: 48px">
          <div class="header">
            <div
              @click="asideWidth = (asideWidth + 250) % 500"
              class="header-title"
            >
              <el-icon style="font-size: 23px; margin-right: 6px">
                <Menu />
              </el-icon>
              <span style="font-size: 18px">图书管理系统</span>
            </div>
            <!--            <el-avatar-->
            <!--                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"-->
            <!--            />-->
            <el-dropdown class="head-icon">
              <span class="head-icon-img">
                <el-avatar v-if="!headImgUrl">user</el-avatar>
                <el-avatar :src="headImgUrl" v-else />
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-upload
                      ref="upload"
                      class="w-full h-full"
                      :action="getHeadImgUploadUrl()"
                      :headers="getHeaders()"
                      :multiple="false"
                      :show-file-list="false"
                      :limit="1"
                      :on-success="uploadSuccess"
                      :on-error="uploadError"
                      :on-exceed="uploadExceed"
                    >
                      上传头像
                    </el-upload>
                  </el-dropdown-item>
                  <el-dropdown-item @click="logoutClick"
                    >注销登录</el-dropdown-item
                  >
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.home-view {
  width: 100%;
  height: 100%;
}

.header,
.header-title {
  height: 100%;
  display: flex;
  align-items: center;
  position: relative;
}

.head-icon {
  position: absolute;
  right: 0;
  outline: none !important;
  border: none !important;
  box-shadow: none !important;
}

.head-icon .head-icon-img {
  outline: none !important;
  border: none !important;
  box-shadow: none !important;
}

.aside {
  color: white;
  transition: width 0.3s;
  overflow: hidden;
}

.aside * {
  white-space: nowrap;
}

.aside-color {
  @apply bg-sky-900;
}

:deep(.el-collapse) {
  border: 0;
}

:deep(.el-collapse-item__header) {
  @apply bg-sky-900 text-light-900 text-lg px-4 py-7 hover:bg-sky-700;
  border: 0;
}

:deep(.el-collapse-item__wrap) {
  @apply bg-sky-900;
  border: 0;
}

:deep(.el-collapse-item__content) {
  @apply text-light-900 text-base p-0;
}

.el-collapse-item__content .item {
  @apply py-5 text-center hover:bg-sky-700;
}

.aside-title-icon {
  @apply px-2 flex;
}

:deep(.aside-title-icon .el-icon) {
  @apply text-xl;
}

.item {
  @apply flex justify-center;
}

.item .item-icon {
  @apply px-2 flex items-center;
}

:deep(.item .item-icon .el-icon) {
  @apply text-xl;
}

.empty-collapse {
  @apply bg-sky-900 text-light-900 text-lg px-4 py-7 h-0 hover:bg-sky-700 flex items-center;
}

.over-mouse-hand {
  cursor: pointer;
}

.item-focus {
  @apply bg-sky-700;
}

::v-deep.el-main {
  @apply bg-gray-100;
}

:deep(.el-header) {
  @apply shadow-lg;
  z-index: 1;
}
</style>
