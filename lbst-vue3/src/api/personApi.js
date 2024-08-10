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
