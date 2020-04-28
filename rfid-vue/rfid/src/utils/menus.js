import {getRequest} from "./api";

export const initMenu=(router,store)=>{
    if(store.state.routes.length > 0){
        return;
    }
    getRequest("/system/config/menu").then(data=>{
        if (data){
            let fmtRoutes = formatRoutes(data);
            router.addRoutes(fmtRoutes);
            store.commit('initRoutes',fmtRoutes);
        }
    })
}

export const formatRoutes=(routes)=>{
    let fmRoutes = [];
    routes.forEach(router=>{
        let {
            path,
            component,
            name,
            meta,
            iconCls,
            children
        } = router;
        if (children && children instanceof Array){
            children = formatRoutes(children);
        }
        let fmRouter = {
            path: path,
            name: name,
            iconCls: iconCls,
            meta: meta,
            children: children,
            component(resolve){
                if (component.startsWith('Home')){
                    require(['../views/'+component+'.vue'],resolve);
                } else if (component.startsWith('Pos')){
                    require(['../views/pos/'+component+'.vue'],resolve);
                }else if (component.startsWith('Sys')){
                    require(['../views/sys/'+component+'.vue'],resolve);
                }
            }
        }
        fmRoutes.push(fmRouter);
    });
    return fmRoutes;
}
