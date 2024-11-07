import {useSelector} from "react-redux";
import {JSX, useEffect, useState} from "react";
import {animated, useSpring, useTransition} from "@react-spring/web";
import {WebInfoEntity} from "../../models/entity/web_info_entity.ts";
import {
    DeleteUserAPI,
    GetFriendAllowAPI,
    GetFriendApplicationAPI,
    GetFriendDeniedAPI,
    GetUserFriendsListAPI
} from "../../interface/friends_api.ts";
import {message} from "antd";
import {UserFriendEntity} from "../../models/entity/user_friends_entity.ts";
import {AlertOutlined, DeleteOutlined, PlusOutlined, UserAddOutlined} from "@ant-design/icons";
import avatar1 from "../../assets/images/avatar_1.webp";
import avatar2 from "../../assets/images/avatar_2.webp";
import avatar3 from "../../assets/images/avatar_3.webp";
import avatar4 from "../../assets/images/avatar_4.webp";
import {FriendsAddModal} from "../../components/modal/friends_add_modal.tsx";
import {FriendsAllowWaitModal} from "../../components/modal/friends_allow_wait_modal.tsx";
import {FriendsApplicationModal} from "../../components/modal/friends_application_modal.tsx";

export function DashboardFriends({onHeaderHandler}: { onHeaderHandler: (header: string) => void }) {
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

    const [userList, setUserList] = useState<UserFriendEntity[]>([] as UserFriendEntity[]);
    const [friendApplication, setFriendApplication] = useState<UserFriendEntity[]>([] as UserFriendEntity[]);
    const [friendAllow, setFriendAllow] = useState<UserFriendEntity[]>([] as UserFriendEntity[]);
    const [friendDenied, setFriendDenied] = useState<UserFriendEntity[]>([] as UserFriendEntity[]);
    const [friendInfo, setFriendInfo] = useState<JSX.Element>(
        <div className={"hidden"}>
            <div className="text-xl font-bold">Loading......</div>
        </div>
    );
    const [groupItems, setGroupItems] = useState([] as { id: number, name: string, description: string }[]);
    const [singleFriend, setSingleFriend] = useState<UserFriendEntity>({uuid: ""} as UserFriendEntity);

    const [addUserModal, setAddUserModal] = useState<boolean>(false);
    const [allowUserModal, setAllowUserModal] = useState<boolean>(false);
    const [applicationUserModal, setApplicationUserModal] = useState<boolean>(false);

    const [refresh, setRefresh] = useState<boolean>(false);

    document.title = `${webInfo.name} - 好友`;
    onHeaderHandler("好友");

    const friendInfoDiv = useSpring({
        opacity: singleFriend.uuid === "" ? 0 : 1,
        config: {duration: 200}
    });

    const transitions = useTransition(groupItems, {
        keys: (item) => item.id,
        from: {opacity: 0, transform: "scale(0.9)"},
        enter: {opacity: 1, transform: "scale(1)"},
        leave: {opacity: 0, transform: "scale(0.9)"},
        config: {duration: 300},
        trail: 100
    });

    useEffect(() => {
        setTimeout(() => {
            const searchList = document.getElementById("search-list");
            if (!searchList) return;

            const children = searchList.children;
            for (let i = 0; i < children.length; i++) {
                const child = children[i] as HTMLElement;
                if (child.style.opacity === "0") {
                    child.style.display = "none";
                }
            }
        }, 350);
    }, [groupItems]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [userResp, appResp, allowResp, deniedResp] = await Promise.all([
                    GetUserFriendsListAPI(),
                    GetFriendApplicationAPI(),
                    GetFriendAllowAPI(),
                    GetFriendDeniedAPI()
                ]);

                if (userResp?.output === "Success") setUserList(userResp.data!);
                else message.warning(userResp?.error_message);

                if (appResp?.output === "Success") setFriendApplication(appResp.data!);
                else message.warning(appResp?.error_message);

                if (allowResp?.output === "Success") setFriendAllow(allowResp.data!);
                else message.warning(allowResp?.error_message);

                if (deniedResp?.output === "Success") setFriendDenied(deniedResp.data!);
                else message.warning(allowResp?.error_message);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData().then();
    }, [addUserModal, allowUserModal, applicationUserModal, refresh]);

    useEffect(() => {
        const button = document.querySelector("#flicker-button");
        if (!button) return;

        const interval = setInterval(() => {
            button.classList.toggle("bg-red-700");
        }, 1000);

        return () => clearInterval(interval);
    });

    useEffect(() => {
        if (singleFriend.uuid !== "") {
            const randomBool = Math.random() >= 0.1;
            setGroupItems((prevItems) => {
                if (randomBool) {
                    return [
                        ...prevItems,
                        {
                            id: Math.random() * 1000,
                            name: `小组${prevItems.length + 1}`,
                            description: `小组描述${prevItems.length + 1}`
                        }
                    ];
                } else {
                    return prevItems.length > 0
                        ? prevItems.filter((item) => item.id !== prevItems[0].id) : prevItems;
                }
            });

            setFriendInfo(
                <animated.div
                    style={friendInfoDiv}
                    className="col-span-4 bg-white rounded-lg shadow-lg p-3 space-y-3 h-full max-h-dvh -mb-32 overflow-hidden">
                    <div className="text-xl font-bold">好友信息</div>
                    <div className="flex flex-col col-span-1 md:col-span-2 p-3 space-y-3 h-full overflow-y-auto">
                        <div className="flex gap-3 items-center">
                            <img src={handlerAvatar()} alt={singleFriend.uuid} className="size-20 rounded-full"/>
                            <div className="flex flex-col">
                                <p className="text-2xl font-bold">{singleFriend.username}</p>
                                <p className="text-sm text-gray-500">{singleFriend.phone}</p>
                                <p className="text-sm text-gray-500">{singleFriend.email}</p>
                            </div>
                        </div>
                        <div className="grid grid-cols-1 md:grid-cols-2">
                            <div className="flex gap-3">
                                <div className="flex-1">
                                    <div className="text-lg font-bold">账号情况</div>
                                    {singleFriend.enable ? (
                                        <div
                                            className="text-sm bg-green-500 text-white px-2 py-1 text-center rounded-lg shadow transition hover:bg-green-600">
                                            正常
                                        </div>
                                    ) : (
                                        <div
                                            className="text-sm bg-red-500 text-white px-2 py-1 text-center rounded-lg shadow transition hover:bg-red-600">
                                            已禁用
                                        </div>
                                    )}
                                </div>
                                <div className="flex-1">
                                    <div className="text-lg font-bold">封禁状态</div>
                                    {singleFriend.banned_at ? (
                                        <div
                                            className="text-sm bg-red-500 text-white px-2 py-1 text-center rounded-lg shadow transition hover:bg-red-600">
                                            已封禁
                                        </div>
                                    ) : (
                                        <div
                                            className="text-sm bg-green-500 text-white px-2 py-1 text-center rounded-lg shadow transition hover:bg-green-600">
                                            正常
                                        </div>
                                    )}
                                </div>
                            </div>
                            <div className="flex justify-end items-end">
                                <button onClick={() => deleteUserSubmit(singleFriend.uuid)}
                                        className="transition bg-red-500 flex items-center px-4 py-2 text-white rounded-md hover:bg-red-600 space-x-3 hover:scale-105">
                                    <DeleteOutlined/>
                                    <span>删除好友</span>
                                </button>
                            </div>
                        </div>
                        <div className="border-b-4 border-gray-200 rounded-full"/>
                        <div className="text-lg font-medium">共同的小组</div>
                        <div className="grid gap-3 grid-cols-2 md:grid-cols-3 lg:grid-cols-4 py-1"
                             id="search-list"
                        >
                            {transitions((style, item) => (
                                <animated.div key={item.id} style={style} id={item.id.toString()}>
                                    <div className="grid gap-1 items-center p-3 bg-sky-100/75 rounded-lg shadow">
                                        <div className="text-lg font-bold">{item.name}</div>
                                        <div className="text-sm text-gray-500">{item.description}</div>
                                    </div>
                                </animated.div>
                            ))}
                        </div>
                    </div>
                </animated.div>
            );
        }
    }, [singleFriend]);

    function handlerAvatar(): string {
        const avatars = [avatar1, avatar2, avatar3, avatar4];
        return avatars[Math.floor(Math.random() * avatars.length)];
    }

    function makeUserListElement(): JSX.Element[] {
        return userList.map((item, index) => (
            <div
                key={index}
                className={`transition flex items-center justify-between p-3 hover:bg-gray-100 w-full cursor-pointer rounded-lg ${singleFriend.uuid === item.uuid ? "bg-gray-100" : ""}`}
                onClick={() => setSingleFriend(item)}
            >
                <div className="flex items-center w-full">
                    <img src={handlerAvatar()} alt={item.uuid} className="w-10 h-10 rounded-full"/>
                    <div className="ml-3 overflow-hidden">
                        <p className="text-lg font-medium text-ellipsis overflow-hidden whitespace-nowrap">{item.username}</p>
                        <p className="text-sm text-gray-500 text-ellipsis overflow-hidden whitespace-nowrap">{item.phone}</p>
                    </div>
                </div>
            </div>
        ));
    }

    function getAlertButton(): JSX.Element {
        if (friendAllow.length > 0 || friendDenied.length > 0) {
            return (
                <div id="flicker-button" onClick={() => setApplicationUserModal(true)}
                     className="transition bg-red-500 flex items-center px-2 text-white rounded-md hover:bg-red-600">
                    <AlertOutlined/>
                </div>
            );
        } else {
            return <div/>;
        }
    }

    function getAllowFriendButton(): JSX.Element {
        if (friendApplication.length > 0) {
            return (
                <div onClick={() => setAllowUserModal(true)}
                     className="transition bg-emerald-500 flex items-center px-2 text-white rounded-md hover:bg-emerald-600">
                    <UserAddOutlined/>
                </div>
            );
        } else {
            return <></>;
        }
    }

    async function deleteUserSubmit(uuid: string) {
        const getResp = await DeleteUserAPI(uuid);
        if (getResp?.output === "Success") {
            message.success("好友删除成功");
            setUserList(userList.filter(item => item.uuid !== uuid));
            setSingleFriend({uuid: ""} as UserFriendEntity);
        } else {
            message.warning(getResp?.error_message);
        }
    }

    return (
        <>
            <div className="grid gap-3 grid-cols-6 h-full">
                <div
                    className={`col-span-2 bg-white rounded-lg shadow-lg p-3 space-y-3 w-full h-full flex flex-col max-h-dvh ${userList.length > 0 ? "-mb-32" : ""}`}>
                    <div className="flex justify-between">
                        <div className="text-xl font-bold">好友列表</div>
                        <div className="flex gap-1">
                            {getAlertButton()}
                            {getAllowFriendButton()}
                            <button onClick={() => setAddUserModal(true)}
                                    className="transition bg-sky-500 flex items-center px-4 text-white rounded-md hover:bg-sky-600 space-x-1">
                                <PlusOutlined/>
                                <span>添加</span>
                            </button>
                        </div>
                    </div>
                    {userList.length > 0 ?
                        <div className="overflow-y-auto overflow-x-hidden space-y-1">
                            {makeUserListElement()}
                        </div>
                        :
                        <div className="text-md text-center">暂无好友</div>
                    }
                </div>
                {friendInfo}
            </div>
            <FriendsAddModal propOpen={addUserModal} emit={(value: boolean) => setAddUserModal(value)}/>
            <FriendsAllowWaitModal propOpen={allowUserModal} friendApplication={friendApplication}
                                   emit={(value: boolean) => setAllowUserModal(value)} refresh={setRefresh}/>
            <FriendsApplicationModal propOpen={applicationUserModal} friendPending={friendAllow} friendDenied={friendDenied}
                                     emit={(value: boolean) => setApplicationUserModal(value)} refresh={setRefresh}/>
        </>
    );
}
