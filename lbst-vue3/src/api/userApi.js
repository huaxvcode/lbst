import req from "../utils/request";

export async function getRoleByUid(uid) {
  try {
    const resp = await req.get(`/role/${uid}`);
    return resp.data;
  } catch(e) {
    console.log(e);
    return null;
  }
}
