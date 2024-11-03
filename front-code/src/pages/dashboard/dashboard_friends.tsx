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

import {useSelector} from "react-redux";
import {WebInfoEntity} from "../../models/entity/web_info_entity.ts";
import {JSX, useEffect, useRef, useState} from "react";
import {GetFriendAllowAPI, GetFriendApplicationAPI, GetUserFriendsListAPI} from "../../interface/friends_api.ts";
import {message} from "antd";
import {UserFriendListEntity} from "../../models/entity/user_friends_list_entity.ts";
import avatar1 from "../../assets/images/avatar_1.webp";
import avatar2 from "../../assets/images/avatar_2.webp";
import avatar3 from "../../assets/images/avatar_3.webp";
import avatar4 from "../../assets/images/avatar_4.webp";
import {AlertOutlined, PlusOutlined, UserAddOutlined} from "@ant-design/icons";

export function DashboardFriends({onHeaderHandler}: { onHeaderHandler: (header: string) => void }) {
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

    const [userList, setUserList] = useState<UserFriendListEntity[]>([] as UserFriendListEntity[]);
    const [friendApplication, setFriendApplication] = useState<UserFriendListEntity[]>([] as UserFriendListEntity[]);
    const [friendAllow, setFriendAllow] = useState<UserFriendListEntity[]>([] as UserFriendListEntity[]);
    const refresh = useRef<boolean>(false);
    const [userUuid, setUserUuid] = useState<string>();

    document.title = `${webInfo.name} - 好友`;
    onHeaderHandler("好友");

    useEffect(() => {
        const funcUserFriend = async () => {
            const getResp = await GetUserFriendsListAPI();
            if (getResp?.output === "Success") {
                setUserList(getResp.data!);
            } else {
                message.warning(getResp?.error_message);
            }
        }
        const funcFriendApplication = async () => {
            const getResp = await GetFriendApplicationAPI();
            if (getResp?.output === "Success") {
                setFriendApplication(getResp.data!);
            } else {
                message.warning(getResp?.error_message);
            }
        }
        const funcFriendAllow = async () => {
            const getResp = await GetFriendAllowAPI();
            if (getResp?.output === "Success") {
                setFriendAllow(getResp.data!);
            } else {
                message.warning(getResp?.error_message);
            }
        }
        funcUserFriend().then();
        funcFriendApplication().then();
        funcFriendAllow().then();
    }, [refresh]);

    useEffect(() => {
        // 获取按钮元素
        const button = document.querySelector('#flicker-button');

        if (!button) return; // 确保按钮存在

        // 设置定时器，每秒切换一次类名
        const interval = setInterval(() => {
            button.classList.toggle('bg-red-700');  // 降低亮度
        }, 1000);

        // 清理定时器以避免内存泄漏
        return () => clearInterval(interval);
    });

    useEffect(() => {
        if (userUuid != undefined || userUuid !== "") {

        }
    }, [userUuid]);

    function handlerAvatar(): string {
        const avatars = [avatar1, avatar2, avatar3, avatar4];
        return avatars[Math.floor(Math.random() * avatars.length)];
    }

    // 生成用户列表元素
    function makeUserListElement(): JSX.Element[] {
        return userList.map((item, index) => {
            return (
                <div key={index}
                     className={"transition flex items-center justify-between p-3 hover:bg-gray-100 cursor-pointer rounded-lg " + (userUuid === item.uuid ? "bg-gray-100" : "")}
                     onClick={() => setUserUuid(item.uuid)}>
                    <div className={"flex items-center max-w-full overflow-hidden"}>
                        <img src={handlerAvatar()} alt={item.uuid} className={"w-10 h-10 rounded-full"}/>
                        <div className={"ml-3 max-w-full overflow-hidden"}>
                            <p className={"text-lg font-medium whitespace-nowrap overflow-hidden text-ellipsis"}>{item.username}</p>
                            <p className={"text-sm text-gray-500"}>{item.phone}</p>
                        </div>
                    </div>
                </div>
            );
        });
    }

    // 若申请的用户存在，则显示红色按钮
    function getAlertButton(): JSX.Element {
        if (friendApplication.length > 0) {
            return (
                <div
                    id={"flicker-button"}
                    className="transition bg-red-500 flex items-center px-2 text-white rounded-md hover:bg-red-600">
                    <AlertOutlined/>
                </div>
            );
        } else {
            return <></>;
        }
    }

    function getAllowFriendButton(): JSX.Element {
        if (friendAllow.length > 0) {
            return (
                <div
                    className="transition bg-emerald-500 flex items-center px-2 text-white rounded-md hover:bg-emerald-600">
                    <UserAddOutlined/>
                </div>
            );
        } else {
            return <></>;
        }
    }

    return (
        <div className={"grid gap-3 grid-cols-6"}>
            <div
                className="col-span-2 bg-white rounded-lg shadow-lg p-3 space-y-3 w-full h-full flex flex-col max-h-dvh -mb-32">
                <div className={"flex justify-between"}>
                    <div className="text-xl font-bold">好友列表</div>
                    <div className={"flex gap-1"}>
                        {getAlertButton()}
                        {getAllowFriendButton()}
                        <div
                            className={"transition bg-sky-500 flex items-center px-4 text-white rounded-md hover:bg-sky-600 space-x-1"}>
                            <PlusOutlined/>
                            <span>添加</span>
                        </div>
                    </div>
                </div>
                <div className="overflow-y-auto overflow-x-hidden space-y-1">
                    {makeUserListElement()}
                </div>
            </div>
            <div className={"hidden lg:block col-span-4 bg-white rounded-lg shadow-lg overflow-y-auto p-3"}>
                <div className="text-xl font-bold">好友信息</div>
            </div>
        </div>
    );
}
