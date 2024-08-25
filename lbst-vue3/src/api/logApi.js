import req from "../utils/request";

const logApi = (function () {
    return {
        getLoginLog: async function (pageNum, pageSize) {
            try {
                const res = await req.get(`/loginLogList/${pageNum}/${pageSize}`);
                if (res.data == null || res.data.code != 200) return null;
                return res.data.data;
            } catch (e) {
                console.log(e);
                return null;
            }
        },

        getBookLog: async function (pageNum, pageSize) {
            try {
                const res = await req.get(`/bookLogList/${pageNum}/${pageSize}`);
                if (res.data == null || res.data.code != 200) return null;
                return res.data.data;
            } catch (e) {
                console.log(e);
                return null;
            }
        },

        getOpLog: async function (pageNum, pageSize) {
            try {
                const res = await req.get(`/opLogList/${pageNum}/${pageSize}`);
                if (res.data == null || res.data.code != 200) return null;
                return res.data.data;
            } catch (e) {
                console.log(e);
                return null;
            }
        },

        getErrorLog: async function (pageNum, pageSize) {
            try {
                const res = await req.get(`/errorLogList/${pageNum}/${pageSize}`);
                if (res.data == null || res.data.code != 200) return null;
                return res.data.data;
            } catch (e) {
                console.log(e);
                return null;
            }
        },
    };
})();

export default logApi;