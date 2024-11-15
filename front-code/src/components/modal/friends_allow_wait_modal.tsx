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

import {message, Modal} from "antd";
import {useEffect, useState} from "react";
import {AllowUserAPI} from "../../interface/friends_api.ts";
import {UserFriendEntity} from "../../models/entity/user_friends_entity.ts";
import {FriendAddDTO} from "../../models/dto/friend_add_dto.ts";

export function FriendsAllowWaitModal({propOpen, friendApplication, emit, refresh}: {
    propOpen: boolean;
    friendApplication: UserFriendEntity[];
    emit: (open: boolean) => void,
    refresh: (data: boolean) => void
}) {
    const [open, setOpen] = useState(false);

    useEffect(() => {
        setOpen(propOpen);
    }, [propOpen]);

    async function handleOk() {
        emit(false);
    }

    async function allowSubmit(uuid: string, allow: boolean) {
        const getResp = await AllowUserAPI({friend_uuid: uuid, allow: allow} as FriendAddDTO);
        if (getResp?.output === "Success") {
            message.success("操作成功");
            refresh(true);
        } else {
            message.warning(getResp?.error_message);
        }
    }

    return (
        <Modal
            open={open}
            title="好友申请"
            onOk={handleOk}
            onCancel={() => emit(false)}
            footer={
                <div className="flex gap-1 justify-end text-white">
                    <button
                        onClick={handleOk}
                        className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition"
                    >
                        完成
                    </button>
                </div>
            }
        >
            <div className="flex flex-col gap-3">
                {friendApplication?.length === 0 ? (
                    <div className="text-center text-gray-500">暂无好友申请</div>
                ) : (
                    <>
                        <div className="grid gap-1">
                            {friendApplication.map((item, index) => (
                                <div key={index}
                                     className="transition flex justify-between items-center gap-1 p-3 bg-gray-100/50 shadow rounded-lg hover:bg-gray-100 hover:shadow-lg">
                                    <div className={"flex flex-col"}>
                                        <div className="flex items-center gap-1">
                                            <span className="text-sm font-medium">{item.username}</span>
                                            <span className="text-xs text-gray-500">({item.phone})</span>
                                        </div>
                                        <div className="text-xs text-gray-500">{item.email ? item.email : "null"}</div>
                                    </div>
                                    <div className={"flex gap-1 text-white"}>
                                        <button onClick={() => allowSubmit(item.uuid, true)}
                                                className="py-1 px-4 rounded-lg shadow bg-green-500 hover:bg-green-600 transition">
                                            同意
                                        </button>
                                        <button onClick={() => allowSubmit(item.uuid, false)}
                                                className="py-1 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition">
                                            拒绝
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </>
                )}
            </div>
        </Modal>
    );
}
