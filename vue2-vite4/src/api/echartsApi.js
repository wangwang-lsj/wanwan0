import request from "@/utils/request.js";

const echartsApi = {
    /**
     * 获取统计数据
     * @returns {*}
     */
    statistics() {
        return request({
            method: "GET",
            url: "/echarts/statistics"
        });
    },

    /**
     * 获取示例数据
     * @returns {*}
     */
    example() {
        return request({
            method: "GET",
            url: "/echarts/example"
        });
    },

    /**
     * 获取成员数据
     * @returns {*}
     */
    members() {
        return request({
            method: "GET",
            url: "/echarts/members"
        });
    }
};

export default echartsApi;
