import req from "../utils/request.js";

export async function getBookList(page, pageSize, keyWord, bookTypeId) {
    try {
        const resp = await req.get(
            `/book/list/${page}/${pageSize}/${keyWord}/${bookTypeId}`
        );
        return resp.data;
    } catch (e) {
        console.log(e);
        return null;
    }
}

export async function saveOrUpdate(data) {
    try {
        const resp = await req.post(`/book/save`, data);
        return resp.data;
    } catch (e) {
        console.log(e);
        return null;
    }
}

export async function getBookType() {
    try {
        const resp = await req.get(`/book/type`);
        return resp.data;
    } catch (e) {
        console.log(e);
        return null;
    }
}

export async function deleteByIdList(idList) {
    try {
        const resp = await req.post(`/book/deleteByIdList`, idList);
        if (resp.data == null || resp.data.code != 200) return null;
        return resp.data.data;
    } catch (e) {
        console.log(e);
        return null;
    }
}

export async function borrow(bookId) {
    try {
        const resp = await req.get(`/book/borrow/${bookId}`);
        if (resp.data == null || resp.data.code != 200) return null;
        return resp.data.data;
    } catch (e) {
        console.log(e);
        return null;
    }
}

export async function getBorrowList() {
    try {
        const resp = await req.get(`/book/getBorrowList`);
        if (resp.data == null || resp.data.code != 200) return null;
        return resp.data.data;
    } catch (e) {
        console.log(e);
        return null;
    }
}

export async function returnBook(id) {
    try {
        const resp = await req.get(`/book/returnBook/${id}`);
        if (resp.data == null || resp.data.code != 200 || !resp.data.data) return false;
        else return true;
    } catch (e) {
        return false;
    }
}