import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import App from './App.tsx'
import './assets/styles/tailwind.css'
import store from "./store/store.ts";
import {Provider} from "react-redux";

createRoot(document.getElementById('root')!).render(
    <Provider store={store}>
        <StrictMode>
            <App/>
        </StrictMode>
    </Provider>
)
