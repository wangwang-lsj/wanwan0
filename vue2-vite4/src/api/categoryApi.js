import request from "@/utils/request.js";

const categoryApi = {
    /**
     * 获取所有分类
     * @returns {Promise}
     */
    getAll() {
        return request({
            method: "GET",
            url: "/categories"
        });
    },

    /**
     * 根据ID获取分类信息
     * @param {string} id - 分类ID
     * @returns {Promise}
     */
    getById(id) {
        return request({
            method: "GET",
            url: `/categories/${id}`
        });
    },

    /**
     * 分页查询分类列表
     * @param {Object} params - 请求参数
     * @returns {Promise}
     */
    page(params) {
        return request({
            method: "GET",
            url: "/categories/page",
            params
        });
    },

    /**
     * 新增或更新分类信息
     * @param {Object} data - 请求体数据
     * @returns {Promise}
     */
    saveOrUpdate(data) {
        return request({
            method: "POST",
            url: "/categories",
            data
        });
    },

    /**
     * 根据ID删除分类
     * @param {string} id - 分类ID
     * @returns {Promise}
     */
    deleteById(id) {
        return request({
            method: "DELETE",
            url: `/categories/${id}`
        });
    },

    /**
     * 批量删除分类
     * @param {Array} ids - 要删除的分类ID数组
     * @returns {Promise}
     */
    deleteBatch(ids) {
        return request({
            method: "DELETE",
            url: "/categories",
            data: ids
        });
    }
};

export default categoryApi;
