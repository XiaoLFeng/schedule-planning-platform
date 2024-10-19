/*
 * ***************************************************************************************
 * author: XiaoLFeng(https://www.x-lf.com)
 * about:
 *   The project contains the source code of com.xlf.schedule.
 *   All source code for this project is licensed under the MIT open source license.
 * licenseStatement:
 *   Copyright (c) 2016-2024 XiaoLFeng. All rights reserved.
 *   For more information about the MIT license, please view the LICENSE file
 *     in the project root directory or visit:
 *   https://opensource.org/license/MIT
 * disclaimer:
 *   Since this project is in the model design stage, we are not responsible for any losses
 *     caused by using this project for commercial purposes.
 *   If you modify the code and redistribute it, you need to clearly indicate what changes
 *     you made in the corresponding file.
 *   If you want to modify it for commercial use, please contact me.
 * ***************************************************************************************
 */

import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {WebInfoEntity} from "../models/entity/web_info_entity.ts";

const initialState: WebInfoEntity = {
    name: '',
    version: '',
    author: '',
    license: '',
    copyright: '',
    icp: '',
    record: '',
    description: '',
    keywords: ''
} as WebInfoEntity;

const webInfoSlice = createSlice({
    name: 'webInfo',
    initialState,
    reducers: {
        setWebInfo: (state, action: PayloadAction<WebInfoEntity>) => {
            return {...state, ...action.payload};
        },
        updateWebInfoField: (state, action: PayloadAction<{ key: keyof WebInfoEntity, value: string }>) => {
            state[action.payload.key] = action.payload.value;
        },
        resetWebInfo: () => initialState
    }
});

export const {setWebInfo, updateWebInfoField, resetWebInfo} = webInfoSlice.actions;
export default webInfoSlice.reducer;
