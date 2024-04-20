//获取当前可视范围的高度
export const getClientHeight = () => {
    let clientHeight = 0;
    if (document.body.clientHeight && document.documentElement.clientHeight) {
        clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight)
    } else {
        clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight)
    }
    return clientHeight
}

//获取文档完整的高度
export const getScrollHeight= () => {
    return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight)
}

//获取当前滚动条的位置
export const getScrollTop = () =>{
    let scrollTop = 0;
    //window.pageYOffset = document.documentElement.scrollTop
    if (document.documentElement && document.documentElement.scrollTop) {
        scrollTop = document.documentElement.scrollTop
    } else if (document.body) {
        scrollTop = document.body.scrollTop
    }
    return scrollTop
}

/**
 * 防抖
 * @param {Function} fn
 * @param {Number} delay
 */

export const debounce = function debounce(fn, delay) {
    let timer = null;
    return function () {
        clearTimeout(timer);
        let args = arguments;
        let that = this;
        timer = setTimeout(function () {
            fn.apply(that, args);
        }, delay);
    };
};
