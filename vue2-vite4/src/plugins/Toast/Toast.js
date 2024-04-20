import ToastComponent from './Toast.vue' /* 引入的时候，Toast.vue中的script代码就执行了 */
const Toast = {}

Toast.install = function(Vue) {
    // debugger

    // console.log(ToastComponent); /* {name: 'Tip', watch: {…}, data: ƒ, created: ƒ, mounted: ƒ, …} */

    let ToastComponentConstructor = Vue.extend(ToastComponent)
    /* console.log(ToastComponentConstructor); */ /* 构造器函数 
                                            ƒ VueComponent(options) {
                                                this._init(options);
                                            } */

    let toastInstance = new ToastComponentConstructor() /* 此时，Toast组件的created方法会执行, 创建一个toast组件实例 */
    // console.log(toastInstance);/* VueComponent {_uid: 0, _isVue: true, __v_skip: true, _scope: EffectScope, $options: {…}, …} */

    // console.log(toastInstance.$el,11); /* undefined, 要调用挂载方法之后才能拿到 */
    toastInstance.$mount()             
    // console.log(toastInstance.$el,11); /* <div data-v-0321d2b2="" class="toast-container" style="z-index: 999999;"><div data-v-0321d2b2="" class="toast" style="background-color: rgb(82, 196, 26); display: none;"><i data-v-0321d2b2="" class="icon iconfont" style="background-color: rgb(82, 196, 26);"></i><span data-v-0321d2b2=""></span></div></div> */

    // console.log(toastInstance.$mount() === toastInstance.$mount(), toastInstance ===toastInstance.$mount() ,1); // true true 1 /* 也就是说是都是同一个toastInstance, 且每次调用$mount方法，都会触发mounted方法执行 */

    let toastInstanceEl = toastInstance.$mount().$el
    // debugger
    // console.log(toastInstanceEl.scrollHeight,'toast');
    // console.log(toastInstanceEl);  /* 此时，Toast组件的mounted方法会执行 */
                                   /* 打印出：<div data-v-0321d2b2="" class="toast-container" style="z-index: 999999;"><div data-v-0321d2b2="" class="toast" style="background-color: rgb(82, 196, 26); display: none;"><i data-v-0321d2b2="" class="icon iconfont" style="background-color: rgb(82, 196, 26);"></i><span data-v-0321d2b2=""></span></div></div> */

    // console.log('同一个vue组件调用多次$mount, 获取的$el是同一个吗??',toastInstanceEl === toastInstance.$mount().$el); // true /* 也就是说一个vue组件被创建出来，多次调用挂载方法，只会获取到一个$el的dom */

    /* 所以，在mounted方法中，此时，其实还没有真正的添加到父节点，但是dom已经有了，就是$el，可以调用$el.querySelector(..)了 */

    document.body.appendChild(toastInstance.$mount().$el) /* 获取组件挂在后的dom，添加到父节点 */

    Vue.prototype.$toast = function(type,msg) {
        toastInstance.showMsg(type,msg) /* 通过操作组件身上的方法，来操作该组件挂载的dom */
    }
    

    /* 结合上面的测试，可以发现，
       1. 我们写的vue文件，其实就是在定义一个对象，这个对象，指定模板和配置选项，
       2. Vue根据我们定义的这个对象，使用Vue.extend({..})方法创建vue组件构造函数，
       3. 使用new调用这个构造函数，就会创建出一个vue组件实例，同时这个组件实例的created方法会被调用（这个created方法就是我们定义vue文件时，自己写的） 
       4. 同一个组件实例调用$mount方法后，只会生成一个$el在组件身上，后面组件实例继续调用$mount, 获取的就是该组件实例上的$el。并且都会执行mounted方法。
       5. 把组件挂在后，获取的$el添加到真实dom节点
       5. 通过调用组件身上的方法，然后就可以操作dom了
    */
}

export default Toast