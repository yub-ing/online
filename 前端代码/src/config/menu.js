/**
 * 菜单
 */
export default [
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
            {path: "/admin", name: "管理员", id: "1_0", parent: "1",roles:['admin',]},
            {path: "/user", name: "用户", id: "1_1", parent: "1",roles:['admin',]},
        ]
    },

]

