/*
 * ***************************************************************************************
 * Author: XiaoLFeng (https://www.x-lf.com)
 * About:
 *   This project contains the source code of com.xlf.schedule.
 *   All source code for this project is licensed under the MIT open source license.
 * License Statement:
 *   Copyright (c) 2016-2024 XiaoLFeng. All rights reserved.
 *   For more information about the MIT license, please view the LICENSE file
 *   in the project root directory or visit:
 *   https://opensource.org/license/MIT
 * Disclaimer:
 *   Since this project is in the model design stage, we are not responsible for any losses
 *   caused by using this project for commercial purposes.
 *   If you modify the code and redistribute it, you need to clearly indicate what changes
 *   you made in the corresponding file.
 *   If you want to modify it for commercial use, please contact me.
 * ***************************************************************************************
 */

import {message, Modal} from "antd";
import {DashOutlined, LoadingOutlined, WarningOutlined} from "@ant-design/icons";
import {JSX, useEffect, useState} from "react";
import {UserInfoEntity} from "../../models/entity/user_info_entity.ts";
import {SelectUserListAPI} from "../../interface/select_list_api.ts";
import {AddUserAPI} from "../../interface/friends_api.ts";
import {useSelector} from "react-redux";
import {UserEntity} from "../../models/entity/user_entity.ts";
import {FriendAddDTO} from "../../models/dto/friend_add_dto.ts";

export function FriendsAddModal({propOpen, emit}: { propOpen: boolean; emit: (open: boolean) => void }) {
    const userEntity = useSelector((state: { userCurrent: UserEntity }) => state.userCurrent);

    const [open, setOpen] = useState(false);
    const [selectInput, setSelectInput] = useState<string>("");
    const [remark, setRemark] = useState<string>("");
    const [debounce, setDebounce] = useState<number>(0);
    const [userList, setUserList] = useState<UserInfoEntity[]>([] as UserInfoEntity[]);
    const [loading, setLoading] = useState<boolean>(false);
    const [button, setButton] = useState<JSX.Element>(
        <div className="flex gap-3 justify-end text-white">
            <button
                onClick={() => emit(false)}
                className="py-1.5 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition"
            >
                取消
            </button>
            <button
                onClick={handleOk}
                className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition"
            >
                添加
            </button>
        </div>
    );

    useEffect(() => {
        setOpen(propOpen);
    }, [propOpen]);

    useEffect(() => {
        clearTimeout(debounce);
        setDebounce(
            setTimeout(async () => {
                const getResp = await SelectUserListAPI(selectInput);
                if (getResp?.output === "Success") {
                    if (getResp.data!.length === 0) {
                        setUserList([{username: "未查询到用户"} as UserInfoEntity]);
                    } else {
                        setUserList(getResp.data!);
                    }
                } else {
                    message.warning("查询格式有误");
                }
                setDebounce(0);
                console.log("userList", userList);
            }, 500)
        );
    }, [selectInput]);

    useEffect(() => {
        if (loading) {
            setButton(
                <div className="flex gap-3 justify-end text-white">
                    <button disabled={true}
                            className="py-1.5 px-4 rounded-lg shadow bg-red-700 transition"
                    >
                        取消
                    </button>
                    <button
                        onClick={handleOk}
                        className="py-1.5 px-4 rounded-lg shadow bg-sky-600 transition flex items-center gap-1"
                    >
                        <LoadingOutlined/>
                        <span>操作中</span>
                    </button>
                </div>
            )
        } else {
            setButton(
                <div className="flex gap-3 justify-end text-white">
                    <button
                        onClick={() => emit(false)}
                        className="py-1.5 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition"
                    >
                        取消
                    </button>
                    <button
                        onClick={handleOk}
                        className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition"
                    >
                        添加
                    </button>
                </div>
            );
        }
    }, [loading]);

    async function handleOk() {
        setLoading(true);
        const getResp = await AddUserAPI({friend_uuid: selectInput, remark: remark} as FriendAddDTO);
        if (getResp?.output === "Success") {
            message.success("添加成功，等待对方接受");
            emit(false);
        } else {
            message.warning(getResp?.error_message);
        }
        setLoading(false);
    }

    return (
        <Modal
            open={open}
            title="添加好友"
            onOk={handleOk}
            onCancel={() => emit(false)}
            footer={button}
        >
            <div className="flex flex-col gap-3">
                <div className="grid gap-1">
                    {userList
                        .filter((item) => item.uuid === selectInput)
                        .map((item, index) => {
                            if (userEntity.uuid === item.uuid) {
                                return (
                                    <div key={index} role="alert"
                                         className="rounded border-s-4 border-red-500 bg-red-50 p-4">
                                        <div className="flex items-center gap-2 text-red-800">
                                            <WarningOutlined/>
                                            <strong className="block font-medium">您不能添加自己为好友</strong>
                                        </div>
                                        <p className="mt-2 text-sm text-red-700">
                                            您当前所查询的用户为您自己，您不能添加自己为好友。
                                        </p>
                                    </div>
                                );
                            }
                            return (
                                <div key={index} className="flex flex-col">
                                    <div className={"grid grid-cols-2 border-t border-b border-gray-400"}>
                                        <div className={"text-center border-r border-gray-400"}>用户名</div>
                                        <div className={"text-center"}>{item.username}</div>
                                    </div>
                                    <div className={"grid grid-cols-2 border-b border-gray-400"}>
                                        <div className={"text-center border-r border-gray-400"}>邮箱</div>
                                        <div className={"text-center"}>{item.email}</div>
                                    </div>
                                    <div className={"grid grid-cols-2 border-b border-gray-400"}>
                                        <div className={"text-center border-r border-gray-400"}>手机号</div>
                                        <div className={"text-center"}>{item.phone}</div>
                                    </div>
                                </div>
                            );
                        })}
                </div>
                <div>
                    <label htmlFor="HeadlineAct" className="block text-sm font-medium text-gray-900">
                        用户查询
                    </label>
                    <div className="relative mt-1.5">
                        <input
                            type="text"
                            list="HeadlineActArtist"
                            id="HeadlineAct"
                            onInput={(e) => setSelectInput(e.currentTarget.value)}
                            className="transition w-full rounded-lg border-gray-300 pe-10 text-gray-700 sm:text-sm [&::-webkit-calendar-picker-indicator]:opacity-0"
                            placeholder="可输入用户名、邮箱、手机号查询"
                        />
                        <span className="absolute inset-y-0 end-0 flex w-8 items-center">
                            <DashOutlined/>
                        </span>
                    </div>
                    <datalist id="HeadlineActArtist">
                        {userList.map((item, index) => {
                            if (item.username === "未查询到用户") {
                                return <option key={index} value={item.username} disabled/>;
                            } else {
                                return (
                                    <option key={index} value={item.uuid}>
                                        {item.username} [{item.phone}]
                                    </option>
                                );
                            }
                        })}
                    </datalist>
                </div>
                <div>
                    <label htmlFor="HeadlineAct" className="block text-sm font-medium text-gray-900">
                        好友请求描述
                    </label>
                    <textarea
                        id="HeadlineAct"
                        onInput={(e) => setRemark(e.currentTarget.value)}
                        className="transition w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                        placeholder="好友请求描述"
                    />
                </div>
            </div>
        </Modal>
    )
        ;
}
