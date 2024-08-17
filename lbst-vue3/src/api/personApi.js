import req from "../utils/request"


export async function getHeadImgUrl(name) {
  const resp = await req.get(`/head-img/${name}`, {
    responseType: 'blob'
  });
  const url = URL.createObjectURL(resp.data);
  return url;
}

export function getUserInfo(uid) {
  return req.get(`/user-info/${uid}`)
}

// export async function flushHeadImg() {
//   const userId = localStorage.getItem("userId");
//   let headImgUrl = null;
//   await getUserInfo(userId)
//     .then((resp) => resp.data)
//     .then((data) => {
//       if (data.data && data.data.headImg) {
//         const headImgName = data.data.headImg;

//         // 这里没有 await，导致 headImgUrl 还是 null 时，
//         // 被最后一句 return 出去
//         getHeadImgUrl(headImgName)
//           .then((url) => {
//             headImgUrl = url;
//           })
//           .catch((e) => {
//             headImgUrl = null;
//             console.log("获取头像失败！");
//           });


//       } else {
//         headImgUrl = null;
//       }
//     });
//   return headImgUrl;
// };

export async function flushHeadImg() {
  const userId = localStorage.getItem("userId");
  const url = await getUserInfo(userId)
    .then((resp) => resp.data)
    .then((data) => {
      if (data.data && data.data.headImg) {
        const headImgName = data.data.headImg;
        return getHeadImgUrl(headImgName)
          .then((url) => {
            return url;
          })
          .catch((e) => {
            console.log("获取头像失败！");
            return null;
          });
      } else {
        return null;
      }
    });
  return url;
};
