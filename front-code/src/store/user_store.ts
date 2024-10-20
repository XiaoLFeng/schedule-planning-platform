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

import {UserEntity} from "../models/entity/user_entity";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

const initialState: UserEntity = {
    bannedAt: 0,
    createdAt: 0,
    email: "",
    enable: false,
    phone: "",
    role: "",
    updatedAt: 0,
    username: "",
    uuid: ""
} as UserEntity;

const userCurrentSlice = createSlice({
    name: 'userCurrent',
    initialState,
    reducers: {
        setUser: (state, action: PayloadAction<UserEntity>) => {
            return {...state, ...action.payload};
        },
        updateUserField: (state, action: PayloadAction<{ key: keyof UserEntity, value: never }>) => {
            state[action.payload.key] = action.payload.value;
        },
        resetUser: () => initialState
    }
});

export const {setUser, updateUserField, resetUser} = userCurrentSlice.actions;
export default userCurrentSlice.reducer;
