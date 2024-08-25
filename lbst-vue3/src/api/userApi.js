import req from "../utils/request";

export async function getRoleByUid(uid) {
    try {
        const resp = await req.get(`/role/${uid}`);
        return resp.data;
    } catch (e) {
        console.log(e);
        return null;
    }
}

export async function getUserList() {
    try {
        const resp = await req.get(`/userList`);
        if (resp.data == null || resp.data.code != 200) return null;
        return resp.data.data;
    } catch (e) {
        console.log(e);
        return null;
    }
}

export async function setRoleType(uid, type) {
    try {
        const resp = await req.get(`/setRoleType/${uid}/${type}`);
        if (resp.data == null || resp.data.code != 200) return null;
        return resp.data.data;
    } catch (e) {
        console.log(e);
        return null;
    }
}