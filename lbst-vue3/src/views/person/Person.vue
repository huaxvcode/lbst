<template>
  <div class="person">
    <div class="person-content">
      <el-button
        class="edit-button"
        :icon="Edit"
        circle
        v-show="!canModify"
        @click="canModify = true"
      />
      <div class="edit-choose" v-show="canModify">
        <el-button
          type="primary"
          class="edit-button-ok"
          :icon="Check"
          circle
          @click="editOk"
        />
        <el-button
          type="danger"
          class="edit-button-cancel"
          :icon="Close"
          circle
          @click="cancel"
        />
      </div>
      <div class="upload-head">
        <div class="person-head">
          <el-avatar
            :src="form.headImgUrl"
            v-if="form.headImgUrl"
            fit="cover"
          />
          <el-avatar :icon="UserFilled" v-else :size="200" />
        </div>
        <el-upload
          class="avatar-uploader"
          :action="getHeadImgUploadUrl()"
          :headers="getHeaders()"
          :show-file-list="false"
          :on-success="uploadSuccess"
          :on-error="uploadError"
          :on-exceed="uploadExceed"
          @mouseenter="uploadHover = true"
          @mouseleave="uploadHover = false"
        >
          <el-icon class="upload-icon" v-show="uploadHover">
            <Plus />
          </el-icon>
        </el-upload>
      </div>
      <h1 class="person-username">{{ data.username }}</h1>
      <div class="space"></div>

      <el-form :model="form" label-width="auto" class="person-form">
        <el-form-item label="昵称" label-position="left">
          <el-input
            v-model="form.nickName"
            style="width: 17rem"
            :placeholder="
              data.nickName && data.nickName != 'unnamed'
                ? data.nickName
                : '未录入'
            "
            :disabled="!canModify"
          />
        </el-form-item>
        <el-form-item label="出生年份" label-position="left">
          <el-input
            v-model="form.birthYear"
            style="width: 17rem"
            :placeholder="data.yearOfBirth ? data.yearOfBirth : '未录入'"
            :disabled="!canModify"
          />
        </el-form-item>
        <el-form-item label="学历" label-position="left">
          <el-select
            v-model="form.education"
            :placeholder="data.education ? edList[data.education] : Select"
            style="width: 17rem"
            :disabled="!canModify"
          >
            <el-option
              v-for="(item, index) in edList"
              :key="index"
              :label="item"
              :value="index"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <el-text class="person-uid">uid: {{ data.uid }}</el-text>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { Edit, Check, Close } from "@element-plus/icons-vue";
import {
  flushHeadImg,
  getHeadImgUploadUrl,
  getUserInfo,
  getUsername,
  saveOrUpdateUserInfo,
} from "../../api/personApi";
import { UserFilled } from "@element-plus/icons-vue";
import { getHeaders } from "../../utils/base";
import { ElMessage } from "element-plus";

const form = reactive({
  headImgUrl: "",
  uid: "",
  nickName: "",
  birthYear: "",
  education: "",
});

const clearForm = () => {
  form.nickName = "";
  form.birthYear = "";
  form.education = "";
};

const data = reactive({
  username: "",
  uid: "",
  nickName: "",
  yearOfBirth: "",
  education: "",
});

const edList = ["小学", "初中", "高中", "大学", "硕士", "博士", "博士后"];

const initForm = () => {
  flushHeadImg().then((res) => (form.headImgUrl = res));
  data.uid = localStorage.getItem("userId");

  getUserInfo(data.uid)
    .then((resp) => resp.data)
    .then((res) => {
      let rdata = res.data;
      data.nickName = rdata.nickName;
      data.yearOfBirth = rdata.yearOfBirth;
      data.education = rdata.education;
    });

  getUsername(data.uid)
    .then((resp) => resp.data)
    .then((res) => res.data)
    .then((res) => {
      data.username = res;
    });
};

initForm();

const canModify = ref(false);

const modifyData = () => {
  data.nickName = form.nickName;
  data.yearOfBirth = form.birthYear;
  data.education = form.education;
};

const uploadHover = ref(false);

const uploadSuccess = () => {
  window.location.reload();
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

const cancel = () => {
  form.nickName = "";
  form.birthYear = "";
  form.education = "";
  canModify.value = false;
};

const editOk = () => {
  if (form.nickName) {
    if (form.nickName.length > 30) {
      ElMessage({
        message: "昵称长度不能超过 30 个字符",
        type: "warning",
      });
      return;
    }
  }
  if (form.birthYear) {
    if (
      !(
        /^\d+$/.test(form.birthYear) &&
        Number.parseInt(form.birthYear) >= 1800 &&
        Number.parseInt(form.birthYear) <= new Date().getFullYear()
      )
    ) {
      ElMessage({
        message: "请输入正确的出生年份",
        type: "warning",
      });
      return;
    }
  }
  if (form.nickName) data.nickName = form.nickName;
  if (form.birthYear) data.yearOfBirth = form.birthYear;
  if (form.education) data.education = form.education;

  saveOrUpdateUserInfo(data.uid, data)
    .then((resp) => resp.data)
    .then((res) => {
      if (res.code != 200) {
        ElMessage({
          message: "信息保存失败",
          type: "error",
        });
      } else {
        ElMessage({
          message: "保存成功",
          type: "success",
        });
        cancel();
      }
    })
    .catch((e) => {
      console.log(e);
      ElMessage({
        message: "信息保存失败",
        type: "error",
      });
    });
};

// setInterval(() => {
//   console.log(uploadHover.value);
// }, 2000);
</script>

<style scoped>
.person {
  @apply w-full min-h-full p-20 shadow-lg rounded-lg flex justify-center relative items-center;
}

.person-content {
  @apply flex relative justify-center flex-col items-center w-140 h-180 bg-gray-50 shadow-lg rounded-2xl;
}

.upload-head * {
  @apply rounded-full;
}

.upload-head {
  @apply relative;
}

.person-head {
  @apply w-50 h-50;
}

.person-head .el-avatar {
  @apply w-full h-full;
}

.upload-icon {
  @apply bg-dark-200 bg-opacity-40;
}

.upload-icon,
.avatar-uploader {
  @apply w-full h-full absolute top-0 left-0 rounded-full;
  z-index: 1;
}

::v-deep .upload-icon.el-icon svg {
  @apply w-10 h-10 !text-dark-500;
}

.person-username {
  @apply font-serif;
}

.person-uid {
  @apply absolute bottom-2 right-2 text-gray-400;
}

.space {
  @apply h-10;
}

.person-form {
  @apply w-full flex justify-center flex-col items-center;
}

.edit-button,
.edit-choose {
  @apply absolute;
}

.edit-button {
  @apply top-0 right-0;
}

.edit-choose {
  @apply bottom-20;
}

::v-deep .el-avatar .el-icon {
  @apply w-full h-full;
}
::v-deep .el-avatar .el-icon svg {
  @apply w-full h-full;
}
</style>
