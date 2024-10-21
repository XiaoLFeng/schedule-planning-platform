import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import App from './App.tsx'
import './assets/styles/tailwind.css'
import store from "./store/store.ts";
import {Provider} from "react-redux";
import {ConfigProvider} from "antd";
import zhCN from 'antd/locale/zh_CN';
import 'dayjs/locale/zh-cn';

createRoot(document.getElementById('root')!).render(
    <Provider store={store}>
        <ConfigProvider locale={zhCN}>
            <StrictMode>
                <App/>
            </StrictMode>
        </ConfigProvider>
    </Provider>
)
