import {SlateTransforms} from '@wangeditor/editor'
class MyButtonMenu{
    title =  "生成目录"
    // iconSvg = QUOTE_SVG
    tag = 'button'

    getValue() {
        // 用不到 getValue
        return ''
    }

    isActive() {
        return false
    }

    isDisabled(){
        return false
    }

    exec(editor) {

        let headers = editor.getElemsByTypePrefix('header')
        let nodes = headers.map(item => {

            let node = {
                type: 'paragraph',
                children: [{
                    text: item.children[0].text,
                    color: 'rgb(103, 149, 181)'
                }]
            }
            if (item.type === 'header1') {
                node.indent = '2em'

            }

            if (item.type === 'header2') {
                node.indent = '4em'

            }
            if (item.type === 'header3') {
                node.indent = '6em'
            }
            if (item.type === 'header4') {
                node.indent = '8em'
            }
            if (item.type === 'header5') {
                node.indent = '10em'
            }


            return node
        })
        const divider = {
            type: 'divider',
            children: [{
                text: ''
            }]
        }

        const menu = {
            type: 'paragraph',
            children: [{
                text: '目录',
                bold: true
            }]
        }

        nodes.unshift(menu)
        nodes.push(divider)

        SlateTransforms.insertNodes(editor, nodes)
    }
}


export const myButtonMenu={
    key: 'createMenu', // menu key ，唯一。注册之后，可配置到工具栏
    factory() {
        return new MyButtonMenu()
    }
}
