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
import {ScheduleGroupEntity} from "../../models/entity/schedule_group_entity.ts";
import {CloseOutlined} from "@ant-design/icons";

export function GroupManageModal({propOpen, groupEntity, refresh, emit}: {
    propOpen: boolean,
    groupEntity: ScheduleGroupEntity,
    refresh: (data: boolean) => void,
    emit: (data: boolean) => void
}) {
    const [open, setOpen] = useState<boolean>(false);
    const [entity, setEntity] = useState<ScheduleGroupEntity>({} as ScheduleGroupEntity);
    const [changeTag, setChangeTag] = useState<string>("" as string);

    useEffect(() => {
        setOpen(propOpen);
        setEntity(groupEntity);
    }, [groupEntity, propOpen]);

    function handleOk() {
        emit(false);
        refresh(true);
    }

    return (
        <Modal
            open={open}
            title="小组管理"
            onOk={handleOk}
            onCancel={() => emit(false)}
            footer={
                <div className="flex gap-1 justify-end text-white">
                    <button onClick={() => emit(false)}
                            className="py-1.5 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition">
                        取消
                    </button>
                    <button onClick={handleOk}
                            className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition">
                        完成
                    </button>
                </div>
            }
        >
            <div className="flex flex-col gap-3">
                <div className="flex flex-col gap-2">
                    <div className="text-xl font-bold">小组信息</div>
                    <div>
                        <label htmlFor="group-name" className="flex font-medium text-gray-700 gap-1">
                            <span>小组名字</span>
                            <span className={"text-red-500 text-xs"}>*</span>
                        </label>
                        <input
                            type="text"
                            id="group-name"
                            placeholder="john@rhcp.com"
                            value={entity.name}
                            className="transition w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
                        />
                    </div>
                    <div className={"flex-1"}>
                        <label htmlFor="group-tags" className="flex font-medium text-gray-700 gap-1">
                            <span>小组标签</span>
                            <span className={"text-red-500 text-xs"}>*</span>
                        </label>
                        <div className={"flex gap-1"}>
                            <input
                                type="text"
                                id="group-tags"
                                onChange={(event) => setChangeTag(event.currentTarget.value)}
                                placeholder="宿舍"
                                className="transition w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
                            />
                            <button className={"flex-shrink-0 py-1.5 px-4 rounded-md text-white shadow bg-sky-500 hover:bg-sky-600 transition"}
                                    onClick={async () => {
                                        if (changeTag.trim() !== "") {
                                            entity.tags?.push(changeTag.trim());
                                            setEntity({...entity});
                                        } else {
                                            message.warning("标签不能为空");
                                        }
                                    }}>
                                添加
                            </button>
                        </div>
                    </div>
                    <div className={"flex gap-1 flex-wrap break-words"}>
                        {
                            entity.tags?.map((tag, index) => (
                                <div key={index}
                                     className={"flex-shrink-0 transition bg-gray-100 text-gray-600 px-3 py-1 rounded-md justify-center flex space-x-1"}>
                                    <span>#{tag}</span>
                                    <CloseOutlined className={"transition text-gray-600 hover:text-gray-800"}
                                                   onClick={() => {
                                                       entity.tags.splice(index, 1);
                                                       setEntity({...entity});
                                                   }}/>
                                </div>
                            ))
                        }
                    </div>
                </div>
                <div className="flex flex-col">
                    <div className="text-xl font-bold">小组成员</div>
                </div>
            </div>
        </Modal>
    );
}
