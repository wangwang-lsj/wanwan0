import request from "@/utils/request.js";
import { serverIp } from "../../public/config.js";

const fileApi = {
    /**
     * 获取文件上传地址
     * @returns {string}
     */
    upload() {
        return 'http://' + serverIp + ':9090/api/files/upload';
    },

    /**
     * 下载文件
     * @param fileUUID
     */
    download(fileUUID) {
        window.open(fileUUID);
    },

    /**
     * 分页查询文件
     * @param params
     * @returns {*}
     */
    page(params) {
        return request({
            method: "GET",
            url: "/files/page",
            params
        });
    },

    /**
     * 更新文件信息
     * @param data
     * @returns {*}
     */
    update(data) {
        return request({
            method: "PUT",
            url: "/files/update",
            data
        });
    },

    /**
     * 根据ID删除文件
     * @param id
     * @returns {*}
     */
    deleteById(id) {
        return request({
            method: "DELETE",
            url: "/files/" + id
        });
    },

    /**
     * 批量删除文件
     * @param ids
     * @returns {*}
     */
    deleteBatch(ids) {
        return request({
            method: "DELETE",
            url: "/files",
            data: ids
        });
    },

    /**
     * 编辑文件信息
     * @returns {*}
     */
    editFileById() {
        return request({
            method: "PUT",
            url: "/files"
        });
    }
};

export default fileApi;
