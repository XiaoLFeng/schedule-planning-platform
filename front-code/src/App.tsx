import {BrowserRouter, Route, Routes} from "react-router-dom";
import {BaseAuth} from "./pages/base_auth.tsx";
import {BaseHome} from "./pages/base_home.tsx";
import {useEffect} from "react";
import Cookies from "js-cookie";
import {WebInfoDTO} from "./models/entity/web_info_dto.ts";
import {GetWebInfoAPI} from "./interface/system_api.ts";
import {useDispatch} from "react-redux";
import {setWebInfo} from "./store/web_store.ts";

function App() {
    const dispatch = useDispatch();

    useEffect(() => {
        const fetchData = async () => {
            const item = localStorage.getItem("GlobalProperties-WebInfo");
            if (item) {
                const getGlobalProperties = Cookies.get("X-GlobalProperties-ExpiresAt");
                if (getGlobalProperties) {
                    dispatch(setWebInfo(JSON.parse(item) as WebInfoDTO));
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
            </Routes>
        </BrowserRouter>
    )
}

export default App
