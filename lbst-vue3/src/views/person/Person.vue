<template>
  <div class="person">
    <el-form :model="form" label-width="auto" style="max-width: 600px">
      <el-form-item label="头像">
        <el-avatar :src="form.headImgUrl" v-if="form.headImgUrl" />
        <el-avatar :icon="UserFilled" v-else />
      </el-form-item>
      <el-form-item label="UID">
        <el-input
          v-model="form.uid"
          style="width: 240px"
          :placeholder="data.uid"
          :disabled="!canModify"
        />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input
          v-model="form.nickName"
          style="width: 240px"
          :placeholder="
            data.nickName && data.nickName != 'unnamed'
              ? data.nickName
              : '未录入'
          "
          :disabled="!canModify"
        />
      </el-form-item>
      <el-form-item label="出生年份">
        <el-input
          v-model="data.birthYear"
          style="width: 240px"
          :placeholder="data.yearOfBirth ? data.yearOfBirth : '未录入'"
          :disabled="!canModify"
        />
      </el-form-item>
      <el-form-item label="学历">
        <el-select
          v-model="data.education"
          :placeholder="data.education ? data.education : Select"
          size="large"
          style="width: 240px"
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
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { flushHeadImg, getUserInfo } from "../../api/personApi";
import { UserFilled } from "@element-plus/icons-vue";

const form = reactive({
  headImgUrl: "",
  uid: "",
  nickName: "",
  birthYear: "",
  education: "",
});

const data = reactive({
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
};

initForm();

const canModify = ref(false);

const clearForm = () => {
  form.uid = "";
  form.nickName = "";
  form.birthYear = "";
  form.education = "";
};

const modifyData = () => {
  data.nickName = form.nickName;
  data.yearOfBirth = form.birthYear;
  data.education = form.education;
};

setInterval(() => {
  console.log(form);
}, 1000 * 5);
</script>

<style scoped>
.person {
  @apply w-full h-full bg-white shadow-lg rounded-lg;
}
</style>
