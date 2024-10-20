import {BrowserRouter, Route, Routes} from "react-router-dom";
import {BaseAuth} from "./pages/base_auth.tsx";
import {BaseHome} from "./pages/base_home.tsx";
import {useEffect} from "react";
import Cookies from "js-cookie";
import {WebInfoEntity} from "./models/entity/web_info_entity.ts";
import {GetWebInfoAPI} from "./interface/system_api.ts";
import {useDispatch} from "react-redux";
import {setWebInfo} from "./store/web_store.ts";
import {BaseDashboard} from "./pages/base_dashboard.tsx";

/**
 * # App
 * The main entry of the application.
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
function App() {
    const dispatch = useDispatch();

    useEffect(() => {
        const fetchData = async () => {
            const item = localStorage.getItem("GlobalProperties-WebInfo");
            if (item) {
                const getGlobalProperties = Cookies.get("X-GlobalProperties-ExpiresAt");
                if (getGlobalProperties) {
                    dispatch(setWebInfo(JSON.parse(item) as WebInfoEntity));
                    return;
                }
            }
            const getResp = await GetWebInfoAPI();
            if (getResp?.output === "Success") {
                dispatch(setWebInfo(getResp.data!));
                localStorage.setItem("GlobalProperties-WebInfo", JSON.stringify(getResp.data!));

                // 设置 4 小时的过期时间
                const expiresAt = new Date();
                expiresAt.setHours(expiresAt.getHours() + 4);
                Cookies.set("X-GlobalProperties-ExpiresAt", String(expiresAt.getTime()), {expires: expiresAt});
            }
        };
        fetchData().then();
    }, [dispatch]);

    return (
        <BrowserRouter>
            <Routes>
                <Route path={"/"} element={<BaseHome/>}/>
                <Route path={"/auth/*"} element={<BaseAuth/>}/>
                <Route path={"/dashboard/*"} element={<BaseDashboard/>}/>
            </Routes>
        </BrowserRouter>
    )
}

export default App
