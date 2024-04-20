import request from '@/utils/request'

const commentApi = {
    /**
     * 根据文章ID获取评论列表
     * @param {Object} params - 请求参数
     * @returns {Promise}
     */
    getByArticleId(params) {
        return request({
            method: "GET",
            url: "/comments/page",
            params
        });
    },

    /**
     * 获取回复列表
     * @param {Object} params - 请求参数
     * @returns {Promise}
     */
    getReplies(params) {
        return request({
            method: "GET",
            url: "/comments/replies",
            params
        });
    },

    /**
     * 根据文章ID获取评论数
     * @param {string} id - 文章ID
     * @returns {Promise}
     */
    getCountByArticleId(id) {
        return request({
            method: "GET",
            url: "/comments/" + id
        });
    },

    /**
     * 添加评论
     * @param {Object} data - 请求体数据
     * @returns {Promise}
     */
    addComment(data) {
        return request({
            method: "POST",
            url: "/comments",
            data
        });
    },

    /**
     * 点赞评论
     * @param {string} commentId - 评论ID
     * @param {string} userId - 用户ID
     * @returns {Promise}
     */
    like(commentId, userId) {
        return request({
            method: "POST",
            url: `/comments/like/${commentId}/${userId}`
        });
    },

    /**
     * 取消点赞评论
     * @param {string} commentId - 评论ID
     * @param {string} userId - 用户ID
     * @returns {Promise}
     */
    disLike(commentId, userId) {
        return request({
            method: "POST",
            url: `/comments/dislike/${commentId}/${userId}`
        });
    }
};

export default commentApi;
