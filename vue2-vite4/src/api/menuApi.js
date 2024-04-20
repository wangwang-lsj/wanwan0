import request from "@/utils/request.js";

const menuApi = {
    /**
     * 分页查询菜单
     * @param params
     * @returns {*}
     */
    page(params) {
        return request({
            method: "GET",
            url: "/menus/page",
            params
        });
    },

    /**
     * 获取所有图标
     * @returns {*}
     */
    getIcons() {
        return request({
            method: "GET",
            url: "/menus/icons"
        });
    },

    /**
     * 根据名称获取菜单
     * @param name
     * @returns {*}
     */
    getByName(name) {
        return request({
            method: "GET",
            url: "/menus",
            params: { name }
        });
    },

    /**
     * 根据id获取菜单
     * @param id
     * @returns {*}
     */
    getById(id) {
        return request({
            method: "GET",
            url: "/menus/" + id
        });
    },

    /**
     * 保存或更新菜单
     * @param data
     * @returns {*}
     */
    saveOrUpdate(data) {
        return request({
            method: "POST",
            url: "/menus",
            data
        });
    },

    /**
     * 通过id删除菜单
     * @param id
     * @returns {*}
     */
    deleteById(id) {
        return request({
            method: "DELETE",
            url: "/menus/" + id
        });
    },

    /**
     * 删除菜单
     * @param ids
     * @returns {*}
     */
    deleteBatch(ids) {
        return request({
            method: "DELETE",
            url: "/menus",
            data: ids
        });
    }
};

export default menuApi;
