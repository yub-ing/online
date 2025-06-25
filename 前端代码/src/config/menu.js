/**
 * 菜单
 */
export default [
    {
        name: "网站首页",
        id: "0_0",
        path: "/",
    },
    {
        name: "首页",
        id: "0",
        path: "/hello"
    },
    {
        name: "功能列表",
        id: "1",
        path: "/hello/codeying",
        children: [
            {path: "/admin", name: "管理员", id: "1_0", parent: "1",roles:['',]},
            {path: "/merchant", name: "商家", id: "1_1", parent: "1",roles:['admin','user',]},
            {path: "/user", name: "用户", id: "1_2", parent: "1",roles:['admin','merchant',]},
            {path: "/goods", name: "商品信息", id: "1_3", parent: "1",roles:['admin','user','merchant',]},
            {path: "/stype", name: "商品类型", id: "1_4", parent: "1",roles:['admin','merchant',]},
            {path: "/cart", name: "购物车", id: "1_5", parent: "1",roles:['admin','user',]},
            {path: "/notice", name: "公告", id: "1_6", parent: "1",roles:['admin','user','merchant',]},
        ]
    },
    {
        name: "我的消息",
        id: "chat",
        path: "/hello/chat"
    },
]

